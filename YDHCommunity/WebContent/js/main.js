(function($) {

	var $window = $(window), $body = $('body'), $banner = $('#banner'), $header = $('#header');

	$window.on('load', function() {
		$("#loading").removeClass("pre");
		window.setTimeout(function() {
			$("#loading").addClass("task");
		}, 500);
		window.setTimeout(function() {
			$("#loading").removeClass("task");
			$("#loading").addClass("end");
		}, 900); 
	});
	
	breakpoints({
		xlarge : [ '1281px', '1680px' ],
		large : [ '981px', '1280px' ],
		medium : [ '737px', '980px' ],
		small : [ '481px', '736px' ],
		xsmall : [ null, '480px' ]
	});
	
	if (browser.mobile)
		$body.addClass('is-mobile');
	else {
		breakpoints.on('>medium', function() {
			$body.removeClass('is-mobile'); 
		});
		breakpoints.on('<=medium', function() {
			$body.addClass('is-mobile');
		});
	}

	$('.scrolly').scrolly({
		speed : 1500,
		offset : $header.outerHeight()
	});
	 
	$("#change-white-theme").click(function(e) {
		$body.addClass("white-theme");
		$("#menu").toggleClass("toggle");
		$("#content").toggleClass("blur"); 
	});
	
	$("#change-dark-theme").click(function(e) {
		$body.removeClass("white-theme");
		$("#menu").toggleClass("toggle");
		$("#content").toggleClass("blur"); 
	});
	 
  
	$("#menuToggle").click(function(e) {
		$("#menu").toggleClass("toggle");
		$("#content").toggleClass("blur"); 
	});
	 
	$("#content").click(function(e) {
		$("#menu").removeClass("toggle");
		$("#content").removeClass("blur"); 
	});

	if ($banner.length > 0 && $header.hasClass('alt')) {

		$window.on('resize', function() {
			$window.trigger('scroll');
		});

		$banner.scrollex({
			bottom : $header.outerHeight() + 1,
			terminate : function() {
				$header.removeClass('alt');
			},
			enter : function() {
				$header.addClass('alt');
			},
			leave : function() {
				$header.removeClass('alt');
			}
		});

	}

})(jQuery);