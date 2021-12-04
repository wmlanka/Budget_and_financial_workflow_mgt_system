function createAjaxRequest() {
	var request = false;
    try {
    	request = new ActiveXObject('Msxml2.XMLHTTP');
	}catch (err2) {
    	try {
			request = new ActiveXObject('Microsoft.XMLHTTP');// code for IE6, IE5
      	}catch (err3) {
			try {
				request = new XMLHttpRequest();// code for IE7+, Firefox, Chrome, Opera, Safari
			}catch (err1){
				request = false;
			}
		}
	}
	return request;
}