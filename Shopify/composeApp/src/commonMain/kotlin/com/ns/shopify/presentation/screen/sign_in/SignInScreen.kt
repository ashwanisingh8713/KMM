package com.ns.shopify.presentation.screen.sign_in


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.ns.shopify.presentation.componets.CustomDefaultBtn
import com.ns.shopify.presentation.componets.CustomTextField
import com.ns.shopify.presentation.componets.ErrorSuggestion
import com.ns.shopify.presentation.screen.forgot_password.ForgetPasswordScreen
import com.ns.shopify.presentation.screen.sign_up.SignUpScreen
import com.ns.shopify.presentation.settings.SettingsViewModel
import org.koin.compose.rememberKoinInject


/**
 * Created by Ashwani Kumar Singh on 05,December,2023.
 */
class SignInScreen : Screen {

    @Composable
    override fun Content() {
        val navController = LocalNavigator.current
        if (navController != null) {
            LoginComponent(navController = navController)
        }

    }


    @Composable
    fun LoginComponent(navController: Navigator) {
        var email by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var checkBox by remember {
            mutableStateOf(false)
        }
        val emailErrorState = remember {
            mutableStateOf(false)
        }
        val passwordErrorState = remember {
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
                // Back Arrow Button
                /*Box(modifier = Modifier.weight(0.7f)) {
                    DefaultBackArrow {
                        navController.pop()
                    }
                }*/
                Box(modifier = Modifier.weight(1.0f)) {
                    Text(text = "Sign in", color = MaterialTheme.colors.onPrimary, fontSize = 18.sp)
                }

            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "Welcome! Sign In", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Sign in with your email or password\nor continue with social media.",
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))
            CustomTextField(
                placeholder = "example@email.com",
                label = "Email",
                errorState = emailErrorState,
                keyboardType = KeyboardType.Email,
                visualTransformation = VisualTransformation.None,
                onChanged = { newEmail ->
                    email = newEmail
                },
                icon = Icons.Outlined.Email
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                placeholder = "********",
                label = "Password",
                keyboardType = KeyboardType.Password,
                errorState = passwordErrorState,
                visualTransformation = PasswordVisualTransformation(),
                onChanged = { newPass ->
                    password = newPass
                },
                icon = Icons.Outlined.Lock
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (emailErrorState.value) {
                ErrorSuggestion("Please enter valid email address.")
            }
            if (passwordErrorState.value) {
                Row() {
                    ErrorSuggestion("Please enter valid password.")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checkBox, onCheckedChange = {
                            checkBox = it
                        },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                    )
                    Text(
                        text = "Remember me",
                        color = MaterialTheme.colors.primary,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = "Forget Password",
                    color = MaterialTheme.colors.primary,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable {
                        // TODO: Forgot Password Navigation
                        navController.push(ForgetPasswordScreen())
                    }
                )
            }

            val vm = rememberKoinInject<SettingsViewModel>()

            CustomDefaultBtn(shapeSize = 50f, btnText = "Continue") {
                //email pattern
//            val pattern = Patterns.EMAIL_ADDRESS
//            val isEmailValid = pattern.matcher(email.text).matches()
                val isEmailValid = email.text.isNotEmpty()
                val isPassValid = password.text.length >= 8
                emailErrorState.value = !isEmailValid
                passwordErrorState.value = !isPassValid
                if (isEmailValid && isPassValid) {
                    // TODO: Login Success Navigation
                    // navController.navigate(AuthScreen.SignInSuccess.route)
                    vm.saveLoggedInStatus(true) // From here it sends callback to App.kt
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 10.dp,
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                MaterialTheme.colors.secondary,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberVectorPainter(image = Icons.Outlined.Person),
                            contentDescription = "Google Login Icon"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                MaterialTheme.colors.secondary,
                                shape = CircleShape
                            )
                            .clickable {

                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = rememberVectorPainter(image = Icons.Outlined.Person),
                            contentDescription = "Twitter Login Icon"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                MaterialTheme.colors.secondary,
                                shape = CircleShape
                            )
                            .clickable {

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberVectorPainter(image = Icons.Outlined.Person),
                            contentDescription = "Facebook Login Icon"
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Don't have an account? ", color = MaterialTheme.colors.primary)
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.clickable {
                            // TODO: Sign Up Navigation
                            navController.push(SignUpScreen())
                        })
                }
            }


        }
    }

}


