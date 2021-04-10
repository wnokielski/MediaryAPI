package com.mediary.Models.Entities;

import javax.persistence.*;

@Entity
@Table(name = "testresultitem", schema = "public", catalog = "MediaryDB")
public class TestresultitemEntity {
    private Integer id;
    private String name;
    private String value;
    private String unit;
    private TestresultEntity testresultByTestresultid;

    @Id
    @SequenceGenerator(name="testresultitem_id_seq", sequenceName="testresultitem_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="testresultitem_id_seq")
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
    @Column(name = "value", nullable = true, length = 50)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "unit", nullable = true, length = 10)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestresultitemEntity that = (TestresultitemEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "testresultid", referencedColumnName = "id", nullable = false)
    public TestresultEntity getTestresultByTestresultid() {
        return testresultByTestresultid;
    }

    public void setTestresultByTestresultid(TestresultEntity testresultByTestresultid) {
        this.testresultByTestresultid = testresultByTestresultid;
    }
}
