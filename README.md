# Dietitian+ (backend)

A comprehensive website application designed for people looking to lose weight and professional dietitians.
It allows dietitians to create meal plans, generate shopping lists, and manage client progress, while users can access
personalized diets and track their BMI.
The backend also includes a subscription model with a paywall, Stripe payment gateway integration, account activation
via email, and automated email notifications related to subscription status.
The application is hosted on AWS, and the Swagger API documentation is
available [here](http://51.21.186.125:8090/swagger-ui/index.html).

[Dietitian+ (frontend)](https://github.com/BartlomiejJaruga/dietitian-plus-frontend)

---

# Table of Contents

1. [Application Functionalities](#application-functionalities)
    - [Patient Website Features](#patient-website-features)
    - [Dietitian Website Features](#dietitian-website-features)
2. [Tech Stack](#tech-stack)
3. [Database Scheme](#database-scheme)
4. [How to Run the Project](#how-to-run-the-project)
5. [Author](#author)

---

# Application Functionalities:

## Patient Website Features:

1. Diet Overview – Access and review meal plans assigned by the dietitian for a specific week.
2. Shopping List – Automatically generated shopping lists based on the assigned meal plan, categorized by product type
   and available packaging sizes (e.g., 1 L, 1 kg, 300 g).
3. Personal Profile & Tracking – Store personal details such as height, current weight, and calculate BMI based on daily
   diet progression.
4. Allergy & Intolerance Tracking – Select allergies and intolerances to ensure the dietitian only includes safe
   products and meals in your diet plan.
5. Meal Details – View meal compositions with specific quantities (grams/pieces/kcal) and instructions on how to prepare
   them.

## Dietitian Website Features:

1. Meal Plan Creation – Design weekly meal plans based on the patient's caloric needs.
2. Ingredient Management – Select ingredients with precise measurements (grams/pieces/kcal) and choose by their
   availability in stores (packaging sizes).
3. Template System – Ability to predefine meal templates for different days of the week, allowing easy customization.
4. Patient Interview — conduct a structured interview with the patient, collecting essential information such as
   personal details, medical history, allergies and intolerances.
5. Patient Management – Store and monitor patient data, including weight, height and BMI calculations over time.
6. Easy Notes System — Create notes about every patient to store various additional data about them.
7. Diet Customization – Adjust meal plans according to individual patient preferences and progress.
8. Public Dish Library — Share created dishes for other dietitians so they can use it as a template for their meals.
9. Extensive Product Library — Take advantage of an extensive product database containing all specialized data needed to
   compose a perfect menu for a patient.

---

# Tech Stack

- **Backend:** Java SpringBoot
- **Database:** Postgres
- **Authentication:** JWT (JSON Web Tokens), email-based account activation
- **Payments:** Stripe API (subscription model with paywall)
- **Email Services:** SMTP (account activation, subscription notifications)
- **API Documentation:** Swagger / OpenAPI
- **Hosting:** AWS (EC2 instance)
- **Environment Management:** `.env`
- **Containerization:** DockerDesktop

---

# Database scheme:

<img src="assets/database_scheme.png" alt="database_scheme">

---

# How to Run the Project:

## Prerequisites

- Java 21+
- Docker

## Step-by-step

1. **Clone the repository:**

    ```powershell
    git clone https://github.com/JakubJagodzinski/dietitian-plus-backend.git
    ```

2. **Navigate to the project directory:**

    ```powershell
    cd dietitian-plus-backend
    ```

3. **Copy the `.env.example` file to `.env` and fill in the required environment variables:**

    ```powershell
    copy .env.example .env
    ```

4. build docker image

    ```powershell
    docker compose build
    ```

5. **Create and run containers**

    ```powershell
    docker compose up -d
    ```

---

# Author

Jakub Jagodziński
