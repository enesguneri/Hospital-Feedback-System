package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.enes.hospitalfeedbacksystem.databinding.FragmentRegisterBinding
import com.enes.hospitalfeedbacksystem.model.RegisterUserDTO
import com.enes.hospitalfeedbacksystem.viewmodel.RegisterViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val fullName = "$firstName $lastName"

            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Şifreler eşleşmiyor!", Toast.LENGTH_SHORT).show()
            } else if (password.length < 8) {
                Toast.makeText(requireContext(), "Şifre en az 8 karakter olmalıdır!", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty() || email.isEmpty() || fullName.isEmpty()) {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
            } else if (!password.any { it.isDigit() } && !password.any { it.isUpperCase() }) {
                Toast.makeText(
                    requireContext(),
                    "Şifre büyük harf ve rakam içermelidir!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Geçerli bir e-posta adresi girin!", Toast.LENGTH_SHORT).show()
            } else {
                val registerUser = RegisterUserDTO(fullName, email, password)
                viewModel.registerUser(registerUser)
            }
        }
        viewModel.registerResult.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Toast.makeText(requireContext(), "Kayıt başarılı!", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        viewModel.registerError.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), "Kayıt başarısız: $error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}