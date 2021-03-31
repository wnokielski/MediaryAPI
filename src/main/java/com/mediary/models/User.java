package com.mediary.models;

import javax.persistence.*;

@Entity
@Table(name = "Users", schema="public")
public class User {

    private Long id;
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @SequenceGenerator(name="users_id_seq", sequenceName="users_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="users_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
