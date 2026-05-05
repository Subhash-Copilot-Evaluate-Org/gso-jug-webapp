# Feature Specification: Opinion Poll Feature

**Feature Branch**: `001-opinion-poll-feature`  
**Created**: 2025-07-14  
**Status**: Draft  
**JIRA Epic**: SCRUM-157  

---

## User Scenarios & Testing *(mandatory)*

### User Story 1 — View Active Poll on Homepage (Priority: P1)

As a website visitor, I want to see the currently active opinion poll displayed prominently on the homepage so that I am immediately aware of ongoing community polls and can choose to participate.

**Referenced JIRA Story**: SCRUM-176

**Why this priority**: This is the entry point for the entire poll experience. Without visibility of an active poll, no participation is possible. It is the foundation on which all other visitor-facing stories depend.

**Independent Test**: Can be fully tested by visiting the homepage while a poll is active and confirming the poll question and answer options are rendered — delivers the core discovery value even without voting.

**Acceptance Scenarios**:

1. **Given** an active poll exists, **When** a visitor loads the homepage, **Then** the poll question and all answer options are displayed in a dedicated poll section.
2. **Given** no active poll exists, **When** a visitor loads the homepage, **Then** the poll section is hidden or replaced with a "No active poll" placeholder message.
3. **Given** a poll whose end date has passed, **When** a visitor loads the homepage, **Then** the expired poll is not displayed as active.

---

### User Story 2 — Submit Vote on Active Poll (Priority: P1)

As a website visitor, I want to cast my vote on the active opinion poll so that my opinion is recorded and contributes to the community's collective response.

**Referenced JIRA Story**: SCRUM-178

**Why this priority**: Voting is the core action of the feature. Without this, the feature has no interactive value.

**Independent Test**: Can be fully tested by selecting an answer option and submitting — system records the vote and confirms submission to the user.

**Acceptance Scenarios**:

1. **Given** an active poll is displayed and the visitor has not voted, **When** the visitor selects an answer and clicks Submit, **Then** the vote is recorded and a confirmation message is shown.
2. **Given** the visitor has not selected any option, **When** the visitor clicks Submit, **Then** an inline validation message asks them to select an option before submitting.
3. **Given** an active poll is displayed, **When** the visitor submits a vote, **Then** the page does not perform a full reload (the experience is smooth and responsive).
4. **Given** the poll closes between page load and submission, **When** the visitor tries to submit, **Then** an appropriate message informs them the poll is no longer accepting votes.

---

### User Story 3 — View Real-Time Results After Voting (Priority: P1)

As a website visitor, I want to see poll results immediately after casting my vote so that I can understand how my opinion compares with the rest of the community.

**Referenced JIRA Story**: SCRUM-177

**Why this priority**: Seeing results is the reward that motivates participation. Without this, the user experience is incomplete and engagement is diminished.

**Independent Test**: Verified by submitting a vote and observing that a results view (percentage bars or counts per option) appears without page reload.

**Acceptance Scenarios**:

1. **Given** the visitor has just submitted a vote, **When** the vote is confirmed, **Then** the poll section transitions to display current results with percentage share for each option.
2. **Given** the visitor revisits the homepage after having already voted, **When** the page loads, **Then** the results view is shown directly instead of the voting form.
3. **Given** multiple visitors have voted, **When** any visitor views results, **Then** the totals and percentages reflect all recorded votes accurately.

---

### User Story 4 — Prevent Duplicate Voting (Priority: P1)

As a website visitor, I am prevented from casting more than one vote on the same poll so that poll integrity is maintained and results are not skewed.

**Referenced JIRA Story**: SCRUM-179

**Why this priority**: Duplicate vote prevention is a critical integrity requirement. Without it, all poll data is unreliable.

**Independent Test**: Verified by voting on a poll, then returning and attempting to vote again — the vote form is replaced by the results view and a second submission is rejected.

**Acceptance Scenarios**:

