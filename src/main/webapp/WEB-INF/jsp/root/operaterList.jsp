<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2018/1/28
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="bjui-pageHeader" style="background-color:#fefefe; border-bottom:none;">
    <form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#operaterList')}">
        <fieldset>
            <legend style="font-weight:normal;">页头搜索：</legend>
            <div style="margin:0; padding:1px 5px 5px;">
                <c:if test="${user.auth eq 1}">
                    <span>姓名：</span>
                    <input type="text" name="userName" class="form-control" size="15">
                </c:if>

                <span>日期选择</span>
                <input type="text" name="beginDate" value="" data-toggle="datepicker" placeholder="点击选择日期" data-pattern="yyyy-MM-dd HH:mm:ss">
                 至 <input type="text" name="endDate" value="" data-toggle="datepicker" placeholder="点击选择日期" data-pattern="yyyy-MM-dd HH:mm:ss">

                <div class="btn-group">
                    <button type="submit" class="btn-green" data-icon="search">搜索</button>
                    &nbsp;&nbsp;<button type="reset" class="btn-orange" data-icon="times">重置</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>

<div class="bjui-pageContent">
    <table id="operaterList" class="table table-bordered">
    </table>
</div>

<script src="${pageContext.request.contextPath}/js/toolDate.js"></script>
<script type="text/javascript">
    $('#operaterList').datagrid({
        height: '100%',
        tableWidth: '100%',
        filterThead: false,
        gridTitle: '显示用户列表',
        showToolbar: true,
        showCheckboxcol: true,
        delPK: 'id',
        local: 'remote',
        dataUrl: '/operation/getOperaterInfoList',
        inlineEditMult: false,
        saveAll: false,
        addLocation: 'last',
        toolbarCustom: function () {
            return '<button type="button" class="btn btn-green" onclick="alert(1)" data-icon="sign-out"><i class="fa fa-sign-out"></i> 导出</button>';
        },
        delConfirm: true,
        columns: [
            {
                name: 'userName',
                label: '用户名',
                align: 'center'
            }, {
                name: 'event',
                label: '操作事件',
                align: 'center'
            }, {
                name: 'descript',
                label: '操作描述',
                align: 'center'
            }, {
                name: 'opDate',
                label: '操作时间',
                align: 'center',
                render: function (value, data) {
                    if (value === undefined || value === "") {
                        return "";
                    }
                    var date = new Date(value);
                    return date.Format("yyyy-MM-dd hh:mm:ss");
                }
            }
        ],
        editUrl: 'ajaxDone1.html',
        paging: {pageSize: 20, pageCurrent: 1},
        linenumberAll: true
    })
</script>