package com.example.bai13spring.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "intern")
public class Intern implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @Column(name = "major")
    private String major;

    @Column(name = "semester")
    private int semester;

    @Column(name = "university_name")
    private String university_name;

    @Override
    public String toString() {
        return "Intern{" +
                "id=" + id +
                ", employee=" + employee +
                ", major='" + major + '\'' +
                ", semester=" + semester +
                ", university_name='" + university_name + '\'' +
                '}';
    }
}

