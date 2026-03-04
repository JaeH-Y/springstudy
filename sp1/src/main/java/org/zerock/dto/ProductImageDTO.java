package org.zerock.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
	private Long iNo;
	private Long pNo;
	private String iFilename;
	private String iUuid;
	private int ord;
	private LocalDateTime reDate;
}
