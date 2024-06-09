package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.AbstractJpaEntity;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectUniqueValuesJpaEntity extends AbstractJpaEntity {

    @Column(unique = true, nullable = false)
    @NotNull
    private String subjectName;

    public SubjectUniqueValuesJpaEntity(String name, UUID subjectId) {
        this.subjectName = name;
        setAggregateId(subjectId);
    }
}
