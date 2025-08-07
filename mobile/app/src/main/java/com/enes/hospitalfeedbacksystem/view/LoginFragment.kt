package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.enes.hospitalfeedbacksystem.databinding.FragmentLoginBinding
import com.enes.hospitalfeedbacksystem.util.TokenManager
import com.enes.hospitalfeedbacksystem.viewmodel.LoginViewModel
import androidx.navigation.findNavController


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()


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

        if (TokenManager.getToken(requireContext()) != null){
            viewModel.getCurrentUserInfo(requireContext())

            viewModel.userInfo.observe(viewLifecycleOwner, Observer { userInfo ->
                if (userInfo.role == "Admin") {
                    val action = LoginFragmentDirections.actionLoginFragmentToAdminFragment()
                    view.findNavController().navigate(action)
                } else if (userInfo.role == "Patient") {
                    val action = LoginFragmentDirections.actionLoginFragmentToMyFeedbacksFragment()
                    view.findNavController().navigate(action)
                }
            })
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password)
        }

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { response ->
            if (response.token.isNotBlank()) {
                TokenManager.saveToken(requireContext(), response.token)
                viewModel.getCurrentUserInfo(requireContext())

                viewModel.userInfo.observe(viewLifecycleOwner, Observer { userInfo ->
                    if (userInfo.role == "Admin") {
                        val action = LoginFragmentDirections.actionLoginFragmentToAdminFragment()
                        view.findNavController().navigate(action)
                    } else if (userInfo.role == "Patient") {
                        val action = LoginFragmentDirections.actionLoginFragmentToMyFeedbacksFragment()
                        view.findNavController().navigate(action)
                    }
                })
            }
        })


        viewModel.error.observe(viewLifecycleOwner, Observer { errorMsg ->
            Toast.makeText(requireContext(), "Şifre hatalı veya kullanıcı bulunamadı.", Toast.LENGTH_LONG).show()
            Log.e("LoginFragment", errorMsg)
        })


        binding.forgotPasswordText.setOnClickListener {
            //update düzenlenecek.
        }

        binding.registerText.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            it.findNavController().navigate(action)
        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}