package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractAggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.RegisterCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
@WriteApplicationBean
public class User extends AbstractAggregate {

    private String email;

    private String firstName;

    private String lastName;

    private String personalId;


    @CommandHandler
    public User(RegisterCommand cmd) {
        //TODO check if email is unique
        AggregateLifecycle.apply(new UserCreatedEvent(
                UUID.randomUUID(),
                cmd.getEmail(),
                cmd.getFirstName(),
                cmd.getLastName(),
                cmd.getPersonalId()));
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent event) {
        setId(event.getId());
        this.email = event.getEmail();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.personalId = event.getPersonalId();
    }
}
