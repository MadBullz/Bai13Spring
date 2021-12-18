package com.example.bai13spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    public Employee(String fullName, Date birthday, String phone, String email) {
        FullName = fullName;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
    }


    @Column(name = "fullName")
    private String FullName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;



    @Column(name = "employee_type")
    private int employee_type;

    @Column(name = "employee_count")
    private int employee_count;


    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Fresher fresher;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Experience experience;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Intern intern;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonIgnore
    private List<Certificate> certificates;

    @Override
    public String toString() {
        return "Employee{" +
                "Id=" + Id +
                ", FullName='" + FullName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
