$(document).ready(function () {
    $("#aboutCourse-btn").click(function () {
        let CourseId = $("#CourseId").val()
        let CourseCno = $("#CourseCno").val()
        let CourseCname = $("#CourseCname").val()
        let CoursePeriod = $("#CoursePeriod").val()
        let CourseCredit = $("#CourseCredit").val()
        if (!CourseCno || CourseCno.length != 10) {
            $("#error-message-Course").text("课程号必须为10位！");
            return
        }
        if (!CourseCname || CourseCname.length == 0) {
            $("#error-message-Course").text("必须填写课程名！");
            return
        }
        if (!CoursePeriod) {
            $("#error-message-Course").text("必须填写学时！");
            return
        }
        if (!CourseCredit) {
            $("#error-message-Course").text("必须填写学分！");
            return
        }

        let data = {}
        if (CourseId) {
            data = {
                id: CourseId,
                cno: CourseCno,
                cname: CourseCname,
                period: CoursePeriod,
                credit: CourseCredit
            }
        } else {
            data = {
                cno: CourseCno,
                cname: CourseCname,
                period: CoursePeriod,
                credit: CourseCredit
            }
        }
        $.post(
            "/course/save",
            data,
            function (data, status) {
                console.log(data)
                if (data == "OK") {
                    $("#error-message-Course").text("")
                    $("#ok-message-Course").text("数据保存成功！")
                    $("#aboutCourse-btn").hide();
                    $("#aboutCourse-close-btn").text("关闭");
                } else if (data == "VALIDATION_FAILED") {
                    $("#error-message-Course").text("各项不能为空！")
                } else if (data == "DUPLICATE_ENTRY") {
                    $("#ok-message-Course").text("")
                    $("#error-message-Course").text("课程号已经存在！")
                } else if (data == "RUN_ERROR") {
                    location.href = "/error"
                }
            });
    })
    $("#aboutCourse-close-btn").click(function () {
        if ($("#aboutCourse-close-btn").text() == "关闭") {
            $("#aboutCourse-close-btn").text("放弃");
            $("#aboutCourse-btn").show();
            location.href = "/course/page?menu=course&Loginer=" + $("#Loginer").val();
        }
    })
    $("#aboutCourse-times-btn").click(function () {
        if ($("#aboutCourse-close-btn").text() == "关闭") {
            $("#aboutCourse-close-btn").text("放弃");
            $("#aboutCourse-btn").show();
            location.href = "/course/page?menu=course&Loginer=" + $("#Loginer").val();
        }
    })
})


var editCourse = function (id) {
    $("#myModalLabelForCourse").text("编辑课程信息");
    $("#error-message-Course").text("")
    $("#error-ok-Course").text("")
    let cno = $("#" + id + "_cno_course").text();
    let cname = $("#" + id + "_cname_course").text();
    let period = $("#" + id + "_period_course").text();
    let credit = $("#" + id + "_credit_course").text();
    $("#CourseId").val(id);
    $("#CourseCno").val(cno);
    $("#CourseCname").val(cname);
    $("#CoursePeriod").val(period);
    $("#CourseCredit").val(credit);
}

var newCourseButton = function () {
    $("#myModalLabelForCourse").text("新建课程信息");
    $("#CourseId").val("");
    $("#CourseCno").val("");
    $("#CourseCname").val("");
    $("#CoursePeriod").val("");
    $("#CourseCredit").val("");
}

var deleteCourse = function (id) {
    location.replace("/login_success");
    $.post("/course/remove",
        {
            id: id
        },
        function (data, status) {
            if (data == "OK") {
                location.href = "/course/page?menu=course&Loginer=" + $("#Loginer").val();
            } else if (data = "VALIDATION_FAILED") {
                alert("要删除的课程不存在！")
            } else if (data = "RUN_ERROR") {
                location.href = "/error"
            }
        });
}