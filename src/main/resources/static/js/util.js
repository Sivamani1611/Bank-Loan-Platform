// This script will handle the password visibility toggle for the login form.

function togglePasswordVisibility(toggleButtonId, passwordFieldId) {

    // Get the password input field and the toggle button from the DOM.
    const passwordField = document.getElementById(passwordFieldId);
    const toggleButton = document.getElementById(toggleButtonId);

    // Add a click event listener to the toggle button.
    if(toggleButton && passwordField) {
        // Get the eye icon element inside the button.
        
        const icon = toggleButton.querySelector('i');

        // Check the current type of the password field.
        if (passwordField.type === 'password') {
            // If it's a password, change it to text to reveal the password.
            passwordField.type = 'text';
            // Change the icon to 'fa-eye-slash' to show the password is now visible.
            icon.classList.remove('fa-eye');
            icon.classList.add('fa-eye-slash');
        } else {
            // If it's text, change it back to password to hide the text.
            passwordField.type = 'password';
            // Change the icon back to 'fa-eye' to show the password is now hidden.
            icon.classList.remove('fa-eye-slash');
            icon.classList.add('fa-eye');
        }
    }
}

async function fetchRequest(endPoint = "", options = {method: "GET"}) {
    console.log(endPoint, options);

    const responseObj = await fetch(endPoint, options)
    .then(res => res.json())
    .then(data => data)
    .catch(err => {
        if(err) {
            return null;
        }
        console.error(err);
    });

    return responseObj;

}

async function checkIfUserExists(email = "", spanTag="", msg="") {
    const endPoint = "/api/customer/retrieve/" + email;

    const user = await fetchRequest(endPoint)
    .then(responseObj => {
        if(responseObj === null) {
            console.log("User not Created!");
            return null
        }
        return responseObj;
    })
    .catch(err => console.error(err));

    if(user !== null && spanTag==="emailSpanTag") {
        console.log("User Found!");
        document.getElementById(spanTag).innerHTML = `<span class='text-danger'>${msg}</span>&nbsp;<span class="text-danger">✗</span><br/>`;
    } else if(user === null && spanTag==="resetEmailSpanTag") {
        document.getElementById(spanTag).innerHTML = `<span class='text-danger'>${msg}</span>&nbsp;<span class="text-danger">✗</span><br/>`;
    } else {
        document.getElementById(spanTag).innerText = " ";
    }

    return user !== null;
}


function remove(tag) {
    document.getElementById(tag).innerText = " ";
}