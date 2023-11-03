package com.rhms.hms_backend.Models;
import jakarta.persistence.*;


@Entity
@Table(name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private int propertyId;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @Column(name = "property_uniq_id", nullable = false)
    private String propertyUniqueId;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    // Constructors, getters, setters, and other methods go here

    // Default constructor
    public Property() {
    }

    // Constructor with all fields
    public Property(String propertyName, String propertyUniqueId, String roomNumber) {
        this.propertyName = propertyName;
        this.propertyUniqueId = propertyUniqueId;
        this.roomNumber = roomNumber;
    }

    // Getters and setters for all fields

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyUniqueId() {
        return propertyUniqueId;
    }

    public void setPropertyUniqueId(String propertyUniqueId) {
        this.propertyUniqueId = propertyUniqueId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}