package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.AbstractJpaEntity;

import java.time.Year;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"subjectId", "schoolClassId", "year"})})
public class RealisationUniqueValuesJpaEntity extends AbstractJpaEntity {

    private UUID subjectId;

    private UUID schoolClassId;

    private Year year;

    public RealisationUniqueValuesJpaEntity(UUID subjectId, UUID schoolClassId, Year year, UUID realisationId) {
        this.subjectId = subjectId;
        this.schoolClassId = schoolClassId;
        this.year = year;
        setSubjectId(realisationId);
    }
}
