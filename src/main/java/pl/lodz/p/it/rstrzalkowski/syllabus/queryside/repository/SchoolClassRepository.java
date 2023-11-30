package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SchoolClassEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.UUID;

@Repository
@ReadApplicationBean
public interface SchoolClassRepository extends JpaRepository<SchoolClassEntity, UUID> {
    Page<SchoolClassEntity> findAllByArchived(boolean archived, Pageable pageable);
}
