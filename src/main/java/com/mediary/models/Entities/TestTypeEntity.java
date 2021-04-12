package com.mediary.Models.Entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "`TestType`", schema = "public", catalog = "MediaryDB")
public class TestTypeEntity {
    private Integer id;
    private String name;
    private String parameters;
    private Collection<TestResultEntity> testresultsById;

    @Id
    @SequenceGenerator(name="`TestType_ID_seq`", sequenceName="`TestType_ID_seq`", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="`TestType_ID_seq`")
    @Column(name = "`ID`", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "`Name`", nullable = false, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "`Parameters`", nullable = false)
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

        com.mediary.Models.Entities.TestTypeEntity that = (com.mediary.Models.Entities.TestTypeEntity) o;

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
    public Collection<TestResultEntity> getTestresultsById() {
        return testresultsById;
    }

    public void setTestresultsById(Collection<TestResultEntity> testresultsById) {
        this.testresultsById = testresultsById;
    }
}
