package com.example.erpacebyte.registration

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.erpacebyte.R
import com.example.erpacebyte.mvvm.ERPUserMainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {
    private lateinit var userName:TextInputEditText
    private lateinit var userPassword:TextInputEditText
    private lateinit var userFilledCaptcha:TextInputEditText
    private lateinit var userFilledCaptchaLayout:TextInputLayout
    private lateinit var loginBtn:Button
    private lateinit var captcha:String
    private lateinit var captchaTextView:TextView
    private lateinit var forgetPasswordTV:TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewBG: View

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_login, container, false)

        //Initializing Views
        userName = view.findViewById(R.id.eTUsername)
        userPassword = view.findViewById(R.id.eTPassword)
        userFilledCaptcha = view.findViewById(R.id.eTCaptcha)
        userFilledCaptchaLayout = view.findViewById(R.id.userCaptchaLayout)
        loginBtn = view.findViewById(R.id.loginBtn)
        captchaTextView = view.findViewById(R.id.tvCaptcha)
        forgetPasswordTV = view.findViewById(R.id.tVForgetPassword)
        progressBar = view.findViewById(R.id.progressBar)
        viewBG = view.findViewById(R.id.viewBG)

        //Handling Captcha and End Icon Click
        captcha = generateNewCaptcha()
        captchaTextView.text = captcha
        userFilledCaptchaLayout.setEndIconOnClickListener {
            captcha = generateNewCaptcha()
            captchaTextView.text = captcha
        }

        //ViewModel
        val viewModel = ViewModelProvider(this)[ERPUserMainViewModel::class.java]

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        //handling progress bar
        viewModel.isLoading.observe(viewLifecycleOwner) { loading->
            if(loading) {
                progressBar.visibility= View.VISIBLE
                viewBG.visibility= View.VISIBLE
            }
            else {
                progressBar.visibility = View.GONE
                viewBG.visibility = View.GONE
            }
        }

        //Forget Password Click
        forgetPasswordTV.setOnClickListener {
            Snackbar.make(view,"Contact Admin", Snackbar.LENGTH_LONG).show()
        }

        // Observe user details
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {

                if (user.userPassword == userPassword.text.toString()) {
                    Snackbar.make(view, "Login Successful!", Snackbar.LENGTH_SHORT).show()
                    saveUserToPrefs(userName.text.toString())
                    view.findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)

                } else {
                    Snackbar.make(view, "Incorrect Details", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(view, "User not found. Contact Admin", Snackbar.LENGTH_SHORT).show()
            }
        }



        // handling login btn click

        view.findViewById<Button>(R.id.loginBtn).setOnClickListener {
            if(userName.text.toString().isEmpty() || userPassword.text.toString().isEmpty() || userFilledCaptcha.text.toString().isEmpty()){
                Snackbar.make(view,"Please fill all the fields",Snackbar.LENGTH_SHORT).show()
            }
            else if(captcha != userFilledCaptcha.text.toString()){
                Snackbar.make(view, "Enter correct captcha", Snackbar.LENGTH_SHORT).show()
            }
            else{
                viewModel.fetchUser(userName.text.toString())
            }
        }

        return view
    }

    private fun generateNewCaptcha(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" // Allowed characters
        return (1..4) // Change the number (6) to set captcha length
            .map { chars.random() }
            .joinToString("")
    }

    private fun saveUserToPrefs(userName: String) {
        val editor = sharedPreferences.edit()
        editor.putString("USERNAME", userName)
        editor.apply()
    }


}