package com.enes.hospitalfeedbacksystem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enes.hospitalfeedbacksystem.databinding.DfRecyclerRowBinding
import com.enes.hospitalfeedbacksystem.databinding.HfRecyclerRowBinding
import com.enes.hospitalfeedbacksystem.model.DoctorDTO
import com.enes.hospitalfeedbacksystem.model.DoctorFeedbackDTO
import com.enes.hospitalfeedbacksystem.model.FeedbackItem
import com.enes.hospitalfeedbacksystem.model.HospitalFeedbackDTO

class FeedbackAdapter(private val feedbackList: List<FeedbackItem>,private val doctorList: List<DoctorDTO>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_DOCTOR = 0
        private const val TYPE_HOSPITAL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (feedbackList[position]) {
            is FeedbackItem.DoctorFeedback -> TYPE_DOCTOR
            is FeedbackItem.HospitalFeedback -> TYPE_HOSPITAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_DOCTOR -> {
                val binding = DfRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DoctorFeedbackViewHolder(binding,doctorList)
            }
            TYPE_HOSPITAL -> {
                val binding = HfRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HospitalFeedbackViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = feedbackList[position]) {
            is FeedbackItem.DoctorFeedback -> (holder as DoctorFeedbackViewHolder).bind(item.data)
            is FeedbackItem.HospitalFeedback -> (holder as HospitalFeedbackViewHolder).bind(item.data)
        }
    }

    override fun getItemCount() = feedbackList.size

    class DoctorFeedbackViewHolder(private val binding: DfRecyclerRowBinding, private val doctorList : List<DoctorDTO>) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DoctorFeedbackDTO) {
            val doctor = doctorList.find { it.id == data.doctorId }
            binding.doctorNameText.text = doctor?.fullName
            binding.commentText.text = data.comment
            binding.dateText.text = data.createdAt
            binding.doctorRatingBar.rating = data.score.toFloat()

        }
    }

    class HospitalFeedbackViewHolder(private val binding: HfRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HospitalFeedbackDTO) {
            binding.subjectText.text = data.subject
            binding.messageText.text = data.message
            if (data.answer == null) {
                binding.statusText.text = "Yanıt Bekleniyor"
            } else {
                binding.answerLayout.visibility = View.VISIBLE
                binding.statusBadge.visibility = View.VISIBLE
                binding.answerText.text = data.answer
                binding.statusText.text = "Yanıtlandı"
            }
            binding.dateText.text = data.createdAt
        }
    }
}