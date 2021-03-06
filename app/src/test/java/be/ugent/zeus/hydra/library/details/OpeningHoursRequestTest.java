package be.ugent.zeus.hydra.library.details;

import be.ugent.zeus.hydra.BuildConfig;
import be.ugent.zeus.hydra.TestApp;
import be.ugent.zeus.hydra.common.network.ArrayJsonSpringRequestTest;
import be.ugent.zeus.hydra.common.network.JsonSpringRequest;
import be.ugent.zeus.hydra.library.details.OpeningHours;
import be.ugent.zeus.hydra.library.details.OpeningHoursRequest;
import be.ugent.zeus.hydra.library.list.LibraryList;
import com.google.gson.Gson;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Niko Strijbol
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class)
public class OpeningHoursRequestTest extends ArrayJsonSpringRequestTest<OpeningHours> {

    public OpeningHoursRequestTest() {
        super(OpeningHours[].class);
    }

    @Override
    protected Resource getSuccessResponse() {
        return new ClassPathResource("library_hours.json");
    }

    @Override
    protected JsonSpringRequest<OpeningHours[]> getRequest() {
        try {
            // Get a library from the file with libraries.
            Gson gson = new Gson();
            Resource resource = new ClassPathResource("all_libraries.json");
            LibraryList list = gson.fromJson(new InputStreamReader(resource.getInputStream()), LibraryList.class);
            return new OpeningHoursRequest(list.getLibraries().get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}