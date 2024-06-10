package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProfileImageCommand {

    @TargetAggregateIdentifier
    private UUID id;

    private String imageUrl;
}
