package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.UUID;

@Repository
@ReadApplicationBean
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Page<UserEntity> findAllByArchived(boolean archived, Pageable pageable);
}