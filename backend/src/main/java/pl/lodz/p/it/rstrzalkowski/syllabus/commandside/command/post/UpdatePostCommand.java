package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePostCommand {

    @TargetAggregateIdentifier
    private UUID realisationId;

    private UUID postId;

    private String title;

    private String content;
}
