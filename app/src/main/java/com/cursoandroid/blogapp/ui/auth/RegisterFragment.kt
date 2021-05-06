package com.cursoandroid.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cursoandroid.blogapp.R
import com.cursoandroid.blogapp.core.Result
import com.cursoandroid.blogapp.data.remote.auth.AuthDataSource
import com.cursoandroid.blogapp.databinding.FragmentRegisterBinding
import com.cursoandroid.blogapp.domain.auth.AuthRepoImpl
import com.cursoandroid.blogapp.presentation.auth.AuthViewModel
import com.cursoandroid.blogapp.presentation.auth.AuthViewModelFactory


class RegistrerFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        signUp()

    }

    private fun signUp() {

        binding.btnSignup.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPass.text.toString().trim()

            if (validateUserData(
                    password,
                    confirmPassword,
                    username,
                    email
                )
            ) return@setOnClickListener

            createUser(email, password, username)

        }

    }

    private fun createUser(email: String, password: String, username: String) {
        viewModel.signUp(email, password, username)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnSignup.isEnabled = false
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        findNavController().navigate(R.id.action_registrerFragment_to_homeScreenFragment)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSignup.isEnabled = true
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private fun validateUserData(
        password: String,
        confirmPassword: String,
        username: String,
        email: String
    ): Boolean {
        if (password != confirmPassword) {
            binding.editTextConfirmPass.error = "Password doesnt match"
            binding.editTextPassword.error = "Password doesnt match"
            return true
        }

        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return true
        }

        if (email.isEmpty()) {
            binding.editTextEmail.error = "Email is empty"
            return true
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = "Email isnt valid"
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return true
        }

        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPass.error = "Confirm password is empty"
            return true
        }
        return false
    }
}