package com.mediary.Models.Entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "scheduleitemtype", schema = "public", catalog = "MediaryDB")
public class ScheduleitemtypeEntity {
    private Integer id;
    private String name;
    private Collection<ScheduleitemEntity> scheduleitemsById;

    @Id
    @SequenceGenerator(name="scheduleitemtype_id_seq", sequenceName="scheduleitemtype_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="scheduleitemtype_id_seq")
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleitemtypeEntity that = (ScheduleitemtypeEntity) o;

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

    @OneToMany(mappedBy = "scheduleitemtypeByScheduleitemtypeid")
    public Collection<ScheduleitemEntity> getScheduleitemsById() {
        return scheduleitemsById;
    }

    public void setScheduleitemsById(Collection<ScheduleitemEntity> scheduleitemsById) {
        this.scheduleitemsById = scheduleitemsById;
    }
}
