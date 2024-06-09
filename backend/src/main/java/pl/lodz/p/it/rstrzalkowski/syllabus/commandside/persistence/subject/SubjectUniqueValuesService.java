package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.subject.SubjectUniqueValuesExistCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@WriteApplicationBean
public class SubjectUniqueValuesService {
    private final SubjectUniqueValuesRepository subjectUniqueValuesRepository;

    public void lockSubjectName(String name, UUID subjectId) {
        try {
            subjectUniqueValuesRepository.save(new SubjectUniqueValuesJpaEntity(name, subjectId));
            subjectUniqueValuesRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new SubjectUniqueValuesExistCommandExecutionException();
        }
    }

    public void updateSubjectName(String name, UUID subjectId) {
        try {
            subjectUniqueValuesRepository.findByAggregateId(subjectId).ifPresent(subjectUniqueValuesJpaEntity -> {
                subjectUniqueValuesJpaEntity.setSubjectName(name);
                subjectUniqueValuesRepository.save(subjectUniqueValuesJpaEntity);
                subjectUniqueValuesRepository.flush();
            });
        } catch (DataIntegrityViolationException e) {
            throw new SubjectUniqueValuesExistCommandExecutionException();
        }
    }

    public void releaseValues(UUID subjectId) {
        subjectUniqueValuesRepository.findByAggregateId(subjectId).ifPresent(subjectUniqueValuesRepository::delete);
        subjectUniqueValuesRepository.flush();
    }
}
