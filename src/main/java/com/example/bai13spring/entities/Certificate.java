package com.example.bai13spring.entities;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "certificate")
public class Certificate implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "certificateName")
    private String certificateName;

    @Column(name = "certificateRank")
    private String certificateRank;

    @Column(name = "certificatedDate")
    private Date certificatedDate;

    public Certificate(String certificateName, String certificateRank, Date certificatedDate) {
        this.certificateName = certificateName;
        this.certificateRank = certificateRank;
        this.certificatedDate = certificatedDate;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", certificateName='" + certificateName + '\'' +
                ", certificateRank='" + certificateRank + '\'' +
                ", certificatedDate=" + certificatedDate +
                ", employee=" + employee +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

}
