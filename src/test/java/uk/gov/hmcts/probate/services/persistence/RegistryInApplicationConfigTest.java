package uk.gov.hmcts.probate.services.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.probate.services.persistence.component.RegistryUpdate;
import uk.gov.hmcts.probate.services.persistence.model.Registry;
import uk.gov.hmcts.probate.services.persistence.repository.RegistryRepository;
import uk.gov.hmcts.probate.services.persistence.repository.RepositoryTestConfiguration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = {
            "spring.jpa.hibernate.dialect=org.hibernate.dialect.H2Dialect",
            "spring.jpa.hibernate.ddl-auto: update",
            "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"},
        classes = { RegistryInApplicationConfigTest.TestConfiguration.class })
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = {RepositoryTestConfiguration.class})
@ActiveProfiles("registry")
public class RegistryInApplicationConfigTest {
    @Autowired
    private ApplicationConfig properties;

    @Autowired
    private RegistryRepository registryRepository;

    @Autowired
    private RegistryUpdate registryUpdate;

    @Test
    public void shouldPopulateRegistryPropertiesInConfig() {
        assertThat(properties.getRegistries().size(), is(3));
    }

    @Test
    public void shouldPopulateRegistryPropertiesInDatabase() {
        assertThat(registryRepository.findAll().size(), is(3));
    }

    @Test
    public void shouldUpdateRegistryPropertiesInDatabase() throws Exception {
        //given
        Registry oxford = registryRepository.findById("oxford");
        oxford.setRatio(5L);
        registryRepository.saveAndFlush(oxford);
        assertThat(registryRepository.findById("oxford").getRatio(), is(5L));

        //when
        registryUpdate.updateRegistries(properties.getRegistries());

        //then
        assertThat(registryRepository.findById("oxford").getRatio(), is(4L));
    }

    @EnableConfigurationProperties(ApplicationConfig.class)
    static class TestConfiguration {}
}
