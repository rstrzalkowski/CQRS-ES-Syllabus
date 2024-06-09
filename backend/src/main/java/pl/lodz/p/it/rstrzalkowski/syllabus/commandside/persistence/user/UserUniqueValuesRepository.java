package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.Optional;

@Repository
@WriteApplicationBean
public interface UserUniqueValuesRepository extends JpaRepository<UserUniqueValuesJpaEntity, String> {
    Optional<UserUniqueValuesJpaEntity> findByPersonalId(String personalId);

    Optional<UserUniqueValuesJpaEntity> findByEmail(String email);
}
