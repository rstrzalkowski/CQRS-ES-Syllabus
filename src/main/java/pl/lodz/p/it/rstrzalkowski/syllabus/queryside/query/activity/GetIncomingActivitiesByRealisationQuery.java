package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity;


import org.springframework.data.domain.Pageable;

public record GetIncomingActivitiesByRealisationQuery(Long realisationId, Pageable pageable) {
}
