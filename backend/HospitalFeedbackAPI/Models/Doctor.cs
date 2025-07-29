namespace HospitalFeedbackAPI.Models
{
    public class Doctor
    {
        public required int Id { get; set; }
        public required string FullName { get; set; }
        public required string Department { get; set; }
        public string? Title { get; set; }
    }
}
