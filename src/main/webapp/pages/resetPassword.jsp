<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Loan Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <style>
        .dropdown-menu {
            min-width: 0;
            white-space: nowrap;
        }
    </style>
</head>
<body class="bg-light">
    <div id="resetPasswordPage" class="container-fluid vh-100">
        <div class="row h-100">
            <!-- Left Side: Professional branding from the login page -->
            <div class="col-lg-6 d-none d-lg-flex bg-primary text-white flex-column justify-content-center align-items-center">
                <div class="text-center">
                    <i class="fas fa-university fa-5x mb-4"></i>
                    <h1 class="display-4 fw-bold mb-3">SecureBank</h1>
                    <p class="lead">Professional Loan Management System</p>
                    <div class="row mt-5">
                        <div class="col-4">
                            <div class="feature-box">
                                <i class="fas fa-shield-alt fa-2x mb-2"></i>
                                <h6>Secure</h6>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="feature-box">
                                <i class="fas fa-chart-line fa-2x mb-2"></i>
                                <h6>Analytics</h6>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="feature-box">
                                <i class="fas fa-users fa-2x mb-2"></i>
                                <h6>Customer Focused</h6>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Right Side: Password reset form -->
            <div class="col-lg-6 d-flex align-items-center justify-content-center">
                <div class="w-100" style="max-width: 400px;">
                    <div class="card shadow-lg border-0">
                        <div class="card-body p-5">
                            <div class="text-center mb-4">
                                <i class="fas fa-university fa-3x text-primary mb-3"></i>
                                <h3 class="card-title">Reset Your Password</h3>
                                <p class="text-muted">Enter your email and password to reset.</p>
                            </div>
                            
                            <form id="resetPasswordForm" onsubmit="(handleResetFormSubmit(event))">

                                <div class="mb-3">
                                    <label for="resetEmail" class="form-label">Email Address</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                                        <input type="email" class="form-control" id="resetEmail" required placeholder="Enter your email">
                                    </div>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="resetPassword" class="form-label">Password</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                        <input type="password" class="form-control" id="resetPasswordField" required minlength="6" onkeyup="(validatePassword(event.target.value))">
                                        <button class="btn btn-outline-secondary" type="button" id="toggleResetPasswordBtn" onclick="(togglePasswordVisibility('toggleResetPasswordBtn', 'resetPasswordField'))">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                    <small class="text-muted">
                                        <span id="passwordSpanTag"></span>
                                    </small>
                                </div>

                                <div class="mb-3">
                                    <label for="confirmPassword" class="form-label">Confirm Password</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                        <input type="password" class="form-control" id="resetConfirmPasswordField" required minlength="6" onkeyup="(matchPassword(event.target.value, 'resetPasswordField'))">
                                        <button class="btn btn-outline-secondary" type="button" id="toggleResetConfirmPassword" onclick="(togglePasswordVisibility('toggleResetConfirmPassword', 'resetConfirmPasswordField'))">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </div>
                                    <small class="text-muted" id="confirmPasswordTag"></small>
                                </div>

                                <button type="submit" class="btn btn-primary w-100 mb-3">
                                    <i class="fas fa-paper-plane me-2"></i>Reset Password
                                </button>
                                
                                <div class="text-center">
                                    <p class="mb-0">
                                        Remember your password? 
                                        <a style="cursor: pointer;" class="text-decoration-none" onclick="(location.href='/login')">Sign in here</a>
                                    </p>
                                </div>
                            </form>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/js/util.js"></script>
    <script src="/js/registerUser.js"></script>
    <script src="/js/resetUserPassword.js"></script>
</body>
</html>
