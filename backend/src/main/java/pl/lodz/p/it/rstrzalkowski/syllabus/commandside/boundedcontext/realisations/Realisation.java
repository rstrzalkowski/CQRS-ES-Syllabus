package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractAggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.entity.Activity;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.entity.Grade;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.entity.Post;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.ArchiveActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.CreateActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.UpdateActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.grade.CreateGradeCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.ArchivePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.CreatePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.UpdatePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.ArchiveRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.CreateRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.UpdateRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.ActivityArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.ActivityCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.ActivityUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.GradeCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.GradeUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ArchivedObjectException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.activity.NoSuchActivityCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.grade.NoSuchGradeCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.post.NoSuchPostCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Aggregate(snapshotTriggerDefinition = "syllabusSnapshotTriggerDefinition")
@NoArgsConstructor
@AllArgsConstructor
@WriteApplicationBean
public class Realisation extends AbstractAggregate {

    private UUID subjectId;
    private UUID schoolClassId;
    private UUID teacherId;
    private Year year;

    private List<Post> posts;
    private List<Activity> activities;


    @CommandHandler
    public Realisation(CreateRealisationCommand cmd) {
        AggregateLifecycle.apply(new RealisationCreatedEvent(
                UUID.randomUUID(),
                cmd.getSubjectId(),
                cmd.getClassId(),
                cmd.getTeacherId(),
                cmd.getYear(),
                Timestamp.from(Instant.now()))
        );
    }

    @CommandHandler
    public void handle(CreatePostCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        AggregateLifecycle.apply(new PostCreatedEvent(
                UUID.randomUUID(),
                cmd.getRealisationId(),
                cmd.getTeacherId(),
                cmd.getTitle(),
                cmd.getContent(),
                Timestamp.from(Instant.now()))
        );
    }

    @CommandHandler
    public void handle(CreateActivityCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        AggregateLifecycle.apply(new ActivityCreatedEvent(
                UUID.randomUUID(),
                cmd.getRealisationId(),
                cmd.getTeacherId(),
                cmd.getWeight(),
                cmd.getDate(),
                cmd.getDescription(),
                cmd.getName(),
                Timestamp.from(Instant.now()))
        );
    }

