package com.genericsdao.dao;

import com.genericsdao.bean.Shop;

public interface IShopDao extends IBaseDao<Shop> {
    Shop findById(int id);
}
