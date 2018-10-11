package com.example.android.demo.Db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * 只要更改数据就需要重新编译
 * Build --> Make Module 'APP'编译
 */
@Entity
public class Student {
    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    @Property(nameInDb = "stu_no")
    private int stuNo;
    @Property(nameInDb = "stu_name")
    @NotNull
    private String stuName;
    @Property(nameInDb = "teacher_id")
    private Long teacherId;
    @Property(nameInDb = "stu_age")
    private String stuAge;
    @Property(nameInDb = "stu_price")
    private String stuPrice;
    @Generated(hash = 2100874659)
    public Student(Long id, int stuNo, @NotNull String stuName, Long teacherId,
            String stuAge, String stuPrice) {
        this.id = id;
        this.stuNo = stuNo;
        this.stuName = stuName;
        this.teacherId = teacherId;
        this.stuAge = stuAge;
        this.stuPrice = stuPrice;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getStuNo() {
        return this.stuNo;
    }
    public void setStuNo(int stuNo) {
        this.stuNo = stuNo;
    }
    public String getStuName() {
        return this.stuName;
    }
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
    public Long getTeacherId() {
        return this.teacherId;
    }
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
    public String getStuAge() {
        return this.stuAge;
    }
    public void setStuAge(String stuAge) {
        this.stuAge = stuAge;
    }
    public String getStuPrice() {
        return this.stuPrice;
    }
    public void setStuPrice(String stuPrice) {
        this.stuPrice = stuPrice;
    }

}
