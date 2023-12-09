package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SubjectEntity;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {

    private UUID id;

    private UUID realisationId;

    private String subjectName;

    private String subjectAbbreviation;

    private String imageUrl;

    private String schoolClassName;

    public SubjectDTO(RealisationEntity realisation) {
        this.realisationId = realisation.getId();
        this.subjectName = realisation.getSubject().getName();
        this.subjectAbbreviation = realisation.getSubject().getAbbreviation();
        this.schoolClassName = realisation.getSchoolClass().getSchoolClassName();
        this.imageUrl = realisation.getSubject().getImageUrl();
    }

    public SubjectDTO(SubjectEntity subjectEntity) {
        this.id = subjectEntity.getId();
        this.subjectName = subjectEntity.getName();
        this.subjectAbbreviation = subjectEntity.getAbbreviation();
        this.imageUrl = subjectEntity.getImageUrl();
    }
}
