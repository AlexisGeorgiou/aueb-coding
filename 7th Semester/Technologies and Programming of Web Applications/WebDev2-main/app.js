const express = require('express');
var bodyParser = require('body-parser');
const cors = require('cors');
const res = require('express/lib/response');
const app = express();

//Cors Access
app.use(cors());
// Making directory public
app.use('/static', express.static(__dirname + '/public'))

// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: true }));

// parse application/json
app.use(bodyParser.json())

//Users data
var users = [];

// Routing
app.get('/', function(req, res){
    res.send("Hello from server.");
})

app.post('/register', function(req, res){
    console.log("Server got a register POST request.");

    //Unpack request
    let formData = req.body;
    const user = {
        fname: formData.fname,
        lname: formData.lname,
        address: formData.address,
        tel: formData.tel,
        morfotikoepipedo: formData.morfotikoepipedo,
        email: formData.email,
        pwd: formData.pwd
    }

    //Checking if email already exists in users Object
    var hasMatch = false;
    for (var index = 0; index < users.length; ++index) {
        var currentuser = users[index];
        if(currentuser.email == user.email){
            hasMatch = true;
            break;
         }
    }

    if (hasMatch){
        // If we found the email in our registered users array we return code 409 Conflict
        console.log("Received already registered email:", user.email);
        res.status(409).send();
    } else {
        // New email, new user, return code 201 Created
        users.push(user);
        console.log("Registered new user email:", user.email);
        console.log("Users registered:", users);

        res.status(201).send();
    }
});

app.post('/login', function(req, res){
    console.log("Server got a login POST request.");

    //Unpack POST request
    let formData = req.body;
    const user = {
        email: formData.email,
        pwd: formData.pwd
    }

    console.log(user)

    //Check if the email and pass are correct.
    var correctindex = -1;
    for (var index = 0; index < users.length; ++index) {
        var currentuser = users[index];
        if(currentuser.email == user.email){
            if(currentuser.pwd == user.pwd){
                correctindex = index;
            }
            break;
         }
    }

    // Successfull identification
    if (correctindex != -1){
        //Send user's data
        console.log("Correct email/pass");
        res.status(201).send();
        
    } else{
    // Unsuccessfull identification
        console.log("Wrong email/pass");
        res.status(409).send();
    }

});

app.get('/login/:email', function(req, res){
    //Gets email, finds the index in the data, sends the user's data
    console.log("Server got a profile GET request.")
    console.log("Searching email index:", req.params.email);
    const index = users.findIndex(user => user.email == req.params.email);
    console.log("User's index:", index);
    console.log(users[index]);
    res.send(users[index]);
});

// Port
const port = 8080;
app.listen(port, function(){ console.log(`Listening port ${port}`)})