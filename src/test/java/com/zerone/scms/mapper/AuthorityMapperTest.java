package com.zerone.scms.mapper;

import com.zerone.scms.model.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorityMapperTest {
    @Autowired
    private AuthorityMapper authorityMapper;

    @Test
    @Transactional
    @Rollback
    void getAuthoritiesByRoleId() {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(null);

        Authority authority1 = new Authority();
        authority1.setAuthorityName("reset_user_psw");
        authority1.setAuthorityDescription("重置用户密码");
        authorities.add(authority1);

        Authority authority2 = new Authority();
        authority2.setAuthorityName("set_user_enabled");
        authority2.setAuthorityDescription("设置用户是否可用");
        authorities.add(authority2);

        Authority authority3 = new Authority();
        authority3.setAuthorityName("academic_write");
        authority3.setAuthorityDescription("教务员的CUD");
        authorities.add(authority3);

        Authority authority4 = new Authority();
        authority4.setAuthorityName("academic_read");
        authority4.setAuthorityDescription("教务员的R");
        authorities.add(authority4);

        Authority authority5 = new Authority();
        authority5.setAuthorityName("teacher_write");
        authority5.setAuthorityDescription("教师的CUD");
        authorities.add(authority5);

        Authority authority6 = new Authority();
        authority6.setAuthorityName("teacher_read");
        authority6.setAuthorityDescription("教师的R");
        authorities.add(authority6);

        Authority authority7 = new Authority();
        authority7.setAuthorityName("class_grade_write");
        authority7.setAuthorityDescription("班级的CUD");
        authorities.add(authority7);

        Authority authority8 = new Authority();
        authority8.setAuthorityName("class_grade_read");
        authority8.setAuthorityDescription("班级的R");
        authorities.add(authority8);

        Authority authority9 = new Authority();
        authority9.setAuthorityName("student_write");
        authority9.setAuthorityDescription("学生的CUD");
        authorities.add(authority9);

        Authority authority10 = new Authority();
        authority10.setAuthorityName("student_read");
        authority10.setAuthorityDescription("学生的R");
        authorities.add(authority10);

        Authority authority11 = new Authority();
        authority11.setAuthorityName("course_write");
        authority11.setAuthorityDescription("课程的CUD");
        authorities.add(authority11);

        Authority authority12 = new Authority();
        authority12.setAuthorityName("course_read");
        authority12.setAuthorityDescription("课程的R");
        authorities.add(authority12);

        Authority authority13 = new Authority();
        authority13.setAuthorityName("teaching_task_write");
        authority13.setAuthorityDescription("教学任务的CUD");
        authorities.add(authority13);

        Authority authority14 = new Authority();
        authority14.setAuthorityName("teaching_task_read");
        authority14.setAuthorityDescription("教学任务的R");
        authorities.add(authority14);

        Authority authority15 = new Authority();
        authority15.setAuthorityName("score_write");
        authority15.setAuthorityDescription("成绩的CUD");
        authorities.add(authority15);

        Authority authority16 = new Authority();
        authority16.setAuthorityName("score_read");
        authority16.setAuthorityDescription("成绩的R");
        authorities.add(authority16);

        Authority authority17 = new Authority();
        authority17.setAuthorityName("user_read");
        authority17.setAuthorityDescription("用户R");
        authorities.add(authority17);

        int isnum = 0;
        List<Authority> authorities1 = authorityMapper.getAuthoritiesByRoleId(1L);
        assertNotNull(authorities1);
        assertEquals(5, authorities1.size());
        Set<Integer> indexes1 = new HashSet<>();
        indexes1.add(1);
        indexes1.add(2);
        indexes1.add(3);
        indexes1.add(4);
        indexes1.add(17);
        for (int i : indexes1) {
            Authority authority = authorities.get(i);
            for (int j = 0; j < 5; j++) {
                Authority newAuthority = authorities1.get(j);
                if (newAuthority.getAuthorityName().equals(authority.getAuthorityName())) {
                    authority.setId(newAuthority.getId());
                    assertEquals(authority, newAuthority);
                    isnum++;
                }
            }
        }
        assertEquals(5, isnum);

//测试教务员权限
        List<Authority> authorities2 = authorityMapper.getAuthoritiesByRoleId(2L);
        assertNotNull(authorities2);
        assertEquals(12, authorities2.size());
        Set<Integer> indexes2 = new HashSet<Integer>();
        indexes2.add(4);
        indexes2.add(5);
        indexes2.add(6);
        indexes2.add(7);
        indexes2.add(8);
        indexes2.add(9);
        indexes2.add(10);
        indexes2.add(11);
        indexes2.add(12);
        indexes2.add(13);
        indexes2.add(14);
        indexes2.add(16);
        isnum = 0;
        for (int i : indexes2) {
            Authority authority = authorities.get(i);
            for (int j = 0; j < authorities2.size(); j++) {
                Authority newAuthority = authorities2.get(j);

                if (newAuthority.getAuthorityName().equals(authority.getAuthorityName())) {
                    authority.setId(newAuthority.getId());
                    assertEquals(authority, newAuthority);
                    isnum++;
                }
            }
        }
        assertEquals(12, isnum);

        //测试教师权限
        List<Authority> authorities3 = authorityMapper.getAuthoritiesByRoleId(3L);
        assertNotNull(authorities3);
        assertEquals(8, authorities3.size());
        Set<Integer> indexes3 = new HashSet<Integer>();
        indexes3.add(4);
        indexes3.add(6);
        indexes3.add(8);
        indexes3.add(10);
        indexes3.add(12);
        indexes3.add(14);
        indexes3.add(15);
        indexes3.add(16);
        isnum = 0;
        for (int i : indexes3) {
            Authority authority = authorities.get(i);
            for (int j = 0; j < authorities3.size(); j++) {
                Authority newAuthority = authorities3.get(j);
                if (newAuthority.getAuthorityName().equals(authority.getAuthorityName())) {
                    authority.setId(newAuthority.getId());
                    assertEquals(authority, newAuthority);
                    isnum++;
                }
            }
        }
        assertEquals(8, isnum);

        //测试学生权限
        List<Authority> authorities4 = authorityMapper.getAuthoritiesByRoleId(4L);
        assertNotNull(authorities4);
        assertEquals(7, authorities4.size());
        Set<Integer> indexes4 = new HashSet<Integer>();
        indexes4.add(4);
        indexes4.add(6);
        indexes4.add(8);
        indexes4.add(10);
        indexes4.add(12);
        indexes4.add(14);
        indexes4.add(16);
        indexes4.add(17);
        isnum = 0;
        for (int i : indexes4) {
            Authority authority = authorities.get(i);
            for (int j = 0; j < authorities4.size(); j++) {
                Authority newAuthority = authorities4.get(j);
                if (newAuthority.getAuthorityName().equals(authority.getAuthorityName())) {
                    authority.setId(newAuthority.getId());
                    assertEquals(authority, newAuthority);
                    isnum++;
                }
            }
        }
        assertEquals(7, isnum);

    }
}