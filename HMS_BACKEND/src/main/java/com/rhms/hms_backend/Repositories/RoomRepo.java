package com.rhms.hms_backend.Repositories;

import com.rhms.hms_backend.Models.Room;
import com.rhms.hms_backend.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepo  extends JpaRepository<Room,Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM available_rooms")
    List<Object[]> findAvailableRoom();


    @Query(nativeQuery = true, value = "SELECT * FROM roomassignmentview")
    List<Object[]> findAssignRoom();


    @Query(nativeQuery = true, value = "SELECT * FROM property WHERE property_uniq_id = 'ch4108'")
    List<Object[]> findProperty();


    @Query(nativeQuery = true, value = "SELECT * FROM property WHERE property_uniq_id = :propertyName")
    List<Object[]> findPropertyByName(@Param("propertyName") String propertyName);




}
