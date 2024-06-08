package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.realisation.RealisationAlreadyExistsCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.time.Year;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@WriteApplicationBean
public class RealisedSubjectService {
    private final RealisedSubjectRepository realisedSubjectRepository;

    public void lockSubjectIdAndClassIdAndYear(UUID subjectId, UUID schoolClassId, Year year, UUID realisationId) {
        try {
            realisedSubjectRepository.save(new RealisedSubjectEntity(subjectId, schoolClassId, year, realisationId));
            realisedSubjectRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new RealisationAlreadyExistsCommandExecutionException();
        }
    }

    public void releaseSubjectIdAndClassIdAndYear(UUID realisationId) {
        realisedSubjectRepository.findByAggregateId(realisationId).ifPresent(realisedSubjectRepository::delete);
        realisedSubjectRepository.flush();
    }
}
