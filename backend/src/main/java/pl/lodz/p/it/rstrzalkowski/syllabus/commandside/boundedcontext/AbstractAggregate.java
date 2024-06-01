package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext;

import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.UUID;

@Data
@WriteApplicationBean
public abstract class AbstractAggregate {

    @AggregateIdentifier
    private UUID id;
    private boolean archived;
}
