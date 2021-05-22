package com.mediary.Models.Entities;

import javax.persistence.*;

@Entity
@Table(name = "`testItem`", schema = "public", catalog = "MediaryDB")
public class TestItemEntity {
    private Integer id;
    private String name;
    private String value;
    private String unit;
    private MedicalRecordEntity medicalRecordById;

    @Id
    @SequenceGenerator(name = "`TestItem_ID_seq`", sequenceName = "`TestItem_ID_seq`", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "`TestItem_ID_seq`")
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
    @Column(name = "`Value`", nullable = true, length = 50)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

        TestItemEntity that = (TestItemEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (value != null ? !value.equals(that.value) : that.value != null)
            return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null)
            return false;

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
    @JoinColumn(name = "`MedicalRecordID`", referencedColumnName = "`ID`", nullable = false)
    public MedicalRecordEntity getMedicalRecordById() {
        return medicalRecordById;
    }

    public void setMedicalRecordById(MedicalRecordEntity medicalRecordById) {
        this.medicalRecordById = medicalRecordById;
    }
}
