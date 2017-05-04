package com.ugc.gameserver;

import com.ugc.gameserver.service.DermaOrderServiceImpl;
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
public class UGCGameServerApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(UGCGameServerApplication.class, args);
		context.getBean(DermaOrderServiceImpl.class).listenContract();

	}

}
