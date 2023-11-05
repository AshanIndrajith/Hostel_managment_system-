package com.rhms.hms_backend.Services;


import com.rhms.hms_backend.Models.Room_assignment;
import com.rhms.hms_backend.Repositories.RoomAssignmentRepository;
import com.rhms.hms_backend.Repositories.RoomRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomAssignmentService {

    private final RoomAssignmentRepository roomAssignmentRepo;

    @Autowired
    public RoomAssignmentService(RoomAssignmentRepository roomAssignmentRepo) {
        this.roomAssignmentRepo = roomAssignmentRepo;
    }

    public String assignRoomToStudent(String roomId, String userId) {
        try {
            roomAssignmentRepo.insertRoomAssignment(roomId, userId);
            return "Success";
        } catch (DataAccessException e) {
            if (e.getMessage().contains("User is not registered in the system")) {
                return "User is not registered in the system";
            } else if (e.getMessage().contains("User is already assigned to a room")) {
                return "User is already assigned to a room";
            }
            return "Error occurred";
        }
    }



    @Transactional
    public void deleteRoomAssignmentByUserId(String userId) {
        roomAssignmentRepo.deleteByUserId(userId);
    }


    @Transactional
    public List<Room_assignment> callCreateRoomAssignmentView(String roomNumber) {
        return roomAssignmentRepo.callCreateRoomAssignmentView(roomNumber);
    }


    public List<Room_assignment> findRoomAssignmentsByRoomNumber(String roomNumber) {
        return roomAssignmentRepo.findRoomAssignmentsByRoomNumber(roomNumber);
    }

}
