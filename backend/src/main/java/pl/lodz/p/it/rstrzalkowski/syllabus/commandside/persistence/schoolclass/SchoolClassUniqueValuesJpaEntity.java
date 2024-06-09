package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.schoolclass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.AbstractJpaEntity;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "level"})})
public class SchoolClassUniqueValuesJpaEntity extends AbstractJpaEntity {

    private String name;

    private Integer level;

    @Column(unique = true, nullable = false)
    private UUID teacherId;

    public SchoolClassUniqueValuesJpaEntity(String name, Integer level, UUID teacherId, UUID schoolClassId) {
        this.name = name;
        this.level = level;
        this.teacherId = teacherId;
        setAggregateId(schoolClassId);
    }
}
