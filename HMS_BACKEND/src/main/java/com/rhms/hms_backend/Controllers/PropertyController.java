package com.rhms.hms_backend.Controllers;


import com.rhms.hms_backend.Models.Property;
import com.rhms.hms_backend.Services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class PropertyController {

    @Autowired
    private  PropertyService propertyService;



    @GetMapping("/pr/all")
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }


    @GetMapping("/get-property")
    public List<Property> getPropertyByRoomNumber(@RequestParam String roomNumber) {
        return propertyService.getPropertyByRoomNumber(roomNumber);
    }


}
