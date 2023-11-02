package com.rhms.hms_backend.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.Table;


@Entity
public class Room {


    @Id
    @GeneratedValue
    private Long roomID;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "room_capacity")
    private int roomCapacity;

    @Column(name = "status")
    private String status;




    public Room(Long roomID, String roomNumber, int roomCapacity, String status) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.roomCapacity = roomCapacity;
        this.status = status;

    }

    public Room() {

    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
