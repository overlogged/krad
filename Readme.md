# krad-backend

master ![travis](https://www.travis-ci.org/NiceKingWei/krad.svg?branch=master)

dev ![travis](https://www.travis-ci.org/NiceKingWei/krad.svg?branch=dev)

## git notes
    git branch 查看分支
    git checkout xxxx 切换到分支
    git checkout -b your_name 创建个人本地分支

    日常工作流程
    git checkout dev
    git pull origin dev
    git checkout your_name
    git merge dev
    # working
    git add .
    git commit -m "add/fix/modify/remove"
    git checkout dev
    git pull origin dev
    
    # 以上操作均不会出错
    git merge your_name
    有可能有冲突
    解决冲突，并commit

    # 以下操作不会出错
    git push
    
## idea notes
    import project，选择pom.xml


## deploy notes
* install maven mongo
* config.txt

## 前后端交互

| 阶段                                      | msg内容(全部有sid)                                           | 返回内容                                                     |
| ----------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| INIT.0   游戏刚开始                       | 空                                                           | 所有玩家的信息（user_info数组）,字符串“choose hero" +当前玩家的序号+英雄列表（数组） |
| INIT.1   选择英雄阶段                     | 选择的英雄(字符串)                                           | 字符串”start game" + 所有人选择的英雄列表（数组）+分队结果（数组） |
| MAINGAME.0.0  准备阶段.发牌阶段           | 空                                                           | 字符串："choose strategy decision"+玩家的手牌(数组)+可选开火目标（玩家序号数组）+可选移动方向（整型数组） |
| MAINGAME.0.1  准备阶段.决策阶段           | 玩家选择的决策（牌的数组下标）+玩家选择的开火目标/移动方向（两个都传，但是有用的只有一个，另一个赋-1即可) | 字符串："choose seen card"+玩家出牌之后的手牌（数组）        |
| MAINGAME.0.3  准备阶段.明牌阶段           | 玩家选择的明牌（若不选择明牌，则为0,如果玩家手牌中没有出拳牌，则一定要选明牌） | 字符串“choose gamble"+所有人的决策选择（数组），所有人明牌的选择（数组） |
| MAINGAME.1.0  出拳阶段.选择出拳牌阶段     | 玩家选择的出拳牌（数组）                                     | 字符串"win judge"+玩家出拳之后的手牌（数组）                 |
| MAINGAME.1.1  出拳阶段.判断输赢和倍率阶段 | 空                                                           | 字符串"ACTION: deposit account"+所有人出拳的选择（数组），每个人出了几张牌（数组），哪些玩家赢了（数组，赢1，输0） |
| ACTION.0.0   蓄能结算                     | 空                                                           | 字符串”skills account"+所有人应该剩余的能量（数组）          |
| ACTION.0.1 技能结算                       | 空                                                           | 字符串”fire account"(我们还没有技能)                         |
| ACTION.0.2 开火结算                       | 空                                                           | 字符串"move account"+所有人应该剩余的生命值（数组）          |
| ACTION.0.3  移动结算                      | 空                                                           | 字符串“elem account"+所有人应该处于的位置（地图数组下标数组） |
| ACTION.0.4  要素获得结算                  | 空                                                           | 字符串”if human wins"+ 所有人的要素情况（数组，有1，无0）    |
| ACTION.0.5  人类胜利结算                  | 空                                                           | 若人类胜利：字符串“Game Over, human wins" ,若人类尚未胜利：字符串"infection account" |
| ACTION.0.6  感染及感染者胜利结算          | 空                                                           | 若感染者胜利：字符串”Game Over, zombie wins"+所有人的阵营（数组:0人1感染者），若感染者尚未胜利：字符串“desert account"+所有人的阵营（数组） |
| ACTION.0.7  弃牌结算                      | 弃牌选择数组                                                 | 字符串”PREPARE: choose decision"+ 玩家能量情况（整型）+玩家剩余手牌（数组） |

