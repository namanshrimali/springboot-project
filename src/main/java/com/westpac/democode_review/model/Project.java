package com.westpac.democode_review.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Project {
    @Id
    @GeneratedValue
    private int id;
    @Column(name= "Name")
    private String name;
    @Column(name= "Client")
    private String client;
    @Column(name= "Date_of_Proposal")
    private Date proposedAt;
    @Column(name= "Start_Status")
    private Boolean isStarted;
    @Column(name= "Budget")
    private long budget;
    @Column(name = "Last_Deployed")
    private Date lastDeployed;
}
