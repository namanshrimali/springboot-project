package com.westpac.democode_review.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Project {
    @Id
    private String id;
    @Column(name= "Name")
    private String projectName;
    @Column(name= "Title")
    private String client;
    @Column(name= "Date of Proposal")
    private Date proposedAt;
    @Column(name= "Start Status")
    private Boolean isStarted;
    @Column(name= "Budget")
    private long budget;
    @Column(name = "Last Deployed")
    private Date lastDeployed;
}
