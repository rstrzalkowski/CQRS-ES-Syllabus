package pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.converter;

import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.RealisationDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;

public class RealisationConverter {
    public static RealisationDto convertToRealisationDto(final RealisationEntity realisationEntity) {
        final RealisationDto realisationDto = new RealisationDto();
        realisationDto.setId(realisationEntity.getId());
        realisationDto.setSubjectName(realisationEntity.getSubject().getName());
        realisationDto.setSchoolClassName(realisationEntity.getSchoolClass().getSchoolClassName());
        realisationDto.setYear(realisationEntity.getYear());
        realisationDto.setTeacherId(realisationEntity.getTeacher().getId());
        
        return realisationDto;
    }
}
