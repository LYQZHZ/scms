package com.zerone.scms.controller;

import com.zerone.scms.model.Academic;
import com.zerone.scms.model.User;
import com.zerone.scms.service.AcademicService;
import com.zerone.scms.service.UserService;
import com.zerone.scms.util.ResponseConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/academic")
public class AcademicController {
    private Logger logger = LoggerFactory.getLogger(AcademicController.class);
    @Autowired
    private AcademicService academicService;
    @Autowired
    private UserService userService;

//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('query_academic')")
//    public String getAllAcademics() {
//        return "academic_page";
//    }
//
//    @RequestMapping(value = "/part", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('query_academic')")
//    public String getPartAcademics() {
//        return "academic_page";
//    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    @PostAuthorize("hasAuthority('academic_write')")//Post和Pre的区别post如果该请求没有相应授权，不会返回相应视图，但是代码会正常执行
    public String saveAcademic(ExtendedModelMap model, Academic academic) {
        String result = ResponseConstant.OK;
        try {
            if (academicService.validateAcademic(academic)) {
                if (academic.getId() == null) {
                    if (academicService.selectAcademicByJobNo(academic.getJobNo()) != null) {
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                } else {
                    Academic oldAcademic = academicService.selectAcademicByJobNo(academic.getJobNo());
                    if (oldAcademic != null && !oldAcademic.getId().equals(academic.getId())) {
                        System.out.println("DUPLICATE_ENTRY2");
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                }
            } else {
                result = ResponseConstant.VALIDATION_FAILED;
            }
            if (result.equals(ResponseConstant.OK)) {
                academicService.saveAcademic(academic);
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = ResponseConstant.RUN_ERROR;
        }
        return result;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @PostAuthorize("hasAuthority('academic_read')")
    public String getAdminPage(String menu, Model model) {
        String result = "admin_page";
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("userList", users);
            List<Academic> academics = academicService.getAllAcademics();
            model.addAttribute("academics", academics);
            model.addAttribute("Loginer", "Admin");
            model.addAttribute("pageType", menu);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @PostAuthorize("hasAuthority('academic_read')")
    public String queryAcademicByRealName(String realname, Model model) {
        String result = "admin_page";
        try {
            List<Academic> academics = null;
            if (realname == null || realname.length() == 0) {
                academics = academicService.getAllAcademics();
            } else {
                academics = academicService.queryAcademicsByRealName(realname);
            }
            List<User> users = userService.getAllUsers();
            model.addAttribute("academics", academics);
            model.addAttribute("userList", users);
            model.addAttribute("Loginer", "admin");
            model.addAttribute("pageType", "academic");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @PostAuthorize("hasAuthority('academic_write')")
    @ResponseBody
    public String removeAcademic(Long id) {
        String result = ResponseConstant.OK;
        try {
            if (id != null) {
                if (academicService.getAcademicById(id) != null) {
                    academicService.removeAcademic(id);
                } else {
                    result = ResponseConstant.VALIDATION_FAILED;
                }
            } else {
                result = ResponseConstant.VALIDATION_FAILED;
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = ResponseConstant.RUN_ERROR;
        }
        return result;
    }

}
