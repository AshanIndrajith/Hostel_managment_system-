package com.rhms.hms_backend.Controllers;


import com.rhms.hms_backend.Models.Property;
import com.rhms.hms_backend.Services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

//    @GetMapping("/getProperties")
//    public ResponseEntity<List<Object[]>> getPropertiesByUniqID(@RequestParam("propertyUniqId") String propertyUniqId) {
//        List<Object[]> properties = propertyService.getPropertiesByUniqID(propertyUniqId);
//        return ResponseEntity.ok(properties);
//    }


    @GetMapping("/getProperty/{id}")
    @ResponseBody
    public ResponseEntity<List<Property>> findIdPropertyByPId(@PathVariable String id) {

        List<Property> properties = propertyService.findIdPropertyByPId(id);

        if (properties.isEmpty()) {
            // Error message: No vehicles found
            System.out.println("No properties found " + id);
            return ResponseEntity.notFound().build();
        }

        // Success message: Vehicles found
        System.out.println("properties found " + id);
        return ResponseEntity.ok(properties);
    }


    @GetMapping("/view")
    public ResponseEntity<List<Property>> viewAllProperties() {
        List<Property> properties = propertyService.findAllProperties();
        if (!properties.isEmpty()) {
            return ResponseEntity.ok(properties);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
