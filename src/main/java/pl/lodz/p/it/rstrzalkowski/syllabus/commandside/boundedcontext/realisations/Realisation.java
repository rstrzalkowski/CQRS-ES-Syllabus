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
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.realisations.entity.Post;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.CreatePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.CreateRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RealisationCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
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

    private List<Post> posts = new ArrayList<>();
    private List<Activity> activities = new ArrayList<>();

    //CreatePostCommand
    //CreateActivityCommand
    //GradeActivityCommand


    @CommandHandler
    public Realisation(CreateRealisationCommand cmd) {
        //TODO check if subject, schoolclass and teacher exist
        AggregateLifecycle.apply(new RealisationCreatedEvent(
                UUID.randomUUID(),
                cmd.getSubjectId(),
                cmd.getClassId(),
                cmd.getTeacherId(),
                cmd.getYear()));
    }

    @CommandHandler
    public void on(CreatePostCommand cmd) {
        AggregateLifecycle.apply(new PostCreatedEvent(
                UUID.randomUUID(),
                cmd.getRealisationId(),
                cmd.getTeacherId(),
                cmd.getTitle(),
                cmd.getContent()));
    }

    @EventSourcingHandler
    public void on(RealisationCreatedEvent event) {
        setId(event.getId());
        this.subjectId = event.getSubjectId();
        this.schoolClassId = event.getSchoolClassId();
        this.teacherId = event.getTeacherId();
        this.year = event.getYear();
    }

    @EventSourcingHandler
    public void on(PostCreatedEvent event) {
        Post post = new Post(event.getTeacherId(), event.getTitle(), event.getContent(), false);
        post.setId(event.getId());
        this.posts.add(post);
        System.out.println(posts);
    }
}