package com.mediary.Models.Entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "`TestResult`", schema = "public", catalog = "MediaryDB")
public class TestResultEntity {
    private Integer id;
    private String title;
    private String note;
    private Date dateofthetest;
    private Collection<com.mediary.Models.Entities.FileEntity> filesById;
    private TestTypeEntity testtypeByTesttypeid;
    private UserEntity userByUserid;
    private Collection<TestResultItemEntity> testresultitemsById;

    @Id
    @SequenceGenerator(name="`TestResult_ID_seq`", sequenceName="`TestResult_ID_seq`", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="`TestResult_ID_seq`")
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
    @Column(name = "`Note`", nullable = true, length = 200)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "`DateOfTheTest`", nullable = false)
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

        com.mediary.Models.Entities.TestResultEntity that = (com.mediary.Models.Entities.TestResultEntity) o;

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
    public Collection<FileEntity> getFilesById() {
        return filesById;
    }

    public void setFilesById(Collection<FileEntity> filesById) {
        this.filesById = filesById;
    }

    @ManyToOne
    @JoinColumn(name = "`TestTypeID`", referencedColumnName = "`ID`", nullable = false)
    public TestTypeEntity getTesttypeByTesttypeid() {
        return testtypeByTesttypeid;
    }

    public void setTesttypeByTesttypeid(TestTypeEntity testtypeByTesttypeid) {
        this.testtypeByTesttypeid = testtypeByTesttypeid;
    }

    @ManyToOne
    @JoinColumn(name = "`UserID`", referencedColumnName = "`ID`", nullable = false)
    public UserEntity getUserByUserid() {
        return userByUserid;
    }

    public void setUserByUserid(UserEntity userByUserid) {
        this.userByUserid = userByUserid;
    }

    @OneToMany(mappedBy = "testresultByTestresultid")
    public Collection<TestResultItemEntity> getTestresultitemsById() {
        return testresultitemsById;
    }

    public void setTestresultitemsById(Collection<TestResultItemEntity> testresultitemsById) {
        this.testresultitemsById = testresultitemsById;
    }
}
