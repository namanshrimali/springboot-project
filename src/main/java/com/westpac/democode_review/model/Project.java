package com.westpac.democode_review.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Project {
    String id;
    String projectName;
    String client;
    Date proposedAt;
    Boolean isStarted;
    long budget;
    Date lastDeployed;
}
