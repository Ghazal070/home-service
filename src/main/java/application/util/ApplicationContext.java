package application.util;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import application.repository.CustomerRepository;
import application.repository.ExpertRepository;
import application.repository.impl.CustomerRepositoryImpl;
import application.repository.impl.ExpertRepositoryImpl;
import application.service.CustomerService;
import application.service.ExpertService;
import application.service.PasswordEncode;
import application.service.SignupService;
import application.service.impl.CustomerServiceImpl;
import application.service.impl.ExpertServiceImpl;
import application.service.impl.PasswordEncodeImpl;
import application.service.impl.SignupServiceImpl;


public class ApplicationContext {
    private static Logger logger;
    private static ApplicationContext INSTANCE;
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;


    private ApplicationContext() {
        getEntityManager();
        getLogger();
        logger.info("ApplicationContext initialized");
        CustomerRepository customerRepository = new CustomerRepositoryImpl(entityManager);
        ExpertRepository expertRepository = new ExpertRepositoryImpl(entityManager);
        AuthHolder authHolder = new AuthHolder();
        PasswordEncode passwordEncode = new PasswordEncodeImpl();
        CustomerService customerService =new CustomerServiceImpl(customerRepository,authHolder,passwordEncode);
        ExpertService expertService =new ExpertServiceImpl(expertRepository,authHolder,passwordEncode);
        SignupService signupService= new SignupServiceImpl(expertService,customerService, passwordEncode, authHolder);



    }


    public static ApplicationContext getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationContext();
        }
        return INSTANCE;
    }

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
        }
        return entityManagerFactory;
    }

    public static Logger getLogger() {
        if(logger==null){
            logger = LoggerFactory.getLogger(ApplicationContext.class);
        }
        return logger;
    }
}
