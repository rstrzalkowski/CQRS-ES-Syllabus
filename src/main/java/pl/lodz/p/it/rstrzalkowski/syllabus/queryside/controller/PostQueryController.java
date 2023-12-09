package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.PostDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.PostQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.post.GetPostByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.post.GetRecentActivePostsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@ReadApplicationBean
public class PostQueryController {

    private final PostQueryHandler postQueryHandler;
    private final AccessGuard accessGuard;

    @GetMapping("/{id}")
    @Secured({"TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public PostDTO getPostById(@PathVariable("id") UUID id) {
        accessGuard.checkAccessToPost(id);
        return postQueryHandler.handle(new GetPostByIdQuery(id));
    }

    @GetMapping("/recent")
    @Secured({"STUDENT"})
    public Page<PostDTO> getRecentPosts(Pageable pageable) {
        return postQueryHandler.handle(new GetRecentActivePostsQuery(pageable));
    }
}
