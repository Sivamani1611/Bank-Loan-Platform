async function handleLoginFormSubmit(event) {
    
    event.preventDefault();

    // Get the form data
    const form = document.getElementById('loginForm');

    const email = form.elements.email.value;
    const password = form.elements.loginPasswordField.value;

    const endpoint = "/api/user/retrieve/" + email;

    const userObj = await fetchRequest(endpoint, {method: "GET"});

    console.log(userObj);
    const errorTag = document.getElementById("credentialsError");

    if(userObj !== null && password === userObj.password) {
        sessionStorage.setItem("role", userObj.role);
        sessionStorage.setItem("userEmail", userObj.email);

        if(userObj.role.toLowerCase() === "customer") {
            location.href = "/customer/loanApplication";
        } else if(userObj.role.toLowerCase() === "admin") {
            location.href = "/admin/loanApplication";
        } else {
            alert("Unknown user role. Cannot redirect.");
            location.href = "/login";
        }

    } else {
        errorTag.innerHTML = "<span class='text-danger'>Invalid Login Credentials.</span>";
    }

}