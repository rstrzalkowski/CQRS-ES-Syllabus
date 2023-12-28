package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.GradeEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@ReadApplicationBean
public interface GradeRepository extends JpaRepository<GradeEntity, UUID> {
    Page<GradeEntity> findAllByArchivedAndStudentId(boolean archived, UUID studentId, Pageable pageable);

    Optional<GradeEntity> findByActivityIdAndStudentId(UUID activityId, UUID studentId);

    List<GradeEntity> findAllByArchivedAndActivityArchivedAndActivityRealisationIdAndStudentId(boolean archived,
                                                                                               boolean activityArchived,
                                                                                               UUID realisationId,
                                                                                               UUID studentId);

    List<GradeEntity> findAllByArchivedAndStudentIdAndActivityRealisationId(boolean archived, UUID studentId,
                                                                            UUID realisationId);

    List<GradeEntity> findByActivityIdAndArchived(UUID id, boolean b);
}
