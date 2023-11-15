package pl.lodz.p.it.rstrzalkowski.syllabus.shared.util;

import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.SubjectDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SubjectEntity;

public class SubjectConverter {
    public static SubjectDto convertToSubjectDto(final SubjectEntity subjectEntity) {
        final SubjectDto subjectDto = new SubjectDto();
        subjectDto.setUuid(subjectEntity.getId());
        subjectDto.setName(subjectEntity.getName());
        subjectDto.setAbbreviation(subjectEntity.getAbbreviation());
        return subjectDto;
    }
}