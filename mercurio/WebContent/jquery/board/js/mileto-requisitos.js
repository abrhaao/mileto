
function verificaAjax() {
	var support4Ajax	=	false;
	try {
		if (window.XMLHttpRequest) {
			support4Ajax	=	 true;
		}
	}
	catch(err) {
		//document.getElementById("demo").innerHTML = err.message;
		support4Ajax	=	false;
	}
	//alert("ajax = " + support4Ajax);
	return support4Ajax;
}

function verificaJquery() {
	var support4Jquery	=	false;
	try {
		if  (
	            ( !Array.prototype.indexOf ) ||
	            ( !Array.prototype.forEach ) ||
	            ( !String.prototype.indexOf ) ||
	            ( !String.prototype.trim ) ||                
	            ( !Function.prototype.bind ) ||
	            ( !Object.keys ) ||
	            ( !Object.create ) ||
	            ( !JSON ) ||
	            ( !JSON.stringify ) ||
	            ( !JSON.stringify.length ) ||
	            ( JSON.stringify.length < 3 )
	        )
	    {
			support4Jquery	=	false;
	    } else { 
	    	support4Jquery	=	true;
	    }
	}
	catch(err) {
		support4Jquery	=	false;
	}
	//alert("jquery = " + support4Jquery);
	return support4Jquery;
}


function verificaMath1() {
	var valorzao = 0;
	try {
		 valorzao = Math.floor ( 9.9999999 );
	}
	catch(err) {
		return false;
	}
	if ( valorzao == 9 ) {
		return true;
	} else { 
		return false;
	}
}

function verificaMath2() {
	var valorzao = 0;
	try {
		 valorzao = Math.trunc ( 9.9999999 );
	}
	catch(err) {
		return false;
	}
	if ( valorzao == 9 ) {
		return true;
	} else { 
		return false;
	}
}


function verificaLocalStorage() {
	var e="modernizr";
	try {
		localStorage.setItem(e,e);
		localStorage.removeItem(e)
	} catch(n) {
		return false;
	}
	return true;
}

function verificaSessionStorage() {
	var e="modernizr";
	try {
		sessionStorage.setItem(e,e);
		sessionStorage.removeItem(e)
	} catch(n) {
		return false;
	}
	return true;
}

function verifyWebSqlDatabase() {
	try { 
		var db = openDatabase('mydb', '1.0', 'Test DB', 2 * 1024 * 1024);
	} catch(n)  {
		return false;
	}
	return true;
}

/**
function verificaFuncoesTempo() {
	try {
		adddlert("Welcome guest!");
	}
	catch(err) {
		document.getElementById("demo").innerHTML = err.message;
	}
	return false;
}



/**
 * jQuery Timer doesn't natively act like a stopwatch, it only
 * aids in building one.  You need to keep track of the current
 * time in a variable and increment it manually.  Then on each
 * incrementation, update the page.
 *
 * The increment time for jQuery Timer is in milliseconds. So an
 * input time of 1000 would equal 1 time per second.  In this
 * example we use an increment time of 70 which is roughly 14
 * times per second.  You can adjust your timer if you wish.
 *
 * The update function converts the current time to minutes,
 * seconds and hundredths of a second.  It then outputs that to
 * the stopwatch element, $stopwatch, and then increments. Since
 * the current time is stored in hundredths of a second so the
 * increment time must be divided by ten.
 */