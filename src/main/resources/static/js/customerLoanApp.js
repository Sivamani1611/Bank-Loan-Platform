

function logout() {
    sessionStorage.removeItem("userEmail");
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

    console.log(tbodyTag);

    loanApplicationsList.forEach((loanApplication, index) => {
        htmlCode += `
                        <tr id='${index + 1}'>
                            <td>${loanApplication.applicationId}</td>
                            <td>
                                <i class="fas fa-user text-primary me-2"></i>
                                ${loanApplication.loanProductId === 1 ? "BankLoan": "PersonLoan"}
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

insertTableData();