package com.enes.hospitalfeedbacksystem.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            handleMenuClick()
        }

    }

    private fun handleMenuClick() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = BottomSheetFeedbackMenuBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        bottomSheetView.hospitalFeedbackCard.setOnClickListener {

        }

        bottomSheetView.doctorFeedbackCard.setOnClickListener {

        }

        bottomSheetView.cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}