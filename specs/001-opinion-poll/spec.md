# Feature Specification: Opinion Poll

**Feature Branch**: `001-opinion-poll`  
**Created**: 2025-07-15  
**Status**: Draft  
**JIRA Epic**: SCRUM-196  

## User Scenarios & Testing *(mandatory)*

### User Story 1 - View Active Poll on Website (Priority: P1)

As a website visitor, I want to see an active opinion poll on the GSO JUG website so that I can engage with the community by sharing my opinion on a topic relevant to the Java/JUG community.

**Why this priority**: This is the foundational user-facing experience. Without a visible poll, no other functionality has value. It delivers immediate engagement value to visitors and forms the basis for all subsequent stories.

**Independent Test**: Can be fully tested by navigating to the website homepage (or a dedicated poll page) and verifying a poll with a question and answer options is displayed — this delivers visitor engagement value on its own.

**Acceptance Scenarios**:

1. **Given** an active poll exists, **When** a visitor loads the poll page, **Then** the poll question and all available answer options are displayed clearly.
2. **Given** an active poll exists, **When** a visitor has already voted, **Then** the results view is displayed instead of the voting form.
3. **Given** no active poll exists, **When** a visitor navigates to the poll page, **Then** a friendly message is displayed indicating no poll is currently available.
4. **Given** a poll has expired, **When** a visitor views the page, **Then** the poll is shown in read-only results mode and voting is disabled.

---

### User Story 2 - Cast a Vote on a Poll (Priority: P2)

As a website visitor, I want to select one answer option and submit my vote so that my opinion is recorded and contributes to the overall poll results.

**Why this priority**: Voting is the core interaction of the poll feature. Without this, the feature provides no engagement mechanism. It depends on Story 1 (poll display) being in place.

**Independent Test**: Can be tested by selecting an answer option on an active poll, submitting the vote, and verifying the system records the vote and presents the updated results.

**Acceptance Scenarios**:

1. **Given** an active poll is displayed, **When** a visitor selects one option and submits, **Then** the vote is recorded and the poll results are shown.
2. **Given** a visitor has already voted, **When** they attempt to submit another vote, **Then** the system rejects the duplicate vote and informs the visitor they have already voted.
3. **Given** an active poll, **When** a visitor submits without selecting an option, **Then** the system shows a validation message prompting them to select an option before submitting.
4. **Given** an active poll, **When** the vote is submitted successfully, **Then** the results page shows the updated vote counts and percentages including the new vote.

---

### User Story 3 - View Poll Results (Priority: P3)

As a website visitor, I want to view the current poll results so that I can see how the community has voted and understand the overall sentiment.

**Why this priority**: Results display closes the engagement loop for voters. It allows visitors to see community opinion even after voting, reinforcing engagement. Depends on Story 2.

**Independent Test**: Can be tested by viewing a poll with at least one submitted vote and verifying that vote counts and percentage breakdowns are correctly displayed for each answer option.

**Acceptance Scenarios**:

1. **Given** a poll has received votes, **When** a visitor views the results, **Then** each answer option is displayed with its vote count and percentage of total votes.
2. **Given** a poll has received votes, **When** results are displayed, **Then** a total vote count is shown.
3. **Given** a poll has zero votes, **When** results are displayed (e.g., after poll closes with no participation), **Then** zero counts are shown gracefully without division errors.
4. **Given** results are displayed, **When** a visitor inspects the page, **Then** results are shown using a visual indicator (e.g., progress bar or chart) for readability.

---

### User Story 4 - Admin Creates a Poll (Priority: P4)

As an administrator, I want to create a new opinion poll with a question and multiple answer options so that I can gather community feedback on topics relevant to the GSO JUG membership.

**Why this priority**: Poll creation is required for any poll content to exist on the site. It is prioritized after display/voting because the initial poll data can be seeded for MVP; admin creation is needed for ongoing management.

**Independent Test**: Can be tested by logging in as an admin, creating a poll with a question and at least 2 answer options, saving it, and verifying it appears as available in the system.

**Acceptance Scenarios**:

1. **Given** an authenticated admin, **When** they submit a new poll with a question and at least 2 answer options, **Then** the poll is saved and becomes available for activation.
2. **Given** an admin is creating a poll, **When** they submit with fewer than 2 answer options, **Then** the system rejects the submission with a validation message.
3. **Given** an admin is creating a poll, **When** they submit with a blank question, **Then** the system rejects the submission with a validation message.
4. **Given** an admin creates a poll, **When** they set an end date, **Then** voting is automatically disabled after that date/time passes.

---

### User Story 5 - Admin Manages Polls (Priority: P5)

As an administrator, I want to activate, deactivate, and delete polls so that I can control which poll is currently visible to website visitors and manage poll content over time.

**Why this priority**: Poll lifecycle management ensures only appropriate and timely polls are surfaced to visitors. Depends on Story 4 (poll creation).

