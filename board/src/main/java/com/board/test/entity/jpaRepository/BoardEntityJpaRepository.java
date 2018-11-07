package com.board.test.entity.jpaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.board.test.entity.BoardEntity;


@Repository
public interface BoardEntityJpaRepository extends JpaRepository<BoardEntity, Long> {

	//boardUuid로 게시글 상세 검색
	public BoardEntity findByBoardUuid(String boardUuid);

	public List<BoardEntity> findByUseYnOrderByWriteDateDesc(String useYn);

	public Page<BoardEntity> findByUseYnOrderByWriteDateDesc(String useYn, Pageable pageable);
	
	
	
}
