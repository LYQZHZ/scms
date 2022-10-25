package com.zerone.scms.mapper;

import com.zerone.scms.model.ClassAndGrade;
import com.zerone.scms.model.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Insert("INSERT INTO `scdb_scms`.`course`\n" +
            "(`cno`,\n" +
            "`cname`,\n" +
            "`period`,\n" +
            "`credit`)\n" +
            "VALUES\n" +
            "(#{cno},\n" +
            "#{cname},\n" +
            "#{period},\n" +
            "#{credit});")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "cno", property = "cno"),
            @Result(column = "cname", property = "cname"),
            @Result(column = "period", property = "period"),
            @Result(column = "credit", property = "credit")
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCourse(Course course);

    @Update("UPDATE `scdb_scms`.`course`\n" +
            "SET\n" +
            "`cno` = #{cno},\n" +
            "`cname` = #{cname},\n" +
            "`period` = #{period},\n" +
            "`credit` = #{credit}\n" +
            "WHERE `id` = #{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "cno", property = "cno"),
            @Result(column = "cname", property = "cname"),
            @Result(column = "period", property = "period"),
            @Result(column = "credit", property = "credit")
    })
    int updateCourse(Course course);

    @Delete("DELETE FROM `scdb_scms`.`course`\n" +
            "WHERE id = #{id};")
    int deleteCourse(Long id);

    @Select("SELECT `course`.`id`,\n" +
            "    `course`.`cno`,\n" +
            "    `course`.`cname`,\n" +
            "    `course`.`period`,\n" +
            "    `course`.`credit`\n" +
            "FROM `scdb_scms`.`course` \n" +
            "WHERE cname LIKE '%' #{cname} '%' ")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "cno", property = "cno"),
            @Result(column = "cname", property = "cname"),
            @Result(column = "period", property = "period"),
            @Result(column = "credit", property = "credit")
    })
    List<Course> selectCoursesByCourseName(String className);

    @Select("SELECT `course`.`id`,\n" +
            "    `course`.`cno`,\n" +
            "    `course`.`cname`,\n" +
            "    `course`.`period`,\n" +
            "    `course`.`credit`\n" +
            "FROM `scdb_scms`.`course`;")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "cno", property = "cno"),
            @Result(column = "cname", property = "cname"),
            @Result(column = "period", property = "period"),
            @Result(column = "credit", property = "credit")
    })
    List<Course> selectAllCourses();

    /**
     * @Description:按照id获取教师对象
     * @return:
     * @Author: 你的名字
     * @Date:
     */
    @Select("SELECT `course`.`id`,\n" +
            "    `course`.`cno`,\n" +
            "    `course`.`cname`,\n" +
            "    `course`.`period`,\n" +
            "    `course`.`credit`\n" +
            "FROM `scdb_scms`.`course`\n" +
            "WHERE `id`=#{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "cno", property = "cno"),
            @Result(column = "cname", property = "cname"),
            @Result(column = "period", property = "period"),
            @Result(column = "credit", property = "credit")
    })
    Course selectCourseById(Long id);

    @Select("SELECT `course`.`id`,\n" +
            "    `course`.`cno`,\n" +
            "    `course`.`cname`,\n" +
            "    `course`.`period`,\n" +
            "    `course`.`credit`\n" +
            "FROM `scdb_scms`.`course`\n" +
            "WHERE `cno`=#{cno};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "cno", property = "cno"),
            @Result(column = "cname", property = "cname"),
            @Result(column = "period", property = "period"),
            @Result(column = "credit", property = "credit")
    })
    Course selectCourseByCno(String cno);
}
