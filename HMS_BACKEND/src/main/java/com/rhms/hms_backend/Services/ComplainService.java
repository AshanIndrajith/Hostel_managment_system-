package com.rhms.hms_backend.Services;


import com.rhms.hms_backend.Models.Complain;
import com.rhms.hms_backend.Repositories.ComplainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ComplainService {

    @Autowired
    private ComplainRepo complainRepo;



    public Complain SaveComplain(Complain complain){
       return complainRepo.save(complain);

    }

}
