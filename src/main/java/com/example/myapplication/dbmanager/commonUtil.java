package com.example.myapplication.dbmanager;

import android.content.Context;

import com.student.dao.StudentDao;
import com.student.entity.Student;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


/**
 * 完成对某一张表的具体操作，orm，操作的是对象，student
 * Created by Administrator on 2016/8/12.
 */
public class commonUtil {
    public daomanager manager;

    public commonUtil(Context context) {
        manager = daomanager.getInstance();
        manager.init(context);
    }

    /**
     * 完成对数据表student的插入操作
     *
     * @param student
     * @return
     */
    public boolean insertStudent(Student student) {
        boolean flag = false;
        flag = (manager.getDaoSession().insert(student)) != -1 ? true : false;
        return flag;
    }

    /**
     * 同时插入多条记录可能会耗时比较长，所以单开一个线程进行插入
     *
     * @param students
     * @return
     */
    public boolean insertMultStudent(final List<Student> students) {
        boolean flag = false;
        try {
            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Student student : students) {
                        manager.getDaoSession().insertOrReplace(student);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对Student的某一个数据的修改
     *
     * @param student
     * @return
     */
    public boolean updateStudent(Student student) {
        boolean flag = false;
        try {
            manager.getDaoSession().update(student);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 实现删除操作
     *
     * @param student
     * @return
     */
    public boolean deteleDate(Student student) {

        boolean flag = false;
        try {
            manager.getDaoSession().delete(student);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
        //删除所有数据
//        manager.getDaoSession().deleteAll();
    }

    /**
     * 返回多行记录
     *
     * @return
     */
    public List<Student> listAll() {
        return manager.getDaoSession().loadAll(Student.class);
    }

    /**
     * 按照主键返回单行记录
     *
     * @param id
     * @return
     */
    public Student listOneStudent(long id) {
        return manager.getDaoSession().load(Student.class, id);
    }

    public void query1() {
        List<Student> list = manager.getDaoSession().queryRaw(Student.class, "where name like ? and _id > ?", new String[]{"%李%", "1001"});
    }

    public void query2() {
        QueryBuilder<Student> builder = manager.getDaoSession().queryBuilder(Student.class);
        //里面每个语句都是逻辑与操作
        //下面语句等同于select * from where _id > ? and name like ?
        //
        builder.where(StudentDao.Properties.Id.ge(23), StudentDao.Properties.Name.like("%李%")).limit(3);
        //里面每个语句都是逻辑或的操作
        //下面语句等同于select * from where _id > ? or name like ?
        builder.whereOr(StudentDao.Properties.Id.ge(23), StudentDao.Properties.Name.like("%李%"));
    }

}
