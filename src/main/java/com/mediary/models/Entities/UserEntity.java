package com.mediary.Models.Entities;

import javax.persistence.*;

import com.mediary.Models.Enums.Gender;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "`User`", schema = "public", catalog = "MediaryDB")
public class UserEntity {
    private Integer id;
    private String email;
    private String password;
    private String fullName;
    private Gender gender;
    private Date dateOfBirth;
    private BigDecimal weight;
    private BigDecimal height;
    private Collection<ScheduleItemEntity> scheduleItemsById;
    private Collection<StatisticEntity> statisticsById;
    private Collection<MedicalRecordEntity> medicalRecordsById;

    @Id
    @SequenceGenerator(name = "`User_ID_seq`", sequenceName = "`User_ID_seq`", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "`User_ID_seq`")
    @Column(name = "`ID`", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "`Email`", nullable = false, length = 254)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "`Password`", nullable = false, length = 72)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "`FullName`", nullable = false, length = 50)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "`Gender`", nullable = true, length = 10)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "`DateOfBirth`", nullable = true)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "`Weight`", nullable = true, precision = 4, scale = 1)
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "`Height`", nullable = true, precision = 3)
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        com.mediary.Models.Entities.UserEntity that = (com.mediary.Models.Entities.UserEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null)
            return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null)
            return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null)
            return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null)
            return false;
        if (height != null ? !height.equals(that.height) : that.height != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userById")
    public Collection<ScheduleItemEntity> getScheduleItemsById() {
        return scheduleItemsById;
    }

    public void setScheduleItemsById(Collection<ScheduleItemEntity> scheduleItemsById) {
        this.scheduleItemsById = scheduleItemsById;
    }

    @OneToMany(mappedBy = "userById")
    public Collection<StatisticEntity> getStatisticsById() {
        return statisticsById;
    }

    public void setStatisticsById(Collection<StatisticEntity> statisticsById) {
        this.statisticsById = statisticsById;
    }

    @OneToMany(mappedBy = "userById")
    public Collection<MedicalRecordEntity> getMedicalRecordsById() {
        return medicalRecordsById;
    }

    public void setMedicalRecordsById(Collection<MedicalRecordEntity> medicalRecordsById) {
        this.medicalRecordsById = medicalRecordsById;
    }
}
