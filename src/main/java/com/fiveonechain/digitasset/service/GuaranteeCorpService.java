package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.GuaranteeCorp;

import java.util.Optional;

/**
 * Created by fanjl on 16/11/18.
 */
public interface GuaranteeCorpService {
    int nextId();

    boolean isExists(int userId);

    int insertCorp(GuaranteeCorp guaranteeCorp);

    GuaranteeCorp findByUserId(int userId);

    Optional<GuaranteeCorp> getGuarOptional(int userId);

    Optional<GuaranteeCorp> getGuarOptByGuarId(int guarId);

}
