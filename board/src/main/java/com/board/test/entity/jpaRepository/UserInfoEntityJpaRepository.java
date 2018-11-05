package com.board.test.entity.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.board.test.entity.UserInfoEntity;


@Repository
public interface UserInfoEntityJpaRepository extends JpaRepository<UserInfoEntity, Long> {
		
	public UserInfoEntity findByUserId(String userId);
	
}
