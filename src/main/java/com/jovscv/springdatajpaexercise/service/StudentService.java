package com.jovscv.springdatajpaexercise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jovscv.springdatajpaexercise.dto.CourseDto;
import com.jovscv.springdatajpaexercise.dto.StudentDto;
import com.jovscv.springdatajpaexercise.model.Course;
import com.jovscv.springdatajpaexercise.model.Student;
import com.jovscv.springdatajpaexercise.repo.CourseRepository;
import com.jovscv.springdatajpaexercise.repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final CourseRepository courseRepository;
    private final ObjectMapper mapper;

    public static final int SUCCESS_STATUS = 1;
    public static final int FAILED_STATUS = 0;
    public static final String NO_DATA_FOUND_MSG = "Sorry, no data was found.";

    public void saveStudent(StudentDto studentDto) throws JsonProcessingException {
        try {
            String serializedStudent = mapper.writeValueAsString(studentDto);
            Student student = mapper.readValue(serializedStudent, Student.class);
            repository.save(student);
        }catch (Exception e) {
            throw  new RuntimeException(e.getMessage());
        }
    }

    public List<StudentDto> retrieveAllStudents() throws JsonProcessingException{
        try {
            String serializedStudents = mapper.writeValueAsString(repository.findAll());
            return Arrays.asList(mapper.readValue(serializedStudents, StudentDto[].class));
        }catch (Exception e) {
            throw  new RuntimeException(e.getMessage());
        }
    }

    public Map<String, Object> retrieveStudent(Long id) throws JsonProcessingException{
        try {

            Optional<Student> student =  repository.findById(id);
            StudentDto studentDto = mapper.readValue(mapper.writeValueAsString(student), StudentDto.class);
            if(student.isEmpty()) {
                return getResponseObject(FAILED_STATUS, NO_DATA_FOUND_MSG);
            }
            return getResponseObject(SUCCESS_STATUS, studentDto);
        }catch (Exception e) {
            throw  new RuntimeException(e.getMessage());
        }
    }

    public Map<String, Object> retrieveCourses(Long id) {
        try {
//            return getResponseObject(SUCCESS_STATUS, courseRepository.findAll().stream()
//                    .filter(course -> course.getStudent().getId().equals(id))
//                    .findFirst().get());

            return getResponseObject(SUCCESS_STATUS, courseRepository.findCourseByStudentId(id).get());

        }catch (Exception e) {
            throw  new RuntimeException(e.getMessage());
        }
    }

    public Map<String, Object> retrieveCourse(Long studentId, Long courseId) {
        try {
            Optional<Student> studentOptional = repository.findById(studentId);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                Optional<Course> courseOptional = student.getCourse().stream()
                        .filter(course -> course.getId().equals(courseId))
                        .findFirst();
                if (courseOptional.isPresent()) {
                    Course course = courseOptional.get();
                    return getResponseObject(SUCCESS_STATUS, course);
                }
            }
            throw new NoSuchElementException("Course not found for the given student ID and course ID");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addCourse(Long studentId, CourseDto courseDto) throws JsonProcessingException{
        try {
            String serializedCourse = mapper.writeValueAsString(courseDto);
            Course course = mapper.readValue(serializedCourse, Course.class);
            course.setStudent(Student.builder().id(studentId).build());
            courseRepository.save(course);
        }catch (Exception e) {
            throw  new RuntimeException(e.getMessage());
        }
    }

    public static <T extends Map<String, T>> Map<String, Object> getResponseObject(int status, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("message", message);
        return result;

    }

    public static <T extends Map<String, T>> Map<String, Object> getResponseObject(int status, Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("data", data);
        return result;
    }
}
