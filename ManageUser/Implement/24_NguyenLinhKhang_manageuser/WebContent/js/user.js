//Ẩn hiện vùng trình độ tiếng Nhật
function hiddenData() {
	// lấy về vùng trình độ tiếng Nhật
	var levelJapan = document.getElementById('levelJapan');
	// Nếu đang ẩn
	if (levelJapan.style.display === "none") {
		// Thì set display để hiển thị
		levelJapan.style.display = "block"
	} else { // Ngược lại, ẩn đi
		levelJapan.style.display = "none";
	}
}

//Click xóa ở ADM005
function alertDelete(mess) {
	// show câu thông báo
	if(confirm(mess)) { // nếu click ok
	// Lấy về form cũ trên màn hình ADM005
	var form = document.getElementById("deleteForm");
	// Truyền action cho form có id là deleteForm
	form.action = "DeleteUser.do";
	// Truyền method cho form có id là deleteForm
	form.method = "POST";
	// Submit form
	form.submit();
	}
}