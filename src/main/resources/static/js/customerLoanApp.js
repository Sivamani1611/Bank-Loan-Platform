let loanProductsList = [];

let loanApplicationsList = [];

window.onload = async function () {

    loanProductsList = await fetchRequest("/api/loanProduct/retrieve/all");
    loanProductsList = loanProductsList.loanProduct;
    insertProductsIntoSelectTag("filterByProductSelectTag");
    insertTableData();

    const role = sessionStorage.getItem("role");
    if(role === null || role !== "customer") {
        alert("You are unable to access this page!\nPlease login as Customer to access this page.");
        location.href = "/login";
    }
};

setTimeout(() => insertDataIntoStats(), 100);

async function handleLoanApplicationFormSubmit(event) {
    event.preventDefault(); // Prevent the default form submission behavior

    const form = document.getElementById("loanApplicationForm");

    const customerEmail = form.elements.customerEmail.value;
    const loanProductId = form.elements.loanProductId.value;
    const loanAmount = form.elements.loanAmount.value.trim();
    const tenure = form.elements.tenure.value.trim();
    const purpose = form.elements.purpose.value.trim();

    const applicationDate = generateDate();

    const customerObj = await fetchRequest("/api/customer/retrieve/" + customerEmail, );

    const loanApplicationObj = {
        customerId: customerObj.customerId,
        loanProductId: Number(loanProductId),
        loanAmount: Number(loanAmount),
        approvalStatus: "PENDING",
        applicationDate: applicationDate
    };

    const loanAppOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(loanApplicationObj)
    };

    const responseObj = await fetchRequest("/api/loanApplication/save", loanAppOptions);

    form.reset();

    const myModalEl = document.getElementById('newApplicationModal');

    // Get the Bootstrap modal instance from the element
    const modal = bootstrap.Modal.getInstance(myModalEl);

    // Hide the modal
    modal.hide();
    renderAllChanges();
}

function insertApprovalStatusClassName(approvalStatus) {
    if(approvalStatus === "APPROVED") {
        return "status-approved";
    }
    if(approvalStatus === "PENDING") {
        return "status-pending";
    }
    return "status-rejected";
}

async function renderTableData(loanApplicationsList) {

        let htmlCode = ``;

        loanApplicationsList.forEach((loanApplication, index) => {
            htmlCode += `
                <tr id='${index + 1}'>
                    <td>${loanApplication.applicationId}</td>
                    <td>
                        <i class="fas fa-${(insertLoanProductIcon(loanProductsList, loanApplication.loanProductId))} text-primary me-2"></i>
                        ${(getLoanProductName(loanProductsList, loanApplication.loanProductId))}
                    </td>
                    <td>
                        <b>₹ ${loanApplication.loanAmount}</b>
                    </td>
                    <td>
                        <span class="status-badge ${(insertApprovalStatusClassName(loanApplication.approvalStatus))}">${loanApplication.approvalStatus}</span>
                    </td>
                    <td>${loanApplication.applicationDate}</td>
                    <td>
                        <button class="btn btn-sm btn-outline-primary" onclick="alert('View Details APP001')">
                            <i class="fas fa-eye"></i>
                        </button>
                    </td>
                </tr>
            `;
        });

        return htmlCode;
    }

async function insertTableData() {

    const tbodyTag = document.getElementById("loanAppTableBody");

    loanApplicationsList = await fetchLoanApplicationsForCustomer();

    sessionStorage.setItem("customerLoanApps", JSON.stringify(loanApplicationsList));

    const htmlCode = await renderTableData(loanApplicationsList);

    tbodyTag.innerHTML = htmlCode;

}

function insertDataIntoStats() {
    const loanApplicationsList = JSON.parse(sessionStorage.getItem("customerLoanApps"));
    const totalLoanAppsCount = loanApplicationsList.length;

    const totalPendingLoanApplications = loanApplicationsList.filter((loanApplication) => loanApplication.approvalStatus === "PENDING").length;
    const totalApprovedLoanApplications = loanApplicationsList.filter((loanApplication) => loanApplication.approvalStatus === "APPROVED").length;
    const totalApprovedAmount = loanApplicationsList.reduce((acc, loanApplication) => {
        if (loanApplication.approvalStatus === "APPROVED") {
            acc += loanApplication.loanAmount;
        }
        return acc;
    }, 0);

    document.getElementById("totalAppsTag").innerHTML = `<span>${totalLoanAppsCount}</span>`;
    document.getElementById("pendingAppsTag").innerHTML = `<span>${totalPendingLoanApplications}</span>`;
    document.getElementById("approvedAppsTag").innerHTML = `<span>${totalApprovedLoanApplications}</span>`;
    document.getElementById("totalApprovedAmountTag").innerHTML = `<span>${totalApprovedAmount}</span>`;


}

