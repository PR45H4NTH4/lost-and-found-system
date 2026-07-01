package com.uni.lostandfound.service;

import com.uni.lostandfound.entity.Course;
import com.uni.lostandfound.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        return courseRepository.findById(id).map(existingCourse -> {
            existingCourse.setName(courseDetails.getName());
            existingCourse.setLecturer(courseDetails.getLecturer());
            existingCourse.setIsActive(courseDetails.getIsActive());
            return courseRepository.save(existingCourse);
        }).orElse(null);
    }

    @Override
    public Course patchCourse(Long id, Course partialCourseDetails) {
        return courseRepository.findById(id).map(existingCourse -> {
            if (partialCourseDetails.getName() != null) {
                existingCourse.setName(partialCourseDetails.getName());
            }
            if (partialCourseDetails.getLecturer() != null) {
                existingCourse.setLecturer(partialCourseDetails.getLecturer());
            }
            if (partialCourseDetails.getIsActive() != null) {
                existingCourse.setIsActive(partialCourseDetails.getIsActive());
            }
            return courseRepository.save(existingCourse);
        }).orElse(null);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
