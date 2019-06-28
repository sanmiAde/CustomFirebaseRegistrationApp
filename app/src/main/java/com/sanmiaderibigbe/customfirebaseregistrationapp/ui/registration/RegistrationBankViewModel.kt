package com.sanmiaderibigbe.customfirebaseregistrationapp.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sanmiaderibigbe.customfirebaseregistrationapp.model.User
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.FirebaseRepository
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

class RegistrationBankViewModel(private val firebaseRepository: FirebaseRepository) : ViewModel() {


    private lateinit var registrationSuccessFull: MutableLiveData<Boolean>

    private val registrationResource = MutableLiveData<Resource<Boolean>>()

    fun register(user: User): MutableLiveData<Resource<Boolean>> {
        registrationResource.value = Resource.loading(false)

        val registerUserDetails = user.copy(password = "", verify_password = "")
        firebaseRepository.register(registerUserDetails.email, user.password)
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onSuccess = {

                    firebaseRepository.uploadUserData(registerUserDetails).observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(

                            onComplete = { registrationResource.value = Resource.success(true) },
                            onError = { throwable ->

                                registrationResource.value = Resource.error(throwable.localizedMessage, false)
                            }
                        )
                },
                onError = { throwable ->
                    registrationResource.value = Resource.error(throwable.localizedMessage, false)

                }

            )

        return registrationResource
    }

    private fun updateRegistrationError(throwable: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}