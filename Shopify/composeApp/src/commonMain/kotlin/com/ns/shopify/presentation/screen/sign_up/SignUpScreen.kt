package com.ns.shopify.presentation.screen.sign_up

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.ns.shopify.presentation.componets.AlertDialog
import com.ns.shopify.presentation.componets.CustomDefaultBtn
import com.ns.shopify.presentation.componets.CustomTextField
import com.ns.shopify.presentation.componets.DefaultBackArrow
import com.ns.shopify.presentation.componets.ErrorSuggestion
import com.ns.shopify.presentation.componets.Loading


/**
 * Created by Ashwani Kumar Singh on 06,December,2023.
 */

internal class SignUpScreen(

    private val temp_firstName: String = "Ashwani",
    private val temp_lastName: String = "Singh",
    private val temp_address: String = "My Address",
    private val temp_phoneNumber: String = "+918390098880"

) : Screen {

    companion object {
        val temp_email: String = "kaxori9962@rentaen.com"
        val temp_passwd: String = "12345678"
    }


    @Composable
    override fun Content() {



        val navController = LocalNavigator.current
        if (navController != null) {
            SignUpScreen(navController = navController)
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun SignUpScreen(navController: Navigator) {
        var email = remember { mutableStateOf(TextFieldValue(temp_email)) }
        var password = remember { mutableStateOf(TextFieldValue(temp_passwd)) }
        var confirmPass = remember { mutableStateOf(TextFieldValue(temp_passwd)) }
        var firstName = remember { mutableStateOf(TextFieldValue(temp_firstName)) }
        var lastName = remember { mutableStateOf(TextFieldValue(temp_lastName)) }
        var phoneNumber = remember { mutableStateOf(TextFieldValue(temp_phoneNumber)) }
        var address = remember { mutableStateOf(TextFieldValue(temp_address)) }
        val emailErrorState = remember { mutableStateOf(false) }
        val passwordErrorState = remember { mutableStateOf(false) }
        val conPasswordErrorState = remember { mutableStateOf(false) }
        val firstNameErrorState = remember { mutableStateOf(false) }
        val lastNameErrorState = remember { mutableStateOf(false) }
        val phoneNumberErrorState = remember { mutableStateOf(false) }
        val addressErrorState = remember { mutableStateOf(false) }
        val animate = remember { mutableStateOf(true) }


        val viewModel = getScreenModel<SignUpViewModel>()
        val state = viewModel.state.collectAsState()

        val createCustomerAccount:()-> Unit = {
            viewModel.signUpRequest(
                email = email.value.text,
                password = password.value.text,
                firstName = firstName.value.text,
                lastName = lastName.value.text,
                phone = phoneNumber.value.text,
            )
        }

        val dialogErrorClicked:()->Unit = {
            viewModel.clearErrorState()
        }


        AnimatedContent(targetState = animate.value, transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { value ->
                    value
                }
            ) with slideOutHorizontally(
                targetOffsetX = { value ->
                    -value
                }
            )
        }) {
            if (it) {  // First Part of SingUp Screen
                RegisterAccount(
                    email = email,
                    password = password,
                    confirmPass = confirmPass,
                    emailErrorState = emailErrorState,
                    passwordErrorState = passwordErrorState,
                    conPasswordErrorState = conPasswordErrorState,
                    animate = animate,
                    navController = navController
                )
            } else { // Second Part of SingUp Screen
                CompleteProfile(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    address = address,
                    firstNameErrorState = firstNameErrorState,
                    lastNameErrorState = lastNameErrorState,
                    phoneNumberErrorState = phoneNumberErrorState,
                    addressErrorState = addressErrorState,
                    animate = animate,
                    createCustomerAccount = createCustomerAccount,
                    state = state,
                    navController = navController,
                    dialogErrorClicked = dialogErrorClicked
                )
            }
        }
    }


    /**
     * (Register Account UIs) First Part of SingUp Screen
     */
    @Composable
    fun RegisterAccount(
        email: MutableState<TextFieldValue>,
        password: MutableState<TextFieldValue>,
        confirmPass: MutableState<TextFieldValue>,
        emailErrorState: MutableState<Boolean>,
        passwordErrorState: MutableState<Boolean>,
        conPasswordErrorState: MutableState<Boolean>,
        animate: MutableState<Boolean>,
        navController: Navigator
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Box(modifier = Modifier.weight(0.7f)) {
                    DefaultBackArrow {
                        navController.pop()
                    }
                }
                Box(modifier = Modifier.weight(1.0f)) {
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colors.primary,
                        fontSize = 18.sp
                    )
                }


            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "Register Account", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Complete your details or continue\nwith social media.",
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))
            CustomTextField(
                initialValue = temp_email,
                placeholder = "example@email.com",
                label = "Email",
                errorState = emailErrorState,
                keyboardType = KeyboardType.Email,
                visualTransformation = VisualTransformation.None,
                onChanged = { newEmail ->
                    email.value = newEmail
                },
                icon = Icons.Outlined.Email
            )

            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                initialValue = temp_passwd,
                placeholder = "********",
                label = "Password",
                keyboardType = KeyboardType.Password,
                errorState = passwordErrorState,
                visualTransformation = PasswordVisualTransformation(),
                onChanged = { newPass ->
                    password.value = newPass
                },
                icon = Icons.Outlined.Lock
            )


            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                initialValue = temp_passwd,
                placeholder = "********",
                label = "Confirm Password",
                keyboardType = KeyboardType.Password,
                errorState = conPasswordErrorState,
                visualTransformation = PasswordVisualTransformation(),
                onChanged = { newPass ->
                    confirmPass.value = newPass
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
            if (conPasswordErrorState.value) {
                ErrorSuggestion("Confirm Password miss matched.")
            }
            CustomDefaultBtn(shapeSize = 50f, btnText = "Continue") {
                //email pattern

                val isEmailValid = email.value.text.isNotEmpty()
                val isPassValid = password.value.text.length >= 8
                val conPassMatch = password.value.text == confirmPass.value.text
                emailErrorState.value = !isEmailValid
                passwordErrorState.value = !isPassValid
                conPasswordErrorState.value = !conPassMatch
                if (isEmailValid && isPassValid && conPassMatch) {
                    animate.value = !animate.value
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
                                MaterialTheme.colors.primary,
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
                                MaterialTheme.colors.primary,
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
                                MaterialTheme.colors.primary,
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



                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .clickable {

                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "By continuing you confirm that you agree",
                        color = MaterialTheme.colors.primary
                    )
                    Row()
                    {
                        Text(
                            text = "with our ",
                            color = MaterialTheme.colors.primary,
                        )
                        Text(
                            text = "Terms & Condition",
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.clickable {

                            })
                    }

                }
            }


        }
    }

    @Composable
    fun CompleteProfile(
        firstName: MutableState<TextFieldValue>,
        lastName: MutableState<TextFieldValue>,
        phoneNumber: MutableState<TextFieldValue>,
        address: MutableState<TextFieldValue>,
        firstNameErrorState: MutableState<Boolean>,
        lastNameErrorState: MutableState<Boolean>,
        phoneNumberErrorState: MutableState<Boolean>,
        addressErrorState: MutableState<Boolean>,
        animate: MutableState<Boolean>,
        navController: Navigator,
        createCustomerAccount:()-> Unit,
        state : State<SignUpState>,
        dialogErrorClicked:()->Unit
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Box(modifier = Modifier.weight(0.7f)) {
                    DefaultBackArrow {
                        animate.value = !animate.value
                    }
                }
                Box(modifier = Modifier.weight(1.0f)) {
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colors.primary,
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = "Complete Profile", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Complete your details or continue\nwith social media.",
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))
            CustomTextField(
                initialValue = temp_firstName,
                placeholder = "Enter your first name",
                label = "First Name",
                errorState = firstNameErrorState,
                keyboardType = KeyboardType.Text,
                visualTransformation = VisualTransformation.None,
                onChanged = { newText ->
                    firstName.value = newText
                },
                icon = null
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                initialValue = temp_lastName,
                placeholder = "Enter your last name",
                label = "Last Name",
                errorState = lastNameErrorState,
                keyboardType = KeyboardType.Text,
                visualTransformation = VisualTransformation.None,
                onChanged = { newText ->
                    lastName.value = newText
                },
                icon = null
            )

            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                initialValue = temp_phoneNumber,
                placeholder = "Enter your phone number",
                label = "Phone Number",
                keyboardType = KeyboardType.Phone,
                errorState = phoneNumberErrorState,
                visualTransformation = VisualTransformation.None,
                onChanged = { newNumber ->
                    phoneNumber.value = newNumber
                },
                icon = null
            )


            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                initialValue = temp_address,
                placeholder = "example: Dhaka, Bangladesh",
                label = "Address",
                keyboardType = KeyboardType.Password,
                errorState = addressErrorState,
                visualTransformation = VisualTransformation.None,
                onChanged = { newText ->
                    address.value = newText
                },
                icon = null
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (firstNameErrorState.value || lastNameErrorState.value) {
                ErrorSuggestion("Please enter valid name.")
            }
            if (phoneNumberErrorState.value) {
                ErrorSuggestion("Please enter valid phone number.")
            }
            if (addressErrorState.value) {
                ErrorSuggestion("Please enter valid address.")
            }

            Box(){
                if(state.value.isLoading){
                    Loading()
                } else {
                    CustomDefaultBtn(shapeSize = 50f, btnText = "Continue") {
                        val isPhoneValid =
                            phoneNumber.value.text.isNotEmpty() || phoneNumber.value.text.length > 9
                        val isFNameValid = firstName.value.text.isNotEmpty() || firstName.value.text.length > 3
                        val isLNameValid = lastName.value.text.isNotEmpty() || lastName.value.text.length > 3
                        val isAddressValid = address.value.text.isNotEmpty() || address.value.text.length > 5
                        firstNameErrorState.value = !isFNameValid
                        lastNameErrorState.value = !isLNameValid
                        addressErrorState.value = !isAddressValid
                        phoneNumberErrorState.value = !isPhoneValid
                        if (isFNameValid && isLNameValid && isAddressValid && isPhoneValid) {
                            // TODO, Navigate to OTP Screen
                            //navController.navigate(AuthScreen.OTPScreen.route)

                            createCustomerAccount()
                        }
                    }
                }
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .clickable {

                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "By continuing you confirm that you agree",
                    color = MaterialTheme.colors.primary
                )
                Row()
                {
                    Text(
                        text = "with our ",
                        color = MaterialTheme.colors.primary,
                    )
                    Text(
                        text = "Terms & Condition",
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.clickable {

                        })
                }

            }
        }

        if(state.value.isLoaded) {
            AlertDialog(
                title = "Success",
                message = "Account created successfully. \nPlease go to your registered email and verify your account, before login.",
                onClose = {
                    navController.pop()
                },

            )
        }
        if(state.value.error != null && state.value.error!!.isNotBlank()) {
            AlertDialog(
                title = "Error!",
                message = state.value.error,
                onClose = {
                    dialogErrorClicked()
                },

                )
        }

    }

}


