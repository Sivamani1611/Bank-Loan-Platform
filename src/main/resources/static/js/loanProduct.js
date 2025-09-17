const messageModal = document.getElementById('messageModal');
const messageModalText = document.getElementById('modal-message-text');
const loanProductsGrid = document.getElementById('loan-products-grid');
const totalLoansCountEl = document.getElementById('total-loans-count');
const totalUsersCountEl = document.getElementById('total-users-count');
const addLoanModal = document.getElementById('addLoanModal');
const loanProductForm = document.getElementById('loanProductForm');


document.addEventListener('DOMContentLoaded', () => {
    setupEventListeners();
    fetchAndRenderProducts();
    document.getElementById("userDisplayName").innerText = sessionStorage.getItem("userEmail");
    validateUserRole("admin");
});

function showMessage(message) {
    messageModalText.textContent = message;
    // Using Bootstrap's 'show' class to display the modal.
    // Make sure your HTML modal has the 'modal' and 'fade' classes, and a 'data-bs-backdrop="static"' attribute.
    // The .show class will handle the display.
    // You'll also need to add a Bootstrap dismiss button to your modal HTML.
    $(messageModal).modal('show');
}

async function fetchAndRenderProducts() {
    try {
        loanProductsGrid.innerHTML = '<p class="text-center text-secondary col-12">Loading loan products...</p>';

        const url = '/api/loanProduct/retrieve/all';
        console.log('GET request to:', url);

        const response = await fetch(url);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const responseData = await response.json();
        const loans = responseData.loanProduct;

        renderProductCards(loans);
    } catch (error) {
        console.error('Failed to fetch loan products:', error);
        loanProductsGrid.innerHTML = '<p class="text-center text-danger col-12">Failed to load data. Please check the server connection.</p>';
        showMessage('Error: Failed to fetch data from the server. Please check the console for details.');
    }
}

function renderProductCards(loans) {
    if (!Array.isArray(loans) || loans.length === 0) {
        loanProductsGrid.innerHTML = '<p class="text-center text-secondary col-12">No loan products found.</p>';
        totalLoansCountEl.textContent = 0;
        return;
    }

    let html = '';
    loans.forEach(loan => {
        html += `
        <div class="col-md-4 mb-4">
            <div class="card h-100 shadow">
                <div class="card-body d-flex flex-column justify-content-between">
                    <div>
                        <h4 class="card-title">${loan.productName}</h4>
                        <div class="mt-3">
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <span class="text-muted small">Interest Rate</span>
                                    <span class="fw-bold text-primary">${loan.interestRate}% p.a.</span>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <span class="text-muted small">Loan Amount</span>
                                    <span class="fw-bold">₹${loan.minAmount.toLocaleString()}/₹${loan.maxAmount.toLocaleString()}</span>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <span class="text-muted small">Max Tenure</span>
                                    <span class="fw-bold">${loan.tenure} Months</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="d-flex justify-content-end mt-3">
                        <button class="btn btn-link p-0 me-2" data-action="edit" data-id="${loan.loanProductId}">
                            <i class="fas fa-edit me-1"></i> Edit
                        </button>
                        <button class="btn btn-link text-danger p-0" data-action="delete" data-id="${loan.loanProductId}">
                            <i class="fas fa-trash-alt me-1"></i> Delete
                        </button>
                    </div>
                </div>
            </div>
        </div>`;
    });

    loanProductsGrid.innerHTML = html;
    totalLoansCountEl.textContent = loans.length;
    totalUsersCountEl.textContent = '1,234';
}

async function saveOrUpdateProduct(event) {
    event.preventDefault();

    const form = event.target;
    const loanProductId = form.loanProductId.value;

    const formData = {
        productName: form.loanName.value,
        interestRate: Number(form.interestRate.value),
        minAmount: Number(form.minAmount.value),
        maxAmount: Number(form.maxAmount.value),
        tenure: Number(form.tenure.value),
    };

    const method = 'POST';
    const url = loanProductId ? `/api/loanProduct/update/${loanProductId}` : '/api/loanProduct/save';

    console.log(`${method} request to:`, url);
    console.log('Payload:', JSON.stringify(formData));

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData),
        });

        if (!response.ok) {
            throw new Error('Failed to save loan product. Server responded with an error.');
        }

        const result = await response.json();
        console.log('Response from API:', result);
        showMessage('Loan Product Saved!');

        // Hide the Bootstrap modal.
        $(addLoanModal).modal('hide');
        form.reset();
        fetchAndRenderProducts();
    } catch (error) {
        console.error('Error saving loan product:', error);
        showMessage('Error saving loan product. Please try again.');
    }
}

async function deleteProduct(loanId) {
    if (!confirm('Are you sure you want to delete this loan product?')) {
        return;
    }

    const url = `/api/loanProduct/delete/${loanId}`;
    console.log('DELETE request to:', url);

    try {
        const response = await fetch(url, { method: 'DELETE' });
        console.log('Response from API:', response);
        if (!response.ok) {
            throw new Error('Failed to delete loan product. Server responded with an error.');
        }

        showMessage('Loan Product Deleted Successfully!');
        fetchAndRenderProducts();
    } catch (error) {
        console.error('Error deleting loan product:', error);
        showMessage('Error deleting loan product. Please try again.');
    }
}

async function editProduct(loanId) {
    const url = `/api/loanProduct/retrieve/${loanId}`;
    console.log('GET request to:', url);

    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Failed to retrieve loan product for editing.');
        }
        const loan = await response.json();
        console.log('Response from API:', loan);

        loanProductForm.loanProductId.value = loan.loanProductId;
        loanProductForm.loanName.value = loan.productName;
        loanProductForm.minAmount.value = loan.minAmount;
        loanProductForm.maxAmount.value = loan.maxAmount;
        loanProductForm.interestRate.value = loan.interestRate;
        loanProductForm.tenure.value = loan.tenure;

        // Show the Bootstrap modal.
        $(addLoanModal).modal('show');
    } catch (error) {
        console.error('Error fetching loan product for editing:', error);
        showMessage('Error fetching loan product details.');
    }
}

function setupEventListeners() {
    loanProductForm.addEventListener('submit', saveOrUpdateProduct);

    document.getElementById('addLoanButton').addEventListener('click', () => {
        loanProductForm.reset();
        // Show the Bootstrap modal.
        $(addLoanModal).modal('show');
        loanProductForm.loanProductId.value = '';
    });


    loanProductsGrid.addEventListener('click', (event) => {
        const targetButton = event.target.closest('button');
        if (!targetButton) return;

        const action = targetButton.getAttribute('data-action');
        const loanId = targetButton.getAttribute('data-id');

        if (action === 'delete') {
            deleteProduct(loanId);
        } else if (action === 'edit') {
            editProduct(loanId);
        }
    });
}
