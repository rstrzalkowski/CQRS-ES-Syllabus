package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckSubjectExistenceCommand {

    @TargetAggregateIdentifier
    @NotNull
    private UUID subjectId;
}