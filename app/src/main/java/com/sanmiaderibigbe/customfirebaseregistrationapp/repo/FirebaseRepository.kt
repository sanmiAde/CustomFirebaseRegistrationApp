package com.sanmiaderibigbe.customfirebaseregistrationapp.repo

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sanmiaderibigbe.customfirebaseregistrationapp.model.User
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Maybe

class FirebaseRepository {

     val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val userNode = "users"


    fun authenticate(email: String, password: String): Maybe<AuthResult> {
        return RxFirebaseAuth.signInWithEmailAndPassword(firebaseAuth, email, password)
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun register(email: String, password: String): Maybe<AuthResult> {

        return RxFirebaseAuth.createUserWithEmailAndPassword(firebaseAuth, email, password)
    }

    fun uploadUserData(user: User): Completable {

        val currentUser = firebaseAuth.currentUser

        return RxFirebaseDatabase.setValue(
            firebaseDatabase.getReference("users")
                .child(currentUser?.uid!!), user)
    }


}