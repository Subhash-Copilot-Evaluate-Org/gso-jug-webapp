---
on:
  repository_dispatch:
    types:
      - jira-copilot-prompt

  workflow_dispatch:
    inputs:      
      jira_issue:
        description: 'JIRA issue key (e.g. ALMI-123)'
        required: true
permissions:
  contents: read
  issues: read
  pull-requests: read
tools:
  github:
    toolsets: [default]
mcp-servers:
    atlassian-mcp:
      url: "https://skoganti-api-gateway.azure-api.net/atlassian-mcp/v1/mcp"
      headers:
        Ocp-Apim-Subscription-Key: "9abc8589fd9b44eeb67dc94a9e78c0ce"
      allowed: ["*"]
---

# Step-1: Get JIRA Issue Content from Atlassian MCP Server ( atlassian-mcp)

use the atlassian-mcp server to get the content of the JIRA issue specified by the `jira_issue` input. The content should include all relevant details such as the issue summary, description, and any other pertinent information that will help in creating user stories.

This JIRA issue is typically a feature/EPIC created in JIRA.




# Step-2: Use the content of JIRA issue to generate user stories using speckit.specify agent

Use the speckit.specify agent to generate user stories based on the content of the JIRA issue obtained in Step-1. The agent should analyze the issue details and create well-defined user stories that can be easily understood and implemented by the development team. The prompt to the speckit.specify agent should be designed to ensure that the generated user stories are comprehensive and cover all necessary aspects of the feature/EPIC. It should look something like this:

``` /speckit.specify *** JIRA Content from Step-1 *** ```

The output from the speckit.specify agent should be a list of user stories that are derived from the original JIRA issue, ready to be added back into JIRA or used for further planning and development activities. This output is typically generated in spec.md file as a result of the speckit.specify agent execution.

# Step-3: Use the generated spec.md file to create user stories in JIRA

Use the content of the generated spec.md file from Step-2 to create user stories in JIRA. This can be done by parsing the spec.md file to extract individual user stories and then using the JIRA API to create new issues for each user story under the original EPIC/feature. Each user story should be linked back to the original EPIC/feature in JIRA to maintain traceability and ensure that all related work items are connected.

If there are user stories that are already present in the original JIRA issue, the workflow should be able to identify and avoid creating duplicate user stories. This can be achieved by comparing the generated user stories with the existing ones in JIRA before creating new issues.

Use the atlassian-mcp server again to create new user story issues in JIRA, ensuring that they are properly linked to the original EPIC/feature. The workflow should also handle any necessary error checking and logging to ensure that the process runs smoothly and any issues can be easily identified and resolved.

# Step-4: Post a comment on the original JIRA issue with the details of the created user stories

After successfully creating the user stories in JIRA, post a comment on the original JIRA issue (the EPIC/feature) with the details of the created user stories. This comment should include a summary of the user stories that were generated and created, along with links to each of the new user story issues in JIRA. This will provide visibility to all stakeholders and team members about the work that has been done and allow them to easily access the newly created user stories for further planning and development activities.