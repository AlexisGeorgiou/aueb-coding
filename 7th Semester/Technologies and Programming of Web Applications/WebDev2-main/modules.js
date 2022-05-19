//This file is loaded first on the HTML code, and the browser recognize the function on the page's js

function fetchAndInsertHTML(url, elementID, template, alert = false){
    //Request data
    let myHeaders = new Headers();
    myHeaders.append("Accept","application/json");

    let init = {
        method: "GET",
        headers: myHeaders
    }

    fetch(url, init)
    .then(response => response.json())
    .then(obj => {
        console.log("Received object:", obj)

        if (alert == true && obj.length == 0){
            // No data received
            alert("Δεν βρέθηκε κάνενα μάθημα με την λέξη που δώσατε. Προσπαθήστε πάλι με άλλη λέξη.");
            return false;  
        } else {
            // If we receive data
            // Identify element
            let elementHTML = document.getElementById(elementID);

            // obj is an array with every lesson or category

            //Use template and add the html code
            let content = template(obj);
            elementHTML.innerHTML = content; 

            //Display the section when loaded
            elementHTML.classList.remove("hidden");
        }
    })
    .catch(error => {console.log(error)
    })
}

async function postForm(url, formData){

    let myHeaders = new Headers();
    myHeaders.append('Content-Type', 'application/json');

    let formStr = JSON.stringify(formData);
    //formData is JSON, formStr is stringified
    //console.log(formData);
    let init = {
        method: "POST",
        headers: myHeaders,
        body: formStr
    }

    //await implemantion (to be able to return statuscode)
    const response = await fetch(url, init);
    const statuscode = await response.status;
    return statuscode; //Promise that contains statuscode

    //.then implemantation

    // fetch(url, init).then(response => {
    //     console.log("Request succedeed:", response.ok);
    //     return response.status;
    // }).catch(error => {
    //     console.log(error);
    // });

}


//TEMPLATES

// "this" on #each this refers to every element of the array thus every lesson
var lessonstemplate = Handlebars.compile(`
    {{#each this}}
    <article class="txt-style">
        <h3>{{title}}</h3>
        <img src=https://elearning-aueb.herokuapp.com/static/images/{{img}} alt="intro_opp" width="400" height="200">
        <section>
            <h4>Γενικές πληροφορίες</h4>
            <p>{{description}}</p>
        </section>
        <section>
            <h4>Μαθησιακοί Στόχοι</h4>
            <p>{{objectives}}</p>
        </section>
    </article>
    {{/each}}`);
    
var categoriestemplate = Handlebars.compile(`
    {{#each this}}
    <a href="courses.html?category={{id}}" target="_self">{{title}}</a>
    {{/each}}`);

var profiletemplate = Handlebars.compile(`
    <h2>Στοιχεία χρήστη</h2>
    <p>Όνομα: {{fname}}</p>
    <p>Επίθετο: {{lname}}</p>
    <p>Διεύθυνση: {{address}}</p>
    <p>Τηλέφωνο: {{tel}}</p>
    <p>Μορφωτικό επίπεδο: {{morfotikoepipedo}}</p>
    <p>Email: {{email}}</p> `);