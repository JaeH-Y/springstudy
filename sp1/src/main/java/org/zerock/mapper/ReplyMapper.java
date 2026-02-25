package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.dto.BoardDTO;
import org.zerock.dto.ReplyDTO;

public interface ReplyMapper {

	Long insert(ReplyDTO dto);
	
	ReplyDTO read(@Param("rno") Long rno);
	
	int update(@Param("dto") ReplyDTO dto);
	
	int delete(@Param("rno") Long rno);
	
	List<ReplyDTO> repliesOfBoard(@Param("bno") Long bno,
								@Param("skip") int skip,
								@Param("limit") int limit);
	
	int countOfReplies(@Param("bno") Long bno); 
	
	
}
