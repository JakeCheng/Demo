package com.example.android.demo.GreenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.android.demo.Db.Teacher;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEACHER".
*/
public class TeacherDao extends AbstractDao<Teacher, Long> {

    public static final String TABLENAME = "TEACHER";

    /**
     * Properties of entity Teacher.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Teacher_id = new Property(1, String.class, "teacher_id", false, "teacher_id");
        public final static Property Teacher_name = new Property(2, String.class, "teacher_name", false, "teacher_name");
        public final static Property Teacher_price = new Property(3, String.class, "teacher_price", false, "teacher_price");
    }

    private DaoSession daoSession;


    public TeacherDao(DaoConfig config) {
        super(config);
    }
    
    public TeacherDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEACHER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"teacher_id\" TEXT," + // 1: teacher_id
                "\"teacher_name\" TEXT NOT NULL ," + // 2: teacher_name
                "\"teacher_price\" TEXT);"); // 3: teacher_price
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_TEACHER_teacher_id ON \"TEACHER\"" +
                " (\"teacher_id\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEACHER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Teacher entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String teacher_id = entity.getTeacher_id();
        if (teacher_id != null) {
            stmt.bindString(2, teacher_id);
        }
        stmt.bindString(3, entity.getTeacher_name());
 
        String teacher_price = entity.getTeacher_price();
        if (teacher_price != null) {
            stmt.bindString(4, teacher_price);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Teacher entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String teacher_id = entity.getTeacher_id();
        if (teacher_id != null) {
            stmt.bindString(2, teacher_id);
        }
        stmt.bindString(3, entity.getTeacher_name());
 
        String teacher_price = entity.getTeacher_price();
        if (teacher_price != null) {
            stmt.bindString(4, teacher_price);
        }
    }

    @Override
    protected final void attachEntity(Teacher entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Teacher readEntity(Cursor cursor, int offset) {
        Teacher entity = new Teacher( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // teacher_id
            cursor.getString(offset + 2), // teacher_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // teacher_price
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Teacher entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTeacher_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTeacher_name(cursor.getString(offset + 2));
        entity.setTeacher_price(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Teacher entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Teacher entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Teacher entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
