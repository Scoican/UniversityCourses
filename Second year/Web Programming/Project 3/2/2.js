function validateForm() {
    let name = document.getElementById("name")
    let dob = document.getElementById("dateOfBirth")
    let age = document.getElementById("age")
    let email = document.getElementById("email")

    let error = "Campurile "
    let ok = true;

    if (name.value === "") {
        error = error.concat("nume si ")
        document.getElementById("name").style.border = "1px solid red";
        ok = false
    }

    let date = new Date(dob.value);
    let year = date.getFullYear();
    if (year > 2000 || year < 1890 || date == "Invalid Date") {
        error = error.concat("data nasterii si ")
        document.getElementById("dateOfBirth").style.border = "1px solid red";
        ok = false
    }

    if (age.value < 18) {
        error = error.concat("varsta si ")
        document.getElementById("age").style.border = "1px solid red";
        ok = false
    }

    let indexEmail = email.value.indexOf("@");
    console.log(indexEmail);
    console.log(email.value.length);
    if (indexEmail === -1 || indexEmail === email.value.length - 1) {
        error = error.concat("email ")
        document.getElementById("email").style.border = "1px solid red";
        ok = false
    }
    error = error.concat("nu sunt completate corect!")
    console.log(error)
    if (ok) {
        alert("Datele sunt completate corect")
    } else {
        alert(error)
    }
    return ok;

}