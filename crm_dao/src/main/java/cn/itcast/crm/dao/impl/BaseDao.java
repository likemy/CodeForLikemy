package cn.itcast.crm.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.crm.dao.IBaseDao;

public class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {
	//当前实体类型
	private Class<T> entityClass;
	
	/**
	 * 获取到当前实体类型???
	 */
	public BaseDao(){
		//获取Customer类型
		//this: 代表 CustomerDao
		//this.getClass(): 代表CustomerDao.class
		//getGenericSuperclass: 带有泛型的父类
		//Type t = this.getClass().getGenericSuperclass(); : 代表BaseDao<Customer>
		//强制转换 ParameterizedType  ：代表BaseDao<Customer>
		//ParameterizedType.getActualTypeArgruments(); : 代表<Customer>
		//ParameterizedType.getActualTypeArgruments()[0]: 代表Customer.class
		
		Type t = this.getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType)t;
		entityClass = (Class)pt.getActualTypeArguments()[0];
	}
	
	@Override
	public void save(T c) {
		this.getHibernateTemplate().save(c);
	}

	@Override
	public List<T> findAll() {
		//loadAll(): 加载所有数据
		return this.getHibernateTemplate().loadAll(entityClass);
	}

	@Override
	public void delete(Long id) {
		this.getHibernateTemplate().delete(get(id));
	}

	@Override
	public T get(Long id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public void update(T c) {
		this.getHibernateTemplate().update(c);
	}

	/**
	 * 查询总记录数:
	 *   select count(*) from xxx where xx=xxx and xx=xxx
	 */
	@Override
	public Long findByTotalCount(DetachedCriteria dc) {
		//执行聚合查询:count(*)
		dc.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>)this.getHibernateTemplate().findByCriteria(dc);
		return list!=null&&list.size()>0?list.get(0):0;
	}

	/**
	 * 查找数据列表
	 */
	@Override
	public List<T> findByPage(DetachedCriteria dc, Integer page,
			Integer pageSize) {
		//起始行： (当前页码-1)*页面大小
		List<T> list = (List<T>)this.getHibernateTemplate().findByCriteria(dc, (page-1)*pageSize, pageSize);
		return list;
	}
	
}
