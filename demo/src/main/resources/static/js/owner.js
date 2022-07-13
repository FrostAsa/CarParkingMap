$(function () {
    const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    let labelIndex = 0;
    var geocoder;
    var map;
    var from;
    var laydate;

    function initMap() {
        geocoder = new google.maps.Geocoder();
        var bangalore = {lat: 47.56, lng: -52.71};
        var mapProp = {
            center: bangalore,
            zoom: 12,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
    }

    google.maps.event.addDomListener(window, 'load', initMap);

    // Adds a marker to the map.
    function addMarker(data, map) {
        const marker = new google.maps.Marker({
            position: data.address,
            label: labels[labelIndex++ % labels.length],
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
        directionsService.route(request, function (result, status) {
            if (status == 'OK') {
                console.log(result)
            }
        });
    }


    function codeAddress(data) {
        geocoder.geocode({'address': data.address}, function (results, status) {
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
                // marker.addListener("click", () => {
                //     markerClick(marker)
                // });
                console.log(results[0].geometry.location);
                return results[0].geometry.location;
            } else {
                alert('Geocode was not successful for the following reason: ' + status);
            }
        });
    }
    function renderTime(elem, type, value, range, fn) {
        layui.laydate.render({
            elem: elem, //bind element
            type: type, //time category
            value: value, //defaults
            range: range, //time range opened or not
            done: fn, //Select the time-triggered callback function
            trigger: 'click',
            theme: '#1890ff', //theme color
        });
    }

    //JavaScript code below
    layui.use(['form', 'table', 'laydate'], function () {
        form = layui.form
        var table = layui.table
        laydate = layui.laydate;
            table.render({
                elem: '#orderTable'
                , url: "/GmCarOrderOrder/getList"
                , totalRow: true
                ,where :{"type":1}
                , cols: [[
                     {field: 'id', title: 'id', width: 50, edit: 'text'}
                    , {field: 'startTime', title: 'startTime', width: 160, edit: 'text'}
                    , {field: 'endTime', title: 'endTime',width: 160,  edit: 'text'}
                    , {field: 'price', title: 'price',width: 100, }
                    , {field: 'address', title: 'address',width: 160,}
                    , {field: 'status', title: 'status',width: 160,templet:function (d) {
                            if ( d.status==0){
                                return "Unfinished";
                            }else   if ( d.status==2){
                                return "Cancelled";
                            } {
                                return   'Finished';
                            }
                        }}
                ]], parseData :function (data) {
                    var  d={
                        "code": 0, //parse interface state
                        "msg": "", //parse prompt text
                    }
                    if (data.data){
                        d.count=data.data.length, //parse the length of data
                         d.data=data.data//Parse data list
                    }
                    return d;

                }
                //trigger event
            });
            var ref = setInterval(function(){
                table.reload('orderTable',{"type":1});
            },5000);
            $('#upload').click(function () {
                layer.open({
                    type:1,
                    title: 'upload parking space information',
                    content: $('#carFrom'),
                    btn: ['upload information']
                    , area: ['600px', '550px']
                    , yes: function (index, layero) {
                        var formData = $('#carExample').serializeArray();
                        var d = {};
                        $.each(formData, function () {
                            d[this.name] = this.value;
                        });
                        $.ajax({
                            type: 'post',
                            dataType: "json",
                            url: '/gmCarport/add',
                            data: JSON.stringify(d),
                            contentType: 'application/json',
                            success: function (data) {
                                console.log(data)
                                if (data.code == 200) {
                                    console.log(data)
                                    layer.close(index)
                                } else {
                                    $("reMs").html(data.msg);
                                    // layer.msg(data.msg)
                                }
                            },
                        })
                    }
                });
            })
            renderTime("#time_range_1", "time", '', true, function (value, date, endDate) {
                if (value) {
                    let arr = value.split(" - ")
                    $("#startTime").val(arr[0]);
                    $("#endTime").val(arr[1])
                } else {
                    $("#startTime").val("");
                    $("#endTime").val("")
                }
            });
        });


    })
