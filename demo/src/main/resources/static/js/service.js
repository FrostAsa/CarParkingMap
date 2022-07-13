$(function () {

    $(document).ready(function() {
        $.ajax({
            type: 'get',
            dataType: "json",
            url: '/application/getServiceList',
            success: function (data) {

                if (data.code == 200) {
                    console.log(data.date)
                    for (var i=0;i<data.date.length;i++){
                        var app=data.date[i];
                        console.log(app)
                        var str=' <button type="button" class="layui-btn layui-btn-primary layui-btn-lg"  name="'+
                            app.web_server_redirect_uri
                            +'">'+app.name+'</button>'
                        $('#appList').append(str)
                    }
                    // form.val('example', data.date);

                } else {
                    layer.msg(data.msg, {
                        time: 5000, //Automatically close after 20s
                        btn: ['Understood', 'Got it', 'OK']
                    });
                }
            }
        })
    })

    $('#appList').click(function (obj) {
    console.log(obj.target.name)
         window.open(obj.target.name) ;
    })


})