package pl.lodz.p.it.rstrzalkowski.syllabus.mock;

import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.schoolclass.SchoolClassUniqueValuesRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.schoolclass.SchoolClassUniqueValuesService;

import java.util.UUID;


public class MockSchoolClassUniqueValuesService extends SchoolClassUniqueValuesService {

    public MockSchoolClassUniqueValuesService(SchoolClassUniqueValuesRepository schoolClassUniqueValuesRepository) {
        super(schoolClassUniqueValuesRepository);
    }

    @Override
    public void releaseValues(UUID subjectId) {

    }

    @Override
    public void lockSchoolClassLevelAndTeacherId(String name, Integer level, UUID teacherId, UUID schoolClassId) {

    }

    @Override
    public void updateSchoolClassLevelAndTeacherId(String name, Integer level, UUID teacherId, UUID schoolClassId) {

    }
}
