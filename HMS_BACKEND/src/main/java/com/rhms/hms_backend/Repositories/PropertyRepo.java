package com.rhms.hms_backend.Repositories;

import com.rhms.hms_backend.Models.Property;
import com.rhms.hms_backend.Models.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PropertyRepo extends JpaRepository<Property,Long> {



                @Query("SELECT p FROM Property p WHERE p.propertyUniqId = :propertyUniqId")
                List<Property> findByPropertyUniqId(@Param("propertyUniqId") String propertyUniqId);




}
