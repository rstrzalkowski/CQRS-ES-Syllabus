package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SchoolClassEntity;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassDTO {

    private UUID id;

    private String name;

    private String fullName;

    private UserDTO supervisingTeacher;

    private List<UserDTO> students;

    private Integer level;


    public SchoolClassDTO(SchoolClassEntity schoolClass) {
        this.id = schoolClass.getId();
        this.name = schoolClass.getName();
        this.fullName = schoolClass.getFullName();
        this.supervisingTeacher = new UserDTO(schoolClass.getSupervisingTeacher());
        this.level = schoolClass.getLevel();
        this.students = schoolClass.getStudents().stream().map(UserDTO::new).toList();
    }
}
