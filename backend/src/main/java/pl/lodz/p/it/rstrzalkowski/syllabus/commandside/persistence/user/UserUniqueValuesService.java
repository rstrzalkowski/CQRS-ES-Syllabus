package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.UserUniqueValuesExistCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@WriteApplicationBean
public class UserUniqueValuesService {
    private final UserUniqueValuesRepository userUniqueValuesRepository;

    public void lockPersonalIdAndEmail(String personalId, String email) {
        try {
            userUniqueValuesRepository.save(new UserUniqueValuesJpaEntity(email, personalId));
            userUniqueValuesRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new UserUniqueValuesExistCommandExecutionException();
        }
    }

    public void updateAggregateId(String email, UUID userId) {
        userUniqueValuesRepository.findByEmail(email).ifPresent(userUniqueValuesJpaEntity -> {
            userUniqueValuesJpaEntity.setAggregateId(userId);
            userUniqueValuesRepository.save(userUniqueValuesJpaEntity);
            userUniqueValuesRepository.flush();
        });
    }

    public void releaseValues(String personalId) {
        userUniqueValuesRepository.findByPersonalId(personalId).ifPresent(userUniqueValuesRepository::delete);
        userUniqueValuesRepository.flush();
    }
}
