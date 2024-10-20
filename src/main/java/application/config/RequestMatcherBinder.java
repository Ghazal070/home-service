package application.config;

import java.util.Collection;

public interface RequestMatcherBinder {

    String [] getPermitsAllUrls();

    Collection<AuthorityPair> getAuthorityPair();

    interface AuthorityPair{

        String [] getUrls();
        String [] getAuthorities();
    }


}
