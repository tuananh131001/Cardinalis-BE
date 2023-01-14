package com.cardinalis.userservice.security.oauth;

import com.cardinalis.userservice.config.AppProperties;
import com.cardinalis.userservice.security.JwtProvider;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private AppProperties appProperties;
    private final JwtProvider jwtProvider;
    //authorizedRedirectUris value from yml
    @Value("${app.oauth2.authorizedRedirectUris}")
    private String authorizedRedirectUris;


//    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = jwtProvider.generateTokenForGithub(authentication);
        System.out.println(token);
        String targetUrl = determineTargetUrl(token);
        System.out.println("here");
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
}
    protected String determineTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(authorizedRedirectUris)
                .queryParam("token", token)
                .build().toUriString();
    }


    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    System.out.println("authorizedRedirectUri = " + authorizedRedirectUri);
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
