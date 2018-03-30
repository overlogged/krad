public class Rectangle { //定义整个地图某一具体阶梯属性。
    int ladderheight;//每个阶梯的高度（含平台）
    char symbol;//与平台的接口的标志，若不为与平台接口，置为‘0’
    boolean isBrother;//阶梯是否有高度一样的旁支
    int laddernext;//阶梯的下一个指向
    int ladderfoward;//阶梯的上一个指向
    LadderHeight Brother;//阶梯的旁支
    boolean isPlatform;//是否为平台
    PlatformDetail platform;//记录平台分叉
    boolean isFactor;//是否含有要素
    FactorDetail key;//有要素的话，是什么东西
    boolean isSkillarea;//是否配合技能放了
    // 什么
}
class FactorDetail {
    char []name;//要素名字
    void fuction(){}//要素功能
}
class PlatformDetail{
    int forknumber;//有多少分叉
    char []fork;//每个分叉指向哪里
    char platformsymbol;//平台标志
}
class SkillArea{
    char []name;//技能名字
    void fuction(){}//技能功能
}
class SetLadder{//创建整个地图的阶梯
    int laddernumber;//总阶梯数
    void SetLadder(int n){
        laddernumber=n;//给对象赋予laddernumber值
    }
    LadderHeight[] deatils=new LadderHeight[laddernumber];//创建整个地图的对象
     void VestHeight(){}//设置高度
     void VestPlatform(){}//设置平台
     void VestFactor(){}//设置要素
}
class  VestHeight{
     VestHeight(LadderHeight[] x){}
}
class VestPlatform{
     VestPlatform(LadderHeight[] x){}
}
class VestFactor {
     VestFactor(LadderHeight[] x) {}
}
