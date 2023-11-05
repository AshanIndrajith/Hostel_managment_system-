package com.rhms.hms_backend.Repositories;



import com.rhms.hms_backend.Models.Room_assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomAssignmentRepository extends JpaRepository<Room_assignment, Long> {

    @Procedure(procedureName = "AssignRoomToStudent")
    void insertRoomAssignment(@Param("inUserId") String inUserId, @Param("inRoomId") String inRoomId);


    @Modifying
    @Query(value = "DELETE FROM room_assignment WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") String userId);


    @Procedure("create_room_assignment_view")
    List<Room_assignment> callCreateRoomAssignmentView(@Param("roomNumber") String roomNumber);


    @Query(value = "SELECT assignment_id, user_id, room_id, assignment_date FROM room_assignment_view WHERE room_id = ?1", nativeQuery = true)
    List<Room_assignment> findRoomAssignmentsByRoomNumber(String roomNumber);


}
