package org.pssframework.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface Manager<E, PK extends Serializable> {

	public abstract E getById(PK id) throws DataAccessException;

	public abstract List<E> findAll() throws DataAccessException;

	public abstract List<E> findAll(String sql, Map<String, ?> map) throws DataAccessException;

	/** 根据id检查是否插入或是更新数据 */
	public abstract void saveOrUpdate(E entity) throws DataAccessException;

	/** 插入数据 */
	public abstract void save(E entity) throws DataAccessException;

	public abstract void removeById(PK id) throws DataAccessException;

	public abstract void update(E entity) throws DataAccessException;

	public abstract boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException;

}