package com.sanmiaderibigbe.customfirebaseregistrationapp.repo

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseAuth
import io.reactivex.Maybe

class FirebaseRepository {

    val firebaseAuth = FirebaseAuth.getInstance()


    fun authenticate(email : String, password : String): Maybe<AuthResult> {
        return RxFirebaseAuth.signInWithEmailAndPassword(firebaseAuth, email, password)
    }

    fun signOut(){
        firebaseAuth.signOut()
    }



}