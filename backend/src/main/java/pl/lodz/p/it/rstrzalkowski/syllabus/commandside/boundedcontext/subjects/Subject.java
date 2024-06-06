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
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.UpdateSubjectImageCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectNameService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectImageUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectNameUpdateFailedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ArchivedObjectException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

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
    public Subject(CreateSubjectCommand cmd, SubjectNameService subjectNameService) {
        UUID subjectId = UUID.randomUUID();

        subjectNameService.lockSubjectName(cmd.getName(), subjectId);

        AggregateLifecycle.apply(new SubjectCreatedEvent(
                subjectId,
                cmd.getName(),
                cmd.getAbbreviation(),
                Timestamp.from(Instant.now()))
        );
    }

    @CommandHandler
    public void handle(UpdateSubjectCommand cmd, SubjectNameService subjectNameService) {
        if (isArchived()) {
            if (!Objects.equals(cmd.getName(), name)) {
                AggregateLifecycle.apply(new SubjectNameUpdateFailedEvent(cmd.getName()));
            }
            throw new ArchivedObjectException();
        }

        if (!Objects.equals(cmd.getName(), name)) {
            subjectNameService.updateSubjectName(cmd.getName(), getId());
        }

        AggregateLifecycle.apply(new SubjectUpdatedEvent(
                getId(),
                cmd.getName() != null ? cmd.getName() : this.name,
                cmd.getAbbreviation() != null ? cmd.getAbbreviation() : this.abbreviation,
                cmd.getImageUrl() != null ? cmd.getImageUrl() : this.imageUrl));
    }

    @CommandHandler
    public void handle(ArchiveSubjectCommand cmd, SubjectNameService subjectNameService) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        subjectNameService.releaseSubjectName(getId());

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
