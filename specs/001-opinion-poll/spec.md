# Feature Specification: Opinion Poll Feature

**Feature Branch**: `feature/SCRUM-157-opinion-poll`  
**JIRA Epic**: `SCRUM-157`  
**Created**: 2025-01-30  
**Status**: Draft  
**Input**: Add an opinion poll feature on the GSO JUG website to enhance user engagement by allowing visitors to participate in polls, view results, and allow administrators to manage polls.

---

## User Scenarios & Testing *(mandatory)*

<!--
  User stories are prioritized as independently testable user journeys.
  Each story delivers standalone value if implemented alone.
-->

### User Story 1 - Visitor Views and Participates in an Active Poll (Priority: P1)

A website visitor navigates to the GSO JUG homepage (or a dedicated poll page/section) and sees an active opinion poll question with multiple answer options. The visitor selects one option and submits their vote. After voting, they immediately see the current poll results — a visual breakdown of how all voters have responded — so they can compare their opinion with the wider community.

**Why this priority**: This is the core user-facing value of the feature. Without this story there is no poll feature. Everything else is secondary to a visitor being able to see a question, cast a vote, and see results.

**Independent Test**: Can be fully tested by loading the homepage with a seeded active poll, selecting an option, submitting, and verifying the results page shows updated counts. Delivers the primary engagement value with no other story required.

**Acceptance Scenarios**:

1. **Given** an active poll exists with at least two options, **When** a visitor loads the poll page, **Then** the poll question and all available options are displayed clearly.
2. **Given** a visitor has not yet voted on the current poll, **When** they select an option and click "Vote", **Then** their vote is recorded and they are shown the current results for that poll.
3. **Given** a visitor submits a vote, **When** the results are displayed, **Then** each option shows the number of votes and a percentage of total votes, summing to 100%.
4. **Given** a visitor has already voted on the current poll (same browser session or tracked by cookie), **When** they revisit the poll, **Then** they see the results directly without being allowed to vote again.
5. **Given** no active poll exists at the moment, **When** a visitor loads the poll section, **Then** a friendly message such as "No active poll right now — check back soon!" is displayed.

---

### User Story 2 - Administrator Creates a New Poll (Priority: P2)

An administrator accesses a poll management area and creates a new opinion poll by entering a question and at least two answer options. The administrator can set the poll as active (visible to visitors) or save it as a draft. Only one poll can be active at a time. The administrator can also set an optional expiry date for the poll.

**Why this priority**: Without poll creation, there is nothing for visitors to vote on. This story enables the content lifecycle for the feature. It is P2 (not P1) because for an MVP a poll can be seeded directly in the database; however, self-service creation is critical for ongoing use.

**Independent Test**: Can be fully tested by navigating to the admin poll creation form, filling in a question and two options, saving, and verifying the poll appears in the management list and in the database.

**Acceptance Scenarios**:

1. **Given** an administrator is on the poll creation page, **When** they enter a question and at least two options and click "Save", **Then** a new poll is persisted and appears in the poll management list.
2. **Given** an administrator creates a poll and marks it as "Active", **When** a visitor loads the poll section, **Then** the newly created poll is displayed.
3. **Given** an active poll already exists, **When** an administrator activates a different poll, **Then** the previously active poll is automatically deactivated, ensuring only one poll is active at a time.
4. **Given** an administrator leaves the question field empty, **When** they try to save, **Then** a validation error message is shown and the poll is not saved.
5. **Given** an administrator provides fewer than two options, **When** they try to save, **Then** a validation error message is shown and the poll is not saved.
6. **Given** an administrator sets an expiry date on a poll, **When** the current date/time passes the expiry date, **Then** the poll is automatically deactivated and visitors see the "no active poll" message.

---

### User Story 3 - Administrator Views and Manages Existing Polls (Priority: P3)

An administrator can view a list of all polls (active, draft, expired, and closed). For each poll, the administrator can see the question, status, total vote count, and creation date. The administrator can edit a draft poll, close an active poll early, delete a poll (removing it and all associated votes), or reactivate a closed/expired poll.

**Why this priority**: Once polls exist, the administrator needs lifecycle management. This is P3 because it extends P2 (creation) with edit/delete/status-change capabilities that enable ongoing operations.

