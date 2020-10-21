package com.icia.memberboard.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class PageDTO {
	private int page;
	private int maxPage;
	private int startPage;
	private int endPage;
	private int startRow;
	private int endRow;
}
