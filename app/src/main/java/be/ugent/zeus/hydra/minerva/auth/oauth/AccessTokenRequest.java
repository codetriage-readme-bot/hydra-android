package be.ugent.zeus.hydra.minerva.auth.oauth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import be.ugent.zeus.hydra.minerva.account.OAuthConfiguration;
import be.ugent.zeus.hydra.common.request.Request;
import be.ugent.zeus.hydra.common.network.IOFailureException;
import be.ugent.zeus.hydra.common.request.RequestException;
import be.ugent.zeus.hydra.common.request.Result;
import com.google.gson.Gson;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * Exchange an authorisation code for an access code.
 *
 * @author Niko Strijbol
 */
abstract class AccessTokenRequest implements Request<BearerToken> {

    @SuppressWarnings("WeakerAccess")
    protected OAuthConfiguration configData;
    protected String code;
    private Gson gson;

    AccessTokenRequest(OAuthConfiguration configData, String code) {
        this.configData = configData;
        this.code = code;
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public Result<BearerToken> performRequest(Bundle args) {
        try {
            OAuthJSONAccessTokenResponse accessTokenResponse = getToken();
            return new Result.Builder<BearerToken>()
                    .withData(gson.fromJson(accessTokenResponse.getBody(), BearerToken.class))
                    .build();
        } catch (OAuthSystemException e) {
            return new Result.Builder<BearerToken>()
                    .withError(new IOFailureException(e))
                    .build();
        } catch (OAuthProblemException e) {
            return new Result.Builder<BearerToken>()
                    .withError(new RequestException(e))
                    .build();
        }
    }

    /**
     * Get the token.
     *
     * @return The token data.
     *
     * @throws OAuthProblemException See documentation of {@link OAuthProblemException}.
     * @throws OAuthSystemException See documentation of {@link OAuthSystemException}.
     */
    protected abstract OAuthJSONAccessTokenResponse getToken() throws OAuthProblemException, OAuthSystemException;
}