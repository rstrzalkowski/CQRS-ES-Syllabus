package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.PostDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.PostEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.post.PostNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.post.GetActivePostsByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.post.GetArchivedPostsByRealisationQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.post.GetPostByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.post.GetRecentActivePostsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.PostRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.NotImplementedException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class PostQueryHandler {

    private final PostRepository postRepository;

    @QueryHandler
    public Page<PostDTO> handle(GetActivePostsByRealisationQuery query) {
        Page<PostEntity> posts =
            postRepository.findByRealisationIdAndArchived(query.realisationId(), false, query.pageable());
        return posts.map((PostDTO::new));
    }

    @QueryHandler
    public Page<PostDTO> handle(GetArchivedPostsByRealisationQuery query) {
        return postRepository.findByRealisationIdAndArchived(query.realisationId(), true, query.pageable())
            .map(PostDTO::new);
    }

    @QueryHandler
    public PostDTO handle(GetPostByIdQuery query) {
        return new PostDTO(postRepository.findById(query.id())
            .orElseThrow(PostNotFoundException::new));
    }

    @QueryHandler
    public Page<PostDTO> handle(GetRecentActivePostsQuery query) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return postRepository.findByRealisation_SchoolClass_Students_IdAndArchived(user.getId(), false, pageable)
//            .map(PostDTO::new);
        throw new NotImplementedException();
    }
}
