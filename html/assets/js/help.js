var state_help = {
    preload: function() {
        var background = game.add.image(0, 0, 'userbackground2');
        var font_1='35px pristina';
        var font_2='28px pristina';
        var textFill='#ffffff';
        background.smoothed = true;
        background.height = game.height;
        background.width = game.width;
        game.add.button(665, 550, 'button', go_backloginhelp, this, 1, 0, 2, 0);
        game.add.bitmapText(690, 555, 'chiller', 'Back', 28);
        game.add.text(350, 10, 'Help', {
            font:"60px pristina",
            fill:"#ffffff"
        });

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
        game.add.text(80, 65, text_1,{
            font:font_1,
            fill:"#FFF68F"
        });
        game.add.text(60, 105, text_2, {
            font:font_2,
            fill:textFill
        });
        game.add.text(10, 130, text_3, {
            font:font_2,
            fill:textFill
        });
        game.add.text(80, 160, text_4, {
            font:font_1,
            fill:"#FFF68F"
        });
        game.add.text(60, 195, text_5, {
            font:font_2,
            fill:textFill
        });
        game.add.text(60, 230, text_6, {
            font:font_2,
            fill:textFill
        });
        game.add.text(60, 265, text_7, {
            font:font_2,
            fill:textFill
        });
        game.add.text(60, 300, text_8, {
            font:font_2,
            fill:textFill
        });
        game.add.text(60, 335, text_9, {
            font:font_2,
            fill:textFill
        });
        game.add.text(80, 370, text_10, {
            font:font_1,
            fill:"#FFF68F"
        });
        game.add.text(60, 410, text_11, {
            font:font_2,
            fill:textFill
        });
        game.add.text(10, 445, text_12, {
            font:font_2,
            fill:textFill
        });
        game.add.text(60, 480, text_13, {
            font:font_2,
            fill:textFill
        });
        game.add.text(10, 510, text_14, {
            font:font_2,
            fill:textFill
        });

        show("help");
    }
  }

  function go_backloginhelp(){
      game.state.start('userinterface');
  }