using HospitalFeedbackAPI.Models;

namespace HospitalFeedbackAPI.DTO
{
    public class UserDto
    {
        public required int Id { get; set; }
        public required string FullName { get; set; }
        public required string Email { get; set; }
        public required string Role { get; set; }
        public List<DoctorFeedback>? DoctorFeedbacks { get; set; }
        public List<HospitalFeedback>? HospitalFeedbacks { get; set; }
    }
}
