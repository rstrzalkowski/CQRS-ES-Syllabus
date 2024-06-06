package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"subjectId", "schoolClassId", "year"})})
public class RealisedSubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID subjectId;

    private UUID schoolClassId;

    private Year year;

    private UUID aggregateId;

    public RealisedSubjectEntity(UUID subjectId, UUID schoolClassId, Year year) {
        this.subjectId = subjectId;
        this.schoolClassId = schoolClassId;
        this.year = year;
    }

    public RealisedSubjectEntity(UUID subjectId, UUID schoolClassId, Year year, UUID realisationId) {
        this.subjectId = subjectId;
        this.schoolClassId = schoolClassId;
        this.year = year;
        this.aggregateId = realisationId;
    }
}