async function fetchLoanApplicationsById(loanApplicationId) {

    if(loanApplicationId.length === 0) {
        insertTableData();
        return;
    }

    const tbodyTag = document.getElementById("loanAppTableBody");

    const loanApplicationObj = await fetchRequest("/api/loanApplication/retreive/" + loanApplicationId, );

    if(loanApplicationObj === null) {
        tbodyTag.innerHTML = "<p class='w-100'>No Applications Found!</p>";
        return;
    }

    htmlCode = `
        <tr id='1'>
            <td>${loanApplicationObj.applicationId}</td>
            <td>
                <i class="fas fa-${(insertLoanProductIcon(loanProductsList, loanApplicationObj.loanProductId))} text-primary me-2"></i>
                ${(getLoanProductName(loanProductsList, loanApplicationObj.loanProductId))} Loan
            </td>
            <td>
                <b>₹ ${loanApplicationObj.loanAmount}</b>
            </td>
            <td>
                <span class="status-badge ${(insertApprovalStatusClassName(loanApplicationObj.approvalStatus))}">${loanApplicationObj.approvalStatus}</span>
            </td>
            <td>${loanApplicationObj.applicationDate}</td>
            <td>
                <button class="btn btn-sm btn-outline-primary" onclick="alert('View Details APP001')">
                    <i class="fas fa-eye"></i>
                </button>
            </td>
        </tr>
    `;
    tbodyTag.innerHTML = htmlCode;
}

async function fetchLoanApplicationsByString(value, query) {

    if(value.length === 0) {
        insertTableData();
        return;
    }

    const tbodyTag = document.getElementById("loanAppTableBody");
    let htmlCode = "";

    loanApplicationsList = loanApplicationsList.filter((loanApplication) => {
        if(query === "approvalStatus") {
            return loanApplication.approvalStatus === value;
        } else if(query === "productType") {
            return loanApplication.loanProductId === Number(value);
        } else if(query === "date") {
            return loanApplication.applicationDate === value;
        }
    });

    if(loanApplicationsList.length === 0) {
        tbodyTag.innerHTML = "<p class='w-100'>No Applications Found!</p>";
        return;
    }

    loanApplicationsList.forEach((loanApplication, index) => {
        htmlCode += `
            <tr id='${index + 1}'>
                <td>${loanApplication.applicationId}</td>
                <td>
                    <i class="fas fa-${(insertLoanProductIcon(loanProductsList, loanApplication.loanProductId))} text-primary me-2"></i>
                    ${(getLoanProductName(loanProductsList, loanApplication.loanProductId))} Loan
                </td>
                <td>
                    <b>₹ ${loanApplication.loanAmount}</b>
                </td>
                <td>
                    <span class="status-badge ${(insertApprovalStatusClassName(loanApplication.approvalStatus))}">${loanApplication.approvalStatus}</span>
                </td>
                <td>${loanApplication.applicationDate}</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary" onclick="alert('View Details APP001')">
                        <i class="fas fa-eye"></i>
                    </button>
                </td>
            </tr>
        `;
    });

    tbodyTag.innerHTML = htmlCode;

}

async function fetchLoanApplicationsForCustomer() {

    const userEmail = sessionStorage.getItem("userEmail");
    const customerObj = await fetchRequest("/api/customer/retrieve/" + userEmail, );

    const loanAppEndPoint = "/api/loanApplication/retreive/customer/" + customerObj.customerId;

    const loanApplicationsObj = await fetchRequest(loanAppEndPoint, )
    .then(data => data)
    .catch(err => console.error(err));

    return loanApplicationsObj.listOfLoanApplications;

}

function logout() {
    sessionStorage.removeItem("userEmail");
    sessionStorage.removeItem("customerLoanApps");
    sessionStorage.removeItem("role");
    location.href = "/login";
}

function setUserEmail(nameTag) {
    const email = sessionStorage.getItem('userEmail');
    document.getElementById(nameTag).value = email;
    insertProductsIntoSelectTag("loanProductId");
}

function insertProductsIntoSelectTag(selectTagId) {
    const selectTag = document.getElementById(selectTagId);

    let htmlCode = selectTag.innerHTML;

    loanProductsList.forEach((loanProduct) => {
        htmlCode += `<option id="${loanProduct.loanProductId}" value='${loanProduct.loanProductId}'>${loanProduct.productName}</option>`;
    });
    selectTag.innerHTML = htmlCode;

}

function renderAllChanges() {
    insertTableData();
    setTimeout(() => insertDataIntoStats(), 100);
}