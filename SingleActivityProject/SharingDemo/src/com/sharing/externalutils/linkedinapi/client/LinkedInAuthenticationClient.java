/**
 *
 */
package com.sharing.externalutils.linkedinapi.client;

import com.sharing.externalutils.linkedinapi.client.oauth.LinkedInAccessToken;
import com.sharing.externalutils.linkedinapi.client.oauth.LinkedInApiConsumer;

/**
 * @author Nabeel Mukhtar
 *
 */
public interface LinkedInAuthenticationClient {

    /**
     * Method description
     *
     *
     * @param apiConsumer
     */
    public void setApiConsumer(LinkedInApiConsumer apiConsumer);

    /**
     * Method description
     *
     *
     * @return
     */
    public LinkedInApiConsumer getApiConsumer();

    /**
     * Method description
     *
     *
     * @param accessToken
     */
    public void setAccessToken(LinkedInAccessToken accessToken);

    /**
     * Method description
     *
     *
     * @return
     */
    public LinkedInAccessToken getAccessToken();
}
