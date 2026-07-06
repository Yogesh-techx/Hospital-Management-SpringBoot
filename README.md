# Hospital Management System

A backend Hospital Management System built using Java, Spring Boot, Spring Data JPA, Hibernate, PostgreSQL, Maven, and REST APIs.

The application provides RESTful APIs for managing hospital departments, doctors, patients, appointments, medical records, and prescriptions. It follows a layered architecture with DTO-based API communication, business-rule validation, JPA entity relationships, centralized exception handling, standardized API responses, and PostgreSQL persistence.

## Features

- Department creation, retrieval, update, deletion, and name-based search
- Doctor creation and bulk insertion
- Doctor search by department, specialization, appointment, patient, and availability day
- Patient registration, retrieval, update, deletion, contact-based search, and age-based filtering
- Appointment booking and lifecycle management
- Appointment search by date, status, patient, and doctor
- Doctor scheduling conflict validation
- Patient same-day appointment restriction
- Medical record creation for completed appointments
- One medical record per appointment
- Medical record retrieval by patient, doctor, and visiting date
- Medical record diagnosis and treatment updates
- Prescription creation for existing medical records
- One prescription per medical record
- Prescription retrieval by medical record and patient
- DTO-based request and response handling
- Bean Validation for selected API request DTOs
- Custom exception classes
- Centralized exception handling using `@RestControllerAdvice`
- Standardized API response structure
- Layered Controller-Service-Repository architecture
- PostgreSQL persistence using Spring Data JPA and Hibernate

## Application Architecture

The application follows a layered architecture.

```text
                         Client / Postman
                                |
                                v
                        REST Controller Layer
                                |
                                v
                         Service Interface
                                |
                                v
                      Service Implementation Layer
                                |
                                v
                    Spring Data JPA Repository Layer
                                |
                                v
                         PostgreSQL Database
```

### Controller Layer

The controller layer exposes REST endpoints, receives request DTOs, delegates business operations to service interfaces, and returns standardized API responses using `ResponseEntity<ApiResponse<T>>`.

### Service Layer

Service interfaces define the application operations available for each domain.

Service implementation classes contain the business logic, entity-to-DTO mapping, DTO-to-entity mapping where required, validation of application rules, and coordination between repositories.

### Repository Layer

The repository layer uses Spring Data JPA repositories and derived query methods to perform persistence operations and relationship-based database queries.

### DTO Layer

Request and response DTOs are used to define API input and output structures.

The application does not expose JPA entities directly through the REST controllers.

### Exception Handling

Custom runtime exceptions are handled globally using `GlobalExceptionHandler`.

The application uses a standardized `ApiResponse<T>` structure for both successful and error responses.

## Domain Model

The application contains six main JPA entities:

- `Department`
- `Doctor`
- `Patient`
- `Appointment`
- `MedicalRecord`
- `Prescription`

It also uses the following enums:

- `AppointmentStatus`
- `AvailabilityDays`
- `Gender`

## Entity Relationships

```text
Department
    |
    | One-to-Many
    v
Doctor
    |
    | One-to-Many
    +----------------------> Appointment <----------------------+
    |                              ^                            |
    |                              |                            |
    |                              | Many-to-One                |
    |                              |                            |
    +----------------------> MedicalRecord <---------------- Patient
                                   |
                                   | One-to-One
                                   v
                              Prescription
```

Additional relationship details:

- A `Department` can contain multiple `Doctor` entities.
- A `Doctor` belongs to one `Department`.
- A `Doctor` can have multiple `Appointment` entities.
- A `Patient` can have multiple `Appointment` entities.
- An `Appointment` belongs to one `Doctor` and one `Patient`.
- A `Doctor` can be associated with multiple `MedicalRecord` entities.
- A `Patient` can be associated with multiple `MedicalRecord` entities.
- A `MedicalRecord` belongs to one `Appointment`, one `Doctor`, and one `Patient`.
- The `Appointment` to `MedicalRecord` relationship is one-to-one from the medical-record side because the `appointment_id` column is unique.
- A `Prescription` belongs to one `MedicalRecord`.
- The `MedicalRecord` to `Prescription` relationship is one-to-one from the prescription side because the `record_id` column is unique.
- Doctor availability days are stored using an `@ElementCollection`.

## Business Rules Implemented

The application enforces the following business rules.

### Department Rules

