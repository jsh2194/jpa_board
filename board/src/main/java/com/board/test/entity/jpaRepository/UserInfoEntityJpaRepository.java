package com.board.test.entity.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.board.test.entity.UserInfoEntity;


@Repository
public interface UserInfoEntityJpaRepository extends JpaRepository<UserInfoEntity, Long> {
	
	//아이디로 유저검색
	public UserInfoEntity findByUserId(String userId);

	//로그인 정보 검색
	public UserInfoEntity findByUserIdAndPwd(String userId, String pwd);
	
}
