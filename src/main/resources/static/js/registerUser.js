function validatePassword(passwordText = "") {

    const passwordRegex = /^(?=(.*[A-Z]){1})(?=(.*[a-z]){1})(?=(.*[0-9]){1})(?=(.*[@!#$&]){1})[a-zA-Z0-9@!#$&]{8,100}$/;

    const oneUpperLetter = /[A-Z]+/.test(passwordText);
    const oneLowerLetter = /[a-z]+/.test(passwordText);
    const oneDigitLetter = /[0-9]+/.test(passwordText);
    const oneSpecialLetter = /[@!$&]+/.test(passwordText);

    const passwordSpanTag = document.getElementById("passwordSpanTag");

    // The corrected code to generate the HTML for password validation.

    let htmlCode = '';

    // Check for password length
    if (passwordText.length >= 8) {
        htmlCode += `<span class='text-success'>Password must be at least 8 characters long.</span>&nbsp;<span class="text-success">✓</span><br/>`;
    } else {
        htmlCode += `<span class='text-danger'>Password must be at least 8 characters long.</span>&nbsp;<span class="text-danger">✗</span><br/>`;
    }

    // Check for one uppercase letter
    if (!oneUpperLetter) {
        htmlCode += `<span class='text-danger'>Require Atleast One UpperCase Letter</span>&nbsp;<span class="text-danger">✗</span><br/>`;
    } else {
        htmlCode += `<span class='text-success'>Require Atleast One UpperCase Letter</span>&nbsp;<span class="text-success">✓</span><br/>`;
    }

    // Check for one lowercase letter
    if (!oneLowerLetter) {
        htmlCode += `<span class='text-danger'>Require Atleast One LowerCase Letter</span>&nbsp;<span class="text-danger">✗</span><br/>`;
    } else {
        htmlCode += `<span class='text-success'>Require Atleast One LowerCase Letter</span>&nbsp;<span class="text-success">✓</span><br/>`;
    }

    // Check for one digit
    if (!oneDigitLetter) {
        htmlCode += `<span class='text-danger'>Require Atleast One Digit</span>&nbsp;<span class="text-danger">✗</span><br/>`;
    } else {
        htmlCode += `<span class='text-success'>Require Atleast One Digit</span>&nbsp;<span class="text-success">✓</span><br/>`;
    }

    // Check for one special symbol
    if (!oneSpecialLetter) {
        htmlCode += `<span class='text-danger'>Require Atleast One Special Symbol. Like (@!$&)</span>&nbsp;<span class="text-danger">✗</span><br/>`;
    } else {
        htmlCode += `<span class='text-success'>Require Atleast One Special Symbol. Like (@!$&)</span>&nbsp;<span class="text-success">✓</span><br/>`;
    }

    // You can now set the innerHTML of a validation message container with htmlCode.


    passwordSpanTag.innerHTML = htmlCode;

    return passwordRegex.test(passwordText);

}

function matchPassword(currPassword, enteredPassword) {
    const password = document.getElementById(enteredPassword).value;
    

    const confirmPasswordTag = document.getElementById("confirmPasswordTag");

    if(currPassword !== password) {
        confirmPasswordTag.innerHTML = "<span class='text-danger'>Both passwords should match each other.</span>&nbsp;<i class='fa-solid fa-xmark' style='color: #ff0000'></i>";
    } else {
        confirmPasswordTag.innerHTML = "<span class='text-success'>Both are mathced.</span>&nbsp;<i class='fa-solid fa-check' style='color: #198754'></i>";
    }

    return currPassword === password;
}

async function handleRegisterFormSubmit(event) {
    // This is the most important line. It stops the form's default behavior,
    // which is to reload the page.
    event.preventDefault();

    // Get the form data
    const form = document.getElementById('registerForm');
    
    const name = form.elements.fullname.value;
    const email = form.elements.registerEmail.value;
    const address = form.elements.registerAddress.value;
    const phone = form.elements.registerPhone.value;
    const kycStatus = form.elements.kycStatus.value;
    const password = form.elements.registerPasswordField.value;
    const confirmPassword = form.elements.registerConfirmPasswordField.value;
    
    const acceptTerms = form.elements.acceptTerms.checked;


    if(validatePassword(password) && matchPassword(confirmPassword) && acceptTerms) {
        
        const customer = {
            name,
            email,
            phone,
            address,
            kycStatus
        };



        const customerOptions = {
            // 1. Specify the HTTP method as 'POST'.
            method: 'POST', 

            // 2. Set the headers. The 'Content-Type' header is crucial
            //    for telling the server that the body is JSON. 
            headers: {
                'Content-Type': 'application/json',
                // You can add other headers here, like an Authorization token.
                // 'Authorization': 'Bearer YOUR_TOKEN'
            },

            // 3. Set the body. The 'data' object must be converted to a JSON string
            //    using JSON.stringify() before being sent.
            body: JSON.stringify(customer)
        };

        const customerObj = await fetchRequest("/api/customer/save", customerOptions);

        const user = {
            name,
            email,
            password,
            customer: {
                "customerId": customerObj.customerId
            }
        }

        
        const userOptions = {
            // 1. Specify the HTTP method as 'POST'.
            method: 'POST', 

            // 2. Set the headers. The 'Content-Type' header is crucial
            //    for telling the server that the body is JSON. 
            headers: {
                'Content-Type': 'application/json',
                // You can add other headers here, like an Authorization token.
                // 'Authorization': 'Bearer YOUR_TOKEN'
            },

            // 3. Set the body. The 'data' object must be converted to a JSON string
            //    using JSON.stringify() before being sent.
            body: JSON.stringify(user)
        };

        const userObj = await fetchRequest("/api/user/save", userOptions);

        console.log(customerObj, userObj);

        alert("Account Created Successfully!Please Login...");
        
        location.href = "/login";

    }

}
