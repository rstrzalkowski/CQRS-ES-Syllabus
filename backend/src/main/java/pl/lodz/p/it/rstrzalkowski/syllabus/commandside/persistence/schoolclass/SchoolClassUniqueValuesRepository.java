package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.schoolclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.Optional;
import java.util.UUID;

@Repository
@WriteApplicationBean
public interface SchoolClassUniqueValuesRepository extends JpaRepository<SchoolClassUniqueValuesJpaEntity, UUID> {
    Optional<SchoolClassUniqueValuesJpaEntity> findByAggregateId(UUID aggregateId);
}
