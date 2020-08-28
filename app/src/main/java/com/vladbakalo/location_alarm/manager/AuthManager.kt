package com.vladbakalo.location_alarm.manager

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    fun isUserAuthorized(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun createSignInWithEmailAndPassTask(email: String, pass: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, pass)
    }

    fun createSignInWithGoogleTask(email: String, pass: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, pass)
    }

    fun createRegisterWithEmailAndPassTask(email: String, pass: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, pass)
    }
}