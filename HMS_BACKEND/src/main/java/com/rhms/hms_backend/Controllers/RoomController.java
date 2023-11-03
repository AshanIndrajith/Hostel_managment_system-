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




    @GetMapping("/AssignRoom")
    public ResponseEntity<?> getAssignRoom() {
        try {
            List<Object[]> assignRoom = roomService.findAssignRooms();
            if (assignRoom != null && !assignRoom.isEmpty()) {
                return ResponseEntity.ok(assignRoom); // 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No assign rooms found."); // 404 Not Found
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred."); // 500 Internal Server Error
        }
    }



    @GetMapping("/pr")
    public ResponseEntity<?> getProperty() {
        try {
            List<Object[]> property = roomService.findProperty();
            if (property != null && !property.isEmpty()) {
                return ResponseEntity.ok(property); // 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No assign property found."); // 404 Not Found
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred."); // 500 Internal Server Error
        }
    }


    @GetMapping("/properties")
    public ResponseEntity<List<Object[]>> getPropertiesByName(@RequestParam String propertyName) {
        List<Object[]> properties = roomService.getPropertiesByName(propertyName);
        if (properties != null && !properties.isEmpty()) {
            return ResponseEntity.ok(properties);
        } else {
            // You can customize the response for empty results, for example, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findById")
    public ResponseEntity<List<Object[]>> getPropertiesById(@RequestParam String id) {
        List<Object[]> properties = roomService.getPropertiesById(id);
        if (properties != null && !properties.isEmpty()) {
            return ResponseEntity.ok(properties);
        } else {
            // You can customize the response for empty results, for example, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }





}
