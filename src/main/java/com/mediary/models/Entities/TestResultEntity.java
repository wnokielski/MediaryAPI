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
    private Date dateOfTheTest;
    private Collection<FileEntity> filesById;
    private TestTypeEntity testTypeById;
    private UserEntity userById;
    private Collection<TestResultItemEntity> testResultItemsById;

    @Id
    @SequenceGenerator(name = "`TestResult_ID_seq`", sequenceName = "`TestResult_ID_seq`", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "`TestResult_ID_seq`")
    @Column(name = "`ID`", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "`Title`", nullable = false, length = 50)
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
    public Date getDateOfTheTest() {
        return dateOfTheTest;
    }

    public void setDateOfTheTest(Date dateOfTheTest) {
        this.dateOfTheTest = dateOfTheTest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        com.mediary.Models.Entities.TestResultEntity that = (com.mediary.Models.Entities.TestResultEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null)
            return false;
        if (note != null ? !note.equals(that.note) : that.note != null)
            return false;
        if (dateOfTheTest != null ? !dateOfTheTest.equals(that.dateOfTheTest) : that.dateOfTheTest != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (dateOfTheTest != null ? dateOfTheTest.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "testResultById")
    public Collection<FileEntity> getFilesById() {
        return filesById;
    }

    public void setFilesById(Collection<FileEntity> filesById) {
        this.filesById = filesById;
    }

    @ManyToOne
    @JoinColumn(name = "`TestTypeID`", referencedColumnName = "`ID`", nullable = false)
    public TestTypeEntity getTestTypeById() {
        return testTypeById;
    }

    public void setTestTypeById(TestTypeEntity testTypeById) {
        this.testTypeById = testTypeById;
    }

    @ManyToOne
    @JoinColumn(name = "`UserID`", referencedColumnName = "`ID`", nullable = false)
    public UserEntity getUserById() {
        return userById;
    }

    public void setUserById(UserEntity userById) {
        this.userById = userById;
    }

    @OneToMany(mappedBy = "testResultById")
    public Collection<TestResultItemEntity> getTestResultItemsById() {
        return testResultItemsById;
    }

    public void setTestResultItemsById(Collection<TestResultItemEntity> testResultItemsById) {
        this.testResultItemsById = testResultItemsById;
    }
}
