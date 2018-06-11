var state_help = {
    preload: function() {
        var background = game.add.image(0, 0, 'background');
        background.smoothed = true;
        background.height = game.height;
        background.width = game.width;
        game.add.button(580, 490, 'button', go_backloginhelp, this, 1, 0, 2, 0);
        game.add.bitmapText(600, 495, 'chiller', 'Back', 28);
    
        show("help");
    }
  }

  function go_backloginhelp(){
      game.state.start('userinterface');
  }