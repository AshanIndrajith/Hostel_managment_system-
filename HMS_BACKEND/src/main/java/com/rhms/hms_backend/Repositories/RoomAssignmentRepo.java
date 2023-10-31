package com.rhms.hms_backend.Repositories;

import com.rhms.hms_backend.Models.RoomAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface RoomAssignmentRepo extends JpaRepository<RoomAssignment, Long> {

    @Procedure("InsertRoomAssignment")
    void insertRoomAssignment(@Param("inUserId") String inUserId, @Param("inRoomId") String inRoomId);

}
