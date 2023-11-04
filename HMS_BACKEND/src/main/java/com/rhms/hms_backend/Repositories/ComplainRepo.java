package com.rhms.hms_backend.Repositories;

import com.rhms.hms_backend.Models.Complain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComplainRepo extends CrudRepository<Complain, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM daily_complaint_count")
    List<Object[]> findAllComplaint();


}
