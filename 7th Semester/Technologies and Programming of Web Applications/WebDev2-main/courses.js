console.log("Loading js..");


window.onload = function(){

    const urlSearchParams = new URLSearchParams(window.location.search);
    const params = Object.fromEntries(urlSearchParams.entries());
    
    let id = params.category;
    console.log("Category id:", id);

    //Url
    let url = "https://elearning-aueb.herokuapp.com/courses/search?category=" + id;
    console.log("Getting data from:", url);

    //Function
    fetchAndInsertHTML(url, "indexDynamicLessons", lessonstemplate, true);

}

console.log("Loaded JS")

