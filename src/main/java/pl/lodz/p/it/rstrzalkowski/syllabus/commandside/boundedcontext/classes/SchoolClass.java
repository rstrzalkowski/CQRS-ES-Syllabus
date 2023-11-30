package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.classes;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractAggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass.CreateSchoolClassCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@WriteApplicationBean
public class SchoolClass extends AbstractAggregate {

    private UUID levelId;
    private UUID teacherId;
    private String name;
    private String fullName;

    //AddStudentCommand
    //RemoveStudentCommand

    @CommandHandler
    public SchoolClass(CreateSchoolClassCommand cmd) {
        AggregateLifecycle.apply(new SchoolClassCreatedEvent(
                UUID.randomUUID(),
                cmd.getLevelId(),
                cmd.getTeacherId(),
                cmd.getShortName(),
                cmd.getFullName()));
    }

    @EventSourcingHandler
    public void on(SchoolClassCreatedEvent event) {
        setId(event.getId());
        this.levelId = event.getLevelId();
        this.teacherId = event.getTeacherId();
        this.name = event.getName();
        this.fullName = event.getFullName();
    }
}
