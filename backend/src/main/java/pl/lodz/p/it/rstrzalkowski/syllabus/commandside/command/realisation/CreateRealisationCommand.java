package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateRealisationCommand {

    @NotNull
    private Year year;

    @NotNull
    private UUID teacherId;

    @NotNull
    private UUID subjectId;

    @NotNull
    private UUID classId;

    @Setter
    private UUID realisationId;
}
