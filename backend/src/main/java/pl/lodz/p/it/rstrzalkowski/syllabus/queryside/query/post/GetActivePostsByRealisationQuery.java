package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.post;


import org.springframework.data.domain.Pageable;

import java.util.UUID;

public record GetActivePostsByRealisationQuery(UUID realisationId, Pageable pageable) {
}
