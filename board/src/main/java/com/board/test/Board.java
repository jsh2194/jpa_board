package com.board.test;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import lombok.Data;



@Data
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long NO;							//순번
	
	@NotNull
	private String TITLE;						//타이틀
	
	@NotNull
	@Lob
	private String CONTENTS;					//내용
	
	
	@NotNull
	private String WRITE_ID;					//작성자ID
	
	private String UPDATE_ID;					//수정자ID
	
	@NotNull
	private LocalDateTime WRITE_DATE;			//작성일시
	
	private LocalDateTime UPDATE_DATE;			//수정일시
	
	@NotNull
	private String USE_YN;						//사용여부
	
	@NotNull
	private long VIEW_CNT;						//뷰카운트
	
	
	
	@NotNull
	private String USER_ID;						//작성자ID
	
	@NotNull
	private String USER_NAME;					//작성자이름
	
	@NotNull
	private String PWD;							//비밀번호
	

	
}
