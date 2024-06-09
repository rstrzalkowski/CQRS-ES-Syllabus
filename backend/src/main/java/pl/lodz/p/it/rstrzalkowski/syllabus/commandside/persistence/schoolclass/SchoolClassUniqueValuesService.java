package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.schoolclass;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.schoolclass.SchoolClassUniqueValuesExistCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@WriteApplicationBean
public class SchoolClassUniqueValuesService {
    private final SchoolClassUniqueValuesRepository schoolClassUniqueValuesRepository;

    public void lockSchoolClassLevelAndTeacherId(String name, Integer level, UUID teacherId, UUID schoolClassId) {
        try {
            schoolClassUniqueValuesRepository.save(new SchoolClassUniqueValuesJpaEntity(name, level, teacherId, schoolClassId));
            schoolClassUniqueValuesRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new SchoolClassUniqueValuesExistCommandExecutionException();
        }
    }

    public void updateSchoolClassLevelAndTeacherId(String name, Integer level, UUID teacherId, UUID schoolClassId) {
        try {
            schoolClassUniqueValuesRepository.findByAggregateId(schoolClassId).ifPresent(schoolClassUniqueValuesJpaEntity -> {
                schoolClassUniqueValuesJpaEntity.setName(name);
                schoolClassUniqueValuesJpaEntity.setLevel(level);
                schoolClassUniqueValuesJpaEntity.setTeacherId(teacherId);
                schoolClassUniqueValuesRepository.save(schoolClassUniqueValuesJpaEntity);
                schoolClassUniqueValuesRepository.flush();
            });
        } catch (DataIntegrityViolationException e) {
            throw new SchoolClassUniqueValuesExistCommandExecutionException();
        }
    }

    public void releaseValues(UUID subjectId) {
        schoolClassUniqueValuesRepository.findByAggregateId(subjectId).ifPresent(schoolClassUniqueValuesRepository::delete);
        schoolClassUniqueValuesRepository.flush();
    }
}
