package com.mediary.Models.Entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "`User`", schema = "public", catalog = "MediaryDB")
public class UserEntity {
    private Integer id;
    private String email;
    private String username;
    private String password;
    private String fullName;
    private String gender;
    private Date dateofbirth;
    private BigDecimal weight;
    private Collection<com.mediary.Models.Entities.ScheduleItemEntity> scheduleitemsById;
    private Collection<StatisticEntity> statisticsById;
    private Collection<TestResultEntity> testresultsById;

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
    @Column(name = "`Username`", nullable = true, length = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    @Column(name = "`FullName`", nullable = false, length = 40)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "`Gender`", nullable = true)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "`DateOfBirth`", nullable = true)
    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    @Basic
    @Column(name = "`Weight`", nullable = true, precision = 1)
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
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
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null)
            return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null)
            return false;
        if (dateofbirth != null ? !dateofbirth.equals(that.dateofbirth) : that.dateofbirth != null)
            return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (dateofbirth != null ? dateofbirth.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUserid")
    public Collection<ScheduleItemEntity> getScheduleitemsById() {
        return scheduleitemsById;
    }

    public void setScheduleitemsById(Collection<ScheduleItemEntity> scheduleitemsById) {
        this.scheduleitemsById = scheduleitemsById;
    }

    @OneToMany(mappedBy = "userByUserid")
    public Collection<StatisticEntity> getStatisticsById() {
        return statisticsById;
    }

    public void setStatisticsById(Collection<StatisticEntity> statisticsById) {
        this.statisticsById = statisticsById;
    }

    @OneToMany(mappedBy = "userByUserid")
    public Collection<TestResultEntity> getTestresultsById() {
        return testresultsById;
    }

    public void setTestresultsById(Collection<TestResultEntity> testresultsById) {
        this.testresultsById = testresultsById;
    }
}
