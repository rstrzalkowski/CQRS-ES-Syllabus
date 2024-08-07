package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;

import java.time.Year;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealisationDTO {
    private UUID id;
    private String subjectName;
    private UUID teacherId;
    private Year year;
    private String teacherFirstName;
    private String teacherLastName;
    private String schoolClassName;
    private String subjectAbbreviation;
    private String imageUrl;

    public RealisationDTO(RealisationEntity realisation) {
        this.id = realisation.getId();
        this.subjectName = realisation.getSubject().getName();
        this.year = realisation.getYear();
        this.teacherId = realisation.getTeacher().getId();
        this.teacherFirstName = realisation.getTeacher().getFirstName();
        this.teacherLastName = realisation.getTeacher().getLastName();
        this.schoolClassName = realisation.getSchoolClass().getSchoolClassName();
        this.subjectAbbreviation = realisation.getSubject().getAbbreviation();
        this.imageUrl = realisation.getSubject().getImageUrl();
    }
}
