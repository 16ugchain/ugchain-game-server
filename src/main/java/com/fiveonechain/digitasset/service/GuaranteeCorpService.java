package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.GuaranteeCorp;

/**
 * Created by fanjl on 16/11/18.
 */
public interface GuaranteeCorpService {
    int nextId();

    boolean isExists(int user_id);

    int insertCorp(GuaranteeCorp guaranteeCorp);

    GuaranteeCorp findByUserId(int user_id);
}
