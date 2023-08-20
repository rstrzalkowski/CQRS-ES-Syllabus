package pl.lodz.p.it.rstrzalkowski.syllabus.aggregate;

import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@Data
public abstract class AbstractAggregate {
    @AggregateIdentifier
    private UUID id;
    private boolean archived;
}
