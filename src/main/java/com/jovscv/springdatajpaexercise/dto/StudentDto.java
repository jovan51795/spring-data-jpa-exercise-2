package com.jovscv.springdatajpaexercise.dto;

import com.jovscv.springdatajpaexercise.model.Course;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public record StudentDto(
        Long id,
        String firstName,
        String lastName,
        String address,
        int age,
        List<CourseDto> course
) {
}
