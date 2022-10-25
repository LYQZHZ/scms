package com.zerone.scms.controller;

import com.zerone.scms.model.Academic;
import com.zerone.scms.model.User;
import com.zerone.scms.service.AcademicService;
import com.zerone.scms.service.UserService;
import com.zerone.scms.util.ResponseConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.attribute.standard.PresentationDirection;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private AcademicService academicService;

    @RequestMapping(value = "/modipsw", method = RequestMethod.POST)
    @ResponseBody //等价于在类名前加@RestController，会使此方法直接返回字符串到客户端，而不是返回视图，但这样就可以实现对类中得某一个方法使其返回字符串，而不是所有。
    @PreAuthorize("#username == authentication.principal.username")  //只有登录者才能修改自己密码
    public String modifyPassword(@RequestBody String username, String oldpassword, String newpassword) {

        String result = ResponseConstant.NOK;
        if (username != null && username.length() > 0 && oldpassword != null && oldpassword.length() > 0 && newpassword != null && newpassword.length() > 0) {
            if (newpassword.length() >= 6 && newpassword.length() <= 20) {
                try {
                    if (userService.modifyUserPassword(username, oldpassword, newpassword)) {
                        result = ResponseConstant.OK;
                    }
                } catch (RuntimeException e) {
                    result = ResponseConstant.RUN_ERROR;
                    logger.error(e.getMessage());
                }
            } else {
                result = ResponseConstant.VALIDATION_FAILED;
            }
        } else {
            result = ResponseConstant.VALIDATION_FAILED;
        }

        return result;
    }

    ;

    @RequestMapping(value = "/initpsw", method = RequestMethod.POST)
    @ResponseBody //等价于在类名前加@RestController，会使此方法直接返回字符串到客户端，而不是返回视图，但这样就可以实现对类中得某一个方法使其返回字符串，而不是所有。
    @PreAuthorize("hasAuthority('reset_user_psw')")
    public String initPassword(Long userId) {
        String result = ResponseConstant.NOK;
        if (userId != null) {
            try {
                if (userService.initPassword(userId)) {
                    result = ResponseConstant.OK;
                }
            } catch (RuntimeException e) {
                result = ResponseConstant.RUN_ERROR;
                logger.error(e.getMessage());
            }
        } else {
            result = ResponseConstant.VALIDATION_FAILED;
        }

        return result;
    }

    @RequestMapping(value = "/changestatus", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('set_user_enabled')")
    public String enabledOrDisabledUser(ExtendedModelMap model, Long userId) {
        String result = ResponseConstant.NOK;
        if (userId != null) {
            try {
                if (userService.enabledOrDisabledUser(userId)) {
                    List<User> users = userService.getAllUsers();
                    model.addAttribute("userList", users);
                    model.addAttribute("pageType", "user");
                    result = ResponseConstant.OK;
                }
            } catch (RuntimeException e) {
                result = ResponseConstant.RUN_ERROR;
                logger.error(e.getMessage());
            }
        } else {
            result = ResponseConstant.VALIDATION_FAILED;
        }
        return result;
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('user_read')")
    public String queryUserByUserName(ExtendedModelMap model, String username) {
        String result = "admin_page";
        List<User> users = null;
        List<Academic> academics = null;
        if (null == username || username.length() == 0) {
            try {
                users = userService.getAllUsers();
                academics = academicService.getAllAcademics();

            } catch (RuntimeException e) {
                users = new ArrayList<>();
                academics = new ArrayList<>();
                logger.error(e.getMessage());
                result = "error";
            }
        } else {
            try {
                users = userService.queryUsersByUserName(username);
                academics = academicService.getAllAcademics();
            } catch (RuntimeException e) {
                users = new ArrayList<>();
                academics = new ArrayList<>();
                logger.error(e.getMessage());
                result = "error";
            }

        }
        model.addAttribute("userList", users);
        model.addAttribute("academics", academics);
        model.addAttribute("Loginer", "admin");
        model.addAttribute("pageType", "user");

        return result;
    }
}
