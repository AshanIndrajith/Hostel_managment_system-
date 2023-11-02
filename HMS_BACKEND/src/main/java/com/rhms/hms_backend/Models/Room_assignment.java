package com.rhms.hms_backend.Models;


import jakarta.persistence.*;

@Entity
@Table(name="room_assignment")
public class Room_assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "assignment_date")
    private String assignmentDate;

    public Room_assignment() {
    }

    public Room_assignment(Long assignmentId, String userId, String roomId, String assignmentDate) {
        this.assignmentId = assignmentId;
        this.userId = userId;
        this.roomId = roomId;
        this.assignmentDate = assignmentDate;
    }


    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }


}
