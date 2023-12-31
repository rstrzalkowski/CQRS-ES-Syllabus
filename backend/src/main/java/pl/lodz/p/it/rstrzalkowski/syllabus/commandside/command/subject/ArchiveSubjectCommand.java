package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArchiveSubjectCommand {

    @TargetAggregateIdentifier
    @NotNull
    private UUID id;
}
