package com.ugc.micropayment;

import com.ugc.micropayment.service.TransactionRecordService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class MicroPaymentApplication  {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MicroPaymentApplication.class, args);
		context.getBean(TransactionRecordService.class).recharge();
	}

}
