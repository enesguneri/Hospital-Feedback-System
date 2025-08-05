package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.enes.hospitalfeedbacksystem.databinding.FragmentCreateDoctorFeedbackBinding
import com.enes.hospitalfeedbacksystem.viewmodel.DoctorFeedbackViewModel
import com.enes.hospitalfeedbacksystem.viewmodel.DoctorViewModel


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

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        doctorViewModel.getDoctorList(requireContext())
        doctorViewModel.doctorList.observe(viewLifecycleOwner) { doctors ->
            if (doctors.isNotEmpty()) {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, doctors.map { it.fullName })
                binding.doctorAutoComplete.setAdapter(adapter)
                binding.doctorAutoComplete.threshold = 3
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
