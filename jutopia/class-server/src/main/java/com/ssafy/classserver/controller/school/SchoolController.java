package com.ssafy.classserver.controller.school;

import com.ssafy.classserver.dto.ClassRoomDto;
import com.ssafy.classserver.dto.SchoolDto;
import com.ssafy.classserver.jpa.entity.ClassRoomEntity;
import com.ssafy.classserver.jpa.entity.GradeEntity;
import com.ssafy.classserver.jpa.entity.SchoolEntity;
import com.ssafy.classserver.service.SchoolService;
import com.ssafy.classserver.vo.request.RequestClassRoom;
import com.ssafy.classserver.vo.request.RequestSchool;
import com.ssafy.classserver.vo.response.ResponseClassRoom;
import com.ssafy.classserver.vo.response.ResponseGrade;
import com.ssafy.classserver.vo.response.ResponseSchool;
import com.ssafy.common.api.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/class-server/api/school")
public class SchoolController {
    Environment env;
    SchoolService schoolService;
    ModelMapper mapper;

    @Autowired
    public SchoolController(Environment env, SchoolService schoolService) {
        this.env = env;
        this.schoolService = schoolService;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/")
    public Api<List<ResponseSchool>> getAllSchool() {
        Iterable<SchoolEntity> schoolList = schoolService.getAllSchool();

        List<ResponseSchool> result = new ArrayList<>();

        for (SchoolEntity school : schoolList) {
            // 해당 학교의 학년 정보 가져오기
            Iterable<GradeEntity> gradeList = schoolService.getGradesBySchoolId(school.getId());
            List<ResponseGrade> responseGrades = new ArrayList<>();


            for (GradeEntity grade : gradeList) {
                // GradeEntity를 ResponseGrade로 매핑
                ResponseGrade responseGrade = mapper.map(grade, ResponseGrade.class);

                // 해당 학년의 반정보 가져오기
                List<ResponseClassRoom> responseClassRooms = new ArrayList<>();
                Iterable<ClassRoomEntity> classList = schoolService.getClassesByGradeId(grade.getId());
                for (ClassRoomEntity classroom : classList) {
                    ResponseClassRoom classRoom = mapper.map(classroom, ResponseClassRoom.class);
                    responseClassRooms.add(classRoom);
                }
                responseGrade.setClassRooms(responseClassRooms);

                responseGrades.add(responseGrade);
                System.out.println(grade);
            }
            // 학교 정보와 학년 정보를 함께 저장
            ResponseSchool responseSchool = new ResponseSchool(
                    school.getId(),
                    school.getSchoolName(),
                    school.getSchoolCode(),
                    school.getRegion(),
                    responseGrades
            );
            result.add(responseSchool);
        }

        return Api.OK(result);
    }

    // 학교와 학년 등록하기 (관리자)
    @PostMapping("/")
    public Api<ResponseSchool> createSchool(@RequestBody RequestSchool school) {
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        SchoolDto schoolDto = schoolService.createSchool(mapper.map(school, SchoolDto.class));

        ResponseSchool responseSchool = mapper.map(schoolDto, ResponseSchool.class);
        return Api.CREATED(responseSchool);
    }

    // 반 등록 (선생님)
    @PostMapping("/classroom/{gradeId}")
    public Api<ResponseClassRoom> createClassRoom(@RequestBody RequestClassRoom classRoom, @PathVariable UUID gradeId) {
        ClassRoomDto classRoomDto = schoolService.createClassRoom(mapper.map(classRoom, ClassRoomDto.class), gradeId);
        return Api.CREATED(mapper.map(classRoomDto, ResponseClassRoom.class));
    }

    // 등록된 학교정보 가져오기 (선생님)
    @GetMapping("/classroom/{classroomId}")
    public Api<ResponseClassRoom> getClassRoom(@PathVariable UUID classroomId){
        ClassRoomDto classroom = schoolService.getClassRoom(classroomId);
        System.out.println("controller : " + classroom);
        return Api.OK(mapper.map(classroom, ResponseClassRoom.class));
    }

    @GetMapping("/{schoolName}/{grade}/{classNum}")
    public UUID getClassroomId(@PathVariable("schoolName") String schoolName,
                                           @PathVariable("grade") int grade,
                                           @PathVariable("classNum") int classNum) {

        System.out.println("1. class-server : 요청 받아옴!!!" + schoolName + " : " + grade + " : " + classNum);
        UUID classroomId = schoolService.getClassRoomId(schoolName, grade, classNum);
        return classroomId;
    }
}
