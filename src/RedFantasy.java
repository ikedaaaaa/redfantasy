import java.util.Random;
import java.util.stream.IntStream;
/**
 * RedFantasy
 */
public class RedFantasy {
    Monsters ms = new Monsters();

    Random rnd = new Random();

    Status player = new Status("player");
    Status cpu = new Status("cpu");
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

        this.drawMonsters(playerDrawSize,this.player);
        this.drawMonsters(cpuDrawSize,this.cpu);


        System.out.println("--------------------");
        this.setMonsters(this.player);
        System.out.println("");
        this.setMonsters(this.cpu);
        System.out.println("\n--------------------");
        System.out.println("Battle!");
        int playerDice = this.rnd.nextInt(6)+1; //1~6のサイコロを振る
        int cpuDice = this.rnd.nextInt(6)+1; //1~6のサイコロを振る
        this.diceProcessing(playerDice,this.player);
        this.diceProcessing(cpuDice,this.cpu);
        System.out.println("--------------------");
        int playerMonterTotalPoint = monsterTotalPointCalculation(this.player);
        int cpuMonterTotalPoint = monsterTotalPointCalculation(this.cpu);
        System.out.println("--------------------");

        String judgmentWord = judgment(playerMonterTotalPoint,cpuMonterTotalPoint);
        System.out.println(judgmentWord);

        System.out.println("Player HP is " + this.player.hp);
        System.out.println("CPU HP is " + this.cpu.hp);
        
        System.out.println("--------------------");
        // 対戦結果の記録
        this.writeHistory(this.player);
        this.writeHistory(this.cpu);
    }
    public void drawMonsters(int drawSize, Status user){
        System.out.println(user.name + " Draw " + drawSize + " monsters");
        IntStream.range(0,drawSize)
            .forEach(index -> {
                int monsterNumber = this.rnd.nextInt(this.ms.monsters.size());
                user.monsters[index] = monsterNumber;
                user.monstersPoint[index] = this.ms.monsters.get(monsterNumber).monsterPoint;
            });
    }

    public void setMonsters(Status user){
        System.out.print(user.name + " Monsters List:");
        IntStream.range(0,user.monsters.length)
            .filter(index -> user.monsters[index] != -1)
            .forEach(index ->  System.out.print(this.ms.monsters.get(user.monsters[index]).monsterName + " "));
    }

    public void diceProcessing(int dice, Status user){
        System.out.println(user.name + "'s Dice'：" + dice);
        switch (dice) {
            case 1 -> {
                System.out.println("失敗！すべてのモンスターポイントが半分になる");
                IntStream.range(0,user.monsters.length)
                    .filter(index -> user.monsters[index] != -1)
                    .forEach(index -> user.monstersPoint[index] = user.monstersPoint[index] / 2);
            }
            case 6 -> {
                System.out.println("Critical！すべてのモンスターポイントが倍になる");
                IntStream.range(0,user.monsters.length)
                    .filter(index -> user.monsters[index] != -1)
                    .forEach(index -> user.monstersPoint[index] = user.monstersPoint[index] * 2);
            }
            default -> user.bonusPoint = dice;
        }
    }

    public int monsterTotalPointCalculation(Status user){
        int monterTotalPoint = user.bonusPoint + 
                IntStream.range(0, user.monsters.length)
                    .filter(index -> user.monsters[index] != -1)
                    .map(index -> user.monstersPoint[index])
                    .sum();
        System.out.print(user.name + " Monster Pointの合計:");
        System.out.println(monterTotalPoint);
        return monterTotalPoint;
    }

    public String judgment(int playerMonterTotalPoint,int cpuMonterTotalPoint){
        int pointDiff = playerMonterTotalPoint - cpuMonterTotalPoint;
        if(pointDiff > 0){
            this.cpu.hp = this.cpu.hp - (pointDiff);
            return "Player Win!";
        }else if(pointDiff < 0){
            this.player.hp = this.player.hp - (pointDiff * -1);
            return "CPU Win!";
        }else{
            return "Draw!";
        }
    }
    public void writeHistory(Status user){
        IntStream.range(0, user.history.length)
            .filter(index -> user.history[index] == -9999)
            .findFirst()
            .ifPresent(index -> user.history[index] = user.hp);
    }
}