/**
 * Created by Wei on 2014/5/20.
 */
var msg = $.messager;
var win = '';
function getFirstDayofCurrentMonth() {
    var date = new Date();
    return date.getFullYear()
        + "-"
        + ((date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1)
        + "-01";
}
function getToday() {
    return $.fullCalendar.formatDate(new Date(), "yyyy-MM-dd");
}
function getDateValidRange() {
    var oneDay = 24 * 3600 * 1000;
    var date = new Date();
    var startDay = $.fullCalendar.formatDate(date, "dd");
    var startMonth = $.fullCalendar.formatDate(date, "MM");
    var changeDay = startDay;
    var changeMonth = startMonth;
    var monthCount = 0;
    var endDate = "";
    var endFlag = false;
    while (!endFlag) {
        date = new Date(date.getTime() + oneDay);
        changeDay = $.fullCalendar.formatDate(date, "dd");
        changeMonth = $.fullCalendar.formatDate(date, "MM");
        if (startMonth != changeMonth) {
            startMonth = changeMonth;
            monthCount++;
        }
        if (monthCount == 13) {
            endFlag = true;
        } else {
            endDate = $.fullCalendar.formatDate(date, "yyyy-MM-dd");
        }
    }
    return endDate;
}
function getMonthDateValidRange() {
    var oneDay = 24 * 3600 * 1000;
    var view = $('#calendar').fullCalendar('getView');
    var currentMonth = $.fullCalendar.formatDate(view.start, "MM");
    var startMonth = currentMonth;
    var changeMonth = startMonth;
    var monthCount = 0;
    var endDate = "";
    var endFlag = false;
    var currentDate = new Date($.fullCalendar.formatDate(view.start, "yyyy-MM-dd"));
    while (!endFlag) {
        currentDate = new Date(currentDate.getTime() + oneDay);
        changeMonth = $.fullCalendar.formatDate(currentDate, "MM");
        if (startMonth != changeMonth) {
            startMonth = changeMonth;
            monthCount++;
        }
        if (monthCount == 3) {
            endFlag = true;
        }else{
            endDate = $.fullCalendar.formatDate(currentDate, "yyyy-MM-dd");
        }
    }
    return endDate;
}
function getData() {
    var oneDay = 24 * 3600 * 1000;
    var date = new Date();
    var events = [];
    var endDate = getMonthDateValidRange();
    var endFlag = false;
    while (!endFlag) {
        var isSunOrSat = (date.getDay() == 0 || date.getDay() == 6) ? true : false;
        var day = $.fullCalendar.formatDate(date, "yyyy-MM-dd");
        if (day == endDate) endFlag = true;
        if (!isSunOrSat) {
            events.push({
                id: day,
                //title: "工作日 " + day,
                title: day,
                start: $.fullCalendar.parseDate(day),
                value: 3
            });
        }
        date = new Date(date.getTime() + oneDay);
    }
    return events;
}
function setMonthData() {
    var view = $('#calendar').fullCalendar('getView');
    var viewMonth = $.fullCalendar.formatDate(view.start, "yyyyMM");
    var viewDate = $.fullCalendar.formatDate(view.start, "yyyy-MM-dd");
    var oneDay = 24 * 3600 * 1000;
    var date = new Date();
    var currentMonth = $.fullCalendar.formatDate(date, "yyyyMM");
    var endDate = getMonthDateValidRange();
    var endFlag = false;
    if (viewMonth < currentMonth){
    	msg.alert('提示信息', '不能设置已过期的月份', 'info');
        return false;
    }
    if (!isCurrentMonth(date, view)) {
        date = new Date(viewDate);
    }
    while (!endFlag) {
        var isSunOrSat = (date.getDay() == 0 || date.getDay() == 6) ? true : false;
        var day = $.fullCalendar.formatDate(date, "yyyy-MM-dd");
        if (day == endDate) endFlag = true;
        if (!isSunOrSat) {
            var length = window.calendar.fullCalendar("clientEvents", day).length;
            var event = {
                id: day,
                //title: "工作日 " + day,
                title: day,
                start: $.fullCalendar.parseDate(day),
                enddate: $.fullCalendar.parseDate(day)
            };
            if (length > 0){
                window.calendar.fullCalendar("removeEvents", event.id);
            }
            window.calendar.fullCalendar("renderEvent", event, true);
        }
        date = new Date(date.getTime() + oneDay);
    }
}
function addWorkday(){
    if (!window.cEvent) {
        window.calendar.fullCalendar("renderEvent", {
            id: window.selectdate,
            //title: "工作日 " + window.selectdate,
            title: window.selectdate,
            start: $.fullCalendar.parseDate(window.selectdate),
            enddate: $.fullCalendar.parseDate(window.selectdate)
        }, true);
    }
}
/*
function initDialog() {
	win = $('#w');
	var ok = win.find('.ok'),
		cancel = win.find('.cancel'),
		day = win.find('#day'),
		night = win.find('#night');
	win.window({
		iconCls:'icon-save',
		collapsible:false,
		minimizable:false,
		maximizable:false,
		resizable:false,
		closed:true,
		onOpen:function(){
            day.removeAttr("checked");
            night.removeAttr("checked");
            if (window.cEvent) {
                if (window.cEvent.value & 1)
                    day.click();
                if (window.cEvent.value & 2)
                    night.click();
            }
		}
	});
	ok.click(function(){
        var value = 0;
        if (day.is(":checked"))
            value += parseInt(day.val());
        if (night.is(":checked"))
            value += parseInt(night.val());
        if (!window.cEvent) {
            window.calendar.fullCalendar("renderEvent", {
                id: window.selectdate,
                value: value,
                title: "工作日 " + window.selectdate,
                start: $.fullCalendar.parseDate(window.selectdate),
                enddate: $.fullCalendar.parseDate(window.selectdate)
            }, true);
        } else {
            window.cEvent.value = value;
            window.calendar.fullCalendar("updateEvent", window.cEvent.id);
        }
		win.window('close');
		return false;
	});
	cancel.click(function(){
		win.window('close');
		return false;
	});
}
*/
function isBeyondRange(date) {
    var today = getToday();
    var endDay = getDateValidRange();
    if (date < today || date > endDay) {
    	msg.alert('提示信息', "有效日期为：" + today + " 至 " + endDay, 'info');
        return false;
    } else {
        return true;
    }
}
function isCurrentMonth(date, view) {
    var currentViewMonth = $.fullCalendar.formatDate(view.start, "MM");
    var clickMonth = $.fullCalendar.formatDate(date, "MM");
    if (currentViewMonth != clickMonth) {
        return false;
    } else {
        return true;
    }
}
function isSunOrSat(el) {
    var $el = $(el);
    if ($el.hasClass("fc-sun") || $el.hasClass("fc-sat")
        || $el.parents("td").hasClass("fc-sun")
        || $el.parents("td").hasClass("fc-sat")) {
        return true;
    } else {
        return false;
    }
}
function initCalendar() {
    window.calendar = $('#calendar').fullCalendar({
        weekends: true,
        buttonText: {
            today: '今天',
            month: '月视图',
            week: '周视图',
            day: '日视图'
        },
        columnFormat: {
            month: 'dddd',
            week: 'dddd M-d',
            day: 'dddd M-d'
        },
        titleFormat: {
            month: 'yyyy 年 MMMM 月',
            week: "yyyy 年 MMMM 月 d 日",
            day: 'yyyy 年 MMMM 月 d 日 dddd'
        },
        monthNames: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
        dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
        header: {
            left: 'prevYear,prev,next,nextYear today',
            center: 'title',
//            right: 'month,agendaWeek,agendaDay'
            right: 'month'
        },
        allDaySlot: false,
        dayClick: function (date, allDay, jsEvent, view) {
            window.cEvent = "";
            // 判断点击的是否是周六或周日
//            if (isSunOrSat(jsEvent.target)) return false;
            window.selectdate = $.fullCalendar.formatDate(date, "yyyy-MM-dd");
            if (!isBeyondRange(window.selectdate)) return false;
            if (!isCurrentMonth(date, view)) {
            	msg.alert('提示信息', "在当前视图里只能设置当前月的工作日", 'info');
                return false;
            }
            var events = window.calendar.fullCalendar("clientEvents", [window.selectdate]);
            if (events.length > 0) return false;
            /*
            win.window({
            	title: window.selectdate
            }).window('open');
            */
            msg.confirm(window.selectdate, '确认设置 ' + window.selectdate + ' 为工作日？', function(r){
            	if (!r) return false;
            	addWorkday();
            });
        },
        /*
        eventClick: function (event, jsEvent, view) {
            if ($(jsEvent.target).is("a")) return jsEvent.preventDefault();
            window.cEvent = calendar.fullCalendar('clientEvents', [event.id])[0];
            win.window({
            	title: window.cEvent.title
            }).window('open');
        },
        */
        eventAfterRender: function (event, element, view) {
            var inner = $(element).find(".fc-event-inner");
            $(element).height($(element).height() * 6 - 5);
            inner.append("<span style='color:red;position: absolute;left: 40px;top: 40px;font-weight: bold;'>工作日</span>");
            inner.append("<a href='#' class='delete' data-id='" + event.id + "'>删除</a>");
            if (!isCurrentMonth(event.start, view)) inner.parent().hide();
        }
    });
}
$(function () {
    initCalendar();
    $("body").on("click", ".delete", function (e) {
        var id = $(this).data("id");
    	msg.confirm('删除工作日', '确认删除工作日？', function(r){
    		if (!r) return false;
            window.calendar.fullCalendar("removeEvents", [id]);
    	});
        return false;
    });

    $(".setCurrentMonthData").hover(function(){
        $(this).addClass('fc-state-hover');
    },function(){
        $(this).removeClass('fc-state-hover');
    }).mousedown(function(){
        $(this).addClass('fc-state-down');
    }).mouseup(function(){
        $(this).removeClass('fc-state-down');
    }).click(function(){
        setMonthData();
    });

    $("#saveBtn").click(function () {
        var events = calendar.fullCalendar("clientEvents");
        var resultList = [];
        for (temp in events) {
            resultList.push({
                "id": events[temp].id,
                "allDay": events[temp].allDay,
                "title": events[temp].title,
                "start": $.fullCalendar.formatDate(events[temp].start, "yyyy-MM-dd")
            });
        }

        var params = {"content": JSON.stringify(resultList)};
        $.post("/admin/workding_day_record!save.action", params, function (resp) {
            if (resp.status == 'success') {
            	msg.alert('保存工作日', "保存工作日成功！", 'info');
            } else {
                if (resp.message != "" && resp.message != null) {
                	msg.alert('保存工作日', "保存工作日失败：" + resp.message, 'error');
                } else {
                	msg.alert('保存工作日', "保存工作日失败！", 'error');
                }
            }
        }, "json");
    });

    if (window.dateList != null && window.dateList != "") {
        while (window.dateList[0].start.trim().length != 0
        		&& window.dateList[0].start < getToday()) {
        	window.dateList.splice(0, 1);
        }
    } else {
    	window.dateList = getData();
    }
    window.calendar.fullCalendar("addEventSource", window.dateList);
});