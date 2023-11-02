package com.rhms.hms_backend.Models;

import jakarta.persistence.*;

@Entity
@Table(name="property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    @Column(name = "property_name")
    private String propertyName;

    @Column(name = "property_uniq_id")
    private String propertyUniqId;

    @Column(name = "room_number")
    private String roomNumber;

    public Property(Long propertyId, String propertyName, String propertyUniqId, String roomNumber) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.propertyUniqId = propertyUniqId;
        this.roomNumber = roomNumber;
    }

    public Property() {

    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyUniqId() {
        return propertyUniqId;
    }

    public void setPropertyUniqId(String propertyUniqId) {
        this.propertyUniqId = propertyUniqId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
