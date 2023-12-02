package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateSchoolClassCommand {

    @NotNull
    private Integer level;

    @NotNull
    private UUID teacherId;

    @NotNull
    @Length(max = 5)
    private String shortName;

    @NotNull
    @Length(max = 40)
    private String fullName;
}
