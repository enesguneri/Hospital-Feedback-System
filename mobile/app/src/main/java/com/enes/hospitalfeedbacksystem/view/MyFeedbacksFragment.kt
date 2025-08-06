package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enes.hospitalfeedbacksystem.adapter.FeedbackAdapter
import com.enes.hospitalfeedbacksystem.databinding.BottomSheetFeedbackMenuBinding
import com.enes.hospitalfeedbacksystem.databinding.FragmentMyFeedbacksBinding
import com.enes.hospitalfeedbacksystem.model.FeedbackItem
import com.enes.hospitalfeedbacksystem.viewmodel.DoctorFeedbackViewModel
import com.enes.hospitalfeedbacksystem.viewmodel.DoctorViewModel
import com.enes.hospitalfeedbacksystem.viewmodel.HospitalFeedbackViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class MyFeedbacksFragment : Fragment() {
    private var _binding: FragmentMyFeedbacksBinding? = null
    private val binding get() = _binding!!

    private val hospitalFeedbackViewModel: HospitalFeedbackViewModel by viewModels()
    private val doctorFeedbackViewModel: DoctorFeedbackViewModel by viewModels()
    private val doctorViewModel: DoctorViewModel by viewModels()

    private val feedbackList = arrayListOf<FeedbackItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyFeedbacksBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emptyStateLayout.visibility = View.VISIBLE

        binding.menuButton.setOnClickListener {
            handleMenuClick(view)
        }

        hospitalFeedbackViewModel.getMyFeedbacks(requireContext())
        doctorFeedbackViewModel.getMyFeedbacks(requireContext())
        doctorViewModel.getDoctorList(requireContext())

        hospitalFeedbackViewModel.myFeedbacks.observe(viewLifecycleOwner) { feedbacks ->
            if (feedbacks.isNotEmpty()) {
                feedbacks.forEach {
                    feedbackList.add(FeedbackItem.HospitalFeedback(it))
                }
            }
            doctorFeedbackViewModel.myFeedbacks.observe(viewLifecycleOwner) { feedbacks2 ->
                if (feedbacks2.isNotEmpty()) {
                    feedbacks2.forEach {
                        feedbackList.add(FeedbackItem.DoctorFeedback(it))
                    }
                }
            }
            if (feedbackList.isNotEmpty()){
                binding.emptyStateLayout.visibility = View.GONE
                doctorViewModel.doctorList.observe(viewLifecycleOwner) { doctors ->
                    binding.emptyStateLayout.visibility = View.GONE
                    binding.feedbackRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.feedbackRecyclerView.adapter = FeedbackAdapter(feedbackList, doctors)
                }
            }
        }




    }

    private fun handleMenuClick(view: View) {
        val inflater = LayoutInflater.from(requireContext())
        val menuBinding = BottomSheetFeedbackMenuBinding.inflate(inflater)
        val bottomSheetDialog = BottomSheetDialog(requireContext())

        menuBinding.hospitalFeedbackCard.setOnClickListener {
            val action = MyFeedbacksFragmentDirections.actionMyFeedbacksFragmentToCreateHospitalFeedbackFragment()
            view.findNavController().navigate(action)
            bottomSheetDialog.dismiss()
        }

        menuBinding.doctorFeedbackCard.setOnClickListener {
            val action = MyFeedbacksFragmentDirections.actionMyFeedbacksFragmentToCreateDoctorFeedbackFragment()
            view.findNavController().navigate(action)
            bottomSheetDialog.dismiss()
        }

        menuBinding.cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(menuBinding.root)
        bottomSheetDialog.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}