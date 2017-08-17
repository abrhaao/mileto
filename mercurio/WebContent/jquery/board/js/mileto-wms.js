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

var xServer         //=   "http://10.8.0.232:8081/mercurio"   
var xMaxByScreen    //=6
var qMaxByScreen   // =   6;        
var isThereAlertOnScreen = false;

try {

	do {
		xServer				=	localStorage.getItem("myServer");
		xMaxByScreen		=	localStorage.getItem("maxByScreen");
		xEnterprise			=	localStorage.getItem("myEnterprise");
		xMaxHours			=	localStorage.getItem("maxHours");

		if ( xServer == null ) {
			//localStorage.setItem("myServer", "http://10.8.0.232:8081/mercurio");
			localStorage.setItem("myServer", "http://localhost:8080/mercurio");
		}
		if ( xMaxByScreen == null ) {
			localStorage.setItem("maxByScreen", "7");
		}
		if ( xEnterprise == null ) {
			localStorage.setItem("myEnterprise", "DEMO");
		}	
		if ( xMaxHours == null ) {
			localStorage.setItem("maxHours", "4");
		}			
	} while ( xServer == null || xMaxByScreen == null );
	
	qMaxByScreen = xMaxByScreen;

} catch(error) {
	alert("ruim");
}

//alert( localStorage.getItem("myServer") );

