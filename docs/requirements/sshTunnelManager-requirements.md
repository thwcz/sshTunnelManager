---
title: SSH Tunnel Manager Solution Specification
version: 1.0.0
date: 2026-03-25
status: Ready
consumers: [TDDDeveloper, TechnicalDesigner]
tags: [desktop, java, ssh, tunnel, gui, security]
---

# SSH Tunnel Manager Solution Specification

**Generated:** 2026-03-25 | **Tokens:** [est]

---

## PART A: SHARED CONTEXT (Both Agents)

### 1. REQUIREMENT SUMMARY

- **Type:** Feature (Full Application)
- **Priority:** High
- **Complexity:** Complex
- **Source:** sshTunnelManager.md

**Functional Requirements:**

- Java desktop application for managing SSH (single/double) tunnels
- Enforce single instance (error on second start)
- Tray icon with menu (show, about, exit)
- Custom tunnel-themed icon for window, taskbar, tray
- Model-View-Controller architecture
- DAO/DTO/BO for data
- Data stored in "conf" folder (JSON files)
- Unique name fields, valid FQDN/IP for addresses, port validation
- Mapped port uniqueness and range logic
- GUI forms for all editing screens
- Main screen: folder/connection tree, active connections, favorites, connection indicators
- Add/Edit/Delete for folders, connections, datacenters, credentials
- Master password setup and validation, credential encryption
- Password complexity and private key validation
- SSH key format conversion (Putty→OpenSSH)
- Connection monitoring, reconnect logic, alive test
- Logging (best practice)
- Gradle build, VS Code integration, run configs
- Unit tests and JavaDoc

**Non-Functional Requirements:**

- Security: encrypted credentials, password complexity, no PII in logs
- Usability: graphical forms, error highlighting, dropdowns, tooltips
- Performance: no auto-build, responsive UI, efficient connection monitoring
- Reliability: retry logic, error handling, logging

**Acceptance Criteria:**

- [ ] Only one instance runs at a time
- [ ] All data validations enforced (names, addresses, ports, mappedPort)
- [ ] Tray icon/menu works as specified
- [ ] All forms are graphical, not plain editors
- [ ] Master password and credential flows work
- [ ] SSH connections managed, monitored, and reconnected as required
- [ ] Logging and error messages are meaningful
- [ ] Gradle build and VS Code run configs present
- [ ] Unit tests cover core logic

**Business Rules:**

- Name fields must be unique
- Address fields must match FQDN/IP regex
- Port fields: numeric, 1025-65534
- mappedPort: unique, range by host type, dropdown with available ports
- Password complexity enforced
- No retry on auth-reject
- Putty keys auto-converted
- Max 3 password retries on startup

### 2. CURRENT STATE ANALYSIS

**Current Implementation:**
| Aspect | Current State |
|--------|---------------|
| Class | Not implemented |
| Methods | N/A |
| Behavior | N/A |
| Patterns | N/A |
| Dependencies | N/A |

**Current Tests:**
| Aspect | Current State |
|--------|---------------|
| Test Class | N/A |
| Test Count | 0 |
| Coverage | None |
| Patterns | N/A |
| Helpers | N/A |

**Code Conventions Observed:**

- Naming: To be defined (follow Java best practices)
- Validation: To use annotations and manual checks
- Error handling: Popups, field highlighting, logs
- Logging: Standard Java logging or Log4j

### 3. GAP ANALYSIS

**Implementation Gaps:**
| Area | Current | Required | Action |
|------|---------|----------|--------|
| Methods | None | All core features | ADD |
| Validation | None | All rules above | ADD |
| Error Handling | None | All flows | ADD |
| Integration | None | Gradle, VS Code | ADD |

**Testing Gaps:**
| Category | Current Coverage | Required | Gap |
|----------|------------------|----------|-----|
| Happy Path | None | All flows | NEW |
| Edge Cases | None | All validations | NEW |
| Error Scenarios | None | All errors | NEW |
| Enum Coverage | N/A | All dropdowns | NEW |
| Performance | None | Connection monitor | NEW |
| Concurrency | None | Single instance | NEW |

