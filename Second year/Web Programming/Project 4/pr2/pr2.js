function validateForm() {
    var msg = "Campurile ";
    var ok = false;
	document.getElementsByName("nume")[0].style.borderColor = "black";
	document.getElementsByName("dataNasterii")[0].style.borderColor = "black";
	document.getElementsByName("varsta")[0].style.borderColor = "black";
	document.getElementsByName("email")[0].style.borderColor = "black";

	var x = document.forms["form"]["nume"].value;
    if (x == "" || (x.search(/[^a-zA-Z ]/)>=0)) {
		ok = true;
        msg = msg+"nume ";
        document.getElementsByName("nume")[0].style.borderColor = "red";
    }

    var x = document.forms["form"]["dataNasterii"].value;
    if (x == "") {
		if(ok ==  true){
			msg = msg + ", ";
		}
		ok = true; 
        msg = msg+"data nasterii ";
        document.getElementsByName("dataNasterii")[0].style.borderColor = "red";
    }

    var x = document.forms["form"]["varsta"].value;
    if (x == "" || isNaN(x)) {
		if(ok ==  true){
			msg = msg + ", ";
		}
		ok = true; 
        msg = msg+"varsta ";
        document.getElementsByName("varsta")[0].style.borderColor = "red";
    }

    var x = document.forms["form"]["email"].value;
    if (x == "") {
		if(ok ==  true){
			msg = msg + ", ";
		}
		ok = true; 
        msg = msg+"email ";
        document.getElementsByName("email")[0].style.borderColor = "red";
    }

    if(msg == "Campurile "){
        
        msg = "Datele sunt completate corect";
        alert(msg);
        return true;
    }
    else{
    msg = msg+"nu sunt valide !";
    alert(msg);
    return false;
    }


}