    @CommandHandler
    public void handle(CreateGradeCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        Activity activity = activities.stream()
                .filter(a -> Objects.equals(a.getId(), cmd.getActivityId()))
                .filter(a -> !a.isArchived())
                .findFirst()
                .orElseThrow(NoSuchActivityCommandExecutionException::new);

        Grade currentGrade = activity.getGrades().stream()
                .filter(g -> Objects.equals(g.getStudentId(), cmd.getStudentId()))
                .filter(g -> !g.isArchived())
                .findFirst().orElse(null);

        if (currentGrade != null) {
            AggregateLifecycle.apply(new GradeUpdatedEvent(
                    currentGrade.getId(),
                    activity.getId(),
                    LocalDateTime.now(),
                    cmd.getComment(),
                    cmd.getValue())
            );
            return;
        }

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

    @CommandHandler
    public void handle(UpdatePostCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        Post post = posts.stream()
                .filter(p -> Objects.equals(p.getId(), cmd.getPostId()))
                .filter(p -> !p.isArchived())
                .findFirst()
                .orElseThrow(NoSuchPostCommandExecutionException::new);

        AggregateLifecycle.apply(new PostUpdatedEvent(
                post.getId(),
                cmd.getTitle() != null ? cmd.getTitle() : post.getTitle(),
                cmd.getContent() != null ? cmd.getContent() : post.getContent(),
                Timestamp.from(Instant.now()))
        );
    }

    @CommandHandler
    public void handle(UpdateRealisationCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        AggregateLifecycle.apply(new RealisationUpdatedEvent(
                cmd.getId(),
                cmd.getYear() != null ? cmd.getYear() : this.year,
                cmd.getTeacherId() != null ? cmd.getTeacherId() : this.teacherId)
        );
    }

    @CommandHandler
    public void handle(UpdateActivityCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        Activity activity = activities.stream()
                .filter(a -> Objects.equals(a.getId(), cmd.getActivityId()))
                .filter(a -> !a.isArchived())
                .findFirst()
                .orElseThrow(NoSuchActivityCommandExecutionException::new);

        AggregateLifecycle.apply(new ActivityUpdatedEvent(
                activity.getId(),
                cmd.getWeight() != null ? cmd.getWeight() : activity.getWeight(),
                cmd.getDate() != null ? cmd.getDate() : activity.getDate(),
                cmd.getDescription() != null ? cmd.getDescription() : activity.getDescription(),
                cmd.getName() != null ? cmd.getName() : activity.getName())
        );
    }

    @CommandHandler
    public void handle(ArchiveRealisationCommand cmd) {
        if (isArchived()) {
            throw new ArchivedObjectException();
        }

        AggregateLifecycle.apply(new RealisationArchivedEvent(
                cmd.getId())
        );
    }

    @CommandHandler
    public void handle(ArchivePostCommand cmd) {
        boolean postExists = posts.stream()
                .anyMatch(p -> Objects.equals(p.getId(), cmd.getId()) && !p.isArchived());

        if (isArchived() || !postExists) {
            throw new ArchivedObjectException();
        }

        AggregateLifecycle.apply(new PostArchivedEvent(
                cmd.getId())
        );
    }

    @CommandHandler
    public void handle(ArchiveActivityCommand cmd) {
        boolean activityExists = activities.stream()
                .anyMatch(a -> Objects.equals(a.getId(), cmd.getId()) && !a.isArchived());

        if (isArchived() || !activityExists) {
            throw new ArchivedObjectException();
        }

        AggregateLifecycle.apply(new ActivityArchivedEvent(
                cmd.getId())
        );
    }

    @EventSourcingHandler
    public void on(RealisationCreatedEvent event) {
        setId(event.getId());
        this.subjectId = event.getSubjectId();
        this.schoolClassId = event.getSchoolClassId();
        this.teacherId = event.getTeacherId();
        this.year = event.getYear();
        this.activities = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    @EventSourcingHandler
    public void on(PostCreatedEvent event) {
        Post post = new Post(
                event.getTeacherId(),
                event.getTitle(),
                event.getContent()
        );

        post.setId(event.getId());
        this.posts.add(post);
    }

    @EventSourcingHandler
    public void on(ActivityCreatedEvent event) {
        Activity activity = new Activity(
                event.getTeacherId(),
                event.getName(),
                event.getDate(),
                event.getWeight(),
                event.getDescription()
        );

        activity.setGrades(new ArrayList<>());
        activity.setId(event.getId());

        this.activities.add(activity);
    }

    @EventSourcingHandler
    public void on(GradeCreatedEvent event) {
        Activity activity = activities.stream()
                .filter(a -> Objects.equals(a.getId(), event.getActivityId()))
                .filter(a -> !a.isArchived())
                .findFirst()
                .orElseThrow(NoSuchActivityCommandExecutionException::new);

        Grade grade = new Grade(
                event.getTeacherId(),
                event.getStudentId(),
                event.getValue(),
                event.getDate(),
                event.getComment()
        );

        grade.setId(event.getId());

        activity.getGrades().add(grade);
    }

    @EventSourcingHandler
    public void on(RealisationUpdatedEvent event) {
        this.teacherId = event.getTeacherId();
        this.year = event.getYear();
    }

    @EventSourcingHandler
    public void on(GradeUpdatedEvent event) {
        Activity activity = activities.stream()
                .filter(a -> Objects.equals(a.getId(), event.getActivityId()))
                .filter(a -> !a.isArchived())
                .findFirst()
                .orElseThrow(NoSuchActivityCommandExecutionException::new);

        Grade grade = activity.getGrades().stream()
                .filter(g -> Objects.equals(g.getId(), event.getId()))
                .filter(g -> !g.isArchived())
                .findFirst()
                .orElseThrow(NoSuchGradeCommandExecutionException::new);

        grade.setDate(event.getDate());
        grade.setValue(event.getValue());
        grade.setComment(event.getComment());
    }

    @EventSourcingHandler
    public void on(ActivityUpdatedEvent event) {
        Activity activity = activities.stream()
                .filter(a -> Objects.equals(a.getId(), event.getId()))
                .filter(a -> !a.isArchived())
                .findFirst()
                .orElseThrow(NoSuchActivityCommandExecutionException::new);

        activity.setDate(event.getDate());
        activity.setWeight(event.getWeight());
        activity.setName(event.getName());
        activity.setDescription(event.getDescription());
    }

    @EventSourcingHandler
    public void on(PostUpdatedEvent event) {
        Post post = posts.stream()
                .filter(p -> Objects.equals(p.getId(), event.getId()))
                .filter(p -> !p.isArchived())
                .findFirst()
                .orElseThrow(NoSuchPostCommandExecutionException::new);

        post.setContent(event.getContent());
        post.setTitle(event.getTitle());
    }

    @EventSourcingHandler
    public void on(RealisationArchivedEvent event) {
        setArchived(true);
    }

    @EventSourcingHandler
    public void on(ActivityArchivedEvent event) {
        activities.stream()
                .filter(a -> a.getId() == event.getId() && !a.isArchived())
                .findFirst()
                .ifPresent(activity -> activity.setArchived(true));
    }

    @EventSourcingHandler
    public void on(PostArchivedEvent event) {
        posts.stream()
                .filter(p -> p.getId() == event.getId() && !p.isArchived())
                .findFirst()
                .ifPresent(post -> post.setArchived(true));
    }
}
