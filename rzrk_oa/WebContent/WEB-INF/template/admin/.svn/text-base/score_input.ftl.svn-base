<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>编辑在线客服 - Powered By UNICORN</title>
    <meta name="Author" content="UNICORN Team" />
    <meta name="Copyright" content="UNICORN" />
    <link rel="icon" href="favicon.ico" type="image/x-icon" />
    <link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
    <link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
    <script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
    <script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>

    <script type="text/javascript">
        $().ready(function() {

            var $validateErrorContainer = $("#validateErrorContainer");
            var $validateErrorLabelContainer = $("#validateErrorContainer ul");
            var $validateForm = $("#validateForm");

            // 表单验证
            $validateForm.validate({
                errorContainer: $validateErrorContainer,
                errorLabelContainer: $validateErrorLabelContainer,
                wrapper: "li",
                errorClass: "validateError",
                ignoreTitle: true,
                rules: {
                    "scoreSetting.scoreGoodsRatio": {
                        required: true,
                        number:true
                    },
                    "scoreSetting.scoreRegister":{
                        required: true,
                        digits:true
                    },
                    "scoreSetting.scoreLogin": {
                        required: true,
                        digits:true
                    },
                    "scoreSetting.scoreComment": {
                        required: true,
                        digits:true
                    },
                    "scoreSetting.scoreMark": {
                        required: true,
                        digits:true
                    },
                    "scoreSetting.scoreEmail": {
                        required: true,
                        digits:true
                    },
                    "scoreSetting.scoreMobile": {
                        required: true,
                        digits:true
                    },
                    "scoreSetting.scoreFullInform": {
                        required: true,
                        digits:true
                    }
                },
                messages: {
                    "scoreSetting.scoreGoodsRatio": {
                        required: "商品积分换算比率不允许为空",
                        number:"商品积分换算比率只允许填写数字"
                    },
                    "scoreSetting.scoreRegister":{
                        required: "注册积分不允许为空",
                        digits:"注册积分只允许整数数字"
                    },
                    "scoreSetting.scoreLogin": {
                        required: "每日登录积分不允许为空",
                        digits:"每日登录积分只允许整数数字"
                    },
                    "scoreSetting.scoreComment": {
                        required: "商品评价积分不允许为空",
                        digits:"商品评价积分只允许整数数字"
                    },
                    "scoreSetting.scoreMark": {
                        required: "评论评价积分不允许为空",
                        digits:"评论评价积分只允许整数数字"
                    },
                    "scoreSetting.scoreEmail": {
                        required: "邮箱验证积分不允许为空",
                        digits:"邮箱验证积分只允许整数数字"
                    },
                    "scoreSetting.scoreMobile": {
                        required: "手机验证积分不允许为空",
                        digits:"手机验证积分只允许整数数字"
                    },
                    "scoreSetting.scoreFullInform": {
                        required: "完善个人资料积分不允许为空",
                        digits:"完善个人资料积分只允许整数数字"
                    }
                },
                submitHandler: function(form) {
                    $(form).find(":submit").attr("disabled", true);
                    form.submit();
                }
            });

        });
    </script>

<body class="input instantMessaging">
<div class="bar">
    编辑积分设置
</div>
<div id="validateErrorContainer" class="validateErrorContainer">
    <div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
    <ul></ul>
</div>
<div class="body">
    <form id="validateForm" action="score!update.action" method="post">
        <table id="instantMessagingTable" class="inputTable">
            <tr>
                <th>
                    商品积分换算比率:
                </th>
                <td>
                    <input type="text" name="scoreSetting.scoreGoodsRatio" class="formText" value="${(scoreSetting.scoreGoodsRatio)!}" />
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>
                    注册积分:
                </th>
                <td>
                    <input type="text" name="scoreSetting.scoreRegister" class="formText" value="${(scoreSetting.scoreRegister)!}" />
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>
                    每日登录积分:
                </th>
                <td>
                    <input type="text" name="scoreSetting.scoreLogin" class="formText" value="${(scoreSetting.scoreLogin)!}" />
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>
                    商品评价积分:
                </th>
                <td>
                    <input type="text" name="scoreSetting.scoreComment" class="formText" value="${(scoreSetting.scoreComment)!}" />
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>
                    评论评价积分:
                </th>
                <td>
                    <input type="text" name="scoreSetting.scoreMark" class="formText" value="${(scoreSetting.scoreMark)!}" />
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>
                    邮箱验证积分:
                </th>
                <td>
                    <input type="text" name="scoreSetting.scoreEmail" class="formText" value="${(scoreSetting.scoreEmail)!}" />
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>
                    手机验证积分:
                </th>
                <td>
                    <input type="text" name="scoreSetting.scoreMobile" class="formText" value="${(scoreSetting.scoreMobile)!}" />
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>
                    完善个人资料积分:
                </th>
                <td>
                    <input type="text" name="scoreSetting.scoreFullInform" class="formText" value="${(scoreSetting.scoreFullInform)!}" />
                    <label class="requireField">*</label>
                </td>
            </tr>
        </table>
        <div class="buttonArea">
            <input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
            <input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
        </div>
    </form>
</div>
</body>
</html>