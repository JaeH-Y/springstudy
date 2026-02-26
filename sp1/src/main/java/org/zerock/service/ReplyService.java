package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.dto.BoardDTO;
import org.zerock.dto.BoardListPagingDTO;
import org.zerock.dto.ReplyDTO;
import org.zerock.dto.ReplyListPagingDTO;
import org.zerock.exception.ReplyException;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional	// 클래스 내 모든 메서드가 트랜잭션 적용 대상
public class ReplyService {
	
	private final ReplyMapper mapper;
	
	public void addReply(ReplyDTO dto) {
		log.info("addReply............." + dto);
		try {
			mapper.insert(dto);
			Long rno = dto.getRno();
		}
		catch (Exception e) {
			throw new ReplyException(500, "INSERT ERROR");
		}
	}
	
	public ReplyDTO getOneReply(Long rno) {
		log.info("getOneReply............." + rno);
		try {
			
			ReplyDTO dto = mapper.read(rno);
			if(dto == null) {
				throw new ReplyException(404, "NOT FOUND");
			}
			return dto;
		}
		catch (Exception e) {
			throw new ReplyException(500, "READ ERROR");
		}
	}
	
	public void modifyReply(ReplyDTO dto) {
		log.info("modifyReply............." + dto);
		try {
			int result = mapper.update(dto);
			if(result == 0) {
				throw new ReplyException(404, "NOT FOUND");
			}
		}
		catch (Exception e) {
			throw new ReplyException(500, "UPDATE ERROR");
		}
	}
	
	public void removeReply(Long rno) {
		log.info("removeReply............." + rno);
		try {
			int result = mapper.delete(rno);
			if(result == 0) {
				throw new ReplyException(404, "NOT FOUND");
			}
		}
		catch (Exception e) {
			throw new ReplyException(500, "DELETE ERROR");
		}
	}
	
	public ReplyListPagingDTO listOfBoard(Long bno, int page, int size) {
		try {
			int skip = (page - 1) * size;
			
			List<ReplyDTO> list = mapper.repliesOfBoard(bno, skip, size);
			
			int count = mapper.countOfReplies(bno);
			
			return new ReplyListPagingDTO(list, count, page, size);
		}
		catch (Exception e) {
			throw new ReplyException(500, e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
