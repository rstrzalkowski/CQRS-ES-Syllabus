package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.grade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateGradeCommand {

    @Min(1)
    @Max(5)
    @NotNull
    private Integer value;

    @NotNull
    private UUID studentId;

    @Length(max = 100)
    private String comment;

    @NotNull
    private UUID activityId;
}
