$(document).ready(function(){
//    var s1 = [2, 5.12, 8.3, 9.6];
//    var s1 = [2, 5.12, 8.3, 9.6, 12, 15, 20, 23, 24, 19, 7, 2, 6];
//	      ['4',33.6, 'low'],['5',85.9, '<img src=images/runner1.png>'],['8',219.9, null]];
    
//    var s1 = [[2,'17:19'], [5.12,'20:12'], [8.3,'3'], [9.6,'4'], [12,'5'], [15,'6'], [20,'7'], [23,'8'], [24,'9'], [19,'10'], [7,'11'], [2,'12'], [6,'13']];
    var s1 = [['17:19',2],['July',6],['Aug',10],['Sep',7]];
//    var ticks = ['June', 'July', 'Aug', 'Sep'];
    var labels = [null, null, '<img src=../images/runner1.png>'];

    
    
    $.jqplot.config.enablePlugins = true;

    $.jqplot('chartdiv',  [s1], {
	title:'How you finished compared to everyone in the race',
	animate: false,
	seriesDefaults:
	    { renderer:$.jqplot.BarRenderer,
	      rendererOptions: { fillToZero: true},
	      //showMarker: false,
	      //shadow: true,
	      pointLabels: { labels: labels, show: true, escapeHTML: false, location: 'n', ypadding: 3}
	    },

	axesDefaults: { 
	    tickRenderer: $.jqplot.CanvasAxisTickRenderer, 
	    tickOptions: { angle: 0, fontSize: '12pt' }
	},
	axes:{ 
	    //yaxis:{ pad: 1.3,
	//	    label:'&nbsp;', 
	//	    min: 0,
	//	    showTicks: true, 
	//	    tickOptions: { angle: -90, formatString: '' }
	//	  },
	    xaxis:{ renderer: $.jqplot.CategoryAxisRenderer,
//		    label: 'Finish Time',
//		    ticks: ticks,
		    tickOptions: { angle: -30, formatString: '%s' }
		  }
	}
    });
});
