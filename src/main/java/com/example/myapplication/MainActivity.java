package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.dbmanager.commonUtil;
import com.student.entity.Student;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    private static final String TAG = "ln";

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button4)
    Button button4;
    @Bind(R.id.button5)
    Button button5;

    private commonUtil commonUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        commonUtil = new commonUtil(MainActivity.this);
    }

    @OnClick(R.id.button)
    public void insertDate() {
        Student student = new Student();
        student.setAddress("北京");
        student.setName("张三");
        student.setAge(23);
        commonUtil.insertStudent(student);
    }

    @OnClick(R.id.button2)
    public void deleteDate() {
        //按照指定的id进行删除，delete from student where id  = ?
        Student student = new Student();
        student.setId(1001l);
        commonUtil.deteleDate(student);
    }

    @OnClick(R.id.button3)
    public void updata() {
        //修改语句应该是updata student set name = "jack" where id = 1001；
        //所以修改对象student中必须要有id，否则会将所有数据全部改成传入的对象数据
        Student student = new Student();
        student.setId(1001l);
        student.setAge(100);
        student.setName("jack");
        student.setAddress("湖北科技学院");
        commonUtil.updateStudent(student);
    }

    @OnClick(R.id.button4)
    public void selectDate() {
//        List<Student> students=commonUtil.listAll();
        Student student = new Student();
        commonUtil.listOneStudent(1001l);
    }

    @OnClick(R.id.button5)
    public void querybuilder()
    {

    }
}
