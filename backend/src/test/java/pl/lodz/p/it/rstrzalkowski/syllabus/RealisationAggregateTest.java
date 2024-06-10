package pl.lodz.p.it.rstrzalkowski.syllabus;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.Realisation;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.ArchiveActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.CreateActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.UpdateActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.grade.CreateGradeCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.ArchivePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.CreatePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.CreateRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.UpdateRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation.RealisationUniqueValuesService;
import pl.lodz.p.it.rstrzalkowski.syllabus.mock.MockRealisationUniqueValuesService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.ActivityArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.ActivityCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.ActivityUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.GradeCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationUpdatedEvent;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Objects;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.matches;
import static org.axonframework.test.matchers.Matchers.payloadsMatching;

public class RealisationAggregateTest {

    private AggregateTestFixture<Realisation> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(Realisation.class);
        RealisationUniqueValuesService realisationUniqueValuesService = new MockRealisationUniqueValuesService(null);
        fixture.registerInjectableResource(Mockito.mock(CommandGateway.class));
        fixture.registerInjectableResource(realisationUniqueValuesService);
    }

    @Test
    void testCreateRealisationCommand() {
        UUID realisationId = UUID.randomUUID();
        UUID subjectId = UUID.randomUUID();
        UUID classId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        Year year = Year.now();

        fixture.givenNoPriorActivity()
                .when(new CreateRealisationCommand(year, teacherId, subjectId, classId, realisationId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof RealisationCreatedEvent rce) {
                                return Objects.equals(rce.getId(), realisationId) &&
                                        Objects.equals(rce.getSubjectId(), subjectId) &&
                                        Objects.equals(rce.getSchoolClassId(), classId) &&
                                        Objects.equals(rce.getTeacherId(), teacherId) &&
                                        Objects.equals(rce.getYear(), year);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testCreatePostCommand() {
        UUID realisationId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        String title = "Test Post";
        String content = "Test Content";

        fixture.given(new RealisationCreatedEvent(realisationId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Year.now(), Timestamp.from(Instant.now())))
                .when(new CreatePostCommand(title, content, realisationId, teacherId, postId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof PostCreatedEvent pce) {
                                return Objects.equals(pce.getId(), postId) &&
                                        Objects.equals(pce.getRealisationId(), realisationId) &&
                                        Objects.equals(pce.getTeacherId(), teacherId) &&
                                        Objects.equals(pce.getTitle(), title) &&
                                        Objects.equals(pce.getContent(), content);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testCreateActivityCommand() {
        UUID realisationId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        int weight = 10;
        LocalDateTime date = LocalDateTime.now();
        String description = "Test Description";
        String name = "Test Activity";

        fixture.given(new RealisationCreatedEvent(realisationId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Year.now(), Timestamp.from(Instant.now())))
                .when(new CreateActivityCommand(name, weight, description, date, realisationId, teacherId, activityId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof ActivityCreatedEvent ace) {
                                return Objects.equals(ace.getId(), activityId) &&
                                        Objects.equals(ace.getRealisationId(), realisationId) &&
                                        Objects.equals(ace.getTeacherId(), teacherId) &&
                                        Objects.equals(ace.getWeight(), weight) &&
                                        Objects.equals(ace.getDate(), date) &&
                                        Objects.equals(ace.getDescription(), description) &&
                                        Objects.equals(ace.getName(), name);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testCreateGradeCommand() {
        UUID realisationId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();
        UUID gradeId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        int value = 5;
        String comment = "Good work";

        fixture.given(
                        new RealisationCreatedEvent(realisationId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Year.now(), Timestamp.from(Instant.now())),
                        new ActivityCreatedEvent(activityId, realisationId, teacherId, 10, LocalDateTime.now(), "Test Description", "Test Activity", Timestamp.from(Instant.now()))
                )
                .when(new CreateGradeCommand(value, studentId, teacherId, comment, activityId, realisationId, gradeId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof GradeCreatedEvent gce) {
                                return Objects.equals(gce.getId(), gradeId) &&
                                        Objects.equals(gce.getActivityId(), activityId) &&
                                        Objects.equals(gce.getStudentId(), studentId) &&
                                        Objects.equals(gce.getTeacherId(), teacherId) &&
                                        Objects.equals(gce.getValue(), value) &&
                                        Objects.equals(gce.getComment(), comment);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testUpdateRealisationCommand() {
        UUID realisationId = UUID.randomUUID();
        UUID subjectId = UUID.randomUUID();
        UUID classId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        Year year = Year.now();
        UUID newTeacherId = UUID.randomUUID();

        fixture.given(new RealisationCreatedEvent(realisationId, subjectId, classId, teacherId, year, Timestamp.from(Instant.now())))
                .when(new UpdateRealisationCommand(realisationId, null, newTeacherId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof RealisationUpdatedEvent rue) {
                                return Objects.equals(rue.getId(), realisationId) &&
                                        Objects.equals(rue.getTeacherId(), newTeacherId) &&
                                        Objects.equals(rue.getYear(), year);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testUpdateActivityCommand() {
        UUID realisationId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        int weight = 10;
        LocalDateTime date = LocalDateTime.now();
        String description = "Test Description";
        String name = "Test Activity";
        int newWeight = 20;
        LocalDateTime newDate = LocalDateTime.now().plusDays(1);
        String newName = "Updated Activity";

        fixture.given(
                        new RealisationCreatedEvent(realisationId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Year.now(), Timestamp.from(Instant.now())),
                        new ActivityCreatedEvent(activityId, realisationId, teacherId, weight, date, description, name, Timestamp.from(Instant.now()))
                )
                .when(new UpdateActivityCommand(realisationId, activityId, newName, newWeight, description, newDate))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof ActivityUpdatedEvent aue) {
                                return Objects.equals(aue.getId(), activityId) &&
                                        Objects.equals(aue.getWeight(), newWeight) &&
                                        Objects.equals(aue.getDate(), newDate) &&
                                        Objects.equals(aue.getDescription(), description) &&
                                        Objects.equals(aue.getName(), newName);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testArchiveActivityCommand() {
        UUID realisationId = UUID.randomUUID();
        UUID activityId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        int weight = 10;
        LocalDateTime date = LocalDateTime.now();
        String description = "Test Description";
        String name = "Test Activity";

        fixture.given(
                        new RealisationCreatedEvent(realisationId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Year.now(), Timestamp.from(Instant.now())),
                        new ActivityCreatedEvent(activityId, realisationId, teacherId, weight, date, description, name, Timestamp.from(Instant.now()))
                )
                .when(new ArchiveActivityCommand(realisationId, activityId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof ActivityArchivedEvent aae) {
                                return Objects.equals(aae.getId(), activityId);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testArchivePostCommand() {
        UUID realisationId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        String title = "Test Post";
        String content = "Test Content";

        fixture.given(
                        new RealisationCreatedEvent(realisationId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Year.now(), Timestamp.from(Instant.now())),
                        new PostCreatedEvent(postId, realisationId, teacherId, title, content, Timestamp.from(Instant.now()))
                )
                .when(new ArchivePostCommand(realisationId, postId))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof PostArchivedEvent pae) {
                                return Objects.equals(pae.getId(), postId);
                            }
                            return false;
                        })
                )));
    }
}