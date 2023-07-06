package com.jovscv.springdatajpaexercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jovscv.springdatajpaexercise.dto.CourseDto;
import com.jovscv.springdatajpaexercise.dto.StudentDto;
import com.jovscv.springdatajpaexercise.model.Course;
import com.jovscv.springdatajpaexercise.model.Student;
import com.jovscv.springdatajpaexercise.repo.CourseRepository;
import com.jovscv.springdatajpaexercise.repo.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StudentService.class})
@ExtendWith(SpringExtension.class)
class StudentServiceTest {
    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;
    @Test
    void testSaveStudent() throws JsonProcessingException {
        Student student = new Student();
        student.setAddress("42 Main St");
        student.setAge(1);
        student.setCourse(new ArrayList<>());
        student.setFirstName("Jane");
        student.setId(1L);
        student.setLastName("Doe");
        when(studentRepository.save(Mockito.<Student>any())).thenReturn(student);

        Student student2 = new Student();
        student2.setAddress("42 Main St");
        student2.setAge(1);
        student2.setCourse(new ArrayList<>());
        student2.setFirstName("Jane");
        student2.setId(1L);
        student2.setLastName("Doe");
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Student>>any())).thenReturn(student2);
        when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
        studentService.saveStudent(new StudentDto(1L, "Jane", "Doe", "42 Main St", 1, new ArrayList<>()));
        verify(studentRepository).save(Mockito.<Student>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<Student>>any());
        verify(objectMapper).writeValueAsString(Mockito.<Object>any());
    }


    @Test
    void testRetrieveAllStudents() throws JsonProcessingException {
        when(studentRepository.findAll()).thenReturn(new ArrayList<>());
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<StudentDto[]>>any()))
                .thenReturn(new StudentDto[]{new StudentDto(1L, "Jane", "Doe", "42 Main St", 1, new ArrayList<>())});
        when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
        assertEquals(1, studentService.retrieveAllStudents().size());
        verify(studentRepository).findAll();
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<StudentDto[]>>any());
        verify(objectMapper).writeValueAsString(Mockito.<Object>any());
    }

    @Test
    void testRetrieveStudent() throws JsonProcessingException {
        Student student = new Student();
        student.setAddress("42 Main St");
        student.setAge(1);
        student.setCourse(new ArrayList<>());
        student.setFirstName("Jane");
        student.setId(1L);
        student.setLastName("Doe");
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<StudentDto>>any()))
                .thenReturn(new StudentDto(1L, "Jane", "Doe", "42 Main St", 1, new ArrayList<>()));
        when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
        assertEquals(2, studentService.retrieveStudent(1L).size());
        verify(studentRepository).findById(Mockito.<Long>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<StudentDto>>any());
        verify(objectMapper).writeValueAsString(Mockito.<Object>any());
    }

    /**
     * Method under test: {@link StudentService#retrieveCourses(Long)}
     */
    @Test
    void testRetrieveCourses() {
        when(courseRepository.findCourseByStudentId(Mockito.<Long>any())).thenReturn(Optional.of(new ArrayList<>()));
        assertEquals(2, studentService.retrieveCourses(1L).size());
        verify(courseRepository).findCourseByStudentId(Mockito.<Long>any());
    }


    @Test
    void testRetrieveCourses2() {
        when(courseRepository.findCourseByStudentId(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.retrieveCourses(1L));
        verify(courseRepository).findCourseByStudentId(Mockito.<Long>any());
    }


    @Test
    void testRetrieveCourses3() {
        when(courseRepository.findCourseByStudentId(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));
        assertThrows(RuntimeException.class, () -> studentService.retrieveCourses(1L));
        verify(courseRepository).findCourseByStudentId(Mockito.<Long>any());
    }

    @Test
    void testRetrieveCourse() {
        Student student = new Student();
        student.setAddress("42 Main St");
        student.setAge(1);
        student.setCourse(new ArrayList<>());
        student.setFirstName("Jane");
        student.setId(1L);
        student.setLastName("Doe");
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(RuntimeException.class, () -> studentService.retrieveCourse(1L, 1L));
        verify(studentRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testRetrieveCourse2() {
        Student student = mock(Student.class);
        when(student.getCourse()).thenReturn(new ArrayList<>());
        doNothing().when(student).setAddress(Mockito.<String>any());
        doNothing().when(student).setAge(anyInt());
        doNothing().when(student).setCourse(Mockito.<List<Course>>any());
        doNothing().when(student).setFirstName(Mockito.<String>any());
        doNothing().when(student).setId(Mockito.<Long>any());
        doNothing().when(student).setLastName(Mockito.<String>any());
        student.setAddress("42 Main St");
        student.setAge(1);
        student.setCourse(new ArrayList<>());
        student.setFirstName("Jane");
        student.setId(1L);
        student.setLastName("Doe");
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(RuntimeException.class, () -> studentService.retrieveCourse(1L, 1L));
        verify(studentRepository).findById(Mockito.<Long>any());
        verify(student).getCourse();
        verify(student).setAddress(Mockito.<String>any());
        verify(student).setAge(anyInt());
        verify(student).setCourse(Mockito.<List<Course>>any());
        verify(student).setFirstName(Mockito.<String>any());
        verify(student).setId(Mockito.<Long>any());
        verify(student).setLastName(Mockito.<String>any());
    }

    @Test
    void testRetrieveCourse3() {
        Student student = new Student();
        student.setAddress("42 Main St");
        student.setAge(1);
        student.setCourse(new ArrayList<>());
        student.setFirstName("Jane");
        student.setId(1L);
        student.setLastName("Doe");

        Course course = new Course();
        course.setCourseName("Course not found for the given student ID and course ID");
        course.setDepartment("Course not found for the given student ID and course ID");
        course.setId(1L);
        course.setStudent(student);

        ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(course);
        Student student2 = mock(Student.class);
        when(student2.getCourse()).thenReturn(courseList);
        doNothing().when(student2).setAddress(Mockito.<String>any());
        doNothing().when(student2).setAge(anyInt());
        doNothing().when(student2).setCourse(Mockito.<List<Course>>any());
        doNothing().when(student2).setFirstName(Mockito.<String>any());
        doNothing().when(student2).setId(Mockito.<Long>any());
        doNothing().when(student2).setLastName(Mockito.<String>any());
        student2.setAddress("42 Main St");
        student2.setAge(1);
        student2.setCourse(new ArrayList<>());
        student2.setFirstName("Jane");
        student2.setId(1L);
        student2.setLastName("Doe");
        Optional<Student> ofResult = Optional.of(student2);
        when(studentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(2, studentService.retrieveCourse(1L, 1L).size());
        verify(studentRepository).findById(Mockito.<Long>any());
        verify(student2).getCourse();
        verify(student2).setAddress(Mockito.<String>any());
        verify(student2).setAge(anyInt());
        verify(student2).setCourse(Mockito.<List<Course>>any());
        verify(student2).setFirstName(Mockito.<String>any());
        verify(student2).setId(Mockito.<Long>any());
        verify(student2).setLastName(Mockito.<String>any());
    }

    @Test
    void testRetrieveCourse4() {
        Student student = new Student();
        student.setAddress("42 Main St");
        student.setAge(1);
        student.setCourse(new ArrayList<>());
        student.setFirstName("Jane");
        student.setId(1L);
        student.setLastName("Doe");

        Course course = new Course();
        course.setCourseName("Course not found for the given student ID and course ID");
        course.setDepartment("Course not found for the given student ID and course ID");
        course.setId(1L);
        course.setStudent(student);

        Student student2 = new Student();
        student2.setAddress("17 High St");
        student2.setAge(5);
        student2.setCourse(new ArrayList<>());
        student2.setFirstName("John");
        student2.setId(2L);
        student2.setLastName("Smith");

        Course course2 = new Course();
        course2.setCourseName("data");
        course2.setDepartment("data");
        course2.setId(2L);
        course2.setStudent(student2);

        ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(course2);
        courseList.add(course);
        Student student3 = mock(Student.class);
        when(student3.getCourse()).thenReturn(courseList);
        doNothing().when(student3).setAddress(Mockito.<String>any());
        doNothing().when(student3).setAge(anyInt());
        doNothing().when(student3).setCourse(Mockito.<List<Course>>any());
        doNothing().when(student3).setFirstName(Mockito.<String>any());
        doNothing().when(student3).setId(Mockito.<Long>any());
        doNothing().when(student3).setLastName(Mockito.<String>any());
        student3.setAddress("42 Main St");
        student3.setAge(1);
        student3.setCourse(new ArrayList<>());
        student3.setFirstName("Jane");
        student3.setId(1L);
        student3.setLastName("Doe");
        Optional<Student> ofResult = Optional.of(student3);
        when(studentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(2, studentService.retrieveCourse(1L, 1L).size());
        verify(studentRepository).findById(Mockito.<Long>any());
        verify(student3).getCourse();
        verify(student3).setAddress(Mockito.<String>any());
        verify(student3).setAge(anyInt());
        verify(student3).setCourse(Mockito.<List<Course>>any());
        verify(student3).setFirstName(Mockito.<String>any());
        verify(student3).setId(Mockito.<Long>any());
        verify(student3).setLastName(Mockito.<String>any());
    }

    @Test
    void testRetrieveCourse5() {
        when(studentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.retrieveCourse(1L, 1L));
        verify(studentRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link StudentService#addCourse(Long, CourseDto)}
     */
    @Test
    void testAddCourse() throws JsonProcessingException {
        Student student = new Student();
        student.setAddress("42 Main St");
        student.setAge(1);
        student.setCourse(new ArrayList<>());
        student.setFirstName("Jane");
        student.setId(1L);
        student.setLastName("Doe");

        Course course = new Course();
        course.setCourseName("Course Name");
        course.setDepartment("Department");
        course.setId(1L);
        course.setStudent(student);
        when(courseRepository.save(Mockito.<Course>any())).thenReturn(course);

        Student student2 = new Student();
        student2.setAddress("42 Main St");
        student2.setAge(1);
        student2.setCourse(new ArrayList<>());
        student2.setFirstName("Jane");
        student2.setId(1L);
        student2.setLastName("Doe");

        Course course2 = new Course();
        course2.setCourseName("Course Name");
        course2.setDepartment("Department");
        course2.setId(1L);
        course2.setStudent(student2);
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Course>>any())).thenReturn(course2);
        when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
        studentService.addCourse(1L, new CourseDto(1L, "Course Name", "Department"));
        verify(courseRepository).save(Mockito.<Course>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<Course>>any());
        verify(objectMapper).writeValueAsString(Mockito.<Object>any());
    }

    @Test
    void testAddCourse2() throws JsonProcessingException {
        when(courseRepository.save(Mockito.<Course>any())).thenThrow(new RuntimeException("foo"));

        Student student = new Student();
        student.setAddress("42 Main St");
        student.setAge(1);
        student.setCourse(new ArrayList<>());
        student.setFirstName("Jane");
        student.setId(1L);
        student.setLastName("Doe");

        Course course = new Course();
        course.setCourseName("Course Name");
        course.setDepartment("Department");
        course.setId(1L);
        course.setStudent(student);
        when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<Course>>any())).thenReturn(course);
        when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
        assertThrows(RuntimeException.class,
                () -> studentService.addCourse(1L, new CourseDto(1L, "Course Name", "Department")));
        verify(courseRepository).save(Mockito.<Course>any());
        verify(objectMapper).readValue(Mockito.<String>any(), Mockito.<Class<Course>>any());
        verify(objectMapper).writeValueAsString(Mockito.<Object>any());
    }


    @Test
    void testGetResponseObject() {
        assertEquals(2, StudentService.getResponseObject(1, "Data").size());
        assertEquals(2, StudentService.getResponseObject(1, "Not all who wander are lost").size());
    }
}

