package com.ns.shopify.presentation.screen.forgot_password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.ns.shopify.presentation.componets.CustomDefaultBtn
import com.ns.shopify.presentation.componets.CustomTextField
import com.ns.shopify.presentation.componets.DefaultBackArrow


/**
 * Created by Ashwani Kumar Singh on 06,December,2023.
 */

internal class ForgetPasswordScreen : Screen {

    @Composable
    override fun Content() {
        val navController = LocalNavigator.current
        if (navController != null) {
            ForgetPasswordScreen(navController = navController)
        }
    }
}

@Composable
fun ForgetPasswordScreen(navController: Navigator) {
    var email: String = ""
    val emailErrorState = remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {

            Box(modifier = Modifier.weight(0.3f)) {
                DefaultBackArrow() {
                    navController.pop()
                }
            }
            Box(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = "Forget Password",
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 18.sp
                )
            }


        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Forget Password", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Please enter your email and we will send\nyou a link to return your account",
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(150.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CustomTextField(
                placeholder = "example@email.com",
                label = "Email",
                errorState = emailErrorState,
                keyboardType = KeyboardType.Email,
                visualTransformation = VisualTransformation.None,
                onChanged = { newEmail ->
               //     email = newEmail
                }
            )

            CustomDefaultBtn(shapeSize = 50f, btnText = "Continue") {
                //email pattern
                val isEmailValid = true
                emailErrorState.value = !isEmailValid
                if (isEmailValid) {
                    // TODO, make API request navigate to sign in screen
                    navController.pop()
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 30.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Don't have an account? ", color = MaterialTheme.colors.onPrimary)
                Text(
                    text = "Sign Up",
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable {
                        // TODO, navigate to sign up screen
                        //navController.push(AuthScreen.SignUpScreen.route)
                    })
            }
        }


    }
}