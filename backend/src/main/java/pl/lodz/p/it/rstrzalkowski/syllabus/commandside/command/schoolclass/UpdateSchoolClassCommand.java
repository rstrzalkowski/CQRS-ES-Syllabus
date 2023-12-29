package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateSchoolClassCommand {

    @TargetAggregateIdentifier
    private UUID id;

    private UUID teacherId;

    private Integer level;

    private String name;

    private String fullName;
}
