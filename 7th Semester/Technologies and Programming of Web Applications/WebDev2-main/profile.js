console.log("Loading js..");


window.onload = function(){
    // Constraint validation API

    //On form submittion
    const form = document.getElementById("form");
    form.addEventListener('submit', function (event) {
        //Prevent page to reload
        event.preventDefault();

        //Prepare data
        let email = document.getElementById("email").value;
        let pwd = document.getElementById("pwd").value;

        // Data JSON
        let formData = {
            email: email,
            pwd: pwd
        }

        //POST data using Fetch API
        console.log("Client submits login form");
        // console.log(formData)


        let url = "http://localhost:8080/login";
        // Post data using Fetch API
        postForm(url, formData).then(function(statuscode){
            //If there is no other user with same email
            if (statuscode == 201){
                // Show message for successfull login
                document.getElementById("formDiv").classList.add("hidden");
                document.getElementById("successfullLoginMessage").classList.remove("hidden");

                fetchAndInsertHTML(url + '/' + email, "successfullLoginMessage", profiletemplate);
            } else {
                //If there is a problem with login (can't find email and password on users data)
                alert("Το email ή ο κωδικός είναι λάθος. Προσπαθήστε ξανά.")
                return;
            }

        });

      });
}

