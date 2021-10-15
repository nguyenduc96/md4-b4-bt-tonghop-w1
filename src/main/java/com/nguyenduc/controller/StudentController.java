package com.nguyenduc.controller;

import com.nguyenduc.model.Student;
import com.nguyenduc.model.StudentForm;
import com.nguyenduc.service.student.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class StudentController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IStudentService studentService;

    @GetMapping("/home")
    public ModelAndView home(@RequestParam(name = "q", required = false) String q) {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Student> students;
        if (q == null || q.equals("")) {
            students = studentService.findAll();
        } else {
            students = studentService.findByName(q);
            if (students.size() == 0) {
                modelAndView.addObject("message", "Not found!!!!");
            }
        }
        if (students == null) {
            modelAndView.addObject("message", "Home");
        } else {
            modelAndView.addObject("students", students);
        }
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        return new ModelAndView("create", "studentForm", new StudentForm());
    }

    @PostMapping("/create")
    public ModelAndView createStudent(@ModelAttribute StudentForm studentForm) {
        ModelAndView modelAndView = new ModelAndView("create");
        MultipartFile multipartFile = studentForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(studentForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Student student = new Student(studentForm.getId(), studentForm.getName(),
                studentForm.getAddress(), studentForm.getEmail(), fileName);
        studentService.save(student);
        modelAndView.addObject("studentForm", studentForm);
        modelAndView.addObject("message", "Created successfully!!!");
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView showDelete(@PathVariable("id") Long id) {
        Student student = studentService.findById(id);
        return new ModelAndView("delete", "student", student);
    }

    @PostMapping("/delete")
    public String deleteStudent(@ModelAttribute Student student) {
        studentService.delete(student.getId());
        return "redirect:/home";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showForm(@PathVariable("id") Long id) {
        Student student = studentService.findById(id);
        return new ModelAndView("create", "studentForm", new StudentForm(student.getId(),
                student.getName(), student.getAddress(), student.getEmail(), null));
    }
}
