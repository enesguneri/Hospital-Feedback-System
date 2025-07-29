namespace HospitalFeedbackAPI.Models
{
    public class DoctorFeedback
    {
        public required int Id { get; set; }
        
        public required int UserId { get; set; }
        public User? User { get; set; }
        public required int DoctorId { get; set; }
        public Doctor? Doctor { get; set; }
        public required int Score { get; set; }  // 1-10 arası
        public string? Comment { get; set; }
        public DateTime CreatedAt { get; set; }
    }
}
