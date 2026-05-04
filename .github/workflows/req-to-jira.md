---
on:
  issues:
    types: [opened]
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

# Step-1: Analyze Requirements and Create JIRA Backlog Items

When a new issue is opened in this repository, this workflow will pass the content of the issue as a prompt to the custom agent present in the repository. the custome agent name is speckit.clarify.agent.md . 

The prompt for this agent will look like this:

```/speckit.specify ** issue content ** ```


# Step-2: Create JIRA Backlog Items

The speckit.specify agent in Step-1  generates a spec.md file with corresponding user stories for the requirement given in the issue content. 

Use the atlassian-mcp server to create JIRA backlog items by referring the content of the spec.md file. The backlog items should be created in the appropriate JIRA project and should include all necessary details such as title, description, acceptance criteria, and any relevant labels or components. The atlassian cloud ID is skoganti2026.atlassian.net

``` Use the content of spec.md to create JIRA backlog items. use the atlassian-mcp MCP Server to create the backlog items. ```

