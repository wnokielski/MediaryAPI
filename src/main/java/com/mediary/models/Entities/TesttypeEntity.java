package com.mediary.Models.Entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "testtype", schema = "public", catalog = "MediaryDB")
public class TesttypeEntity {
    private Integer id;
    private String name;
    private String parameters;
    private Collection<TestresultEntity> testresultsById;

    @Id
    @SequenceGenerator(name="testtype_id_seq", sequenceName="testtype_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="testtype_id_seq")
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "parameters", nullable = false)
    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TesttypeEntity that = (TesttypeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "testtypeByTesttypeid")
    public Collection<TestresultEntity> getTestresultsById() {
        return testresultsById;
    }

    public void setTestresultsById(Collection<TestresultEntity> testresultsById) {
        this.testresultsById = testresultsById;
    }
}
