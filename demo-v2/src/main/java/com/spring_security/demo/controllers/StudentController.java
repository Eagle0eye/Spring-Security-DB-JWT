package com.spring_security.demo.controllers;

import com.spring_security.demo.model.Student;
import com.spring_security.demo.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {


    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<Student> viewStudents(){
        return studentService.getStudents();
    }

    @GetMapping("/students/{id}")
    public Student viewStudent(@PathVariable int id){
        return studentService.getStudent(id);
    }

    @GetMapping("/csrf-token")
    public CsrfToken viewCSRFToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/students")
    public String genStudent(@RequestBody Student student)
    {
        return studentService.addStudent(student);
    }
}
