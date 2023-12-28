package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.grades;

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
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.ActivityCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.GradeCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@WriteApplicationBean
public class Activity extends AbstractAggregate {

    private UUID teacherId;

    private LocalDateTime date;

    private Integer weight;

    private List<Grade> grades;


    @CommandHandler
    public void handle(CreateGradeCommand cmd) {
        AggregateLifecycle.apply(new GradeCreatedEvent(
            UUID.randomUUID(),
            cmd.getActivityId(),
            cmd.getStudentId(),
            cmd.getTeacherId(),
            LocalDateTime.now(),
            cmd.getComment(),
            cmd.getValue(),
            Timestamp.from(Instant.now()))
        );
    }

    @EventSourcingHandler
    public void on(GradeCreatedEvent event) {
        this.grades.add(new Grade(
            event.getId(),
            event.getTeacherId(),
            event.getStudentId(),
            event.getValue(),
            event.getDate(),
            event.getComment()));
    }

    @EventSourcingHandler
    public void on(ActivityCreatedEvent event) {
        setId(event.getId());
        this.date = event.getDate();
        this.weight = event.getWeight();
        this.teacherId = event.getTeacherId();
        this.grades = new ArrayList<>();
    }
}
