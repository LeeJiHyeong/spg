var mainMenu = (function() {

	var $listItems = $( '#mainmenu > ul > li.has-submenu' ),
		$menuItems = $listItems.children( 'a' ),
		$body = $( 'body' ),
		current = -1;

	function init() {
		$menuItems.on( 'mouseenter', open );
		$listItems.on( 'click', function( event ) { event.stopPropagation(); } );
	}

	function open( event ) {
		var $item = $( event.currentTarget ).parent( 'li.has-submenu' ),
			idx = $item.index();
		
		if( current !== idx ){
			$listItems.removeClass('mainmenu-open');
		}
		
		if($item.length != 0){
			if( current !== -1 ) {
				$listItems.eq( current ).removeClass( 'mainmenu-open' );
			}

			if( current === idx ) {
				$item.addClass( 'mainmenu-open' ).on( 'mouseleave', allClose );
				current = -1;
			}
			else {
				$item.addClass( 'mainmenu-open' ).on( 'mouseleave', allClose );
				current = idx;
				$body.off( 'click' ).on( 'click', close );
			}
			return false;
		}
		else window.location = $item.find('a').attr('href');
	}

	function close( event ) {
		$listItems.eq( current ).removeClass( 'mainmenu-open' );
		current = -1;
	}
	
	function allClose (event) {
		$listItems.removeClass('mainmenu-open');
	}

	return { init : init };

})();
