var state_help = {
    preload: function() {
        var background = game.add.image(0, 0, 'userbackground');
        background.smoothed = true;
        background.height = game.height;
        background.width = game.width;
        game.add.button(665, 525, 'button', go_backloginhelp, this, 1, 0, 2, 0);
        game.add.bitmapText(690, 530, 'chiller', 'Back', 28);
        game.add.bitmapText(350, 25, 'chiller', 'Help', 75);

        var text_1='Prepare for the game:';
        var text_2='Before you started, characters should be chosen, team should be decided and map  ';
        var text_3='ought to be selected.Characters are chosen by players,and team is divided arbitrarily.';
        var text_4='The properties of characters:';
        var text_5='MOTILITY represents how much map unit you can cover in one turn';
        var text_6='FIREPOWER represents how much energy will be used on firing';
        var text_7='RANGE represents how far you bullet can reach';
        var text_8='HEALTHPOINT represents you upper limit of energy and hand cards';
        var text_9='SKILLS represents the unique ability that only your character owns';
        var text_10='Card System:：';
        var text_11=' Players will get a set of hand cards, which include Strategy Cards (Move, Fire ';
        var text_12='and use Skills)and Gamble Cards(Rock, Scissors and Paper)'
        var text_13='The game will cycle in three stages before one side wins or the game becomes to ';
        var text_14='to long to continue ---- PREPARE, GAMBLE, ACTION(or ACCOUNT)';
        game.add.bitmapText(80, 85, 'chiller', text_1, 45);
        game.add.bitmapText(60, 115, 'chiller', text_2, 34);
        game.add.bitmapText(10, 145, 'chiller', text_3, 34);
        game.add.bitmapText(80, 175, 'chiller', text_4, 45);
        game.add.bitmapText(60, 210, 'chiller', text_5, 34);
        game.add.bitmapText(60, 245, 'chiller', text_6, 34);
        game.add.bitmapText(60, 280, 'chiller', text_7, 34);
        game.add.bitmapText(60, 315, 'chiller', text_8, 34);
        game.add.bitmapText(60, 350, 'chiller', text_9, 34);
        game.add.bitmapText(80, 385, 'chiller', text_10, 45);
        game.add.bitmapText(60, 425, 'chiller', text_11, 34);
        game.add.bitmapText(10, 465, 'chiller', text_12, 34);
        game.add.bitmapText(60, 495, 'chiller', text_13, 34);
        game.add.bitmapText(10, 525, 'chiller', text_14, 34);

        show("help");
    }
  }

  function go_backloginhelp(){
      game.state.start('userinterface');
  }