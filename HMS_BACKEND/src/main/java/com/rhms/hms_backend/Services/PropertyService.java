package com.rhms.hms_backend.Services;


import com.rhms.hms_backend.Models.Property;
import com.rhms.hms_backend.Repositories.PropertyRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {




    @Autowired
    private PropertyRepo propertyRepo;




    public List<Property> findIdPropertyByPId(String id) {

        return propertyRepo.findByPropertyUniqId(id);
    }


    public List<Property> findAllProperties() {
        return propertyRepo.findAll();
    }


}
