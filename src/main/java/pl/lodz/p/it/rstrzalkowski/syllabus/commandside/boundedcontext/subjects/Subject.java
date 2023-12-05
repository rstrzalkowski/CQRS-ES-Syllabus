package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.subjects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractAggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CreateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.UpdateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.subject.SubjectNotFoundCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@WriteApplicationBean
public class Subject extends AbstractAggregate {

    private String name;
    private String abbreviation;


    @CommandHandler
    public Subject(CreateSubjectCommand cmd) {
        AggregateLifecycle.apply(new SubjectCreatedEvent(
                UUID.randomUUID(),
                cmd.getName(),
                cmd.getAbbreviation()));
    }

    @CommandHandler
    public Subject(UpdateSubjectCommand cmd) {
        if (getId() == null) { // Dla agregatu nie został zaaplikowany SubjectCreatedEvent, więc przedmiot o danym ID nie istnieje
            throw new SubjectNotFoundCommandExecutionException();
        }

        AggregateLifecycle.apply(new SubjectUpdatedEvent(
                UUID.randomUUID(),
                cmd.getName() != null ? cmd.getName() : this.name,
                cmd.getAbbreviation() != null ? cmd.getAbbreviation() : this.abbreviation));
    }

    @EventSourcingHandler
    public void on(SubjectCreatedEvent event) {
        setId(event.getId());
        this.name = event.getName();
        this.abbreviation = event.getAbbreviation();
    }

    @EventSourcingHandler
    public void on(SubjectUpdatedEvent event) {
        this.name = event.getName();
        this.abbreviation = event.getAbbreviation();
    }
}
