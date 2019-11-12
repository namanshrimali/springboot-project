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
    String id;
    @Column(name= "Name")
    String projectName;
    @Column(name= "Title")
    String client;
    @Column(name= "Date of Proposal")
    Date proposedAt;
    @Column(name= "Start Status")
    Boolean isStarted;
    @Column(name= "Budget")
    long budget;
    @Column(name = "Last Deployed")
    Date lastDeployed;
}
