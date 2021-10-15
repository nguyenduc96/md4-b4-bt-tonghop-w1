package com.nguyenduc.repository.student;

import com.nguyenduc.model.Student;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class StudentRepository implements IStudentRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Student> findAll() {
        String queryStr = "SELECT s FROM Student AS s";
        TypedQuery<Student> query = entityManager.createQuery(queryStr, Student.class);
        return query.getResultList();
    }

    @Override
    public List<Student> findByName(String name) {
        String queryStr = "SELECT s FROM Student AS s WHERE s.name like :name";
        TypedQuery<Student> query = entityManager.createQuery(queryStr, Student.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void save(Student student) {
        if (student.getId() == null) {
            entityManager.merge(student);
        } else {
            entityManager.persist(student);
        }
    }

    @Override
    public void delete(Long id) {
        Student student = findById(id);
        if (student != null) {
            entityManager.remove(student);
        }
    }

    @Override
    public Student findById(Long id) {
        String queryStr = "SELECT s FROM Student AS s WHERE s.id = :id";
        TypedQuery<Student> query = entityManager.createQuery(queryStr, Student.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
