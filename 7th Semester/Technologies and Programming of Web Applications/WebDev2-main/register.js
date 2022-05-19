console.log("Loading js..");


window.onload = function(){
    // Constraint validation API
    const pwdelement = document.getElementById("pwd");
    const confpwdelement = document.getElementById("confpwd");

    //Validate password on user input
    pwdelement.onchange = validatePassword;
    confpwdelement.onkeyup = validatePassword;
    
    //On form submittion
    const form = document.getElementById("form");
    form.addEventListener('submit', function (event) {
        //Prevent page to reload
        event.preventDefault();

        //It is impossible to submit with unvalidated data
        //Password Validation
        if (pwdelement.value != confpwdelement.value){
            alert("Ο κωδικός δεν ταιριάζει.")
            return;
        }
        

        //Prepare data
        let fname = document.getElementById("fname").value;
        let lname = document.getElementById("lname").value;
        let address = document.getElementById("address").value;
        let tel = document.getElementById("tel").value;
        let morfotikoepipedo = document.getElementById("morfotikoepipedo").value;
        let email = document.getElementById("email").value;
        let pwd = pwdelement.value;

        // Data JSON
        let formData = {
            fname: fname,
            lname: lname,
            address: address,
            tel: tel,
            morfotikoepipedo: morfotikoepipedo,
            email: email,
            pwd: pwd
        }

        //POST data using Fetch API
        console.log("Client submits form");

        let url = "http://localhost:8080/register";
        // Post data using Fetch API
        postForm(url, formData).then(function(statuscode){
            //If there is no other user with same email
            if (statuscode == 201){
                // Show message for going back to main menu (successfull register)
                document.getElementById("formDiv").classList.add("hidden");
                document.getElementById("successfullRegisterMessage").classList.remove("hidden");
            } else {
                //If there is a problem with the email
                alert("Το email έχει χρησιμοποιηθεί ήδη. Δοκιμάστε άλλο email.")
                return;
            }

        });

        // console.log(statuscode)
        // console.log(typeof statuscode)




      });

    function validatePassword(){
        // console.log(pwdelement.value);
        // console.log(confpwdelement.value);
        if(pwdelement.value != confpwdelement.value){
            confpwdelement.setCustomValidity("Ο κωδικός δεν ταιριάζει");
        } else{
            confpwdelement.setCustomValidity("");
        }
    }


}





