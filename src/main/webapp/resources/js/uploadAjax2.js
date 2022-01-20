$(document).ready(function(){
	// 글쓰기 버튼을 클릭하면 실행될 함수
	$("input[type='submit']").on("click",function(){
		// formData 객체 선언
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		// 콘솔창에 선택한 파일의 정보를 띄우는 변수
		var files = inputFile[0].files;
		console.log(files);
		
		//add File Data to formData
		for(var i = 0; i <files.length; i++){
			formData.append("uploadFile",files[i]);
		}
		
		// ajax : 자바스크립트와 컨트롤러 연결하는 역할
		// formData자체를 데이터로 전송하고, formData를 데이터로 전송할때는 processData와 contentType은 반드시 false
		$.ajax({
			url: '/uploadAjax2Action',
			processData: false,
			contentType: false,
			data: formData,
			type: 'POST',
			success: function(result){// 사용자가 선택한 파일을 원하는 경로에 성공적으로 업로드 한 후 실행할 함수
				alert("uploaded");
//				alert(result);
			}
		})// $.ajax
	})
})