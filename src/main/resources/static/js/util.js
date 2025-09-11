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
