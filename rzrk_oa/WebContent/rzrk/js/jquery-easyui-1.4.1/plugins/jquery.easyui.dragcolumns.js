$.extend($.fn.datagrid.methods,{
		columnMoving: function(jq){
			return jq.each(function(){
				var target = this;
				var cells = $(this).datagrid('getPanel').find('div.datagrid-header td[field]');
				cells.draggable({
					revert:true,
					cursor:'pointer',
					edge:5,
					proxy:function(source){
						var p = $('<div class="tree-node-proxy tree-dnd-no" style="position:absolute;border:1px solid #ff0000"/>').appendTo('body');
						p.html($(source).text());
						p.hide();
						return p;
					},
					onBeforeDrag:function(e){
						e.data.startLeft = $(this).offset().left;
						e.data.startTop = $(this).offset().top;
					},
					onStartDrag: function(){
						$(this).draggable('proxy').css({
							left:-10000,
							top:-10000
						});
					},
					onDrag:function(e){
						if(e.clientX > $(this).offset().left && e.clientX < ( $(this).offset().left + $(this).width() )){
							$(this).draggable('proxy').hide().css({
								left:e.pageX+15,
								top:e.pageY+15
							});
						}else{
							$(this).draggable('proxy').show().css({
								left:e.pageX+15,
								top:e.pageY+15
							});
						}
						return false;
					}
				}).droppable({
					accept:'td[field]',
					onDragOver:function(e,source){
						$(source).draggable('proxy').removeClass('tree-dnd-no').addClass('tree-dnd-yes');
						$(this).css('border-left','1px solid #ff0000');
					},
					onDragLeave:function(e,source){
						$(source).draggable('proxy').removeClass('tree-dnd-yes').addClass('tree-dnd-no');
						$(this).css('border-left',0);
					},
					onDrop:function(e,source){
						$(this).css('border-left',0);
						var fromField = $(source).attr('field');
						var toField = $(this).attr('field');
						setTimeout(function(){
							moveField(fromField,toField);
							$(target).datagrid();
							$(target).datagrid('columnMoving');
						},0);
					}
				});
				
				// move field to another location
				function moveField(from,to){
					var columns = $(target).datagrid('options').columns;
					var cc = columns[0];
					var c = _remove(from);
					if (c){
						_insert(to,c);
					}
					
					function _remove(field){
						for(var i=0; i<cc.length; i++){
							if (cc[i].field == field){
								var c = cc[i];
								cc.splice(i,1);
								return c;
							}
						}
						return null;
					}
					function _insert(field,c){
						var newcc = [];
						for(var i=0; i<cc.length; i++){
							if (cc[i].field == field){
								newcc.push(c);
							}
							newcc.push(cc[i]);
						}
						columns[0] = newcc;
					}
					saveCookie(columns);
				}
			});
		}
	}); 


$.extend($.fn.datagrid.methods, {
    /**
     * 开打提示功能（基于1.3.3+版本）
     * @param {} jq
     * @param {} params 提示消息框的样式
     * @return {}
     */
    doCellTip:function (jq, params) {
        function showTip(showParams, td, e, dg) {
            //无文本，不提示。
            if ($(td).text() == "") return;
            params = params || {};
            var options = dg.data('datagrid');
            
            var styler = 'style="';
            if(showParams.width){
            	styler = styler + "width:" + showParams.width + ";";
            }
            if(showParams.maxWidth){
            	styler = styler + "max-width:" + showParams.maxWidth + ";";
            }
            if(showParams.minWidth){
            	styler = styler + "min-width:" + showParams.minWidth + ";";
            }
            styler = styler + '"';
            showParams.content = '<div class="tipcontent" ' + styler + '>' + showParams.content + '</div>';
            $(td).tooltip({
                content:showParams.content,
                trackMouse:true,
                position:params.position,
                onHide:function () {
                    $(this).tooltip('destroy');
                },
                onShow:function () {
                    var tip = $(this).tooltip('tip');
                    if(showParams.tipStyler){
                        tip.css(showParams.tipStyler);
                    }
                    if(showParams.contentStyler){
                        tip.find('div.tipcontent').css(showParams.contentStyler);
                    }
                }
            }).tooltip('show');
        };
        return jq.each(function () {
            var grid = $(this);
            var options = $(this).data('datagrid');
    		var opts = grid.datagrid('options');
            if (!options.tooltip) {
                var panel = grid.datagrid('getPanel').panel('panel');
                panel.find('.datagrid-body').each(function () {
                    var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;
                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').undelegate('td', 'mouseenter').undelegate('td', 'mouseleave').delegate('td[field]', {
                        'mouseenter':function (e) {
                            //if($(this).attr('field')===undefined) return;
                            //if ($(this).attr('title') !== undefined) return;
                            var that = this;
                            var setField = null;
                            if(params.specialShowFields && params.specialShowFields.sort){
                                for(var i=0; i<params.specialShowFields.length; i++){
                                    if(params.specialShowFields[i].field == $(this).attr('field')){
                                        setField = params.specialShowFields[i];
                                    }
                                }
                            }
                            //if(setField==null){
                            //}else{
                            
                            	var td = $(this);
                            	options.factContent = td.find(">div").clone().css({'margin-left':'-5000px', 'width':'auto', 'display':'inline', 'position':'absolute'}).appendTo('body')
                            	var contentWidth = options.factContent.width();
                            	if (contentWidth > td.width()){
                            		if ($(this).attr('title') !== undefined) return;
                            		td.attr("title", td.text());
                            		td.poshytip({
                            			className: 'tip-yellowsimple',
                            			showTimeout: 200,
                            			alignTo: 'target',
                            			alignX: 'right',
                            			alignY: 'center',
                            			offsetX: 5,
                            			allowTipHover: false
                            		}).poshytip('showDelayed', 200);
                            		td.removeAttr("title");
                            	}else{
                            		td.poshytip('destroy');
                            		td.removeAttr("title");
                            	}
                            	
                            //}
                        	options.tooltip = true;
                        },
                        'mouseleave':function (e) {
                            if (options.factContent) {
                                options.factContent.remove();
                                options.factContent = null;
                            }
                        }
                    });
                });
            }
        });
    },
    /**
     * 关闭消息提示功能（基于1.3.3版本）
     * @param {} jq
     * @return {}
     */
    cancelCellTip:function (jq) {
        return jq.each(function () {
            var data = $(this).data('datagrid');
            if (data.factContent) {
                data.factContent.remove();
                data.factContent = null;
            }
            var panel = $(this).datagrid('getPanel').panel('panel');
            panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')
        });
    }
});
