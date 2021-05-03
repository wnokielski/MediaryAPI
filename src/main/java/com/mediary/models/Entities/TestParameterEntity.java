package com.mediary.Models.Entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "`TestParameter`", schema = "public", catalog = "MediaryDB")
public class TestParameterEntity {
    private Integer id;
    private String name;
    private String unit;
    private TestTypeEntity testTypeById;

    @Id
    @SequenceGenerator(name = "`TestParameter_ID_seq", sequenceName = "TestParameter_Id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TestParameter_ID_seq")
    @Column(name = "`ID`", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "`Name`", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "`Unit`", nullable = true, length = 10)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TestParameterEntity that = (TestParameterEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "`TestTypeID`", referencedColumnName = "`ID`", nullable = false)
    public TestTypeEntity getTestTypeById() {
        return testTypeById;
    }

    public void setTestTypeById(TestTypeEntity testTypeById) {
        this.testTypeById = testTypeById;
    }

}
