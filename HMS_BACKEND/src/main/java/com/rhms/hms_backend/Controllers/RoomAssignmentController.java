package com.rhms.hms_backend.Controllers;


import com.rhms.hms_backend.Models.RoomAssignment;
import com.rhms.hms_backend.Services.RoomAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/roomAssign")
public class RoomAssignmentController {


    @Autowired
    private RoomAssignmentService roomAssignmentService;


    @PostMapping("/insert")
    public ResponseEntity<String> insertRoomAssignment(@RequestBody RoomAssignment roomAssignment) {
        try {
            // Call the service function to insert the room assignment
            roomAssignmentService.insertRoomAssignment(roomAssignment.getUserId(), roomAssignment.getRoomId());
            return ResponseEntity.ok("Room assignment inserted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }




}
