package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.subjects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractAggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.grades.entity.Grade;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.grade.CreateGradeCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CreateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.GradeCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.List;
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

    @EventSourcingHandler
    public void on(SubjectCreatedEvent event) {
        setId(event.getId());
        this.name = event.getName();
        this.abbreviation = event.getAbbreviation();
    }
}
