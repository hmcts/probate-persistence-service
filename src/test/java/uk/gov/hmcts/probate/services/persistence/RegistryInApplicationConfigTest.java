package uk.gov.hmcts.probate.services.persistence;

import org.junit.Before;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
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

    @Before
    public void setupRegistries() {
        registryRepository.deleteAll();
        registryUpdate.updateRegistries(properties.getRegistries());
    }

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

    @Test
    public void shouldIgnoreCounterUpdatesFromConfig() throws Exception {
        //given
        Registry oxford = registryRepository.findById("oxford");
        oxford.setRatio(5L);
        oxford.setCounter(2L);
        registryRepository.saveAndFlush(oxford);
        assertThat(registryRepository.findById("oxford").getCounter(), is(2L));

        //when
        registryUpdate.updateRegistries(properties.getRegistries());

        //then
        assertThat(registryRepository.findById("oxford").getCounter(), is(2L));
        assertThat(registryRepository.findById("oxford").getRatio(), is(4L));
    }

    @Test
    public void shouldDeleteRegistryWhenOmmittedInConfig() {
        //given
        List<Registry> duplicatedRegistryWithoutOxford = properties.getRegistries().stream()
                .filter(registry -> !registry.getId().equals("oxford"))
                .collect(Collectors.toList());
        assertThat(duplicatedRegistryWithoutOxford.size(), is(2));

        //when
        registryUpdate.updateRegistries(duplicatedRegistryWithoutOxford);

        //then
        assertNull(registryRepository.findById("oxford"));
    }

    @Test
    public void shouldNotAllowUpdatesToEmailsOrAddresses() {
        //given
        Registry oxfordRegistry = properties.getRegistries().stream()
                .filter(registry -> registry.getId().equals("oxford"))
                .findAny().get();
        oxfordRegistry.setEmail("a@b.com");
        oxfordRegistry.setAddress("SW1");

        //when
        registryUpdate.updateRegistries(properties.getRegistries());

        //then
        assertThat(registryRepository.findById("oxford").getEmail(), is("oxford@email.com"));
        assertThat(registryRepository.findById("oxford").getAddress(), is("Line 1 Ox\nLine 2 Ox\nLine 3 Ox\nPostCode Ox\n"));
    }

    @Test
    public void shouldAllowUpdatesToEmailsOrAddressesViaADeleteAndAnInsert() {
        //given
        Registry oxfordRegistry = properties.getRegistries().stream()
                .filter(registry -> registry.getId().equals("oxford"))
                .findAny().get();
        oxfordRegistry.setEmail("a@b.com");
        oxfordRegistry.setAddress("SW1");
        List<Registry> duplicatedRegistryWithoutOxford = properties.getRegistries().stream()
                .filter(registry -> !registry.getId().equals("oxford"))
                .collect(Collectors.toList());
        assertThat(duplicatedRegistryWithoutOxford.size(), is(2));

        //when
        registryUpdate.updateRegistries(duplicatedRegistryWithoutOxford);
        registryUpdate.updateRegistries(properties.getRegistries());

        //then
        assertThat(registryRepository.findById("oxford").getEmail(), is("a@b.com"));
        assertThat(registryRepository.findById("oxford").getAddress(), is("SW1"));
    }

    @EnableConfigurationProperties(ApplicationConfig.class)
    static class TestConfiguration {}
}