**Independent Test**: Can be tested by seeding two polls with different statuses, navigating to the admin management list, and verifying each poll's status, vote count, and available actions are displayed and functional.

**Acceptance Scenarios**:

1. **Given** multiple polls exist in various states, **When** an administrator opens the poll management page, **Then** all polls are listed with their question, status (Active / Draft / Closed / Expired), total vote count, and creation date.
2. **Given** an administrator clicks "Edit" on a Draft poll, **When** they change the question or options and save, **Then** the changes are persisted and reflected in the list.
3. **Given** an administrator clicks "Close" on an Active poll, **When** they confirm the action, **Then** the poll status changes to Closed and it is no longer displayed to visitors.
4. **Given** an administrator clicks "Delete" on any poll, **When** they confirm, **Then** the poll and all its associated vote records are permanently removed.
5. **Given** an administrator attempts to edit an Active or Closed poll (with existing votes), **When** they try to change the answer options, **Then** the system prevents option changes to preserve vote data integrity and displays an appropriate message.

---

### User Story 4 - Administrator Views Detailed Poll Results (Priority: P4)

An administrator can open a detailed results view for any poll, showing the total vote count per option, percentage breakdown, and a simple visual chart. The results page is accessible even for closed and expired polls, so historical engagement data is preserved.

**Why this priority**: Results visibility for administrators goes beyond the visitor-facing results — admins need a dedicated view with historical access. This is P4 as it adds analytical value on top of the core feature.

**Independent Test**: Can be tested by seeding a poll with known vote counts, navigating to the admin results page for that poll, and verifying each option shows the correct vote count and percentage.

**Acceptance Scenarios**:

1. **Given** a poll with votes recorded, **When** an administrator opens the results detail page, **Then** each option displays its vote count and percentage of total votes.
2. **Given** a poll with votes, **When** the results are displayed, **Then** a visual representation (e.g., bar chart or progress bars) shows the relative vote proportions.
3. **Given** a closed or expired poll, **When** an administrator views its results, **Then** the historical vote data is displayed correctly and is not affected by the poll's status.
4. **Given** a poll with zero votes, **When** the results page is displayed, **Then** each option shows 0 votes and 0%, and the total shows "No votes yet".

---

### User Story 5 - Visitor Is Prevented from Voting Multiple Times (Priority: P5)

The system prevents a single visitor from casting more than one vote on the same poll, while minimising friction for genuine first-time voters. Duplicate vote prevention is enforced via browser cookie / local storage so that casual duplicate attempts are blocked without requiring user accounts.

**Why this priority**: Vote integrity is important for meaningful results. It is P5 because lightweight cookie-based prevention covers the majority of accidental duplicate votes; the spec does not require authentication for visitors.

**Independent Test**: Can be tested by voting on a poll, then attempting to access the voting form again in the same browser — the form should be replaced by the results view. A different browser or cleared cookies restores voting ability (by design).

**Acceptance Scenarios**:

1. **Given** a visitor has already voted in the current browser session, **When** they return to the poll section, **Then** the voting form is not shown; instead, the results are displayed.
2. **Given** a visitor votes and a cookie/token is set, **When** the visitor refreshes the page, **Then** they still see the results, not the voting form.
3. **Given** the browser cookies are cleared, **When** the visitor returns to the poll, **Then** the voting form is shown again (this is acceptable and expected behaviour given the no-account approach).
4. **Given** a direct POST request is made to the vote endpoint for a poll the browser has already voted on, **When** the server checks the request, **Then** the duplicate vote is rejected and an appropriate message is returned.

---

### Edge Cases

- What happens when a visitor votes and the network request fails mid-submission? → The vote is not counted; the visitor sees an error message and the voting form remains available to retry.
- What happens if two visitors vote at exactly the same time? → Votes are recorded independently; no race condition should cause a vote to be lost (atomic increment or transactional insert).
- What happens if an administrator deletes a poll that is currently being viewed by a visitor? → The visitor sees a graceful "Poll no longer available" message.
- What happens when all options of a poll are deleted before the poll is activated? → The system requires at least two options before a poll can be activated.
- What happens if the expiry date is set in the past when creating a poll? → A validation error is shown and the poll is not saved.
- What happens when the total vote count reaches a very high number (e.g., 1,000,000+)? → Percentages are computed and displayed correctly without overflow or truncation errors.

