package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.subjects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractAggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.ArchiveSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CreateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.UpdateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ResponseBadRequestException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@WriteApplicationBean
public class Subject extends AbstractAggregate {

    private String name;
    private String abbreviation;
    private String imageUrl;


    @CommandHandler
    public Subject(CreateSubjectCommand cmd) {
        AggregateLifecycle.apply(new SubjectCreatedEvent(
            UUID.randomUUID(),
            cmd.getName(),
            cmd.getAbbreviation(),
            Timestamp.from(Instant.now()))
        );
    }

    @CommandHandler
    public void handle(UpdateSubjectCommand cmd) {
        AggregateLifecycle.apply(new SubjectUpdatedEvent(
            getId(),
            cmd.getName() != null ? cmd.getName() : this.name,
            cmd.getAbbreviation() != null ? cmd.getAbbreviation() : this.abbreviation,
            cmd.getImageUrl() != null ? cmd.getImageUrl() : this.imageUrl));
    }

    @CommandHandler
    public void handle(ArchiveSubjectCommand cmd) {
        if (isArchived()) {
            throw new ResponseBadRequestException();
        }

        AggregateLifecycle.apply(new SubjectArchivedEvent(
            getId())
        );
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

    @EventSourcingHandler
    public void on(SubjectArchivedEvent event) {
        setArchived(true);
    }
}
