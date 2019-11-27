package com.westpac.democode_review.dto;

import lombok.Getter;

import java.util.Date;
@Getter
public class IncomingMessageDTO {
    private int projectId;
    private String projectName;
    private String projectClient;
    private Date projectProposedAt;
    private Boolean isProjectStarted;
    private long projectBudget;
    private Date projectLastDeployed;
}
