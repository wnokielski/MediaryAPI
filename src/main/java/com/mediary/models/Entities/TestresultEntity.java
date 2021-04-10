package com.mediary.Models.Entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "testresult", schema = "public", catalog = "MediaryDB")
public class TestresultEntity {
    private Integer id;
    private String title;
    private String note;
    private Date dateofthetest;
    private Collection<FilesEntity> filesById;
    private TesttypeEntity testtypeByTesttypeid;
    private UsersEntity usersByUserid;
    private Collection<TestresultitemEntity> testresultitemsById;

    @Id
    @SequenceGenerator(name="testresult_id_seq", sequenceName="testresult_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="testresult_id_seq")
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
    @Column(name = "note", nullable = true, length = 200)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "dateofthetest", nullable = false)
    public Date getDateofthetest() {
        return dateofthetest;
    }

    public void setDateofthetest(Date dateofthetest) {
        this.dateofthetest = dateofthetest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestresultEntity that = (TestresultEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (dateofthetest != null ? !dateofthetest.equals(that.dateofthetest) : that.dateofthetest != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (dateofthetest != null ? dateofthetest.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "testresultByTestresultid")
    public Collection<FilesEntity> getFilesById() {
        return filesById;
    }

    public void setFilesById(Collection<FilesEntity> filesById) {
        this.filesById = filesById;
    }

    @ManyToOne
    @JoinColumn(name = "testtypeid", referencedColumnName = "id", nullable = false)
    public TesttypeEntity getTesttypeByTesttypeid() {
        return testtypeByTesttypeid;
    }

    public void setTesttypeByTesttypeid(TesttypeEntity testtypeByTesttypeid) {
        this.testtypeByTesttypeid = testtypeByTesttypeid;
    }

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    public UsersEntity getUsersByUserid() {
        return usersByUserid;
    }

    public void setUsersByUserid(UsersEntity usersByUserid) {
        this.usersByUserid = usersByUserid;
    }

    @OneToMany(mappedBy = "testresultByTestresultid")
    public Collection<TestresultitemEntity> getTestresultitemsById() {
        return testresultitemsById;
    }

    public void setTestresultitemsById(Collection<TestresultitemEntity> testresultitemsById) {
        this.testresultitemsById = testresultitemsById;
    }
}
