package com.ugc.micropayment.service;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ugc.micropayment.mapper.AccountMapper;

/**
 * Created by fanjl on 2017/4/6.
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountMapper accountMapper;
	
	
	
    @Override
    public void createAccount(String address) {

    	accountMapper.insertAccount(address);
    }

    @Override
    public boolean isExistsAddress(String address) {
    	int i = accountMapper.findAddress(address);
    	//判断查询返回结果：0则不存在
    	if (i == 0) {			
    		return false;
		}else {
			return true;
		}
    }

    @Override
    public boolean isAmountEnough(String address, BigDecimal amount) {
    	
    	int i = accountMapper.queryAmountEnough(address,amount);
    	//判断返回结果，0则证明账户金额不足
    	if (i == 0) {			
    		return false;
		}else {
			return true;
		}
    }
}
