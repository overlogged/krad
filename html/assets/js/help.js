var state_help = {
    preload: function() {
        var background = game.add.image(0, 0, 'userbackground2');
        var font_1='35px 华文行楷';
        var font_2='20px 华文仿宋';
        var textFill='#ffffff';
        background.smoothed = true;
        background.height = game.height;
        background.width = game.width;
        backBtn = game.add.button(665, 550, 'button', go_backloginhelp, this, 1, 0, 2, 0);
        game.add.bitmapText(690, 555, 'chiller', 'Next', 28);
        game.add.text(350, 10, 'Help', {
            font:"60px pristina",
            fill:"#ffffff"
        });
        tooltip10 = game.add.sprite(735, 510, 'tooltip10');
        tooltip10.inputEnabled = true;

        var text_1='游戏开始：';
        var text_2='提示栏信息获得分配英雄 ';
        var text_3='红色字样消息框获取局面信息和历史记录';
        var text_4='英雄获取后地图界面上出现英雄（头上有悬浮蓝色箭头）';
        var text_5='鼠标悬置于英雄模型上出现状态栏';
        var text_6='游戏运行流程：';
        var text_7='准备阶段：分为决策阶段、明牌选取';
        var text_8='出拳阶段：进行出拳牌的操作';
        var text_9='最后进入结算与弃牌阶段';
        var text_10='说明：各阶段推进依靠牌区右侧的确定按钮，请根据提示栏信息操作';
        var text_11=' 准备阶段：';
        var text_12='决策阶段：选择使用移动牌或者直接跳过决策'
        var text_13='（提示：目前没有开火和技能牌，玩家请勿使用）';
        var text_14='移动说明：使用移动牌，将消耗此时存储的能量，一次可移动多格';
        var text_15='（提示：注意本回合获取的能量不能参与本回合的移动结算）';
        game.add.text(80, 65, text_1,{
            font:font_1,
            fill:"#FFF68F"
        });
        game.add.text(50, 100, text_2, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 130, text_3, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 160, text_4, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 195, text_5, {
            font:font_2,
            fill:textFill
        });
        game.add.text(80, 230, text_6, {
            font:font_1,
            fill:"#FFF68F"
        });
        game.add.text(50, 265, text_7, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 300, text_8, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 335, text_9, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 370, text_10, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 410, text_11, {
            font:font_1,
            fill:"#FFF68F"
        });
        game.add.text(50, 445, text_12, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 480, text_13, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 510, text_14, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 540, text_15, {
            font:font_2,
            fill:textFill
        });

        show("help");
        tooltip_help();
    },
    update: function(){
        if(star){
            if(backBtn.input.pointerOver()){
                tooltip10.alpha=0.7;
              }
              else{
                tooltip10.alpha=0;
              }
        }
    }
  }

  function go_backloginhelp(){
      game.state.start('help2');
  }

  function tooltip_help(){
      star = true;
  }