package com.board.test.entity.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.board.test.entity.BoardEntity;


@Repository
public interface BoardEntityJpaRepository extends JpaRepository<BoardEntity, Long> {

}
