package com.cos.blog.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;

import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;


import lombok.RequiredArgsConstructor;


//스프링이 컴포넌트 스캔을 통해서 Bean에 등록 해줌. => Ioc해줌
@Service
@RequiredArgsConstructor
public class BoardService {
	
	/* 방법1.
	 * @Autowired private BoardRepository boardRepository;
	 * @Autowired private ReplyRepository replyRepository;
	 */
	
	/*방법1. @Autowired 논리
	 * private BoardRepository boardRepository;
	 * private ReplyRepository replyRepository;
	 * 
	 * public BoardService(BoardRepository bRepo, ReplyRepository rRepo) {
	 * this.boardRepository = bRepo; this.replyRepository = rRepo; }
	 */
	
	//방법3
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
	
	@Transactional
	public void 글쓰기(Board board, User user) {//title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board); 
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");			
				});
	}
	
	@Transactional
	public void 글삭제하기 (int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {//수정하기 위해 영속성 필요
		Board board = boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");			
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시 (service 종료시) 트랜잭션이 종료된다.
		//이때 더티체킹 - 자동업데이트가 됨 - db flush 
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
	 int result =replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
		System.out.println("댓글 삭제 : "+replyId);
	}
}
