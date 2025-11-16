# Resident Hub

Resident Hub is a modern property management and emergency broadcast platform. It empowers administrators to efficiently manage residents, disseminate instant SMS alerts and voice call notifications, and publish timely updates regarding emergencies or maintenance events.

## Key Features & Benefits

*   **Resident Management:** Streamline resident information and communication channels.
*   **Emergency Broadcasting:** Instantly notify residents via SMS and voice calls during emergencies.
*   **Maintenance Updates:** Keep residents informed about planned maintenance activities.
*   **Centralized Communication:** Facilitate seamless communication between administrators and residents.
*   **Dockerized Deployment:** Easy deployment and scalability using Docker.

## Application screenshots
![image](https://private-user-images.githubusercontent.com/195568862/514870463-b7546506-49af-4115-b34a-60199529b2ca.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQyNjQsIm5iZiI6MTc2MzI5Mzk2NCwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcwNDYzLWI3NTQ2NTA2LTQ5YWYtNDExNS1iMzRhLTYwMTk5NTI5YjJjYS5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMTUyNDRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1lMWZlNDliZjhjYThlNTc5ZmUwM2Q1OTNjOTI4ZWU0MGY0OTNmZWIwY2ZlNDc4NjIwNTJjNDE3NzNlZjBlMGI3JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.C_IDSiBeX1k8ZhjaktZpHTqbf9YuhW0dB2-v8fNUGyw)


## Prerequisites & Dependencies

Before you begin, ensure you have the following installed:

*   **Java:** JDK 17 or higher
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
