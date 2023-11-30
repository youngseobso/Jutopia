package com.ssafy.classserver.service;

import com.ssafy.classserver.dto.ClassRoomDto;
import com.ssafy.classserver.dto.SchoolDto;
import com.ssafy.classserver.jpa.entity.ClassRoomEntity;
import com.ssafy.classserver.jpa.entity.GradeEntity;
import com.ssafy.classserver.jpa.entity.SchoolEntity;

import java.util.Optional;
import java.util.UUID;

public interface SchoolService {
    Iterable<SchoolEntity> getAllSchool();
    Iterable<GradeEntity> getGradesBySchoolId(UUID schoolId);
    Optional<SchoolEntity> getOneSchool(UUID schoolId);
    SchoolDto createSchool(SchoolDto school);
    ClassRoomDto createClassRoom(ClassRoomDto classRoomDto, UUID gradeId);
    ClassRoomDto getClassRoom(UUID classroomId);
    Iterable<ClassRoomEntity> getClassesByGradeId(UUID id);

    UUID getClassRoomId(String schoolName, int grade, int classNum);
}
