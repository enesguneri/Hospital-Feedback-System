namespace HospitalFeedbackAPI.Models
{
    public class HospitalFeedback
    {
        public required int Id { get; set; }

        public required int UserId { get; set; }
        public User? User { get; set; }

        public required string Subject { get; set; }
        public required string Message { get; set; }
        public string? Answer { get; set; }
        public required DateTime CreatedAt { get; set; }
    }
}
