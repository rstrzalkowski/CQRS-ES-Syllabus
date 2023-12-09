package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.post;


import org.springframework.data.domain.Pageable;

public record GetRecentActivePostsQuery(Pageable pageable) {
}
