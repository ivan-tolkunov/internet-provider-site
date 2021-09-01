package ua.ivan.provider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ua.ivan.provider.model.Donate;
import ua.ivan.provider.service.DonateService;
import ua.ivan.provider.service.UserDetailsServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DonateTest {
    @Autowired
    @Qualifier("donateService")
    DonateService donateService;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    UserDetailsServiceImpl userDetailsService;

    @Test
    void findAll() {
        assertEquals(donateService.findAll().get(0).getSum(), 200L);
    }

    @Test
    void saveDonate() {
        Donate donate = new Donate();
        donate.setSum(200L);
        donate.setUserId(userDetailsService.getUserByEmail("dfew@mail.com"));
        assertEquals(donateService.saveDonate(donate).getSum(), donate.getSum());
    }

    @Test
    void deleteDonate() {
        Donate donate = new Donate();
        donate.setSum(200L);
        donate.setUserId(userDetailsService.getUserByEmail("dfew@mail.com"));
        donateService.saveDonate(donate);
        assertTrue(donateService.deleteDonate(donate.getId()));
    }
}
