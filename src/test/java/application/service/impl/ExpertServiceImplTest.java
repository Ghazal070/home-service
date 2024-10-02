package application.service.impl;

import application.entity.enumeration.ExpertStatus;
import application.entity.users.Expert;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class ExpertServiceImplTest {

    @Autowired
    private ExpertServiceImpl underTest;


    @Test
    void havePermissionTrueExpertToServices() {
        Expert expert = Expert.builder().id(100).expertStatus(ExpertStatus.Accepted).build();

        Boolean actual = underTest.havePermissionExpertToServices(expert);

        assertTrue(actual);

    }

    @Test
    void havePermissionFalseExpertToServices() {
        Expert expert = Expert.builder().id(100).expertStatus(ExpertStatus.New).build();

        Boolean actual = underTest.havePermissionExpertToServices(expert);

        assertFalse(actual);

    }
}