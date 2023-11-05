package com.rhms.hms_backend.Services;

import com.rhms.hms_backend.Models.Users;
import com.rhms.hms_backend.Repositories.UserRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;


    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }




//Delete user
    public HttpStatus deleteUser(Long id) {
        try {
            Users users = userRepo.findById(id).orElse(null);
            if (users == null) {
                return HttpStatus.NOT_FOUND;
            }


            userRepo.delete(users);
            return HttpStatus.NO_CONTENT;

        } catch (EmptyResultDataAccessException e) {
            return HttpStatus.NOT_FOUND;
        } catch (DataIntegrityViolationException e) {
            return HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public Integer getUserCount(){
        return Math.toIntExact(userRepo.count());
    }



    public List<Object[]> findStudentsData() {
        return userRepo.findStudentsData();
    }

    public List<Object[]> findWardenData(){
        return userRepo.findAllWarden();
    }


    public List<Object[]> findDeanData(){
        return userRepo.findDeanData();
    }


    public List<Object[]> findSwardenData(){
        return userRepo.findSwardenData();
    }


    public User getUserById(Long id) {
        return (User) userRepo.findById(id).orElse(null);
    }



    public Users getStudentById(Long id) {
        Optional<Users> customerOptional = userRepo.findById(id);
        return customerOptional.orElse(null);
    }


    public Users updateStudent(Users users) {
        return userRepo.save(users);
    }


    public void deleteUserById(Long id) {

        userRepo.deleteById(id);

    }



    public int getTotalRegisteredStudents() {
        return userRepo.getTotalRegisteredStudents();
    }





//    public User updateProfile(Integer id, User profile) {
//        User existingUser = userRepo.findById(id).orElse(null);
//        if (existingUser == null) {
//            return null;
//        }
//        existingUser.setFirstname(profile.getFirstname());
//        existingUser.setLastname(profile.getLastname());
//        existingUser.setEmail(profile.getEmail());
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encodedPassword = encoder.encode(profile.getPassword());
//        existingUser.setPassword(encodedPassword);
//
//        return userRepo.save(existingUser);
    }
