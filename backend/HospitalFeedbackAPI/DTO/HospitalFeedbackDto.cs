namespace HospitalFeedbackAPI.DTO
{
    public class HospitalFeedbackDto
    {
        public required int Id { get; set; }
        public required string Subject { get; set; }
        public required string Message { get; set; }
        public required DateTime CreatedAt { get; set; }
        public string? Answer { get; set; }
    }
}
