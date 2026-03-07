# System Architecture Overview

> **High-level architecture for the Neonatal Intensive Care Unit (NICU) management system.**
> **Technology Stack: Java 21 + Spring Boot 3.x (Backend) | Angular 17+ (Frontend)**

---

## Architecture Principles

1. **Microservices-Based** — Loosely coupled Spring Boot services for independent scaling and deployment
2. **API-First** — All functionality exposed through well-documented REST/FHIR APIs (SpringDoc OpenAPI)
3. **Event-Driven** — Real-time data flows through Apache Kafka for vital signs and alerts
4. **Cloud-Native** — Containerized Spring Boot services deployable on any cloud or on-premise Kubernetes cluster
5. **Security by Design** — Spring Security with OAuth 2.0/OIDC, encryption, and audit built into every layer
6. **100% Code Coverage** — JaCoCo (Java) and Istanbul (Angular) enforced in CI pipeline

---

## Technology Stack

### Backend

| Technology | Purpose | Version |
|-----------|---------|---------|
| **Java (OpenJDK)** | Programming language | 21 LTS |
| **Spring Boot** | Application framework | 3.x |
| **Spring Security** | Authentication & authorization | 6.x |
| **Spring Data JPA** | Data access (PostgreSQL prod, H2 dev/test) | 3.x |
| **Spring WebFlux** | Reactive APIs for real-time data | 3.x |
| **Spring Cloud** | Microservices patterns (config, discovery, gateway) | 2023.x |
| **HAPI FHIR** | HL7 FHIR R4 implementation | 7.x |
| **Apache Kafka** | Event streaming | 3.x |
| **Gradle** | Build tool | 8.x |
| **JUnit 5 + Mockito** | Testing | Latest |
| **JaCoCo** | Code coverage (100% enforced) | Latest |
| **Testcontainers** | Integration test infrastructure | Latest |
| **Flyway** | Database versioning and migration | Latest |
| **MapStruct** | DTO mapping | Latest |
| **Lombok** | Boilerplate reduction | Latest |

### Frontend

| Technology | Purpose | Version |
|-----------|---------|---------|
| **Angular** | SPA framework | 17+ |
| **Angular Material** | UI component library | 17+ |
| **NgRx** | State management | Latest |
| **RxJS** | Reactive programming | 7.x |
| **TypeScript** | Programming language | 5.x |
| **Angular CLI** | Build & scaffolding | 17+ |
| **Jasmine + Karma** | Unit testing | Latest |
| **Cypress** | End-to-end testing | Latest |
| **Istanbul / nyc** | Code coverage (100% enforced) | Latest |

---

## System Components

### 1. Presentation Layer (Angular 17+)

| Component | Technology | Purpose |
|-----------|-----------|---------|
| **Clinical Web Application** | Angular 17+ SPA | Browser-based clinician interface (responsive, works on tablets) |
| **Bedside Display** | Angular component library | Real-time vital signs and patient summary at point of care |
| **Parent Portal** | Angular 17+ SPA (separate app) | Family-facing web/mobile application |
| **Admin Dashboard** | Angular 17+ SPA | System administration, configuration, and reporting |
| **Mobile Application** | Angular + Capacitor/Ionic | On-call alerts, quick charting, and secure messaging |

### 2. API Gateway (Spring Cloud Gateway)

- Centralized entry point for all client requests (Spring Cloud Gateway)
- Rate limiting, request throttling, and circuit breaking (Resilience4j)
- Authentication token validation (Spring Security + OAuth 2.0 / OpenID Connect)
- API versioning and backward compatibility management
- Request/response logging for audit trail (Spring Cloud Sleuth + Micrometer)

### 3. Core Services (Spring Boot Microservices)

| Service | Responsibility |
|---------|---------------|
| **Patient Service** | Registration, demographics, mother-infant linkage, MRN management |
| **Vitals Service** | Real-time vital signs ingestion, storage, trending, and alarming |
| **Documentation Service** | Clinical notes, assessments, flowsheets, and charting |
| **Medication Service** | Ordering, dispensing, administration, and safety checks |
| **Nutrition Service** | Feeding management, TPN, breast milk tracking, caloric calculations |
| **Respiratory Service** | Ventilator management, ABG tracking, respiratory protocols |
| **Laboratory Service** | Order entry, result management, critical value alerting |
| **Growth Service** | Growth tracking, percentile calculations, developmental milestones |
| **Alert Service** | Clinical decision support rules engine, alarm management |
| **Infection Service** | Surveillance, antibiotic stewardship, NHSN reporting |
| **Order Service** | CPOE, order sets, order routing, and verification |
| **Scheduling Service** | Staff scheduling, patient appointments, procedure scheduling |
| **Reporting Service** | Analytics, quality metrics, VON reporting, dashboards |
| **Notification Service** | Push notifications, alerts, secure messaging |
| **Audit Service** | Comprehensive audit trail for all data access and modifications |
| **Identity Service** | User authentication, authorization, RBAC, session management |

