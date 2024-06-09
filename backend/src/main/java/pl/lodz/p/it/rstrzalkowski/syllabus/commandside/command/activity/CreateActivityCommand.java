package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateActivityCommand {

    @NotNull
    @Length(max = 40)
    private String name;

    @NotNull
    @Min(1)
    private Integer weight;

    @NotNull
    @Length(max = 200)
    private String description;

    @NotNull
    private LocalDateTime date;

    @NotNull
    @TargetAggregateIdentifier
    private UUID realisationId;

    @Setter
    private UUID teacherId;

    @Setter
    private UUID activityId;
}
