async function handleResetFormSubmit(event) {
    
    event.preventDefault();

    const resetForm = document.getElementById("resetPasswordForm");

    const email = resetForm.elements.resetEmail.value;
    const password = resetForm.elements.resetPasswordField.value;


    const getEndpoint = "/api/user/retrieve/" + email;

    const getUserOptions = {method: "GET"};

    const userObj = await fetchRequest(getEndpoint, getUserOptions);

    if(!userObj) {
        alert("Email ID Not Found!!!\nPlease enter valid Email...");
        return;
    }
    
    const modifiedUserObj = {...userObj, password};

    const updateEndpoint = "/api/user/save";

    const saveUserOptions = {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(modifiedUserObj)
    }
    
    const resetedObj = await fetchRequest(updateEndpoint, saveUserOptions);

    alert("Password Resetted Successfully...");
    location.href = "/login";
    
}