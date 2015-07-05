(function($){

        var msgtpl = $('#msgtpl').html();
        var lastmsg = false;
        $('#msgtpl').remove();

        var socket = io.connect('http://localhost:1337');

        $('#loginform').submit(function(event){
          event.preventDefault();
          if($('#username').val() == ''){
            alert('Vous devez entrer un pseudo !');
          }else{
            socket.emit('login', {username: $('#username').val(), mail: $('#mail').val()});
          }
          return false;
        });

		/**
		* Envois de message
		**/
        $('#form').submit(function(event){
          event.preventDefault();
          socket.emit('newmsg', {message : $('#message').val() });
          $('#message').val('');
          $('#message').focus();
          return false;
        });
		
        socket.on('newmsg', function(message){
          if(lastmsg != message.user.id){
            $('#messages').append('<div class="sep"></div>');
            lastmsg = message.user.id;
          }
          $('#messages').append( '<div class="message">' + Mustache.render(msgtpl, message) + '</div>' );
          $("#messages").animate({ scrollTop: $("#messages").prop("scrollHeight") }, 500);
        });

        socket.on('logged', function(){
          $('#login').fadeOut();
		  $('#message').focus();
        });

		/**
		* Gestion des connectés
		**/
        socket.on('newusr', function(user){
          $('#users').append('<img src="' + user.avatar + '" id="' + user.id + '">')
        });

        socket.on('disusr', function(user){
          $('#'+user.id).remove();
        })

})(jQuery);