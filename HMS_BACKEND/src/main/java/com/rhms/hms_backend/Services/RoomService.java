package com.rhms.hms_backend.Services;


import com.rhms.hms_backend.Models.Room;
import com.rhms.hms_backend.Repositories.RoomRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {


    @Autowired
    private RoomRepo roomRepo;

    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    public Room saveRoom(Room room) {
        return roomRepo.save(room);
    }



    public Iterable<Room> getAllRoom() {
        return (List<Room>) roomRepo.findAll();
    }


    public Room getRoomById(Long id) {
        Optional<Room> roomOptional = roomRepo.findById(id);
        return roomOptional.orElse(null);
    }



    public void deleteRoomById(Long id) {
        roomRepo.deleteById(id);
    }


    public Room updateRoom(Room room) {
        return roomRepo.save(room);
    }



    public List<Object[]> findAvailableRooms() {
        return roomRepo.findAvailableRoom();
    }

    public List<Object[]> findAssignRooms() {

        return roomRepo.findAssignRoom();
    }


    public List<Object[]> findProperty() {

        return roomRepo.findProperty();
    }


    public List<Object[]> getPropertiesByName(String propertyName) {
        return roomRepo.findPropertyByName(propertyName);
    }


    public List<Object[]> getPropertiesById(String id) {
        return roomRepo.getPropertiesById(id);
    }

    public int getRoomCount() {
        return roomRepo.GetRoomCount();
    }

}
