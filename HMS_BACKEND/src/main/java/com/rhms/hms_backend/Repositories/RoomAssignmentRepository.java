package com.rhms.hms_backend.Repositories;



import com.rhms.hms_backend.Models.Room_assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomAssignmentRepository extends JpaRepository<Room_assignment, Long> {

    @Procedure(procedureName = "AssignRoomToStudent")
    void insertRoomAssignment(@Param("inUserId") String inUserId, @Param("inRoomId") String inRoomId);


}
