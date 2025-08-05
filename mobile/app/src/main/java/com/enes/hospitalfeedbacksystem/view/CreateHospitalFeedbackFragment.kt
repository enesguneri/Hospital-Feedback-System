package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.enes.hospitalfeedbacksystem.databinding.FragmentCreateHospitalFeedbackBinding
import com.enes.hospitalfeedbacksystem.model.HospitalFeedbackCreateDTO
import com.enes.hospitalfeedbacksystem.viewmodel.HospitalFeedbackViewModel

class CreateHospitalFeedbackFragment : Fragment() {

    private var _binding: FragmentCreateHospitalFeedbackBinding? = null
    private val binding get() = _binding!!

    private val viewModel : HospitalFeedbackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateHospitalFeedbackBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                findNavController().popBackStack()
            }
        }

        binding.submitButton.setOnClickListener {
            val subject = binding.subjectEditText.text.toString()
            val message = binding.messageEditText.text.toString()

            if (subject.isNotEmpty() && message.isNotEmpty()) {
                val feedback = HospitalFeedbackCreateDTO(subject, message)
                viewModel.submitFeedback(requireContext(), feedback)
            }
            else if (subject.isEmpty()){
                binding.subjectEditText.error = "Konu boş bırakılamaz."
            }
            else if (message.isEmpty()){
                binding.messageEditText.error = "Mesaj boş bırakılamaz."
            }

        }

        viewModel.submitResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Geri bildirim gönderilemedi.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMsg ->
            if (errorMsg.isNotEmpty()) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}