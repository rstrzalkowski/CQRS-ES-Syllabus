package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.realisation.RealisationUniqueValuesExistCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.time.Year;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@WriteApplicationBean
public class RealisationUniqueValuesService {
    private final RealisationUniqueValuesRepository realisationUniqueValuesRepository;

    public void lockSubjectIdAndClassIdAndYear(UUID subjectId, UUID schoolClassId, Year year, UUID realisationId) {
        try {
            realisationUniqueValuesRepository.save(new RealisationUniqueValuesJpaEntity(subjectId, schoolClassId, year, realisationId));
            realisationUniqueValuesRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new RealisationUniqueValuesExistCommandExecutionException();
        }
    }

    public void releaseValues(UUID realisationId) {
        realisationUniqueValuesRepository.findByAggregateId(realisationId).ifPresent(realisationUniqueValuesRepository::delete);
        realisationUniqueValuesRepository.flush();
    }
}
