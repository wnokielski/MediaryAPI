package com.mediary.Models.Entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "users", schema = "public", catalog = "MediaryDB")
public class UsersEntity {
    private Integer id;
    private String uid;
    private String fullname;
    private String gender;
    private Date dateofbirth;
    private BigDecimal weight;
    private Collection<ScheduleitemEntity> scheduleitemsById;
    private Collection<StatisticEntity> statisticsById;
    private Collection<TestresultEntity> testresultsById;

    @Id
    @SequenceGenerator(name="users_id_seq", sequenceName="users_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="users_id_seq")
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid", nullable = false, length = 50)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "fullname", nullable = false, length = 40)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Basic
    @Column(name = "gender", nullable = false)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "dateofbirth", nullable = false)
    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    @Basic
    @Column(name = "weight", nullable = true, precision = 1)
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (fullname != null ? !fullname.equals(that.fullname) : that.fullname != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (dateofbirth != null ? !dateofbirth.equals(that.dateofbirth) : that.dateofbirth != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (fullname != null ? fullname.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (dateofbirth != null ? dateofbirth.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "usersByUserid")
    public Collection<ScheduleitemEntity> getScheduleitemsById() {
        return scheduleitemsById;
    }

    public void setScheduleitemsById(Collection<ScheduleitemEntity> scheduleitemsById) {
        this.scheduleitemsById = scheduleitemsById;
    }

    @OneToMany(mappedBy = "usersByUserid")
    public Collection<StatisticEntity> getStatisticsById() {
        return statisticsById;
    }

    public void setStatisticsById(Collection<StatisticEntity> statisticsById) {
        this.statisticsById = statisticsById;
    }

    @OneToMany(mappedBy = "usersByUserid")
    public Collection<TestresultEntity> getTestresultsById() {
        return testresultsById;
    }

    public void setTestresultsById(Collection<TestresultEntity> testresultsById) {
        this.testresultsById = testresultsById;
    }
}
