package com.jovscv.springdatajpaexercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jovscv.springdatajpaexercise.dto.CourseDto;
import com.jovscv.springdatajpaexercise.dto.StudentDto;
import com.jovscv.springdatajpaexercise.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exercise-2")
@RequiredArgsConstructor
public class MainController {

    private final StudentService service;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody StudentDto studentDto) throws JsonProcessingException {
        service.saveStudent(studentDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> retrieveAllStudents() throws JsonProcessingException {
        return ResponseEntity.ok(service.retrieveAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> retrieveStudent(@PathVariable("id") Long id) throws JsonProcessingException {
        return ResponseEntity.ok(service.retrieveStudent(id));
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Map<String, Object>> retrieveCourses(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.retrieveCourses(id));
    }


    @GetMapping("/course")
    public ResponseEntity<Map<String, Object>> retrieveCourse(@RequestParam("student_id") Long studentId, @RequestParam("course_id") Long courseId) {
        return ResponseEntity.ok(service.retrieveCourse(studentId, courseId));
    }

    @PostMapping("/course/add/{student_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCourse(@PathVariable("student_id") Long id,@RequestBody CourseDto courseDto) throws JsonProcessingException {
        service.addCourse(id,courseDto);
    }

    @GetMapping("/sample")
    public String sample() {
        return "jovanie";
    }

}
