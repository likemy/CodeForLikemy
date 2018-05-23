package cn.itcast.crm.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
public interface IBaseDao<T> {
	public void save(T c);
	public List<T> findAll();
	public void delete(Long id);
	public T get(Long id);
	public void update(T c);
	public Long findByTotalCount(DetachedCriteria dc);
	public List<T> findByPage(DetachedCriteria dc, Integer page, Integer pageSize);
}
