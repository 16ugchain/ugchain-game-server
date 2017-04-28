package com.ugc.gameserver.service;

import com.ugc.gameserver.domain.Derma;
import com.ugc.gameserver.domain.DermaOrder;
import com.ugc.gameserver.domain.OrderStatusEnum;
import com.ugc.gameserver.mapper.DermaOrderMapper;
import com.ugc.gameserver.mapper.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fanjl on 2017/4/27.
 */
@Service
public class DermaOrderServiceImpl implements DermaOrderService {
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private DermaOrderMapper dermaOrderMapper;
    @Override
    public int createOrder(String token, Derma derma) {
        DermaOrder dermaOrder = new DermaOrder();
        dermaOrder.setToken(token);
        dermaOrder.setDerma(derma);
        dermaOrder.setOrderId(nextId());
        dermaOrder.setStatus(OrderStatusEnum.PENDING.getId());
        dermaOrderMapper.insertDermaOrder(dermaOrder);
        return dermaOrder.getOrderId();
    }

    @Override
    public void updateOrder(String token, OrderStatusEnum orderStatusEnum) {
        dermaOrderMapper.updateDermaOrder(orderStatusEnum.getId(),token);
    }


    @Override
    public int nextId() {
        return sequenceMapper.nextId(sequenceMapper.DERMA_ORDER);
    }
}
