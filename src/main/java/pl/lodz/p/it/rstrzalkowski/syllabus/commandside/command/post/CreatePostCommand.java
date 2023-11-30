package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatePostCommand {

    @NotNull
    @Length(min = 1, max = 40)
    private String title;

    @NotNull
    @Length(min = 1, max = 2000)
    private String content;

    @NotNull
    @TargetAggregateIdentifier
    private UUID realisationId;

    private UUID teacherId;
}
