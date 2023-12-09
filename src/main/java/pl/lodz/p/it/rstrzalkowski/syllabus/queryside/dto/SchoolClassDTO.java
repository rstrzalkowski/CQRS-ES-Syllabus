package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SchoolClassEntity;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassDTO {

    private UUID id;

    private String name;

    private String fullName;

    private UUID supervisingTeacherId;


    public SchoolClassDTO(SchoolClassEntity schoolClass) {
        this.id = schoolClass.getId();
        this.name = schoolClass.getName();
        this.fullName = schoolClass.getFullName();
        this.supervisingTeacherId = schoolClass.getSupervisingTeacher().getId();
    }
}
