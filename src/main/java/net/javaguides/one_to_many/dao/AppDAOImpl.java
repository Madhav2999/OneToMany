package net.javaguides.one_to_many.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import net.javaguides.one_to_many.entity.Course;
import net.javaguides.one_to_many.entity.Instructor;
import net.javaguides.one_to_many.entity.InstructorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO
{
    @Autowired
    private EntityManager entityManager;


    @Override
    @Transactional
    public void save(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int id)
    {
        Instructor instructor = findInstructorById(id);
        //as we are deleting the instructor but it is mapped with courses as a foreign key hence we need to remove the instructor from the courses
        List<Course>courses = instructor.getCourses();
        for(Course course: courses)
        {
            course.setInstructor(null);
        }
        entityManager.remove(instructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return entityManager.find(InstructorDetail.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int id)
    {
       InstructorDetail instructorDetail = findInstructorDetailById(id);
       // removing the bi-directional relationship between instructor and instructor detail
         instructorDetail.getInstructor().setInstructorDetail(null);

         entityManager.remove(instructorDetail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "from Course where instructor.id = :data", Course.class);
        query.setParameter("data", id);
        List<Course> courses = query.getResultList();
        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int id) {
        TypedQuery<Instructor> query = entityManager.createQuery(
                "select i from Instructor i join fetch i.courses join fetch i.instructorDetail where i.id = :data", Instructor.class);
        query.setParameter("data", id);

        Instructor instructor = query.getSingleResult();
        return instructor;
    }

    @Override
    @Transactional
    public void update(Instructor tempInstructor) {
         entityManager.merge(tempInstructor);
    }

    @Override
    @Transactional
    public void update(Course tempCourse) {
         entityManager.merge(tempCourse);
    }

    @Override
    public Course findCourseById(int id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        Course course = findCourseById(id);
        List<Course> courses = course.getInstructor().getCourses();
        for(Course c: courses)
        {
            if(c.getId()==id)
            {
                courses.remove(c);
                break;
            }
        }
        course.getInstructor().setCourses(courses);
        entityManager.remove(course);
    }

    @Override
    @Transactional
    public void save(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int id) {
        TypedQuery<Course>query = entityManager.createQuery(
                "select c from Course c join fetch c.reviews  where c.id = :data", Course.class);
        query.setParameter("data", id);
        Course course = query.getSingleResult();
        return course;
    }
}
