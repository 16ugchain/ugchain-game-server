package com.ugc.micropayment.service;

import com.ugc.micropayment.configuration.ConfigurationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

@SpringBootTest(classes = {ConfigurationTest.class})
@RunWith(SpringRunner.class)
public class AccountTest {

	@Autowired
	private AccountService accountService;
	
	
	@Test
	public void testCreatAccount(){
		accountService.createAccount("adfc0262bbed8c1f4bd24a4a763ac616803a8c54");
	}
	
	@Test
	public void testIsExistAccount(){
		
		Boolean boolean1 = accountService.isExistsAddress("adfc0262bbed8c1f4bd24a4a763ac616803a8c54");
		System.out.println(boolean1);
		
	}
	
	@Test
	public void testIsEnoughAccount(){
		int amount1 = 1;
		
		BigInteger amount11 = BigInteger.valueOf(amount1);
		
		Boolean boolean1 = accountService.isAmountEnough("adfc0262bbed8c1f4bd24a4a763ac616803a8c54", amount11);
		System.out.println(boolean1);
		
		
	}
	
	
	
}
