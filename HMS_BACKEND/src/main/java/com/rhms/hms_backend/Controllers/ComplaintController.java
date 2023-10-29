package com.rhms.hms_backend.Controllers;
import com.rhms.hms_backend.Models.Complaint;
import com.rhms.hms_backend.Services.ComplaintService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {
    @Autowired
    ComplaintService complaintService;

    @GetMapping
    private List<Complaint> getAllComplaints(){
        return complaintService.getAllComplaints();
    }
    @PostMapping("/add")
    private Long SaveComplaint(@RequestBody Complaint complaint){
        complaintService.Save(complaint);
        return complaint.getId();
    }
    @GetMapping("/{complaintId}")
    private Complaint getCompalintById(@PathVariable("complaintId") Long complaintID){
        return complaintService.getById(complaintID);
    }
    @PutMapping("/update/{complaintId}")
    public Long updateComplaint(@PathVariable("complaintId") Long complaintId, @RequestBody Complaint updatedComplaint) {
        Complaint existingComplaint = complaintService.getById(complaintId);

        if (existingComplaint != null) {
            updatedComplaint.setId(complaintId);
            complaintService.Save(updatedComplaint);
            return updatedComplaint.getId();
        } else {
            throw new EntityNotFoundException("Complaint with ID " + complaintId + " not found");
        }
    }

    @DeleteMapping("/delete/{complaintId}")
    private void DeleteComplaint(@PathVariable("complaintId") Long id){
        complaintService.delete(id);
    }
}
