package com.sanmiaderibigbe.customfirebaseregistrationapp.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.FirebaseRepository
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.Resource
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.Status
import io.reactivex.rxkotlin.subscribeBy

class LoginHomeSharedViewModel : ViewModel() {

    private val firebaseRepository = FirebaseRepository()

    private val TAG: String = "LoginViewModel"

    private val loginResource = MutableLiveData<Resource<AuthenticationState>>()

    enum class AuthenticationState {
        AUTHENTICATED,
        UNAUTHENTICATED,
    }

    init {
        when (firebaseRepository.firebaseAuth.currentUser == null) {

            true -> loginResource.value = Resource(Status.LOADED, AuthenticationState.UNAUTHENTICATED, null)

            false -> loginResource.value = Resource(Status.LOADED, AuthenticationState.AUTHENTICATED, null)
        }
    }


    fun signIn(email: String, password: String) {
        loginResource.value = Resource(Status.LOADING, AuthenticationState.UNAUTHENTICATED, null)
        firebaseRepository.authenticate(email, password)
            .subscribeBy(
                onComplete = { Log.d(TAG, "Firebase login task has completed") },
                onSuccess = { loggedIn: AuthResult -> this::updateAuthenticationState },
                onError = { throwable: Throwable -> this::updateLoginError }

            )


    }

    fun getLoginResource(): LiveData<Resource<AuthenticationState>> {
        return loginResource
    }

    private fun updateAuthenticationState(result: AuthResult) {
        when (result.user == null) {
            true -> loginResource.value = Resource(Status.ERROR, AuthenticationState.UNAUTHENTICATED, null)

            false -> loginResource.value = Resource(Status.SUCCESS, AuthenticationState.AUTHENTICATED, null)
        }
    }

    private fun updateLoginError(throwable: Throwable) {
        loginResource.value = Resource(Status.ERROR, AuthenticationState.UNAUTHENTICATED, throwable.localizedMessage)
    }


    fun signOut() {
        firebaseRepository.signOut()

        loginResource.value = Resource(Status.LOADED, AuthenticationState.UNAUTHENTICATED, null)


    }
}