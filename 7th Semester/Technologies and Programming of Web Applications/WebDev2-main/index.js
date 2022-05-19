console.log("Loading js..");

window.onload = function(){

    // Get button element
    var searchButtonElement = document.getElementById("searchBtn");

    // EVENT: When the user press the button
    searchButtonElement.onclick = function(){
        //We store his input on keyword
        let searchBarElement = document.getElementById("indexSearchText");
        let keyword = searchBarElement.value;
        console.log("User searched for:", keyword);

        // If keyword is empty, end the event.
        if (keyword == ''){
            alert("Δεν βάλατε κάποιο keyword στην αναζήτηση, παρακαλώ γράψτε κάτι στην μπάρα αναζήτησης πριν πατήσετε το κουμπί 'Αναζήτηση'.");
            return false; 
        }


        //Url
        let url = "https://elearning-aueb.herokuapp.com/courses/search?title=" + keyword;
        console.log("Getting data from:", url);

        fetchAndInsertHTML(url, "indexDynamicLessons", lessonstemplate, true)
    }

    //Loading Categories on window load

    //Url
    let url = "https://elearning-aueb.herokuapp.com/categories";
    console.log("Getting data from:", url);

    fetchAndInsertHTML(url, "indexDynamicNav", categoriestemplate, false)
}

console.log("Loaded JS")

