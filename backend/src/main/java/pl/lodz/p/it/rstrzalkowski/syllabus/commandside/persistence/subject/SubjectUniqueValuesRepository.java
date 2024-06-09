package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.Optional;
import java.util.UUID;

@Repository
@WriteApplicationBean
public interface SubjectUniqueValuesRepository extends JpaRepository<SubjectUniqueValuesJpaEntity, UUID> {
    Optional<SubjectUniqueValuesJpaEntity> findByAggregateId(UUID aggregateId);
}
