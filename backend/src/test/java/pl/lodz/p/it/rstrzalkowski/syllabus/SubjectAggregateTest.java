package pl.lodz.p.it.rstrzalkowski.syllabus;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CreateSubjectCommand;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SubjectAggregateTest {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private EventStore eventStore;

    private UUID subjectId;

    @BeforeEach
    void setUp() {
        subjectId = UUID.randomUUID();
    }

    @Test
    void testCreateSubject() {
        String name = "Mathematics";
        String abbreviation = "MATH";

        commandGateway.sendAndWait(new CreateSubjectCommand(name, abbreviation));
        commandGateway.sendAndWait(new CreateSubjectCommand(name, abbreviation));
    }
}