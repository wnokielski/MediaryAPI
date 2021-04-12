package com.mediary.Models.Entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "`ScheduleItem`", schema = "public", catalog = "MediaryDB")
public class ScheduleItemEntity {
    private Integer id;
    private String title;
    private Date date;
    private String place;
    private String address;
    private String note;
    private UserEntity userByUserid;
    private ScheduleItemTypeEntity scheduleitemtypeByScheduleitemtypeid;

    @Id
    @SequenceGenerator(name="`ScheduleItem_ID_seq`", sequenceName="`ScheduleItem_ID_seq`", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="`ScheduleItem_ID_seq`")
    @Column(name = "`ID`", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "`Title`", nullable = false, length = 30)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "`Date`", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "`Place`", nullable = false, length = 30)
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Basic
    @Column(name = "`Address`", nullable = true, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "`Note`", nullable = true, length = 200)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        com.mediary.Models.Entities.ScheduleItemEntity that = (com.mediary.Models.Entities.ScheduleItemEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "`UserID`", referencedColumnName = "`ID`", nullable = false)
    public UserEntity getUserByUserid() {
        return userByUserid;
    }

    public void setUserByUserid(UserEntity userByUserid) {
        this.userByUserid = userByUserid;
    }

    @ManyToOne
    @JoinColumn(name = "`ScheduleItemTypeID`", referencedColumnName = "`ID`", nullable = false)
    public ScheduleItemTypeEntity getScheduleitemtypeByScheduleitemtypeid() {
        return scheduleitemtypeByScheduleitemtypeid;
    }

    public void setScheduleitemtypeByScheduleitemtypeid(ScheduleItemTypeEntity scheduleitemtypeByScheduleitemtypeid) {
        this.scheduleitemtypeByScheduleitemtypeid = scheduleitemtypeByScheduleitemtypeid;
    }
}
