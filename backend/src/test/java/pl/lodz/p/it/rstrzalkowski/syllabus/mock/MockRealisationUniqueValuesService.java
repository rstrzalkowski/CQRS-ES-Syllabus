package pl.lodz.p.it.rstrzalkowski.syllabus.mock;

import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation.RealisationUniqueValuesRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.realisation.RealisationUniqueValuesService;

import java.time.Year;
import java.util.UUID;


public class MockRealisationUniqueValuesService extends RealisationUniqueValuesService {

    public MockRealisationUniqueValuesService(RealisationUniqueValuesRepository realisationUniqueValuesRepository) {
        super(realisationUniqueValuesRepository);
    }

    @Override
    public void lockSubjectIdAndClassIdAndYear(UUID subjectId, UUID schoolClassId, Year year, UUID realisationId) {

    }

    @Override
    public void releaseValues(UUID realisationId) {

    }
}
