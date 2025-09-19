package com.example.animationdemo.ui.theme

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthState

class AuthViewModel : ViewModel() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    private val _username = MutableLiveData<String>("")
    val username: LiveData<String> = _username
    val authState: LiveData<AuthState> = _authState
    init {
        checkAuthStatus()
    }


    fun checkAuthStatus(){
        if(auth.currentUser==null){
            _authState.value = com.example.animationdemo.ui.theme.AuthState.Unauthenticated
        }else{
            _authState.value = com.example.animationdemo.ui.theme.AuthState.Authenticated
            _username.value = auth.currentUser?.displayName ?: "Guest"
        }
    }
    fun login(email : String,password : String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = com.example.animationdemo.ui.theme.AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = com.example.animationdemo.ui.theme.AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    _authState.value = com.example.animationdemo.ui.theme.AuthState.Authenticated
                    _username.value = auth.currentUser?.displayName ?: "Guest"
                }else{
                    _authState.value = com.example.animationdemo.ui.theme.AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signup(email: String, password: String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = com.example.animationdemo.ui.theme.AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = com.example.animationdemo.ui.theme.AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    auth.currentUser?.updateProfile(
                        com.google.firebase.auth.UserProfileChangeRequest.Builder()
                            .build())
                    _authState.value = com.example.animationdemo.ui.theme.AuthState.Authenticated
                }else{
                    _authState.value = com.example.animationdemo.ui.theme.AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = com.example.animationdemo.ui.theme.AuthState.Unauthenticated
    }

}
sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}