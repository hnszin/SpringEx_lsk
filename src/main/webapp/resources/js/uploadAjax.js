$(document).ready(function() {
	// 파일의 크기와 확장자(jpg..등)를 검사하는 함수 선언
	
	// 서버에서 설정해놓은 파일크기 설정
	var maxSize=5242880; //5MB
	// 서버에서 허용가능한 확장자 설정(정규식:규칙있는 것에 검증하고자 할 때 쓰는 식)
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	
	function checkExtension(fileSize,fileName){//파일의 크기, 확장자
		//사용자가 선택한 파일의 크기 >= 서버에서 설정해놓은 파일의 크기이면,
		if(fileSize>=maxSize){
			alert("선택하신 파일의 크기가 큽니다.")
			return false;
		}
		//사용자가 선택한 파일의 확장자가 서버에서 설정해놓은 파일의 확장자이면,
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드 할 수 없습니다.")
			return false;
		}
		return true; // 위 if문 2개가 아니면 true값을 던져라.
	}
	
	var formObj = $("form[role='form']")
	// 글쓰기 버튼을 클릭하면
	$("input[type='submit']").on("click", function(e) {
		
		e.preventDefault();
		var str="";
		// 각 li태그에 있는
		$("#uploadResult ul li").each(function(i,obj){
			console.log(obj);
			// data선택자를 이용하여 input태그의 value값으로 셋팅
			/*
			 * data 함수
			 * <span>
			 * $("span").data("age",13) => <span data-age="13"> data힘수 괄호안에 매개변수가 두개면 setter
			 * <span data-age="13">
			 * $("span").data("age") => data함수 괄호안에 매개변수가 하나면 getter*/
			str+="<input type='text' name='attachList["+i+"].fileName' value='"+$(obj).data("filename")+"'>"
			str+="<input type='text' name='attachList["+i+"].uuid' value='"+$(obj).data("uuid")+"'>"
			str+="<input type='text' name='attachList["+i+"].uploadPath' value='"+$(obj).data("path")+"'>"
			str+="<input type='text' name='attachList["+i+"].image' value='"+$(obj).data("type")+"'>"
		})
		formObj.append(str).submit();
		
		
		
		
		
		
	})
	

	// 파일 선택의 내용이 변경되면
	$("input[type='file']").on("change", function() {
		// 가상의 form태그 : formData라는 객체
		var formData = new FormData();
		// <input type="file" name="uploadFile" multiple>을 inputFile변수에 대입하여 선언
		var inputFile = $("input[name='uploadFile']");
		// inputFile변수값에 배열을 설정하고 파일에 대한 정보를 files변수에 대입하여 선언
		var files = inputFile[0].files;
		// 콘솔창에 내가 선택한 파일들에 대한 정보를 띄움
		console.log(files);
		
		for (var i = 0; i < files.length; i++) {
			// 파일의 크기가 이상이면
			if(!checkExtension(files[i].size,files[i].name)){
				// 업로드를 하지않는다.
				return false;
			}
			formData.append("uploadFile", files[i]); // append : 배열에 순서대로
														// 추가하는 역할
		}
		// ajax : 자바스크립트와 컨트롤러 연결하는 역할
		// formData자체를 데이터로 전송하고,
		// formData를 데이터로 전송할때는 processData와 contentType은 반드시 false
		$.ajax({
			url : "/uploadAjax",
			type : "post", // 무조건 post
			data : formData,
			processData : false,
			contentType : false,
			success : function(result) { // 사용자가 선택한 파일을 원하는 경로에 성공적으로 업로드 한 후 실행할 함수
				// showUploadedFile 함수 호출
				showUploadedFile(result);
			}
		})// $.ajax 끝
	})
})// $(document).ready(function(){}) 끝

// 사용자가 선택한 파일을 원하는 경로에 성공적으로 업로드 한 후 웹브라우저에 파일을 띄우는 함수 선언(showUploadedFile)
function showUploadedFile(uploadresultArr){
	var str="";
	$(uploadresultArr).each(function(i,obj){// 배열에서 사용할 수 있는 간략한 jquery for문이다  i : index번호, obj : i의 값
		// 사용자가 업로드 한 파일의 타입이 이미지가 아니면(excel문서파일, ppt파일),
		if(!obj.image){
			var fileCallPath=encodeURIComponent(obj.uploadPath+obj.uuid+"_"+obj.fileName)	//endcodeURIComponent:URI로 데이터를 전달하기 위해서 문자열을 인코딩
			str+="<li data-path='"+obj.uploadPath+"'";
			str+="data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"'data-type='"+obj.image+"'>";
			str+="<li><a href='download?fileName="+fileCallPath+"'>"+obj.fileName+"</a></li>"
		
		}else{// 사용자가 업로드 한 파일의 타입이 이미지이면 (.jpg, .png, .gif)
			var fileCallPath=encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName)	//endcodeURIComponent
			console.log(fileCallPath)
				// img태그를  사용해서 웹브라우저에 이미지 출력
			str+="<li data-path='"+obj.uploadPath+"'";
			str+="data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"'data-type='"+obj.image+"'>";
			str+="<img src='/display?fileName="+fileCallPath+"'></li>"
		}
	})
	$("#uploadResult ul").html(str)
}