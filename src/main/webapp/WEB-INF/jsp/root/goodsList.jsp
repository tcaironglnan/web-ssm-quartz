<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2018/1/28
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="bjui-pageHeader" style="background-color:#fefefe; border-bottom:none;">
    <form data-toggle="ajaxsearch" data-options="{searchDatagrid:$.CurrentNavtab.find('#goodsList')}">
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
    <table id="goodsList" class="table table-bordered">
    </table>
</div>

<script src="${pageContext.request.contextPath}/js/toolDate.js"></script>
<script type="text/javascript">
    $('#goodsList').datagrid({
        height: '100%',
        tableWidth: '100%',
        filterThead: false,
        gridTitle: '显示用户列表',
        showToolbar: true,
        showCheckboxcol: true,
        delPK: 'id',
        local: 'remote',
        dataUrl: '/goods/getGoodsList',
        inlineEditMult: false,
        saveAll: false,
        addLocation: 'last',
        toolbarCustom: function () {
            return '<button type="button" class="btn btn-green" onclick="alert(2)" data-icon="sign-out"><i class="fa fa-sign-out">导出</i></button>  ' +
                '<button type="button" class="btn-blue" onclick="addGoods();">添加货物</button>';
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
                items: [{true: '已卖'}, {false: '未卖'}],
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
                items: [{0: '否'}, {1: '一般'}, {2: '是'}],
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
                label: '操作',
                columns: [{
                    name: "",
                    label: "编辑操作",
                    align: "center",
                    render: function (value, data) {
                        return '<a href="javascript:;" onclick="editGoods(\'' + data.id + '\')">' + '编辑货物' + '</a>';
                    }
                }, {
                    name: "",
                    label: "删除操作",
                    align: "center",
                    render: function (value, data) {
                        return '<a href="javascript:;" onclick="deleteGoods(\'' + data.id + '\')">' + '删除货物' + '</a>';
                    }
                }]
            }
        ],
        editUrl: 'ajaxDone1.html',
        paging: {pageSize: 20, pageCurrent: 1},
        linenumberAll: true
    });


    function editGoods(id) {
        BJUI.navtab({
            id: 'goodsList_' + new Date().getTime(),
            url: '/admin/page/editGoodsPage?id='+id,
            title: '编辑货物信息界面'
        });
        BJUI.navtab('refresh', 'base-button')
    }

    function deleteGoods(value) {
        BJUI.ajax('doajax', {
            url: '/admin/deleteOne?id=' + value,
            loadingmask: true,
            okCallback: function (json, options) {

                if (json === 0) {
                    BJUI.alertmsg('info', "删除失败");
                }
                if (json === 1) {
                    BJUI.alertmsg('ok', "删除成功");
                }
                if (json === 2) {
                    BJUI.alertmsg('info', "请先登录");
                }
                BJUI.navtab('refresh', '')
            }
        });
    }

    function addGoods() {
        BJUI.navtab({
            id: 'userList_' + new Date().getTime(),
            url: '/admin/page/insertGoodsPage',
            title: '添加货物界面'
        });
        BJUI.navtab('refresh', 'base-button')
    }
</script>