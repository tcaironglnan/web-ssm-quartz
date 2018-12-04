<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2018/1/28
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="bjui-pageHeader" style="background-color:#fefefe; border-bottom:none;">
    <form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#goodsDeletedList')}">
        <fieldset>
            <legend style="font-weight:normal;">页头搜索：</legend>
            <div style="margin:0; padding:1px 5px 5px;">
                <span>货物名称：</span>
                <input type="text" name="goodsName" class="form-control" size="15">

                <span>日期选择</span>
                <input type="text" name="beginDate" value="" data-toggle="datepicker" placeholder="点击选择日期" data-pattern="yyyy-MM-dd HH:mm:ss">
                至 <input type="text" name="endDate" value="" data-toggle="datepicker" placeholder="点击选择日期" data-pattern="yyyy-MM-dd HH:mm:ss">

                <div class="btn-group">
                    <button type="submit" class="btn-green" data-icon="search">搜索</button>
                    <button type="reset" class="btn-orange" data-icon="times">重置</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>

<div class="bjui-pageContent">
    <table id="goodsDeletedList" class="table table-bordered">
    </table>
</div>

<script src="${pageContext.request.contextPath}/js/toolDate.js"></script>
<script type="text/javascript">
    $('#goodsDeletedList').datagrid({
        height: '100%',
        tableWidth: '100%',
        filterThead: false,
        gridTitle: '显示用户列表',
        showToolbar: true,
        showCheckboxcol: true,
        delPK: 'id',
        local: 'remote',
        dataUrl: '/goods/getGoodsDeletedList',
        inlineEditMult: false,
        saveAll: false,
        addLocation: 'last',
        toolbarCustom: function () {
            return '<button type="button" class="btn btn-green" onclick="alert(1)" data-icon="sign-out"><i class="fa fa-sign-out">导出</i></button>';
        },
        delConfirm: true,
        columns: [
            {
                name: 'goodsName',
                label: '货物名',
                align: 'center'
            }, {
                name: 'goodsInCost',
                label: '进价',
                align: 'center'
            }, {
                name: 'total',
                label: '货物总量',
                align: 'center'
            }, {
                name: 'count',
                label: '货物剩余量',
                align: 'center'
            }, {
                name: 'isSale',
                label: '货物状态',
                align: 'center',
                type: 'select',
                items: [{0: '已卖'}, {1: '未卖'}],
                render: $.datagrid.renderItem
            }, {
                name: 'color',
                label: '货物的颜色',
                align: 'center'
            }, {
                name: 'descript',
                label: '备注',
                align: 'center'
            }, {
                name: 'goodsOutCost',
                label: '售价',
                align: 'center'
            }, {
                name: 'flag',
                label: '货物是否受欢迎',
                align: 'center',
                type: 'select',
                items: [{0: '否'}, {1: '是'}],
                render: $.datagrid.renderItem
            }, {
                name: 'goodsFactory',
                label: '货物厂家',
                align: 'center'
            }, {
                name: 'goodsNumber',
                label: '货物编号',
                align: 'center'
            }, {
                name: 'goodsType',
                label: '货物类型',
                align: 'center'
            }, {
                name: 'isDebt',
                label: '是否有欠款',
                align: 'center',
                type: 'select',
                items: [{true: '还有'}, {false: '没有'}],
                render: $.datagrid.renderItem
            }, {
                name: 'balanceDue',
                label: '欠款金额',
                align: 'center'
            }, {
                name: 'addTime',
                label: '进货时间',
                align: 'center',
                render: function (value, data) {
                    if (value === undefined || value === "") {
                        return "";
                    }
                    var date = new Date(value);
                    return date.Format("yyyy-MM-dd hh:mm:ss");
                }
            }, {
                name: 'overTime',
                label: '卖完时间',
                align: 'center',
                render: function (value, data) {
                    if (value === undefined || value === "") {
                        return "";
                    }
                    var date = new Date(value);
                    return date.Format("yyyy-MM-dd hh:mm:ss");
                }
            }, {
                name: "",
                label: "操作",
                align: "center",
                render: function (value, data) {
                    return '<a href="javascript:;" onclick="revertGoods(\'' + data.id + '\')">' + '恢复用户' + '</a>';
                }
            }
        ],
        editUrl: 'ajaxDone1.html',
        paging: {pageSize: 20, pageCurrent: 1},
        linenumberAll: true
    });

    function revertGoods(value) {

        BJUI.ajax('doajax', {
            url: '/admin/revertOne?id='+value,
            loadingmask: true,
            okCallback: function(json, options) {

                if (json === 0) {
                    BJUI.alertmsg('info', "恢复失败");
                }
                if (json === 1) {
                    BJUI.alertmsg('ok', "恢复成功");
                }
                if (json === 2) {
                    BJUI.alertmsg('info', "请先登录");
                }
                BJUI.navtab('refresh', '')
            }
        });
    }
</script>