package com.rhms.hms_backend.Repositories;

import com.rhms.hms_backend.Models.Complaint;
import org.springframework.data.repository.CrudRepository;

public interface ComplaintRepository extends CrudRepository<Complaint, Long> {
}
