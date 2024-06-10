package com.mindhub.homebanking.utilsTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.mindhub.homebanking.utils.AccountUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountUtilsTests {
    @Test
    public void createRandomAccountNumber(){
        String accountNumber = AccountUtils.createAccountNumber();
        assertThat(accountNumber,is(not(emptyOrNullString())));
    }
}
