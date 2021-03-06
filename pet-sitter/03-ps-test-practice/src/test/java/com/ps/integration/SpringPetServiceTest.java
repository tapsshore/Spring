package com.ps.integration;

import com.ps.base.UserType;
import com.ps.config.TestAppConfig;
import com.ps.ents.Pet;
import com.ps.ents.User;
import com.ps.repo.stub.StubPetRepo;
import com.ps.services.PetService;
import com.ps.services.impl.SimplePetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Set;

import static com.ps.util.TestObjectsBuilder.buildUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SpringPetServiceTest {

    private static final Long PET_ID = 1L;
    private static final User owner = buildUser("test@gmail.com", "a!2#tre", UserType.OWNER);

    @Configuration
    static class TestConfig{

        @Bean
        public PetService simplePetService(){
            final SimplePetService simplePetService = new SimplePetService(stubPetRepo());
            return simplePetService;
        }

        @Bean
        public StubPetRepo stubPetRepo(){
            return new StubPetRepo();
        }
    }
    @Autowired
    private PetService simplePetService;

    //positive test, we know that a Pet with ID=1 exists
    @Test
    public void findByIdPositive() {
        Pet pet = simplePetService.findById(PET_ID);
        assertNotNull(pet);
    }

    //positive test, we know that pets for this owner exist and how many
    @Test
    public void findByOwnerPositive() {
        Set<Pet> result = simplePetService.findAllByOwner(owner);
        assertEquals(result.size(), 2);
    }

}
