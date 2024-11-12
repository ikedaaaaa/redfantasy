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

        //Draw player's monster card
        // playerMonsters.length -3 ~ playerMonsters.length までのランダムなint型の数値をp1に代入する
        int p1 = this.rnd.nextInt(this.player.monsters.length - 2) + 3;
        System.out.println("Player Draw " + p1 + " monsters");
        IntStream.range(0,p1)
            .forEach(index -> {
                int m = this.rnd.nextInt(this.ms.monsters.size());
                this.player.monsters[index] = m;
                this.player.monstersPoint[index] = this.ms.monsters.get(m).monsterPoint;
            });

        ////Draw cpu's monster card
        int p2 = this.rnd.nextInt(this.cpu.monsters.length -2 ) + 3;
        System.out.println("CPU Draw " + p2 + " monsters");
        IntStream.range(0,p2)
            .forEach(index -> {
            int m = this.rnd.nextInt(this.ms.monsters.size());
            this.cpu.monsters[index] = m;
            this.cpu.monstersPoint[index] = this.ms.monsters.get(m).monsterPoint;
        });

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
        int d1 = this.rnd.nextInt(6)+1; //1~6のサイコロを振る
        System.out.println("Player's Dice'：" + d1);
        if(d1 == 1){
            System.out.println("失敗！すべてのモンスターポイントが半分になる");
            IntStream.range(0,this.player.monsters.length)
                .filter(index -> this.player.monsters[index] != -1)
                .forEach(index -> this.player.monstersPoint[index] = this.player.monstersPoint[index] / 2);
        }else if(d1 == 6){
            System.out.println("Critical！すべてのモンスターポイントが倍になる");
            IntStream.range(0,this.player.monsters.length)
                .filter(index -> this.player.monsters[index] != -1)
                .forEach(index -> this.player.monstersPoint[index] = this.player.monstersPoint[index] * 2);
        }else{
            this.player.bonusPoint = d1;
        }
        int d2 = this.rnd.nextInt(6)+1; //1~6のサイコロを振る
        System.out.println("CPU's Dice'：" + d2);
        if(d2 == 1){
            System.out.println("失敗！すべてのモンスターポイントが半分になる");
            IntStream.range(0,this.cpu.monsters.length)
                .filter(index -> this.cpu.monsters[index] != -1)
                .forEach(index -> this.cpu.monstersPoint[index] = this.cpu.monstersPoint[index] / 2);
        }else if(d2 == 6){
            System.out.println("Critical！すべてのモンスターポイントが倍になる");
            IntStream.range(0,this.cpu.monsters.length)
                .filter(index -> this.cpu.monsters[index] != -1)
                .forEach(index -> this.cpu.monstersPoint[index] = this.cpu.monstersPoint[index] * 2);
        }else{
            this.cpu.bonusPoint = d2;
        }

        System.out.println("--------------------");
        System.out.print("Player Monster Pointの合計:");
        int p3 = this.player.bonusPoint + 
            IntStream.range(0, this.player.monsters.length)
                 .filter(index -> this.player.monsters[index] != -1)
                 .map(index -> this.player.monstersPoint[index])
                 .sum();
        System.out.println(p3);

        System.out.print("CPU Monster Pointの合計:");
        int p4 = this.cpu.bonusPoint + 
        IntStream.range(0, this.cpu.monsters.length)
             .filter(index -> this.cpu.monsters[index] != -1)
             .map(index -> this.cpu.monstersPoint[index])
             .sum();
        System.out.println(p4);
        System.out.println("--------------------");

        if(p3 > p4){
            System.out.println("Player Win!");
            this.cpu.hp = this.cpu.hp - (p3 - p4);
        }else if(p4 > p3){
            System.out.println("CPU Win!");
            this.player.hp = this.player.hp - (p4 - p3);
        }else if(p3 == p4){
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
    public int[] getPlayerHistory(){
        return this.player.history;
    }
    public int[] getCpuHistory(){
        return this.cpu.history;
    }

    public int getPlayerHp(){
        return this.player.hp; 
    }

    public int getCpuHp(){
        return this.cpu.hp;
    }
}