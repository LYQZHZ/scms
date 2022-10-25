package com.zerone.scms.mapper;

import com.zerone.scms.model.Academic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AcademicMapper {
    @Insert("INSERT INTO `scdb_scms`.`academics`\n" +
            "(`job_no`,\n" +
            "`real_name`,\n" +
            "`gender`,\n" +
            "`age`,\n" +
            "`contact_number`,\n" +
            "`users_id`)\n" +
            "VALUES\n" +
            "(#{jobNo},\n" +
            "#{realName},\n" +
            "#{gender},\n" +
            "#{age},\n" +
            "#{contactNumber},\n" +
            "#{userId});")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "users_id", property = "userId")
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertAcademic(Academic academic);

    /**
     * @Description:按照id获取用户对象
     * @return:
     * @Author: 你的名字
     * @Date:
     */
    @Select("SELECT `academics`.`id`,\n" +
            "    `academics`.`job_no`,\n" +
            "    `academics`.`real_name`,\n" +
            "    `academics`.`gender`,\n" +
            "    `academics`.`age`,\n" +
            "    `academics`.`contact_number`,\n" +
            "    `academics`.`users_id`\n" +
            "FROM `scdb_scms`.`academics`\n" +
            "WHERE `id`=#{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "users_id", property = "userId")
    })
    Academic selectAcademicById(Long id);

    /**
     * @Description:获取所有教务员
     * @return: 所有教务员对象的list
     * @Author: 你的名字
     * @Date:
     */
    @Select("SELECT `academics`.`id`,\n" +
            "    `academics`.`job_no`,\n" +
            "    `academics`.`real_name`,\n" +
            "    `academics`.`gender`,\n" +
            "    `academics`.`age`,\n" +
            "    `academics`.`contact_number`,\n" +
            "    `academics`.`users_id`\n" +
            "FROM `scdb_scms`.`academics`;")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "users_id", property = "userId")
    })
    List<Academic> selectAllAcademics();

    @Select("SELECT `academics`.`id`,\n" +
            "    `academics`.`job_no`,\n" +
            "    `academics`.`real_name`,\n" +
            "    `academics`.`gender`,\n" +
            "    `academics`.`age`,\n" +
            "    `academics`.`contact_number`,\n" +
            "    `academics`.`users_id`\n" +
            "FROM `scdb_scms`.`academics` \n" +
            "WHERE real_name LIKE '%' #{realname} '%' ")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "users_id", property = "userId")
    })
    List<Academic> selectAcademicsByRealName(String realname);


    /**
     * @Description:更新教务员
     * @return: 影响的行数
     * @Author: 你的名字
     * @Date: Academic
     */
    @Update("UPDATE `scdb_scms`.`academics`\n" +
            "SET\n" +
            "`job_no` = #{jobNo},\n" +
            "`real_name` = #{realName},\n" +
            "`gender` = #{gender},\n" +
            "`age` = #{age},\n" +
            "`contact_number` = #{contactNumber}\n" +
            "WHERE `id` = #{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber")
    })
    Long updateAcademic(Academic academic);

    @Delete("DELETE FROM `scdb_scms`.`academics`\n" +
            "WHERE id = #{id};")
    Long deleteAcademicById(Long id);

    @Select("SELECT `academics`.`id`,\n" +
            "    `academics`.`job_no`,\n" +
            "    `academics`.`real_name`,\n" +
            "    `academics`.`gender`,\n" +
            "    `academics`.`age`,\n" +
            "    `academics`.`contact_number`,\n" +
            "    `academics`.`users_id`\n" +
            "FROM `scdb_scms`.`academics`\n" +
            "WHERE job_no = #{jobNo}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "users_id", property = "userId")
    })
    Academic selectAcademicByJobNo(String jobNo);
}
