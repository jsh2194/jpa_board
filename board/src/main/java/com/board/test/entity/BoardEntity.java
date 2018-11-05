package com.board.test.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import lombok.Data;



@Data
@Entity(name="board")
public class BoardEntity {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long no;							//순번
	
	@NotNull
	private String title;						//타이틀
	
	@NotNull
	@Lob
	private String contents;					//내용
	
	
	@NotNull
	private String writeId;					//작성자ID
	
	private String updateId;					//수정자ID
	
	@NotNull
	private LocalDateTime writeDate;			//작성일시
	
	private LocalDateTime updateDate;			//수정일시
	
	@NotNull
	private String useYn;						//사용여부
	
	@NotNull
	private long viewCnt;						//뷰카운트
	
	
}