1. **Given** a visitor has already voted on the active poll, **When** they return to the homepage, **Then** the voting form is not shown; only the results are displayed.
2. **Given** a visitor attempts to submit a vote programmatically a second time for the same poll, **When** the submission is processed, **Then** the system rejects it with a clear "already voted" response and does not alter the vote count.
3. **Given** duplicate-vote detection is cookie/session based, **When** a visitor clears their browser cookies, **Then** the system uses any available secondary signal (e.g., IP + poll ID combination) to limit repeated votes, documenting the trade-off in a visible way.

---

### User Story 5 — Browse Historical Poll Results (Priority: P2)

As a website visitor, I want to browse past poll results on a dedicated archive page so that I can explore the history of community opinions over time.

**Referenced JIRA Story**: SCRUM-187

**Why this priority**: Historical results add long-term value and community transparency but are not required for the initial launch.

**Independent Test**: Verified by navigating to the poll archive page and confirming a list of closed polls with their final results is displayed.

**Acceptance Scenarios**:

1. **Given** at least one closed poll exists, **When** a visitor navigates to the poll history page, **Then** a list of past polls is shown, each with its question, answer options, and final vote counts.
2. **Given** many historical polls exist, **When** the visitor browses the archive, **Then** results are paginated (maximum 10 per page) so the page remains usable.
3. **Given** a poll has been closed, **When** it appears in the archive, **Then** it is clearly marked as "Closed" and its final vote distribution is visible.

---

### User Story 6 — Admin: Create a New Poll (Priority: P1)

As an admin, I want to create a new opinion poll with a question and multiple answer options so that I can collect community input on relevant topics.

**Referenced JIRA Story**: SCRUM-181

**Why this priority**: Poll creation is the supply side of the feature. Without admins being able to create polls, there is no content for visitors to engage with.

**Independent Test**: Verified by logging in as an admin, completing the poll creation form, and confirming the new poll appears in the admin poll list with DRAFT status.

**Acceptance Scenarios**:

1. **Given** an admin is logged in, **When** they complete the poll creation form with a question and at least 2 answer options and save, **Then** the poll is created with DRAFT status and appears in the admin poll management list.
2. **Given** an admin submits the form with fewer than 2 answer options, **When** the form is submitted, **Then** a validation error is shown and the poll is not saved.
3. **Given** an admin submits the form with a blank question, **When** the form is submitted, **Then** a validation error is shown requiring a non-empty question.
4. **Given** an admin creates a poll with an optional start and end date, **When** the poll is saved, **Then** those dates are stored and will control automatic activation and deactivation.

---

### User Story 7 — Admin: Edit a Draft Poll (Priority: P2)

As an admin, I want to edit a poll that is in DRAFT status so that I can refine the question and options before making it live.

**Referenced JIRA Story**: SCRUM-188

**Why this priority**: Editing draft polls is essential for workflow quality but not for the initial MVP if polls can be deleted and recreated.

**Independent Test**: Verified by editing a draft poll's question and saving, then confirming the updated question appears in the admin list.

**Acceptance Scenarios**:

1. **Given** a poll is in DRAFT status, **When** an admin edits the question or options and saves, **Then** the changes are persisted and reflected immediately.
2. **Given** a poll is ACTIVE or CLOSED, **When** an admin attempts to edit it, **Then** the edit form is not available and a message explains why editing is locked.
3. **Given** an admin removes all but one answer option while editing, **When** they try to save, **Then** a validation error prevents the save.

---

### User Story 8 — Admin: Manage Poll Status (Priority: P1)

As an admin, I want to activate, deactivate, or close polls from the admin dashboard so that I have direct control over which poll is currently visible to visitors.

**Referenced JIRA Story**: SCRUM-180

**Why this priority**: Without status management, admins cannot make polls live or retire them — core operational requirement.

**Independent Test**: Verified by activating a DRAFT poll and confirming it appears on the homepage, then closing it and confirming it disappears from the homepage.

**Acceptance Scenarios**:

1. **Given** a DRAFT poll exists, **When** an admin activates it, **Then** the poll status becomes ACTIVE and it becomes visible on the homepage.
2. **Given** an ACTIVE poll exists, **When** an admin activates a different DRAFT poll, **Then** the previously active poll is automatically deactivated, and only one poll is active at a time.
3. **Given** an ACTIVE poll, **When** an admin closes it, **Then** the poll status becomes CLOSED, it is removed from the homepage, and it moves to the historical results archive.
4. **Given** an ACTIVE poll, **When** an admin deactivates it, **Then** the poll returns to DRAFT and is no longer visible on the homepage (votes are retained).

