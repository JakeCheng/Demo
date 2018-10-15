package com.example.android.demo.Db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by android on 2018/10/15.
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    @Property(nameInDb = "email")
    @NotNull
    private String email;
    @Generated(hash = 1339009088)
    public User(Long id, @NotNull String email) {
        this.id = id;
        this.email = email;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
