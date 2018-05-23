package cn.itcast.crm.dao;

import cn.itcast.crm.entity.User;

public interface IUserDao extends IBaseDao<User> {
	public User findByCode(String code);
}
