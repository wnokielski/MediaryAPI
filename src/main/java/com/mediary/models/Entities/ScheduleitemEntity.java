package com.mediary.Models.Entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "scheduleitem", schema = "public", catalog = "MediaryDB")
public class ScheduleitemEntity {
    private Integer id;
    private String title;
    private Date date;
    private String place;
    private String address;
    private String note;
    private UsersEntity usersByUserid;
    private ScheduleitemtypeEntity scheduleitemtypeByScheduleitemtypeid;

    @Id
    @SequenceGenerator(name="scheduleitem_id_seq", sequenceName="scheduleitem_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="scheduleitem_id_seq")
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 30)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "place", nullable = false, length = 30)
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "note", nullable = true, length = 200)
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

        ScheduleitemEntity that = (ScheduleitemEntity) o;

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
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    public UsersEntity getUsersByUserid() {
        return usersByUserid;
    }

    public void setUsersByUserid(UsersEntity usersByUserid) {
        this.usersByUserid = usersByUserid;
    }

    @ManyToOne
    @JoinColumn(name = "scheduleitemtypeid", referencedColumnName = "id", nullable = false)
    public ScheduleitemtypeEntity getScheduleitemtypeByScheduleitemtypeid() {
        return scheduleitemtypeByScheduleitemtypeid;
    }

    public void setScheduleitemtypeByScheduleitemtypeid(ScheduleitemtypeEntity scheduleitemtypeByScheduleitemtypeid) {
        this.scheduleitemtypeByScheduleitemtypeid = scheduleitemtypeByScheduleitemtypeid;
    }
}
