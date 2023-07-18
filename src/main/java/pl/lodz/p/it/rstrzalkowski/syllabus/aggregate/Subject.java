package pl.lodz.p.it.rstrzalkowski.syllabus.aggregate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.command.CreateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.event.SubjectCreatedEvent;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends AbstractAggregate {
    private String name;
    private String abbreviation;
    private List<Subject> subjects;

    @CommandHandler
    public Subject(CreateSubjectCommand cmd) {
        AggregateLifecycle.apply(new SubjectCreatedEvent(UUID.randomUUID(), cmd.getName(), cmd.getAbbreviation()));
    }

    @EventSourcingHandler
    public void on(SubjectCreatedEvent event) {
        setId(event.id());
        this.name = event.name();
        this.abbreviation = event.abbreviation();
    }
}
