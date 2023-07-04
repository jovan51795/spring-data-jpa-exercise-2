package com.jovscv.springdatajpaexercise.repo;

import com.jovscv.springdatajpaexercise.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