---

### User Story 9 — Admin: Set Poll Schedule (Priority: P2)

As an admin, I want to set a start and end date for a poll so that the poll activates and deactivates automatically without requiring manual intervention.

**Referenced JIRA Story**: SCRUM-182

**Why this priority**: Scheduling is a convenience feature for admins. Manual activation is an acceptable alternative for MVP.

**Independent Test**: Verified by setting a start date in the near future, waiting for that time, and confirming the poll becomes visible on the homepage automatically.

**Acceptance Scenarios**:

1. **Given** a poll with a future start date, **When** the system clock reaches the start date, **Then** the poll status transitions to ACTIVE automatically.
2. **Given** a poll with a past end date, **When** the system clock passes the end date, **Then** the poll status transitions to CLOSED automatically.
3. **Given** a scheduled poll, **When** an admin manually closes it before the end date, **Then** the manual action takes precedence over the schedule.
4. **Given** start date is after end date, **When** the admin tries to save, **Then** a validation error prevents saving.

---

### User Story 10 — Admin: View Analytics and Export Results (Priority: P2)

As an admin, I want to view detailed poll analytics and export results as a CSV so that I can report on community engagement and share results with stakeholders.

**Referenced JIRA Stories**: SCRUM-183, SCRUM-175

**Why this priority**: Analytics and export are valuable for stakeholders but are not required for the feature to function.

**Independent Test**: Verified by opening an active or closed poll in the admin dashboard and confirming vote totals, percentages per option, and a working CSV export button.

**Acceptance Scenarios**:

1. **Given** a poll with recorded votes, **When** an admin views its analytics page, **Then** they see total vote count, vote count per option, and percentage share per option.
2. **Given** an admin clicks "Export CSV", **When** the export is generated, **Then** a downloadable CSV file is produced containing poll question, each option, its vote count, and percentage.
3. **Given** a poll with zero votes, **When** an admin views its analytics, **Then** vote counts show zero and percentages show 0% without errors.

---

### User Story 11 — Protect Admin Endpoints (Priority: P1)

As a developer, I need all admin poll management endpoints protected by authentication and authorization so that only authorised administrators can create, modify, or close polls.

**Referenced JIRA Story**: SCRUM-170, SCRUM-184

**Why this priority**: Security is non-negotiable; unprotected admin endpoints would expose critical functionality to the public.

**Independent Test**: Verified by attempting to access admin poll endpoints without credentials and confirming a 401/403 response is returned.

**Acceptance Scenarios**:

1. **Given** an unauthenticated user, **When** they attempt to access any admin poll management URL, **Then** they receive an authentication challenge (redirect to login or 401/403 response).
2. **Given** a logged-in non-admin user, **When** they attempt to access admin poll endpoints, **Then** access is denied with a 403 response.
3. **Given** all poll submission endpoints, **When** a request is made without a valid CSRF token, **Then** the request is rejected with a 403 response.
4. **Given** poll answer option inputs, **When** a user submits malicious input (e.g., script injection), **Then** the input is sanitised and not stored or rendered as executable code.

---

### User Story 12 — Persist Poll Data (Priority: P1)

As a developer, I need the poll data model and persistence layer implemented so that polls, options, and votes survive server restarts and are stored reliably.

**Referenced JIRA Stories**: SCRUM-185, SCRUM-171, SCRUM-173

**Why this priority**: Without persistence, no other story can function in a meaningful way. This is the foundational data layer.

**Independent Test**: Verified by creating a poll, casting votes, restarting the application, and confirming both the poll and vote data are intact.

**Acceptance Scenarios**:

