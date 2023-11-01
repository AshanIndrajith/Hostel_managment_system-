package com.rhms.hms_backend.Services;


import com.rhms.hms_backend.Repositories.RoomAssignmentRepository;
import com.rhms.hms_backend.Repositories.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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

}
