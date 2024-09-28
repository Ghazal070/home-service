package application.util;


import application.entity.users.userFactory.CustomerFactory;
import application.entity.users.userFactory.ExpertFactory;
import application.repository.*;
import application.repository.impl.*;
import application.service.*;
import application.service.impl.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ApplicationContext {
    private static Logger logger;
    private static ApplicationContext INSTANCE;
    private EntityManager entityManager;
    private DatabaseAccess databaseAccess;
    private EntityManagerFactory entityManagerFactory;


    private ApplicationContext() {
        getEntityManager();
        getLogger();
        logger.info("ApplicationContext initialized");
        databaseAccess = new JpaDatabaseAccess(entityManager);
        ExpertFactory expertFactory = new ExpertFactory();
        CustomerFactory customerFactory =new CustomerFactory();
        CustomerRepository customerRepository = new CustomerRepositoryImpl(databaseAccess);
        ExpertRepository expertRepository = new ExpertRepositoryImpl(databaseAccess);
        AuthHolder authHolder = new AuthHolder();
        PasswordEncode passwordEncode = new PasswordEncodeImpl();
        DutyRepository dutyRepository = new DutyRepositoryImpl(databaseAccess);
        DutyService dutyService = new DutyServiceImpl(dutyRepository);
        OrderRepository orderRepository =new OrderRepositoryImpl(databaseAccess);
        OrderService orderService = new OrderServiceImpl(orderRepository);
        CustomerService customerService =new CustomerServiceImpl(customerRepository,authHolder,passwordEncode, dutyService, authHolder, orderService);
        ExpertService expertService =new ExpertServiceImpl(expertRepository,authHolder,passwordEncode);
        SignupService signupService= new SignupServiceImpl(expertService,customerService, passwordEncode, authHolder, expertFactory, customerFactory);



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
