package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.GuaranteeCorp;
import com.fiveonechain.digitasset.mapper.GuaranteeCorpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fanjl on 16/11/18.
 */
@Component
public class GuaranteeCorpServiceImpl implements IGuaranteeCorpService {
    @Autowired
    private GuaranteeCorpMapper guaranteeCorpMapper;
    @Override
    public boolean isExists(int user_id) {
        return guaranteeCorpMapper.isExists(user_id);
    }

    @Override
    public int insertCorp(GuaranteeCorp guaranteeCorp) {
        return guaranteeCorpMapper.insertCorp(guaranteeCorp);
    }

    @Override
    public GuaranteeCorp findByUserId(int user_id) {
        return guaranteeCorpMapper.findByUserId(user_id);
    }
}
