package com.spring_security.demo.service;

import com.spring_security.demo.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private List<Student> students;

    public StudentService() {
        students = new ArrayList<>();
        students.add(new Student(1,"Yousef",10));
        students.add(new Student(2,"joe",50));
        students.add(new Student(3,"mohamed",99));
        students.add(new Student(4,"Kamal",100));
    }
    public String addStudent(Student student)
    {
        students.add(student);
        return "Student has been created";
    }

    public Student getStudent(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Student> getStudents() {
        return students;
    }
}
