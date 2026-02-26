<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/includes/header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
	
<div class="row justify-content-center">
  <div class="col-lg-12">
   	<div class="card shadow mb-4">
     	<div class="card-header py-3">
      	<h6 class="m-0 font-weight-bold text-primary">Board Read</h6>
     	</div>
			<div class="card-body">
			      	
				<div class="mb-3 input-group input-group-lg">
			    <span class="input-group-text">Bno</span>
			    <input type="text" class="form-control" value="${board.bno}" readonly>
			  </div>
			
			  <div class="mb-3 input-group input-group-lg">
			    <span class="input-group-text">Title</span>
			    <input type="text" name="title" class="form-control" value="${board.title}" readonly>
			  </div>
			
			  <div class="mb-3 input-group input-group-lg">
			    <span class="input-group-text">Content</span>
			    <textarea class="form-control" name="content" rows="3" readonly
			    	style="resize: none;">${board.content} </textarea>
			  </div>
			
			  <div class="mb-3 input-group input-group-lg">
			    <span class="input-group-text">Writer</span>
			    <input type="text" name="writer" class="form-control" value="${board.writer}" readonly>
			  </div>
			
			  <div class="mb-3 input-group input-group-lg">
			    <span class="input-group-text">RegDate</span>
			    <input type="text" name="regDate" class="form-control" value="${board.regDate}" readonly>
			  </div>
			
			  <div class="float-end">
			    <button type="button" class="btn btn-info btnList">LIST</button>
			    <c:if test="${!board.delFlag}">
			    	<button type="button" class="btn btn-warning btnModify">MODIFY</button>
			    </c:if>
			  </div>
			</div>
   	</div>
  </div>
  <!-- 댓글 작성 div -->
  <div class="col-lg-12">
		<div class="card shadow mb-4">
			<div class='m-4'>
				<!--댓글 작성 폼 -->
				<form id="replyForm" class="mt-4">
					<!-- 게시글 번호 hidden 처리 -->
					<input type="hidden" name="bno" value="${board.bno}" />
					
					<div class="mb-3 input-group input-group-lg">
						<span class="input-group-text">Replyer</span>
						<input type="text" name="replyer" class="form-control" required />
					</div>
	
					<div class="mb-3 input-group">
						<span class="input-group-text">Reply Text</span>
						<textarea name="replyText" class="form-control" rows="3" required
							style="resize: none;"></textarea>
					</div>
					
					<div class="text-end">
						<button type="submit" class="btn btn-primary addReplyBtn">Submit Reply</button>
					</div>
				</form>
				<!-- 댓글 작성 폼 끝 -->
			</div>
		</div>
	</div>
	<!-- 댓글 뷰 div -->
	<div class="col-lg-12">
		<div class="card shadow mb-4">
			<div class='m-4'>
				<!--댓글 목록 -->
				<ul class="list-group replyList">
				</ul>
				<!-- 댓글 목록 -->
				<!-- 페이징 -->
				<div aria-label="댓글 페이지 네비게이션" class="mt-4">
					<ul class="pagination justify-content-center">
					</ul>
				</div>
				<!-- 페이징 끝 -->
			</div>
		</div>
	</div>
</div>

