package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.activity;


import org.springframework.data.domain.Pageable;

public record GetArchivedActivitiesByRealisationQuery(Long realisationId, Pageable pageable) {
}
