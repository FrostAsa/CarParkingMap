$(function () {
    layui.use(['form', 'table', 'laydate'], function () {
        var form = layui.form
        var table = layui.table;
        var laydate = layui.laydate;
        table.render({
            elem: '#userTable'
            , url: '/oauth/page'

            , cellMinWidth: 80,
            skin: 'line' //line border style
            , even: true //Enable interlaced background
            , toolbar: '#toolbarDemo'

            , page: { //Supports all parameters passed to the laypage component (except some parameters, such as: jump/elem) - see documentation for details
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //Customise pagination layout
                //,curr: 5 //The initial setting is on page 5
                , groups: 1 //Show only 1 consecutive page number
                , first: false //Don't show the home page
                , last: false //Don't show last page
            }
            , cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'id', width: 20, title: 'id'},
                {field: 'username', width: 150, title: 'Account'},
                {field: 'nickname', width: 150, title: 'Nickname'}
                // {field: 'img', width: 150, height: 60, title: 'portrait', templet: function (d) {
                //         return '<img src="' + d.img + '" style="height: 50px" width="150px">'
                //     }
                // }
            ]]
            , parseData: function (res) { //res is the original returned data
                return {
                    "code": res.code, //Parse interface state
                    "msg": res.msg, //Parse the prompt text
                    "count": res.date.total, //Parse data length
                    "data": res.date.list //Parse data list
                };
            }
            ,response: {
                statusCode: 200 //Specifies the successful status code, default: 0
            }
            , request: {
                pageName: 'pageNum' //The parameter name of the page number, default: page
                , limitName: 'pageSize' //The parameter name of the amount of data per page, default: limit
            }
        });
        table.on('rowDouble(userTable)', function (obj) {
            var data = obj.data;
            updat(form, data)
        });
        table.on('toolbar(userTable)', function (obj) {

            // var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'detail':
                    layer.open({
                        type: 1,
                        title: "filter",
                        area: ['400px', '200'],
                        btn: ['Confirm', 'Cancel'],
                        yes: function (index, layero) {
                            var data = form.val('example');
                            table.reload('test', {
                                url: '/oauth/page'
                                , where: data//Set additional parameters of asynchronous data interface
                            });
                            layer.close(index)
                        },
                        content: $('#from') //Here content is a DOM, note: it is best to store this element in the outermost layer of the body, otherwise it may be affected by other relative elements
                    });
                    break;
                case 'update':
                    var checkStatus = table.checkStatus('userTable');

                    var date = checkStatus.data;
                    if (date.length != 1) {
                        layer.msg("please select a data")
                    } else {
                        updat(form, date[0])
                    }
                    break;
                case 'add':
                    updat(form, null)
                    break;
            }
            ;
        });

        laydate.render({
            elem: '#tId'
        });
    })
    function updat(form, date) {
        layer.open({
            type: 1,
            title: "edit",
            area: ['400px', '420px'],
            btn: ['confirm', 'cancel'],
            yes: function (index, layero) {
                var data = form.val('formTest');
                $.ajax({
                    type: 'POST',
                    dataType: "json",
                    url: '/oauth/save',
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    success: function (data) {
                        if (data.code == 200) {
                            layer.msg(data.msg, {
                                time: 2000, //Automatically close after 20s
                                btn: ['Understood', 'Got it', 'OK']
                            });

                        } else {
                            layer.msg(data.msg, {
                                time: 5000, //Automatically close after 20s
                                btn: ['Understood', 'Got it', 'OK']
                            });
                        }
                    }
                });


                layer.close(index)
            },
            content: $('#fromTest') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , success: function (index, layero) {
                console.log(date);
                form.val("formTest", null);
                $("#formTest")[0].reset();
                form.render();
                if (date){
                    form.val("formTest", date);
                    $("#imgReading").focus();
                    // $("#img").attr("src", date.img)
                }
            }
        });
    }

})