
document.getElementById('searchBtn').addEventListener('click', function() {
    const applicationId = document.getElementById('applicationId').value;
    if (applicationId) {
        fetchRepaymentData(applicationId);
    }
});

document.getElementById('generateBtn').addEventListener('click', function() {
    const applicationId = document.getElementById('applicationId').value;
    if (applicationId) {
        generateRepaymentSchedule(applicationId);
    } else {
        alert('Please enter a Loan Application ID to generate a schedule.');
    }
});

function showLoading() {
    document.getElementById('totalLoanAmount').textContent = '$0.00';
    document.getElementById('outstandingBalance').textContent = '$0.00';
    document.getElementById('totalPaid').textContent = '$0.00';
    document.getElementById('remainingInstallments').textContent = '0';
    document.getElementById('loanProductName').textContent = '-';
    document.getElementById('repaymentTableBody').innerHTML = '';
}

async function fetchRepaymentData(applicationId) {
    showLoading();

    try {
        const loanAppResponse = await fetch(`/api/loanApplication/retreive/${applicationId}`);
        if (loanAppResponse.ok) {
            const loanAppData = await loanAppResponse.json();
            document.getElementById('totalLoanAmount').textContent = `$${parseFloat(loanAppData.loanAmount).toFixed(2)}`;
        } else {
            document.getElementById('totalLoanAmount').textContent = 'N/A';
        }

        const balanceResponse = await fetch(`/api/repayments/outstanding-balance/${applicationId}`);
        if (balanceResponse.ok && balanceResponse.status !== 204) {
            const balanceData = await balanceResponse.json();
            document.getElementById('outstandingBalance').textContent = `$${parseFloat(balanceData).toFixed(2)}`;
        } else {
            document.getElementById('outstandingBalance').textContent = '$0.00';
        }

        const scheduleResponse = await fetch(`/api/repayments/schedule/${applicationId}`);
        if (scheduleResponse.ok && scheduleResponse.status !== 204) {
            const scheduleData = await scheduleResponse.json();

            if (scheduleData && scheduleData.length > 0) {
                const totalPaid = scheduleData.filter(r => r.paymentStatus === 'COMPLETED').reduce((sum, r) => sum + r.amountDue, 0);
                const outstandingAmount = scheduleData.filter(r => r.paymentStatus === 'PENDING').reduce((sum, r) => sum + r.amountDue, 0);
                const remainingInstallments = scheduleData.filter(r => r.paymentStatus === 'PENDING').length;

                document.getElementById('totalPaid').textContent = `$${totalPaid.toFixed(2)}`;
                document.getElementById('outstandingBalance').textContent = `$${outstandingAmount.toFixed(2)}`;
                document.getElementById('remainingInstallments').textContent = remainingInstallments;
                document.getElementById('loanProductName').textContent = scheduleData[0].loanProductName;

                const tableBody = document.getElementById('repaymentTableBody');
                tableBody.innerHTML = '';
                scheduleData.forEach(repayment => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${repayment.loanProductName}</td>
                        <td>${repayment.dueDate}</td>
                        <td>$${parseFloat(repayment.amountDue).toFixed(2)}</td>
                        <td>${repayment.paymentDate || "-"}</td>
                        <td><span class="badge bg-${repayment.paymentStatus === 'COMPLETED' ? 'success' : 'warning'}">${repayment.paymentStatus}</span></td>
                        <td>
                            <button class="btn btn-sm btn-success ${repayment.paymentStatus === 'COMPLETED' ? 'disabled' : ''}"
                                    onclick="makePayment(${repayment.repaymentId}, ${repayment.amountDue})">
                                Pay
                            </button>
                        </td>
                    `;
                    tableBody.appendChild(row);
                });
            } else {
                document.getElementById('noData').textContent = 'No repayment data found for this loan application.';
            }
        } else {
            document.getElementById('noData').textContent = 'No repayment data found for this loan application.';
        }
    } catch (error) {
        console.error('Error fetching repayment data:', error);
        alert('Failed to fetch data. Please check the Loan ID.');
    }
}

async function generateRepaymentSchedule(applicationId) {
    try {
        const loanAppResponse = await fetch(`/api/loanApplication/retreive/${applicationId}`);
        if (!loanAppResponse.ok) {
            alert('Loan application not found. Please check the ID.');
            return;
        }
        const loanAppData = await loanAppResponse.json();

        const loanProductResponse = await fetch(`/api/loanProduct/retrieve/${loanAppData.loanProductId}`);
        if (!loanProductResponse.ok) {
            alert('Loan product not found. Cannot generate schedule.');
            return;
        }
        const loanProductData = await loanProductResponse.json();

        const requestBody = {
            applicationId: loanAppData.applicationId,
            loanAmount: loanAppData.loanAmount,
            interestRate: loanProductData.interestRate,
            tenure: loanProductData.tenure
        };

        console.log('Loan Application Data:', loanAppData);
        console.log('Loan Product Data:', loanProductData);
        console.log('Generating schedule with data:', requestBody);
        const generateResponse = await fetch('/api/repayments/generate-schedule', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        });

        if (generateResponse.ok) {
            alert('Repayment schedule generated successfully!');
            fetchRepaymentData(applicationId);
        } else {
            const errorText = await generateResponse.text();
            alert('Failed to generate schedule: ' + errorText);
        }
    } catch (error) {
        console.error('Error generating repayment schedule:', error);
        alert('An unexpected error occurred while generating the schedule.');
    }
}

async function makePayment(repaymentId, amountDue) {
console.log('Initiating payment for Repayment ID:', repaymentId, 'Amount Due:', amountDue);
    if (confirm(`Are you sure you want to mark this payment as complete?`)) {
        try {
            const response = await fetch(`/api/repayments/pay/${repaymentId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ paidAmount: amountDue })
            });

            if (response.ok) {
                alert('Payment successfully recorded!');
                const applicationId = document.getElementById('applicationId').value;
                fetchRepaymentData(applicationId);
            } else {
                const error = await response.json();
                alert('Payment failed: ' + error.message);
            }
        } catch (error) {
            console.error('Error recording payment:', error);
            alert('An error occurred while recording the payment.');
        }
    }
}