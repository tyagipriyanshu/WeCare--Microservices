# WeCare-Microservices
**WeCare-Microservices** is the **backend implementation** of the WeCARE platform using **Spring REST** and **Spring Microservices architecture**. The project is designed to handle user, coach, and booking functionalities in a **microservices-based architecture**, allowing scalability and flexibility.
The system utilizes **Consul** for distributed and centralized configurations, while Spring Cloud Gateway acts as the API Gateway for routing incoming requests. **Feign Client** enables smooth communication between microservices.

## Architecture
The application follows a **Microservices** architecture with the following services:
* **userMS**: Handles user registration, authentication, viewing upcoming appointments and user profile.
* **coachMS**: Handles coach registration, authentication, viewing upcoming appointments and coach profile.
* **bookingMS**: Manages appointment booking, rescheduling, and cancellation of appointments.

## Key Features:
* **Service Discovery**: **Consul** is used for automatic discovery of services, ensuring that microservices can dynamically find and communicate with each other.
* **Centralized Configuration**: **Consul** is used to store and manage configuration properties for all microservices, providing a single source of truth for configuration management.
* **Distributed Configuration**: All microservices load their configuration properties from **Consul**, allowing for centralized management and dynamic updates.
* **Inter-Service Communication**: **Feign Client** enables REST-based communication between microservices.
* **API Gateway**: **Spring Cloud Gateway** routes incoming requests to the appropriate microservices, ensuring centralized entry and load balancing.

## Technologies Used
* **Backend Framework**: Spring Boot (REST APIs), Spring Cloud (for microservices)
* **Service Discovery**: Consul
* **Centralized & Distributed Configuration**: Consul
* **API Gateway**: Spring Cloud Gateway
* **Inter-service Communication**: Feign Client
* **Database**: MySQL (or any relational database)
* **Others**: Spring Security, Spring Data JPA

## API Endpoints
**User Service** (`userMS`)
* POST `/users`: Register a new user.
* POST `/users/login`: User login.
* GET `/users/{userId}`: Get user profile.
* GET `/users/booking/{userId}`: Get list of appointments for the user.

**Coach Service** (`coachMS`)
* POST `/coaches`: Register a new coach.
* POST `/coaches/login`: Coach login.
* GET `/coaches/{coachId}`: Get coach profile.
* GET `/coaches/all`: List all available coaches.
* GET `/coaches/booking/{coachId}`: Get list of appointments for the coach.

**Booking Service** (`bookingMS`)
* POST `/users/{userId}/booking/{coachId}`: Book an appointment.
* PUT `/booking/{bookingId}`: Reschedule an appointment.
* DELETE `/booking/{bookingId}`: Cancel an appointment.
* GET `/booking/{id}`: Show all appointments for a user or coach ({id} can either be the user ID or coach ID).

