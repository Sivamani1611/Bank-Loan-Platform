async function handleResetFormSubmit(event) {
    
    event.preventDefault();

    const resetForm = document.getElementById("resetPasswordForm");

    const email = resetForm.elements.resetEmail.value;
    const password = resetForm.elements.resetPasswordField.value;
    
    console.log(email, password);

    const getEndpoint = "/api/user/retrieve/" + email;

    const getUserOptions = {method: "GET"};

    const userObj = await fetchRequest(getEndpoint, getUserOptions);

    console.log(userObj);
    
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