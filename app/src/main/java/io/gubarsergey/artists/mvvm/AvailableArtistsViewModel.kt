package io.gubarsergey.artists.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.gubarsergey.artists.service.AvailableArtistsAPI
import io.gubarsergey.artists.service.AvailableArtistsResponseDto
import io.gubarsergey.artists.ui.AvailableArtistsProps
import io.gubarsergey.auth.AuthState
import io.gubarsergey.auth.mvvm.PrefHelper
import io.gubarsergey.common.APIError
import io.gubarsergey.redux.operations.Command
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AvailableArtistsViewModel(
    private val api: AvailableArtistsAPI,
    private val prefHelper: PrefHelper,
) : ViewModel() {

    enum class Sorting {
        NONE,
        MOST_ORDERS,
        BEST_RATING,
    }

    private var artistsData: AvailableArtistsResponseDto? = null

    private val _artists = MutableLiveData<List<AvailableArtistsProps.ArtistProps>>()
    val artists: LiveData<List<AvailableArtistsProps.ArtistProps>> = _artists

    private val _errors = MutableLiveData<APIError>()
    val errors: LiveData<APIError> = _errors

    private val _sorting = MutableLiveData<Sorting>()
    val sorting: LiveData<Sorting> = _sorting

    private val _chipsSelected: MutableLiveData<MutableList<String>> = MutableLiveData()
    val chipsSelected: LiveData<MutableList<String>> = _chipsSelected

    init {
        loadArtists()
    }

    private fun loadArtists() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    api.getAvailableArtists(prefHelper.getToken()?.bearerFormatted ?: "")
                }
            }

            result.fold(
                onSuccess = {
                    val mapped = it.toUI()
                    artistsData = it
                    _artists.postValue(mapped)
                },
                onFailure = {
                    _errors.postValue(APIError.General)
                }
            )

        }
    }

    private fun AvailableArtistsResponseDto.toUI(): List<AvailableArtistsProps.ArtistProps> {
        return this.artists.map {
            AvailableArtistsProps.ArtistProps(
                id = it._id,
                fullName = it.firstName + " " + it.lastName,
                profileDescription = it.profileDescription,
                genres = it.genres,
                averageRating = it.ratings.map { rating -> rating.rating }.average(),
                ratingCount = it.ratings.size,
                email = it.email,
                makeAnOrder = Command(action = {}),
                userRole = AuthState.UserRole.CUSTOMER,
            )
        }
    }

    fun selectBestRatingFilter() {
        _sorting.value = Sorting.BEST_RATING
        _artists.value = _artists.value?.sortedByDescending { it.averageRating }
    }

    fun selectMostOrdersFilter() {
        _sorting.value = Sorting.MOST_ORDERS
        _artists.value = _artists.value?.sortedByDescending { it.ratingCount }
    }

    fun selectNoneFilter() {
        _sorting.value = Sorting.NONE
        _artists.value = artistsData?.toUI()
    }

    fun selectChip(chip: String) {
        val chips = _chipsSelected.value ?: mutableListOf()
        if (chip in chips) {
            chips.remove(chip)
        } else {
            chips.add(chip)
        }
        _chipsSelected.value = chips
        _artists.value = _artists.value?.filter { it.genres.containsAll(chips) }
    }
}
