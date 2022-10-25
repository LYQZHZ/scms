package com.zerone.scms.service;

import com.zerone.scms.model.Academic;
import com.zerone.scms.model.User;

import java.util.List;

public interface AcademicService {
    Boolean saveAcademic(Academic academic);

    Academic getAcademicById(Long id);

    List<Academic> getAllAcademics();

    List<Academic> queryAcademicsByRealName(String realname);

    Boolean removeAcademic(Long id);

    /**
     * @Description:验证数据是否符合要求，符合返回真，不符合返回假
     * @return:
     * @Author: 李岩青
     * @Date:
     */
    boolean validateAcademic(Academic academic);

    Academic selectAcademicByJobNo(String jobNo);
}
