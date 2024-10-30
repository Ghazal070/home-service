package application.config;


import java.util.Collection;
import java.util.List;

public class RequestMatcherBinderImpl implements RequestMatcherBinder {

    @Override
    public String[] getPermitsAllUrls() {

        return new String[]{
                "/v1/users/signup",
                "v1/users/customers/activate",
                "v1/users/experts/activate",
                "v1/users/admins/login",
                "v1/users/customers/login",
        };
    }

    @Override
    public Collection<AuthorityPair> getAuthorityPair() {

        return List.of(
                new AuthorityPair() {
                    @Override
                    public String[] getUrls() {
                        return new String[]{
                                "/v1/admin/**"
                        };
                    }

                    @Override
                    public String[] getAuthorities() {
                        return new String[]{
                                "admin-manage"
                        };
                    }
                },
                new AuthorityPair() {
                    @Override
                    public String[] getUrls() {
                        return new String[]{
                                "/v1/customers/**"
                        };
                    }

                    @Override
                    public String[] getAuthorities() {
                        return new String[]{
                                "customer-manage"
                        };
                    }
                },
                new AuthorityPair() {
                    @Override
                    public String[] getUrls() {
                        return new String[]{
                                "/v1/experts/**"
                        };
                    }

                    @Override
                    public String[] getAuthorities() {
                        return new String[]{
                                "expert-manage"
                        };
                    }
                }
        );
    }
}
