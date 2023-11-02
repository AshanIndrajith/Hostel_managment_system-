package com.rhms.hms_backend.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Complaint {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private int roomNo;
    @Column(nullable = false)
    private String complainant;
    @Column(nullable = false)
    private String property;

    @Column(nullable = false)
    private String defectType;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String Status;
    @Column(nullable = false)
    private String adminLevel;
    @Column(nullable = false)
    private LocalDate complainedDate;
    @Column(nullable = true)
    private String feedback;

    @Column(nullable = false)
    private String img;

    public Complaint() {
    }

    public Complaint(Long id, int roomNo, String complainant, String property, String defectType, String description, String status, String adminLevel, LocalDate complainedDate, String feedback, String img) {
        this.id = id;
        this.roomNo = roomNo;
        this.complainant = complainant;
        this.property = property;
        this.defectType = defectType;
        this.description = description;
        Status = status;
        this.adminLevel = adminLevel;
        this.complainedDate = complainedDate;
        this.feedback = feedback;
        this.img = img;
    }

    public Complaint(int roomNo, String complainant, String property, String defectType, String description, String status, String adminLevel, LocalDate complainedDate, String feedback, String img) {
        this.roomNo = roomNo;
        this.complainant = complainant;
        this.property = property;
        this.defectType = defectType;
        this.description = description;
        Status = status;
        this.adminLevel = adminLevel;
        this.complainedDate = complainedDate;
        this.feedback = feedback;
        this.img = img;
    }

    public Complaint(Long id, int roomNo, String complainant, String property, String defectType, String description, String status, String adminLevel, LocalDate complainedDate, String feedback) {
        this.id = id;
        this.roomNo = roomNo;
        this.complainant = complainant;
        this.property = property;
        this.defectType = defectType;
        this.description = description;
        Status = status;
        this.adminLevel = adminLevel;
        this.complainedDate = complainedDate;
        this.feedback = feedback;
    }

    public Complaint(int roomNo, String complainant, String property, String defectType, String description, String status, String adminLevel, LocalDate complainedDate, String feedback) {
        this.roomNo = roomNo;
        this.complainant = complainant;
        this.property = property;
        this.defectType = defectType;
        this.description = description;
        Status = status;
        this.adminLevel = adminLevel;
        this.complainedDate = complainedDate;
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getComplainant() {
        return complainant;
    }

    public void setComplainant(String complainant) {
        this.complainant = complainant;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDefectType() {
        return defectType;
    }

    public void setDefectType(String defectType) {
        this.defectType = defectType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    public LocalDate getComplainedDate() {
        return complainedDate;
    }

    public void setComplainedDate(LocalDate complainedDate) {
        this.complainedDate = complainedDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String path) {
        this.img = path;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", roomNo=" + roomNo +
                ", complainant='" + complainant + '\'' +
                ", property='" + property + '\'' +
                ", defectType='" + defectType + '\'' +
                ", description='" + description + '\'' +
                ", Status='" + Status + '\'' +
                ", adminLevel='" + adminLevel + '\'' +
                ", complainedDate=" + complainedDate +
                ", feedback='" + feedback + '\'' +
                ", path='" + img + '\'' +
                '}';
    }
}
