package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.GuaranteeCorp;
import com.fiveonechain.digitasset.mapper.GuaranteeCorpMapper;
import com.fiveonechain.digitasset.mapper.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by fanjl on 16/11/18.
 */
@Component
public class GuaranteeCorpServiceImpl implements GuaranteeCorpService {
    @Autowired
    private GuaranteeCorpMapper guaranteeCorpMapper;
    @Autowired
    private SequenceMapper sequenceMapper;

    @Override
    public int nextId() {
        return sequenceMapper.nextId(SequenceMapper.GUARANTEECORP_ID);
    }

    @Override
    public boolean isExists(int userId) {
        return guaranteeCorpMapper.isExists(userId);
    }

    @Override
    public int insertCorp(GuaranteeCorp guaranteeCorp) {
        int guaranteecorpId = nextId();
        guaranteeCorp.setGuaranteecorpId(guaranteecorpId);
        return guaranteeCorpMapper.insertCorp(guaranteeCorp);
    }

    @Override
    public GuaranteeCorp findByUserId(int userId) {
        return guaranteeCorpMapper.findByUserId(userId);
    }

    @Override
    public Optional<GuaranteeCorp> getGuarOptional(int userId) {
        GuaranteeCorp guar = guaranteeCorpMapper.findByUserId(userId);
        return Optional.ofNullable(guar);
    }
}
