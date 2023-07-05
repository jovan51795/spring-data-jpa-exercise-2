package com.jovscv.springdatajpaexercise.repo;

import com.jovscv.springdatajpaexercise.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<List<Course>> findCourseByStudentId(Long studentId);
}
