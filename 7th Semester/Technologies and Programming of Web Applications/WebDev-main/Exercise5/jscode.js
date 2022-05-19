var password = document.getElementById("pwd"),
confirm_password = document.getElementById("conf-pwd");
var country = document.getElementById("country");
var age = document.getElementById("age");

function validatePassword(){
    if(password.value != confirm_password.value){
        confirm_password.setCustomValidity("Ο κωδικός δεν ταιριάζει");
    } else {
        confirm_password.setCustomValidity("")
    }
}

function validateCountry(){
    if(country.value=="Άλλο"){
        country.setCustomValidity("Δεν παρέχουμε υπηρεσίες στην συγκεκριμένη χώρα");
    } else {
        country.setCustomValidity("");
    }
}

function validateAge(){
    if(age.value <18){
        age.setCustomValidity("Δεν επιτρέπεται η εγγραφή σε άτομα κάτω των 18");
    } else {
        age.setCustomValidity("");
    }
}
password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;
country.onchange = validateCountry;
age.onchange = validateAge;