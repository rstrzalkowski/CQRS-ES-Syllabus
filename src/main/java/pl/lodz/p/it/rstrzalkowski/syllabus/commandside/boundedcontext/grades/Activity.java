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
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.GradeCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@WriteApplicationBean
public class Activity extends AbstractAggregate {

    private UUID teacherId;

    private List<Grade> grades;


    @CommandHandler
    public void handle(CreateGradeCommand cmd) {
        AggregateLifecycle.apply(new GradeCreatedEvent(
                UUID.randomUUID(),
                cmd.getActivityId(),
                cmd.getStudentId(),
                cmd.getValue()));
    }

    @EventSourcingHandler
    public void on(GradeCreatedEvent event) {
        //this.grades.add(new Grade(event.getId()));
    }
}
