package com.rhms.hms_backend.Services;


import com.rhms.hms_backend.Models.Complain;
import com.rhms.hms_backend.Models.Room_assignment;
import com.rhms.hms_backend.Repositories.ComplainRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ComplainService {

    @Autowired
    private ComplainRepo complainRepo;


    public Iterable<Complain> getAllComplain() {

        return (List<Complain>) complainRepo.findAll();
    }


    public Complain SaveComplain(Complain complain){
       return complainRepo.save(complain);

    }

    public Complain getComplainById(Long id) {
        Optional<Complain> complainOptional = complainRepo.findById(id);
        return complainOptional.orElse(null);
    }

    public void deleteComplainById(Long id) {
        complainRepo.deleteById(id);

    }


    public List<Object[]> findAllComplaint(){
        return complainRepo.findAllComplaint();
    }


    public Complain updateComplain(Complain complain) {
        return complainRepo.save(complain);
    }

    public Iterable<Complain> ApprovedComplain() {
        return (List<Complain>) complainRepo.ApprovedComplaint();
    }

    public Iterable<Complain> ComplaintWardenView() {
        return (List<Complain>) complainRepo.ComplaintWardenView();
    }

    public Iterable<Complain> ComplaintDeanView() {
        return (List<Complain>) complainRepo.ComplaintDeanView();
    }



    public List<Complain> getComplainByuid(String id) {
        return complainRepo.getComplainByuid(id);
    }


    public List<Complain> findcomplain(String uid) {
        return complainRepo.findComplain(uid);
    }

    @Transactional
    public List<Complain> CreateTodayComplaintView(){
        return complainRepo.CreateTodayComplaintView();
    }


    @Transactional
    public List<Complain> CreateMonthlyComplaintView(){
        return complainRepo.CreateMonthlyComplaintView();
    }

    public int getTotalComplains() {
        return complainRepo.GetComplaintCount();
    }
    public int getPendingComplaintCount() {
        return complainRepo.GetPendingComplaintCount();
    }

}
