package com.zerone.scms.mapper;

import com.zerone.scms.model.TeachingTask;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface TeachingTaskMapper {

    @Insert("INSERT INTO `scdb_scms`.`teaching_task`\n" +
            "(`ttname`,\n" +
            "`class_grades_id`,\n" +
            "`courses_id`,\n" +
            "`teachers_id`,\n" +
            "`begin_date`,\n" +
            "`end_date`)\n" +
            "VALUES\n" +
            "(#{ttname},\n" +
            "#{classAndGrade.id},\n" +
            "#{course.id},\n" +
            "#{teacher.id},\n" +
            "#{beginDate},\n" +
            "#{endDate})"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ttname", property = "ttname"),
            @Result(column = "begin_date", property = "beginDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(
                    column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "teachers_id", property = "teacher",
                    one = @One(select = "com.zerone.scms.mapper.TeacherMapper.selectTeacherById", fetchType = FetchType.LAZY)
            )
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertTeachingTask(TeachingTask teachingTask);

    @Update("UPDATE `scdb_scms`.`teaching_task`\n" +
            "SET\n" +
            "`ttname` = #{ttname},\n" +
            "`class_grades_id` = #{classAndGrade.id},\n" +
            "`courses_id` = #{course.id},\n" +
            "`teachers_id` = #{teacher.id},\n" +
            "`begin_date` = #{beginDate},\n" +
            "`end_date` = #{endDate}\n" +
            "WHERE `id` = #{id}"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ttname", property = "ttname"),
            @Result(column = "begin_date", property = "beginDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(
                    column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "teachers_id", property = "teacher",
                    one = @One(select = "com.zerone.scms.mapper.TeacherMapper.selectTeacherById", fetchType = FetchType.LAZY)
            )
    })
    int updateTeachingTask(TeachingTask teachingTask);

    @Delete("DELETE FROM `scdb_scms`.`teaching_task`\n" +
            "WHERE `id` = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ttname", property = "ttName"),
            @Result(column = "begin_date", property = "beginDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(
                    column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassGradeMapper.selectClassGradeById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "teachers_id", property = "teacher",
                    one = @One(select = "com.zerone.scms.mapper.TeacherMapper.selectTeacherById", fetchType = FetchType.LAZY)
            )
    })
    int deleteTeachingTask(Long id);

    @Select("SELECT `id`,\n" +
            "`ttname`,\n" +
            "`class_grades_id`,\n" +
            "`courses_id`,\n" +
            "`teachers_id`,\n" +
            "`begin_date`,\n" +
            "`end_date`\n" +
            "FROM `scdb_scms`.`teaching_task`"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ttname", property = "ttname"),
            @Result(column = "begin_date", property = "beginDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(
                    column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "teachers_id", property = "teacher",
                    one = @One(select = "com.zerone.scms.mapper.TeacherMapper.selectTeacherById", fetchType = FetchType.LAZY)
            )
    })
    List<TeachingTask> selectAllTeachingTasks();

    @Select("SELECT `id`,\n" +
            "`ttname`,\n" +
            "`class_grades_id`,\n" +
            "`courses_id`,\n" +
            "`teachers_id`,\n" +
            "`begin_date`,\n" +
            "`end_date`\n" +
            "FROM `scdb_scms`.`teaching_task`\n" +
            "WHERE `id` = #{id}"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ttname", property = "ttname"),
            @Result(column = "begin_date", property = "beginDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(
                    column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "teachers_id", property = "teacher",
                    one = @One(select = "com.zerone.scms.mapper.TeacherMapper.selectTeacherById", fetchType = FetchType.LAZY)
            )
    })
    TeachingTask selectTeachingTaskById(Long id);

    @Select("SELECT `id`,\n" +
            "`ttname`,\n" +
            "`class_grades_id`,\n" +
            "`courses_id`,\n" +
            "`teachers_id`,\n" +
            "`begin_date`,\n" +
            "`end_date`\n" +
            "FROM `scdb_scms`.`teaching_task`\n" +
            "WHERE `ttname`  LIKE '%' #{ttName} '%'"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ttname", property = "ttname"),
            @Result(column = "begin_date", property = "beginDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(
                    column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "teachers_id", property = "teacher",
                    one = @One(select = "com.zerone.scms.mapper.TeacherMapper.selectTeacherById", fetchType = FetchType.LAZY)
            )
    })
    List<TeachingTask> selectTeachingTasksByName(String ttName);

    @Select("SELECT `id`,\n" +
            "`ttname`,\n" +
            "`class_grades_id`,\n" +
            "`courses_id`,\n" +
            "`teachers_id`,\n" +
            "`begin_date`,\n" +
            "`end_date`\n" +
            "FROM `scdb_scms`.`teaching_task`\n" +
            "WHERE `ttname` = #{ttname}"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ttname", property = "ttname"),
            @Result(column = "begin_date", property = "beginDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(
                    column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassGradeMapper.selectClassById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "teachers_id", property = "teacher",
                    one = @One(select = "com.zerone.scms.mapper.TeacherMapper.selectTeacherById", fetchType = FetchType.LAZY)
            )
    })
    TeachingTask selectTeachingTaskByTtName(String ttname);

    @Select("SELECT `id`,\n" +
            "`ttname`,\n" +
            "`class_grades_id`,\n" +
            "`courses_id`,\n" +
            "`teachers_id`,\n" +
            "`begin_date`,\n" +
            "`end_date`\n" +
            "FROM `scdb_scms`.`teaching_task`\n" +
            "WHERE `courses_id` = #{courseId}"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ttname", property = "ttname"),
            @Result(column = "begin_date", property = "beginDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(
                    column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "teachers_id", property = "teacher",
                    one = @One(select = "com.zerone.scms.mapper.TeacherMapper.selectTeacherById", fetchType = FetchType.LAZY)
            )
    })
    List<TeachingTask> selectTeachingTasksByCourseId(Long courseId);

    @Select("SELECT `id`,\n" +
            "`ttname`,\n" +
            "`class_grades_id`,\n" +
            "`courses_id`,\n" +
            "`teachers_id`,\n" +
            "`begin_date`,\n" +
            "`end_date`\n" +
            "FROM `scdb_scms`.`teaching_task`\n" +
            "WHERE `teachers_id` = #{teacherId}"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ttname", property = "ttname"),
            @Result(column = "begin_date", property = "beginDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(
                    column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "teachers_id", property = "teacher",
                    one = @One(select = "com.zerone.scms.mapper.TeacherMapper.selectTeacherById", fetchType = FetchType.LAZY)
            )
    })
    List<TeachingTask> selectTeachingTasksByTeacherId(Long teacherId);

    @Select("SELECT `id`,\n" +
            "`ttname`,\n" +
            "`class_grades_id`,\n" +
            "`courses_id`,\n" +
            "`teachers_id`,\n" +
            "`begin_date`,\n" +
            "`end_date`\n" +
            "FROM `scdb_scms`.`teaching_task`\n" +
            "WHERE `class_grades_id` = #{classGradeId}"
    )
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "ttname", property = "ttname"),
            @Result(column = "begin_date", property = "beginDate"),
            @Result(column = "end_date", property = "endDate"),
            @Result(
                    column = "class_grades_id", property = "classAndGrade",
                    one = @One(select = "com.zerone.scms.mapper.ClassMapper.selectClassById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "courses_id", property = "course",
                    one = @One(select = "com.zerone.scms.mapper.CourseMapper.selectCourseById", fetchType = FetchType.LAZY)
            ),
            @Result(
                    column = "teachers_id", property = "teacher",
                    one = @One(select = "com.zerone.scms.mapper.TeacherMapper.selectTeacherById", fetchType = FetchType.LAZY)
            )
    })
    List<TeachingTask> selectTeachingTasksByClassGradeId(Long classGradeId);
}
