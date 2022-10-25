package com.zerone.scms.mapper;

import com.zerone.scms.model.Student;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface StudentMapper {


    @Insert("INSERT INTO `scdb_scms`.`students`\n" +
            "(`sno`,\n" +
            "`real_name`,\n" +
            "`gender`,\n" +
            "`age`,\n" +
            "`contact_number`,\n" +
            "`class_grades_id`,\n" +
            "`users_id`)\n" +
            "VALUES\n" +
            "(#{sno},\n" +
            "#{realName},\n" +
            "#{gender},\n" +
            "#{age},\n" +
            "#{contactNumber},\n" +
            "#{classAndGrade.id},\n" +
            "#{userId});")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "sno", property = "sno"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "class_grades_id", property = "classAndGrade.id"),
            @Result(column = "users_id", property = "userId"),

    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertStudent(Student student);

    @Update("UPDATE `scdb_scms`.`students`\n" +
            "SET\n" +
            "`sno` = #{sno},\n" +
            "`real_name` = #{realName},\n" +
            "`gender` = #{gender},\n" +
            "`age` = #{age},\n" +
            "`contact_number` = #{contactNumber},\n" +
            "`class_grades_id` = #{classAndGrade.id}\n" +
            "WHERE `id` = #{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "sno", property = "sno"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "class_grades_id", property = "classAndGrade.id"),
            @Result(column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY))
    })
    int updateStudent(Student student);

    @Delete("DELETE FROM `scdb_scms`.`students`\n" +
            "WHERE id = #{id};")
    int deleteStudent(Long id);

    @Select("SELECT `students`.`id`,\n" +
            "    `students`.`sno`,\n" +
            "    `students`.`real_name`,\n" +
            "    `students`.`gender`,\n" +
            "    `students`.`age`,\n" +
            "    `students`.`contact_number`,\n" +
            "    `students`.`class_grades_id`,\n" +
            "    `students`.`users_id`\n" +
            "FROM `scdb_scms`.`students` \n" +
            "WHERE real_name LIKE '%' #{realname} '%' ")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "sno", property = "sno"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "class_grades_id", property = "classAndGrade.id"),
            @Result(column = "users_id", property = "userId"),
            @Result(column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY))
    })
    List<Student> selectStudentsByRealName(String realname);

    @Select("SELECT `students`.`id`,\n" +
            "    `students`.`sno`,\n" +
            "    `students`.`real_name`,\n" +
            "    `students`.`gender`,\n" +
            "    `students`.`age`,\n" +
            "    `students`.`contact_number`,\n" +
            "    `students`.`class_grades_id`,\n" +
            "    `students`.`users_id`\n" +
            "FROM `scdb_scms`.`students`;")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "sno", property = "sno"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "class_grades_id", property = "classAndGrade.id"),
            @Result(column = "users_id", property = "userId"),
            @Result(column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY))
    })
    List<Student> selectAllStudents();

    /**
     * @Description:按照id获取学生对象
     * @return:
     * @Author: 你的名字
     * @Date:
     */
    @Select("SELECT `students`.`id`,\n" +
            "    `students`.`sno`,\n" +
            "    `students`.`real_name`,\n" +
            "    `students`.`gender`,\n" +
            "    `students`.`age`,\n" +
            "    `students`.`contact_number`,\n" +
            "    `students`.`class_grades_id`,\n" +
            "    `students`.`users_id`\n" +
            "FROM `scdb_scms`.`students`\n" +
            "WHERE `id`=#{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "sno", property = "sno"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "class_grades_id", property = "classAndGrade.id"),
            @Result(column = "users_id", property = "userId"),
            @Result(column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY))
    })
    Student selectStudentById(Long id);

    @Select("SELECT `students`.`id`,\n" +
            "    `students`.`sno`,\n" +
            "    `students`.`real_name`,\n" +
            "    `students`.`gender`,\n" +
            "    `students`.`age`,\n" +
            "    `students`.`contact_number`,\n" +
            "    `students`.`class_grades_id`,\n" +
            "    `students`.`users_id`\n" +
            "FROM `scdb_scms`.`students`\n" +
            "WHERE `sno`=#{sno};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "sno", property = "sno"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "class_grades_id", property = "classAndGrade.id"),
            @Result(column = "users_id", property = "userId"),
            @Result(column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY))
    })
    Student selectStudentBySno(String sno);

    @Insert("<script>" +
            "INSERT INTO `scdb_scms`.`students`\n" +
            "(`sno`,\n" +
            "`real_name`,\n" +
            "`gender`,\n" +
            "`age`,\n" +
            "`contact_number`,\n" +
            "`class_grades_id`,\n" +
            "`users_id`)\n" +
            "VALUES\n" +
            "<foreach collection=\"students\" item=\"student\" separator=\",\">" +
            "(#{student.sno},\n" +
            "#{student.realName},\n" +
            "#{student.gender},\n" +
            "#{student.age},\n" +
            "#{student.contactNumber},\n" +
            "#{student.classAndGrade.id},\n" +
            "#{student.userId})" +
            "</foreach>" +
            "</script>")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "sno", property = "sno"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "class_grades_id", property = "classAndGrade.id"),
            @Result(column = "users_id", property = "userId"),
            @Result(column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY))

    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertStudents(List<Student> students);

    @Select("SELECT `students`.`id`,\n" +
            "    `students`.`sno`,\n" +
            "    `students`.`real_name`,\n" +
            "    `students`.`gender`,\n" +
            "    `students`.`age`,\n" +
            "    `students`.`contact_number`,\n" +
            "    `students`.`class_grades_id`,\n" +
            "    `students`.`users_id`\n" +
            "FROM `scdb_scms`.`students` \n" +
            "WHERE class_grades_id =#{id};")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "sno", property = "sno"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "gender", property = "gender"),
            @Result(column = "age", property = "age"),
            @Result(column = "contact_number", property = "contactNumber"),
            @Result(column = "class_grades_id", property = "classAndGrade.id"),
            @Result(column = "users_id", property = "userId"),
            @Result(column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY))
    })
    List<Student> selectStudentsByClassGradeId(Long id);
}
