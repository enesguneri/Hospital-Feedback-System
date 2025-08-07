package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enes.hospitalfeedbacksystem.R
import com.enes.hospitalfeedbacksystem.adapter.FeedbackAdapter
import com.enes.hospitalfeedbacksystem.databinding.FragmentAdminBinding
import com.enes.hospitalfeedbacksystem.model.DoctorDTO
import com.enes.hospitalfeedbacksystem.model.DoctorFeedbackDTO
import com.enes.hospitalfeedbacksystem.model.FeedbackItem
import com.enes.hospitalfeedbacksystem.model.HospitalFeedbackDTO
import com.enes.hospitalfeedbacksystem.viewmodel.DoctorFeedbackViewModel
import com.enes.hospitalfeedbacksystem.viewmodel.DoctorViewModel
import com.enes.hospitalfeedbacksystem.viewmodel.HospitalFeedbackViewModel
import kotlin.getValue


class AdminFragment : Fragment() {
    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!

    private val hospitalFeedbackViewModel: HospitalFeedbackViewModel by viewModels()
    private val doctorFeedbackViewModel: DoctorFeedbackViewModel by viewModels()
    private val doctorViewModel: DoctorViewModel by viewModels()


    private val feedbackList = arrayListOf<FeedbackItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAllData()

        binding.menuButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), binding.menuButton)
            popupMenu.menuInflater.inflate(R.menu.admin_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_profile -> {
                        val action = AdminFragmentDirections.actionAdminFragmentToUserProfileFragment()
                        view.findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun observeAllData() {
        val feedbackList = mutableListOf<FeedbackItem>()
        var hospitalFeedbacksLoaded = false
        var doctorFeedbacksLoaded = false
        var doctorListLoaded = false

        var hospitalFeedbacks: List<HospitalFeedbackDTO>? = null
        var doctorFeedbacks: List<DoctorFeedbackDTO>? = null
        var doctorList: List<DoctorDTO>? = null

        fun tryDisplayData() {
            if (hospitalFeedbacksLoaded && doctorFeedbacksLoaded && doctorListLoaded) {
                feedbackList.clear()

                hospitalFeedbacks?.forEach {
                    feedbackList.add(FeedbackItem.HospitalFeedback(it))
                }

                doctorFeedbacks?.forEach {
                    feedbackList.add(FeedbackItem.DoctorFeedback(it))
                }

                if (feedbackList.isEmpty()) {
                    binding.emptyStateLayout.visibility = View.VISIBLE
                } else {
                    binding.emptyStateLayout.visibility = View.GONE
                    binding.feedbackRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.feedbackRecyclerView.adapter = FeedbackAdapter(feedbackList, doctorList!!, "Admin")
                }
            }
        }

        hospitalFeedbackViewModel.getAllFeedbacks(requireContext())
        doctorFeedbackViewModel.getAllFeedbacks(requireContext())
        doctorViewModel.getDoctorList(requireContext())

        hospitalFeedbackViewModel.allFeedbacks.observe(viewLifecycleOwner) { feedbacks ->
            hospitalFeedbacks = feedbacks
            hospitalFeedbacksLoaded = true
            tryDisplayData()
        }

        doctorFeedbackViewModel.allFeedbacks.observe(viewLifecycleOwner) { feedbacks ->
            doctorFeedbacks = feedbacks
            doctorFeedbacksLoaded = true
            tryDisplayData()
        }

        doctorViewModel.doctorList.observe(viewLifecycleOwner) { doctors ->
            doctorList = doctors
            doctorListLoaded = true
            tryDisplayData()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}