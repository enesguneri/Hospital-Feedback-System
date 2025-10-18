# Hospital-Feedback-System



A full-stack feedback management system designed for hospitals and doctors.

The project enables patients to submit feedback for doctors and hospitals, while administrators can review, manage, and respond to the submitted feedback.



This system includes a .NET 8.0 Web API backend and a Kotlin Android mobile app following the MVVM architecture pattern.



Features



Backend (ASP.NET Core 8.0)

RESTful Web API built using ASP.NET Core 8.0

Entity Framework Core for ORM and database operations

Oracle Database (deployed via Docker)

JWT Authentication for secure user login and role-based access

API documentation with Swagger UI

User management (Admin, Patient)

Feedback creation, retrieval, updating, and deletion endpoints





Mobile (Kotlin + MVVM)

Developed with Kotlin and Android Jetpack

MVVM architecture with ViewModel and LiveData

Retrofit for API communication

RecyclerView for dynamic list rendering

Figma-based UI designs, implemented using XML

Token-based login and secure session management

Role-specific interfaces (Admin, Patient)



Tech Stack

Layer		Technology



Backend		ASP.NET Core 8.0

Database	Oracle (Docker)

Authentication	JWT (JSON Web Token)

ORM		Entity Framework Core

Mobile App	Kotlin, Android Studio

Architecture	MVVM (Model–View–ViewModel)

Networking	Retrofit + Gson Converter

UI		XML layouts, RecyclerView

Design Tool	Figma

