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
        Users student = Users.getCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }


}
