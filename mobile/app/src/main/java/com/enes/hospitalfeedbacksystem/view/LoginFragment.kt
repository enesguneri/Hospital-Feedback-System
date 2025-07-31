package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.enes.hospitalfeedbacksystem.databinding.FragmentLoginBinding
import com.enes.hospitalfeedbacksystem.service.APIClient
import com.enes.hospitalfeedbacksystem.util.TokenManager
import com.enes.hospitalfeedbacksystem.viewmodel.LoginViewModel
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password)
            viewModel.loginResult.observe(viewLifecycleOwner, Observer { response ->
                TokenManager.saveToken(requireContext(), response.token)
            })

            viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
                Toast.makeText(requireContext(), "Hata: $errorMsg", Toast.LENGTH_LONG).show()
            })

            viewLifecycleOwner.lifecycleScope.launch {
                getCurrentUserInfo()
            }
        }




    }

    suspend fun getCurrentUserInfo(){
            if (TokenManager.getToken(requireContext()) != null) {
                val user = APIClient.getAuthApiService(requireContext()).getCurrentUser()
                Toast.makeText(
                    requireContext(),
                    "Giriş Başarılı. Hoş geldin ${user.fullName}",
                    Toast.LENGTH_LONG
                ).show()
            }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}