import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
/**
 * RedFantasy
 */
public class RedFantasy {
    int[] playerMonsters = new int[5];
    int[] playerMonstersPoint = new int[5];

    int[] cpuMonsters = new int[5];
    int[] cpuMonstersPoint = new int[5];

    int playerHp = 50;
    int cpuHp = 50;
    int playerBonusPoint = 0;
    int cpuBonusPoint = 0;

    Monsters ms = new Monsters();

    Random rnd = new Random();

    // battle history
    int[] playerHistory = new int[100];
    int[] cpuHistory = new int[100];
    
    public RedFantasy() {
        Arrays.setAll(this.playerMonsters, value -> -1);
        Arrays.setAll(this.cpuMonsters, value -> -1);



        this.playerHistory[0] = this.playerHp;
        this.cpuHistory[0] = this.cpuHp;
        Arrays.setAll(this.playerHistory, value -> -9999);
        Arrays.setAll(this.cpuHistory, value -> -9999);

    }

    public void startPhase() {

        //Draw player's monster card
        // playerMonsters.length -3 ~ playerMonsters.length までのランダムなint型の数値をp1に代入する
        int p1 = this.rnd.nextInt(this.playerMonsters.length - 2) + 3;
        System.out.println("Player Draw " + p1 + " monsters");
        IntStream.range(0,p1)
            .forEach(index -> {
                int m = this.rnd.nextInt(this.ms.monsters.size());
                this.playerMonsters[index] = m;
                this.playerMonstersPoint[index] = this.ms.monsters.get(m).monsterPoint;
            });

        ////Draw cpu's monster card
        int p2 = this.rnd.nextInt(this.cpuMonsters.length -2 ) + 3;
        System.out.println("CPU Draw " + p2 + " monsters");
        IntStream.range(0,p2)
            .forEach(index -> {
            int m = this.rnd.nextInt(this.ms.monsters.size());
            this.cpuMonsters[index] = m;
            this.cpuMonstersPoint[index] = this.ms.monsters.get(m).monsterPoint;
        });

        System.out.println("--------------------");
        System.out.print("Player Monsters List:");
        IntStream.range(0,this.playerMonsters.length)
            .filter(index -> this.playerMonsters[index] != -1)
            .forEach(index ->  System.out.print(this.ms.monsters.get(this.playerMonsters[index]).monsterName + " "));

        System.out.print("\nCPU Monsters List:");
        IntStream.range(0,this.cpuMonsters.length)
            .filter(index -> this.cpuMonsters[index] != -1)
            .forEach(index ->  System.out.print(this.ms.monsters.get(this.cpuMonsters[index]).monsterName + " "));


        System.out.println("\n--------------------");
        System.out.println("Battle!");
        int d1 = this.rnd.nextInt(6)+1; //1~6のサイコロを振る
        System.out.println("Player's Dice'：" + d1);
        if(d1 == 1){
            System.out.println("失敗！すべてのモンスターポイントが半分になる");
            IntStream.range(0,this.playerMonsters.length)
                .filter(index -> this.playerMonsters[index] != -1)
                .forEach(index -> this.playerMonstersPoint[index] = this.playerMonstersPoint[index] / 2);
        }else if(d1 == 6){
            System.out.println("Critical！すべてのモンスターポイントが倍になる");
            IntStream.range(0,this.playerMonsters.length)
                .filter(index -> this.playerMonsters[index] != -1)
                .forEach(index -> this.playerMonstersPoint[index] = this.playerMonstersPoint[index] * 2);
        }else{
            this.playerBonusPoint = d1;
        }
        int d2 = this.rnd.nextInt(6)+1; //1~6のサイコロを振る
        System.out.println("CPU's Dice'：" + d2);
        if(d2 == 1){
            System.out.println("失敗！すべてのモンスターポイントが半分になる");
            IntStream.range(0,this.cpuMonsters.length)
                .filter(index -> this.cpuMonsters[index] != -1)
                .forEach(index -> this.cpuMonstersPoint[index] = this.cpuMonstersPoint[index] / 2);
        }else if(d2 == 6){
            System.out.println("Critical！すべてのモンスターポイントが倍になる");
            IntStream.range(0,this.cpuMonsters.length)
                .filter(index -> this.cpuMonsters[index] != -1)
                .forEach(index -> this.cpuMonstersPoint[index] = this.cpuMonstersPoint[index] * 2);
        }else{
            this.cpuBonusPoint = d2;
        }

        System.out.println("--------------------");
        System.out.print("Player Monster Pointの合計:");
        int p3 = this.playerBonusPoint + 
            IntStream.range(0, this.playerMonsters.length)
                 .filter(index -> this.playerMonsters[index] != -1)
                 .map(index -> this.playerMonstersPoint[index])
                 .sum();
        System.out.println(p3);

        System.out.print("CPU Monster Pointの合計:");
        int p4 = this.cpuBonusPoint + 
        IntStream.range(0, this.cpuMonsters.length)
             .filter(index -> this.cpuMonsters[index] != -1)
             .map(index -> this.cpuMonstersPoint[index])
             .sum();
        System.out.println(p4);
        System.out.println("--------------------");

        if(p3 > p4){
            System.out.println("Player Win!");
            this.cpuHp = this.cpuHp - (p3 - p4);
        }else if(p4 > p3){
            System.out.println("CPU Win!");
            this.playerHp = this.playerHp - (p4 - p3);
        }else if(p3 == p4){
            System.out.println("Draw!");
        }

        System.out.println("Player HP is " + this.playerHp);
        System.out.println("CPU HP is " + this.cpuHp);
        
        System.out.println("--------------------");
        // 対戦結果の記録
        IntStream.range(0, this.playerHistory.length)
            .filter(index -> this.playerHistory[index] == -9999)
            .findFirst()
            .ifPresent(index -> this.playerHistory[index] = this.playerHp);
        IntStream.range(0, this.cpuHistory.length)
            .filter(index -> this.cpuHistory[index] == -9999)
            .findFirst()
            .ifPresent(index -> this.cpuHistory[index] = this.cpuHp);

    }
    public int[] getPlayerHistory(){
        return this.playerHistory;
    }
    public int[] getCpuHistory(){
        return this.cpuHistory;
    }

    public int getPlayerHp(){
        return this.playerHp; 
    }

    public int getCpuHp(){
        return this.cpuHp;
    }
}