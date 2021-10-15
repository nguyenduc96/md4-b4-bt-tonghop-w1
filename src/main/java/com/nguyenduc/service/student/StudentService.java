package com.nguyenduc.service.student;

import com.nguyenduc.model.Student;
import com.nguyenduc.repository.student.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService {
    @Autowired
    private IStudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findByName(String name) {
        name = "%" + name + "%";
        return studentRepository.findByName(name);
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id);
    }
}
