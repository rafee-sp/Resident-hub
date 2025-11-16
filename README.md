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

![image](https://private-user-images.githubusercontent.com/195568862/514870562-f8f69dde-bec6-431b-954c-e88beb38a660.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQyNjQsIm5iZiI6MTc2MzI5Mzk2NCwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcwNTYyLWY4ZjY5ZGRlLWJlYzYtNDMxYi05NTRjLWU4OGJlYjM4YTY2MC5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMTUyNDRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1mNWQ4Zjc2NjYxZWQzNTlmNmFjMTA5N2Y5NzIzOTBiMTEzMGIzNjM4OWY1MmFjOTg3M2Y1MDc3YjgyNWZiYWNmJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.-DHko0-BsN4tVw9JHxapTcOcTfpIjrV5drMaxJzCS3k)

![image](https://private-user-images.githubusercontent.com/195568862/514870579-83585195-90d0-4788-a186-3f34e12075af.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQyNjQsIm5iZiI6MTc2MzI5Mzk2NCwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcwNTc5LTgzNTg1MTk1LTkwZDAtNDc4OC1hMTg2LTNmMzRlMTIwNzVhZi5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMTUyNDRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT02ZGRjZmQ3MzQ3MzJmNTE1YjhjYjM2YTU1MzI2YjhkOWViZWEzYWFmMWQ1MTQyZjEyMTRjNjNiNDZmN2UxYjFmJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.AQGmctuiWX1OApfvt3R1WFOUrwcd-VlKtgg1pxA7D0c)

![image](https://private-user-images.githubusercontent.com/195568862/514870588-a6718c22-eb79-42b3-a4a6-959b2313a361.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQyNjQsIm5iZiI6MTc2MzI5Mzk2NCwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcwNTg4LWE2NzE4YzIyLWViNzktNDJiMy1hNGE2LTk1OWIyMzEzYTM2MS5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMTUyNDRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1hZTVmM2RlY2IyYzM0MjY5ZTVkZmMxOGRiODliYTdjYTdiNzk3MjY0M2E4MmZlOWZiMTAyMDJlZmJkM2NlNGI5JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.Gpt4FWSF301yAX82l0CYVI451l6aKvKHjnkxgRgtM3c)

![image](https://private-user-images.githubusercontent.com/195568862/514870595-f902b9bf-045e-4564-8288-df12d6ece611.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQyNjQsIm5iZiI6MTc2MzI5Mzk2NCwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcwNTk1LWY5MDJiOWJmLTA0NWUtNDU2NC04Mjg4LWRmMTJkNmVjZTYxMS5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMTUyNDRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT04MGM2MTJhMGU5ZWI2YTM1YjlkZGNmZjM1ODQ2ZDk4NmM1ZmI0NDAwOGE0YWU1Y2EyN2QyYzI0MGIzYTYyYzQ1JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.sdDi_KCA9EPlQPTNOeYIZ3ySleWfsDsrn9trw9_zFqQ)

![image](https://private-user-images.githubusercontent.com/195568862/514870603-0ae5970e-2275-47ce-ba76-1d4161161dad.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQyNjQsIm5iZiI6MTc2MzI5Mzk2NCwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcwNjAzLTBhZTU5NzBlLTIyNzUtNDdjZS1iYTc2LTFkNDE2MTE2MWRhZC5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMTUyNDRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0wYjcxMzFkYmQwN2YyMDZhMjNjYjRjMzdlYjNhODBkOGNhMDI4ZDYzMGYxZmYwNjYyYTNjY2IyMWViNTY1MDk5JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.5gyvWx1US2SqzT9fzpmvpXatcI5zIzT6GtIb-Cp-sU8)

![image](https://private-user-images.githubusercontent.com/195568862/514870609-7b39bf48-a836-4e11-becc-b3ffbf49ae49.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQyNjQsIm5iZiI6MTc2MzI5Mzk2NCwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcwNjA5LTdiMzliZjQ4LWE4MzYtNGUxMS1iZWNjLWIzZmZiZjQ5YWU0OS5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMTUyNDRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1iOGE2NTFkMDU1NDJlNTdkZjEyYzQ0NzY0NDI5YWFmZWMyOGQ4NzEwZjYyOGY0MWUzYjAyODQwYWE1YTcxOTUyJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.RzjmNE_Ub8qGFtIEilNCq2M3PsG6JHe6ymjcIO3gyXk)

![image](http://private-user-images.githubusercontent.com/195568862/514870623-af531b61-c9f0-42bb-83d8-11f341ad7475.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQyNjQsIm5iZiI6MTc2MzI5Mzk2NCwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcwNjIzLWFmNTMxYjYxLWM5ZjAtNDJiYi04M2Q4LTExZjM0MWFkNzQ3NS5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMTUyNDRaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1iMjkzMGMwOWYwOTA5N2Q5YTBiY2M2Yjc5ZDQ0ZjM5NWI1NzhmOTQ3NGFjNTRiMmY0NjBmZGFjNDRmMzFhMzQ5JlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.cC68nlHOID5XE3871N9DhF-wR8kfXnK27_TZGgve1Ow)

![image](https://private-user-images.githubusercontent.com/195568862/514871354-e236ba8d-8dcc-473a-90b7-0a04e863819d.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQ4MzIsIm5iZiI6MTc2MzI5NDUzMiwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcxMzU0LWUyMzZiYThkLThkY2MtNDczYS05MGI3LTBhMDRlODYzODE5ZC5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMjAyMTJaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0zMjhhZTdlODJjMzQ3OGVhZGNiMTY2N2FjODgzMGRlZDg5ZGI4NjBkNzcxOTg4Yzg1YjAxZWVlNjMzOWQ3NTViJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.BI76Xp9ctJiXpQHweOQkCgF8gSdMlbHTIDGzH6Rpkjs)

![image](https://private-user-images.githubusercontent.com/195568862/514871381-ea79ff96-1b3e-47d5-9292-d48a30bdaef4.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQ4MzIsIm5iZiI6MTc2MzI5NDUzMiwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcxMzgxLWVhNzlmZjk2LTFiM2UtNDdkNS05MjkyLWQ0OGEzMGJkYWVmNC5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMjAyMTJaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1lMDRiMWZkNjI0NDIwM2U2MDA4ZjU0OTVkN2EyODY5ODIwNTQzNDMyMzM3OTQyMTYyNjI0YmM3NTQzMzA5MWMxJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.YK6lpTLUznel2OZjJ2wlt8XYGGW5RjsbOPBL8x63riM)

![image](https://private-user-images.githubusercontent.com/195568862/514871382-d8b6509f-54b4-43aa-b8be-ba73e6fe70d0.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQ4MzIsIm5iZiI6MTc2MzI5NDUzMiwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcxMzgyLWQ4YjY1MDlmLTU0YjQtNDNhYS1iOGJlLWJhNzNlNmZlNzBkMC5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMjAyMTJaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT0yMjRkMWJmZjFjZTExNGFmMzc0ZWVmZDBhYjI5Yzg4YmE2YjYyZTU0ODljNDJjOGVkMjZkNGY5MTdlOGYyMzEwJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.2HFgKbm6zNy609B7BVaH2PYeyrkJ1bsiFCgGZVAWOps)

![image](https://private-user-images.githubusercontent.com/195568862/514871380-9bef3f21-d26b-4db1-8c1a-7f062d67ba92.png?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NjMyOTQ4MzIsIm5iZiI6MTc2MzI5NDUzMiwicGF0aCI6Ii8xOTU1Njg4NjIvNTE0ODcxMzgwLTliZWYzZjIxLWQyNmItNGRiMS04YzFhLTdmMDYyZDY3YmE5Mi5wbmc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD1BS0lBVkNPRFlMU0E1M1BRSzRaQSUyRjIwMjUxMTE2JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MTExNlQxMjAyMTJaJlgtQW16LUV4cGlyZXM9MzAwJlgtQW16LVNpZ25hdHVyZT1lNDU4NjQyYzRlNWMzZTZhMzc0ZjkxMjM3ZTEyZGJmZjViNTQwZWU5ZjA3MzBlYmUyOTE2YzhmNjAxZjYxMTdhJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCJ9.8qBpa0hkj0WL-LziC3189r7kX_fpj060yFt6O9Q8x28)


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
