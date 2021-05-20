package com.mediary.Models.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "`Statistic`", schema = "public", catalog = "MediaryDB")
public class StatisticEntity {
    private Integer id;
    private String value;
    private Timestamp date;
    private StatisticTypeEntity statisticTypeById;
    private UserEntity userById;

    @Id
    @SequenceGenerator(name = "`Statistic_ID_seq`", sequenceName = "`Statistic_ID_seq`", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "`Statistic_ID_seq`")
    @Column(name = "`ID`", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "`Date`", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        StatisticEntity that = (StatisticEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (value != null ? !value.equals(that.value) : that.value != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null)
            return false;

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
    @JoinColumn(name = "`StatisticTypeID`", referencedColumnName = "`ID`", nullable = false)
    public StatisticTypeEntity getStatisticTypeById() {
        return statisticTypeById;
    }

    public void setStatisticTypeById(StatisticTypeEntity statisticTypeById) {
        this.statisticTypeById = statisticTypeById;
    }

    @ManyToOne
    @JoinColumn(name = "`UserID`", referencedColumnName = "`ID`", nullable = false)
    public UserEntity getUserById() {
        return userById;
    }

    public void setUserById(UserEntity userById) {
        this.userById = userById;
    }
}
