package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.enes.hospitalfeedbacksystem.R
import com.enes.hospitalfeedbacksystem.databinding.BottomSheetFeedbackMenuBinding
import com.enes.hospitalfeedbacksystem.databinding.FragmentMyFeedbacksBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView


class MyFeedbacksFragment : Fragment() {
    private var _binding: FragmentMyFeedbacksBinding? = null
    private val binding get() = _binding!!

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