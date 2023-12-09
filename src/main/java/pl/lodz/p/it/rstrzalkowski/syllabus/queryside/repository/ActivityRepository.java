package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.ActivityEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@ReadApplicationBean
public interface ActivityRepository extends JpaRepository<ActivityEntity, UUID> {
    
    Page<ActivityEntity> findByRealisationIdAndArchived(UUID realisationId, boolean archived, Pageable pageable);

    Page<ActivityEntity> findByRealisationIdAndArchivedAndDateAfter(UUID realisationId, boolean archived,
                                                                    LocalDateTime date, Pageable pageable);

    Page<ActivityEntity> findByRealisation_SchoolClass_Students_IdAndArchivedAndDateGreaterThanEqual(UUID studentId,
                                                                                                     boolean archived,
                                                                                                     LocalDateTime date,
                                                                                                     Pageable pageable);
}
