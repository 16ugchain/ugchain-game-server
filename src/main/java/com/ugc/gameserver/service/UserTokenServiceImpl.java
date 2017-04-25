package com.ugc.gameserver.service;

import com.ugc.gameserver.domain.UserToken;
import com.ugc.gameserver.domain.UserTokenStatusEnum;
import com.ugc.gameserver.mapper.SequenceMapper;
import com.ugc.gameserver.mapper.UserTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by fanjl on 2017/4/25.
 */
@Service
public class UserTokenServiceImpl implements UserTokenService {
    @Autowired
    private UserTokenMapper userTokenMapper;
    @Autowired
    private SequenceMapper sequenceMapper;

    @Override
    public int nextUserTokenId() {
        return sequenceMapper.nextId(sequenceMapper.USER_TOKEN);
    }

    @Override
    public UserToken getUserTokenById(int userTokenId) {

        return userTokenMapper.getUserTokenById(userTokenId);
    }

    @Override
    public UserToken insertAndGet(String token, String data, int status) {
        Optional<UserToken> utOp = getUserTokenByToken(token);
        if(utOp.isPresent()){
            return utOp.get();
        }
        UserToken usertoken = new UserToken();
        usertoken.setUserTokenId(nextUserTokenId());
        usertoken.setToken(token);
        usertoken.setData(data);
        usertoken.setStatus(UserTokenStatusEnum.USEING.getId());
        userTokenMapper.insertUserToken(usertoken);
        return usertoken;
    }


    @Override
    public boolean isExistsUserToken(String token) {

        return userTokenMapper.isExistsUserToken(token);
    }

    @Override
    public List<UserToken> getUserTokenListByStatus(List<Integer> status) {
        return userTokenMapper.getUserTokenList(status);
    }

    @Override
    public Optional<UserToken> getUserTokenByToken(String token) {
        UserToken userToken = userTokenMapper.getUserTokenByToken(token);
        return Optional.ofNullable(userToken);
    }
}
