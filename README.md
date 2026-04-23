# url-shortener
pring Boot application designed to shorten long URLs into manageable links.

Overview:-
This is a Spring Boot application that provides a RESTful service to shorten long URLs. The project focuses on efficient data mapping, unique ID generation, and high-performance redirection logic.

Tech Stack:-
Language: Java 17+
Framework: Spring Boot
Database: MySQL
Build Tool: Maven
API Testing: Postman

Architecture and Design Patterns:-
The project is built using a clean Layered Architecture to separate concerns and ensure maintainability:

Controller: Handles API requests for creating short links and processing redirections.

Service: Implements the logic for generating short aliases and managing URL mappings.

Repository: Interfaces with the database to store and retrieve original URLs based on their short IDs.

Key Features:-
URL Compression: Converts long, complex URLs into short, easy-to-share links.
Instant Redirection: Provides high-speed redirection from the short alias to the target destination.
Unique ID Generation: Uses hashing or custom logic to ensure every short link is unique.
Error Handling: Manages cases for expired links or invalid URL formats.

Getting Started:-
Clone the project: git clone https://github.com/sirisha-singareddy/url-shortener.git

Configure Database: Update src/main/resources/application.properties with your database credentials.

Run: Execute "mvn spring-boot:run" in your terminal or run from your IDE.
