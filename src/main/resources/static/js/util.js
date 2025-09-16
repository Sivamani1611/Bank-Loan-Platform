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

function insertLoanProductIcon(loanProducts, loanProductId) {
    const loanProductName = getLoanProductName(loanProducts, loanProductId);
    if(loanProductName.toLowerCase().includes("personal")) {
        return "user";
    } else if(loanProductName.toLowerCase().includes("home")) {
        return "home";
    } else if(loanProductName.toLowerCase().includes("car")) {
        return "car";
    } else if(loanProductName.toLowerCase().includes("student") || loanProductName.toLowerCase().includes("education")) {
        return "graduation-cap";
    } else if(loanProductName.toLowerCase().includes("unknown")) {
        return "question";
    }
    return "briefcase";
}

function getLoanProductName(loanProducts, loanProductId) {
    const loanProductObj = loanProducts.filter((loanProduct) => loanProduct.loanProductId === Number(loanProductId))[0];
    if(!loanProductObj) {
        return "Unknown";
    }
    return loanProductObj.productName;
}

function remove(tag) {
    document.getElementById(tag).innerText = " ";
}

function generateDate() {
    const today = new Date();

    const year = today.getFullYear();
    const month = (today.getMonth() + 1).toString().padStart(2, '0');

    const day = today.getDate().toString().padStart(2, '0');

    const applicationDate = `${year}-${month}-${day}`;

    console.log("The formatted date is: " + applicationDate);

    return applicationDate;
}