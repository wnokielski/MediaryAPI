package com.mediary.Models.Entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "statistic", schema = "public", catalog = "MediaryDB")
public class StatisticEntity {
    private Integer id;
    private String value;
    private Date date;
    private StatistictypeEntity statistictypeByStatistictypeid;
    private UsersEntity usersByUserid;

    @Id
    @SequenceGenerator(name="statistic_id_seq", sequenceName="statistic_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="statistic_id_seq")
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatisticEntity that = (StatisticEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "statistictypeid", referencedColumnName = "id", nullable = false)
    public StatistictypeEntity getStatistictypeByStatistictypeid() {
        return statistictypeByStatistictypeid;
    }

    public void setStatistictypeByStatistictypeid(StatistictypeEntity statistictypeByStatistictypeid) {
        this.statistictypeByStatistictypeid = statistictypeByStatistictypeid;
    }

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    public UsersEntity getUsersByUserid() {
        return usersByUserid;
    }

    public void setUsersByUserid(UsersEntity usersByUserid) {
        this.usersByUserid = usersByUserid;
    }
}
