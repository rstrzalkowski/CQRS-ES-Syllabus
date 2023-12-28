package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateSubjectCommand {

    @TargetAggregateIdentifier
    private UUID subjectId;

    private String name;

    private String abbreviation;

    private String imageUrl;
}
