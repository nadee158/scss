/**
 * 
 */
package com.privasia.scss.cosmos.oracle.repository;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * @author Janaka
 *
 */
@NoRepositoryBean
public interface BaseRepository <T, ID extends Serializable> extends Repository<T, ID> {
	
	void delete(T deleted);
	 
    Stream<T> findAll();
     
    Optional<T> findOne(ID primaryKey);
 
    T save(T persisted);
    
    boolean exists(ID primaryKey);

}
