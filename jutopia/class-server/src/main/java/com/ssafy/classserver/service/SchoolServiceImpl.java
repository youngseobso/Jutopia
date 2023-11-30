package com.ssafy.classserver.service;

import com.ssafy.classserver.dto.ClassRoomDto;
import com.ssafy.classserver.dto.SchoolDto;
import com.ssafy.classserver.jpa.entity.ClassRoomEntity;
import com.ssafy.classserver.jpa.entity.GradeEntity;
import com.ssafy.classserver.jpa.entity.SchoolEntity;
import com.ssafy.classserver.jpa.repository.ClassRoomRepository;
import com.ssafy.classserver.jpa.repository.GradeRepository;
import com.ssafy.classserver.jpa.repository.SchoolRepository;
import com.ssafy.classserver.vo.request.RequestGrade;
import com.ssafy.classserver.vo.response.ResponseGrade;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Data
@Slf4j
public class SchoolServiceImpl implements SchoolService{
    SchoolRepository schoolRepository;
    GradeRepository gradeRepository;
    ClassRoomRepository classRoomRepository;
    ModelMapper mapper;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository, GradeRepository gradeRepository
                            , ClassRoomRepository classRoomRepository) {
        this.schoolRepository = schoolRepository;
        this.gradeRepository = gradeRepository;
        this.classRoomRepository = classRoomRepository;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public Iterable<SchoolEntity> getAllSchool() {
        return schoolRepository.findAll();
    }

    @Override
    public Iterable<GradeEntity> getGradesBySchoolId(UUID schoolId) {
        return gradeRepository.findAllBySchoolId(schoolId);
    }

    @Override
    public SchoolDto createSchool(SchoolDto school) {
//        // userDto 객체를 Jpa를 통해 DB에 저장할 수 있는 객체인 Entity로 변환 할 수 있는 라이브러리
//        ModelMapper mapper = new ModelMapper();
//        // mapper의 설정에서 강력한 설정으로 변환
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // 학교 정보 저장
        SchoolEntity schoolEntity = schoolRepository.save(mapper.map(school, SchoolEntity.class));
        System.out.println(schoolEntity);
        // 해당 학교의 학년 정보 저장
        for(ResponseGrade grade : school.getGrades()) {
            GradeEntity gradeEntity = mapper.map(grade, GradeEntity.class);
            gradeEntity.setSchool(schoolEntity);
            gradeRepository.save(gradeEntity);
        }

        return school;
    }

    @Override
    public ClassRoomDto createClassRoom(ClassRoomDto classRoomDto, UUID gradeId) {
        ClassRoomEntity classRoomEntity = mapper.map(classRoomDto, ClassRoomEntity.class);
        // gradeRepository.findById(gradeId)로 얻은 Optional을 orElseThrow로 언랩핑하여 GradeEntity를 얻습니다.
        GradeEntity gradeEntity = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new EntityNotFoundException("GradeEntity not found with id: " + gradeId));

        classRoomEntity.setGrade(gradeEntity);
        classRoomEntity = classRoomRepository.save(classRoomEntity);

        System.out.println(classRoomEntity);

        ClassRoomDto returnDto = mapper.map(classRoomEntity, ClassRoomDto.class);
        returnDto.setId(classRoomEntity.getId());
        returnDto.setSchoolName(gradeEntity.getSchool().getSchoolName());
        returnDto.setGradeNum(Integer.parseInt(String.valueOf(gradeEntity.getGradeNum())));
        System.out.println(returnDto);
        return returnDto;
    }

    @Override
    public ClassRoomDto getClassRoom(UUID classroomId) {
        Optional<ClassRoomEntity> classroom = classRoomRepository.findById(classroomId);
        System.out.println("service : " + classroom);
        ClassRoomDto result = mapper.map(classroom.get(), ClassRoomDto.class);
        result.setSchoolName(classroom.get().getGrade().getSchool().getSchoolName());
        result.setGradeNum(classroom.get().getGrade().getGradeNum());
        System.out.println("service: " + result);
        return result;
    }

    @Override
    public Iterable<ClassRoomEntity> getClassesByGradeId(UUID gradeId) {
        return classRoomRepository.findAllByGradeId(gradeId);
    }

    @Override
    public UUID getClassRoomId(String schoolName, int gradeNum, int classNum) {
        UUID schoolId = schoolRepository.findFirstBySchoolName(schoolName).get().getId();
        UUID gradeId = gradeRepository.findFirstByGradeNumAndSchoolId(gradeNum, schoolId).get().getId();
        UUID classroomId = classRoomRepository.findFirstByClassNumAndGradeId(classNum, gradeId).get().getId();
        return classroomId;
    }

    @Override
    public Optional<SchoolEntity> getOneSchool(UUID schoolId) {
        return schoolRepository.findById(schoolId);
    }

}
