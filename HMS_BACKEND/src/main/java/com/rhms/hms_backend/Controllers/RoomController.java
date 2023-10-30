package com.rhms.hms_backend.Controllers;

import com.rhms.hms_backend.Models.Room;
import com.rhms.hms_backend.Repositories.RoomRepo;
import com.rhms.hms_backend.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api/room/")
public class RoomController {

    @Autowired
    private RoomService roomService;


    @PostMapping("/save")
    public ResponseEntity<String> saveRoom(@RequestBody Room room) {
        try {
            // Save customer to the database
            roomService.saveRoom(room);

            return ResponseEntity.ok("room saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while saving room: " + e.getMessage());
        }
    }


    @GetMapping("/view")
    @ResponseBody
    public ResponseEntity<List<Room>> listRoom() {
        Iterable<Room> RoomList = roomService.getAllRoom();
        return ResponseEntity.ok((List<Room>) RoomList);
    }


    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }



     @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoomById(id);
            return ResponseEntity.ok("room deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting room: " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable("id") Long id, @RequestBody Room updateRoom) {
        Room existingRoom = roomService.getRoomById(id);
        if (existingRoom != null) {
            existingRoom.setRoomNumber(updateRoom.getRoomNumber());
            existingRoom.setRoomCapacity(updateRoom.getRoomCapacity());
            existingRoom.setStatus(updateRoom.getStatus());
            existingRoom.setOther(updateRoom.getOther());


            Room updatedObj = roomService.updateRoom(existingRoom);
            return ResponseEntity.ok(updatedObj);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/AvailableRoom")
    public ResponseEntity<?> getAvailableRoom() {
        try {
            List<Object[]> availableRooms = roomService.findAvailableRooms();
            if (availableRooms != null && !availableRooms.isEmpty()) {
                return ResponseEntity.ok(availableRooms); // 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No available rooms found."); // 404 Not Found
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred."); // 500 Internal Server Error
        }
    }






}
