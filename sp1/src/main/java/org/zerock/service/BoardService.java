package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.dto.BoardDTO;
import org.zerock.dto.BoardListPagingDTO;
import org.zerock.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class BoardService {
	
	private final BoardMapper boardMapper;
	
	// 게시물 목록 처리: BoardMapper에서 나온 BoardDTO 목록을 반환하도록 작성
	public List<BoardDTO> getList() {
		return boardMapper.selectList();
	}
	
	public BoardListPagingDTO getList(int page, int size, String typeStr, String keyword) {
		// 페이지 번호가 0보다 작으면 무조건 1페이지
		page = page <= 0 ? 1 : page;
		// 사이즈가 10보다 작거나 100보다 크면 10
		size = (size < 10 || size > 100) ? 10 : size;
		
		int skip = (page - 1) * size; // 2페이지라면 (2 - 1) * 10 이 되어야 함
		
		String[] types = typeStr != null ? typeStr.split("") : null;
		
		List<BoardDTO> list = boardMapper.listSearch(skip, size, types, keyword);
		
		int total = boardMapper.listCount(types, keyword);
		
		return new BoardListPagingDTO(list, total, page, size, typeStr, keyword);
	}
	
	public Long addBoard(BoardDTO dto) {
		boardMapper.insert(dto);
		return dto.getBno();
	}
	
	public BoardDTO getBoard(Long bno) {
		return boardMapper.selectOne(bno);
	}
	
	public int modifyBoard(BoardDTO dto) {
		return boardMapper.updateOne(dto);
	}
	
	public int deleteBoard(Long bno) {
		return boardMapper.removeOne(bno);
	}

	public List<BoardDTO> list2(int skip, int size) {
		return boardMapper.list2(skip, size);
	}

	
}
