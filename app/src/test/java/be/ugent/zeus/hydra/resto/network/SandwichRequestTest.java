package be.ugent.zeus.hydra.resto.network;

import android.os.Build;
import android.support.annotation.RequiresApi;

import be.ugent.zeus.hydra.BuildConfig;
import be.ugent.zeus.hydra.TestApp;
import be.ugent.zeus.hydra.resto.Sandwich;
import be.ugent.zeus.hydra.common.network.ArrayJsonSpringRequestTest;
import be.ugent.zeus.hydra.common.network.JsonSpringRequest;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Niko Strijbol
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class)
@RequiresApi(api = Build.VERSION_CODES.N)
public class SandwichRequestTest extends ArrayJsonSpringRequestTest<Sandwich> {

    public SandwichRequestTest() {
        super(Sandwich[].class);
    }

    @Override
    protected Resource getSuccessResponse() {
        return new ClassPathResource("resto/sandwiches.json");
    }

    @Override
    protected JsonSpringRequest<Sandwich[]> getRequest() {
        return new SandwichRequest();
    }

    @Override
    protected Sandwich[] getExpectedResult(Resource resource) throws IOException {
        Sandwich[] result = super.getExpectedResult(resource);
        Arrays.sort(result, Comparator.comparing(Sandwich::getName, String.CASE_INSENSITIVE_ORDER));
        return result;
    }
}