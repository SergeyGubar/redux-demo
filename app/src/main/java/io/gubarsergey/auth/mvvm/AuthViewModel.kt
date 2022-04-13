package io.gubarsergey.auth.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.gubarsergey.auth.service.AuthAPI
import io.gubarsergey.auth.service.AuthRequestDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AuthViewModel(
    private val authApi: AuthAPI,
    private val prefHelper: PrefHelper,
) : ViewModel() {

    private val _emailLiveData = MutableLiveData<String>()
    val emailLiveData = _emailLiveData

    private val _passwordLiveData = MutableLiveData<String>()
    val passwordLiveData = _passwordLiveData

    private val _navigationEvents = MutableLiveData<AuthFragmentMvvm.GoToMyOrders>()
    val navigationEvents = _navigationEvents

    fun login() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = runCatching {
                    authApi.login(AuthRequestDto(username = emailLiveData.value ?: "", password = passwordLiveData.value ?: ""))
                }
                result.fold(
                    onSuccess = {
                        Timber.d("Auth success. Token: ${it.access_token}")
                        prefHelper.saveToken(it.access_token)
                        _navigationEvents.postValue(AuthFragmentMvvm.GoToMyOrders)
                    },
                    onFailure = {
                        Timber.e("Failure $it")
                    }
                )
            }
        }
    }

    fun emailUpdated(email: String) {
        _emailLiveData.value = email
    }

    fun passwordUpdated(password: String) {
        _passwordLiveData.value = password
    }
}
