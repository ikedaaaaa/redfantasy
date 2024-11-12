import java.util.Random;
import java.util.stream.IntStream;
/**
 * RedFantasy
 */
public class RedFantasy {
    Monsters ms = new Monsters();

    Random rnd = new Random();

    Status player = new Status();
    Status cpu = new Status();
    public RedFantasy() {
    }

    public void startPhase() {
        player.resetStatus();
        cpu.resetStatus();
        
        //Draw player's monster card
        // playerMonsters.length -3 ~ playerMonsters.length までのランダムなint型の数値をp1に代入する
        int playerDrawSize = this.rnd.nextInt(this.player.monsters.length - 2) + 3;

        ////Draw cpu's monster card
        int cpuDrawSize = this.rnd.nextInt(this.cpu.monsters.length -2 ) + 3;

        this.drawMonsters("player",playerDrawSize,this.player);
        this.drawMonsters("cpu",cpuDrawSize,this.cpu);


        System.out.println("--------------------");
        System.out.print("Player Monsters List:");
        IntStream.range(0,this.player.monsters.length)
            .filter(index -> this.player.monsters[index] != -1)
            .forEach(index ->  System.out.print(this.ms.monsters.get(this.player.monsters[index]).monsterName + " "));

        System.out.print("\nCPU Monsters List:");
        IntStream.range(0,this.cpu.monsters.length)
            .filter(index -> this.cpu.monsters[index] != -1)
            .forEach(index ->  System.out.print(this.ms.monsters.get(this.cpu.monsters[index]).monsterName + " "));


        System.out.println("\n--------------------");
        System.out.println("Battle!");
        int playerDice = this.rnd.nextInt(6)+1; //1~6のサイコロを振る
        System.out.println("Player's Dice'：" + playerDice);
        if(playerDice == 1){
            System.out.println("失敗！すべてのモンスターポイントが半分になる");
            IntStream.range(0,this.player.monsters.length)
                .filter(index -> this.player.monsters[index] != -1)
                .forEach(index -> this.player.monstersPoint[index] = this.player.monstersPoint[index] / 2);
        }else if(playerDice == 6){
            System.out.println("Critical！すべてのモンスターポイントが倍になる");
            IntStream.range(0,this.player.monsters.length)
                .filter(index -> this.player.monsters[index] != -1)
                .forEach(index -> this.player.monstersPoint[index] = this.player.monstersPoint[index] * 2);
        }else{
            this.player.bonusPoint = playerDice;
        }
        int cpuDice = this.rnd.nextInt(6)+1; //1~6のサイコロを振る
        System.out.println("CPU's Dice'：" + cpuDice);
        if(cpuDice == 1){
            System.out.println("失敗！すべてのモンスターポイントが半分になる");
            IntStream.range(0,this.cpu.monsters.length)
                .filter(index -> this.cpu.monsters[index] != -1)
                .forEach(index -> this.cpu.monstersPoint[index] = this.cpu.monstersPoint[index] / 2);
        }else if(cpuDice == 6){
            System.out.println("Critical！すべてのモンスターポイントが倍になる");
            IntStream.range(0,this.cpu.monsters.length)
                .filter(index -> this.cpu.monsters[index] != -1)
                .forEach(index -> this.cpu.monstersPoint[index] = this.cpu.monstersPoint[index] * 2);
        }else{
            this.cpu.bonusPoint = cpuDice;
        }

        System.out.println("--------------------");
        System.out.print("Player Monster Pointの合計:");
        int playerMonterTotalPoint = this.player.bonusPoint + 
            IntStream.range(0, this.player.monsters.length)
                 .filter(index -> this.player.monsters[index] != -1)
                 .map(index -> this.player.monstersPoint[index])
                 .sum();
        System.out.println(playerMonterTotalPoint);

        System.out.print("CPU Monster Pointの合計:");
        int cpuMonterTotalPoint = this.cpu.bonusPoint + 
        IntStream.range(0, this.cpu.monsters.length)
             .filter(index -> this.cpu.monsters[index] != -1)
             .map(index -> this.cpu.monstersPoint[index])
             .sum();
        System.out.println(cpuMonterTotalPoint);
        System.out.println("--------------------");

        if(playerMonterTotalPoint > cpuMonterTotalPoint){
            System.out.println("Player Win!");
            this.cpu.hp = this.cpu.hp - (playerMonterTotalPoint - cpuMonterTotalPoint);
        }else if(cpuMonterTotalPoint > playerMonterTotalPoint){
            System.out.println("CPU Win!");
            this.player.hp = this.player.hp - (cpuMonterTotalPoint - playerMonterTotalPoint);
        }else if(playerMonterTotalPoint == cpuMonterTotalPoint){
            System.out.println("Draw!");
        }

        System.out.println("Player HP is " + this.player.hp);
        System.out.println("CPU HP is " + this.cpu.hp);
        
        System.out.println("--------------------");
        // 対戦結果の記録
        IntStream.range(0, this.player.history.length)
            .filter(index -> this.player.history[index] == -9999)
            .findFirst()
            .ifPresent(index -> this.player.history[index] = this.player.hp);
        IntStream.range(0, this.cpu.history.length)
            .filter(index -> this.cpu.history[index] == -9999)
            .findFirst()
            .ifPresent(index -> this.cpu.history[index] = this.cpu.hp);

    }
    public void drawMonsters(String name,int drawSize,Status user){
        System.out.println(name + " Draw " + drawSize + " monsters");
        IntStream.range(0,drawSize)
            .forEach(index -> {
                int monsterNumber = this.rnd.nextInt(this.ms.monsters.size());
                user.monsters[index] = monsterNumber;
                user.monstersPoint[index] = this.ms.monsters.get(monsterNumber).monsterPoint;
            });
    }
}