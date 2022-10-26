(function($) {

	const $window = $(window), $body = $('body'), $banner = $('#banner'), $header = $('#header');

	$window.on('load', function() {
		window.setTimeout(function() {
			$body.removeClass('is-preload');
		}, 100);
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

	$('#menu').append('<a href="#menu" class="close"></a>').appendTo($body)
			.panel({
				delay : 500,
				hideOnClick : true,
				hideOnSwipe : true,
				resetScroll : true,
				resetForms : true,
				side : 'right',
				target : $body,
				visibleClass : 'is-menu-visible'
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