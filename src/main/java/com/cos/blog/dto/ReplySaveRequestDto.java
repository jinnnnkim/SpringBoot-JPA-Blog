package com.cos.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//필요한 데이터를 한번에 받을 수 있음
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplySaveRequestDto {
	
	private int userId;
	private int boardId;
	private String content;

}
