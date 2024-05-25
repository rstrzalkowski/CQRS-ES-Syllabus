package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private UUID id;

    @Column(unique = true)
    private String subjectName;
}
