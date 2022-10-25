$(document).ready(function () {
    $("#aboutTeachingTask-btn").click(function () {
        let TeachingTaskId = $("#TeachingTaskId").val()
        let TeachingTaskTtname = $("#TeachingTaskTtname").val()
        let TeachingTaskClassGradeId = $("#TeachingTaskClassGradeId").val()
        let TeachingTaskCourseId = $("#TeachingTaskCourseId").val()
        let TeachingTaskTeacherId = $("#TeachingTaskTeacherId").val()
        let TeachingTaskSchoolYear = $("#TeachingTaskSchoolYear").val()
        let TeachingTaskSchoolTerm = $("#TeachingTaskSchoolTerm").val()
        if (!TeachingTaskTtname) {
            $("#error-message-TeachingTask").text("教学任务名不可为空！");
            return
        }
        if (!TeachingTaskClassGradeId || TeachingTaskClassGradeId.length == 0) {
            $("#error-message-TeachingTask").text("必须选择班级！");
            return
        }

        if (!TeachingTaskCourseId || TeachingTaskCourseId.length == 0) {
            $("#error-message-TeachingTask").text("必须选择课程！");
            return
        }

        if (!TeachingTaskTeacherId || TeachingTaskTeacherId.length == 0) {
            $("#error-message-TeachingTask").text("必须选择任课教师！");
            return
        }

        if (!TeachingTaskSchoolYear || TeachingTaskSchoolYear.length == 0) {
            $("#error-message-TeachingTask").text("学年不能为空！");
            return
        }
        if (!TeachingTaskSchoolTerm || TeachingTaskSchoolTerm.length == 0) {
            $("#error-message-TeachingTask").text("学期不能为空！");
            return
        }
        let data = {}
        if (StudentId) {
            data = {
                id: TeachingTaskId,
                ttname: TeachingTaskTtname,
                classGradeId: TeachingTaskClassGradeId,
                courseId: TeachingTaskCourseId,
                teacherId: TeachingTaskTeacherId,
                schoolYear: TeachingTaskSchoolYear,
                schoolTerm: TeachingTaskSchoolTerm
            }
        } else {
            data = {
                ttname: TeachingTaskTtname,
                classGradeId: TeachingTaskClassGradeId,
                courseId: TeachingTaskCourseId,
                teacherId: TeachingTaskTeacherId,
                schoolYear: TeachingTaskSchoolYear,
                schoolTerm: TeachingTaskSchoolTerm
            }
        }
        $.post(
            "/teachingTask/save",
            data,
            function (data, status) {
                console.log(data)
                if (data == "OK") {
                    $("#error-message-TeachingTask").text("")
                    $("#ok-message-TeachingTask").text("数据保存成功！")
                    $("#aboutTeachingTask-btn").hide();
                    $("#aboutTeachingTask-close-btn").text("关闭");
                } else if (data == "VALIDATION_FAILED") {
                    $("#error-message-TeachingTask").text("任何一项不能为空！")
                } else if (data == "DUPLICATE_ENTRY") {
                    $("#ok-message-TeachingTask").text("")
                    $("#error-message-TeachingTask").text("教学任务已经存在！")
                } else if (data == "RUN_ERROR") {
                    location.href = "/error"
                }
            });
    })

    $("#aboutTeachingTask-close-btn").click(function () {
        if ($("#aboutTeachingTask-close-btn").text() == "关闭") {
            $("#aboutTeachingTask-close-btn").text("放弃");
            $("#aboutTeachingTask-btn").show();
            location.href = "/teachingTask/page?menu=teachingTask&Loginer=" + $("#Loginer").val();
        }
    })
    $("#aboutTeachingTask-times-btn").click(function () {
        if ($("#aboutTeachingTask-close-btn").text() == "关闭") {
            $("#aboutTeachingTask-close-btn").text("放弃");
            $("#aboutTeachingTask-btn").show();
            location.href = "/teachingTask/page?menu=teachingTask&Loginer=" + $("#Loginer").val();
        }
    })
})
var editTeachingTask = function (id) {
    $("#myModalLabelForTeachingTask").text("编辑教学任务信息");
    $("#error-message-TeachingTask").text("")
    $("#error-ok-TeachingTask").text("")
    let ttname = $("#" + id + "_ttname_teachingTask").text();
    let className = $("#" + id + "_className_teachingTask").text();
    let cname = $("#" + id + "_cname_teachingTask").text();
    let realName = $("#" + id + "_realName_teachingTask").text();
    let schoolYear = $("#" + id + "_schoolYear_teachingTask").text();
    let schoolTerm = $("#" + id + "_schoolTerm_teachingTask").text();

    $("#TeachingTaskId").val(id);
    $("#TeachingTaskTtname").val(ttname);
    $("#TeachingTaskClassGradeId").val(className);
    $("#TeachingTaskCourseId").val(cname);
    $("#TeachingTaskTeacherId").val(realName);
    $("#TeachingTaskSchoolYear").val(schoolYear);
    $("#TeachingTaskSchoolTerm").val(schoolTerm);
}

var newTeachingTaskBtn = function () {
    $("#myModalLabelForTeachingTask").text("新建教学任务");
    $("#TeachingTaskId").val("");
    $("#TeachingTaskTtname").val("");
    $("#TeachingTaskClassGradeId").val("");
    $("#TeachingTaskCourseId").val("");
    $("#TeachingTaskTeacherId").val("");
    $("#TeachingTaskSchoolYear").val("");
    $("#TeachingTaskSchoolTerm").val("");
}

var deleteTeachingTask = function (id) {
    location.replace("/login_success");
    $.post("/teachingTask/remove",
        {
            id: id
        },
        function (data, status) {
            if (data == "OK") {
                location.href = "/teachingTask/page?menu=teachingTask&Loginer=" + $("#Loginer").val();
            } else if (data = "VALIDATION_FAILED") {
                alert("要删除的教学任务不存在！")
            } else if (data = "RUN_ERROR") {
                location.href = "/error"
            }
        });
}