package org.linlinjava.litemall.db.dao;

import org.linlinjava.litemall.db.domain.LitemallStoreUser;

import java.util.List;

public interface LitemallSUMapper {
    List<LitemallStoreUser> selectByOWay(Integer storeId);
}
