package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;

import java.time.Year;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RealisationDto extends AbstractDto {
    private UUID id;
    private String subjectName;
    private UUID teacherId;
    private Year year;
    private String teacherFirstName;
    private String teacherLastName;
    private String schoolClassName;
    private String subjectAbbreviation;

    public RealisationDto(RealisationEntity realisation) {
        this.id = realisation.getId();
        this.subjectName = realisation.getSubject().getName();
        this.year = realisation.getYear();
        this.teacherId = realisation.getTeacher().getId();
        this.teacherFirstName = realisation.getTeacher().getFirstName();
        this.teacherLastName = realisation.getTeacher().getLastName();
        this.schoolClassName = realisation.getSchoolClass().getSchoolClassName();
        this.subjectAbbreviation = realisation.getSubject().getAbbreviation();
    }
}