1. Department names must be unique according to the application's department-name lookup and database unique constraint.
2. A department cannot be deleted while doctors are assigned to it.
3. A doctor cannot be created unless the specified department exists.

### Patient Rules

1. Patient contact numbers must contain exactly 10 digits when validated through `PatientRequestDTO`.
2. Patient contact numbers must be unique.
3. Patient email addresses must be unique.
4. Patient age cannot be negative when validated through `PatientRequestDTO`.
5. Patient gender is converted from the request string to the `Gender` enum.

### Appointment Rules

1. A doctor must exist before an appointment can be booked.
2. A patient must exist before an appointment can be booked.
3. A doctor cannot have more than one appointment at the exact same date and time.
4. A patient cannot have more than one appointment on the same calendar day.
5. New appointments are created with the `BOOKED` status.
6. Appointment status can be updated using the status endpoint.
7. Appointments can be cancelled using the dedicated cancellation endpoint.

### Medical Record Rules

1. A medical record can only be created when the associated appointment exists.
2. A medical record can only be created for an appointment with `COMPLETED` status.
3. Only one medical record can be created for an appointment.
4. The doctor and patient associated with a medical record are obtained from the corresponding appointment.
5. The medical record visiting date is assigned using the current application date when the record is created.
6. Diagnosis and treatment can be updated for an existing medical record.

### Prescription Rules

1. A prescription can only be created when the specified medical record exists.
2. Only one prescription can exist for a medical record.
3. Prescription response data includes the patient and doctor names obtained through the associated medical record.

## Appointment Status Values

```text
BOOKED
CANCELLED
COMPLETED
```

## Doctor Availability Values

```text
MONDAY
TUESDAY
WEDNESDAY
THURSDAY
FRIDAY
SATURDAY
SUNDAY
```

## Gender Values

```text
MALE
FEMALE
OTHER
```

## Project Structure

```text
src/main/java/com/hospital/management/

├── HospitalManagementSystemApplication.java
│
├── controller/
│   ├── AppointmentController.java
│   ├── DepartmentController.java
│   ├── DoctorController.java
│   ├── MedicalRecordController.java
│   ├── PatientController.java
│   └── PrescriptionController.java
│
├── dto/
│   ├── ApiResponse.java
│   ├── AppointmentRequestDTO.java
│   ├── AppointmentResponseDTO.java
│   ├── DepartmentRequestDTO.java
│   ├── DepartmentResponseDTO.java
│   ├── DoctorRequestDTO.java
│   ├── DoctorResponseDTO.java
│   ├── MedicalRecordRequestDTO.java
│   ├── MedicalRecordResponseDTO.java
│   ├── PatientRequestDTO.java
│   ├── PatientResponseDTO.java
│   ├── PrescriptionRequestDTO.java
│   └── PrescriptionResponseDTO.java
│
├── exception/
│   ├── DuplicateResourceException.java
│   ├── GlobalExceptionHandler.java
│   ├── InvalidOperationException.java
│   └── ResourceNotFoundException.java
│
├── model/
│   ├── Appointment.java
│   ├── Department.java
│   ├── Doctor.java
│   ├── MedicalRecord.java
│   ├── Patient.java
│   └── Prescription.java
│
├── repository/
│   ├── AppointmentRepository.java
│   ├── DepartmentRepository.java
│   ├── DoctorRepository.java
│   ├── MedicalRecordRepository.java
│   ├── PatientRepository.java
│   └── PrescriptionRepository.java
│
├── service/
│   ├── AppointmentService.java
│   ├── DepartmentService.java
│   ├── DoctorService.java
│   ├── MedicalRecordService.java
│   ├── PatientService.java
│   └── PrescriptionService.java
│
├── service/impl/
│   ├── AppointmentServiceImpl.java
│   ├── DepartmentServiceImpl.java
│   ├── DoctorServiceImpl.java
│   ├── MedicalRecordServiceImpl.java
│   ├── PatientServiceImpl.java
│   └── PrescriptionServiceImpl.java
│
└── util/
    ├── AppointmentStatus.java
    ├── AvailabilityDays.java
    └── Gender.java
```

## API Response Structure

The application wraps successful and error responses using the generic `ApiResponse<T>` class.

```json
{
  "status": 200,
  "message": "OK",
  "data": {}
}
```

The response contains:

