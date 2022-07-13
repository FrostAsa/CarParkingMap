var table;
var directionsService;
var directionsRenderer;
$(function() {
    const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    let labelIndex = 0;
    var geocoder;
    var map;
    var from;
    var laydate;

    function initMap() {
        geocoder = new google.maps.Geocoder();
         directionsService = new google.maps.DirectionsService();
         directionsRenderer = new google.maps.DirectionsRenderer();
        var bangalore = {
            lat: 47.56,
            lng: -52.71
        };
        var mapProp = {
            center: bangalore,
            zoom: 12,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
        directionsRenderer.setMap(map);
        directionsRenderer.setPanel(document.getElementById('directionsPanel'));
        // google.maps.event.addListener(map, "click", (event) => {
        //     addMarker(event.latLng, map);
        // });
        // // Add a marker at the center of the map.
        // addMarker(bangalore, map);
    }

    google.maps.event.addDomListener(window, 'load', initMap);

    // Adds a marker to the map.
    function addMarker(data, map) {
        console.log(data)
        // Add the marker at the clicked location, and add the next-available label
        // from the array of alphabetical characters.
        const marker = new google.maps.Marker({
            position: data.address,
            label: labels[labelIndex++%labels.length],
            map: map,
            optimized: false,
            title: data.address,

        });
        marker.addListener("click", () => {
            markerClick(marker)
        });
    }

    function markerClick(marker) {
        addOrder(marker.data)
    }

    function calcRoute(start, end) {

        var request = {
            origin: start,
            destination: end,
            travelMode: 'DRIVING'
        };
        console.log(request);
        directionsService.route(request,
            function(result, status) {
                console.log(result)
                if (status == 'OK') {
                    directionsRenderer.setDirections(result);
                }
            });
    }

    function addmarker(data) {

        const marker = new google.maps.Marker({
            position: {
                "lat": data.lan,
                "lng": data.lng
            },
            label: data.id,
            map: map,
            optimized: false,
            title: data.address,
            data: data
        });
        marker.addListener("click", () => {
            markerClick(marker)
        });
    }
    function codeAddress(data) {
        geocoder.geocode({
                'address': data.address
            },
            function(results, status) {
                if (status == 'OK') {
                    map.setCenter(results[0].geometry.location);
                    const marker = new google.maps.Marker({
                        position: results[0].geometry.location,
                        label: data.id,
                        map: map,
                        optimized: false,
                        title: data.address,
                        data: data
                    });
                    marker.addListener("click", () => {
                        markerClick(marker)
                    });
                } else {
                    alert('Geocode was not successful for the following reason: ' + status);
                }
            });
    }
    function renderTime(elem, type, value, range, fn) {
        layui.laydate.render({
            elem: elem,
            //bind element
            type: type,
            // time category
            value: value,
            //Defaults
            range: range,
            //Whether to open the time range
            done: fn,
            //Select the time-triggered callback function
            trigger: 'click',
            theme: '#1890ff',
            //theme color
        });
    }
    //JavaScript code below
    layui.use(['form', 'table', 'laydate'], function() {
            form = layui.form;
            table = layui.table;
            laydate = layui.laydate;
            var nowTime = new Date(); //get current date
            renderTime("#time_range_1", "time", '', true,
                function(value, date, endDate) {
                    if (value) {
                        let arr = value.split(" - ");
                        var st = timeString(nowTime) + " " + arr[0];
                        var end = timeString(nowTime) + " " + arr[1];
                        var minute = GetDateDiff(st, end, "minute") ;
                        var price1 = $('#price1').val();
                        $("#price").val(minute * price1);

                        $("#startTime").val(st);
                        $("#endTime").val(end)
                    } else {
                        $("#price").val(0);
                        $("#startTime").val("");
                        $("#endTime").val("")
                    }
                });
            table.render({
                elem: '#orderTable',
                url: "/GmCarOrderOrder/getList",
                totalRow: true,
                where: {
                    "type": 0
                },
                cols: [[{
                    field: 'id',
                    title: 'id',
                    width: 50,
                    edit: 'text'
                },
                    {
                        field: 'startTime',
                        title: 'startTime',
                        width: 160,
                        edit: 'text'
                    },
                    {
                        field: 'endTime',
                        title: 'endTime',
                        width: 160,
                        edit: 'text'
                    },
                    {
                        field: 'price',
                        title: 'price',
                        width: 100,
                    },
                    {
                        field: 'address',
                        title: 'address',
                        width: 160,
                    },
                    {
                        field: 'status',
                        title: 'status',
                        width: 160,
                        templet: function(d) {
                            if (d.status == 0) {
                                return "Unfinished";
                            } else if (d.status == 2) {
                                return "Cancelled";
                            } {
                                return 'Finished';
                            }
                        }
                    },
                    {
                        field: 'cancel',
                        title: 'cancel',
                        width: 160,
                        templet: function(d) {
                            if (d.status == 0) {
                                "<button  name=\"\" onclick='cancel(" + d.id + ")'  type=\"button\" >cancel</button>"+
                                "<button  name=\"\" onclick='navigationAdd(" + d.id + ")'  type=\"button\" >navigation</button>";
                                return ;
                            } else {
                                return '-';
                            }
                        }
                    }]],
                parseData: function(data) {
                    var d = {
                        "code": 0,
                        //parse interface state
                        "msg": "",
                        //Parse the prompt text
                    }
                    if (data.data) {
                        d.count = data.data.length,
                            //parse length of data
                            d.data = data.data //Parse data list
                    }
                    return d
                }
                // trigger event
            });
        });

    $('#space').click(function(obj) {
        $.ajax({
            type: 'get',
            dataType: "json",
            url: '/gmCarport/getList',
            success: function(data) {
                if (data.code == 200) {
                    for (var i = 0; i < data.data.length; i++) {

                        codeAddress(data.data[i])
                    }
                } else {
                    layer.msg(data.msg, {
                        time: 5000,
                        //Automatically close after 20s
                        btn: ['GOT IT', 'OK', 'UNDERSTAND']
                    });
                }
            }
        })

    })
    $('#addOrderBt').click(function() {
        var formData = $('#example').serializeArray();
        var d = {};
        $.each(formData,
            function() {
                d[this.name] = this.value;
            });
        // d.carPortId = $('#id').val();
        d.price = $('#price').val();
        $.ajax({
            type: 'post',
            dataType: "json",
            url: '/GmCarOrderOrder/add',
            data: JSON.stringify(d),
            contentType: 'application/json',
            success: function(data) {
                if (data.code == 200) {
                    table.reload('orderTable');
                    $("#example").css("display", "none");
                    $("#orderItem").css("display", "inline");
                } else {
                    layer.msg(data.msg)
                }
            },
        })

    });
    $('#navigationBt').click(function() {
        var end=   $('#address').val();
        var start= $('#startAdd').val();
        calcRoute(start,end)
    });
    $('#upload').click(function() {
        setTimeout(function () {},1000)
        layer.open({
            type:1,
            title: 'upload driver information',
            content: $('#uploadfrom'),
            btn: ['upload information'],
            area: ['500px', '350px'],
            yes: function(index, layero) {
                var formData = $('#uploadExample').serializeArray();
                var d = {};
                $.each(formData,
                    function() {
                        d[this.name] = this.value;
                    });
                $.ajax({
                    type: 'post',
                    dataType: "json",
                    url: '/gmCar/add',
                    data: JSON.stringify(d),
                    contentType: 'application/json',
                    success: function(data) {
                        if (data.code == 200) {
                            layer.close(index)
                        } else {
                            layer.msg(data.msg)
                        }
                    },
                })
            }
        });
    })

    function getList() {
        $("#example").css("display", "none");
        $("#orderItem").css("display", "inline");
        $.ajax({
            type: 'get',
            dataType: "json",
            url: '/GmCarOrderOrder/getList',
            data: null,
            contentType: 'application/json',
            success: function(data) {
                if (data.code == 200) {
                    var list = data.data
                    var html = "";
                    $.each(list,
                        function(index, da) {
                            console.log(da)
                            html += '<div class="layui-row">' +
                                ' <div class="layui-col-md2">address：\n' +
                                '    </div>\n' + '  <div class="layui-col-md2">\n' +
                                da.address + '    </div>' + '</div>' +
                                '<div class="layui-row">' +
                                ' <div class="layui-col-md2">startTime：\n' +
                                '    </div>\n' +
                                '    <div class="layui-col-md">\n' +
                                da.startTime + '    </div>' + '</div>' +
                                '<div class="layui-row">' +
                                ' <div class="layui-col-md2">endTime：\n' +
                                '    </div>\n' +
                                '    <div class="layui-col-md">\n' + da.endTime +
                                '    </div>' + '</div>' + '<div class="layui-row">' +
                                ' <div class="layui-col-md2">price：\n' + '    </div>\n'
                                + '    <div class="layui-col-md5">\n' + da.price +
                                '    </div>' + '<div class="layui-row">' +
                                ' <div class="layui-col-md2">status：\n' +
                                '    </div>\n' + '    <div class="layui-col-md5">Finished\n' +

                                '    </div>'+
                            '</div><hr class="layui-bg-orange">'
                        });
                    $("#orderItem").html(html);
                } else {
                    layer.msg(data.msg)
                }
            },
        })
    }

    function addOrder(data) {
        $("#orderItem").css("display", "none");
        $("#example").css("display", "inline");
        $('#status').val(data.status == 0 ? 'Vacant': 'Occupied');
        $('#address').val(data.address);
        $('#price1').val(data.price);
        $('#carPortId').val(data.id);
        $('#time').val(data.startTime + "-" + data.endTime);

    }

    function GetDateDiff(startTime, endTime, diffType) {
        //Convert the time format of xxxx-xx-xx to the format of xxxx/xx/xx
        startTime = startTime.replace(/\-/g, "/");
        endTime = endTime.replace(/\-/g, "/");

        //Convert calculation interval generic characters to lowercase
        diffType = diffType.toLowerCase();
        var sTime = new Date(startTime); //start time
        var eTime = new Date(endTime); //end time
        //number as divisor
        var divNum = 1;
        switch (diffType) {
            case "second":
                divNum = 1000;
                break;
            case "minute":
                divNum = 1000 * 60;
                break;
            case "hour":
                divNum = 1000 * 3600;
                break;
            case "day":
                divNum = 1000 * 3600 * 24;
                break;
            default:
                break;
        }
        return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));

    }
    function timeString(time) {
        var datetime = new Date();
        datetime.setTime(time);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        return year + "-" + month + "-" + date;
    }

})
function cancel(id) {
    $.ajax({
        type: 'get',
        dataType: "json",
        url: '/GmCarOrderOrder/cancel/' + id,
        data: null,
        contentType: 'application/json',
        success: function(data) {
            if (data.code == 200) {
                table.reload('orderTable');
            } else {
                layer.msg(data.msg)
            }
        },
    })
}
function navigationAdd(id) {
    var request = {
        origin: start,
        destination: end,
        travelMode: 'DRIVING'
    };
    console.log(request);
    directionsService.route(request,
        function(result, status) {
            if (status == 'OK') {
                console.log(result)
                // directionsRenderer.setDirections(result);
            }
        });
}