---

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The system MUST display the current active poll (question and all options) in a visible section of the website accessible to all visitors.
- **FR-002**: The system MUST allow a visitor to select exactly one option and submit a vote for the active poll.
- **FR-003**: The system MUST persist each vote, associating it with the poll option and capturing the submission timestamp.
- **FR-004**: The system MUST display poll results (vote count per option and percentage of total) to a visitor immediately after they vote.
- **FR-005**: The system MUST prevent a visitor from voting more than once on the same poll within the same browser session using a cookie-based mechanism.
- **FR-006**: The system MUST provide an administrator interface for creating a new poll with a question text and two or more answer options.
- **FR-007**: The system MUST validate that a poll has a non-empty question and at least two options before saving.
- **FR-008**: The system MUST support poll statuses: Draft, Active, Closed, and Expired.
- **FR-009**: The system MUST enforce that only one poll is Active at any given time; activating a new poll deactivates the current one.
- **FR-010**: The system MUST allow administrators to set an optional expiry date on a poll; the poll automatically transitions to Expired when the date passes.
- **FR-011**: The system MUST allow administrators to list all polls with their status, vote count, and creation date.
- **FR-012**: The system MUST allow administrators to edit a poll that is in Draft status.
- **FR-013**: The system MUST prevent editing the answer options of a poll that has already received votes, to preserve data integrity.
- **FR-014**: The system MUST allow administrators to close an Active poll before its expiry date.
- **FR-015**: The system MUST allow administrators to delete any poll along with all its associated vote records.
- **FR-016**: The system MUST provide administrators a detailed results view per poll, displaying vote counts and percentages per option, and a visual representation of results.
- **FR-017**: The system MUST display a "no active poll" message to visitors when no poll is currently active.
- **FR-018**: The system MUST reject duplicate vote submissions from the server side, returning an appropriate error response.

### Key Entities

- **Poll**: Represents an opinion question posed to visitors. Attributes: unique identifier, question text, status (Draft / Active / Closed / Expired), creation date, optional expiry date.
- **PollOption**: Represents one answer choice within a poll. Attributes: unique identifier, reference to parent Poll, option text, display order.
- **Vote**: Represents a single visitor's response. Attributes: unique identifier, reference to PollOption (and indirectly Poll), submission timestamp.

---

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: A visitor can discover, read, vote on, and view results for an active poll in under 60 seconds from page load.
- **SC-002**: Poll results displayed to visitors are accurate — vote counts and percentages reflect the true state of the database at the time of display, with no more than a 5-second staleness window.
- **SC-003**: 95% or more of genuine first-time voters successfully complete a vote without encountering errors, when tested with at least 50 simulated users.
- **SC-004**: Duplicate vote prevention blocks 100% of same-browser repeated vote attempts without requiring any user account or login.
- **SC-005**: An administrator can create, activate, and confirm a new poll is live on the site in under 5 minutes.
- **SC-006**: The system correctly handles a poll with at least 10,000 votes, displaying accurate percentages rounded to one decimal place.
- **SC-007**: All poll data (questions, options, votes) is retained and queryable for historical reporting even after a poll is closed or expired.
- **SC-008**: The poll results page renders and is interactive within 3 seconds on a standard broadband connection.

---

## Assumptions

- The GSO JUG website uses the existing Spring Boot application infrastructure; no new server or deployment platform is required.
- The existing relational database (GSOJUG schema) will be extended with new tables for polls, options, and votes.
- No user authentication is required for visitors to participate in polls; vote deduplication is handled via browser cookies.
- Administrator access is controlled by the existing application security mechanism (assumed to exist or be introduced as a separate concern); this spec does not define the authentication method for admins.
- Only one poll is shown to visitors at a time (the active poll); multi-poll concurrent display is out of scope.
- Poll questions and options are plain text only; rich media (images, videos) in poll content is out of scope for this initial version.
- Email notifications or social sharing of poll results are out of scope for this version.
- The poll UI section will be embedded in the existing website layout/template system (Thymeleaf); a completely separate SPA or mobile app is out of scope.
- Poll results are publicly visible to all visitors after voting; there is no "private results" mode in this initial version.
- The optional expiry check runs on each page load or on a scheduled basis; near-real-time expiry (within 1 minute) is not required — a reasonable check interval (e.g., per-request or every few minutes) is acceptable.
