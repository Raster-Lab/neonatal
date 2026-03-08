# GitHub Copilot Instructions — NICU Management System

> **Best practices and coding guidelines for the Neonatal Intensive Care Unit (NICU) project.**
> This file configures GitHub Copilot to generate code consistent with the project's standards.

---

## Project Overview

This is a healthcare-critical NICU management system built with:

- **Backend:** Java 21 LTS + Spring Boot 3.x (microservices)
- **Frontend:** Angular 17+ (single-page application)
- **FHIR:** HAPI FHIR 7.x for HL7 FHIR R4 interoperability
- **Database:** PostgreSQL (production), H2 Database (dev/test), TimescaleDB (time-series), MongoDB (documents), Redis (cache)
- **DB Versioning:** Flyway
- **Messaging:** Apache Kafka for event streaming
- **Build:** Gradle (backend), Angular CLI (frontend)
- **CI/CD:** GitHub Actions

---

## Code Coverage Requirement

**100% code coverage is mandatory** across all modules. No exceptions unless explicitly documented and approved.

### Backend (Java)

- Use **JaCoCo** for coverage measurement
- CI pipeline fails if coverage drops below 100%
- Only approved exclusions: Spring Boot main application class, generated code (MapStruct, Lombok)
- Every public method must have corresponding unit tests
- Integration tests must cover all API endpoints

### Frontend (Angular)

- Use **Istanbul/nyc** (integrated with Angular CLI) for coverage measurement
- CI pipeline fails if coverage drops below 100% (statements, branches, functions, lines)
- Every component, service, pipe, guard, and directive must have a `.spec.ts` file
- E2E tests (Cypress) must cover all critical user workflows

---

## Java / Spring Boot Best Practices

### General

- Use **Java 21** features: records, sealed classes, pattern matching, virtual threads where appropriate
- Follow **Google Java Style Guide** conventions
- Use **Lombok** to reduce boilerplate (`@Data`, `@Builder`, `@Slf4j`, etc.)
- Use **MapStruct** for DTO-to-entity mapping — avoid manual mapping code
- Prefer **constructor injection** over field injection (`@Autowired` on fields is discouraged)
- Use `final` for fields, method parameters, and local variables whenever possible
- All classes must be documented with Javadoc (at minimum: class-level description, public method descriptions)

### Spring Boot Services

```java
// Preferred service structure
@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Transactional(readOnly = true)
    public PatientDto findById(final UUID id) {
        return patientRepository.findById(id)
            .map(patientMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }
}
```

### REST Controllers

```java
// Preferred controller structure
@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "Patient registration and demographics")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID")
    public ResponseEntity<PatientDto> getPatient(@PathVariable final UUID id) {
        return ResponseEntity.ok(patientService.findById(id));
    }
}
```

### Exception Handling

- Use a global `@RestControllerAdvice` for centralized exception handling
- Define domain-specific exceptions (`ResourceNotFoundException`, `ValidationException`, etc.)
- Return RFC 7807 Problem Detail responses for errors
- Never expose stack traces or internal details in API error responses

### Data Access

- Use **Spring Data JPA** repositories with custom query methods
- Use **Flyway** for database migrations — never modify schema manually
- Use **UUID** for primary keys on all entities
- Use **optimistic locking** (`@Version`) for concurrent access protection
- Implement **soft deletes** for clinical data (regulatory requirement)

### Security

- Annotate all controller methods with `@PreAuthorize` for method-level security
- Use Spring Security's OAuth 2.0 Resource Server configuration
- Validate all input using `@Valid` and Jakarta Bean Validation annotations
- Sanitize all user input to prevent injection attacks
- Log all authentication and authorization events for audit trail
- Never log PHI (Protected Health Information) — use identifiers only

### Testing (Java)

