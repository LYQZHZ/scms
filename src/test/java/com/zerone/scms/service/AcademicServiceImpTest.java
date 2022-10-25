package com.zerone.scms.service;

import com.zerone.scms.mapper.RoleMapper;
import com.zerone.scms.mapper.UserMapper;
import com.zerone.scms.model.Academic;
import com.zerone.scms.model.Role;
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
class AcademicServiceImpTest {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;
    @Autowired
    AcademicService academicService;

    @Test
    @Transactional
    @Rollback
    void saveAcademic() {
        Academic academic = new Academic();
        academic.setRealName("教务员");
        academic.setJobNo("atest00");
        academic.setGender("男");
        academic.setAge(20);
        academic.setContactNumber("1111111111");
        assertTrue(academicService.saveAcademic(academic));
        assertNotNull(academic.getId());
        assertNotNull(academic.getUserId());
        Academic newAcademic = academicService.getAcademicById(academic.getId());
        assertEquals(academic, newAcademic);
        User user = userMapper.selectUserByUserName(academic.getJobNo());
        assertEquals(academic.getJobNo(), user.getUsername());
        assertTrue(user.getEnabled());
        assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), user.getPassword()));

        academic.setRealName("李教务");
        academic.setContactNumber("2222222222");
        academic.setGender("女");
        academic.setAge(21);
        assertTrue(academicService.saveAcademic(newAcademic));
        Academic lastAcademic = academicService.getAcademicById(academic.getId());
        assertEquals(newAcademic, lastAcademic);
    }

    @Test
    @Transactional
    @Rollback
    void getAcademicById() {
        Academic academic = new Academic();
        academic.setRealName("张教务");
        academic.setJobNo("a0000001");
        academic.setGender("男");
        academic.setAge(20);
        academic.setContactNumber("11111111111");

        academic.setUsername(academic.getJobNo());
        academic.setPassword(new BCryptPasswordEncoder().encode(academic.getJobNo()));
        academic.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        academic.setRole(role);

        academic.setUserId(academic.getUserId());
        assertTrue(academicService.saveAcademic(academic));
        assertNotNull(academic.getId());
        Academic newAcademic = academicService.getAcademicById(academic.getId());
        assertEquals(academic, newAcademic);
    }

    @Test
    @Transactional
    @Rollback
    void getAllAcademics() {
        List<Academic> oldAcademics = academicService.getAllAcademics();
        List<Academic> academics = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Academic academic = new Academic();
            academic.setRealName("教务员" + i);
            academic.setJobNo("atest00" + i);
            academic.setGender("男");
            academic.setAge(20);
            academic.setContactNumber("1111111111" + i);

            academicService.saveAcademic(academic);
            academics.add(academic);
        }
        List<Academic> allAcademics = academicService.getAllAcademics();
        int count = 0;
        for (Academic academic : academics) {
            for (int i = oldAcademics.size(); i < allAcademics.size(); i++) {
                if (academic.getId().longValue() == allAcademics.get(i).getId().longValue()) {
                    assertEquals(academic, allAcademics.get(i));
                    count++;
                }
            }
        }
        assertEquals(10, count);
    }

    @Test
    @Transactional
    @Rollback
    void queryAcademicsByRealName() {
        List<Academic> oldAcademics = academicService.queryAcademicsByRealName("教务员");
        List<User> oldUsers = userService.queryUsersByUserName("atest00");
        List<Academic> oldAcademics5 = academicService.queryAcademicsByRealName("5");
        List<User> oldUsers5 = userService.queryUsersByUserName("5");
        List<Academic> academics = new ArrayList<Academic>();
        for (int i = 0; i < 10; i++) {
            Academic academic = new Academic();
            academic.setRealName("教务员" + i);
            academic.setJobNo("atest00" + i);
            academic.setGender("男");
            academic.setAge(20);
            academic.setContactNumber("1111111111" + i);
            academicService.saveAcademic(academic);
            academics.add(academic);
        }
        List<Academic> allAcademics = academicService.queryAcademicsByRealName("教务员");
        List<User> allUsers = userService.queryUsersByUserName("atest00");
        List<Academic> allAcademics5 = academicService.queryAcademicsByRealName("5");
        List<User> allUsers5 = userService.queryUsersByUserName("5");
        int academicCount = 0;
        int userCount = 0;
        int academicCount2 = 0;
        int userCount2 = 0;
        for (Academic academic : academics) {
            for (int i = oldAcademics.size(); i < allAcademics.size(); i++) {
                if (academic.getId().longValue() == allAcademics.get(i).getId().longValue()) {
                    assertEquals(academic, allAcademics.get(i));
                    academicCount++;
                }
            }
            for (int i = oldUsers.size(); i < allUsers.size(); i++) {
                if (academic.getJobNo().equals(allUsers.get(i).getUsername())) {
                    assertTrue(allUsers.get(i).getEnabled());
                    assertEquals(academic.getUserId(), allUsers.get(i).getUserId());
                    assertTrue(new BCryptPasswordEncoder().matches(academic.getJobNo(), allUsers.get(i).getPassword()));
                    userCount++;
                }
            }
            for (int i = oldAcademics5.size(); i < allAcademics5.size(); i++) {
                if (academic.getId().longValue() == allAcademics.get(i).getId().longValue()) {
                    assertEquals(academic, allAcademics.get(i));
                    academicCount2++;
                }
            }
            for (int i = oldUsers5.size(); i < allUsers5.size(); i++) {
                if (academic.getJobNo().equals(allUsers.get(i).getUsername())) {
                    assertTrue(allUsers.get(i).getEnabled());
                    assertEquals(academic.getUserId(), allUsers.get(i).getUserId());
                    assertTrue(new BCryptPasswordEncoder().matches(academic.getJobNo(), allUsers.get(i).getPassword()));
                    userCount2++;
                }
            }
        }
        assertEquals(10, academicCount);
        assertEquals(10, userCount);
        assertEquals(1, academicCount2);
        assertEquals(1, userCount2);
    }

    @Test
    @Transactional
    @Rollback
    void removeAcademic() {
        Academic academic = new Academic();
        academic.setRealName("张教务");
        academic.setJobNo("a0000001");
        academic.setGender("男");
        academic.setAge(20);
        academic.setContactNumber("11111111111");

        academicService.saveAcademic(academic);
        Academic newAcademic = academicService.getAcademicById(academic.getId());
        assertNotNull(newAcademic);
        assertTrue(academicService.removeAcademic(newAcademic.getId()));
        Academic lastAcademic = academicService.getAcademicById(academic.getId());
        assertNull(lastAcademic);

    }

    @Test
    @Transactional
    @Rollback
    void validateAcademic() {
    }

    @Test
    @Transactional
    @Rollback
    void selectAcademicByJobNo() {
        Academic academic = new Academic();
        academic.setRealName("张教务");
        academic.setJobNo("a0000001");
        academic.setGender("男");
        academic.setAge(20);
        academic.setContactNumber("11111111111");

        assertTrue(academicService.saveAcademic(academic));
        assertNotNull(academic.getId());
        Academic newAcademic = academicService.selectAcademicByJobNo(academic.getJobNo());
        assertEquals(academic, newAcademic);
    }
}