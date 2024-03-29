package com.sanmiaderibigbe.customfirebaseregistrationapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.FirebaseRepository
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.Resource
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

class LoginHomeSharedViewModel(private val firebaseRepository: FirebaseRepository) : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED,
        UNAUTHENTICATED,
    }

    private val TAG: String = "LoginViewModel"

    private val loginResource = MutableLiveData<Resource<AuthenticationState>>()


    init {
        when (firebaseRepository.firebaseAuth.currentUser == null) {

            true -> loginResource.value = Resource(Status.LOADED,
                AuthenticationState.UNAUTHENTICATED, null)

            false -> loginResource.value = Resource(Status.LOADED,
                AuthenticationState.AUTHENTICATED, null)
        }
    }


    fun signIn(email: String, password: String) {
        loginResource.value = Resource(Status.LOADING,
            AuthenticationState.UNAUTHENTICATED, null)
        firebaseRepository.authenticate(email, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { Log.d(TAG, "Firebase login task has completed") },
                onSuccess = {  authResult: AuthResult? -> updateAuthenticationState(authResult)  },
                onError = { throwable : Throwable -> updateLoginError(throwable) }

            )
    }

    fun getLoginResource(): LiveData<Resource<AuthenticationState>> {
        return loginResource
    }

    private fun updateAuthenticationState(result: AuthResult?) {
        when (result?.user == null) {
            true -> loginResource.value = Resource(Status.ERROR,
                AuthenticationState.UNAUTHENTICATED, null)

            false -> loginResource.value = Resource(Status.SUCCESS,
                AuthenticationState.AUTHENTICATED, null)
        }
    }

    private fun updateLoginError(throwable: Throwable) {
        loginResource.value = Resource(Status.ERROR,
            AuthenticationState.UNAUTHENTICATED, throwable.localizedMessage)
    }


    fun signOut() {
        firebaseRepository.signOut()
        loginResource.value = Resource(Status.LOADED,
            AuthenticationState.UNAUTHENTICATED, null)


    }
}