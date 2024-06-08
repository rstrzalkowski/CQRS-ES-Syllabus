package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    @NotNull
    private String subjectName;

    private UUID aggregateId;

    public SubjectNameEntity(String name, UUID subjectId) {
        this.subjectName = name;
        this.aggregateId = subjectId;
    }
}
