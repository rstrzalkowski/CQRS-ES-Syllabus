package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateSubjectCommand {

    @NotNull
    private String name;

    @NotNull
    private String abbreviation;

    @Setter
    private UUID subjectId;
}
