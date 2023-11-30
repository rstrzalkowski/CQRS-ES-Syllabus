package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RealisedSubjectEntity {
    @EmbeddedId
    public RealisedSubjectId realisedSubjectId;
}
