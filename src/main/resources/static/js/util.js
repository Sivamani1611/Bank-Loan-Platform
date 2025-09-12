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

function insertLoanProductIcon(loanProductId) {
    if(loanProductId === 1) {
        return "user";
    } else if(loanProductId === 2) {
        return "home";
    } else if(loanProductId === 3) {
        return "car";
    } else if(loanProductId === 4) {
        return "graduation-cap";
    }
    return "briefcase";
}

function getLoanProductName(loanProductId) {
    if(loanProductId === 1) {
        return "Personal";
    } else if(loanProductId === 2) {
        return "Home";
    } else if(loanProductId === 3) {
        return "Car";
    } else if(loanProductId === 4) {
        return "Education";
    }
    return "Business";
}