1. **Given** a poll is created and votes are cast, **When** the application is restarted, **Then** the poll, its options, and all votes are intact and correctly reflected on the homepage and admin dashboard.
2. **Given** a vote is submitted, **When** it is processed, **Then** the vote is associated with the correct poll option and stored with a timestamp.
3. **Given** the database schema, **When** it is applied, **Then** all required tables (polls, options, votes) exist with appropriate constraints (e.g., unique vote per visitor per poll).

---

### User Story 13 — Maintain Poll Audit History (Priority: P3)

As a developer, I need poll status changes to be recorded in an audit log so that administrators can trace who changed what and when for compliance and debugging.

**Referenced JIRA Story**: SCRUM-174

**Why this priority**: Audit history is important for governance but not required for initial feature operation.

**Independent Test**: Verified by activating and then closing a poll as an admin and confirming an audit trail entry exists for each status change with timestamp and actor.

**Acceptance Scenarios**:

1. **Given** an admin changes a poll's status, **When** the change is saved, **Then** an audit record is created capturing the previous status, new status, timestamp, and admin identity.
2. **Given** a poll is activated automatically by schedule, **When** the status changes, **Then** an audit record is created attributing the change to the scheduler.
3. **Given** an admin views a poll's audit history, **When** they open the audit log, **Then** all status transitions are listed in chronological order.

---

### User Story 14 — Ensure Poll Feature Performs Under Load (Priority: P2)

As a developer, I need the poll feature to perform reliably under the website's expected traffic so that visitor experience is not degraded during high-traffic events.

**Referenced JIRA Story**: SCRUM-186, SCRUM-172

**Why this priority**: Performance issues would affect all visitors simultaneously and could undermine trust in the website.

**Independent Test**: Verified by running a load simulation with a representative number of concurrent visitors and measuring poll page response times.

**Acceptance Scenarios**:

1. **Given** 200 concurrent visitors viewing the homepage poll, **When** they load the page simultaneously, **Then** poll content is delivered to all visitors within acceptable response times.
2. **Given** poll results are requested frequently, **When** results are served, **Then** the system uses caching to avoid redundant database reads on each request.
3. **Given** poll vote submission occurs concurrently, **When** simultaneous votes are submitted, **Then** all votes are recorded accurately without data loss or corruption.

---

### User Story 15 — Write Tests for Poll Feature (Priority: P2)

As a developer, I need unit and integration tests for the poll feature so that regressions are caught early and code quality is maintained over time.

**Referenced JIRA Story**: SCRUM-189

**Why this priority**: Testing is essential for maintainability but does not affect initial delivery to end users.

**Independent Test**: Verified by running the test suite and confirming all poll-related tests pass with meaningful coverage of core paths.

**Acceptance Scenarios**:

1. **Given** the poll service layer, **When** unit tests are run, **Then** all core business rules (vote submission, status transitions, duplicate prevention) are covered by passing tests.
2. **Given** the poll REST endpoints, **When** integration tests are run, **Then** each endpoint's happy path and key error paths are tested and passing.
3. **Given** the test suite, **When** any test fails, **Then** the failure message clearly identifies which behaviour is broken.

---

### Edge Cases

- What happens when a visitor submits a vote and the network drops mid-request?
- How does the system behave if two admins attempt to activate different polls simultaneously?
- What if a scheduled poll's activation time passes while the server is down for maintenance?
- How are vote totals displayed when a poll has zero votes (avoid division-by-zero on percentages)?
- What if an admin deletes a poll that already has votes — are votes also removed?
- How does the system handle very long poll questions or answer option text in the UI layout?
- What happens if the CSV export is triggered for a poll with thousands of votes — is there a timeout risk?

---

## Requirements *(mandatory)*

### Functional Requirements

**Visitor-Facing**

- **FR-001**: The system MUST display at most one active poll on the homepage at any given time.
- **FR-002**: The system MUST show the poll question and all associated answer options to unauthenticated visitors.
- **FR-003**: The system MUST allow a visitor to select exactly one answer option and submit their vote.
- **FR-004**: The system MUST display current vote results (counts and percentages per option) immediately after a successful vote submission.
- **FR-005**: The system MUST prevent a visitor from voting more than once on the same poll using a combination of browser-side state and server-side validation.
- **FR-006**: The system MUST show results instead of the voting form to a visitor who has already voted on the active poll.
- **FR-007**: The system MUST provide a dedicated page listing all closed polls with their final results.
- **FR-008**: Historical poll results MUST be paginated (no more than 10 per page).

