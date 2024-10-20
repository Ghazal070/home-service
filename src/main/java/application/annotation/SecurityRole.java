package application.annotation;

import application.entity.Role;
import jakarta.transaction.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

public @interface SecurityRole {

    Role[] roles();

    @interface Role{
        String name();
        SequrityAuthority[] authorities();
    }

}
