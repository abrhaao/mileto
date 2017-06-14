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
    var Example1 = new (function() {
        var $stopwatch, // Stopwatch element on the page
            incrementTime = 2000, // Timer speed in milliseconds
            currentTime = 0, // Current time in hundredths of a second
            updateTimer = function() {
                $stopwatch.html(formatTime(currentTime));
                currentTime += incrementTime / 10;
                
				$.ajax({
                            type: 'GET',
                            url: "http://10.80.80.4:8080/mercurio/api/recuperaProgramacaoVendas?enterprise=DEMO",
                            dataType: "json",
                            async: true,
                            beforeSend: function(){
								$('#ajax-loader').css("visibility", "visible");						  
                            },
                            success: function (resultResponseVendas) {
                                console.log("JSON VENDAS = Deu certo");	
                                
                            },
                            error: function (request,error) {
                                console.log("JSON CARREGAMENTO = Deu errado");
                            }
                        });
				
				
				
				
				
				
				
				
				
				
				
				
                $( "span" ).filter(".quadroPedidoVenda").each(function( ix ) {
                        var carregamentoPedido	=	$(this).html();
                        /**
                        $.ajax({
                            type: 'GET',
                            url: "http://10.80.80.4:8080/mercurio/api/recuperaStatusCarregamento?enterprise=9001&filial=01&pedido=" + carregamentoPedido,
                            dataType: "json",
                            async: true,
                            beforeSend: function(){
                            $('#ajax-loader').css("visibility", "visible");						  
                            },
                            success: function (resultResponseCarregamento) {
                                console.log("JSON CARREGAMENTO = Deu certo");	
                                $( "span" ).filter("#PV-" + resultResponseCarregamento.pedido + "-STATUS" ).html( resultResponseCarregamento.status );														
                            },
                            error: function (request,error) {
                                console.log("JSON CARREGAMENTO = Deu errado");
                            }
                        });
                        **/
                        
                        archivos = [ 'cagto14.json', 'cagto15.json', 'cagto16.json' ]
                        
                        $.each(archivos, function(i, archivo ) {
                                    
                                $.getJSON( archivo , function(resultResponseCarregamento) {
                                    console.log("JSON CARREGAMENTO = Deu certo");	
                                    $( "span" ).filter("#PV-" + resultResponseCarregamento.pedido + "-STATUS" ).html( resultResponseCarregamento.status );		
                                    $( "span" ).filter("#PV-" + resultResponseCarregamento.pedido + "-PLACA" ).html( resultResponseCarregamento.placa );
                                    $( "span" ).filter("#PV-" + resultResponseCarregamento.pedido + "-VEICULO" ).html( resultResponseCarregamento.veiculo );	
                                    $( "span" ).filter("#PV-" + resultResponseCarregamento.pedido + "-PILOTO" ).html( resultResponseCarregamento.motorista );		
                                    $( "span" ).filter("#PV-" + resultResponseCarregamento.pedido + "-TRANSPORTE" ).html( resultResponseCarregamento.transportadora );	
                                    $( "span" ).filter("#PV-" + resultResponseCarregamento.pedido + "-LOCALIZA" ).html( resultResponseCarregamento.doca );	
                                    $( "span" ).filter("#PV-" + resultResponseCarregamento.pedido + "-PRODUTO" ).html( resultResponseCarregamento.produto );		
                                });
                        
                        })
                        
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
    var Example3 = new (function() {
        var $galleryImages, // An array of image elements
            $timeRemaining, // Usually hidden element to display time when paused
            imageId = 0, // Which image is being shown
            incrementTime = 2500,
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


    /**
    * Example 4 is as simple as it gets.  Just a timer object and
    * a counter that is displayed as it updates.
    */
    var count = 0,
        timer = $.timer(function() {
            count++;
            $('#counter').html(count);
        });
    timer.set({ time : 1000, autostart : true });


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
