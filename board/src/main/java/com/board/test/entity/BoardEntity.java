package com.board.test.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;



@Data
@Entity(name="board")
public class BoardEntity {
	
	@Id
//	@GeneratedValue(strategy= GenerationType.AUTO)
//	UUID uuid = UUID.randomUUID();		//uuid 생성
	@Column(updatable=false)
	private String boardUuid = String.valueOf(UUID.randomUUID());							//순번
	
	@NotNull
	private String title;						//타이틀
	
	@NotNull
	@Lob
	private String contents;					//내용
	
	
//	@NotNull
////	private String uuid;						//작성자ID - unique id
	
	
	
	@ManyToOne(targetEntity=UserInfoEntity.class, fetch=FetchType.LAZY)
	@JoinColumn(name="userUuid")
	private UserInfoEntity userInfo;
	
	@Column(updatable=false)
	private LocalDateTime writeDate = LocalDateTime.now();			//작성일시
	
	private LocalDateTime updateDate = LocalDateTime.now();			//수정일시
	
	@NotNull
	private String useYn = "Y";						//사용여부
	
	@NotNull
	private long viewCnt = 0;						//뷰카운트
	
	
	
//	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name="userId")
//	private Collection<UserInfoEntity> userInfoEntity;
	
	
//	@ManyToOne(targetEntity=UserInfoEntity.class, fetch=FetchType.LAZY)
//	@JoinColumn(name="userId")
//	private UserInfoEntity userInfoEntity;
	
	
//	@ManyToOne(optional = false)
//	@JoinTable(name = "uuid",
//    joinColumns = @JoinColumn(name = "writeId"),
//    inverseJoinColumns = @JoinColumn(name = "userId"))
//	private UserInfoEntity userInfoEntity;
	
	
	
	
}
