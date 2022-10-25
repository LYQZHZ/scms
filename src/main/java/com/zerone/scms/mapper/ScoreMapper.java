package com.zerone.scms.mapper;

import com.zerone.scms.model.Score;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ScoreMapper {
    /**
     * 插入一个学生的一门课的成绩
     *
     * @param score
     * @return
     */
    @Insert("INSERT INTO `scdb_scms`.`scores`\n" +
            "(`courses_id`,\n" +
            "`students_id`,\n" +
            "`attendance_value`,\n" +
            "`homework_value`,\n" +
            "`quiz_value`,\n" +
            "`exam_value`)\n" +
            "VALUES\n" +
            "(#{course.id},\n" +
            "#{student.id},\n" +
            "#{attendanceValue},\n" +
            "#{homeworkValue},\n" +
            "#{quizValue},\n" +
            "#{examValue})"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "courses_id", property = "course.id"),
            @Result(column = "students_id", property = "student.id"),
            @Result(column = "attendance_value", property = "attendanceValue"),
            @Result(column = "homework_value", property = "homeworkValue"),
            @Result(column = "quiz_value", property = "quizValue"),
            @Result(column = "exam_value", property = "examValue")
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertScore(Score score);

    /**
     * 批量插入成绩
     *
     * @param scores
     * @return
     */
    @Insert("<script>" +
            "INSERT INTO `scdb_scms`.`scores`\n" +
            "(`courses_id`,\n" +
            "`students_id`,\n" +
            "`attendance_value`,\n" +
            "`homework_value`,\n" +
            "`quiz_value`,\n" +
            "`exam_value`)\n" +
            "VALUES\n" +
            "<foreach collection=\"scores\" item=\"score\" separator=\",\">" +
            "(#{score.course.id},\n" +
            "#{score.student.id},\n" +
            "#{score.attendanceValue},\n" +
            "#{score.homeworkValue},\n" +
            "#{score.quizValue},\n" +
            "#{score.examValue})" +
            "</foreach>" +
            "</script>"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "courses_id", property = "course.id"),
            @Result(column = "students_id", property = "student.id"),
            @Result(column = "attendance_value", property = "attendanceValue"),
            @Result(column = "homework_value", property = "homeworkValue"),
            @Result(column = "quiz_value", property = "quizValue"),
            @Result(column = "exam_value", property = "examValue")
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertScores(List<Score> scores);

    /**
     * 更新一条成绩
     *
     * @param score
     * @return
     */
    @Update("UPDATE `scdb_scms`.`scores`\n" +
            "SET\n" +
            "`courses_id` = #{course.id},\n" +
            "`students_id` = #{student.id},\n" +
            "`attendance_value` = #{attendanceValue},\n" +
            "`homework_value` = #{homeworkValue},\n" +
            "`quiz_value` = #{quizValue},\n" +
            "`exam_value` = #{examValue}\n" +
            "WHERE `id` = #{ id }")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "courses_id", property = "course.id"),
            @Result(column = "students_id", property = "student.id"),
            @Result(column = "attendance_value", property = "attendanceValue"),
            @Result(column = "homework_value", property = "homeworkValue"),
            @Result(column = "quiz_value", property = "quizValue"),
            @Result(column = "exam_value", property = "examValue")
    })
    int updateScore(Score score);

    /**
     * 删除一条成绩
     *
     * @param id
     * @return
     */
    @Delete("DELETE FROM `scdb_scms`.`scores`\n" +
            "WHERE `id` = #{ id }")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "courses_id", property = "course.id"),
            @Result(column = "students_id", property = "student.id"),
            @Result(column = "attendance_value", property = "attendanceValue"),
            @Result(column = "homework_value", property = "homeworkValue"),
            @Result(column = "quiz_value", property = "quizValue"),
            @Result(column = "exam_value", property = "examValue")
    })
    int deleteScore(Long id);

    /**
     * 按id查询成绩
     *
     * @param id
     * @return
     */
    @Select("SELECT `id`,\n" +
            "`courses_id`,\n" +
            "`students_id`,\n" +
            "`attendance_value`,\n" +
            "`homework_value`,\n" +
            "`quiz_value`,\n" +
            "`exam_value`\n" +
            "FROM `scdb_scms`.`scores`\n" +
            "WHERE `id` = #{ id }"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "attendance_value", property = "attendanceValue"),
            @Result(column = "homework_value", property = "homeworkValue"),
            @Result(column = "quiz_value", property = "quizValue"),
            @Result(column = "exam_value", property = "examValue"),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "students_id", property = "student",
                    one = @One(select = "com.zerone.scms.mapper.StudentMapper.selectStudentById", fetchType = FetchType.LAZY)
            )
    })
    Score selectScoreById(Long id);

    /**
     * 按课程Id查询某课程全部成绩
     *
     * @param coursId
     * @return
     */
    @Select("SELECT `id`,\n" +
            "`courses_id`,\n" +
            "`students_id`,\n" +
            "`attendance_value`,\n" +
            "`homework_value`,\n" +
            "`quiz_value`,\n" +
            "`exam_value`\n" +
            "FROM `scdb_scms`.`scores`\n" +
            "WHERE `courses_id` = #{ coursId }"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "attendance_value", property = "attendanceValue"),
            @Result(column = "homework_value", property = "homeworkValue"),
            @Result(column = "quiz_value", property = "quizValue"),
            @Result(column = "exam_value", property = "examValue"),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "students_id", property = "student",
                    one = @One(select = "com.zerone.scms.mapper.StudentMapper.selectStudentById", fetchType = FetchType.LAZY)
            )
    })
    List<Score> selectScoreByCourseId(Long coursId);

    /**
     * 按学生Id查询某学生全部课程的成绩
     *
     * @param studentId
     * @return
     */
    @Select("SELECT `id`,\n" +
            "`courses_id`,\n" +
            "`students_id`,\n" +
            "`attendance_value`,\n" +
            "`homework_value`,\n" +
            "`quiz_value`,\n" +
            "`exam_value`\n" +
            "FROM `scdb_scms`.`scores`\n" +
            "WHERE `students_id` = #{ studentId }"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "attendance_value", property = "attendanceValue"),
            @Result(column = "homework_value", property = "homeworkValue"),
            @Result(column = "quiz_value", property = "quizValue"),
            @Result(column = "exam_value", property = "examValue"),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "students_id", property = "student",
                    one = @One(select = "com.zerone.scms.mapper.StudentMapper.selectStudentById", fetchType = FetchType.LAZY)
            )
    })
    List<Score> selectScoreByStudentId(Long studentId);

    /**
     * 查询某学生某门课程的成绩
     *
     * @param studentId
     * @return
     */
    @Select("SELECT `id`,\n" +
            "`courses_id`,\n" +
            "`students_id`,\n" +
            "`attendance_value`,\n" +
            "`homework_value`,\n" +
            "`quiz_value`,\n" +
            "`exam_value`\n" +
            "FROM `scdb_scms`.`scores`\n" +
            "WHERE `students_id` = #{ studentId } AND `courses_id` = #{ coursId }"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "attendance_value", property = "attendanceValue"),
            @Result(column = "homework_value", property = "homeworkValue"),
            @Result(column = "quiz_value", property = "quizValue"),
            @Result(column = "exam_value", property = "examValue"),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "students_id", property = "student",
                    one = @One(select = "com.zerone.scms.mapper.StudentMapper.selectStudentById", fetchType = FetchType.LAZY)
            )
    })
    Score selectScoreByStudentIdAndCourseId(Long studentId, Long coursId);

    /**
     * 查询全部成绩
     *
     * @return
     */
    @Select("SELECT `id`,\n" +
            "`courses_id`,\n" +
            "`students_id`,\n" +
            "`attendance_value`,\n" +
            "`homework_value`,\n" +
            "`quiz_value`,\n" +
            "`exam_value`\n" +
            "FROM `scdb_scms`.`scores`"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "attendance_value", property = "attendanceValue"),
            @Result(column = "homework_value", property = "homeworkValue"),
            @Result(column = "quiz_value", property = "quizValue"),
            @Result(column = "exam_value", property = "examValue"),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "students_id", property = "student",
                    one = @One(select = "com.zerone.scms.mapper.StudentMapper.selectStudentById", fetchType = FetchType.LAZY)
            )
    })
    List<Score> selectAllScores();
}
