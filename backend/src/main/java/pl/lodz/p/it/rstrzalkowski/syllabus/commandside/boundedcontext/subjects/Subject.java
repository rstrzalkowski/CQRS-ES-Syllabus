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
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CheckSubjectExistenceCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CreateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.UpdateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.UpdateSubjectImageCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectUniqueValuesService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectImageUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ArchivedObjectException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.sql.Timestamp;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Aggregate(snapshotTriggerDefinition = "syllabusSnapshotTriggerDefinition")
@NoArgsConstructor
@AllArgsConstructor
@WriteApplicationBean
public class Subject extends AbstractAggregate {

    private String name;
    private String abbreviation;
    private String imageUrl;


    @CommandHandler
    public Subject(CreateSubjectCommand cmd, SubjectUniqueValuesService subjectUniqueValuesService) {
        subjectUniqueValuesService.lockSubjectName(cmd.getName(), cmd.getSubjectId());

        AggregateLifecycle.apply(new SubjectCreatedEvent(
                cmd.getSubjectId(),
                cmd.getName(),
                cmd.getAbbreviation(),
                Timestamp.from(Instant.now()))
        );
    }

    @CommandHandler
    public void handle(UpdateSubjectCommand cmd, SubjectUniqueValuesService subjectUniqueValuesService) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        subjectUniqueValuesService.updateSubjectName(cmd.getName() != null ? cmd.getName() : this.name, getId());

        AggregateLifecycle.apply(new SubjectUpdatedEvent(
                getId(),
                cmd.getName() != null ? cmd.getName() : this.name,
                cmd.getAbbreviation() != null ? cmd.getAbbreviation() : this.abbreviation,
                cmd.getImageUrl() != null ? cmd.getImageUrl() : this.imageUrl));
    }

    @CommandHandler
    public void handle(ArchiveSubjectCommand cmd, SubjectUniqueValuesService subjectUniqueValuesService) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        subjectUniqueValuesService.releaseValues(getId());

        AggregateLifecycle.apply(new SubjectArchivedEvent(
                getId())
        );
    }

    @CommandHandler
    public void handle(UpdateSubjectImageCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        AggregateLifecycle.apply(new SubjectImageUpdatedEvent(
                getId(),
                cmd.getImageUrl())
        );
    }

    @CommandHandler
    public void handle(CheckSubjectExistenceCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }
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
    public void on(SubjectImageUpdatedEvent event) {
        this.imageUrl = event.getImageUrl();
    }

    @EventSourcingHandler
    public void on(SubjectArchivedEvent event) {
        setArchived(true);
    }
}
