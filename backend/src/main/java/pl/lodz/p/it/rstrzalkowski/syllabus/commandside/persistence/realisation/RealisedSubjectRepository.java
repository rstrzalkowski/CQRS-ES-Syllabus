package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

@Repository
@WriteApplicationBean
public interface RealisedSubjectRepository extends JpaRepository<RealisedSubjectEntity, UUID> {
    Optional<RealisedSubjectEntity> findBySubjectIdAndSchoolClassIdAndYear(UUID subjectId, UUID schoolClassId, Year year);
}
