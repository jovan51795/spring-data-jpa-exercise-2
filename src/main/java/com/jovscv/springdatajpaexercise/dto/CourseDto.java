package com.jovscv.springdatajpaexercise.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jovscv.springdatajpaexercise.model.Student;

import javax.persistence.*;

public record CourseDto(
        Long id,
        String courseName,
        String department
) {
}
