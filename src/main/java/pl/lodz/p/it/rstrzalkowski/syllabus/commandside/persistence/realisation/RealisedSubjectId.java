package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RealisedSubjectId implements Serializable {
    private UUID subjectId;

    private UUID realisationId;
}
