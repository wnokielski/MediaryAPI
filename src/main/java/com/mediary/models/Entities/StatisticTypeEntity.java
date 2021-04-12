package com.mediary.Models.Entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "`StatisticType`", schema = "public", catalog = "MediaryDB")
public class StatisticTypeEntity {
    private Integer id;
    private String name;
    private Collection<StatisticEntity> statisticsById;

    @Id
    @SequenceGenerator(name="`StatisticType_ID_seq`", sequenceName="`StatisticType_ID_seq`", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="`StatisticType_ID_seq`")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        com.mediary.Models.Entities.StatisticTypeEntity that = (com.mediary.Models.Entities.StatisticTypeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "statistictypeByStatistictypeid")
    public Collection<StatisticEntity> getStatisticsById() {
        return statisticsById;
    }

    public void setStatisticsById(Collection<StatisticEntity> statisticsById) {
        this.statisticsById = statisticsById;
    }
}
