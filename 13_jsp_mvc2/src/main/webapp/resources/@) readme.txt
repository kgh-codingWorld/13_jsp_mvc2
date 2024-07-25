[ CKEditor4 참조 메뉴얼 ]

	1) https://ckeditor.com/ckeditor-4/download/ 사이트에 접속해서 에디터를 다운로드 받는다.
	
	2) 다운로드받은 에디터를 웹 프로젝트안으로 이동한다.
	
	3) jsp파일에서 다운받은 ckeidtor.js를 import한다.
	       예시) <script src="ckeditor/ckeditor.js"></script>
	
	4) <textarea>태그 아래에 CKEDITOR.replace()함수를 이용하여 textarea태그를 ckeditor로 전환한다.
	     <script>CKEDITOR.replace("textarea태그의 name명");</script>
	
	[ 참고 ] 
	  - validation check와 같은 js 작업들은 별도로 구현해야한다.     
	