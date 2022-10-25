package com.zerone.scms.mapper;

import com.zerone.scms.model.ClassAndGrade;
import com.zerone.scms.model.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {

    @Insert("INSERT INTO `scdb_scms`.`class_grades`\n" +
            "(`class_no`," +
            "`class_name`,\n" +
            "`grade`,\n" +
            "`major`)\n" +
            "VALUES\n" +
            "(#{classNo}," +
            "#{className},\n" +
            "#{grade},\n" +
            "#{major});")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "class_no", property = "classNo"),
            @Result(column = "class_name", property = "className"),
            @Result(column = "grade", property = "grade"),
            @Result(column = "major", property = "major")
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertClass(ClassAndGrade classAndGrade);

    @Update("UPDATE `scdb_scms`.`class_grades`\n" +
            "SET\n" +
            "`class_no` = #{classNo},\n" +
            "`class_name` = #{className},\n" +
            "`grade` = #{grade},\n" +
            "`major` = #{major}\n" +
            "WHERE `id` = #{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "class_no", property = "classNo"),
            @Result(column = "class_name", property = "className"),
            @Result(column = "grade", property = "grade"),
            @Result(column = "major", property = "major")
    })
    int updateClass(ClassAndGrade classAndGrade);

    @Delete("DELETE FROM `scdb_scms`.`class_grades`\n" +
            "WHERE id = #{id};")
    int deleteClass(Long id);

    @Select("SELECT `class_grades`.`id`,\n" +
            "    `class_grades`.`class_no`,\n" +
            "    `class_grades`.`class_name`,\n" +
            "    `class_grades`.`grade`,\n" +
            "    `class_grades`.`major`\n" +
            "FROM `scdb_scms`.`class_grades` \n" +
            "WHERE class_name LIKE '%' #{className} '%' ")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "class_no", property = "classNo"),
            @Result(column = "class_name", property = "className"),
            @Result(column = "grade", property = "grade"),
            @Result(column = "major", property = "major")
    })
    List<ClassAndGrade> selectClassesByClassName(String className);

    @Select("SELECT `class_grades`.`id`,\n" +
            "    `class_grades`.`class_no`,\n" +
            "    `class_grades`.`class_name`,\n" +
            "    `class_grades`.`grade`,\n" +
            "    `class_grades`.`major`\n" +
            "FROM `scdb_scms`.`class_grades`;")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "class_no", property = "classNo"),
            @Result(column = "class_name", property = "className"),
            @Result(column = "grade", property = "grade"),
            @Result(column = "major", property = "major")
    })
    List<ClassAndGrade> selectAllClasses();

    /**
     * @Description:按照id获取教师对象
     * @return:
     * @Author: 你的名字
     * @Date:
     */
    @Select("SELECT `class_grades`.`id`,\n" +
            "    `class_grades`.`class_no`,\n" +
            "    `class_grades`.`class_name`,\n" +
            "    `class_grades`.`grade`,\n" +
            "    `class_grades`.`major`\n" +
            "FROM `scdb_scms`.`class_grades`\n" +
            "WHERE `id`=#{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "class_no", property = "classNo"),
            @Result(column = "class_name", property = "className"),
            @Result(column = "grade", property = "grade"),
            @Result(column = "major", property = "major")
    })
    ClassAndGrade selectClassById(Long id);

    @Select("SELECT `class_grades`.`id`,\n" +
            "    `class_grades`.`class_no`,\n" +
            "    `class_grades`.`class_name`,\n" +
            "    `class_grades`.`grade`,\n" +
            "    `class_grades`.`major`\n" +
            "FROM `scdb_scms`.`class_grades`\n" +
            "WHERE `class_name`=#{className};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "class_no", property = "classNo"),
            @Result(column = "class_name", property = "className"),
            @Result(column = "grade", property = "grade"),
            @Result(column = "major", property = "major")
    })
    ClassAndGrade selectClassByClassNameOne(String className);
}
