package com.zerone.scms.service;

import com.zerone.scms.mapper.AcademicMapper;
import com.zerone.scms.mapper.UserMapper;
import com.zerone.scms.mapper.UserRoleMapper;
import com.zerone.scms.model.Academic;
import com.zerone.scms.model.Role;
import com.zerone.scms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("academicService")
public class AcademicServiceImp implements AcademicService {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    AcademicMapper academicMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public Boolean saveAcademic(Academic academic) {

        Boolean result = true;
        Long id = academic.getId();
        if (null == id) {
            academic.setUsername(academic.getJobNo());
            academic.setPassword(new BCryptPasswordEncoder().encode(academic.getJobNo()));
            academic.setEnabled(true);
            Role role = roleService.queryRoleByRoleName("academic");
            academic.setRole(role);
            if (!userService.saveUser(academic)) {
                result = false;
                throw new RuntimeException("用户信息保存失败1！");
            }

            academic.setUserId(academic.getUserId());
            if (1 != academicMapper.insertAcademic(academic)) {
                result = false;
                throw new RuntimeException("教务员信息保存失败！");
            }

        } else {
            if (academicMapper.updateAcademic(academic) == 1) {
                User user = userMapper.selectUserByUserId(academic.getUserId());
                user.setUsername(academic.getJobNo());
                user.setPassword(new BCryptPasswordEncoder().encode(academic.getJobNo()));
                if (userMapper.updateUser(user) != 1) {
                    result = false;
                    throw new RuntimeException("用户信息保存失败2！");
                }

            } else {
                result = false;
                throw new RuntimeException("教务员信息保存失败！");
            }
        }
        return result;
    }

    @Override
    public Academic getAcademicById(Long id) {
        return academicMapper.selectAcademicById(id);
    }

    @Override
    public List<Academic> getAllAcademics() {
        return academicMapper.selectAllAcademics();
    }

    @Override
    public List<Academic> queryAcademicsByRealName(String realname) {
        return academicMapper.selectAcademicsByRealName(realname);
    }

    @Override
    @Transactional
    public Boolean removeAcademic(Long id) {
        Boolean result = true;
        Academic academic = academicMapper.selectAcademicById(id);
        if (academic != null) {

            if (academicMapper.deleteAcademicById(id) != 1) {
                result = false;
                throw new RuntimeException("删除教务员失败！");
            }
            User user = userMapper.selectUserByUserId(academic.getUserId());
            if (userMapper.deleteUserByUserId(academic.getUserId()) != 1) {
                result = false;
                throw new RuntimeException("删除教务员对应用户失败！");
            }
        } else {
            result = false;
            throw new RuntimeException("删除的教务员不存在！");
        }
        return result;
    }

    @Override
    public boolean validateAcademic(Academic academic) {
        boolean result = true;
        if (academic.getJobNo() == null || academic.getJobNo().length() != 8) {
            result = false;
        }
        if (academic.getRealName() == null || academic.getRealName().length() == 0) {
            result = false;
        }
        if (academic.getGender() == null || academic.getGender().length() != 1) {
            result = false;
        }
        if (academic.getAge() == null || academic.getAge() < 0) {
            result = false;
        }
        if (academic.getContactNumber() == null || academic.getContactNumber().length() != 11) {
            result = false;
        }
        if (academic.getId() != null) {
            if (academic.getUserId() == null) {
                result = false;
            }
        } else {
            if (academic.getUserId() != null) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public Academic selectAcademicByJobNo(String jobNo) {
        return academicMapper.selectAcademicByJobNo(jobNo);
    }

}
