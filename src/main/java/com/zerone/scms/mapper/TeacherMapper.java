package com.zerone.scms.mapper;

import com.zerone.scms.model.Teacher;
import com.zerone.scms.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {

    @Insert("INSERT INTO `scdb_scms`.`teachers`\n" +
            "(`job_no`,\n" +
            "`real_name`,\n" +
            "`gender`,\n" +
            "`age`,\n" +
            "`contact_number`,\n" +
            "`major`,\n" +
            "`users_id`)\n" +
            "VALUES\n" +
            "(#{jobNo},\n" +
            "#{realName},\n" +
            "#{gender},\n" +
            "#{age},\n" +
            "#{contactNumber},\n" +
            "#{major},\n" +
            "#{userId});")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "major", property = "major"),
            @Result(column = "users_id", property = "userId")
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertTeacher(Teacher teacher);

    @Update("UPDATE `scdb_scms`.`teachers`\n" +
            "SET\n" +
            "`job_no` = #{jobNo},\n" +
            "`real_name` = #{realName},\n" +
            "`gender` = #{gender},\n" +
            "`age` = #{age},\n" +
            "`contact_number` = #{contactNumber},\n" +
            "`major` = #{major}\n" +
            "WHERE `id` = #{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "major", property = "major")
    })
    int updateTeacher(Teacher teacher);

    @Delete("DELETE FROM `scdb_scms`.`teachers`\n" +
            "WHERE id = #{id};")
    int deleteTeacher(Long id);

    @Select("SELECT `teachers`.`id`,\n" +
            "    `teachers`.`job_no`,\n" +
            "    `teachers`.`real_name`,\n" +
            "    `teachers`.`gender`,\n" +
            "    `teachers`.`age`,\n" +
            "    `teachers`.`contact_number`,\n" +
            "    `teachers`.`major`,\n" +
            "    `teachers`.`users_id`\n" +
            "FROM `scdb_scms`.`teachers` \n" +
            "WHERE real_name LIKE '%' #{realname} '%' ")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "major", property = "major"),
            @Result(column = "users_id", property = "userId")
    })
    List<Teacher> selectTeachersByRealName(String realname);

    @Select("SELECT `teachers`.`id`,\n" +
            "    `teachers`.`job_no`,\n" +
            "    `teachers`.`real_name`,\n" +
            "    `teachers`.`gender`,\n" +
            "    `teachers`.`age`,\n" +
            "    `teachers`.`contact_number`,\n" +
            "    `teachers`.`major`,\n" +
            "    `teachers`.`users_id`\n" +
            "FROM `scdb_scms`.`teachers`;")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "major", property = "major"),
            @Result(column = "users_id", property = "userId")
    })
    List<Teacher> selectAllTeachers();

    /**
     * @Description:按照id获取教师对象
     * @return:
     * @Author: 你的名字
     * @Date:
     */
    @Select("SELECT `teachers`.`id`,\n" +
            "    `teachers`.`job_no`,\n" +
            "    `teachers`.`real_name`,\n" +
            "    `teachers`.`gender`,\n" +
            "    `teachers`.`age`,\n" +
            "    `teachers`.`contact_number`,\n" +
            "    `teachers`.`major`,\n" +
            "    `teachers`.`users_id`\n" +
            "FROM `scdb_scms`.`teachers`\n" +
            "WHERE `id`=#{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "major", property = "major"),
            @Result(column = "users_id", property = "userId")
    })
    Teacher selectTeacherById(Long id);

    @Select("SELECT `teachers`.`id`,\n" +
            "    `teachers`.`job_no`,\n" +
            "    `teachers`.`real_name`,\n" +
            "    `teachers`.`gender`,\n" +
            "    `teachers`.`age`,\n" +
            "    `teachers`.`contact_number`,\n" +
            "    `teachers`.`major`,\n" +
            "    `teachers`.`users_id`\n" +
            "FROM `scdb_scms`.`teachers`\n" +
            "WHERE `job_no`=#{jobNo};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "job_no", property = "jobNo"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "major", property = "major"),
            @Result(column = "users_id", property = "userId")
    })
    Teacher selectTeacherByJobNo(String jobNo);
}
