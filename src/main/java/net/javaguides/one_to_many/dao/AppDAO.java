package net.javaguides.one_to_many.dao;

import net.javaguides.one_to_many.entity.Course;
import net.javaguides.one_to_many.entity.Instructor;
import net.javaguides.one_to_many.entity.InstructorDetail;

import java.util.List;

public interface AppDAO
{
    void save(Instructor instructor);

    Instructor findInstructorById(int id);

    void deleteInstructorById(int id);

    InstructorDetail findInstructorDetailById(int id);

    void deleteInstructorDetailById(int id);

    List<Course> findCoursesByInstructorId(int id);

    Instructor findInstructorByIdJoinFetch(int id);

    void update(Instructor tempInstructor);

    void update(Course tempCourse);

    Course findCourseById(int id);

    void deleteCourseById(int id);

    void save(Course course);

    Course findCourseAndReviewsByCourseId(int id);
}
