package application.init;

import application.annotation.SecurityRole;
import application.annotation.SequrityAuthority;
import application.entity.Authority;
import application.service.AuthorityService;
import application.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer extends ClassPathScanningCandidateComponentProvider {

    private AuthorityService authorityService;
    private RoleService roleService;

    public DataInitializer() {
        super(false);
        addIncludeFilter(new AnnotationTypeFilter(SecurityRole.class));
        addIncludeFilter(new AnnotationTypeFilter(SessionAttributes.class));
    }

    @SneakyThrows
    @PostConstruct
    public void init(){
        Set<BeanDefinition> beanDefinitions = findCandidateComponents("application");
        for (BeanDefinition bean:beanDefinitions) {
            String beanClassName = bean.getBeanClassName();
            Class<?> beanClass = Class.forName(beanClassName);
            SequrityAuthority securityAuthority = beanClass.getAnnotation(SequrityAuthority.class);
            if (securityAuthority!=null){
                authorityService.createIfNotExist(securityAuthority.name());
            }
        }
        for (BeanDefinition bean:beanDefinitions) {
            String beanClassName = bean.getBeanClassName();
            Class<?> beanClass = Class.forName(beanClassName);
            SecurityRole securityRole = beanClass.getAnnotation(SecurityRole.class);
            if (securityRole!=null){
                for (SecurityRole.Role role:securityRole.roles()) {
                    List<String> authorityNames = Arrays.stream(role.authorities()).map(SequrityAuthority::name)
                            .flatMap(Arrays::stream).toList();
                    Set<Authority> authorities = authorityService.findByNames(authorityNames);
                    if (authorities==null || authorities.size() != authorityNames.size()){
                        throw new IllegalArgumentException("authority not found");
                    }
                    roleService.createOrUpdate(role.name(), authorities);
                }
            }
        }

    }

    @Autowired
    public void setAuthorityService(AuthorityService authorityService){
        this.authorityService=authorityService;
    }

    @Autowired
    public void setRoleService(RoleService roleService){
        this.roleService=roleService;
    }
}
