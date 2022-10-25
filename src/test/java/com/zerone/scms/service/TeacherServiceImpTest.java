package com.zerone.scms.service;

import com.zerone.scms.mapper.RoleMapper;
import com.zerone.scms.mapper.UserMapper;
import com.zerone.scms.model.Role;
import com.zerone.scms.model.Teacher;
import com.zerone.scms.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherServiceImpTest {
    @Autowired
    TeacherService teacherService;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * @Description: 保存教师测试。如果传入的id为空说明是触发了新建教师的功能，就新建教师，并新建用户，新建用户的角色关系，
     * 如果不为空则是触发了更新教师信息的功能，则选择else分支通过id更新教师信息，并且更新相应用户信息
     * @return:
     * @Author: 李岩青
     * @Date:
     */
    @Transactional
    @Rollback(true)
    @Test
    void saveTeacher() {
//        设置教师属性
        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");
//        设置教师属性

//        由于下面的euser要重新设置属性所以就没删除这段代码
        teacher.setUsername(teacher.getJobNo());
        teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
        teacher.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("teacher");
        assertNotNull(role);
        teacher.setRole(role);
//        由于下面的euser要重新设置属性所以就没删除这段代码

        assertTrue(teacherService.saveTeacher(teacher));//保存教师数据 并判断是否保存成功
        assertNotNull(teacher.getId());//看保存teacher后id有没有返回
        Teacher newTeacher = teacherService.getTeacherById(teacher.getId());//通过service层调用通过id查询教师的方法将返回的id作为参数传递查询出教师
        assertEquals(teacher, newTeacher);//判断原来的老师和通过id查询出来的老师是否相等
        User newUser = userMapper.selectUserByUserName(teacher.getJobNo());//通过用户名查询出新用户
        User eUser = new User();//创建新用户对象
        eUser.setUserId(teacher.getUserId());//将原来的教师的属性赋值给新教师对象
        eUser.setUsername(teacher.getUsername());//将原来的教师的属性赋值给新教师对象
        eUser.setPassword(teacher.getPassword());//将原来的教师的属性赋值给新教师对象
        eUser.setRole(teacher.getRole());//将原来的教师的属性赋值给新教师对象
        eUser.setEnabled(teacher.getEnabled());//将原来的教师的属性赋值给新教师对象
        assertEquals(eUser, newUser);//判断通过用户名（教师的工号）查询的用户和原来的用户是否相等
        assertTrue(new BCryptPasswordEncoder().matches(teacher.getUsername(), newUser.getPassword()));//判断查询出来的用户的密码和教师的名字是否相等，相等的话就通过
        //通过验证教师表数据的一致性和用户表数据的一致性来判断教师是否保存成功
    }

    /**
     * @Description: 给出id值作为参数，如果该id的teacher存在则返回一个teacher对象，如果不存在则返回空，
     * 通过不为空的断言语句判断，当不为空时该测试通过，说明通过id查到了对象
     * @return:
     * @Author: 李岩青
     * @Date:
     */
    @Test
    @Transactional
    @Rollback(true)
    void getTeacherById() {
        //和上面保存的逻辑基本相同
        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");

        teacher.setUsername(teacher.getJobNo());
        teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
        teacher.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("teacher");
        assertNotNull(role);
        teacher.setRole(role);

        assertTrue(teacherService.saveTeacher(teacher));
        assertNotNull(teacher.getId());
        Teacher newTeacher = teacherService.getTeacherById(teacher.getId());
        assertEquals(teacher, newTeacher);
        User newUser = userMapper.selectUserByUserName(teacher.getJobNo());
        User eUser = new User();
        eUser.setUserId(teacher.getUserId());
        eUser.setUsername(teacher.getUsername());
        eUser.setPassword(teacher.getPassword());
        eUser.setRole(teacher.getRole());
        eUser.setEnabled(teacher.getEnabled());
        assertEquals(eUser, newUser);
        assertTrue(new BCryptPasswordEncoder().matches(teacher.getUsername(), newUser.getPassword()));
    }

    /**
     * @Description: 获取所有教师
     * @return:
     * @Author: 李岩青
     * @Date:
     */
    @Test
    @Transactional
    @Rollback(true)
    void getAllTeachers() {
        List<Teacher> oldTeachers = teacherService.getAllTeachers();//通过方法获取已有教师的列表
        List<Teacher> teachers = new ArrayList<Teacher>();//新建教师列表
        for (int i = 0; i < 10; i++) {//通过循环新建十个教师对象并保存进数据库中
            Teacher teacher = new Teacher();
            teacher.setJobNo("ttest00" + i);//工号值由0到9
            teacher.setRealName("教师" + i);//姓名由0到9
            teacher.setContactNumber("1111111111" + i);//
            if (i % 2 == 0) {//一半男，网工
                teacher.setGender("男");
                teacher.setAge(30);
                teacher.setMajor("网络工程");
            } else {//一半女，计科
                teacher.setGender("女");
                teacher.setAge(40);
                teacher.setMajor("计算机科学与技术");
            }
            teacherService.saveTeacher(teacher);//保存教师
            teachers.add(teacher);//将该教师添加进新的教师列表
        }
        List<Teacher> allTeachers = teacherService.getAllTeachers();//循环完成后有10个新增现在再次查询所有教师的值是在原来的基础上又新增的所有教师
        int count = 0;//计数为0
        for (Teacher teacher : teachers) {//遍历新增的10个教师的列表
            for (int i = oldTeachers.size(); i < allTeachers.size(); i++) {//遍历第二次查询的所有教师，但以旧教师列表长度为起点，即只遍历新增的教师
                if (teacher.getId().longValue() == allTeachers.get(i).getId().longValue()) {//如果原来的教师id值和第二次查询出来的相等，则判断是否相等，如果相等则计数加1
                    assertEquals(teacher, allTeachers.get(i));
                    count++;
                }
            }
        }
        assertEquals(10, count);//如果全部遍历完成后计数值等于10，则说明查询成功
    }

    /**
     * @Description: 给出真实姓名值作为参数，通过realname模糊查找
     * @return:
     * @Author: 李岩青
     * @Date:
     */
    @Test
    @Transactional
    @Rollback(true)
    void selectTeachersByRealName() {
        List<Teacher> oldTeachers1 = teacherService.selectTeachersByRealName("教师");//通过姓名“教师”查询数据库中已有的教师
        List<Teacher> oldTeachers2 = teacherService.selectTeachersByRealName("教师5");//通过姓名“教师5”查询数据库中已有的教师
        List<Teacher> teachers = new ArrayList<Teacher>();//新建教师列表
        for (int i = 0; i < 10; i++) {//循环新建10个教师对象
            Teacher teacher = new Teacher();
            teacher.setJobNo("ttest00" + i);
            teacher.setRealName("教师" + i);
            teacher.setContactNumber("1111111111" + i);
            if (i % 2 == 0) {
                teacher.setGender("男");
                teacher.setAge(30);
                teacher.setMajor("网络工程");
            } else {
                teacher.setGender("女");
                teacher.setAge(40);
                teacher.setMajor("计算机科学与技术");
            }

            teacherService.saveTeacher(teacher);
            teachers.add(teacher);
        }
        List<Teacher> allTeachers1 = teacherService.selectTeachersByRealName("教师");//再次通过教师查询
        List<Teacher> allTeachers2 = teacherService.selectTeachersByRealName("教师5");//再次通过教师5查询
        int count1 = 0;//计数1
        int count2 = 0;//计数2
        for (Teacher teacher : teachers) {//对用“教师”查询的进行遍历，如果id值相等则计数加1
            for (int i = oldTeachers1.size(); i < allTeachers1.size(); i++) {
                if (teacher.getId().longValue() == allTeachers1.get(i).getId().longValue()) {
                    assertEquals(teacher, allTeachers1.get(i));
                    count1++;
                }
            }
            for (int i = oldTeachers2.size(); i < allTeachers2.size(); i++) {//对用“教师5”的进行遍历，如果相等则计数加1
                if (teacher.getId().longValue() == allTeachers2.get(i).getId().longValue()) {
                    assertEquals(teacher, allTeachers2.get(i));
                    count2++;
                }
            }
        }
        assertEquals(10, count1);//经过遍历含“教师”的模糊查询应该可以有10个对应相等
        assertEquals(1, count2);//经过遍历含“教师5”的模糊查询应该可以有1个对应相等

    }

    /**
     * @Description: 给出工号值作为参数进行查找，不为空则通过
     * @return:
     * @Author: 李岩青
     * @Date:
     */
    @Test
    @Transactional
    @Rollback(true)
    void selectTeacherByJobNo() {
        //新建教师对象
        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");
//下面会用到所以没有删除
        User user = (User) teacher;
        teacher.setUsername(teacher.getJobNo());
        teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
        teacher.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("teacher");
        assertNotNull(role);
        teacher.setRole(role);
//下面会用到所以没有删除
        assertTrue(teacherService.saveTeacher(teacher));//判断是否保存成功
        assertNotNull(teacher.getId());//判断是否返回了id
        //selectTeacherByJobNo
        Teacher newTeacher = teacherService.selectTeacherByJobNo(teacher.getJobNo());//通过工号查询出教师数据
        //selectTeacherByJobNo
        assertEquals(teacher, newTeacher);//判断原来的教师数据和查询出来的教师数据是否相等
        User newUser = userMapper.selectUserByUserName(teacher.getJobNo());//通过用户名（教师工号）查询用户
        User eUser = new User();//新建用户将原来的用户数据赋值给该用户
        eUser.setUserId(teacher.getUserId());
        eUser.setUsername(user.getUsername());
        eUser.setPassword(user.getPassword());
        eUser.setRole(user.getRole());
        eUser.setEnabled(user.getEnabled());
        assertEquals(eUser, newUser);//判断两个用户是否相等
        assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), newUser.getPassword()));//判断查询出的用户密码和原用户的用户名是否相等
    }

    /**
     * @Description: 通过id获取teacher，如果不为空则先删除对应userrole，删除不成功则抛出异常，
     * 然后删除teacher，如果不成功则抛出异常，然后删除user，不成功则抛出异常，如果通过id、获取到的teacher为空，则抛出异常
     * @return: result true或者false
     * @Author: 你的名字
     * @Date:
     */
    @Test
    @Transactional
    @Rollback(true)
    void deleteTeacher() {
        //新建教师对象
        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");

        teacher.setUsername(teacher.getJobNo());
        teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
        teacher.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("teacher");
        assertNotNull(role);
        teacher.setRole(role);
//保存教师对象
        assertTrue(teacherService.saveTeacher(teacher));
        assertNotNull(teacher.getId());
        Teacher newTeacher = teacherService.getTeacherById(teacher.getId());//通过id查询教师对象
        assertNotNull(newTeacher);//判断查询出的教师对象是否为空
        //deleteTeacher
        assertTrue(teacherService.deleteTeacher(teacher.getId()));//通过id删除该教师
        //deleteTeacher
        Teacher lastTeacher = teacherService.getTeacherById(teacher.getId());//再次通过id查询
        assertNull(lastTeacher);//判断是否为空，如果上次查询不为空此时为空则说明删除成功
    }

    @Test
    @Transactional
    @Rollback(true)
    void validateTeacher() {
        Teacher teacher = new Teacher();
//        工号不可为空 或者 必须为8位
        teacher.setJobNo("11111111");
//        姓名不可为空 或者 长度不可为0
        teacher.setRealName("111");
//        性别不可为空 或者 长度必须为1
        teacher.setGender("男");
//        年龄不可为空 或者 年龄不可小于0
        teacher.setAge(11);
//        电话不可为空 或者 长度必须为11位
        teacher.setContactNumber("11111111111");
//        课程不可为空 或者 长度不可为0
        teacher.setMajor("123");
//        id和userid必须为空
        boolean flag = teacherService.validateTeacher(teacher);
        assertTrue(flag);
    }
}