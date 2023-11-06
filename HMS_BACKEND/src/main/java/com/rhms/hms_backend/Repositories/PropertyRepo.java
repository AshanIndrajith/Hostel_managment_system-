package com.rhms.hms_backend.Repositories;

import com.rhms.hms_backend.Models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepo extends JpaRepository<Property, Integer> {



    @Query(value = "SELECT p.property_id,p.room_number, p.property_name, p.property_uniq_id " +
            "FROM room r " +
            "INNER JOIN property p ON r.room_number = p.room_number " +
            "WHERE r.room_number = :roomNumber", nativeQuery = true)
    List<Property> getPropertyByRoomNumber(@Param("roomNumber") String roomNumber);
}
