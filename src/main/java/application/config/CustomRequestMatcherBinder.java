package application.config;


import java.util.Collection;

public class CustomRequestMatcherBinder implements RequestMatcherBinder {

    @Override
    public String[] getPermitsAllUrls() {
        return new String[0];
    }

    @Override
    public Collection<AuthorityPair> getAuthorityPair() {
        return null;
    }
}
