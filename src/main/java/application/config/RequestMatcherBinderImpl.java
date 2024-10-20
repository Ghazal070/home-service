package application.config;


import java.util.Collection;

public class RequestMatcherBinderImpl implements RequestMatcherBinder {

    @Override
    public String[] getPermitsAllUrls() {

        return new String[]{
                "/v1/users/signup"
        };
    }

    @Override
    public Collection<AuthorityPair> getAuthorityPair() {

        return null;
    }
}
