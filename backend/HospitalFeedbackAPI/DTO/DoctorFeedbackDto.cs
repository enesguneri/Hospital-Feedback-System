using HospitalFeedbackAPI.Models;

namespace HospitalFeedbackAPI.DTO
{
    public class DoctorFeedbackDto
    {
        public required int DoctorId { get; set; }
        public required int Score { get; set; }
        public string? Comment { get; set; }
        public DateTime CreatedAt { get; set; }
    }
}
