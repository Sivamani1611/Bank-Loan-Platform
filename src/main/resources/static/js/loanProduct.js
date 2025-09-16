
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

    validateUserRole();
});

function showMessage(message) {
    messageModalText.textContent = message;
    messageModal.classList.remove('hidden');
}

async function fetchAndRenderProducts() {
    try {
        loanProductsGrid.innerHTML = '<p class="text-center text-gray-500 col-span-full">Loading loan products...</p>';

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
        loanProductsGrid.innerHTML = '<p class="text-center text-red-500 col-span-full">Failed to load data. Please check the server connection.</p>';
        showMessage('Error: Failed to fetch data from the server. Please check the console for details.');
    }
}

function renderProductCards(loans) {
    if (!Array.isArray(loans) || loans.length === 0) {
        loanProductsGrid.innerHTML = '<p class="text-center text-gray-500 col-span-full">No loan products found.</p>';
        totalLoansCountEl.textContent = 0;
        return;
    }

    let html = '';
    loans.forEach(loan => {
        html += `
            <div class="loan-card-container" data-loan-id="${loan.loanProductId}">
                <div class="loan-card-header">
                    <h4 class="text-xl font-bold">${loan.productName}</h4>
                </div>
                <div class="loan-card-content space-y-3">
                    <div class="flex justify-between items-center">
                        <span class="text-sm font-medium text-gray-500">Interest Rate</span>
                        <span class="text-sm font-semibold text-purple-600">${loan.interestRate}% p.a.</span>
                    </div>
                    <div class="flex justify-between items-center">
                        <span class="text-sm font-medium text-gray-500">Loan Amount</span>
                        <span class="text-sm font-semibold text-gray-800">₹${loan.minAmount.toLocaleString()}/₹${loan.maxAmount.toLocaleString()}</span>
                    </div>
                    <div class="flex justify-between items-center">
                        <span class="text-sm font-medium text-gray-500">Max Tenure</span>
                        <span class="text-sm font-semibold text-gray-800">${loan.tenure} Months</span>
                    </div>
                </div>
                <div class="loan-card-footer flex justify-end space-x-2">
                    <button class="text-blue-600 hover:text-blue-800 transition-colors" data-action="edit" data-id="${loan.loanProductId}">
                        <i class="fas fa-edit mr-1"></i> Edit
                    </button>
                    <button class="text-red-600 hover:text-red-800 transition-colors" data-action="delete" data-id="${loan.loanProductId}">
                        <i class="fas fa-trash-alt mr-1"></i> Delete
                    </button>
                </div>
            </div>
        `;
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

        addLoanModal.classList.add('hidden');
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

        addLoanModal.classList.remove('hidden');
    } catch (error) {
        console.error('Error fetching loan product for editing:', error);
        showMessage('Error fetching loan product details.');
    }
}

function setupEventListeners() {
    loanProductForm.addEventListener('submit', saveOrUpdateProduct);

    document.getElementById('addLoanButton').addEventListener('click', () => {
        loanProductForm.reset();
        addLoanModal.classList.remove('hidden');
        loanProductForm.loanProductId.value = '';
    });

    document.querySelectorAll('[data-modal-close]').forEach(button => {
        button.addEventListener('click', () => {
            addLoanModal.classList.add('hidden');
            loanProductForm.reset();
        });
    });

    document.querySelectorAll('[data-modal-close-msg]').forEach(button => {
        button.addEventListener('click', () => {
            messageModal.classList.add('hidden');
        });
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


