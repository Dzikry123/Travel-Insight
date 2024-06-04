//package com.tubes.tourismapp.ui.screen.login
//
//import androidx.lifecycle.ViewModel
//import com.tubes.tourismapp.model.responses.ResponseLogin
//import com.tubes.tourismapp.repository.AuthRepo
//
//
//class LoginViewModel(private val authRepo: AuthRepo) : ViewModel() {
//    suspend fun loginVM(email: String, password: String): ResponseLogin {
//        val response = authRepo.loginRepo(email, password)
//        return response
//    }
//}
