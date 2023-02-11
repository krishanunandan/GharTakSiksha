package com.ghartakshiksha.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.ghartakshiksha.R
import com.ghartakshiksha.databinding.ActivitySignupBinding
import com.ghartakshiksha.ui.viewmodel.LoginViewModel
import com.ghartakshiksha.ui.viewmodel.SignupViewModel
import com.ghartakshiksha.utility.AuthListener
import com.ghartakshiksha.utility.setStatusBarColor
import com.ghartakshiksha.utility.showMessageDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SignupActivity : AppCompatActivity(), View.OnClickListener, AuthListener {
    lateinit var binding: ActivitySignupBinding
    var verificationID: String = ""
    var dialog: AlertDialog? = null
    private val signUpViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(Color.parseColor("#FFFFFF"))
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        signUpViewModel.authListener = this

        initListener()

        setAgreementText()

    }

    private fun setProgressDialog() {
        val llPadding = 30
        val ll = LinearLayout(this)
        ll.setBackgroundColor(resources.getColor(R.color.white))
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam
        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        // progressBar.setProgressColor()
        progressBar.layoutParams = llParam
        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(this)
        "Sending verification codes...".also { tvText.text = it }
        tvText.setTextColor(Color.parseColor("#333333"))
        tvText.textSize = 15f
        tvText.layoutParams = llParam
        ll.addView(progressBar)
        ll.addView(tvText)
        val builder: AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setView(ll)
        dialog = builder.create()
        dialog!!.show()
        val window: Window = dialog!!.window!!
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog!!.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog!!.window!!.attributes = layoutParams
        }
    }


    private fun initListener() {
        binding.ibBack.setOnClickListener(this)
        binding.btnGetOTP.setOnClickListener(this)
        binding.btnSignup.setOnClickListener(this)
        binding.llAlreadyHaveAccount.setOnClickListener(this)

        binding.etOTP.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 6) {
                    verifyCode(binding.etOTP.text.toString())
                }
            }
        })
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationID, code)
        verifyPhoneAuthentication(credential)
    }

    private fun sendVerificationCode(phoneNumber: String) {
        setProgressDialog()
        val options = PhoneAuthOptions.newBuilder(Firebase.auth).setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS).setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onCodeSent(
                    verificationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    verificationID = verificationId
                    Log.i("VerificationId - ", verificationId)
                    if (dialog != null) {
                        dialog!!.dismiss()
                    }
                    showMessageDialog(
                        "OTP has been send to your mobile number",
                        getString(R.string.ok),
                        positiveButtonFunction = null
                    )
                }

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("Exception - ", "" + e.message)
                    if (dialog != null) {
                        dialog!!.dismiss()
                    }
                    showMessageDialog(
                        e.message!!, getString(R.string.ok), positiveButtonFunction = null
                    )
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneAuthentication(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseAuth", "signInWithCredential:success")
                    val user = task.result?.user
                    showMessageDialog(
                        "You have successfully verified your mobile number",
                        getString(R.string.ok),
                        positiveButtonFunction = null
                    )
                    binding.llUserDetailsForm.visibility = View.VISIBLE
                } else {
                    Log.d("FirebaseAuth", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        showMessageDialog(
                            (task.exception as FirebaseAuthInvalidCredentialsException).message!!.toString(),
                            getString(R.string.ok),
                            positiveButtonFunction = null
                        )
                    }
                }
            }
    }

    override fun onClick(view: View?) {
        if (view == binding.btnGetOTP) {
            if (binding.etMobileNumber.text.toString().isNotEmpty()) {
                /* var phoneNumber = ""
                 phoneNumber = if (binding.etMobileNumber.text.toString().startsWith("+")) {
                     binding.etMobileNumber.text.toString()
                 } else {
                     binding.tvCountryCode.text.toString() + binding.etMobileNumber.text.toString()
                 }
                 sendVerificationCode(phoneNumber)*/
            } else {
                binding.etMobileNumber.error = "Enter valid Mobile Number"
            }
        } else if (view == binding.btnSignup) {
            tutorSignUp()
        } else if (view == binding.ibBack) {
            finish()
        } else if (view == binding.llAlreadyHaveAccount) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("UserType", "V")
            startActivity(intent)
        }

    }

    private fun tutorSignUp() {
        val mobile = binding.etMobileNumber.text.toString()
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        if (!checkSignUpValidation(
                name, email, password, confirmPassword
            )
        ) {
            if (binding.checkbox.isChecked) {
                signUpViewModel.tutorRegistration(name, email, mobile, password)
            } else {
                showMessageDialog("Please select checkbox",
                    "OK",
                    positiveButtonFunction = {})
            }

        }
    }

    private fun checkSignUpValidation(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        var error = false
        if (name.isEmpty()) {
            error = true
            binding.etName.error = "Enter valid name"
        }

        if (email.isEmpty()) {
            error = true
            binding.etEmail.error = "Enter valid email"
        }

        if (password.isEmpty()) {
            error = true
            binding.etPassword.error = "Enter valid password"
        }
        if (confirmPassword.isEmpty()) {
            error = true
            binding.etConfirmPassword.error = "Enter valid password"
        }
        if (password != confirmPassword) {
            error = true
            binding.etConfirmPassword.error = "Password and confirm password should be same"
        }
        return error
    }

    private fun setAgreementText() {
        val builder = SpannableStringBuilder()
        val text1 = SpannableString("By clicking the box, you are confirming our ")
        text1.setSpan(ForegroundColorSpan(Color.BLACK), 0, text1.length, 0)
        builder.append(text1)


        val termsOfUseText = SpannableString("Terms of use")

        val clickableTermsOfUseSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startWebView()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.BLUE

            }
        }
        termsOfUseText.setSpan(
            clickableTermsOfUseSpan, 0, termsOfUseText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        builder.append(termsOfUseText)
        val text4 = SpannableString(" and ")
        text4.setSpan(ForegroundColorSpan(Color.BLACK), 0, text4.length, 0)
        builder.append(text4)

        val privacyPolicyText = SpannableString("Privacy Policy")
        val clickablePrivacyPolicySpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startWebView()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.BLUE
            }
        }
        privacyPolicyText.setSpan(
            clickablePrivacyPolicySpan,
            0,
            privacyPolicyText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        builder.append(privacyPolicyText)


        binding.tvAgreementText.setText(builder, TextView.BufferType.SPANNABLE)
        binding.tvAgreementText.movementMethod = LinkMovementMethod.getInstance()
    }

    fun openExternalBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url.trim()))
        startActivity(browserIntent)
    }

    private fun startWebView() {
        /* val intent = Intent(this, WebViewActivity::class.java)
         intent.putExtra("URL", Constant.privacyPolicy)
         startActivity(intent)*/
    }

    override fun onStarted() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String, type: String) {
        binding.progressBar.visibility = View.GONE
        showMessageDialog(message, getString(R.string.ok), positiveButtonFunction = {
            startActivity(Intent(this, ProfileActivity::class.java))
        })
    }

    override fun onFailure(message: String, type: String) {
        binding.progressBar.visibility = View.GONE
        showMessageDialog(message, getString(R.string.ok), positiveButtonFunction = null)
    }

    override fun onInternetInfo(message: String, type: String) {
        showMessageDialog(message,
            getString(R.string.retry),
            positiveButtonFunction = { tutorSignUp() })
    }
}