**Risk Assessment:**

- Breaking changes: N/A (new app)
- Backward compatibility: N/A
- Regression risk: N/A

### 4. AFFECTED MODULES

- **Primary:** All (full application)
- **Integration Points:** SSH library, file system, OS tray, Gradle, VS Code

**Files:**
| Type | Path | Action |
|------|------|--------|
| Production | src/main/java/... | ADD |
| Tests | src/test/java/... | ADD |
| DTOs | conf/*.json | ADD |
| Mappers | src/main/java/... | ADD |

### 5. DATA MODEL

**Entities/DTOs:**
| Field | Type | Validation | Description | Status |
|-------|------|------------|-------------|--------|
| name | String | unique, pattern | Folder, datacenter, connection | NEW |
| address | String | regex | Hostname/FQDN/IP | NEW |
| port | int | 1025-65534 | SSH port | NEW |
| mappedPort | int | unique, range | Tunnel port | NEW |
| user | String | required | SSH user | NEW |
| password | String | encrypted | SSH password | NEW |
| keyfile | String | file path | Private key | NEW |
| privkeyPassphrase | String | encrypted | Key passphrase | NEW |
| lastModified | Date | required | Credentials | NEW |
| folders | List | required | Tunnel folders | NEW |
| connections | List | required | Tunnel connections | NEW |

**Enums:**

- ConnectionType: name, port, isDoubleTunnel

**Constraints:**

- @NotNull: all required fields
- @Pattern: name, address
- @Min/@Max: port, mappedPort
- Custom: mappedPort uniqueness, password complexity

### 6. DEPENDENCIES

| Type     | Name      | Purpose       | Mock/Stub Strategy   | Status       |
| -------- | --------- | ------------- | -------------------- | ------------ |
| Injected | SSH lib   | SSH handling  | Mock for tests       | NEW |
| Data     | JSON files| Persistence   | Test data files      | NEW |
| External | OS Tray   | Tray icon     | Mock if possible     | NEW |

---

## PART B: FOR @TechnicalDesigner

### 7. DESIGN CONTEXT

**Architecture Considerations:**

- MVC pattern, DAO/DTO/BO separation
- Tray integration, single instance enforcement
- Secure credential storage, encryption
- Responsive, user-friendly GUI
- Gradle build, VS Code run configs

**Diagram Requirements:**

- Sequence: Startup → Master password → Credentials → Main app
- Workflow: Connection creation/edit, tunnel monitoring

**API Design (if applicable):**
| Endpoint | Method | Description | Auth | Status |
|----------|--------|-------------|------|--------|
| N/A | N/A | N/A | N/A | N/A |

**Error Handling:**

- Validation errors: popup + field highlight
- Auth failures: no retry, error shown
- File errors: error popup
- SSH key conversion: info popup

---

## PART C: FOR @TDDDeveloper

### 8. TESTING SCOPE

**Existing Tests to Keep:**

- N/A (new app)

**Existing Tests to Modify:**

- N/A

**New Tests Required:**
| Category | Test Method | Scenario | Priority |
|----------|-------------|----------|----------|
| Happy Path | testSingleInstance | Only one instance runs | HIGH |
| Happy Path | testTrayMenu | Tray menu actions | HIGH |
| Edge Case | testInvalidPort | Port out of range | HIGH |
| Edge Case | testDuplicateName | Duplicate name error | HIGH |
| Error | testInvalidAddress | Invalid address regex | HIGH |
| Error | testPasswordComplexity | Weak password rejected | HIGH |
| Enum | testConnectionTypeDropdown | All types selectable | HIGH |
| Performance | testConnectionMonitor | Monitor/reconnect logic | MEDIUM |
| Concurrency | testSingleInstanceLock | Race on startup | MEDIUM |

**Test Patterns to Follow**

- Use Mockito for SSH lib, OS tray, file IO
- AssertJ/JUnit for assertions
- Test data: JSON test files

### 9. SOLUTION IMPLEMENTATION

**Production Code Changes:**
| Action | Target | Details |
|--------|--------|---------|
| ADD | MainApp | All core logic |
| ADD | TrayManager | Tray icon/menu |
| ADD | CredentialManager | Master password, encryption |
| ADD | SSHManager | SSH connect/monitor |
| ADD | DataManager | JSON file IO, validation |
| ADD | UI Forms | All screens |
| ADD | Logging | Logging setup |
| ADD | Gradle build | Build script, run config |

**Test Code Changes:**
| Action | Target | Details |
|--------|--------|---------|
| ADD | MainAppTest | Single instance, startup |
| ADD | DataManagerTest | Validation, file IO |
| ADD | SSHManagerTest | SSH connect, monitor |
| ADD | CredentialManagerTest | Password, encryption |
| ADD | TrayManagerTest | Tray menu |

### 10. TDD CHUNKS (Sequenced)

| #   | Scope         | Prod Changes | Test Changes            | Effort |
| --- | ------------- | ------------ | ----------------------- | ------ |
| 1   | Single instance, tray | MainApp, TrayManager | MainAppTest, TrayManagerTest | 1-3h |
| 2   | Credential flow | CredentialManager | CredentialManagerTest | 1-3h |
| 3   | Data model, validation | DataManager | DataManagerTest | 1-3h |
| 4   | SSH connect/monitor | SSHManager | SSHManagerTest | 1-3h |
| 5   | UI forms | UI Forms | UI tests | 1-3h |
| 6   | Gradle/build | Build scripts | Build/run tests | 1-3h |

**TDD Cycle:** RED (write/update tests) → GREEN (implement/modify code) → REFACTOR

### 11. COVERAGE & COMMANDS

**Targets:**

- Instruction Coverage: 100%
- Branch Coverage: 95%+
- New code: 100% covered
- Modified code: Maintain or improve coverage

**Commands:**

- Build: `gradle build`
- Test: `gradle test`
- Coverage: `gradle jacocoTestReport`
- Verify: Check `build/reports/tests/test/index.html`

---

## PART D: ACCEPTANCE & VALIDATION

### 12. ACCEPTANCE CRITERIA

**Functional:**

- [ ] All test scenarios pass (happy, edge, error, enum, perf, concurrency)
- [ ] Business rules enforced with correct exceptions
- [ ] UI matches specification
- [ ] Existing tests still pass (no regressions)

**Quality:**

- [ ] Coverage: 100% instruction, 95%+ branch
- [ ] Build SUCCESS, no unused/wildcard imports
- [ ] Security: input validation, parameterized queries, no PII in logs
- [ ] Follows existing code patterns and conventions

**Design (for @TechnicalDesigner):**

- [ ] Sequence diagram documents key interactions
- [ ] Workflow diagram shows process flow

### 13. EXECUTION NOTES

**For @TDDDeveloper:**

- Pre-check: Read CURRENT STATE ANALYSIS section first
- Follow existing patterns observed in current code
- Modify tests before adding new ones (maintain consistency)
- TDD: Chunk → RED → GREEN → REFACTOR → coverage check → next
- Verify existing tests still pass after changes
- Recovery: build fail → fix imports; test fail → fix assertion; gap → add tests
- **Post-implementation:** Invoke `@RequirementsAnalyst Validate implementation for sshTunnelManager`

**For @TechnicalDesigner:**

- Pre-check: Load modules 00, 02, 05, workspace-profile.md
- Review CURRENT STATE ANALYSIS for existing architecture
- Document changes to existing flows, not just new flows
- Use PlantUML for sequence diagrams, Mermaid for workflows
- Reference copilot-instructions.md from explanations workspace for diagram standards

**For Post-Implementation Validation:**

- Trigger: After development complete and tests passing
- Purpose: Verify specification was implemented correctly
- Output: Validation report appended to this specification
- Action if FAIL: Create follow-up specification for remaining work
