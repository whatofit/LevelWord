package com.genericsdao.dao;

import com.genericsdao.bean.Shop;

public interface ShopDao extends BaseDao<Shop> {
	Shop findById(int id);
}
