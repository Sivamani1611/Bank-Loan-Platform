<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Loan Management - Your Trusted Partner</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('https://source.unsplash.com/1600x900/?bank,money,loan') no-repeat center center/cover;
            color: white;
            padding: 8rem 0;
            text-align: center;
        }
        .loan-card {
            min-height: 250px; /* Ensure consistent card height */
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">LoanWise Bank</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#loan-types">Loan Types</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#about-us">About Us</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#contact">Contact</a>
                </li>
            </ul>

            <div class="d-flex">
                <button class="btn btn-outline-light me-2" type="button" onclick="location.href='/login'">Login</button>
                <button class="btn btn-primary" type="button" onclick="location.href='/register'">Register</button>

            </div>
        </div>
    </div>
</nav>

<section class="hero-section">
    <div class="container">
        <h1 class="display-4 fw-bold">Your Financial Journey Starts Here</h1>
        <p class="lead mt-3">Secure the funds you need with transparent and flexible loan solutions.</p>
        <a href="#loan-types" class="btn btn-primary btn-lg mt-4">Explore Loan Options</a>
    </div>
</section>

<section id="loan-types" class="py-5 bg-light">
    <div class="container">
        <h2 class="text-center mb-5">Discover Our Loan Products</h2>
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">

            <div class="col">
                <div class="card h-100 shadow-sm loan-card">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Personal Loans</h5>
                        <h6 class="card-subtitle mb-2 text-muted">Achieve your personal goals</h6>
                        <p class="card-text flex-grow-1">Whether it's for a dream vacation, medical expenses, or consolidating debt, our flexible personal loans offer quick approval and competitive rates. No collateral required.</p>
                        <a href="#" class="btn btn-outline-primary mt-3">Learn More</a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card h-100 shadow-sm loan-card">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Home Loans</h5>
                        <h6 class="card-subtitle mb-2 text-muted">Your path to homeownership</h6>
                        <p class="card-text flex-grow-1">Realize your dream of owning a home with our tailored home loan solutions. We offer competitive interest rates, flexible repayment options, and expert guidance.</p>
                        <a href="#" class="btn btn-outline-primary mt-3">Learn More</a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card h-100 shadow-sm loan-card">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Auto Loans</h5>
                        <h6 class="card-subtitle mb-2 text-muted">Drive your dream car today</h6>
                        <p class="card-text flex-grow-1">Get on the road faster with our attractive auto loan options. New or used, we help you finance your vehicle with easy application and fast processing.</p>
                        <a href="#" class="btn btn-outline-primary mt-3">Learn More</a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card h-100 shadow-sm loan-card">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Business Loans</h5>
                        <h6 class="card-subtitle mb-2 text-muted">Fuel your enterprise growth</h6>
                        <p class="card-text flex-grow-1">Expand operations, manage cash flow, or invest in new equipment. Our business loans are designed to support small and medium-sized enterprises with favorable terms.</p>
                        <a href="#" class="btn btn-outline-primary mt-3">Learn More</a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card h-100 shadow-sm loan-card">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Education Loans</h5>
                        <h6 class="card-subtitle mb-2 text-muted">Invest in your future</h6>
                        <p class="card-text flex-grow-1">Fund your academic aspirations with our accessible education loans. Cover tuition, accommodation, and study materials for higher education, both domestic and international.</p>
                        <a href="#" class="btn btn-outline-primary mt-3">Learn More</a>
                    </div>
                </div>
            </div>

            <div class="col">
                <div class="card h-100 shadow-sm loan-card">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Agricultural Loans</h5>
                        <h6 class="card-subtitle mb-2 text-muted">Support for farming initiatives</h6>
                        <p class="card-text flex-grow-1">Helping farmers grow and thrive with loans for crop cultivation, livestock farming, farm equipment, and infrastructure development. Tailored for the agricultural sector.</p>
                        <a href="#" class="btn btn-outline-primary mt-3">Learn More</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="about-us" class="py-5">
    <div class="container">
        <h2 class="text-center mb-4">About LoanWise Bank</h2>
        <p class="text-center lead">
            At LoanWise Bank, we are committed to providing innovative and customer-centric financial solutions. With years of experience and a dedication to service, we empower individuals and businesses to achieve their financial goals. Our mission is to make banking simple, secure, and accessible for everyone.
        </p>
    </div>
</section>

<section id="contact" class="py-5 bg-light">
    <div class="container">
        <h2 class="text-center mb-4">Contact Us</h2>
        <div class="row justify-content-center">
            <div class="col-md-8 text-center">
                <p class="lead">Have questions or need assistance? Reach out to our friendly team.</p>
                <p>Email: <a href="mailto:info@loanwisebank.com">info@loanwisebank.com</a></p>
                <p>Phone: <a href="tel:+1234567890">+1 (234) 567-890</a></p>
                <address>
                    123 Loan Street, Financial District, City, Country
                </address>
                <a href="#" class="btn btn-primary mt-3">Get in Touch</a>
            </div>
        </div>
    </div>
</section>

<footer class="bg-dark text-white text-center py-4">
    <div class="container">
        <p class="mb-0">&copy; 2023 LoanWise Bank. All rights reserved.</p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>