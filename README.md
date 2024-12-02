# WeCare-Microservices
**WeCare-Microservices** is the **backend implementation** of the WeCARE platform using **Spring REST** and **Spring Microservices architecture**. The project is designed to handle user, coach, and booking functionalities in a **microservices-based architecture**, allowing scalability and flexibility.
The system utilizes **Consul** for distributed configuration management and **Feign Client** for communication between microservices.

## Features
### Microservices:
* **userMS**: Handles user registration, authentication, and profile management.
* **coachMS**: Manages life coach registration, authentication, and scheduling.
* **bookingMS**: Manages appointment booking, rescheduling, and cancellation between users and coaches.

### Other Key Features:
* **Distributed Configuration**: **Consul** is used to store and manage configuration for all microservices.
* **Service Communication**: **Feign Client** is used for inter-service communication, enabling seamless integration between microservices.

## Architecture
The application follows a **Microservices** architecture with the following services:
* **userMS**: Manages user-related operations like registration and authentication.
* **coachMS**: Handles coach-related operations, including registration and schedule management.
* **bookingMS**: Manages bookings and appointment-related functionality.

## Technologies Used
* **Backend Framework**: Spring Boot (REST APIs), Spring Cloud (for microservices)
* **Service Discovery**: Consul
* **Inter-service Communication**: Feign Client
* **Database**: MySQL (or any relational database)
* **Others**: Spring Security, Spring Data JPA

## API Endpoints
**User Service** (`userMS`)
* POST `/users`: Register a new user.
* POST `/users/login`: User login.
* GET `/users/{userId}`: Get user profile.

**Coach Service** (`coachMS`)
* POST `/coaches`: Register a new coach.
* POST `/coaches/login`: Coach login.
* GET `/coaches/{coachId}`: Get coach profile.
* GET `/coaches/all`: List all available coaches.

**Booking Service** (`bookingMS`)
* POST `/users/{userId}/booking/{coachId}`: Book an appointment.
* PUT `/booking/{bookingId}`: Reschedule an appointment.
* DELETE `/booking/{bookingId}`: Cancel an appointment.

