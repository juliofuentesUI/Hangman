$(document).ready(() => {
    //TODO: change endpoint to point to AWS server instance
    const ENDPOINT = "/HangmanGame";
    $(".letter-button").each((index, buttonEl) => {
        //attach event listener to each button to make post request.
        $(buttonEl).on("click", (event) => {
            event.preventDefault();
            let letter = $(buttonEl).data("lettervalue");
            console.log("The letter is " + letter);
            $.ajax({
                url: ENDPOINT,
                method: "POST",
                crossDomain: true,
                data: {letter: letter},
                success: function(data) {console.log("data returned", data)},
                error: function(xhr, exception) {console.log("error", exception)}
            });
            //in ajax call, no matter if we get a true or false response, we must disable
            //the button that was clicked by adding disabled="true" attribute
            // also add pseudo class to render the button a diff color
        });
    });
});