var state_help2 = {
    preload: function() {
        var background = game.add.image(0, 0, 'userbackground2');
        var font_1='35px 华文行楷';
        var font_2='20px 华文仿宋';
        var textFill='#ffffff';
        background.smoothed = true;
        background.height = game.height;
        background.width = game.width;
        backBtn = game.add.button(665, 550, 'button', go_backloginhelp2, this, 1, 0, 2, 0);
        game.add.bitmapText(690, 555, 'chiller', 'Back', 28);

        tooltip8 = game.add.sprite(735, 510, 'tooltip8');
        tooltip8.inputEnabled = true;

        
        var text_1='明牌说明：玩家选择明牌按钮出拳，明牌出拳会在出拳阶段在消息框中提示给其它';
        var text_2='玩家，其会通过消息选择对应的出拳牌，并且不能在接下来的出拳阶段中使用出拳牌，';
        var text_3='明牌不会消耗，当没有出拳牌的时候必须选择明牌出拳';
        var text_4='出拳阶段：';
        var text_5='消耗出拳牌战胜对手，当出拳胜于对方阵营中至少一名玩家时，本次出拳获胜'
        var text_6='（提示：可同时打出多张相同的出拳牌增加倍率，打出多少出拳牌胜利时就会赢取';
        var text_7='多少点能量）';
        var text_8='蓄能说明：接下获取的能量被存储，便与之后使用';
        var text_9='结算与弃牌阶段：';
        var text_10='1.根据以上信息计算玩家新位置，一次移动多格（称为跳跃）一般不能跨越大格（平';
        var text_11='台），被大格挡住会损失掉一些能量，在图中两个棕色箭头处是允许跳跃的';
        var text_12='2.当感染者和战术小队玩家位于同一格上时，战术小队玩家被感染'
        var text_13='3.弃牌是任意的';
        var text_14='胜利方式：';
        var text_15='战术小队：玩家需要到达最上方的大格取得要素并到最下方的大格（撤离点）';
        var text_16='感染者：感染所有敌人';
        game.add.text(50, 40, text_1,{
            font:font_2,
            fill:textFill
        });
        game.add.text(10, 70, text_2, {
            font:font_2,
            fill:textFill
        });
        game.add.text(10, 100, text_3, {
            font:font_2,
            fill:textFill
        });
        game.add.text(80, 130, text_4, {
            font:font_1,
            fill:"#FFF68F"
        });
        game.add.text(50, 165, text_5, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 200, text_6, {
            font:font_2,
            fill:textFill
        });
        game.add.text(10, 235, text_7, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 270, text_8, {
            font:font_2,
            fill:textFill
        });
        game.add.text(80, 305, text_9, {
            font:font_1,
            fill:"#FFF68F"
        });
        game.add.text(50, 340, text_10, {
            font:font_2,
            fill:textFill
        });
        game.add.text(10, 380, text_11, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 415, text_12, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 450, text_13, {
            font:font_2,
            fill:textFill
        });
        game.add.text(80, 480, text_14, {
            font:font_1,
            fill:"#FFF68F"
        });
        game.add.text(50, 510, text_15, {
            font:font_2,
            fill:textFill
        });
        game.add.text(50, 540, text_16, {
            font:font_2,
            fill:textFill
        });
        show("help");
        tooltip_help2();
    },
    update: function(){
        if(star){
            if(backBtn.input.pointerOver()){
                tooltip8.alpha=0.7;
              }
              else{
                tooltip8.alpha=0;
              }
        }
    }
  }

  function go_backloginhelp2(){
      game.state.start('userinterface');
  }

  function tooltip_help2(){
      star = true;
  }