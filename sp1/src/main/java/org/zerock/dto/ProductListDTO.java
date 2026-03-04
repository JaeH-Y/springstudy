package org.zerock.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {
	private Long pNo;
	private String pName;
	private String pDesc;
	private int pPrice;
	private boolean sale;
	private String writer;
	private String iUuid;
	private String iFilename;
	
	
	
	
	
	
	
	
	
	
	
	
}
