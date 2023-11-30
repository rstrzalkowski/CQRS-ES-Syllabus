package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@Repository
@WriteApplicationBean
public interface SubjectNameRepository extends JpaRepository<SubjectNameEntity, String> {
}
