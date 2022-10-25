package com.zerone.scms.mapper;

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
class AcademicMapperTest {
    @Autowired
    private AcademicMapper academicMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    @Transactional
    @Rollback
    void insertAcademic() {
        Academic academic = new Academic();
        academic.setRealName("张教务");
        academic.setJobNo("atest001");
        academic.setGender("男");
        academic.setAge(20);
        academic.setContactNumber("11111111111");

        academic.setUsername(academic.getJobNo());
        academic.setPassword(new BCryptPasswordEncoder().encode(academic.getJobNo()));
        academic.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        academic.setRole(role);
        assertEquals(1, userMapper.insertUser(academic));
        assertNotNull(academic.getUserId());
        assertEquals(1, academicMapper.insertAcademic(academic));
        assertNotNull(academic.getId());
        Academic newAcademic = academicMapper.selectAcademicById(academic.getId());
        assertEquals(academic, newAcademic);
    }

    @Test
    @Transactional
    @Rollback
    void updateAcademic() {
        Academic academic = new Academic();
        academic.setRealName("张教务");
        academic.setJobNo("atest001");
        academic.setGender("男");
        academic.setAge(20);
        academic.setContactNumber("11111111111");

        academic.setUsername(academic.getJobNo());
        academic.setPassword(new BCryptPasswordEncoder().encode(academic.getJobNo()));
        academic.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        academic.setRole(role);
        userMapper.insertUser(academic);
        academicMapper.insertAcademic(academic);
        Academic newAcademic = academicMapper.selectAcademicById(academic.getId());
        assertEquals(academic, newAcademic);
        newAcademic.setUsername("张小志");
        newAcademic.setAge(30);
        newAcademic.setGender("女");
        newAcademic.setContactNumber("22222222222");
        assertEquals(1, academicMapper.updateAcademic(newAcademic));
        Academic lastAcademic = academicMapper.selectAcademicById(academic.getId());
        assertEquals(newAcademic, lastAcademic);
    }

    @Test
    @Transactional
    @Rollback
    void deleteAcademic() {
        Academic academic = new Academic();
        academic.setRealName("张教务");
        academic.setJobNo("atest001");
        academic.setGender("男");
        academic.setAge(20);
        academic.setContactNumber("11111111111");

        academic.setUsername(academic.getJobNo());
        academic.setPassword(new BCryptPasswordEncoder().encode(academic.getJobNo()));
        academic.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        academic.setRole(role);
        userMapper.insertUser(academic);
        academicMapper.insertAcademic(academic);
        Academic newAcademic = academicMapper.selectAcademicById(academic.getId());
        assertNotNull(newAcademic);
        assertEquals(1, academicMapper.deleteAcademicById(newAcademic.getId()));
        Academic lastAcademic = academicMapper.selectAcademicById(academic.getId());
        assertNull(lastAcademic);
    }

    @Test
    @Transactional
    @Rollback
    void selectAcademicsByRealName() {

        List<Academic> oldAcademics1 = academicMapper.selectAcademicsByRealName("教务员");
        List<Academic> oldAcademics2 = academicMapper.selectAcademicsByRealName("5");
        List<Academic> academics = new ArrayList<>();
        Role role = roleMapper.getRoleByRoleName("academic");
        for (int i = 0; i < 10; i++) {
            Academic academic = new Academic();
            academic.setRealName("教务员" + i);
            academic.setJobNo("atest00" + i);
            academic.setGender("男");
            academic.setAge(20);
            academic.setContactNumber("1111111111" + i);

            academic.setUsername(academic.getJobNo());
            academic.setPassword(new BCryptPasswordEncoder().encode(academic.getJobNo()));
            academic.setEnabled(true);
            academic.setRole(role);
            userMapper.insertUser(academic);

            academicMapper.insertAcademic(academic);
            academics.add(academic);
        }
        List<Academic> allAcademics1 = academicMapper.selectAcademicsByRealName("教务员");
        List<Academic> allAcademics2 = academicMapper.selectAcademicsByRealName("5");
        int count1 = 0;
        int count2 = 0;
        for (Academic academic : academics) {
            for (int i = oldAcademics1.size(); i < allAcademics1.size(); i++) {
                if (academic.getId().longValue() == allAcademics1.get(i).getId().longValue()) {
                    assertEquals(academic, allAcademics1.get(i));
                    count1++;
                }
            }
            for (int i = oldAcademics2.size(); i < allAcademics2.size(); i++) {
                if (academic.getId().longValue() == allAcademics2.get(i).getId().longValue()) {
                    assertEquals(academic, allAcademics2.get(i));
                    count2++;
                }
            }
        }
        assertEquals(10, count1);
        assertEquals(1, count2);

        assertEquals(oldAcademics2.size(), allAcademics2.size() - 1);
        Academic academic = allAcademics2.get(oldAcademics1.size());
        assertEquals(academics.get(5), academic);
    }

    @Test
    @Transactional
    @Rollback
    void selectAllAcademics() {
        List<Academic> oldAcademics = academicMapper.selectAllAcademics();
        List<Academic> academics = new ArrayList<>();
        Role role = roleMapper.getRoleByRoleName("academic");
        for (int i = 0; i < 10; i++) {
            Academic academic = new Academic();
            academic.setRealName("教务员" + i);
            academic.setJobNo("atest00" + i);
            academic.setGender("男");
            academic.setAge(20);
            academic.setContactNumber("1111111111" + i);

            academic.setUsername(academic.getJobNo());
            academic.setPassword(new BCryptPasswordEncoder().encode(academic.getJobNo()));
            academic.setEnabled(true);
            academic.setRole(role);
            userMapper.insertUser(academic);
            academicMapper.insertAcademic(academic);
            academics.add(academic);
        }
        List<Academic> allAcademics = academicMapper.selectAllAcademics();
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
    void selectAcademicById() {
        Academic academic = new Academic();
        academic.setRealName("张教务");
        academic.setJobNo("a0000001");
        academic.setGender("男");
        academic.setAge(20);
        academic.setContactNumber("11111111111");

        User user = academic;
        user.setUsername(academic.getJobNo());
        user.setPassword(new BCryptPasswordEncoder().encode(academic.getJobNo()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));

        academic.setUserId(user.getUserId());
        assertEquals(1, academicMapper.insertAcademic(academic));
        assertNotNull(academic.getId());
        Academic newAcademic = academicMapper.selectAcademicById(academic.getId());
        assertEquals(academic, newAcademic);
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

        User user = academic;
        user.setUsername(academic.getJobNo());
        user.setPassword(new BCryptPasswordEncoder().encode(academic.getJobNo()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));

        academic.setUserId(user.getUserId());
        assertEquals(1, academicMapper.insertAcademic(academic));
        assertNotNull(academic.getId());
        Academic newAcademic = academicMapper.selectAcademicByJobNo(academic.getJobNo());
        assertEquals(academic, newAcademic);
    }
}