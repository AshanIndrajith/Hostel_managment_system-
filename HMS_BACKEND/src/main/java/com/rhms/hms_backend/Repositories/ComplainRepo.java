package com.rhms.hms_backend.Repositories;

import com.rhms.hms_backend.Models.Complain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComplainRepo extends CrudRepository<Complain, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM daily_complaint_count")
    List<Object[]> findAllComplaint();


    @Query(nativeQuery = true, value = "SELECT * FROM ApprovedComplaints")
    List<Complain> ApprovedComplaint();

    @Query(nativeQuery = true, value = "SELECT * FROM warden_complain_view")
    List<Complain> ComplaintWardenView();

    @Query(nativeQuery = true, value = "SELECT * FROM dean_complain_view")
    List<Complain> ComplaintDeanView();


}
