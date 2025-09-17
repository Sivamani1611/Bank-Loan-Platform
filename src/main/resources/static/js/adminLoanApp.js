async function loadDashboardData() {
    document.getElementById("userDisplayName").innerText = sessionStorage.getItem("userEmail");
    validateUserRole("admin");
    try {
        const response = await fetch('/api/admin/loanApplications');
        if (!response.ok) {
            throw new Error('Failed to fetch data');
        }
        const data = (await response.json());

        // Update stats cards
        document.getElementById('totalApplications').textContent = data.totalApplications;
        document.getElementById('pendingApplications').textContent = data.pendingApplications;
        document.getElementById('approvedApplications').textContent = data.approvedApplications;
        document.getElementById('approvedAmount').textContent = `₹ ${data.approvedAmount.toFixed(2)}`;

        // Populate table
        const tableBody = document.getElementById('applicationsTableBody');
        tableBody.innerHTML = ''; // Clear existing rows

        data.loanApplications.forEach((app) => {
            const row = document.createElement('tr');

            const loanIcon = getLoanIcon(app.loanType);
            const statusBadgeClass = getStatusBadgeClass(app.status);

            row.innerHTML = `
                <td>${app.applicationId}</td>
                <td>${app.customerName}
                    <small class="text-muted d-block">${app.customerEmail}</small></td>
                <td><i class="me-2 ${loanIcon}"></i>${app.loanType}</td>
                <td><b>₹ ${app.loanAmount}</b></td>
                <td>₹ ${app.emi?.toFixed(2)}</td>
                <td><span class="status-badge ${statusBadgeClass}">${app.status}</span></td>
                <td>${formatDate(app.appliedDate)}</td>
                <td>
                    <div class="dropdown">
                        <button class="btn btn-sm btn-outline-primary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Actions
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item approve-btn" href="#" data-id="${app.applicationId}">Approve</a></li>
                            <li><a class="dropdown-item reject-btn" href="#" data-id="${app.applicationId}">Reject</a></li>
                        </ul>
                    </div>
                </td>
            `;
            tableBody.appendChild(row);
        });

        // Add event listeners for the new buttons
        document.querySelectorAll('.approve-btn').forEach(btn => {
            btn.addEventListener('click', (e) => updateLoanStatus(e.target.dataset.id, 'APPROVED'));
        });
        document.querySelectorAll('.reject-btn').forEach(btn => {
            btn.addEventListener('click', (e) => updateLoanStatus(e.target.dataset.id, 'REJECTED'));
        });
    } catch (error) {
        console.error('Error fetching data:', error);
        alert('Failed to load data. Please check the backend service.');
    }
}

async function updateLoanStatus(applicationId, newStatus) {
    console.log(`Attempting to update loan ${applicationId} to status: ${newStatus}`);
    try {
        const response = await fetch(`/api/admin/loanApplications/update-status/${applicationId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'text/plain' },
            body: newStatus
        });

        console.log(`Response status for loan ${applicationId} update: ${response.status}`);

        if (!response.ok) {
            throw new Error('Failed to update loan status.');
        }

        alert(`Loan ${applicationId} successfully updated to ${newStatus.toLowerCase()}.`);
        loadDashboardData(); // Refresh the table

    } catch (error) {
        console.error('Error updating status:', error);
        alert('Failed to update loan status.');
    }
}

function getStatusBadgeClass(status) {
    switch (status) {
        case 'PENDING': return 'status-pending';
        case 'APPROVED': return 'status-approved';
        case 'REJECTED': return 'status-rejected';
        default: return '';
    }
}

function getLoanIcon(loanType) {
    switch (loanType) {
        case 'Personal Loan': return 'fas fa-user text-primary';
        case 'Home Loan': return 'fas fa-home text-success';
        case 'Education Loan': return 'fa-solid fa-school text-warning';
        case 'Vehicle Loan': return 'fas fa-car text-info';
        case 'Business Loan': return 'fas fa-briefcase text-dark';
        default: return 'fas fa-file-alt';
    }
}

function formatDate(dateString) {
    const date = new Date(dateString);
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return date.toLocaleDateString('en-US', options);
}