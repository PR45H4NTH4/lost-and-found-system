package com.uni.lostandfound.service;

import com.uni.lostandfound.entity.Course;
import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();
    Course getCourseById(Long id);
    Course saveCourse(Course course);
    Course updateCourse(Long id, Course courseDetails);
    Course patchCourse(Long id, Course partialCourseDetails);
    void deleteCourse(Long id);
}
