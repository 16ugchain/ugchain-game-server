package com.ugc.gameserver.service;

import com.ugc.gameserver.domain.Derma;
import com.ugc.gameserver.domain.OrderStatusEnum;

/**
 * Created by fanjl on 2017/4/27.
 */
public interface DermaOrderService {
    int createOrder(String token, Derma derma);

    void updateOrder(String token, OrderStatusEnum orderStatusEnum);

    int nextId();
}
