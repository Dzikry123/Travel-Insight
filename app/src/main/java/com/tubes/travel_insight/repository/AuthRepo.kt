//package com.tubes.tourismapp.repository
//
//import com.tubes.tourismapp.data.api.ApiService
//import com.tubes.tourismapp.model.responses.ResponseLogin
//import com.tubes.tourismapp.model.responses.ResponseRegister
//
//class AuthRepo(private val apiService: ApiService,) {
//    suspend fun registerRepo(name: String, email: String, password:String) : ResponseRegister {
//        return apiService.register(name, email, password)
//    }
//
//    suspend fun loginRepo(email: String, password:String) : ResponseLogin {
//        return apiService.login(email, password)
//    }
//}