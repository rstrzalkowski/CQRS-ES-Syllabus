package pl.lodz.p.it.rstrzalkowski.syllabus;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.classes.SchoolClass;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass.ArchiveSchoolClassCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass.CheckSchoolClassExistenceCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass.CreateSchoolClassCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.schoolclass.UpdateSchoolClassCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.AssignStudentCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UnassignStudentCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.schoolclass.SchoolClassUniqueValuesService;
import pl.lodz.p.it.rstrzalkowski.syllabus.mock.MockSchoolClassUniqueValuesService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.SchoolClassUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.StudentAssignedToClassEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.StudentUnassignedFromClassEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ArchivedObjectException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.StudentAlreadyAssignedException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.StudentNotAssignedCommandExecutionException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.matches;
import static org.axonframework.test.matchers.Matchers.payloadsMatching;

public class SchoolClassAggregateTest {

    private AggregateTestFixture<SchoolClass> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(SchoolClass.class);
        SchoolClassUniqueValuesService schoolClassUniqueValuesService = new MockSchoolClassUniqueValuesService(null);

        fixture.registerInjectableResource(Mockito.mock(CommandGateway.class));
        fixture.registerInjectableResource(schoolClassUniqueValuesService);
    }

    @Test
    void testCreateSchoolClassCommand() {
        UUID schoolClassId = UUID.randomUUID();
        Integer level = 1;
        UUID teacherId = UUID.randomUUID();
        String shortName = "TIN";
        String fullName = "Technik informatyk";

        fixture.givenNoPriorActivity()
                .when(new CreateSchoolClassCommand(level, teacherId, shortName, fullName, schoolClassId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof SchoolClassCreatedEvent sce) {
                                return Objects.equals(sce.getId(), schoolClassId) &&
                                        Objects.equals(sce.getLevel(), level) &&
                                        Objects.equals(sce.getTeacherId(), teacherId) &&
                                        Objects.equals(sce.getName(), shortName) &&
                                        Objects.equals(sce.getFullName(), fullName);
                            }
                            return false;
                        })
                )));

    }

    @Test
    void testAssignStudentCommand() {
        UUID schoolClassId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        Integer level = 1;
        UUID teacherId = UUID.randomUUID();
        String shortName = "TIN";
        String fullName = "Technik informatyk";

        fixture.given(new SchoolClassCreatedEvent(schoolClassId, level, teacherId, shortName, fullName, Timestamp.from(Instant.now())))
                .when(new AssignStudentCommand(schoolClassId, studentId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof StudentAssignedToClassEvent sa) {
                                return Objects.equals(sa.getStudentId(), studentId) &&
                                        Objects.equals(sa.getSchoolClassId(), schoolClassId);
                            }
                            return false;
                        })
                )));

        fixture.given(new SchoolClassCreatedEvent(schoolClassId, level, teacherId, shortName, fullName, Timestamp.from(Instant.now())), new StudentAssignedToClassEvent(studentId, schoolClassId))
                .when(new AssignStudentCommand(schoolClassId, studentId))
                .expectException(StudentAlreadyAssignedException.class);
    }

    @Test
    void testUnassignStudentCommand() {
        UUID schoolClassId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        Integer level = 1;
        UUID teacherId = UUID.randomUUID();
        String shortName = "TIN";
        String fullName = "Technik informatyk";

        fixture.given(
                        new SchoolClassCreatedEvent(schoolClassId, level, teacherId, shortName, fullName, Timestamp.from(Instant.now())),
                        new StudentAssignedToClassEvent(studentId, schoolClassId)
                )
                .when(new UnassignStudentCommand(schoolClassId, studentId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof StudentUnassignedFromClassEvent su) {
                                return Objects.equals(su.getStudentId(), studentId) &&
                                        Objects.equals(su.getSchoolClassId(), schoolClassId);
                            }
                            return false;
                        })
                )));

        fixture.given(
                        new SchoolClassCreatedEvent(schoolClassId, level, teacherId, shortName, fullName, Timestamp.from(Instant.now()))
                )
                .when(new UnassignStudentCommand(schoolClassId, studentId))
                .expectException(StudentNotAssignedCommandExecutionException.class);
    }

    @Test
    void testUpdateSchoolClassCommand() {
        UUID schoolClassId = UUID.randomUUID();
        Integer level = 1;
        UUID teacherId = UUID.randomUUID();
        String shortName = "TIN";
        String fullName = "Technik informatyk";
        UUID newTeacherId = UUID.randomUUID();
        String newName = "TINE";
        String newFullName = "Technik informatyk ekonom";

        fixture.given(new SchoolClassCreatedEvent(schoolClassId, level, teacherId, shortName, fullName, Timestamp.from(Instant.now())))
                .when(new UpdateSchoolClassCommand(schoolClassId, newTeacherId, level, newName, newFullName))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof SchoolClassUpdatedEvent sc) {
                                return Objects.equals(sc.getId(), schoolClassId) &&
                                        Objects.equals(sc.getTeacherId(), newTeacherId) &&
                                        Objects.equals(sc.getLevel(), level) &&
                                        Objects.equals(sc.getName(), newName) &&
                                        Objects.equals(sc.getFullName(), newFullName);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testArchiveSchoolClassCommand() {
        UUID schoolClassId = UUID.randomUUID();
        Integer level = 1;
        UUID teacherId = UUID.randomUUID();
        String shortName = "Class1";
        String fullName = "First Class";

        fixture.givenCommands(new CreateSchoolClassCommand(level, teacherId, shortName, fullName, schoolClassId))
                .when(new ArchiveSchoolClassCommand(schoolClassId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new SchoolClassArchivedEvent(schoolClassId));

        fixture.givenCommands(new CreateSchoolClassCommand(level, teacherId, shortName, fullName, schoolClassId), new ArchiveSchoolClassCommand(schoolClassId))
                .when(new ArchiveSchoolClassCommand(schoolClassId))
                .expectException(ArchivedObjectException.class);
    }

    @Test
    void testCheckSchoolClassExistenceCommand() {
        UUID schoolClassId = UUID.randomUUID();
        Integer level = 1;
        UUID teacherId = UUID.randomUUID();
        String shortName = "Class1";
        String fullName = "First Class";

        fixture.givenCommands(new CreateSchoolClassCommand(level, teacherId, shortName, fullName, schoolClassId))
                .when(new CheckSchoolClassExistenceCommand(schoolClassId))
                .expectSuccessfulHandlerExecution();

        fixture.given(new SchoolClassCreatedEvent(schoolClassId, level, teacherId, shortName, fullName, Timestamp.from(Instant.now())), new SchoolClassArchivedEvent(schoolClassId))
                .when(new CheckSchoolClassExistenceCommand(schoolClassId))
                .expectException(ArchivedObjectException.class);
    }
}