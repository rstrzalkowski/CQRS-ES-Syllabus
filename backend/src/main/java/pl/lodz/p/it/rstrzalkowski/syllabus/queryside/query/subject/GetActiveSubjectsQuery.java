package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.subject;


import org.springframework.data.domain.Pageable;

public record GetActiveSubjectsQuery(Pageable pageable) {
}
