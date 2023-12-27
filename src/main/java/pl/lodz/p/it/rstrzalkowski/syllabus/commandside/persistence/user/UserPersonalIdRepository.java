package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.Optional;

@Repository
@WriteApplicationBean
public interface UserPersonalIdRepository extends JpaRepository<UserPersonalIdEntity, String> {
    Optional<UserPersonalIdEntity> findByPersonalId(String personalId);
}
