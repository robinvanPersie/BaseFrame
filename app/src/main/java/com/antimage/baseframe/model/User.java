package com.antimage.baseframe.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by xuyuming on 2018/10/25.
 */
// 如果你有一个以上的模式，你可以告诉greendao实体属于哪个模式（选择任何字符串作为名称）。schema = "myschema",
// 标志允许实体类可有更新，删除，刷新方法 active = true,
// 指定数据库中表的名称。默认情况下，该名称基于实体类名。 nameInDb = "AWESOME_USERS",
// 在这里定义多个列的索引 indexes = {@Index(value = "name DESC", unique = true)},
// 如果DAO创建数据库表(默认为true)，则设置标记去标识。如果有多个实体映射到一个表，或者在greenDAO之外创建表创建，将此设置为false。 createInDb = false,
// 是否应该生成所有的属性构造函数。一个无args构造函数总是需要的 generateConstructors = true,
// 是否生成属性的getter和setter generateGettersSetters = true
@Entity(nameInDb = "T_USER", active = false)
public class User {

    @Id(autoincrement = true)
    private Long id;

//    @Property(nameInDb = "USER_NAME")
    private String userId;
    private String name;
    private String mobile;

    @Generated(hash = 1247458037)
    public User(Long id, String userId, String name, String mobile) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.mobile = mobile;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    @Override
    public String toString() {
        return "userId: " + userId + " , name: " + name + " , mobile: " + mobile;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
