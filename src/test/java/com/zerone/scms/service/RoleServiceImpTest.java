package com.zerone.scms.service;

import com.zerone.scms.mapper.RoleMapper;
import com.zerone.scms.model.Authority;
import com.zerone.scms.model.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleServiceImpTest {
    private static List<Authority> authorities;
    @Autowired
    private RoleService roleService;

    @BeforeAll
    static void beforAll() {
        authorities = new ArrayList<>();
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
    }

    @Test
    void queryRoleByRoleName() {
        Role aAdmin = roleService.queryRoleByRoleName("admin");
        Role eAdmin = new Role();
        eAdmin.setRoleName("admin");
        eAdmin.setRoleDescription("系统管理员");
        List<Authority> authoritiesBoutAdmin = new ArrayList<>();
        Set<Integer> indexes1 = new HashSet<Integer>();
        indexes1.add(1);
        indexes1.add(2);
        indexes1.add(3);
        indexes1.add(4);
        indexes1.add(17);
        for (int i : indexes1) {
            Authority authority = authorities.get(i);
            authoritiesBoutAdmin.add(authority);
        }
        aAdmin.setId(null);
        for (Authority authority : aAdmin.getAuthorities()) {
            authority.setId(null);
        }
        eAdmin.setAuthorities(authoritiesBoutAdmin);

        List<Authority> eAdminAuthorities = eAdmin.getAuthorities();
        List<Authority> aAdminAuthorities = aAdmin.getAuthorities();
        eAdmin.setAuthorities(null);
        aAdmin.setAuthorities(null);
        assertEquals(eAdmin, aAdmin);
        int adminCount = 0;
        for (Authority eAuthority : eAdminAuthorities) {
            for (Authority aAuthority : aAdminAuthorities) {
                if (aAuthority.getAuthorityName().equals(eAuthority.getAuthorityName())) {
                    assertEquals(eAuthority, eAuthority);
                    adminCount++;
                }
            }
        }
        assertEquals(5, adminCount);

        Role aAcademic = roleService.queryRoleByRoleName("academic");
        Role eAcademic = new Role();
        eAcademic.setRoleName("academic");
        eAcademic.setRoleDescription("教务管理员");
        eAcademic.setAuthorities(new ArrayList<Authority>());

        Set<Integer> indexes2 = new HashSet<Integer>();
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
        for (int i : indexes2) {
            Authority authority = authorities.get(i);
            eAcademic.getAuthorities().add(authority);
        }
        for (Authority authority : aAcademic.getAuthorities()) {
            authority.setId(null);
        }
        List<Authority> eAcademicAuthorities = eAcademic.getAuthorities();
        List<Authority> aAcademicAuthorities = aAcademic.getAuthorities();
        eAcademic.setAuthorities(null);
        aAcademic.setAuthorities(null);
        eAcademic.setId(aAcademic.getId());
        assertEquals(eAcademic, aAcademic);
        int academicCount = 0;
        for (Authority eAuthority : eAcademicAuthorities) {
            for (Authority aAuthority : aAcademicAuthorities) {
                if (aAuthority.getAuthorityName().equals(eAuthority.getAuthorityName())) {
                    assertEquals(eAuthority, eAuthority);
                    academicCount++;
                }
            }
        }
        assertEquals(11, academicCount);

        Role aTeacher = roleService.queryRoleByRoleName("teacher");
        Role eTeacher = new Role();
        eTeacher.setRoleName("teacher");
        eTeacher.setRoleDescription("教师");
        eTeacher.setAuthorities(new ArrayList<Authority>());

        Set<Integer> indexes3 = new HashSet<Integer>();
        indexes3.add(8);
        indexes3.add(10);
        indexes3.add(12);
        indexes3.add(14);
        indexes3.add(15);
        indexes3.add(16);
        for (int i : indexes3) {
            Authority authority = authorities.get(i);
            eTeacher.getAuthorities().add(authority);
        }
        for (Authority authority : aTeacher.getAuthorities()) {
            authority.setId(null);
        }
        List<Authority> eTeacherAuthorities = eTeacher.getAuthorities();
        List<Authority> aTeacherAuthorities = aTeacher.getAuthorities();
        eTeacher.setAuthorities(null);
        aTeacher.setAuthorities(null);
        eTeacher.setId(aTeacher.getId());
        assertEquals(eTeacher, aTeacher);
        int teacherCount = 0;
        for (Authority eAuthority : eTeacherAuthorities) {
            for (Authority aAuthority : aTeacherAuthorities) {
                if (aAuthority.getAuthorityName().equals(eAuthority.getAuthorityName())) {
                    assertEquals(eAuthority, eAuthority);
                    teacherCount++;
                }
            }
        }
        assertEquals(6, teacherCount);

        Role aStudent = roleService.queryRoleByRoleName("student");
        Role eStudent = new Role();
        eStudent.setRoleName("student");
        eStudent.setRoleDescription("学生");
        eStudent.setAuthorities(new ArrayList<Authority>());

        Set<Integer> indexes4 = new HashSet<Integer>();
        indexes4.add(16);
        for (int i : indexes4) {
            Authority authority = authorities.get(i);
            eStudent.getAuthorities().add(authority);
        }
        for (Authority authority : aStudent.getAuthorities()) {
            authority.setId(null);
        }
        List<Authority> eStudentAuthorities = eStudent.getAuthorities();
        List<Authority> aStudentAuthorities = aStudent.getAuthorities();
        eStudent.setAuthorities(null);
        aStudent.setAuthorities(null);
        eStudent.setId(aStudent.getId());
        assertEquals(eStudent, aStudent);
        int studentCount = 0;
        for (Authority eAuthority : eStudentAuthorities) {
            for (Authority aAuthority : aStudentAuthorities) {
                if (aAuthority.getAuthorityName().equals(eAuthority.getAuthorityName())) {
                    assertEquals(eAuthority, eAuthority);
                    studentCount++;
                }
            }
        }
        assertEquals(1, studentCount);
    }

    @Test
    void queryRoleByRoleId() {
        Role aAdmin = roleService.queryRoleByRoleId(1l);
        Role eAdmin = new Role();
        eAdmin.setRoleName("admin");
        eAdmin.setRoleDescription("系统管理员");
        eAdmin.setAuthorities(new ArrayList<Authority>());
        Set<Integer> indexes1 = new HashSet<Integer>();
        indexes1.add(1);
        indexes1.add(2);
        indexes1.add(3);
        indexes1.add(4);
        indexes1.add(17);

        for (int i : indexes1) {
            Authority authority = authorities.get(i);
            eAdmin.getAuthorities().add(authority);
        }
        for (Authority authority : aAdmin.getAuthorities()) {
            authority.setId(null);
        }
        List<Authority> eAdminAuthorities = eAdmin.getAuthorities();
        List<Authority> aAdminAuthorities = aAdmin.getAuthorities();
        eAdmin.setAuthorities(null);
        aAdmin.setAuthorities(null);
        eAdmin.setId(aAdmin.getId());
        assertEquals(eAdmin, aAdmin);
        int adminCount = 0;
        for (Authority eAuthority : eAdminAuthorities) {
            for (Authority aAuthority : aAdminAuthorities) {
                if (aAuthority.getAuthorityName().equals(eAuthority.getAuthorityName())) {
                    assertEquals(eAuthority, eAuthority);
                    adminCount++;
                }
            }
        }
        assertEquals(5, adminCount);

        Role aAcademic = roleService.queryRoleByRoleId(2L);
        Role eAcademic = new Role();
        eAcademic.setRoleName("academic");
        eAcademic.setRoleDescription("教务管理员");
        eAcademic.setAuthorities(new ArrayList<Authority>());

        Set<Integer> indexes2 = new HashSet<Integer>();
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
        for (int i : indexes2) {
            Authority authority = authorities.get(i);
            eAcademic.getAuthorities().add(authority);
        }
        for (Authority authority : aAcademic.getAuthorities()) {
            authority.setId(null);
        }
        List<Authority> eAcademicAuthorities = eAcademic.getAuthorities();
        List<Authority> aAcademicAuthorities = aAcademic.getAuthorities();
        eAcademic.setAuthorities(null);
        aAcademic.setAuthorities(null);
        eAcademic.setId(aAcademic.getId());
        assertEquals(eAcademic, aAcademic);
        int academicCount = 0;
        for (Authority eAuthority : eAcademicAuthorities) {
            for (Authority aAuthority : aAcademicAuthorities) {
                if (aAuthority.getAuthorityName().equals(eAuthority.getAuthorityName())) {
                    assertEquals(eAuthority, eAuthority);
                    academicCount++;
                }
            }
        }
        assertEquals(11, academicCount);

        Role aTeacher = roleService.queryRoleByRoleId(3L);
        Role eTeacher = new Role();
        eTeacher.setRoleName("teacher");
        eTeacher.setRoleDescription("教师");
        eTeacher.setAuthorities(new ArrayList<Authority>());

        Set<Integer> indexes3 = new HashSet<Integer>();
        indexes3.add(8);
        indexes3.add(10);
        indexes3.add(12);
        indexes3.add(14);
        indexes3.add(15);
        indexes3.add(16);
        for (int i : indexes3) {
            Authority authority = authorities.get(i);
            eTeacher.getAuthorities().add(authority);
        }
        for (Authority authority : aTeacher.getAuthorities()) {
            authority.setId(null);
        }
        List<Authority> eTeacherAuthorities = eTeacher.getAuthorities();
        List<Authority> aTeacherAuthorities = aTeacher.getAuthorities();
        eTeacher.setAuthorities(null);
        aTeacher.setAuthorities(null);
        eTeacher.setId(aTeacher.getId());
        assertEquals(eTeacher, aTeacher);
        int teacherCount = 0;
        for (Authority eAuthority : eTeacherAuthorities) {
            for (Authority aAuthority : aTeacherAuthorities) {
                if (aAuthority.getAuthorityName().equals(eAuthority.getAuthorityName())) {
                    assertEquals(eAuthority, eAuthority);
                    teacherCount++;
                }
            }
        }
        assertEquals(6, teacherCount);

        Role aStudent = roleService.queryRoleByRoleId(4L);
        Role eStudent = new Role();
        eStudent.setRoleName("student");
        eStudent.setRoleDescription("学生");
        eStudent.setAuthorities(new ArrayList<Authority>());

        Set<Integer> indexes4 = new HashSet<Integer>();
        indexes4.add(16);
        for (int i : indexes4) {
            Authority authority = authorities.get(i);
            eStudent.getAuthorities().add(authority);
        }
        for (Authority authority : aStudent.getAuthorities()) {
            authority.setId(null);
        }
        List<Authority> eStudentAuthorities = eStudent.getAuthorities();
        List<Authority> aStudentAuthorities = aStudent.getAuthorities();
        eStudent.setAuthorities(null);
        aStudent.setAuthorities(null);
        eStudent.setId(aStudent.getId());
        assertEquals(eStudent, aStudent);
        int studentCount = 0;
        for (Authority eAuthority : eStudentAuthorities) {
            for (Authority aAuthority : aStudentAuthorities) {
                if (aAuthority.getAuthorityName().equals(eAuthority.getAuthorityName())) {
                    assertEquals(eAuthority, eAuthority);
                    studentCount++;
                }
            }
        }
        assertEquals(1, studentCount);
    }
}