package com.board.test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoDao extends JpaRepository<UserInfoEntity, Long> {

}
