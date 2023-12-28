package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user;


import org.springframework.data.domain.Pageable;

public record GetActiveAdminsQuery(Pageable pageable) {
}