var Example1 = new (function() {
	var $stopwatch, // Stopwatch element on the page
	incrementTime = 8000, // Timer speed in milliseconds
	currentTime = 0, // Current time in hundredths of a second
	updateTimer = function() {
		$stopwatch.html(formatTime(currentTime));
		currentTime += incrementTime / 10;

		//var carregamentoPedido	=	$(this).html();
					
		
		
		
		
		
		////////////////////////
		
		
		
		
		////////////////////////////////////////////////////
		if ( count % 5 == 1 ) {
		
			$.ajax({
				type: 'GET',
				url: xServer + "/api/carregamento/recuperaProgramacaoVendas?enterprise=" + localStorage.getItem("myEnterprise"),
				dataType: "json",
				async: true,
				beforeSend: function(){
					$('#ajax-loader').css("visibility", "visible");						  									
				},
				success: function (resultResponseVendas) {
	
					sessionStorage.setItem('myProgramacao', JSON.stringify(resultResponseVendas));
					
					console.log("Total de Registros = " + resultResponseVendas.length ) ;                           
	
					plotaCarregamentos ( resultResponseVendas );
					
					/**
					var qTelas  =   Math.floor ( (resultResponseVendas.length  / qMaxByScreen) + 0.9999 )  ;                        
					var qPagina =   ( count % qTelas ) + 1 ;
					

					$("#infoBarTelas").html(qTelas);
					$("#infoBarPagina").html(qPagina);
					$("#infoBarRegistros").html(resultResponseVendas.length);
					
					$.each(resultResponseVendas, function(i, resultResponseCarregamento ) {
	
						//console.log(resultResponseCarregamento.highlight)              
	
						if ( i >= ( ( count % qTelas) * qMaxByScreen ) && i < ( (count % qTelas + 1) * qMaxByScreen ) ) {                            
	
							var cssClasseStatus = "";
	
	
	
							if ( resultResponseCarregamento.highlightStatus === undefined ) {
								//alert ("Não Temos um inidividuo com highlightStatus" );
							} else {
								cssClasseStatus	= resultResponseCarregamento.highlightStatus ;    								
							}
	
							if (resultResponseCarregamento.highlight === undefined || resultResponseCarregamento.highlight === null) {
								dataSetContent +=  ' <tr> '	;									
							} else { 
								dataSetContent +=  ' <tr class="rwd-highlight-' + resultResponseCarregamento.highlight + '"> ';
							}
	
							dataSetContent +=  '<td><span><img style="width: 120px; height: 55px;" src="' + resultResponseCarregamento.icone + '"></span><br>';
							dataSetContent +=  '  <span class="rwd-span-transportadora">' + resultResponseCarregamento.transportadora + '</SPAN>';
							dataSetContent +=  '</td>';
							dataSetContent +=  '<td><a href="#" title="' + resultResponseCarregamento.cliente + '" class="tooltip">';
							dataSetContent +=  '<span id="PV-000015-01-PLACA">'+ resultResponseCarregamento.placa + '</span><br>';
							dataSetContent +=   '<span class="rwd-span-motorista">' + resultResponseCarregamento.motorista + '</SPAN>';
							dataSetContent +=  '</a>';
							dataSetContent +=  '</td>';
							dataSetContent +=  '<td><span>' + resultResponseCarregamento.pedido + '</span><br><span class="rwd-dados-produto">' + resultResponseCarregamento.produto + '</span></td>    ';
							dataSetContent +=  '<td><span>'+ resultResponseCarregamento.doca + '</SPAN></td>';
							dataSetContent +=  '<td><span class="rwd-span-orientacoes ' + cssClasseStatus+ '">' + resultResponseCarregamento.status + '</span><br><span class="rwd-dados">'+ resultResponseCarregamento.hora + '</span></td>';
							//dataSetContent +=  '<td><span class="rwd-span-orientacoes">' + resultResponseCarregamento.instrucao + '</span>' + '</td>';
							dataSetContent +=  '<td>' ;
							//if ( ! ( resultResponseCarregamento.produtoTes.equals(" ") ) ) {
							dataSetContent +=   '<span class="rwd-span-orientacoes" style="font-size-adjust: 0.4;"> TES ' + resultResponseCarregamento.produtoTes + '</span><br>';
							//} 
							if ( ! ( resultResponseCarregamento.produtoOnu.trim() === ("") ) ) {
								dataSetContent +=   '<span class="rwd-span-orientacoes" style="font-size-adjust: 0.4;"> ONU ' + resultResponseCarregamento.produtoOnu + '</span>';
							}                                 
							dataSetContent += '</td>';
							dataSetContent +=  '</tr>';
							$("#dataSetProgramacaoVenda").html(dataSetContent);		                                                        
						}
					})
					**/
	
				},
				error: function (request,error) {
					console.log("JSON CARREGAMENTO = Deu errado");
					//alert("Servidor de informações não está não respondendo.");
				}
			});
		
		} else {
			
			//printStorage()
			var retrievedObject = sessionStorage.getItem('myProgramacao');
			var resultResponseVendas = JSON.parse(retrievedObject);
			
			plotaCarregamentos ( resultResponseVendas );				
		}

		///////////////////////
		$.ajax({
			type: 'GET',
			url: xServer + "/api/messagemanager/informa?enterprise=" + localStorage.getItem("myEnterprise") + "&assunto=CARREGAMENTOS",
			dataType: "json",
			async: true,
			beforeSend: function(){

			},
			success: function (resultResponseComunicado) {

				if (resultResponseComunicado.mensagem === undefined || resultResponseComunicado.mensagem === null)	{
					$('.rwd-table').css("opacity", "1.0");		
					$('#board-ajax-informativo').css("visibility", "hidden");	
					
					isThereAlertOnScreen = false;  
				} else {                                            
					if (isThereAlertOnScreen == false) {
						var myBuzzerNotification    =   document.getElementById('buzzer');
						myBuzzerNotification.load();  
						myBuzzerNotification.play();  
								
						/** Fala ... **/
						setTimeout(
									  function() 
									  {
										  if ( resultResponseComunicado.fala != null ) {
												responsiveVoice.setDefaultVoice("Portuguese Female");						
												responsiveVoice.speak( resultResponseComunicado.fala );
										  }
									  }, 2000);
						
						isThereAlertOnScreen = true;                            
					}

					messageContent = resultResponseComunicado.mensagem		                        					                          							
					$('.rwd-table').css("opacity", "0.2");					
					$("#board-ajax-informativo").html(messageContent);		
					$('#board-ajax-informativo').css("visibility", "visible");											
				}


			},
			error: function (request,error) {
				$('#board-ajax-informativo').css("visibility", "hidden");
				$('.rwd-table').css("opacity", "1.0");	
				console.log("JSON CARREGAMENTO = Deu errado");
			}
		});




	},
	init = function() {
		$stopwatch = $('#stopwatch');
		Example1.Timer = $.timer(updateTimer, incrementTime, true);
	};
	this.resetStopwatch = function() {
		currentTime = 0;
		this.Timer.stop().once();
	};
	$(init);
});


