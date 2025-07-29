namespace HospitalFeedbackAPI.DTO
{
    public class RegisterUserDto
    {
        public required string FullName { get; set; }
        public required string Email { get; set; }
        public required string Password { get; set; }
    }
}
