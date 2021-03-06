package be.ugent.zeus.hydra.urgent;

import be.ugent.zeus.hydra.BuildConfig;
import be.ugent.zeus.hydra.TestApp;
import be.ugent.zeus.hydra.urgent.UrgentInfo;
import be.ugent.zeus.hydra.common.network.AbstractJsonSpringRequestTest;
import be.ugent.zeus.hydra.common.network.JsonSpringRequest;
import be.ugent.zeus.hydra.urgent.UrgentInfoRequest;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author Niko Strijbol
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class)
public class UrgentInfoRequestTest extends AbstractJsonSpringRequestTest<UrgentInfo> {

    public UrgentInfoRequestTest() {
        super(UrgentInfo.class);
    }

    @Override
    protected Resource getSuccessResponse() {
        return new ClassPathResource("urgent.json");
    }

    @Override
    protected JsonSpringRequest<UrgentInfo> getRequest() {
        return new UrgentInfoRequest();
    }
}