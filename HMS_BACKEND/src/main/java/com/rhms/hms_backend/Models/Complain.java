package com.rhms.hms_backend.Models;


import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "complain")
public class Complain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "room_number")
    private String room_number;

    @Column(name = "description")
    private String description;


    @Column(name = "complaint_date")
    private String complaint_date;


    @Column(name = "complainant")
    private String complainant;

    @Column(name = "property_uniq_id")
    private String property_uniq_id;

    @Column(name = "property_type")
    private String property_type;

    @Column(name = "status")
    private String status;


    @Column(name = "approved_date")
    private String approved_date;


    @Column(name = "image_name")
    private String imageName;



    @Transient
    private MultipartFile imageFile;

    public Complain() {
    }

    public Complain(Long id, String room_number, String description, String complaint_date, String complainant, String property_uniq_id, String property_type, String status, String approved_date, String imageName, MultipartFile imageFile) {
        this.id = id;
        this.room_number = room_number;
        this.description = description;
        this.complaint_date = complaint_date;
        this.complainant = complainant;
        this.property_uniq_id = property_uniq_id;
        this.property_type = property_type;
        this.status = status;
        this.approved_date = approved_date;
        this.imageName = imageName;
        this.imageFile = imageFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComplaint_date() {
        return complaint_date;
    }

    public void setComplaint_date(String complaint_date) {
        this.complaint_date = complaint_date;
    }

    public String getComplainant() {
        return complainant;
    }

    public void setComplainant(String complainant) {
        this.complainant = complainant;
    }

    public String getProperty_uniq_id() {
        return property_uniq_id;
    }

    public void setProperty_uniq_id(String property_uniq_id) {
        this.property_uniq_id = property_uniq_id;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproved_date() {
        return approved_date;
    }

    public void setApproved_date(String admin_level) {
        this.approved_date = admin_level;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
