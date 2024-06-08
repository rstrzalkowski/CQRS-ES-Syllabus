package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.classes;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractAggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.classes.entity.Student;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass.ArchiveSchoolClassCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass.CreateSchoolClassCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass.UpdateSchoolClassCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.AssignStudentCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UnassignStudentCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.StudentAssignedToClassEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.StudentUnassignedFromClassEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ArchivedObjectException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.StudentAlreadyAssignedException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.StudentNotAssignedCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Aggregate(snapshotTriggerDefinition = "syllabusSnapshotTriggerDefinition")
@NoArgsConstructor
@AllArgsConstructor
@WriteApplicationBean
public class SchoolClass extends AbstractAggregate {

    private UUID teacherId;
    private Integer level;
    private String name;
    private String fullName;
    private List<Student> students;

    @CommandHandler
    public SchoolClass(CreateSchoolClassCommand cmd) {
        AggregateLifecycle.apply(new SchoolClassCreatedEvent(
                UUID.randomUUID(),
                cmd.getLevel(),
                cmd.getTeacherId(),
                cmd.getShortName(),
                cmd.getFullName(),
                Timestamp.from(Instant.now())));
    }

    @CommandHandler
    public void handle(AssignStudentCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        Student student = new Student(cmd.getStudentId());

        if (students.contains(student)) {
            throw new StudentAlreadyAssignedException();
        }

        AggregateLifecycle.apply(new StudentAssignedToClassEvent(
                cmd.getStudentId(),
                cmd.getSchoolClassId()
        ));
    }

    @CommandHandler
    public void handle(UnassignStudentCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        Student student = new Student(cmd.getStudentId());

        if (!students.contains(student)) {
            throw new StudentNotAssignedCommandExecutionException();
        }

        AggregateLifecycle.apply(new StudentUnassignedFromClassEvent(
                cmd.getStudentId(),
                cmd.getSchoolClassId()
        ));
    }

    @CommandHandler
    public void handle(UpdateSchoolClassCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        AggregateLifecycle.apply(new SchoolClassUpdatedEvent(
                getId(),
                cmd.getTeacherId() != null ? cmd.getTeacherId() : this.teacherId,
                cmd.getLevel() != null ? cmd.getLevel() : this.level,
                cmd.getName() != null ? cmd.getName() : this.name,
                cmd.getFullName() != null ? cmd.getFullName() : this.fullName));
    }

    @CommandHandler
    public void handle(ArchiveSchoolClassCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        AggregateLifecycle.apply(new SchoolClassArchivedEvent(
                cmd.getId())
        );
    }

    @EventSourcingHandler
    public void on(SchoolClassCreatedEvent event) {
        setId(event.getId());
        this.level = event.getLevel();
        this.teacherId = event.getTeacherId();
        this.name = event.getName();
        this.fullName = event.getFullName();
        this.students = new ArrayList<>();
    }

    @EventSourcingHandler
    public void on(SchoolClassUpdatedEvent event) {
        this.level = event.getLevel();
        this.teacherId = event.getTeacherId();
        this.name = event.getName();
        this.fullName = event.getFullName();
    }

    @EventSourcingHandler
    public void on(StudentAssignedToClassEvent event) {
        this.students.add(new Student(event.getStudentId()));
    }

    @EventSourcingHandler
    public void on(StudentUnassignedFromClassEvent event) {
        this.students.remove(new Student(event.getStudentId()));
    }

    @EventSourcingHandler
    public void on(SchoolClassArchivedEvent event) {
        setArchived(true);
    }
}
