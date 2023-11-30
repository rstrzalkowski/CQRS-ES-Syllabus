package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity;


import org.springframework.data.domain.Pageable;

public record GetIncomingActivitiesQuery(Pageable pageable) {
}
