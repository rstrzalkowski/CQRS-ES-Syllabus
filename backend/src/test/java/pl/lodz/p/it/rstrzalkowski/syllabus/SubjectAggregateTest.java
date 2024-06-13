package pl.lodz.p.it.rstrzalkowski.syllabus;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.subjects.Subject;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.ArchiveSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CheckSubjectExistenceCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CreateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.UpdateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectUniqueValuesService;
import pl.lodz.p.it.rstrzalkowski.syllabus.mock.MockSubjectUniqueValuesService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SubjectUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ArchivedObjectException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.matches;
import static org.axonframework.test.matchers.Matchers.payloadsMatching;

public class SubjectAggregateTest {

    private AggregateTestFixture<Subject> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(Subject.class);
        SubjectUniqueValuesService subjectUniqueValuesService = new MockSubjectUniqueValuesService(null);
        fixture.registerInjectableResource(subjectUniqueValuesService);
    }

    @Test
    void testCreateSubjectCommand() {
        UUID subjectId = UUID.randomUUID();
        String name = "Mathematics";
        String abbreviation = "MATH";

        fixture.givenNoPriorActivity()
                .when(new CreateSubjectCommand(name, abbreviation, subjectId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof SubjectCreatedEvent sce) {
                                return Objects.equals(sce.getId(), subjectId) &&
                                        Objects.equals(sce.getAbbreviation(), abbreviation) &&
                                        Objects.equals(sce.getName(), name);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testUpdateSubjectCommand() {
        UUID subjectId = UUID.randomUUID();
        String name = "Math";
        String abbreviation = "MTH";
        String newName = "Advanced Math";

        fixture.givenCommands(new CreateSubjectCommand(name, abbreviation, subjectId))
                .when(new UpdateSubjectCommand(subjectId, newName, null, null))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof SubjectUpdatedEvent sce) {
                                return Objects.equals(sce.getId(), subjectId) &&
                                        Objects.equals(sce.getAbbreviation(), abbreviation) &&
                                        Objects.equals(sce.getName(), newName);
                            }
                            return false;
                        })
                )));

        fixture.givenCommands(new CreateSubjectCommand(name, abbreviation, subjectId))
                .when(new UpdateSubjectCommand(subjectId, newName, null, null))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof SubjectUpdatedEvent sce) {
                                return Objects.equals(sce.getId(), subjectId) &&
                                        Objects.equals(sce.getAbbreviation(), abbreviation) &&
                                        Objects.equals(sce.getName(), newName);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testArchiveSubjectCommand() {
        UUID subjectId = UUID.randomUUID();
        String name = "Math";
        String abbreviation = "MTH";

        fixture.given(new SubjectCreatedEvent(subjectId, name, abbreviation, Timestamp.from(Instant.now())))
                .when(new ArchiveSubjectCommand(subjectId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new SubjectArchivedEvent(subjectId));

        fixture.given(new SubjectCreatedEvent(subjectId, name, abbreviation, Timestamp.from(Instant.now())), new SubjectArchivedEvent(subjectId))
                .when(new ArchiveSubjectCommand(subjectId))
                .expectException(ArchivedObjectException.class);
    }

    @Test
    void testCheckSubjectExistenceCommand() {
        UUID subjectId = UUID.randomUUID();
        String name = "Math";
        String abbreviation = "MTH";

        fixture.givenCommands(new CreateSubjectCommand(name, abbreviation, subjectId))
                .when(new CheckSubjectExistenceCommand(subjectId))
                .expectSuccessfulHandlerExecution();

        fixture.given(new SubjectCreatedEvent(subjectId, name, abbreviation, Timestamp.from(Instant.now())), new SubjectArchivedEvent(subjectId))
                .when(new CheckSubjectExistenceCommand(subjectId))
                .expectException(ArchivedObjectException.class);
    }
}