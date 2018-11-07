package com.board.test.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;



@Data
@Entity(name="userInfo")
public class UserInfoEntity {
	
//	@Id
//	@GeneratedValue(strategy= GenerationType.AUTO)
//	private long no;							//순번
	

	@Id
	@NotNull
	@Column(updatable=false)
//	private String user_uuid;						//unique id
	private String userUuid = String.valueOf(UUID.randomUUID());							//순번
	
	
	@NotNull
	private String userId;						//작성자ID
	
	@NotNull
	private String userName;					//작성자이름
	
	@NotNull
	private String pwd;							//비밀번호
	
	
	@NotNull
	@Column(updatable=false)
	private String writeId;					//작성자ID
	
	private String updateId;					//수정자ID
	
	@NotNull
	@Column(updatable=false)
	private LocalDateTime writeDate;			//작성일시
	
	private LocalDateTime updateDate;			//수정일시
	
	@NotNull
	private String useYn = "Y";						//사용여부
	

	
}
