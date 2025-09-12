
function logout() {
    sessionStorage.removeItem("userEmail");
    sessionStorage.removeItem("customerLoanApps");
    location.href = "/login";
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

async function insertTableData() {

    htmlCode = ``;

    const userEmail = sessionStorage.getItem("userEmail");
    const tbodyTag = document.getElementById("loanAppTableBody");

    const customerObj = await fetchRequest("/api/customer/retrieve/" + userEmail, );

    const loanAppEndPoint = "/api/loanApplication/retreive/customer/" + customerObj.customerId;

    const loanApplicationsList = await fetchRequest(loanAppEndPoint, )
    .then(data => data.listOfLoanApplications)
    .catch(err => console.error(err));

    if(sessionStorage.getItem("customerLoanApps") === null) {
        sessionStorage.setItem("customerLoanApps", JSON.stringify(loanApplicationsList));
    }

    loanApplicationsList.forEach((loanApplication, index) => {
        htmlCode += `
                        <tr id='${index + 1}'>
                            <td>${loanApplication.applicationId}</td>
                            <td>
                                <i class="fas fa-${(insertLoanProductIcon(loanApplication.loanProductId))} text-primary me-2"></i>
                                ${(getLoanProductName(loanApplication.loanProductId))} Loan
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

insertTableData();

insertDataIntoStats();

async function fetchLoanApplicationsById(loanApplicationId) {

    if(loanApplicationId.length === 0) {
        insertTableData();
        return;
    }

    const tbodyTag = document.getElementById("loanAppTableBody");

    const loanApplicationObj = await fetchRequest("/api/loanApplication/retreive/" + loanApplicationId, );

    htmlCode = `
                <tr id='1'>
                    <td>${loanApplicationObj.applicationId}</td>
                    <td>
                        <i class="fas fa-${(insertLoanProductIcon(loanApplicationObj.loanProductId))} text-primary me-2"></i>
                        ${(getLoanProductName(loanApplicationObj.loanProductId))} Loan
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

    let endPoint = "";

    if(query === "approvalStatus") {
        endPoint = "/api/loanApplication/retreive/approvalStatus/" + value;
    } else if(query === "productType") {
        endPoint = "/api/loanApplication/retreive/loanProduct/" + value;
    } else if(query === "date") {
        if(value.split('-')[0].length === 4) {
            endPoint = "/api/loanApplication/retreive/applicationDate/" + value;
        } else {
            insertTableData();
            return;
        }
    }

    const loanApplicationsList = JSON.parse(sessionStorage.getItem("customerLoanApps")).filter((loanApplication) => {
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
                    <i class="fas fa-${(insertLoanProductIcon(loanApplication.loanProductId))} text-primary me-2"></i>
                    ${(getLoanProductName(loanApplication.loanProductId))} Loan
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

function setUserEmail(nameTag) {
    console.log(document.getElementById(nameTag));
    const email = sessionStorage.getItem('userEmail');
    document.getElementById(nameTag).value = email;
}