| Field | Description |
|---|---|
| `status` | Numeric HTTP status code |
| `message` | HTTP reason phrase for standard successful responses or exception message for handled errors |
| `data` | Response payload or `null` when no data is returned |

### Example Successful Response

```json
{
  "status": 201,
  "message": "Created",
  "data": {
    "patientId": 1,
    "patientName": "Rahul Sharma",
    "age": 30,
    "gender": "MALE",
    "contact": "9876543210",
    "email": "rahul@example.com"
  }
}
```

### Example Error Response

```json
{
  "status": 404,
  "message": "Patient not found with id: 99",
  "data": null
}
```

## REST API Endpoints

### Department APIs

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/api/departments` | Create a department |
| GET | `/api/departments` | Retrieve all departments |
| GET | `/api/departments/{id}` | Retrieve a department by ID |
| PUT | `/api/departments/{id}` | Update a department |
| DELETE | `/api/departments/{id}` | Delete a department |
| GET | `/api/departments/name/{name}` | Retrieve a department by name |

### Doctor APIs

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/api/doctors` | Create a doctor |
| POST | `/api/doctors/bulk` | Create multiple doctors |
| GET | `/api/doctors` | Retrieve all doctors |
| GET | `/api/doctors/{id}` | Retrieve a doctor by ID |
| DELETE | `/api/doctors/{id}` | Delete a doctor |
| GET | `/api/doctors/department/{deptId}` | Retrieve doctors by department |
| GET | `/api/doctors/specialization/{spec}` | Retrieve doctors by specialization |
| GET | `/api/doctors/appointment/{appointmentId}` | Retrieve doctors by appointment |
| GET | `/api/doctors/patient/{patientId}` | Retrieve doctors associated with a patient through medical records |
| GET | `/api/doctors/availability/{day}` | Retrieve doctors by availability day |

### Patient APIs

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/api/patients` | Register a patient |
| GET | `/api/patients` | Retrieve all patients |
| GET | `/api/patients/{id}` | Retrieve a patient by ID |
| PUT | `/api/patients/{id}` | Update a patient |
| DELETE | `/api/patients/{id}` | Delete a patient |
| GET | `/api/patients/contact/{contact}` | Retrieve a patient by contact number |
| GET | `/api/patients/age/{age}` | Retrieve patients older than the specified age |

### Appointment APIs

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/api/appointments` | Book an appointment |
| GET | `/api/appointments` | Retrieve all appointments |
| GET | `/api/appointments/{id}` | Retrieve an appointment by ID |
| PUT | `/api/appointments/{id}/cancel` | Cancel an appointment |
| PUT | `/api/appointments/{id}/status?status={status}` | Update appointment status |
| GET | `/api/appointments/date/{date}` | Retrieve appointments by date |
| GET | `/api/appointments/status/{status}` | Retrieve appointments by status |
| GET | `/api/appointments/patient/{patientId}` | Retrieve appointments by patient |
| GET | `/api/appointments/doctor/{doctorId}` | Retrieve appointments by doctor |

### Medical Record APIs

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/api/records` | Create a medical record |
| GET | `/api/records` | Retrieve all medical records |
| GET | `/api/records/{id}` | Retrieve a medical record by ID |
| GET | `/api/records/patient/{patientId}` | Retrieve medical records by patient |
| GET | `/api/records/date/{date}` | Retrieve medical records by visiting date |
| GET | `/api/records/doctor/{doctorId}` | Retrieve medical records by doctor |
| PUT | `/api/records/{id}` | Update diagnosis and treatment of a medical record |

### Prescription APIs

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/api/prescriptions` | Create a prescription |
| GET | `/api/prescriptions` | Retrieve all prescriptions |
| GET | `/api/prescriptions/{id}` | Retrieve a prescription by ID |
| GET | `/api/prescriptions/record/{recordId}` | Retrieve a prescription by medical record |
| GET | `/api/prescriptions/patient/{patientId}` | Retrieve prescriptions by patient |

## Example API Requests

### Create a Department

```http
POST /api/departments
Content-Type: application/json
```

```json
{
  "departmentName": "Cardiology"
}
```

### Create a Doctor

```http
POST /api/doctors
Content-Type: application/json
```

```json
{
  "doctorName": "Dr. Ananya Rao",
  "specialization": "Cardiologist",
  "departmentId": 1,
  "availabilityDays": [
    "MONDAY",
    "WEDNESDAY",
    "FRIDAY"
  ]
}
```

