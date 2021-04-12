package com.mediary.Models.Entities;

import javax.persistence.*;

@Entity
@Table(name = "`File`", schema = "public", catalog = "MediaryDB")
public class FileEntity {
    private Integer id;
    private String uuid;
    private String originalname;
    private String url;
    private TestResultEntity testresultByTestresultid;

    @Id
    @SequenceGenerator(name="`File_ID_seq`", sequenceName="`File_ID_seq`", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="`File_ID_seq`")
    @Column(name = "`ID`", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "`UID`", nullable = false, length = 36)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "`OriginalName`", nullable = false, length = 50)
    public String getOriginalname() {
        return originalname;
    }

    public void setOriginalname(String originalname) {
        this.originalname = originalname;
    }

    @Basic
    @Column(name = "`URL`", nullable = false, length = 200)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        com.mediary.Models.Entities.FileEntity that = (com.mediary.Models.Entities.FileEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (originalname != null ? !originalname.equals(that.originalname) : that.originalname != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (originalname != null ? originalname.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "TestResultID", referencedColumnName = "`ID`", nullable = false)
    public TestResultEntity getTestresultByTestresultid() {
        return testresultByTestresultid;
    }

    public void setTestresultByTestresultid(TestResultEntity testresultByTestresultid) {
        this.testresultByTestresultid = testresultByTestresultid;
    }
}
