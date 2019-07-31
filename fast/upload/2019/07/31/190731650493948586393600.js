//请求根路径
//window.webroot = "https://www.zypcy.cn/";
window.webroot = "http://192.168.68.138:8088/";

function getById(id) {
    return document.getElementById(id);
}

//是否是string类型
function isString(s) {

    return typeof( s ) == "string";
};

//取url参数
function queryString(name) {
    /// 获取QueryString
    var AllVars = window.location.search.substring(1);
    var Vars = AllVars.split("&");
    for (var i = 0; i < Vars.length; i++) {
        var Var = Vars[i].split("=");
        if (Var[0] == name) return Var[1];
    }
    return "";
};

// JS生成UUID
//此函数生成16位UUID样式为af22-3fa8-4028-8dea-30a2
function getUUID() {
    return 'xxxx-xxxx-xxxx-xxxx-xxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    });
}

/**
 公用ajax加载，请求示例
 new AjaxRequest({
        type: "get",
        url: "https://5ae979d7531a580014142797.mockapi.io/api/v1/records",
        data: "",
        isShowLoader: true,
        success : function(res){
            console.log(res);
        }
    });
 */
(function(){
    function AjaxRequest(opts){
        this.type         = opts.type || "get";
        this.url          = opts.url;
        this.data        = opts.data || {};
        this.isShowLoader = opts.isShowLoader || false;
        this.success      = opts.success;
        this.error        = opts.error;
        this.init();
    }
    var layIndex = 0;
    AjaxRequest.prototype = {
        //初始化
        init: function(){
            this.sendRequest();
        },
        //渲染loader
        showLoader: function(){
            if(this.isShowLoader){
                layIndex = layer.load(2);
            }
        },
        //隐藏loader
        hideLoader: function(){
            if(this.isShowLoader){
                layer.close(layIndex);
            }
        },
        //发送请求
        sendRequest: function(){
            var self = this;
            $.ajax({
                type: this.type,
                url: this.url,
                data: this.data,
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: this.showLoader(),
                success: function(res){
                    self.hideLoader();
                    if (res != null && res != "") {
                        if(self.success){
                            //Object.prototype.toString.call方法--精确判断对象的类型
                            if (Object.prototype.toString.call(self.success) === "[object Function]") {
                                self.success(res);
                            }else{
                                console.log("callBack is not a function");
                            }
                        }
                    }
                },
                error: function (xhr, type, errorThrown) {
                    console.log("error:" + JSON.stringify(xhr) + "\n" + type + "\n" + errorThrown);
                    self.hideLoader();
                    if(self.error){
                        if (Object.prototype.toString.call(self.error) === "[object Function]") {
                            self.error(xhr, type, errorThrown);
                        }else{
                            console.log("callBack is not a function");
                        }
                    }
                }
            });
        }
    };
    window.AjaxRequest = AjaxRequest;
})();


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
        this.limit       = opts.limit || 10;
        this.limits      = opts.limits || [5, 10, 15, 20, 30];
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

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
//例子：
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}


/** ---- Cookie ---- **/
function setCookie(key, value) {
    var exp = new Date();
    exp.setTime(exp.getTime() + (1000 * 60 * 60 * 12)); // 过期时间12小时
    var cookie = key + "=" + encodeURIComponent(value) + "; expires=" + exp.toUTCString() + "; path=/";
    window.document.cookie = cookie;
}

function getCookie(key) {
    var idx = document.cookie.indexOf(key + "=");
    if (idx === -1) return;

    var start = idx + key.length + 1,
        end = document.cookie.indexOf(";", idx);
    if (end === -1) end = document.cookie.length;

    return decodeURIComponent(document.cookie.substring(start, end));
}

function delCookie(key) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cookie = getCookie(key);
    if (cookie) {
        var delCookie = key + "=" + escape(cookie) + ";expires=" + exp.toUTCString() + "; path=/";
        window.document.cookie = delCookie;
    }
}

/*
//js类
var zhuyu = zy({
    name : "zhuyu",
    age : 28
});
console.log(zhuyu.getName())
function zy(opts) {
    var o = new Object();
    o.name = opts.name || "zhuyu";
    o.age = opts.age || 27;
    o.getName = function () {
        return o.name;
    }
    o.getAge = function () {
        return o.age;
    }
    return o;
}*/


/**
 * select 标签生成，根据type获取数据字典
 * @param type           字典type
 * @param selectElemId   select标签的Id
 * @param dictId         可选填，业务保存字典的值，用来反选，
 */
function getDictByType(type , selectElemId , dictId) {
    new AjaxRequest({
        type: "GET",
        url: webroot + "sys/dict/getByType?type=" + type,
        isShowLoader: true,
        success: function (res) {
            console.log(res);
            if (res) {
                if (res.resultCode == "0000" && res.resultObj) {
                    var optStr = '<option value="">请选择或搜索选择</option>';
                    for(var i=0; i < res.resultObj.length; i++){
                        var dict = res.resultObj[i];
                        var selected = "";
                        if(dictId && dictId != ""){
                            selected = dict.id == dictId ? "selected" : " "
                        }
                        optStr += '<option value="'+dict.id+'" '+selected+'>'+dict.name+'</option>';
                    }
                    $("#" + selectElemId).append(optStr);
                    form.render();
                } else {
                    layer.alert(res.resultMessage, {icon: 2})
                }
            }
        }
    });
}