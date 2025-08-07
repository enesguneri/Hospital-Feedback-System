package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.enes.hospitalfeedbacksystem.databinding.FragmentDoctorFeedbackDetailBinding
import com.enes.hospitalfeedbacksystem.model.DoctorDTO
import com.enes.hospitalfeedbacksystem.viewmodel.DoctorFeedbackViewModel
import com.enes.hospitalfeedbacksystem.viewmodel.DoctorViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.getValue


class DoctorFeedbackDetailFragment : Fragment() {
    private var _binding: FragmentDoctorFeedbackDetailBinding? = null
    private val binding get() = _binding!!

    private var feedbackId: Int = 0
    private var doctorId: Int = -1

    private val doctorFeedbackViewModel: DoctorFeedbackViewModel by viewModels()
    private val doctorViewModel: DoctorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            feedbackId = DoctorFeedbackDetailFragmentArgs.fromBundle(it).id
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoctorFeedbackDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doctorFeedbackViewModel.getFeedbackById(requireContext(), feedbackId)

        doctorFeedbackViewModel.submitResult.observe(viewLifecycleOwner) { feedback ->
            if (feedback != null) {
                doctorId = feedback.doctorId
                binding.feedbackComment.text = feedback.comment
                binding.ratingScore.text = feedback.score.toString()

                val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
                val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

                val dateTime = LocalDateTime.parse(feedback.createdAt, inputFormatter)
                val formattedDate = dateTime.format(outputFormatter)

                binding.feedbackDate.text = formattedDate

                doctorViewModel.getDoctorById(requireContext(), doctorId)
            }
        }


        doctorViewModel.doctor.observe(viewLifecycleOwner) { doctor ->
            if (doctor != null) {
                binding.doctorName.text = "${doctor.title} Dr. ${doctor.fullName} - ${doctor.department}"
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.deleteFeedbackButton.setOnClickListener {
            doctorFeedbackViewModel.deleteFeedbackById(requireContext(), feedbackId)
        }

        doctorFeedbackViewModel.isDeleted.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                findNavController().popBackStack()
            }
        }

        doctorFeedbackViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Log.e("DoctorFeedbackDetail", "$error")
            }
        }

        doctorViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Log.e("DoctorFeedbackDetail", "$error")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}