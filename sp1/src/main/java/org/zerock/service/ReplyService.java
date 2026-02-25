package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.dto.BoardDTO;
import org.zerock.dto.BoardListPagingDTO;
import org.zerock.dto.ReplyDTO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ReplyService {
	
	private final ReplyMapper mapper;
	
	public Long addReply(ReplyDTO dto) {
		log.info("addReply............." + dto);
		
		mapper.insert(dto);
		
		Long rno = dto.getRno();
		
		return rno;
	}

	
}
