package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.RealisationDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetActiveRealisationsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.realisation.GetArchivedRealisationsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.RealisationRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.converter.RealisationConverter;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class RealisationQueryHandler {

    private final RealisationRepository realisationRepository;

    @QueryHandler
    public Page<RealisationDto> handle(GetActiveRealisationsQuery query) {
        return realisationRepository.findAllByArchived(false, query.pageable())
                .map(RealisationConverter::convertToRealisationDto);
    }

    @QueryHandler
    public Page<RealisationDto> handle(GetArchivedRealisationsQuery query) {
        return realisationRepository.findAllByArchived(true, query.pageable())
                .map(RealisationConverter::convertToRealisationDto);
    }
}
