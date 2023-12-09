package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;
import java.util.UUID;

@Repository
@ReadApplicationBean
public interface RealisationRepository extends JpaRepository<RealisationEntity, UUID> {
    Page<RealisationEntity> findAllByArchived(boolean archived, Pageable pageable);

    Page<RealisationEntity> findAllByArchivedAndTeacherId(boolean archived, UUID teacherId, Pageable pageable);

    Page<RealisationEntity> findAllByArchivedAndSchoolClassId(boolean archived, UUID schoolClassId, Pageable pageable);

    boolean existsByArchivedAndSchoolClassIdAndSubjectId(boolean archived, UUID schoolClassId, UUID subjectId);

    Integer countByArchivedAndSubjectId(boolean archived, UUID subjectId);

    List<RealisationEntity> findByArchivedAndSubjectId(boolean archived, UUID subjectId);

    List<RealisationEntity> findAllByArchivedAndTeacherId(boolean archived, UUID teacherId);

    List<RealisationEntity> findAllByArchivedAndSchoolClassId(boolean archived, UUID schoolClassId);
}
