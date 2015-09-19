<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>jstree basic demos</title>
	<style>
	html { margin:0; padding:0; font-size:62.5%; }
	body { max-width:800px; min-width:300px; margin:0 auto; padding:20px 10px; font-size:14px; font-size:1.4em; }
	h1 { font-size:1.8em; }
	.demo { overflow:auto; border:1px solid silver; min-height:100px; }
	</style>
	<link rel="stylesheet" href="/rzrk/css/jstree/style.min.css" />
</head>
<body>

	<div id="frmt" class="demo"></div>
	<script type="text/javascript" src="/rzrk/js/alljs.js"></script>
	<script src="/rzrk/js/jstree/jstree.min.js"></script>
	<script>
       var jsonobj = {}
       $.get("deparment!getAjaxDeparmentTree.action", function(result){
            jsonobj = eval(result);
            var result = convert (jsonobj);
		   	// data format demo
			$('#frmt').jstree({
				'core' : {
					'data' : result.root
				}
			});
       },"json");  
       
	  function convert(jsonobj)
	    {
	        var result = {};
	        for ( var p in jsonobj)
	        {
	            if (p != 'root')
	            {
	                result[p] = jsonobj[p];
	            }
	        }
	        result.root = [];
	        var root = jsonobj.root;
	        for ( var i = 0; i < root.length; i++)
	        {
	            var ri = root[i];
	            ri.text = ri.name;
	            delete ri.name;
	             
	            if (ri.parent != null && ri.parent != 'null' && ri.parent != '')
	            {
	                for ( var j = 0; j < root.length; j++)
	                {
	                    var rj = root[j];
	                    if (rj.id == ri.parent)
	                    {
	                        rj.children = !rj.children ? [] : rj.children;
	                        rj.children.push (ri);
	                        break;
	                    }
	                }
	            }
	             
	            if (ri.parent == null || ri.parent == 'null' || ri.parent == '')
	            {
	                result.root.push (ri);
	            }
	        }
	         
	         
	         
            for(var i in result.root)
		    {
		        if(result.root[i]!=null&&typeof(result.root[i])!="function"&&typeof(result.root[i])=="object")
		        {
		            find(result.root[i]);
		             
		             
		        }
		        delete result.root[i].id;
		        delete result.root[i].parent;
		    } 
	         
	         
	        return result;
	    }
	</script>
</body>
</html>