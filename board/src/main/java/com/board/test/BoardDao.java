package com.board.test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardDao extends JpaRepository<BoardEntity, Long> {

}
