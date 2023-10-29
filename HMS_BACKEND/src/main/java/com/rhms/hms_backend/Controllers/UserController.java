package com.rhms.hms_backend.Controllers;

import com.rhms.hms_backend.Models.Users;
import com.rhms.hms_backend.Repositories.UserRepo;
import com.rhms.hms_backend.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminWelcome(){
        return ResponseEntity.ok("Admin Board");
    }

    @GetMapping("/allUsers")
    public ResponseEntity<Object> getAllUsers() {
        List<Users> users = userService.getAllUsers();

        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            String errorMessage = "No users found.";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/students")
    public List<Object[]> getStudentsData() {
        return userService.findStudentsData();
    }


    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<Users> getStudentById(@PathVariable Long id) {
        Users users = userService.getStudentById(id);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long id, @RequestBody Users updateStudent) {
        Users existingStudent = userService.getStudentById(id);
        if (existingStudent != null) {

            existingStudent.setFname(updateStudent.getFname());
            existingStudent.setLname(updateStudent.getLname());
            existingStudent.setEmail(updateStudent.getEmail());
            existingStudent.setUser_index(updateStudent.getUser_index());


            Users updatedStudentObj = userService.updateStudent(existingStudent);
            return ResponseEntity.ok(updatedStudentObj);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/Wardens")
    public List<Object[]> getWardenData() {
        return userService.findWardenData();
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User is  deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting User: " + e.getMessage());
        }
    }



}
