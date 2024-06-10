package com.mindhub.homebanking.utilsTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void createCardNumber(){
        String cardNumber = CardUtils.createCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void createCvv(){
        String cvvNumber = CardUtils.createCvv();
        assertThat(cvvNumber,is(not(emptyOrNullString())));
    }
}
