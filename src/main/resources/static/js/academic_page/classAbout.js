$(document).ready(function () {
    $("#aboutClass-btn").click(function () {
        let ClassId = $("#ClassId").val()
        let ClassClassName = $("#ClassClassName").val()
        let ClassGrade = $("#ClassGrade").val()
        let ClassMajor = $("#ClassMajor").val()
        if (!ClassClassName || ClassClassName.length == 0) {
            $("#error-message-Class").text("必须填写班级名！");
            return
        }
        if (!ClassGrade || ClassGrade.length == 0) {
            $("#error-message-Class").text("必须填写年级！");
            return
        }
        if (!ClassMajor || ClassMajor.length == 0) {
            $("#error-message-Class").text("必须填写专业！");
            return
        }

        let data = {}
        if (ClassId) {
            data = {
                id: ClassId,
                className: ClassClassName,
                grade: ClassGrade,
                major: ClassMajor
            }
        } else {
            data = {
                className: ClassClassName,
                grade: ClassGrade,
                major: ClassMajor
            }
        }
        $.post(
            "/class/save",
            data,
            function (data, status) {
                console.log(data)
                if (data == "OK") {
                    $("#error-message-Class").text("")
                    $("#ok-message-Class").text("数据保存成功！")
                    $("#aboutClass-btn").hide();
                    $("#aboutClass-close-btn").text("关闭");
                } else if (data == "VALIDATION_FAILED") {
                    $("#error-message-Class").text("各项不能为空！")
                } else if (data == "DUPLICATE_ENTRY") {
                    $("#ok-message-Class").text("")
                    $("#error-message-Class").text("班级名已经存在！")
                } else if (data == "RUN_ERROR") {
                    location.href = "/error"
                }
            });
    })
    $("#aboutClass-close-btn").click(function () {
        if ($("#aboutClass-close-btn").text() == "关闭") {
            $("#aboutClass-close-btn").text("放弃");
            $("#aboutClass-btn").show();
            location.href = "/class/page?menu=class&Loginer=" + $("#Loginer").val();
        }
    })
    $("#aboutClass-times-btn").click(function () {
        if ($("#aboutClass-close-btn").text() == "关闭") {
            $("#aboutClass-close-btn").text("放弃");
            $("#aboutClass-btn").show();
            location.href = "/class/page?menu=class&Loginer=" + $("#Loginer").val();
        }
    })
})


var editClass = function (id) {
    $("#myModalLabelForClass").text("编辑班级信息");
    $("#error-message-Class").text("")
    $("#error-ok-Class").text("")
    let className = $("#" + id + "_className").text();
    let grade = $("#" + id + "_grade").text();
    let major = $("#" + id + "_major").text();
    $("#ClassId").val(id);
    $("#ClassClassName").val(className);
    $("#ClassGrade").val(grade);
    $("#ClassMajor").val(major);
}

var newClassButton = function () {
    $("#myModalLabelForClass").text("新建班级信息");
    $("#ClassId").val("");
    $("#ClassClassName").val("");
    $("#ClassGrade").val("");
    $("#ClassMajor").val("");
}

var deleteClass = function (id) {
    location.replace("/login_success");
    $.post("/class/remove",
        {
            id: id
        },
        function (data, status) {
            if (data == "OK") {
                location.href = "/class/page?menu=class&Loginer=" + $("#Loginer").val();
            } else if (data = "VALIDATION_FAILED") {
                alert("要删除的班级不存在！")
            } else if (data = "RUN_ERROR") {
                location.href = "/error"
            }
        });
}