/**
 * Example 2 is similar to example 1.  The biggest difference
 * besides counting up is the ability to reset the timer to a
 * specific time.  To do this, there is an input text field
 * in a form.
 */
/**
    var Example2 = new (function() {
        var $countdown,
            $form, // Form used to change the countdown time
            incrementTime = 70,
            currentTime = 30000,
            updateTimer = function() {
                $countdown.html(formatTime(currentTime));
                if (currentTime == 0) {
                    Example2.Timer.stop();
                    timerComplete();
                    Example2.resetCountdown();
                    return;
                }
                currentTime -= incrementTime / 10;
                if (currentTime < 0) currentTime = 0;
            },
            timerComplete = function() {
                alert('Example 2: Countdown timer complete!');
            },
            init = function() {
                $countdown = $('#countdown');
                Example2.Timer = $.timer(updateTimer, incrementTime, true);
                $form = $('#example2form');
                $form.bind('submit', function() {
                    Example2.resetCountdown();
                    return false;
                });
            };
        this.resetCountdown = function() {
            var newTime = parseInt($form.find('input[type=text]').val()) * 100;
            if (newTime > 0) {currentTime = newTime;}
            this.Timer.stop().once();
        };
        $(init);
    });
 **/

/**
 * The purpose of this example is to demonstrate the original
 * reason I built jQuery timer, to preserve the time remaining
 * when pausing a timer.
 *
 * Notice the increment time is set to 2500.  If you click
 * 'Play / Pause' immediately after an image changes, you should
 * see a value close to 2.5 seconds remaining.  Once you click
 * play again, the timer continues where it ended instead of
 * starting over again.
 */

/**
    var Example3 = new (function() {
        var $galleryImages, // An array of image elements
            $timeRemaining, // Usually hidden element to display time when paused
            imageId = 0, // Which image is being shown
            incrementTime = 4000,           //Acho que tem que ser o mesmo tempo do primeiro
            updateTimer = function() {
                $galleryImages.eq(imageId).stop(true,true).fadeOut(500);
                imageId++;
                if (imageId > $galleryImages.length) {
                    imageId = 0;
                }
                $galleryImages.eq(imageId).stop(true,false).fadeIn(500);
            },
            init = function() {
                $galleryImages = $('.galleryImages img');
                $timeRemaining = $('#timeRemaining');
                Example3.Timer = $.timer(updateTimer, incrementTime, true).once();
            };
        this.toggleGallery = function() {
            if (this.Timer.isActive) {
                this.Timer.pause();
                var remaining = this.Timer.remaining / 1000;
                $timeRemaining.html(remaining + " seconds remaining.");
            }
            else {
                this.Timer.play();
                $timeRemaining.html("<br/>");
            }
        };
        $(init);
    });
 **/

/**
 * Example 4 is as simple as it gets.  Just a timer object and
 * a counter that is displayed as it updates.
 */
var count = 0,
timer = $.timer(function() {
	count++;
	$('#counter').html(count);

var now = new Date(),
     now = now.getHours()+':'+now.getMinutes();
 	 $('#time').html(now);
});
timer.set({ time : 8000, autostart : true });               //Acho que tem que ser o mesmo tempo do primeiro


// Common functions
function pad(number, length) {
	var str = '' + number;
	while (str.length < length) {str = '0' + str;}
	return str;
}
function formatTime(time) {
	var min = parseInt(time / 6000),
	sec = parseInt(time / 100) - (min * 60),
	hundredths = pad(time - (sec * 100) - (min * 6000), 2);
	return (min > 0 ? pad(min, 2) : "00") + ":" + pad(sec, 2) + ":" + hundredths;
}

function printStorage () {
    var allStrings = '';
    for(var key in window.localStorage){
        if(window.localStorage.hasOwnProperty(key)){
            allStrings += window.localStorage[key];
        }	            
    }
    alert ( "Local Storage Space: " + allStrings ? 3 + ((allStrings.length*16)/(8*1024)) + ' KB' : 'Empty (0 KB)' );
    return allStrings ? 3 + ((allStrings.length*16)/(8*1024)) + ' KB' : 'Empty (0 KB)';
};

