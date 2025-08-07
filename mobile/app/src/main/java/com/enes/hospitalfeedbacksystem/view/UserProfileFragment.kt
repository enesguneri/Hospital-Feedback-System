package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.enes.hospitalfeedbacksystem.databinding.FragmentUserProfileBinding
import com.enes.hospitalfeedbacksystem.util.TokenManager
import com.enes.hospitalfeedbacksystem.viewmodel.LoginViewModel
import kotlin.getValue

class UserProfileFragment : Fragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.visibility = View.GONE

        viewModel.getCurrentUserInfo(requireContext())

        viewModel.userInfo.observe(viewLifecycleOwner) { currentUser ->
            if (currentUser != null) {
                var role = currentUser.role
                if (role == "Patient") {
                    role = "Hasta"
                    binding.adminLayout.visibility = View.GONE
                }
                else if (role == "Admin") {
                    role = "Yetkili"
                    binding.adminLayout.visibility = View.VISIBLE
                }
                binding.userRole.text = role
                binding.userEmail.text = currentUser.email
                binding.userFullName.text = currentUser.fullName
                binding.root.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Kullanıcı bilgileri alınamadı.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.adminLayout.setOnClickListener {
            val action = UserProfileFragmentDirections.actionUserProfileFragmentToAdminFragment()
            findNavController().navigate(action)
        }

        binding.logoutLayout.setOnClickListener {
            TokenManager.clearToken(requireContext())
            Toast.makeText(requireContext(), "Çıkış yapıldı.", Toast.LENGTH_SHORT).show()
            val action = UserProfileFragmentDirections.actionUserProfileFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}