**Independent Test**: Can be tested by activating a previously created poll and verifying it appears on the public-facing poll page, then deactivating it and verifying it disappears.

**Acceptance Scenarios**:

1. **Given** an admin views the poll management list, **When** they activate a poll, **Then** that poll becomes the active poll shown to visitors and any previously active poll is deactivated.
2. **Given** an admin views the poll management list, **When** they deactivate an active poll, **Then** visitors see a "no current poll" message.
3. **Given** an admin views the poll management list, **When** they delete a poll with no votes, **Then** the poll is removed from the system.
4. **Given** an admin attempts to delete a poll that has received votes, **Then** the system warns the admin that deleting will remove all associated vote data and requires confirmation.
5. **Given** an admin views the poll list, **When** the page loads, **Then** all polls are shown with their status (active, inactive, expired), vote count, and creation date.

---

### Edge Cases

- What happens when two admin users attempt to activate different polls simultaneously?
- How does the system handle a visitor voting just as a poll expires?
- What if a visitor clears cookies or uses a different browser after voting — is a second vote possible?
- How does the system handle a poll with answer options that have identical text?
- What is displayed when total votes are zero and percentages would cause division by zero?

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: System MUST display the currently active poll to all website visitors on a dedicated poll section or page.
- **FR-002**: System MUST allow visitors to cast exactly one vote per poll per browser session or device fingerprint.
- **FR-003**: System MUST display poll results (vote counts and percentages per option) after a visitor votes.
- **FR-004**: System MUST prevent a visitor from voting more than once on the same poll.
- **FR-005**: System MUST allow only one poll to be active at any given time.
- **FR-006**: System MUST allow administrators to create polls with a question and a minimum of 2 and maximum of 10 answer options.
- **FR-007**: System MUST allow administrators to activate and deactivate polls via an admin interface.
- **FR-008**: System MUST allow administrators to view all polls with their status, vote counts, and creation dates.
- **FR-009**: System MUST allow administrators to set an optional expiry date on a poll, after which voting is automatically disabled.
- **FR-010**: System MUST display a user-friendly message to visitors when no poll is currently active.
- **FR-011**: System MUST persist all poll data (questions, options, votes) across application restarts.
- **FR-012**: System MUST show poll results in a visually clear format, including vote counts and percentage breakdowns per option.
- **FR-013**: System MUST validate poll creation inputs and reject submissions with a blank question or fewer than 2 answer options.
- **FR-014**: System MUST allow administrators to delete polls, with a confirmation warning when votes are associated with the poll.
- **FR-015**: System MUST restrict poll management operations (create, activate, deactivate, delete) to authenticated administrators only.

### Key Entities

- **Poll**: Represents a single opinion poll. Key attributes: unique identifier, question text, status (active/inactive/expired), optional expiry date, creation date.
- **Poll Option**: Represents one answer choice within a poll. Key attributes: unique identifier, option text, display order, reference to parent Poll.
- **Vote**: Represents a single visitor's vote on a poll. Key attributes: unique identifier, reference to Poll, reference to Poll Option, timestamp, voter identifier (session/cookie token).

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Visitors can view an active poll and submit a vote in under 30 seconds on a standard connection.
- **SC-002**: Poll results are displayed accurately reflecting 100% of submitted votes with no rounding errors greater than 1%.
- **SC-003**: The duplicate vote prevention mechanism correctly rejects re-votes in at least 95% of attempts within the same browser session.
- **SC-004**: Administrators can create, activate, and manage a poll end-to-end in under 5 minutes using the admin interface.
- **SC-005**: Poll results page loads and renders within 2 seconds for up to 10,000 total votes.
- **SC-006**: The opinion poll feature is successfully integrated into the website with no regression to existing functionality (raffle, jobs, home, location pages remain fully functional).

## Assumptions

- The existing Spring Boot application infrastructure (controllers, FreeMarker templates, JPA/Hibernate with the `GSOJUG` schema, Bootstrap CSS) will be reused for this feature without architectural changes.
- Administrator authentication will reuse or leverage the existing application's authentication mechanism; if none exists, a simple session-based admin login is sufficient for v1.
- Duplicate vote prevention will be implemented using browser cookies/session tokens for v1; IP-based prevention and account-based voting are out of scope.
- Only one poll can be active at a time (single active poll model).
- Poll options support plain text only; rich text or image options are out of scope.
- The poll feature will integrate into the existing website navigation and Bootstrap-based layout without a full redesign.
- Mobile responsiveness is expected as the existing site uses Bootstrap, which provides responsive behavior by default.
- Vote data is considered non-personally-identifiable for basic analytics; no GDPR/CCPA special handling is required beyond session token storage.
- Administrators access the admin panel via a protected URL path; no separate user management system needs to be built.