**Admin-Facing**

- **FR-009**: The system MUST provide an admin interface for creating polls with a question (required) and a minimum of 2 answer options (required).
- **FR-010**: The system MUST support optional scheduling of polls with a start date and end date.
- **FR-011**: The system MUST allow an admin to manually transition a poll between DRAFT, ACTIVE, and CLOSED states.
- **FR-012**: When a new poll is activated, the system MUST automatically deactivate any currently ACTIVE poll.
- **FR-013**: The system MUST allow editing of polls in DRAFT status only; ACTIVE and CLOSED polls MUST be locked from editing.
- **FR-014**: The system MUST display poll analytics per poll: total votes, votes per option, and percentage per option.
- **FR-015**: The system MUST allow an admin to export poll results as a downloadable CSV file.

**Security & Integrity**

- **FR-016**: All admin poll management endpoints MUST require authentication and administrator-level authorisation.
- **FR-017**: All vote submission endpoints MUST include CSRF protection.
- **FR-018**: All user-supplied input (poll question, answer options) MUST be validated and sanitised before storage or display.

**Data & Infrastructure**

- **FR-019**: Poll data (polls, options, votes) MUST be persisted across application restarts.
- **FR-020**: All poll status transitions MUST be recorded in an audit log capturing the actor, previous state, new state, and timestamp.
- **FR-021**: The system MUST apply caching to poll results reads to reduce repeated database queries under load.

### Key Entities

- **Poll**: Represents a single opinion poll. Key attributes: unique identifier, question text, status (DRAFT / ACTIVE / CLOSED), optional start date, optional end date, created-by, created-at.
- **PollOption**: Represents one answer choice within a poll. Key attributes: unique identifier, reference to parent Poll, option text, display order.
- **Vote**: Represents a single cast vote. Key attributes: unique identifier, reference to Poll, reference to PollOption, voter token (anonymised), submitted-at timestamp.
- **PollAuditEntry**: Represents a recorded status change. Key attributes: unique identifier, reference to Poll, previous status, new status, changed-by (admin or scheduler), changed-at timestamp.

---

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Visitors can discover an active poll, cast their vote, and view results within a single homepage visit in under 60 seconds from start to finish.
- **SC-002**: Poll results displayed to visitors accurately reflect all recorded votes with no more than a 5-second delay from vote submission to result visibility.
- **SC-003**: The system prevents duplicate votes for at least 95% of standard re-vote attempts from the same browser session.
- **SC-004**: Admins can create, schedule, and activate a new poll within 5 minutes using the admin interface without external assistance.
- **SC-005**: The poll section on the homepage remains available and functional during peak traffic representing at least 200 concurrent visitors.
- **SC-006**: CSV export of poll results completes and downloads within 10 seconds for polls with up to 10,000 votes.
- **SC-007**: 100% of admin endpoint access attempts by unauthenticated or unauthorised users are rejected without exposing poll management functionality.
- **SC-008**: All audit log entries for poll status changes are present and accurate, with no missing entries for manual or automatic transitions.

---

## Assumptions

- The website already has an authentication system that supports an admin role; this feature reuses it without modification.
- Visitor vote deduplication in v1 is cookie and server-session based. IP-based secondary signals may be applied as a best-effort supplement but are not relied upon as the sole mechanism.
- Only one poll will be active at a time; the system enforces this constraint automatically when an admin activates a new poll.
- Poll answer options are limited to plain text (no images or rich media) in the initial version.
- The website operates primarily in English; internationalisation is out of scope for this feature.
- Performance targets (200 concurrent users) are based on the current site traffic profile; higher targets would require separate capacity planning.
- Mobile browsers are fully supported; native mobile app support is out of scope.
- The admin dashboard for poll management is accessible via the existing website admin area, not as a separate application.
- Deleted polls are considered out of scope for v1; admins close polls rather than delete them to preserve historical data.
- Email or push notifications for new polls are out of scope for this feature.
