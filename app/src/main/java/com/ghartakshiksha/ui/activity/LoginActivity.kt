package com.ghartakshiksha.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.ghartakshiksha.R
import com.ghartakshiksha.databinding.ActivityLoginBinding
import com.ghartakshiksha.ui.viewmodel.LoginViewModel
import com.ghartakshiksha.utility.AuthListener
import com.ghartakshiksha.utility.setStatusBarColor
import com.ghartakshiksha.utility.showMessageDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener, AuthListener {
    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(Color.parseColor("#FFFFFF"))
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewModel.authListener = this

        initListener()
    }

    private fun initListener() {
        binding.btnLogin.setOnClickListener(this)
        binding.llDontHaveAccount.setOnClickListener(this)
    }

    private fun validateLoginField(mobile: String, password: String): Boolean {
        var error: Boolean = false
        if (mobile.isEmpty()) {
            error = true
            showMessageDialog("Please add registered mobile number.", "OK")
        } else if (password.isEmpty()) {
            error = true
            showMessageDialog("Please add your account password.", "OK")
        }
        return error
    }

    override fun onClick(view: View?) {
        if (view == binding.btnLogin) {
            val mobile = binding.etMobile.text.toString()
            val password = binding.etPassword.text.toString()
            if (!validateLoginField(mobile, password)) {
                viewModel.tutorLogin(mobile, password)
            }
        } else if (view == binding.llDontHaveAccount) {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    override fun onStarted() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String, type: String) {
        binding.progressBar.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onFailure(message: String, type: String) {
        binding.progressBar.visibility = View.GONE
        showMessageDialog(message, getString(R.string.ok), positiveButtonFunction = null)
    }

    override fun onInternetInfo(message: String, type: String) {
        showMessageDialog(message, getString(R.string.ok), positiveButtonFunction = null)
    }
}