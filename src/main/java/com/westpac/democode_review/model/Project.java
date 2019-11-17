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
    @Column(name= "name")
    private String name;
    @Column(name= "client")
    private String client;
    @Column(name= "date_of_proposal")
    private Date proposedAt;
    @Column(name= "start_status")
    private Boolean isStarted;
    @Column(name= "budget")
    private long budget;
    @Column(name = "last_deployed")
    private Date lastDeployed;
}
