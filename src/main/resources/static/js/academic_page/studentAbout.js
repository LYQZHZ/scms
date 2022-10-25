$(document).ready(function () {
    $("#aboutStudent-btn").click(function () {
        let StudentId = $("#StudentId").val()
        let StudentSno = $("#StudentSno").val()
        let StudentRealName = $("#StudentRealName").val()
        let StudentGender
        StudentGender = $("input:radio:checked").val()
        let StudentAge = $("#StudentAge").val()
        let StudentContactNumber = $("#StudentContactNumber").val()
        let StudentMajor = $("#StudentMajor").val()
        let StudentUserId = $("#StudentUserId").val()
        if (!StudentSno || StudentSno.length != 11) {
            $("#error-message-Student").text("学号的长度为11个字符！");
            return
        }
        if (!StudentRealName || StudentRealName.length == 0) {
            $("#error-message-Student").text("必须填写性名！");
            return
        }
        if (StudentAge) {
            if (StudentAge < 0) {
                $("#error-message-Student").text("必须是正整数！")
                return
            }
        }
        if (StudentContactNumber) {
            if (StudentContactNumber.length != 11) {
                $("#error-message-Student").text("联系电话的长度为11！")
                return
            }
        }
        if (!StudentMajor || StudentMajor.length == 0) {
            $("#error-message-Student").text("必须选择班级！");
            return
        }
        let data = {}
        if (StudentId) {
            data = {
                id: StudentId,
                sno: StudentSno,
                realName: StudentRealName,
                gender: StudentGender,
                age: StudentAge,
                contactNumber: StudentContactNumber,
                major: StudentMajor,
                userId: StudentUserId
            }
        } else {
            data = {
                sno: StudentSno,
                realName: StudentRealName,
                gender: StudentGender,
                age: StudentAge,
                contactNumber: StudentContactNumber,
                major: StudentMajor
            }
        }
        $.post(
            "/student/save",
            data,
            function (data, status) {
                console.log(data)
                if (data == "OK") {
                    $("#error-message-Student").text("")
                    $("#ok-message-Student").text("数据保存成功！")
                    $("#aboutStudent-btn").hide();
                    $("#aboutStudent-close-btn").text("关闭");
                } else if (data == "VALIDATION_FAILED") {
                    $("#error-message-Student").text("学号为11个字符，其他项不能为空！")
                } else if (data == "DUPLICATE_ENTRY") {
                    $("#ok-message-Student").text("")
                    $("#error-message-Student").text("学号已经存在！")
                } else if (data == "RUN_ERROR") {
                    location.href = "/error"
                }
            });
    })

    $("#aboutStudent-close-btn").click(function () {
        if ($("#aboutStudent-close-btn").text() == "关闭") {
            $("#aboutStudent-close-btn").text("放弃");
            $("#aboutStudent-btn").show();
            location.href = "/student/page?menu=student&Loginer=" + $("#Loginer").val();
        }
    })
    $("#aboutStudent-times-btn").click(function () {
        if ($("#aboutStudent-close-btn").text() == "关闭") {
            $("#aboutStudent-close-btn").text("放弃");
            $("#aboutStudent-btn").show();
            location.href = "/student/page?menu=student&Loginer=" + $("#Loginer").val();
        }
    })
})
var editStudent = function (id) {
    $("#myModalLabelForStudent").text("编辑学生信息");
    $("#error-message-Student").text("")
    $("#error-ok-Student").text("")
    let sno = $("#" + id + "_sno_stu").text();
    let realName = $("#" + id + "_realName_stu").text();
    let gender = $("#" + id + "_gender_stu").text();
    let age = $("#" + id + "_age_stu").text();
    let contactNumber = $("#" + id + "_contactNumber_stu").text();
    let major = $("#" + id + "_major_stu").text();
    let userId = $("#" + id + "_userId_stu").val();

    $("#StudentId").val(id);
    $("#StudentSno").val(sno);
    $("#StudentRealName").val(realName);
    if (gender = "男") {
        $("#gender_male_stu").attr("checked", true);
    } else {
        $("#gender_female_stu").attr("checked", true);
    }
    $("#StudentAge").val(age);
    $("#StudentContactNumber").val(contactNumber);
    $("#StudentMajor").val(major);
    $("#StudentUserId").val(userId);
}

var newStudentBtn = function () {
    $("#myModalLabelForStudent").text("新建学生信息");
    $("#StudentId").val("");
    $("#StudentSno").val("");
    $("#StudentRealName").val("");
    $("#StudentGender").val("");
    $("#StudentAge").val("");
    $("#StudentContactNumber").val("");
    $("#StudentMajor").val("");
}

var deleteStudent = function (id) {
    location.replace("/login_success");
    $.post("/student/remove",
        {
            id: id
        },
        function (data, status) {
            if (data == "OK") {
                location.href = "/student/page?menu=student&Loginer=" + $("#Loginer").val();
            } else if (data = "VALIDATION_FAILED") {
                alert("要删除的学生不存在！")
            } else if (data = "RUN_ERROR") {
                location.href = "/error"
            }
        });
}