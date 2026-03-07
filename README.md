# Neonatal Intensive Care Unit (NICU) Project

A comprehensive software system for managing all aspects of neonatal intensive care, from patient admission through discharge and follow-up.

## Overview

This project aims to build a complete, integrated Neonatal Intensive Care Unit (NICU) management system that covers:

- **Patient Management** — Registration, demographics, and mother-infant linkage
- **Real-Time Monitoring** — Continuous vital signs monitoring with alarm management
- **Clinical Documentation** — Comprehensive charting, assessments, and care planning
- **Medication Management** — Weight-based dosing, TPN formulation, and pharmacy integration
- **Nutrition & Feeding** — Enteral feeding tracking, breast milk management, and growth optimization
- **Respiratory Care** — Ventilator management, ABG trending, and weaning protocols
- **Laboratory Integration** — Order entry, result management, and critical value alerts
- **Growth & Development** — Growth charts, developmental milestones, and neurodevelopmental assessments
- **Clinical Decision Support** — Evidence-based alerts, sepsis screening, and treatment pathways
- **Infection Control** — CLABSI surveillance, antimicrobial stewardship, and outbreak detection
- **Family Engagement** — Parent portal, education, and family-centered care tools
- **Discharge Planning** — Readiness tracking, discharge education, and follow-up coordination
- **Reporting & Analytics** — Quality metrics, VON reporting, and benchmarking
- **Regulatory Compliance** — HIPAA, Joint Commission, and CMS compliance
- **Research Integration** — Clinical trial management and quality improvement tools

## Project Milestones

The project is organized into 25 comprehensive milestones spanning approximately 42 weeks. See [MILESTONES.md](MILESTONES.md) for the complete milestone plan with detailed deliverables and acceptance criteria.

| Phase | Milestones | Weeks |
|-------|-----------|-------|
| **Foundation** | M1: Infrastructure & Architecture | 1–4 |
| **Core Clinical** | M2–M8: Patient, Vitals, Charting, Meds, Nutrition, Respiratory, Labs | 5–20 |
| **Advanced Clinical** | M9–M15: Growth, Alerts, Infection, Thermal, Pain, Nursing, Orders | 18–28 |
| **Engagement & Operations** | M16–M19: Family Portal, Discharge, Staffing, Devices | 25–34 |
| **Quality & Compliance** | M20–M24: Analytics, Security, Transport, Palliative, Research | 30–38 |
| **Go-Live** | M25: Testing, Training & Deployment | 35–42 |

## Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| **Backend** | Java (OpenJDK) + Spring Boot | 21 LTS / 3.x |
| **Frontend** | Angular | 17+ |
| **FHIR** | HAPI FHIR | 7.x |
| **Database** | PostgreSQL (prod), H2 Database (dev/test), TimescaleDB, MongoDB, Redis | Latest |
| **DB Versioning** | Flyway | Latest |
| **Search** | Elasticsearch | Latest |
| **Messaging** | Apache Kafka | 3.x |
| **Build** | Gradle (backend), Angular CLI (frontend) | Latest |
| **CI/CD** | GitHub Actions | N/A |
| **Containerization** | Docker, Kubernetes | Latest |
| **Code Coverage** | JaCoCo (Java), Istanbul (Angular) — **100% required** | Latest |

## Documentation

- [MILESTONES.md](MILESTONES.md) — Complete milestone plan with deliverables and acceptance criteria
- [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) — System architecture overview (Java + Angular)
- [docs/COMPLIANCE.md](docs/COMPLIANCE.md) — Regulatory compliance requirements
- [.github/copilot-instructions.md](.github/copilot-instructions.md) — GitHub Copilot best practices and coding guidelines

## Key Design Principles

1. **Patient Safety First** — Every feature prioritizes the safety of the most vulnerable patients
2. **Evidence-Based** — Clinical decision support grounded in published guidelines and best practices
3. **Interoperable** — HL7 FHIR-native design for seamless data exchange
4. **Family-Centered** — Parents are partners in care, not visitors
5. **Clinician-Friendly** — Workflows designed to minimize documentation burden
6. **Secure & Compliant** — HIPAA compliance and data security built into every layer
7. **Scalable** — Architecture supports single-unit to multi-facility deployments

## License

This project is proprietary. All rights reserved.