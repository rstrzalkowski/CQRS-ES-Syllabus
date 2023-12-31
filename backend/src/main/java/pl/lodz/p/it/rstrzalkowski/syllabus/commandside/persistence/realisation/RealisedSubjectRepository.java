package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@Repository
@WriteApplicationBean
public interface RealisedSubjectRepository extends JpaRepository<RealisedSubjectEntity, RealisedSubjectId> {
}