### Register a Patient

```http
POST /api/patients
Content-Type: application/json
```

```json
{
  "patientName": "Rahul Sharma",
  "age": 30,
  "gender": "MALE",
  "contact": "9876543210",
  "email": "rahul@example.com"
}
```

### Book an Appointment

The appointment date and time must use an ISO-8601 `LocalDateTime` compatible format because the service parses the request value using `LocalDateTime.parse()`.

```http
POST /api/appointments
Content-Type: application/json
```

```json
{
  "doctorId": 1,
  "patientId": 1,
  "appointmentDateTime": "2026-07-10T10:30:00"
}
```

### Update Appointment Status

```http
PUT /api/appointments/1/status?status=COMPLETED
```

### Create a Medical Record

The corresponding appointment must exist and have `COMPLETED` status.

```http
POST /api/records
Content-Type: application/json
```

```json
{
  "appointmentId": 1,
  "diagnosis": "Viral fever",
  "treatment": "Rest, hydration, and prescribed medication"
}
```

### Update a Medical Record

The current implementation accepts `MedicalRecordRequestDTO` for updates but updates only the diagnosis and treatment fields.

```http
PUT /api/records/1
Content-Type: application/json
```

```json
{
  "appointmentId": 1,
  "diagnosis": "Updated diagnosis",
  "treatment": "Updated treatment"
}
```

### Create a Prescription

```http
POST /api/prescriptions
Content-Type: application/json
```

```json
{
  "recordId": 1,
  "medicine": "Paracetamol 500 mg",
  "dosageInstructions": "Take one tablet twice daily after meals"
}
```

## Validation and Exception Handling

The application defines the following custom exceptions:

- `ResourceNotFoundException` → `404 NOT FOUND`
- `DuplicateResourceException` → `409 CONFLICT`
- `InvalidOperationException` → `400 BAD REQUEST`
- Request DTO validation errors → `400 BAD REQUEST`
- Unhandled exceptions → `500 INTERNAL SERVER ERROR`

Bean Validation annotations used in the current codebase include:

- `@NotNull`
- `@NotBlank`
- `@Min`
- `@Pattern`
- `@Email`

## Prerequisites

Before running the application, ensure the following are installed:

- Java version compatible with the project's Maven configuration
- PostgreSQL
- Git

## Database Setup

Create a PostgreSQL database for the application.

```sql
CREATE DATABASE hospital_management;
```

Configure the PostgreSQL connection according to your local environment.

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hospital_management
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Running the Application

Clone the repository:

```bash
git clone https://github.com/Yogesh-techx/Hospital-Management-SpringBoot.git
```

Navigate to the project directory:

```bash
cd Hospital-Management-SpringBoot
```

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

## Building the Application

### Windows

```bash
mvnw.cmd clean package
```

### Linux/macOS

```bash
./mvnw clean package
```

After a successful build, the generated JAR file will be available inside the `target` directory.

Run it using:

```bash
java -jar target/<generated-jar-file>.jar
```

## Recommended API Testing Workflow

1. Create a department.
2. Create a doctor associated with the department.
3. Register a patient.
4. Book an appointment between the doctor and patient.
5. Retrieve and verify the appointment.
6. Update the appointment status to `COMPLETED`.
7. Create a medical record using the completed appointment ID.
8. Retrieve the medical record.
9. Create a prescription using the medical record ID.
10. Retrieve the prescription by ID, medical record, or patient.

This sequence exercises the primary entity relationships and business rules implemented by the application.

## Current Project Scope

The current implementation is a Spring Boot layered backend application.

The project currently focuses on:

- REST API development
- Spring Boot backend development
- Spring Data JPA and Hibernate
- PostgreSQL persistence
- JPA entity relationships
- DTO-based API design
- Business-rule implementation
- Bean Validation
- Centralized exception handling
- Standardized API responses
- Derived query methods
- API testing using Postman

## Future Enhancements

- Spring Security authentication and role-based authorization
- Application logging improvements
- Appointment lifecycle transition validation
- Doctor availability validation during appointment booking
- Database query and indexing optimization
- Docker containerization
- CI/CD pipeline
- Microservices-based architecture

## Repository

Hospital Management System Source Code:

`https://github.com/Yogesh-techx/Hospital-Management-SpringBoot`

## Author

**Yogesh**

Java Backend Developer

## License

This project is intended for educational and portfolio purposes.