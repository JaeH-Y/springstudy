/**
 * 
 */

const formObj = document.querySelector('#actionForm');

const listBtn = document.querySelector('.btnList');
const modBtn = document.querySelector('.btnModify');
const delBtn = document.querySelector('.btnRemove');

listBtn.addEventListener('click', function() {
	if(formObj){
		formObj.action = '/board/list';
		formObj.method = 'get';
		formObj.submit();
		}
	});

modBtn.addEventListener('click', function() {
	if(formObj){
		formObj.action = '/board/modify';
		formObj.method = 'post';
		formObj.submit();
		}
	});
if(delBtn){
	delBtn.addEventListener('click', function() {
		formObj.action = '/board/remove';
		formObj.method = 'post';
		formObj.submit();
		});
	}