<!-- 댓글 상세 모달 -->
<div class="modal fade" id="replyModal" tabindex="-1" aria-labelledby="replyModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="replyModalLabel">댓글 수정 / 삭제</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			
			<div class="modal-body">
				<form id="replyModForm">
					<input type="hidden" id="replyRno" name="rno">
					<div class="mb-3">
						<label for="replyText" class="form-label">댓글 내용</label>
						<input type="text" name="replyText" id="replyText" class="form-control">
					</div>
				</form>
      </div>
      
      <div class="modal-footer">
				<button type="button" class="btn btn-primary btnReplyMod">수정</button>
				<button type="button" class="btn btn-danger btnReplyDel">삭제</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/includes/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script type="text/javascript" defer>
	const listBtn = document.querySelector('.btnList');
	const modifyBtn = document.querySelector('.btnModify');

	const replyForm = document.querySelector('#replyForm');
	const replyBtn = document.querySelector('.addReplyBtn');
	
	listBtn.addEventListener('click', () => {
        location.href = '/board/list';
    });
	
	modifyBtn.addEventListener('click', () => {
        location.href = '/board/modify/${board.bno}';
    });
	
	replyBtn.addEventListener('click', e => {
		// 기본 동작(버블링) 차단 -> form의 기본 submit을 막고 AJAX 요청만 수행하도록 처리
		// preventDefault를 구현하지 않는 경우 AJAX 요청 직후 form submit이 수행됨 -> 페이지 이동 -> JS 실행 흐름 끊김
		e.preventDefault();
		
		// 상위 클릭 로직을 차단하기 위한 방어 코드로 상위 이벤트가 있을 때만 의미 있음(선택적 적용)
		e.stopPropagation();
		
		// FormData: 
		// HTML <form> 요소의 입력값을 이름–값(key–value) 쌍 형태로 수집하여
		// HTTP 요청 본문(request body)으로 전송하기 위한 웹 표준 객체
		// 항상 multipart/form-data 방식 => 파일 업로드에 주로 쓰임
		// 파일 있으면 FormData, 없으면 JSON도 고려 가능
		// 이 한 줄로 form 안의 모든 입력 요소를 자동 수집(이때 name 속성 필수)
		const formData = new FormData(replyForm);
		
		axios.post('/replies', formData).then(res => {
			
			replyForm.reset(); // 폼 초기화
			getReplies({goLast: true});
		});
	});
	// 댓글 목록 요청
	let currentPage = 1;	// 현재 댓글 페이지(초기값 부여)
	let currentSize = 10; // 한번에 보여질 댓글 개수(초기값 부여)
	
	const bno = ${board.bno}; // 게시글 번호
	
	function getReplies({goLast, pageNum = 1}){
		// 백틱으로 연결해서 변수까지 사용해도 되는데, 그럴 때 EL을 escape 시켜줘야함
		// axios.get(`/replies/\${bno}/list`, )
		axios.get('/replies/' + bno + '/list', {
			params: {
				page: pageNum || currentPage, // pageNum이 undefined면 currentPage 사용
				size: currentSize
			}
		}).then(res => {
			const data = res.data;
			console.log(data);
			// 마지막 댓글 페이지 호출
			// ES6 문법: 구조 분해 할당(비구조화 할당)
			// => 구조를 분해 해 각 변수에 값을 할당하는 방식(담을 변수와 꺼낼 변수의 이름을 맞춰줘야 함)
			const {totalCount, page, size} = data;
			
			if(goLast && totalCount > (page * size)){
				// 마지막 페이지 계산
				const lastPage = Math.ceil(totalCount / size);
				
				getReplies({pageNum: lastPage}); // 마지막 페이지 호출
			}
			else{
				currentPage = page; // 현재 페이지 업데이트
				currentSize = size;
				printReplies(data);
			}
			
		})
	}
	getReplies({goLast: true}); // 페이지 로드 시 댓글 목록 요청
	
	// 댓글 출력
	const replyList = document.querySelector('.replyList');
	
	function printReplies(data) {
		// ES6 문법 활용
		const {replyDTOList, page, size, prev, next, start, end, pageNums} = data;
		
		let liStr = '';
		
		for (const replyDTO of replyDTOList) {
			liStr += `
				<li class="list-group-item" data-rno="\${replyDTO.rno}">
					<div class="d-flex justify-content-between">
						<div>
							<strong>\${replyDTO.rno}</strong> - \${replyDTO.replyText}
						</div>
						<div class="text-muted small">
							\${replyDTO.replyDate}
						</div>
					</div>
					<div class="mt-1 text-secondary small">
						\${replyDTO.replyer}
					</div>
				</li>
			`;
		}
		
		replyList.innerHTML = liStr;
		
		// 댓글 페이징 처리 
		let pagingStr = '';
		
		if (prev) {
			pagingStr += `
				<li class="page-item">
					<a class="page-link" href="\${start -1}" tabindex="-1">이전</a>
				</li>
			`;
		}
		
		for (const i of pageNums) {
			pagingStr += `
				<li class="page-item \${i === page ? 'active' : ''}">
					<a class="page-link" href="\${i}">\${i}</a>
				</li>
			`;
		}
		
		if (next) {
			pagingStr += `
				<li class="page-item">
					<a class="page-link" href="\${end + 1}">다음</a>
				</li>
			`;
		}
		
		document.querySelector(".pagination").innerHTML = pagingStr;
	}
	
	// 각 페이지 번호의 이벤트 처리
	document.querySelector(".pagination").addEventListener('click', e => {
		e.preventDefault();
		e.stopPropagation();
		
		const target = e.target;
		console.log("Clicked element: ", target);
		const href = target.getAttribute('href');
		
		if(!href) return; // href가 없는 경우 무시
		
		console.log("Clicked pageNum: " + href);
		
		getReplies({pageNum: href}); // 해당 페이지 번호로 댓글 목록 요청
	});
	
	// 댓글 목록에서 특정 댓글에 대한 클릭 이벤트 처리
	const replyModal = new bootstrap.Modal(document.querySelector('#replyModal'));
	const replyModForm = document.querySelector('#replyModForm');
		
	replyList.addEventListener('click', e => {
		// replyModal.show(); // 모달창 동작 확인용
		
		// 가장 가까운 상위 요소 찾기(자기 자신 포함)
		const li = e.target.closest("li");
		// data-: 요소에 데이터를 저장하는 용도(사용자 정의 속성을 만듦)
		// input처럼 value가 있는 요소가 아닐 때 유용
		// dataset 값 얻어오기
		// const rno = li.getAttribute('data-rno'); // getAttribute로 직접 접근
		//const liRno = 1;
		const liRno = li.dataset.rno;
		
		if(!liRno) return; // rno가 없는 경우 무시
		
		axios.get('/replies/' + liRno).then( res => {
			const {replyText, rno, replyer, delFlag} = res.data;
			
			if(delFlag){
          alert("삭제된 댓글입니다.");
          return;
      }
			
			const modalInput = document.querySelector('#replyText');
			modalInput.value = replyText; // 모달의 input에 댓글 내용 채우기
			const modalRno = document.querySelector("#replyRno"); 
			modalRno.value = rno; // 모달의 hidden input에 댓글 번호 채우기
			
			replyModal.show(); // 모달창 띄우기
		})
	});
	
	document.querySelector(".btnReplyMod").addEventListener('click', e =>{
		
		const rno = document.querySelector("#replyRno").value;
		
		const formData = new FormData(replyModForm);
		axios.put('/replies/' + rno, formData).then(res => {
	      replyModal.hide(); // 모달창 닫기
	      getReplies({pageNum: currentPage});
	  });
		
	});
	
	document.querySelector(".btnReplyDel").addEventListener('click', e =>{
		
		const formData = new FormData(replyModForm);
		
		const rno = formData.get('rno');
		console.log("rno: " + rno);
		
		// Axios로 서버의 삭제 기능을 호출
		axios.delete('/replies' + rno).then(res => {
			const data = res.data;
			console.log(data);
			
			// 유효성 검증
			if(data.result === 'deleted'){
				replyModal.hide();
				
				getReplies({pageNum: currentPage});
			}
		});
	});
</script>


















