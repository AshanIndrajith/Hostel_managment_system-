package com.rhms.hms_backend.Services;

import com.rhms.hms_backend.Repositories.RoomAssignmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomAssignmentService {


    @Autowired
    private RoomAssignmentRepo roomAssignmentRepo;

    public void insertRoomAssignment(String userId, String roomId) {
        roomAssignmentRepo.insertRoomAssignment(userId, roomId);
    }
}
