package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.projector;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.ActivityEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.PostEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.RealisationEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SubjectEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.PostRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.RealisationRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostArchivedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.PostUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class PostProjector {

    private final RealisationRepository realisationRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @EventHandler
    public void on(PostCreatedEvent event) {
        UserEntity teacher = userRepository.findById(event.getTeacherId()).orElse(null);
        RealisationEntity realisation = realisationRepository.findById(event.getRealisationId()).orElse(null);

        PostEntity postEntity = new PostEntity(
            event.getId(),
            realisation,
            teacher,
            event.getTitle(),
            event.getContent());
        postRepository.save(postEntity);
    }

    @EventHandler
    public void on(PostUpdatedEvent event) {
        PostEntity post = postRepository.findById(event.getId()).orElseThrow();

        post.setTitle(event.getTitle());
        post.setContent(event.getContent());
        post.setEdited(true);

        postRepository.save(post);
    }

    @EventHandler
    public void on(PostArchivedEvent event) {
        PostEntity post = postRepository.findById(event.getId()).orElseThrow();

        post.setArchived(true);

        postRepository.save(post);
    }
}
