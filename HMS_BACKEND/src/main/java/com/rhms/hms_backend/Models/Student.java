package com.rhms.hms_backend.Models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="student")
public class Student {
    @Id
    private Long id;

    String name;
}
