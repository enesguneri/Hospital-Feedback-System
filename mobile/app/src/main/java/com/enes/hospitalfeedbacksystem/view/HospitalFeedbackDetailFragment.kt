package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.enes.hospitalfeedbacksystem.databinding.FragmentHospitalFeedbackDetailBinding
import com.enes.hospitalfeedbacksystem.viewmodel.HospitalFeedbackViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.getValue


class HospitalFeedbackDetailFragment : Fragment() {
    private var _binding: FragmentHospitalFeedbackDetailBinding? = null
    private val binding get() = _binding!!

    private var feedbackId: Int = 0

    private val hospitalFeedbackViewModel: HospitalFeedbackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            feedbackId = HospitalFeedbackDetailFragmentArgs.fromBundle(it).id
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHospitalFeedbackDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hospitalFeedbackViewModel.getFeedbackById(requireContext(), feedbackId)

        hospitalFeedbackViewModel.result.observe(viewLifecycleOwner) { feedback ->
            if (feedback != null) {
                binding.feedbackMessage.text = feedback.message
                binding.feedbackSubject.text = feedback.subject

                val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
                val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

                val dateTime = LocalDateTime.parse(feedback.createdAt, inputFormatter)
                val formattedDate = dateTime.format(outputFormatter)

                binding.feedbackDate.text = formattedDate

                val answer = feedback.answer
                if (answer != null && answer.isNotEmpty()) {
                    binding.currentAnswerCard.visibility = View.VISIBLE
                    binding.currentAnswerText.text = feedback.answer
                    binding.statusChip.text = "Yanıtlandı"
                } else {
                    binding.replyFormCard.visibility = View.VISIBLE
                    binding.currentAnswerCard.visibility = View.GONE
                    binding.statusChip.text = "Yanıt Bekleniyor"
                }

            }
        }

        hospitalFeedbackViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                Log.e("HospitalFeedbackDetail", "Error: $error")
            }
        }

        hospitalFeedbackViewModel.isAnswerSent.observe(viewLifecycleOwner) { isSent ->
            if (isSent) {
                hospitalFeedbackViewModel.getFeedbackById(requireContext(), feedbackId)
            }
        }

        hospitalFeedbackViewModel.isFeedbackDeleted.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                val action = HospitalFeedbackDetailFragmentDirections.actionHospitalFeedbackDetailToAdminFragment()
                findNavController().navigate(action)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.sendReplyButton.setOnClickListener {
            val answerText = binding.replyEditText.text.toString()
            if (answerText.isNotEmpty()) {
                hospitalFeedbackViewModel.answerFeedback(requireContext(), feedbackId, answerText)
            } else {
                binding.replyEditText.error = "Lütfen bir cevap girin."
            }
        }

        binding.deleteFeedbackButton.setOnClickListener {
            hospitalFeedbackViewModel.deleteFeedback(requireContext(), feedbackId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}