package {{java-package}};

import static test.TestResources.getTestResources;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import test.PortMappingInitializer;
import test.TestResources;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {Application.class, IntegrationsTestConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles(profiles = {"integration"})
@ContextConfiguration(initializers = {PortMappingInitializer.class})
public class ApplicationBootstrappingIntegrationTest {

    @ClassRule
    public static TestResources res = getTestResources();

    @LocalServerPort
    int randomServerPort;

    @Test
    public void applicationShouldStart() throws IOException, URISyntaxException {

    }

}
