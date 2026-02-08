# Resident Hub

Resident Hub is a modern property management and emergency broadcast platform. It empowers administrators to efficiently manage residents, disseminate instant SMS alerts and voice call notifications, and publish timely updates regarding emergencies or maintenance events.

## 🛠️ Tech Stack

**Frontend:**  
- React (with Vite)  
- TailwindCSS
- Axios

**Backend:**  
- Java 21 / Spring Boot 3.x  
- Maven
- Spring Security & JWT for authentication  
- Spring Data JPA / Hibernate

**Database:**  
- MySQL 8.x  (RDS)

**Caching & Sessions:**  
- Redis 7.x  

**Messaging / Communication:**  
- Twilio (SMS & Voice Calls)  

**DevOps / Deployment:**  
- Docker & Docker Compose  
- GitHub Actions (CI/CD)  
- AWS EC2 (hosting)  
- Docker Hub (image registry)  


## Key Features & Benefits

*   **Resident Management:** Streamline resident information and communication channels.
*   **Emergency Broadcasting:** Instantly notify residents via SMS and voice calls during emergencies.
*   **Maintenance Updates:** Keep residents informed about planned maintenance activities.
*   **Centralized Communication:** Facilitate seamless communication between administrators and residents.
*   **Dockerized Deployment:** Easy deployment and scalability using Docker.

## Application screenshots
![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/login.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/home.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/residents.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/resident_add_update.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/send_message.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/message_logs.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/broadcast.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/broadcast_history.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/activity.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/settings.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/user_manageemnt.png?raw=true)

![image](https://github.com/rafee-sp/Resident-hub/blob/main/images/other_settings.png?raw=true)


## Prerequisites & Dependencies

Before you begin, ensure you have the following installed:

*   **Java:** JDK 21 or higher
*   **Maven:** Version 3.6 or higher
*   **Node.js:** Version 18 or higher
*   **npm:** Version 6 or higher
*   **Docker:** Latest version
*   **Git:** Latest Version

## Installation & Setup Instructions

Follow these steps to set up Resident Hub:

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/rafee-sp/Resident-hub.git
    cd Resident-hub
    ```

2.  **Backend Setup:**

    *   Navigate to the `backend` directory:

        ```bash
        cd backend
        ```

    *   Build the backend application using Maven:

        ```bash
        ./mvnw clean install
        ```

    *   Build the Docker image:

        ```bash
        docker build -t residenthub-backend .
        ```

    *   Run the Docker container:

        ```bash
        docker run -p 8080:8080 residenthub-backend
        ```

3.  **Frontend Setup:**

    *   Navigate to the `frontend` directory:

        ```bash
        cd ../frontend
        ```

    *   Install the dependencies:

        ```bash
        npm ci
        ```

    *   Configure the environment variables:
        * Create a `.env` file with the following content:

        ```
        VITE_BACKEND_AUTH_URL=http://localhost:8080/auth
        VITE_BACKEND_URL=http://localhost:8080
        ```

    *   Build the frontend application:

        ```bash
        npm run build
        ```

    *   Build the Docker image:

        ```bash
        docker build -t residenthub-frontend .
        ```

        **Note:** You may need to specify build arguments during the Docker build process to pass the `VITE_BACKEND_URL` and `VITE_BACKEND_AUTH_URL`. Example:

        ```bash
        docker build --build-arg VITE_BACKEND_URL=http://localhost:8080 --build-arg VITE_BACKEND_AUTH_URL=http://localhost:8080/auth -t residenthub-frontend .
        ```
    *   Run the Docker container:

        ```bash
        docker run -p 80:80 residenthub-frontend
        ```

4.  **Access the Application:**

    *   Open your web browser and navigate to `http://localhost`.

## Usage Examples & API Documentation

The Resident Hub application provides an intuitive user interface for managing residents and sending notifications.

**Backend API Endpoints (Example):**

*   `GET /api/residents`: Retrieves a list of residents.
*   `POST /api/notifications`: Sends a notification to selected residents.

*(Detailed API documentation will be provided in a separate document.)*

## Configuration Options

The Resident Hub application can be configured using environment variables.

**Backend:**

* DATABASE, TWILIO and JWT env varibales

**Frontend:**

*   `VITE_BACKEND_URL`: URL for the backend API.
*   `VITE_BACKEND_AUTH_URL`: URL for the backend authentication API.

## Contributing Guidelines

We welcome contributions to the Resident Hub project! To contribute:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes and commit them with clear and concise messages.
4.  Submit a pull request to the main branch.

## License Information

License not specified. All rights reserved.

## Acknowledgments

*   This project utilizes the React framework.
*   The backend is built using the Spring Boot framework.
*   Special thanks to the open-source community for providing valuable resources and tools.
