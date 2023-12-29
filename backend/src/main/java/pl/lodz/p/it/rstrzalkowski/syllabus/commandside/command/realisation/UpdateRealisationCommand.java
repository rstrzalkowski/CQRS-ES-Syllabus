package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Year;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateRealisationCommand {

    @TargetAggregateIdentifier
    private UUID id;

    private Year year;

    private UUID teacherId;
}
