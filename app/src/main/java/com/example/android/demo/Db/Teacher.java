package com.example.android.demo.Db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.example.android.demo.GreenDao.DaoSession;
import com.example.android.demo.GreenDao.StudentDao;
import com.example.android.demo.GreenDao.TeacherDao;

@Entity
public class Teacher {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "teacher_id")
    @Index(unique = true)
    private String teacher_id;
    @Property(nameInDb = "teacher_name")
    @NotNull
    private String teacher_name;
    @Property(nameInDb = "teacher_price")
    private String teacher_price;
    @ToMany(referencedJoinProperty = "teacherId")//指定与之关联的其他类的id
    private List<Student> students;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 648119699)
    private transient TeacherDao myDao;
    @Generated(hash = 981472976)
    public Teacher(Long id, String teacher_id, @NotNull String teacher_name, String teacher_price) {
        this.id = id;
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
        this.teacher_price = teacher_price;
    }
    @Generated(hash = 1630413260)
    public Teacher() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTeacher_id() {
        return this.teacher_id;
    }
    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }
    public String getTeacher_name() {
        return this.teacher_name;
    }
    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 439084603)
    public List<Student> getStudents() {
        if (students == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StudentDao targetDao = daoSession.getStudentDao();
            List<Student> studentsNew = targetDao._queryTeacher_Students(id);
            synchronized (this) {
                if (students == null) {
                    students = studentsNew;
                }
            }
        }
        return students;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 238993120)
    public synchronized void resetStudents() {
        students = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1349174479)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTeacherDao() : null;
    }
    public String getTeacher_price() {
        return this.teacher_price;
    }
    public void setTeacher_price(String teacher_price) {
        this.teacher_price = teacher_price;
    }

}
