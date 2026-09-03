package ${package}.test.domain.business;

import ${package}.domain.business.model.entity.UserInformationEntity;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserInformationEntityTest {

    @Test
    public void shouldRecognizeUserIdentity() {
        assertTrue(UserInformationEntity.of("user-1").hasIdentity());
        assertFalse(UserInformationEntity.of(" ").hasIdentity());
        assertFalse(UserInformationEntity.of(null).hasIdentity());
    }

}
