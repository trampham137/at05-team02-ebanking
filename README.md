# E-Banking Automation Testing Project

## Introduction

The E-Banking Automation Testing Project focuses on building an automation testing framework for a web-based banking
system using Selenium WebDriver and TestNG.

The system supports two main roles:

- Admin
- User

The Admin side is responsible for managing customer-related operations such as depositing money into user accounts.

The User side allows customers to perform common banking activities such as:

- Register and activate account
- Login / Logout
- Open bank account
- View account information and balance
- View transaction history
- Internal transfer
- Interbank transfer

This project was developed to practice automation testing skills, validate business workflows, and improve testing
efficiency through reusable automation architecture.

---

# Objectives

- Apply automation testing knowledge in a practical project
- Build a reusable automation framework using Selenium and TestNG
- Automate key banking workflows
- Validate both UI behavior and business logic
- Handle dynamic data such as OTP verification
- Improve test execution efficiency and reduce manual testing effort

---

# Technology Stack

| Technology              | Purpose                  |
|-------------------------|--------------------------|
| Java 25                 | Programming language     |
| Selenium WebDriver      | Browser automation       |
| TestNG                  | Test execution framework |
| Maven                   | Dependency management    |
| Page Object Model (POM) | Framework design pattern |
| Git / GitHub            | Version control          |
| IntelliJ IDEA           | Development environment  |
| Mailinator              | OTP / Email verification |

---

# Test Environment

## Banking System URL

```text
http://14.176.232.213:8080/EBankingWebsite/
```

## Supported Browser

- Google Chrome

---

# Framework Architecture

The automation framework is designed using the Page Object Model (POM) pattern to improve maintainability, reusability,
and readability.

## Main Components

### Base Layer

- BasePage
- UserBasePage
- AdminBasePage
- BaseTest

### Page Objects

- Account pages
- Transfer pages
- Authentication pages
- Email pages
- Admin pages

### Models

- RegisterData
- InternalTransferData
- InterbankTransferData
- DepositData
- RecentTransactionData
- OTPEmailData

### Utilities

- Wait wrappers
- Browser utilities
- Dynamic test data handling

---

# Project Structure

```bash
src
├── main
│   └── java
│       ├── base
│       ├── models
│       │   └── enums
│       ├── pages
│       │   ├── account
│       │   ├── admin
│       │   ├── auth
│       │   ├── email
│       │   └── transfer
│       └── utils
│
├── test
│   └── java
│       ├── base
│       ├── AccountManagementTest
│       ├── AdminManagementTest
│       ├── InterbankTransferTest
│       └── InternalTransferTest
```

---

# Features Covered

## Admin Features

- Deposit money into user accounts
- Manage customer-related operations

## User Features

- Register and activate account
- Login / Logout
- Open bank account
- View account balance
- View transaction history
- Internal transfer
- Interbank transfer

---

# Scope

## In Scope

- Deposit (Admin side)
- Open account
- View balance and transaction history
- Internal transfer
- Interbank transfer
- Input validation in transfer flows
- Business logic validation

## Out of Scope

- Full admin management features
- Performance testing
- Security testing
- Mobile testing

---

# Testing Approach

The project follows an Agile-Scrum inspired workflow with weekly sprint planning and progress reviews.

## Testing Types

- End-to-End (E2E) Testing
- Black-box Testing
- UI Validation
- Business Logic Validation
- OTP Verification Testing

## Validation Coverage

### Input Validation

- Required fields
- Invalid OTP
- Insufficient balance
- Incorrect transfer data

### Business Logic Validation

- Account balance calculation
- Transfer fee validation
- Transaction history validation
- Transaction timestamp validation

---

# Key Framework Highlights

## Page Object Model (POM)

Reusable page objects are implemented to separate test logic from UI interactions.

Example:

- InternalTransferPage
- TransferConfirmPage
- TransferOtpPage

## Dynamic OTP Handling

The framework integrates with Mailinator to:

- Read OTP emails
- Extract OTP codes dynamically
- Validate OTP format and content

## Reusable Base Methods

Shared reusable methods are implemented in BasePage and BaseTest:

- waitVisible()
- waitClickable()
- type()
- click()
- tab switching
- session clearing

## Business Logic Validation

The framework validates:

- Sender and receiver balances
- Transfer fees
- Transaction records
- Transaction timestamps

## Dynamic Test Data

The framework supports reusable and dynamic test data generation using:

- RegisterData
- TransferData models
- Factory methods

---

# Example Automated Test Scenarios

| Test ID | Description                            |
|---------|----------------------------------------|
| EB-01   | Verify user can open a bank account    |
| EB-02   | Verify admin can deposit money         |
| EB-03   | Verify invalid OTP handling            |
| EB-04   | Verify successful internal transfer    |
| EB-05   | Verify required field validation       |
| EB-06   | Verify transfer amount exceeds balance |
| EB-07   | Verify successful interbank transfer   |
| EB-08   | Verify interbank transfer validation   |

---

# Test Results Summary

| Module             | Executed | Passed | Failed |
|--------------------|----------|--------|--------|
| Account Management | 1        | 1      | 0      |
| Admin Management   | 1        | 1      | 0      |
| Email / OTP        | 1        | 1      | 0      |
| Internal Transfer  | 3        | 3      | 0      |
| Interbank Transfer | 2        | 1      | 1      |

## Overall Result

- Total Test Cases: 8
- Passed: 7
- Failed: 1
- Pass Rate: 87.5%

---

# Known Issue

## EB-08

Validation messages on the Interbank Transfer form are inconsistent with expected requirements.

### Impact

- Affects validation consistency
- Affects user experience

### Does NOT affect

- Core transaction functionality

---

# Lessons Learned

- Understand how to design automation frameworks for real-world workflows
- Improve Selenium debugging and script stabilization skills
- Gain experience handling OTP verification flows
- Improve business logic validation techniques
- Learn the importance of structured test design before automation
- Improve collaboration and sprint planning skills

---

# Future Improvements

- Add more edge-case validation scenarios
- Improve framework maintainability
- Expand automation coverage
- Improve reusable components
- Add CI/CD integration
- Improve reporting and execution management
- Expand backend/API testing coverage

---

# Authors

- Tram Pham
- Ly Nguyen
