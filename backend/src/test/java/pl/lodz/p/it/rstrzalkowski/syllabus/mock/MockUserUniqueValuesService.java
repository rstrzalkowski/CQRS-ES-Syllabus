package pl.lodz.p.it.rstrzalkowski.syllabus.mock;

import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user.UserUniqueValuesRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user.UserUniqueValuesService;

import java.util.UUID;


public class MockUserUniqueValuesService extends UserUniqueValuesService {

    @Override
    public void lockPersonalIdAndEmail(String personalId, String email) {

    }

    @Override
    public void updateAggregateId(String email, UUID userId) {

    }

    @Override
    public void releaseValues(String personalId) {

    }

    public MockUserUniqueValuesService(UserUniqueValuesRepository userUniqueValuesRepository) {
        super(userUniqueValuesRepository);
    }
}
