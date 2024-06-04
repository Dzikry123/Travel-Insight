//package com.tubes.tourismapp.ui.screen.login
//
//import androidx.compose.foundation.layout.height
//import com.tubes.tourismapp.R
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Button
//import androidx.compose.material.ButtonDefaults
//import androidx.compose.material.Icon
//import androidx.compose.material.OutlinedTextField
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//
//@Composable
//fun LoginScreen(navController: NavController) {
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Login here",
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF1F51FF) // Blue color
//        )
//        Text(
//            text = "Welcome back you’ve been missed!",
//            fontSize = 16.sp,
//            color = Color.Black,
//            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
//        )
//
//        OutlinedTextField(
//            value = "",
//            onValueChange = {},
//            label = { Text("Email") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        OutlinedTextField(
//            value = "",
//            onValueChange = {},
//            label = { Text("Password") },
//            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
//        )
//
//        Text(
//            text = "Forgot your password?",
//            color = Color(0xFF1F51FF), // Blue color
//            modifier = Modifier
//                .align(Alignment.End)
//                .padding(top = 8.dp)
//        )
//
//        Button(
//            onClick = { /* Handle login */ },
//            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1F51FF)),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp)
//                .height(50.dp),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Text(
//                text = "Sign in",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp
//            )
//        }
//
//        Text(
//            text = "Create new account",
//            color = Color.Gray,
//            modifier = Modifier.padding(vertical = 16.dp)
//        )
//
//        Text(
//            text = "Or continue with",
//            color = Color.Gray,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            SocialLoginButton(R.drawable.icon_star)
//            SocialLoginButton(R.drawable.travel)
//            SocialLoginButton(R.drawable.person)
//        }
//    }
//}
//
//@Composable
//fun SocialLoginButton(iconRes: Int) {
//    Button(
//        onClick = { /* Handle social login */ },
//        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF1F1F1)),
//        modifier = Modifier.size(50.dp),
//        shape = CircleShape,
//        contentPadding = PaddingValues(0.dp)
//    ) {
//        Icon(
//            painter = painterResource(id = iconRes),
//            contentDescription = null,
//            tint = Color.Unspecified,
//            modifier = Modifier.size(24.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    val navController = rememberNavController()
//    LoginScreen(navController = navController)
//}
