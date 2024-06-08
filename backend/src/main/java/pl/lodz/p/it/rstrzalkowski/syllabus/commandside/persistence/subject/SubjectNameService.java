package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.subject.SubjectAlreadyExistsCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@WriteApplicationBean
public class SubjectNameService {
    private final SubjectNameRepository subjectNameRepository;

    public void lockSubjectName(String name, UUID subjectId) {
        try {
            subjectNameRepository.save(new SubjectNameEntity(name, subjectId));
            subjectNameRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new SubjectAlreadyExistsCommandExecutionException();
        }
    }

    public void updateSubjectName(String name, UUID subjectId) {
        try {
            subjectNameRepository.findByAggregateId(subjectId).ifPresent(subjectNameEntity -> {
                subjectNameEntity.setSubjectName(name);
                subjectNameRepository.save(subjectNameEntity);
                subjectNameRepository.flush();
            });
        } catch (DataIntegrityViolationException e) {
            throw new SubjectAlreadyExistsCommandExecutionException();
        }
    }

    public void releaseSubjectName(UUID subjectId) {
        subjectNameRepository.findByAggregateId(subjectId).ifPresent(subjectNameRepository::delete);
        subjectNameRepository.flush();
    }
}