function plotaCarregamentos( resultResponseVendas) {
	
	var dataSetContent	=	"";
	
	dataSetContent += ' <tr style="background: rgb(48, 64, 80) none repeat scroll 0% 0%; color: yellow;">   '
		dataSetContent += ' <th>Transportadora</th>'
		dataSetContent += ' <th>Veículo / Motorista</th>'
		dataSetContent += ' <th>Pedido</th>    '
		dataSetContent += ' <th>Localização</th>'
		dataSetContent += ' <th>Status</th>'
		dataSetContent += ' <th>Instrução</th>'
		dataSetContent += ' </tr>'	
			
			var qTelas  =   Math.floor ( (resultResponseVendas.length  / qMaxByScreen) + 0.9999 )  ;                        
	var qPagina =   ( count % qTelas ) + 1 ;
	//MATH.Trunc não está implementado na TELEVISÃO

	$("#infoBarTelas").html(qTelas);
	$("#infoBarPagina").html(qPagina);
	$("#infoBarRegistros").html(resultResponseVendas.length);
	
	$.each(resultResponseVendas, function(i, resultResponseCarregamento ) {

		if ( i >= ( ( count % qTelas) * qMaxByScreen ) && i < ( (count % qTelas + 1) * qMaxByScreen ) ) {                            

			var cssClasseStatus = "";

			if ( resultResponseCarregamento.highlightStatus === undefined ) {
				//alert ("Não Temos um inidividuo com highlightStatus" );
			} else {
				cssClasseStatus	= resultResponseCarregamento.highlightStatus ;    								
			}

			if (resultResponseCarregamento.highlight === undefined || resultResponseCarregamento.highlight === null) {
				dataSetContent +=  ' <tr> '	;									
			} else { 
				dataSetContent +=  ' <tr class="rwd-highlight-' + resultResponseCarregamento.highlight + '"> ';
			}

			dataSetContent +=  '<td><span><img style="width: 120px; height: 55px;" src="' + resultResponseCarregamento.icone + '"></span><br>';
			dataSetContent +=  '  <span class="rwd-span-transportadora">' + resultResponseCarregamento.transportadora + '</SPAN>';
			dataSetContent +=  '</td>';
			dataSetContent +=  '<td><a href="#" title="' + resultResponseCarregamento.cliente + '" class="tooltip">';
			dataSetContent +=  '<span id="PV-000015-01-PLACA">'+ resultResponseCarregamento.placa + '</span><br>';
			dataSetContent +=   '<span class="rwd-span-motorista">' + resultResponseCarregamento.motorista + '</SPAN>';
			dataSetContent +=  '</a>';
			dataSetContent +=  '</td>';
			dataSetContent +=  '<td><span>' + resultResponseCarregamento.pedido + '</span><br><span class="rwd-dados-produto">' + resultResponseCarregamento.produto + '</span></td>    ';
			dataSetContent +=  '<td><span>'+ resultResponseCarregamento.doca + '</SPAN></td>';
			dataSetContent +=  '<td><span class="rwd-span-orientacoes ' + cssClasseStatus+ '">' + resultResponseCarregamento.status + '</span><br><span class="rwd-dados">'+ resultResponseCarregamento.hora + '</span></td>';
			dataSetContent +=  '<td>' ;
			dataSetContent +=   '<span class="rwd-span-orientacoes" style="font-size-adjust: 0.4;"> TES ' + resultResponseCarregamento.produtoTes + '</span><br>';
			if ( ! ( resultResponseCarregamento.produtoOnu.trim() === ("") ) ) {
				dataSetContent +=   '<span class="rwd-span-orientacoes" style="font-size-adjust: 0.4;"> ONU ' + resultResponseCarregamento.produtoOnu + '</span>';
			}                                 
			dataSetContent += '</td>';
			dataSetContent +=  '</tr>';
			$("#dataSetProgramacaoVenda").html(dataSetContent);		   
	
		}
	});
	
	
}
