package com.rhms.hms_backend.Repositories;

import com.rhms.hms_backend.Models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepo extends JpaRepository<Property, Integer> {
}
