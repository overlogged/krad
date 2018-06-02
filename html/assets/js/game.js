var state_preload = {
  preload: function () {
    game.load.image('background', 'assets/img/background1.png');
    game.load.image('input', 'assets/img/inputbox1.png', 20, 20);
    game.load.spritesheet('button', 'assets/buttons/button1.png', 100, 34);
    game.load.bitmapFont('chiller', 'assets/fonts/chiller.png', 'assets/fonts/chiller.xml');
    game.load.image('figure1', 'assets/img/figure/figure1.png');
    game.load.image('figure2', 'assets/img/figure/figure2.png');
    game.load.image('figure3', 'assets/img/figure/figure3.png');
    game.load.image('figure4', 'assets/img/figure/figure4.png');
    game.load.image('figure5', 'assets/img/figure/figure5.png');
    game.load.image('figure6', 'assets/img/figure/figure6.png');

    game.load.onLoadComplete.add(function () {
      game.state.start('login');
    })
  },
}
var game = new Phaser.Game(800, 600, Phaser.AUTO, 'container');

game.state.add('preload', state_preload);

game.state.add('login', state_login);
game.state.add('register', state_register);
game.state.add('forget', state_forget);
game.state.add('changepw',state_changepw);
game.state.add('setpw',state_setpw);
game.state.add('changeprofile', state_changeprofile);
game.state.start('preload');