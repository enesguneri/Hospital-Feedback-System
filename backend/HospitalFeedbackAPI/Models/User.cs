namespace HospitalFeedbackAPI.Models
{
    public class User
    {
        public required int Id { get; set; }
        public required string FullName { get; set; }
        public required string Email { get; set; }
        public required string Password { get; set; }
        public required string Role { get; set; } // e.g., "Admin", "Patient"

        public List<DoctorFeedback>? DoctorFeedbacks { get; set; }
        public List<HospitalFeedback>? HospitalFeedbacks { get; set; }

    }
}
