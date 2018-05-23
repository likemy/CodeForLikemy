package cn.itcast.crm.dao.impl;

import java.util.List;

import cn.itcast.crm.dao.IUserDao;
import cn.itcast.crm.entity.User;

public class UserDao extends BaseDao<User> implements IUserDao {

	/**
	 * 根据用户账户查找是否存在
	 */
	@Override
	public User findByCode(String code) {
		List<User> list = (List<User>)this.getHibernateTemplate().find("from User where userCode=?", code);
		return list!=null&&list.size()>0?list.get(0):null;
	}
}
