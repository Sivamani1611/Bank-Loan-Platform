Main Loan Products Application
This document provides a comprehensive overview of the frontend workflow for the Loan Management System. The application uses a standard decoupled architecture, where the frontend (HTML, CSS, JavaScript) is separated from the backend (Spring Boot).

API Endpoints
The frontend communicates with the backend exclusively through two primary REST API endpoints:

GET /api/loans: This endpoint is used to retrieve a list of all existing loan products from the server.

POST /api/loans: This endpoint is used to create a new loan product. The frontend sends the new loan data in the body of the request.

Frontend Workflow
The application's behavior is driven by the JavaScript in main.js, which orchestrates all user interactions and data management.

1. Fetching Data (GET Request)
   On Page Load: When the page index.html is first loaded, the main.js script executes.

API Call: It immediately makes a GET request to the /api/loans endpoint.

Data Processing: The backend is expected to return a JSON array of loan product objects. Once the data is received, the renderLoanProducts() function in JavaScript takes over.

UI Update: This function dynamically generates the rows for the loan product table and updates the statistical cards (total, active, inactive loans) based on the fetched data.

2. Sending Data (POST Request)
   User Interaction: When a user clicks the "Add New Loan" button and submits the form in the modal, the handleFormSubmit() function is triggered.

Data Serialization: This function gathers the values from all the form inputs and packages them into a single JavaScript object.

API Call: The script then makes a POST request to the /api/loans endpoint. The JavaScript object containing the new loan data is serialized into JSON format and sent in the request body.

Refresh UI: After a successful response from the server, the modal is closed and the fetchLoanProducts() function is called again to reload the loan list, ensuring the new product appears on the table.

Detailed JavaScript Functionality (main.js)
The main.js file is the client-side logic layer, responsible for all dynamic behavior. Its core functionality is broken down into the following key functions and event listeners:

DOMContentLoaded Event Listener:

This is the entry point for the entire script. It ensures that the JavaScript code only runs after the entire HTML document has been fully loaded and parsed.

It initializes all event listeners for buttons and forms and then immediately calls fetchLoanProducts() to populate the page with data.

fetchLoanProducts() Function:

Purpose: To retrieve the loan product data from the backend API.

Process: It uses the browser's native fetch() API to make an asynchronous GET request to /api/loans.

Error Handling: A try...catch block is used to gracefully handle network errors or server-side issues. If a fetch fails, an error message is displayed to the user via a custom modal.

Success: On a successful response, it parses the JSON data and passes it to the renderLoanProducts() function.

renderLoanProducts(loans) Function:

Purpose: To build the HTML table rows and update the statistics cards.

Process: It iterates through the array of loans passed to it. For each loan object, it dynamically creates an HTML table row (<tr>) and populates the cells (<td>) with the loan's data (name, amount, interest rate, etc.).

UI Logic: It also handles visual logic, such as determining whether to display "Active" or "Inactive" and setting the appropriate color for the status badge.

Statistics: After the table is rendered, it calculates the count of active and inactive loans and updates the text content of the corresponding statistic cards.

handleFormSubmit(event) Function:

Purpose: To handle the submission of the "Add New Loan" form.

Process: It prevents the default form submission behavior, gathers all the form data, and creates a JavaScript object.

API Call: It then makes an asynchronous POST request to /api/loans with the new loan data in the request body.

Error Handling: Includes a try...catch block to handle potential errors during the data submission process.

Success: If the POST request is successful, it displays a success message, closes the modal, and calls fetchLoanProducts() to refresh the table with the new data.

Modal Management:

The JavaScript includes event listeners for the "Add New Loan" button and the modal's close buttons.

It uses classList.remove('hidden') and classList.add('hidden') to show and hide the modal elements, providing a modern, smooth user experience without relying on traditional alert() or confirm() pop-ups.

A separate showMessage() function is used to handle all custom message displays, centralizing the logic for user notifications.