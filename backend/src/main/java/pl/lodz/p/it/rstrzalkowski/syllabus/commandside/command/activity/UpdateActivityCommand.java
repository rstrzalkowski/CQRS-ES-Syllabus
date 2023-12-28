package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateActivityCommand {

    @TargetAggregateIdentifier
    private UUID id;

    @Length(max = 40)
    private String name;

    @Min(1)
    private Integer weight;

    @Length(max = 200)
    private String description;

    private LocalDateTime date;
}
