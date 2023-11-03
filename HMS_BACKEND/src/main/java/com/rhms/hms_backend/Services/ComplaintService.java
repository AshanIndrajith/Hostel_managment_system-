package com.rhms.hms_backend.Services;

import com.rhms.hms_backend.Models.Complaint;
import com.rhms.hms_backend.Repositories.ComplaintRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {
    @Autowired
    ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaints(){
        List<Complaint> complaints = new ArrayList<>();
        complaintRepository.findAll().forEach(complaint1 -> complaints.add(complaint1));
        return complaints;
    }

    public void Save(Complaint complaint){
        complaintRepository.save(complaint);
    }

    public Complaint getById(Long id){
        Optional<Complaint> optionalComplaint = complaintRepository.findById(id);

        if (optionalComplaint.isPresent()) {
            return optionalComplaint.get();
        } else {
            throw new EntityNotFoundException("Complaint with ID " + id + " not found");
        }
    }

    public void updateComplaint(Complaint complaint,Long id){
        complaintRepository.save(complaint);
    }
    public void delete(Long id) {
        complaintRepository.deleteById(id);

    }


}
