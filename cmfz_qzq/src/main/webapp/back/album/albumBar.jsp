<%@ page language="java" pageEncoding="UTF-8" %>

<body>
<h1>专辑章节统计</h1> <br>
<div id="albumBar" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    var myChart = echarts.init(document.getElementById("albumBar"));

    $.get("/cmfz/album/queryChapterCount").done(function (data) {
        myChart.setOption({
            title: {
                text: "专辑章节统计"
            },
            tooltip: {},
            legend: {
                data: ["数量"]
            },
            xAxis: {
                data: data.albumName,
            },
            yAxis: {},
            series: [{
                name: "数量",
                type: "bar",
                data: data.chapterSum,
                itemStyle: {
                    normal: {
                        color: function (params) {
                            var colorList = ['#c23531', '#2f4554', '#61a0a8', '#d48265', '#91c7ae', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3'];
                            return colorList[params.dataIndex];
                        }
                    }
                }
            }]
        });
    });
</script>
