package com.respirex.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

object Firebase {

    private val auth by lazy { Firebase.auth }
    private val authUI by lazy { AuthUI.getInstance() }
    private val database by lazy { Firebase.database }
    private val usersRef by lazy { database.getReference("users") }

    fun isCurrentUserNull(): Boolean {
        return auth.currentUser == null
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun registerUser(uri: Uri, name: String, email: String, password: String, result: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                val userProfile = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(uri)
                    .build()
                auth.currentUser?.updateProfile(userProfile)?.await()
                usersRef.child(auth.currentUser?.uid!!).child("password").setValue(password).await()
                withContext(Dispatchers.Main) {
                    result("Success")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    when (e) {
                        is FirebaseAuthUserCollisionException -> result("Account already exists with given email address.")
                        is FirebaseNetworkException -> result("No internet connection")
                        is IllegalArgumentException -> result("Fill all details")
                        is NullPointerException -> result("Value passed is null")
                        is FirebaseException -> result("Issue with authentication service")
                        is CancellationException -> result("Issue while validating")
                        else -> result("Something went wrong. Try again later")
                    }
                }
            }
        }
    }

    fun verifyUser(email: String, password: String, result: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main) {
                    result("Success")
                    usersRef.child(auth.currentUser?.uid!!).child("password").setValue(password).await()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    when (e) {
                        is FirebaseNetworkException -> result("No internet connection")
                        is FirebaseAuthInvalidUserException -> result("User does not exist or is disabled")
                        is FirebaseAuthInvalidCredentialsException -> result("Enter valid credentials")
                        is IllegalArgumentException -> result("Fill all details")
                        is NullPointerException -> result("Value passed is null")
                        is FirebaseException -> result("Issue with authentication service")
                        is CancellationException -> result("Issue while validating")
                        else -> result("Something went wrong. Try again later")
                    }
                }
            }
        }
    }

    fun resetPassword(email: String, result: (Boolean,String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result(true,"If email is registered, a reset link has been sent to $email")
                } else {
                    when (task.exception) {
                        // no support from firebase
                        is FirebaseAuthInvalidUserException -> result(false,"No user found with this email address.")
                        is FirebaseAuthInvalidCredentialsException -> result(false,"No user found with this email address.")
                        // supports only
                        is FirebaseNetworkException -> result(false,"No internet connection")
                        is FirebaseAuthException -> result(false,"Authentication error")
                        else -> result(false,"Something went wrong. Try again later")
                    }
                }
            }
        }
    }

    fun getSignInIntent(): Intent {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        val signInIntent = authUI
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()
        return signInIntent
    }

    fun logoutUser(context: Context, onLogoutComplete: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val providerId = auth.currentUser?.providerData[1]?.providerId
            when (providerId) {
                "password" -> {
                    auth.signOut()
                    withContext(Dispatchers.Main) {
                        onLogoutComplete()
                    }
                }

                "google.com" -> {
                    authUI.signOut(context).addOnCompleteListener {
                        onLogoutComplete()
                    }
                }
            }
        }
    }

    fun addReport(report: Report, value: (String) -> Unit) {
        val reportRef = usersRef.child(auth.currentUser?.uid!!).child("report")
        reportRef.push().setValue(report).addOnCompleteListener {
            value("Success")
        }.addOnFailureListener {
            value("Something went wrong")
        }
    }

    fun fetchReport(result: (String, List<Report>?) -> Unit) {
        if (auth.currentUser != null) {
            val reportRef = usersRef.child(auth.currentUser?.uid!!).child("report")
            reportRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val reportList = mutableListOf<Report>()
                        for (eachReport in snapshot.children) {
                            val report = eachReport.getValue(Report::class.java)
                            if (report != null) {
                                reportList.add(report)
                            }
                        }
                        result("${reportList.size} record found", reportList)
                    } else {
                        result("No record found", null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    result("Something went wrong", null)
                }
            })
        }
    }

}