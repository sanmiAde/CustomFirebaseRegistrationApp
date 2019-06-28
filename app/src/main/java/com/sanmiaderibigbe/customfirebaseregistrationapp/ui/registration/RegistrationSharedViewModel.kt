package com.sanmiaderibigbe.customfirebaseregistrationapp.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sanmiaderibigbe.customfirebaseregistrationapp.model.User

class RegistrationSharedViewModel : ViewModel() {

    val userRegistrationData = MutableLiveData<User>()


    fun updateUserData(user: User){
        userRegistrationData.value = user
    }

    fun clearUserData(){
        userRegistrationData.value  = User()
    }



}