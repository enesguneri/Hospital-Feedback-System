package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.enes.hospitalfeedbacksystem.databinding.FragmentCreateDoctorFeedbackBinding
import com.enes.hospitalfeedbacksystem.model.DoctorFeedbackDTO
import com.enes.hospitalfeedbacksystem.viewmodel.DoctorFeedbackViewModel
import com.enes.hospitalfeedbacksystem.viewmodel.DoctorViewModel
import java.time.LocalDateTime


class CreateDoctorFeedbackFragment : Fragment() {
    private var _binding: FragmentCreateDoctorFeedbackBinding? = null
    private val binding get() = _binding!!

    private val viewModel : DoctorFeedbackViewModel by viewModels()
    private val doctorViewModel : DoctorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateDoctorFeedbackBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        doctorViewModel.getDoctorList(requireContext())

        doctorViewModel.doctorList.observe(viewLifecycleOwner) { doctors ->
            if (doctors.isNotEmpty()) {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, doctors.map { it.fullName })
                binding.doctorAutoComplete.setAdapter(adapter)
            }
        }

        binding.submitButton.setOnClickListener {
            val rating = binding.doctorRatingBar.rating.toInt()
            if (binding.doctorAutoComplete.text.isEmpty()) {
                binding.doctorAutoComplete.error = "Doktor seçilmelidir."
            } else if (rating == 0) {
                Toast.makeText(requireContext(), "Lütfen bir puan verin.", Toast.LENGTH_SHORT).show()
            } else {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.submitButton.isEnabled = false
                val selectedDoctorName = binding.doctorAutoComplete.text.toString()
                val comment = binding.commentEditText.text.toString()

                val selectedDoctor =
                    doctorViewModel.doctorList.value?.find { it.fullName == selectedDoctorName }

                if (selectedDoctor != null) {
                    val feedback =
                        DoctorFeedbackDTO(selectedDoctor.id, rating, comment, LocalDateTime.now().toString())
                    viewModel.submitFeedback(requireContext(), feedback)
                }
            }
        }

        viewModel.submitResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                findNavController().popBackStack()
                Toast.makeText(requireContext(), "Geri bildirim gönderildi.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(requireContext(), "Geri bildirim gönderilemedi.", Toast.LENGTH_SHORT).show()
                Log.e("CreateDoctorFeedbackFragment", "Error: $error")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
