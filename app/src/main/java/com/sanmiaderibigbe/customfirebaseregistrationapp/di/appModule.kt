package com.sanmiaderibigbe.customfirebaseregistrationapp.di

import com.sanmiaderibigbe.customfirebaseregistrationapp.model.User
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.FirebaseRepository
import com.sanmiaderibigbe.customfirebaseregistrationapp.ui.login.LoginHomeSharedViewModel
import com.sanmiaderibigbe.customfirebaseregistrationapp.ui.registration.RegistrationBankViewModel
import com.sanmiaderibigbe.customfirebaseregistrationapp.ui.registration.RegistrationSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // single instance of HelloRepository
    single<FirebaseRepository> { FirebaseRepository() }

    factory { User(get()) }

    // MyViewMode   l ViewModel
    viewModel { LoginHomeSharedViewModel(get()) }

    viewModel { RegistrationSharedViewModel() }
    viewModel { RegistrationBankViewModel(get()) }

}