package com.rhms.hms_backend.Services;


import com.rhms.hms_backend.Models.Property;
import com.rhms.hms_backend.Repositories.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {



    @Autowired
    private  PropertyRepo propertyRepo;

    public PropertyService(PropertyRepo propertyRepo) {
        this.propertyRepo = propertyRepo;
    }

    public List<Property> getAllProperties() {
        return propertyRepo.findAll();
    }

    public List<Property> getPropertyByRoomNumber(String roomNumber) {
        return propertyRepo.getPropertyByRoomNumber(roomNumber);
    }
}