```java
// Unit test example
@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    @Test
    void findById_existingPatient_returnsDto() {
        // Given
        final var id = UUID.randomUUID();
        final var entity = new Patient();
        final var dto = new PatientDto();
        when(patientRepository.findById(id)).thenReturn(Optional.of(entity));
        when(patientMapper.toDto(entity)).thenReturn(dto);

        // When
        final var result = patientService.findById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }
}
```

- Follow **Given-When-Then** (Arrange-Act-Assert) pattern
- Use **AssertJ** for fluent assertions
- Use **Testcontainers** for database integration tests
- Mock external dependencies with **Mockito**
- Name test methods: `methodName_condition_expectedResult`

---

## Angular Best Practices

### General

- Use **strict TypeScript** mode (`strict: true` in `tsconfig.json`)
- Follow the **Angular Style Guide** (https://angular.dev/style-guide)
- Use **standalone components** (Angular 17+ default)
- Use **signals** for reactive state where appropriate (Angular 17+)
- Use **NgRx** for global state management
- Prefer **OnPush** change detection strategy for all components
- Use **lazy loading** for feature modules

### Component Structure

```typescript
// Preferred component structure
@Component({
  selector: 'app-patient-detail',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule],
  templateUrl: './patient-detail.component.html',
  styleUrl: './patient-detail.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PatientDetailComponent implements OnInit {
  private readonly patientService = inject(PatientService);
  private readonly route = inject(ActivatedRoute);

  readonly patient = signal<Patient | null>(null);

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.patientService.getById(id).subscribe(p => this.patient.set(p));
    }
  }
}
```

### Services

```typescript
// Preferred service structure
@Injectable({ providedIn: 'root' })
export class PatientService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/v1/patients';

  getById(id: string): Observable<Patient> {
    return this.http.get<Patient>(`${this.apiUrl}/${id}`);
  }
}
```

### Testing (Angular)

```typescript
// Unit test example
describe('PatientService', () => {
  let service: PatientService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(PatientService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch patient by ID', () => {
    const mockPatient: Patient = { id: '123', name: 'Baby Doe' };

    service.getById('123').subscribe(patient => {
      expect(patient).toEqual(mockPatient);
    });

    const req = httpMock.expectOne('/api/v1/patients/123');
    expect(req.request.method).toBe('GET');
    req.flush(mockPatient);
  });
});
```

- Every component must have a corresponding `.spec.ts` file
- Use `HttpClientTestingModule` for HTTP service tests
- Use `ComponentFixture` for component tests
- Test both success and error paths
- Test user interactions (clicks, form submissions)

### Accessibility (WCAG 2.1 AA)

- Use semantic HTML (`<main>`, `<nav>`, `<section>`, `<article>`)
- Add `aria-label` and `aria-describedby` where needed
- Ensure keyboard navigation for all interactive elements
- Maintain minimum 4.5:1 contrast ratio for text
- All form fields must have associated `<label>` elements

---

## FHIR / Healthcare-Specific Guidelines

### HAPI FHIR

- Use **HAPI FHIR 7.x** for all FHIR resource handling
- Map internal domain models to/from FHIR resources explicitly — never expose internal entities directly
- Validate all FHIR resources against the appropriate profiles before persisting
- Use FHIR references (`Reference`) for linking resources (e.g., Patient → Encounter)

### Neonatal-Specific

- Always store gestational age as weeks + days (not just weeks)
- Weight must be stored in grams (g) with precision to 1g
- Temperature must support multiple sites (axillary, rectal, skin) with site annotation
- APGAR scores must be validated (0–10 range, recorded at 1, 5, and optionally 10 minutes)
- All dosing calculations must use current weight — flag stale weights (> 24 hours)

### PHI Protection

- Never log PHI in application logs — use patient identifiers (MRN, UUID) only
- Apply field-level encryption for SSN and financial data
- All API responses must be filtered by the requesting user's RBAC permissions
- Implement audit logging for every PHI access event
- Support data de-identification for research exports (Safe Harbor method)

---

## API Design Standards

- Follow **RESTful** conventions: `GET`, `POST`, `PUT`, `PATCH`, `DELETE`
- Use plural nouns for resource paths: `/api/v1/patients`, `/api/v1/vitals`
- Version all APIs: `/api/v1/...`, `/api/v2/...`
- Return appropriate HTTP status codes (200, 201, 204, 400, 401, 403, 404, 409, 422, 500)
- Use **RFC 7807 Problem Detail** format for error responses
- Paginate list endpoints: `?page=0&size=20&sort=createdAt,desc`
- Document all endpoints with **SpringDoc OpenAPI** annotations (`@Operation`, `@Tag`, `@ApiResponse`)

---

## Git & CI/CD Conventions

### Branching Strategy

- `main` — production-ready, protected
- `develop` — integration branch
- `feature/<ticket>-<description>` — feature branches
- `bugfix/<ticket>-<description>` — bug fix branches
- `hotfix/<ticket>-<description>` — urgent production fixes

### Commit Messages

Follow **Conventional Commits** format:

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`, `ci`, `perf`

Examples:
- `feat(patient): add gestational age validation`
- `fix(vitals): correct SpO2 alarm threshold calculation`
- `test(medication): add TPN dosing unit tests`
- `docs(milestone): update M1 deliverables`

### Pull Requests

- All PRs require at least one code review approval
- CI pipeline must pass (build, tests, 100% coverage, linting, security scan)
- PRs must reference the relevant issue or milestone
- Squash merge to maintain clean history

### CI Pipeline (GitHub Actions)

Every PR and push to `main`/`develop` must pass:

1. **Build** — `gradle clean build` (Java) + `ng build` (Angular)
2. **Unit Tests** — JUnit 5 (Java) + Jasmine/Karma (Angular)
3. **Code Coverage** — 100% enforced (JaCoCo + Istanbul)
4. **Linting** — Checkstyle (Java) + ESLint/Prettier (Angular)
5. **Security Scan** — OWASP Dependency Check + CodeQL
6. **Integration Tests** — Spring Boot Test + Testcontainers
7. **E2E Tests** — Cypress (on staging environment)

---

## Logging & Monitoring

- Use **SLF4J** with **Logback** for all Java logging
- Use structured logging (JSON format) for ELK Stack compatibility
- Log levels: `ERROR` (failures), `WARN` (degraded), `INFO` (business events), `DEBUG` (development)
- Include correlation IDs in all log entries for distributed tracing
- Use **Spring Boot Actuator** for health checks and metrics
- Export metrics to **Prometheus** via Micrometer
- **Never log PHI** — use patient identifiers only

---

## Performance Guidelines

- API response time (p95): < 500ms
- Page load time: < 2 seconds
- Vital signs latency: < 3 seconds (monitor to display)
- Use **Redis** caching for frequently accessed reference data
- Use **database read replicas** for reporting queries
- Use **WebSocket** (Spring WebSocket / STOMP) for real-time vital signs push
- Optimize Angular bundle size with tree-shaking and lazy loading

---

## Milestone Progression Policy

**You MUST complete all deliverables of the current milestone before starting work on a new milestone.**

### Rules

1. **Check milestone status first** — Before beginning any new work, open `MILESTONES.md` and identify the **lowest-numbered milestone that still has unchecked (`- [ ]`) deliverables**. That is the **current milestone**.
2. **Complete the current milestone** — All deliverables (including nested sub-items) in the current milestone must be marked `[x]` with passing tests and 100% code coverage before you may begin work on the next milestone.
3. **Do not skip ahead** — Never start deliverables from a later milestone while the current milestone has pending items. The only exception is work that was **already started and partially completed** in a prior session — finish that work as part of completing the current milestone.
4. **Respect milestone dependencies** — Refer to the **Milestone Timeline Overview** table in `MILESTONES.md` for dependency information. A milestone may only be started once all of its listed dependencies are fully complete.
5. **Milestone completion order** — Follow the milestone numbering order (M1 → M2 → M3 → …). When multiple milestones share the same dependency level and are independent of each other, complete them in numerical order.
6. **Verify before progressing** — Before moving to the next milestone, confirm:
   - Every `- [ ]` item in the current milestone is now `- [x]`
   - All code compiles and all tests pass
   - 100% code coverage is maintained (JaCoCo for Java, Istanbul for Angular)
   - The Change Log in `MILESTONES.md` has been updated

### How to Identify the Current Milestone

1. Open `MILESTONES.md`
2. Scan each milestone's deliverable list starting from M1
3. The first milestone with any `- [ ]` item is your **current milestone**
4. Focus all effort on completing that milestone's remaining deliverables

---

## Milestone Update Policy

**Whenever you complete a task or deliverable, you MUST update `MILESTONES.md` immediately.**

### Rules

1. **Mark the item complete** — Change `- [ ]` to `- [x]` for every deliverable you have fully implemented.
2. **Add a change log entry** — Append a new row to the **Change Log** table at the bottom of `MILESTONES.md`:
   - `Date` — use the current date in `YYYY-MM-DD` format
   - `Milestone(s)` — the milestone identifier(s) affected (e.g., `M2`, `M3, M5`)
   - `Change` — a short description of what was completed
   - `Reason` — which code was implemented (class names, entity names, API endpoints, etc.)
3. **Do not mark partial work complete** — Only mark a checklist item `[x]` when all code, tests, and coverage requirements for that item are fully met.
4. **Sub-items follow the same rule** — If a deliverable has nested checklist items (indented `- [ ]`), mark each sub-item as it is completed and mark the parent only when all children are done.

### Example

When you implement the `VitalSign` entity and REST API for heart rate monitoring:

```markdown
- [x] Implement heart rate monitoring (continuous, with variability analysis)
```

And add to the Change Log:

```markdown
| 2026-03-07 | M3 | Marked completed: heart rate monitoring | VitalSign entity with VitalSignType.HEART_RATE implemented |
```

---

## Feature Completion Checklist (Mandatory)

**Every feature implementation session MUST end with milestone updates. This is not optional.**

After completing any feature, bug fix, or deliverable — and before finishing your session or submitting a PR — you **MUST** execute every step in this checklist:

1. **Identify affected deliverables** — Determine which `MILESTONES.md` checklist items are fully satisfied by the code you implemented (including entity, service, controller, tests, and migration).
2. **Mark deliverables complete** — Change each satisfied `- [ ]` to `- [x]` in `MILESTONES.md`.
3. **Add change log entries** — For every item you mark complete, append a row to the **Change Log** table with the date, milestone ID, description, and implemented classes/endpoints.
4. **Verify no items are missed** — Review all milestone sections (not just the one you targeted) to ensure no other deliverable was incidentally completed by your changes.
5. **Commit milestone updates together with code** — The `MILESTONES.md` update must be part of the same PR as the feature code. Never leave milestone updates for a separate PR or future session.

### When to Run This Checklist

- ✅ After implementing a new entity, service, or API endpoint
- ✅ After completing a set of related features in a single session
- ✅ Before submitting or finalizing any pull request
- ✅ At the end of every coding session, even if work is partially complete (update only fully completed items)

### Consequences of Skipping

Failing to update milestones creates a gap between implemented code and project tracking. This leads to:
- Duplicate work when another contributor re-implements an already-completed feature
- Inaccurate project status reporting
- Incorrect milestone progression decisions

---

## Security Checklist (Every PR)

- [ ] Input validation on all API endpoints (`@Valid`, Jakarta Bean Validation)
- [ ] Authorization checks on all controller methods (`@PreAuthorize`)
- [ ] No PHI in log statements
- [ ] No secrets or credentials in code
- [ ] SQL injection prevention (parameterized queries via JPA)
- [ ] XSS prevention (Angular's built-in sanitization)
- [ ] CSRF protection enabled (Spring Security)
- [ ] CORS configured for allowed origins only
- [ ] Dependency vulnerabilities checked (OWASP Dependency Check)
