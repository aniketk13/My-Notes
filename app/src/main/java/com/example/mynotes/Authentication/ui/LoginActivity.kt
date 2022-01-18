package com.example.mynotes.Authentication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import com.example.mynotes.Authentication.BiometricAuthListener
import com.example.mynotes.MainActivity
import com.example.mynotes.databinding.ActivityLoginBinding
import com.example.mynotes.Authentication.util.BiometricUtil

class LoginActivity : AppCompatActivity(),
    BiometricAuthListener {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showBiometricLoginOption()
    }

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        navigateToMainActivity()
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
        Toast.makeText(this, "Biometric login failed. Error: $errorMessage", Toast.LENGTH_SHORT)
            .show()
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showBiometricLoginOption() {
            BiometricUtil.showBiometricPrompt(
                activity = this,
                listener = this,
                allowDeviceCredential = true
            )
    }

}