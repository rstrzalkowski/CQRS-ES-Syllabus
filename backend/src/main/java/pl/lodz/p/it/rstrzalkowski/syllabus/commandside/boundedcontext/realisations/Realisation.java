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
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.CreateActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.UpdateActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.grade.CreateGradeCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.CreatePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.UpdatePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.CreateRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.UpdateRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.ActivityCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.ActivityUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.GradeCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.GradeUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ResponseBadRequestException;
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
@Aggregate
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
    public void on(CreatePostCommand cmd) {
        if (isArchived()) {
            throw new ResponseBadRequestException();
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
    public void on(CreateActivityCommand cmd) {
        if (isArchived()) {
            throw new ResponseBadRequestException();
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
    public void on(CreateGradeCommand cmd) {
        if (isArchived()) {
            throw new ResponseBadRequestException();
        }

        Activity activity = activities.stream()
            .filter(a -> Objects.equals(a.getId(), cmd.getActivityId()))
            .filter(a -> !a.isArchived())
            .findFirst()
            .orElseThrow(ResponseBadRequestException::new);

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
    public void on(UpdatePostCommand cmd) {
        if (isArchived()) {
            throw new ResponseBadRequestException();
        }

        Post post = posts.stream()
            .filter(p -> Objects.equals(p.getId(), cmd.getPostId()))
            .filter(p -> !p.isArchived())
            .findFirst()
            .orElseThrow(ResponseBadRequestException::new);

        AggregateLifecycle.apply(new PostUpdatedEvent(
            post.getId(),
            cmd.getTitle(),
            cmd.getContent(),
            Timestamp.from(Instant.now()))
        );
    }

    @CommandHandler
    public void on(UpdateRealisationCommand cmd) {
        if (isArchived()) {
            throw new ResponseBadRequestException();
        }

        AggregateLifecycle.apply(new RealisationUpdatedEvent(
            cmd.getId(),
            cmd.getYear(),
            cmd.getTeacherId())
        );
    }

    @CommandHandler
    public void on(UpdateActivityCommand cmd) {
        if (isArchived()) {
            throw new ResponseBadRequestException();
        }

        Activity activity = activities.stream()
            .filter(a -> Objects.equals(a.getId(), cmd.getActivityId()))
            .filter(a -> !a.isArchived())
            .findFirst()
            .orElseThrow(ResponseBadRequestException::new);

        AggregateLifecycle.apply(new ActivityUpdatedEvent(
            activity.getId(),
            cmd.getWeight(),
            cmd.getDate(),
            cmd.getDescription(),
            cmd.getName())
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
            .orElseThrow(ResponseBadRequestException::new);

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
            .orElseThrow(ResponseBadRequestException::new);

        Grade grade = activity.getGrades().stream()
            .filter(g -> Objects.equals(g.getId(), event.getId()))
            .filter(g -> !g.isArchived())
            .findFirst()
            .orElseThrow(ResponseBadRequestException::new);

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
            .orElseThrow(ResponseBadRequestException::new);

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
            .orElseThrow(ResponseBadRequestException::new);

        post.setContent(event.getContent());
        post.setTitle(event.getTitle());
    }
}
