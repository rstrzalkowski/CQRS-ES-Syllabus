package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.PostEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.UUID;

@Repository
@ReadApplicationBean
public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    Page<PostEntity> findByRealisationIdAndArchived(UUID realisationId, boolean archived, Pageable pageable);

    Page<PostEntity> findByRealisation_SchoolClass_Students_IdAndArchived(UUID studentId, boolean archived,
                                                                          Pageable pageable);
}