### 4. Integration Layer

| Integration | Standard/Protocol |
|-------------|------------------|
| **FHIR Server** | HL7 FHIR R4 for data exchange and interoperability |
| **HL7 v2.x Engine** | ADT, ORM, ORU message processing for legacy systems |
| **Device Gateway** | IEEE 11073 / proprietary protocols for bedside device data |
| **LIS Interface** | Laboratory Information System bidirectional interface |
| **RIS/PACS Interface** | Radiology ordering and imaging result retrieval |
| **Pharmacy Interface** | Pharmacy system integration for medication verification |
| **HIE Connector** | Health Information Exchange network connectivity |
| **Smart Pump Interface** | IV pump programming and status monitoring |

### 5. Data Layer

| Store | Technology | Purpose |
|-------|-----------|---------|
| **Clinical Database** | PostgreSQL | Patient records, orders, documentation |
| **Time-Series Database** | TimescaleDB / InfluxDB | Vital signs, waveforms, continuous monitoring data |
| **Document Store** | MongoDB | Unstructured clinical notes, CDA/CCD documents |
| **Cache Layer** | Redis | Session management, real-time data caching |
| **Search Engine** | Elasticsearch | Full-text clinical search, patient lookup |
| **File Storage** | Object Storage (S3-compatible) | Images, documents, attachments |
| **Message Queue** | Apache Kafka | Event streaming for real-time data flows |

### 6. Infrastructure

| Component | Technology | Purpose |
|-----------|-----------|---------|
| **Container Orchestration** | Kubernetes | Service deployment and scaling of Spring Boot containers |
| **Service Mesh** | Istio | Service-to-service communication and security |
| **CI/CD Pipeline** | GitHub Actions | Automated Gradle/Angular build, test (100% coverage), and deployment |
| **Monitoring** | Prometheus + Grafana + Micrometer | System metrics, Spring Boot Actuator endpoints, and alerting |
| **Logging** | ELK Stack (Elasticsearch, Logstash, Kibana) | Centralized logging via SLF4J/Logback |
| **Secret Management** | HashiCorp Vault | Credentials and encryption keys (Spring Cloud Vault) |
| **CDN** | Content delivery | Static Angular assets and parent portal |

---

## Data Flow Diagrams

### Real-Time Vital Signs Flow

```
Bedside Monitor → Device Gateway → Kafka → Vitals Service → Time-Series DB
                                      ↓
                                 Alert Service → Notification Service → Clinician
                                      ↓
                                 Clinical Dashboard (WebSocket)
```

### Medication Order Flow

```
Physician → CPOE → Order Service → CDS Rules Engine → Safety Check
                                          ↓
                                    Pharmacy Queue → Pharmacist Verification
                                          ↓
                                    MAR → Nurse → Barcode Scan → Administration
                                          ↓
                                    Smart Pump → Infusion Delivery
```

### Lab Order Flow

```
Clinician → Order Entry → Order Service → LIS Interface → Laboratory
                                                ↓
                           Result Router ← Result Message (HL7 ORU)
                                ↓
                          Critical Value? → Alert Service → Immediate Notification
                                ↓
                          Result Display → Clinical Dashboard
```

---

## Security Architecture

### Authentication & Authorization

- OAuth 2.0 + OpenID Connect for single sign-on
- Multi-factor authentication (MFA) for external access
- Role-based access control (RBAC) with fine-grained permissions
- Break-the-glass emergency access with mandatory post-access review
- Session management with configurable timeout policies

### Data Protection

- AES-256 encryption for data at rest
- TLS 1.3 for all data in transit
- Field-level encryption for highly sensitive data (SSN, financial)
- Database-level transparent data encryption (TDE)
- Encrypted backups with separate key management

### Network Security

- Network segmentation (clinical, administrative, public zones)
- Web Application Firewall (WAF) for all public endpoints
- DDoS protection for internet-facing services
- VPN required for administrative access
- Intrusion detection and prevention systems (IDS/IPS)

---

## Scalability & Performance

### Performance Targets

| Metric | Target |
|--------|--------|
| Page load time | < 2 seconds |
| Vital signs latency | < 3 seconds (monitor to display) |
| API response time (p95) | < 500 milliseconds |
| Concurrent users | 200+ per unit |
| Data retention | 7 years (configurable per regulation) |
| System uptime | 99.9% (< 8.76 hours downtime/year) |

### Scaling Strategy

- Horizontal scaling of stateless services via Kubernetes
- Database read replicas for reporting workloads
- Time-series data partitioning by date for query performance
- CDN for static content and parent portal assets
- Connection pooling for database efficiency

---

## Disaster Recovery

| Parameter | Target |
|-----------|--------|
| Recovery Point Objective (RPO) | < 1 hour |
| Recovery Time Objective (RTO) | < 4 hours |
| Backup Frequency | Continuous (streaming replication) + hourly snapshots |
| DR Site | Geographically separate, warm standby |
| Failover | Automated with manual confirmation |
| DR Testing | Quarterly full failover drill |
