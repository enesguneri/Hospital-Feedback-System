using HospitalFeedbackAPI.Data;
using HospitalFeedbackAPI.Models;
using HospitalFeedbackAPI.DTO;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Security.Claims;

namespace HospitalFeedbackAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DoctorFeedbackController(AppDbContext context) : ControllerBase
    {
        private readonly AppDbContext _context = context;

        // GET: api/DoctorFeedback
        [Authorize(Roles = "Admin")]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<DoctorFeedbackDto>>> GetAll()
        {
            var feedbacks = await _context.DoctorFeedbacks.ToListAsync();
            var dtos = feedbacks.Select(f => new DoctorFeedbackDto
            {
                Id = f.Id,
                DoctorId = f.DoctorId,
                Score = f.Score,
                Comment = f.Comment,
                CreatedAt = f.CreatedAt
            }).ToList();
            return Ok(dtos);
        }

        // GET: api/DoctorFeedback/5
        [Authorize(Roles = "Admin")]
        [HttpGet("{id}")]
        public async Task<ActionResult<DoctorFeedbackDto>> GetById(int id)
        {
            var feedback = await _context.DoctorFeedbacks.FindAsync(id);

            if (feedback == null)
                return NotFound();

            var dto = new DoctorFeedbackDto
            {
                Id = feedback.Id,
                DoctorId = feedback.DoctorId,
                Score = feedback.Score,
                Comment = feedback.Comment,
                CreatedAt = feedback.CreatedAt
            };
            return Ok(dto);
        }

        // GET: api/DoctorFeedback/doctor/3
        [Authorize(Roles = "Admin")]
        [HttpGet("doctor/{doctorId}")]
        public async Task<ActionResult<IEnumerable<DoctorFeedbackDto>>> GetByDoctorId(int doctorId)
        {
            var feedbacks = await _context.DoctorFeedbacks
                .Where(f => f.DoctorId == doctorId)
                .ToListAsync();

            var dtos = feedbacks.Select(f => new DoctorFeedbackDto
            {
                Id = f.Id,
                DoctorId = f.DoctorId,
                Score = f.Score,
                Comment = f.Comment,
                CreatedAt = f.CreatedAt
            }).ToList();

            return Ok(dtos);
        }

        // POST: api/DoctorFeedback
        [Authorize(Roles = "Admin,Patient")]
        [HttpPost]
        public async Task<ActionResult<DoctorFeedbackDto>> Create(DoctorFeedbackDto request)
        {
            var email = User.FindFirst(ClaimTypes.Email)?.Value;
            var user = await _context.Users.FirstOrDefaultAsync(u => u.Email == email);
            if (user == null)
                return Unauthorized();

            var feedback = new DoctorFeedback
            {
                Id = 0,
                UserId = user.Id,
                DoctorId = request.DoctorId,
                Score = request.Score,
                Comment = request.Comment,
                CreatedAt = DateTime.UtcNow
            };

            _context.DoctorFeedbacks.Add(feedback);
            await _context.SaveChangesAsync();

            request.Id = feedback.Id;

            return CreatedAtAction(nameof(GetById), new { id = feedback.Id }, request);
        }

        // PUT: api/DoctorFeedback/5
        [Authorize(Roles = "Admin,Patient")]
        [HttpPut("{id}")]
        public async Task<IActionResult> Update(int id, DoctorFeedbackDto request)
        {
            var feedback = await _context.DoctorFeedbacks.FindAsync(id);
            if (feedback == null)
                return NotFound();

            feedback.DoctorId = request.DoctorId;
            feedback.Score = request.Score;
            feedback.Comment = request.Comment;
            feedback.CreatedAt = request.CreatedAt;

            await _context.SaveChangesAsync();

            return NoContent();
        }

        // DELETE: api/DoctorFeedback/5
        [Authorize(Roles = "Admin,Patient")]
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            var feedback = await _context.DoctorFeedbacks.FindAsync(id);
            if (feedback == null)
                return NotFound();

            _context.DoctorFeedbacks.Remove(feedback);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        [Authorize(Roles = "Admin,Patient")]
        [HttpGet("my-feedback")]
        public async Task<IActionResult> GetMyFeedback()
        {
            var email = User.FindFirst(ClaimTypes.Email)?.Value;
            var user = await _context.Users.FirstOrDefaultAsync(u => u.Email == email);

            if (user == null) return Unauthorized();

            var feedbacks = await _context.DoctorFeedbacks
                .Where(f => f.UserId == user.Id)
                .ToListAsync();

            var dtos = feedbacks.Select(f => new DoctorFeedbackDto
            {
                Id = f.Id,
                DoctorId = f.DoctorId,
                Score = f.Score,
                Comment = f.Comment,
                CreatedAt = f.CreatedAt
            }).ToList();

            return Ok(dtos);
        }

    }
}
