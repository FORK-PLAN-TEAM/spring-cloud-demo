
/*
* layui table 公共方法，以下3个为必传参数
* elem 表格Id
* url  接口地址
* cols 要展示的列-集合
**/
(function(){
    function DataTable(opts){
        this.id           = getUUID();
        this.elem        = opts.elem;
        this.url         = opts.url;
        this.limit       = opts.limit || 15;
        this.limits      = opts.limits || [10, 15, 20, 30];
        this.request     = opts.request || {
            pageName: 'pageIndex', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        };
        this.where       = opts.where || {};
        this.height      = opts.height || ''; //自适应高度：height : 'full-150'
        this.cols        = opts.cols;
        this.table = this.render();
        return this;
    }
    DataTable.prototype = {
        //初始化table表格
        render: function(){
            var tableIns = layui.table.render({
                id: this.id,
                elem: this.elem,
                url: webroot + this.url, //数据接口
                event: true,
                page: true,
                cellMinWidth : this.cellMinWidth || 50 ,
                height: this.height,
                limit: this.limit,
                limits: this.limits,
                loading: true,
                request: this.request,
                where: this.where,
                parseData: function (res) { //res 即为原始返回的数据
                    var data = res.resultObj;
                    return {
                        "code": 0,         //解析接口状态
                        "msg": "加载成功", //解析提示文本
                        "count": data.total, //解析数据长度
                        "data": data.records //解析数据列表
                    };
                },
                cols: [
                    this.cols
                ],
                page: {
                    layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    ,groups: 1 //只显示 1 个连续页码
                    ,first: false //不显示首页
                    ,last: false //不显示尾页
                }
            });
            return tableIns;
        },
        getTableId : function () {
            return this.id;
        }
    };
    window.DataTable = DataTable;
})();
