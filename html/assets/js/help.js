var state_help = {
    preload: function() {
        var background = game.add.image(0, 0, 'background');
        background.smoothed = true;
        background.height = game.height;
        background.width = game.width;
        game.add.button(600, 490, 'button', go_backloginhelp, this, 1, 0, 2, 0);
        game.add.bitmapText(620, 495, 'chiller', 'Back', 28);
        game.add.bitmapText(350, 30, 'chiller', 'Help', 70);

        var text_1='游戏准备：';
        var text_2='开始游戏前，应当进行角色选定与阵营划分及地图选择，';
        var text_3='用户决定角色，用随机分配的方式划出感染者阵营。';
        var text_4='关于角色：';
        var text_5='机动性：单次运动所能行进的最大距离';
        var text_6='火力：单次开火所能消耗的最大能量';
        var text_7='射程：开火能够击中的最远距离';
        var text_8='生命值：能够储存的能量上限以及手牌上限';
        var text_9='技能：角色独有的特殊能力';
        var text_10='卡牌系统：';
        var text_11='玩家会持有一系列手牌，手牌分为决策牌（包括移动、开火、';
        var text_12='使用主动技能）和出拳牌（石头、剪刀、布）。'
        var text_13='回合模式：';
        var text_14='分为准备阶段、出拳阶段、行动阶段，往复循环';
        game.add.text(80, 85, text_1);
        game.add.text(60, 115, text_2);
        game.add.text(10, 145, text_3);
        game.add.text(80, 175, text_4);
        game.add.text(10, 205, text_5);
        game.add.text(10, 235, text_6);
        game.add.text(10, 265, text_7);
        game.add.text(10, 295, text_8);
        game.add.text(10, 325, text_9);
        game.add.text(80, 355, text_10);
        game.add.text(60, 385, text_11);
        game.add.text(10, 415, text_12);
        game.add.text(80, 455, text_13);
        game.add.text(10, 495, text_14);

        show("help");
    }
  }

  function go_backloginhelp(){
      game.state.start('userinterface');
  }