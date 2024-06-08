package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDescriptionCommand {
    
    @TargetAggregateIdentifier
    private UUID userId;

    @NotNull
    @Length(max = 1024)
    private String description;
}
