import java.util.Random;
import java.util.stream.IntStream;
/**
 * RedFantasy
 */
public class RedFantasy {
    private static final int MIN_MONSTER_DRAW = 3;  // モンスターカードの最小枚数
    private static final int MONSTER_DECK_OFFSET = 2;  // モンスターカードのデッキサイズ調整値

    Monsters ms = new Monsters();

    Random rnd = new Random();

    Status player = new Status("player");
    Status cpu = new Status("cpu");
    public RedFantasy() {
    }

    public void startPhase() {
        this.resetStatuses();
        this.drawMonstersForBothPlayers();
        this.displayMonsters();

        int playerDice = rollDice();
        int cpuDice = rollDice();

        System.out.println("\n--------------------");
        this.battleSummary(playerDice,cpuDice);
 
        this.recordBattleHistory();
    }

    private void resetStatuses(){
        /*
         * プレイヤーとCPUのステータスをリセットする．
         * ゲーム開始時または各ターン開始時に
         * ステータスが初期状態に戻される．
         */
        player.resetStatus();
        cpu.resetStatus();
    }

    private void drawMonstersForBothPlayers(){
        int playerDrawSize = getRandomMonsterDrawSize(player);
        int cpuDrawSize = getRandomMonsterDrawSize(cpu);

        this.drawMonsters(playerDrawSize, this.player);
        this.drawMonsters(cpuDrawSize, this.cpu);
    }
    private int getRandomMonsterDrawSize(Status user) {
        return this.rnd.nextInt(user.monsters.size() - MONSTER_DECK_OFFSET) + MIN_MONSTER_DRAW;
    }

    private void drawMonsters(int drawSize, Status user){
        System.out.println(user.name + " Draw " + drawSize + " monsters");
        IntStream.range(0,drawSize)
            .forEach(index -> {
                int monsterNumber = this.rnd.nextInt(this.ms.monsters.size());
                user.monsters.set(index,monsterNumber);
                // user.monstersPoint[index] = this.ms.monsters.get(monsterNumber).monsterPoint;
                user.monstersPoint.set(index,this.ms.monsters.get(monsterNumber).monsterPoint);
            });
    }
    
    private void displayMonsters() {
        System.out.println("--------------------");
        setMonsters(this.player);
        System.out.println("");
        setMonsters(this.cpu);
    }


    private void setMonsters(Status user){
        System.out.print(user.name + " Monsters List:");
        // IntStream.range(0,user.monsters.size())
        //     .filter(index -> user.monsters[index] != -1)
        //     .forEach(index ->  System.out.print(this.ms.monsters.get(user.monsters[index]).monsterName + " "));
        IntStream.range(0,user.monsters.size())
            .filter(index -> user.monsters.get(index) != -1)
            .forEach(index ->  System.out.print(this.ms.monsters.get(user.monsters.get(index)).monsterName + " "));
    }

    private int rollDice() {
        return rnd.nextInt(6) + 1;
    }

    private  void battleSummary(int playerDice,int cpuDice){
        System.out.println("Battle!");
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
    }

    private void diceProcessing(int dice, Status user){
        System.out.println(user.name + "'s Dice'：" + dice);
        switch (dice) {
            case 1 -> {
                System.out.println("失敗！すべてのモンスターポイントが半分になる");
            //     IntStream.range(0,user.monsters.size())
            //         .filter(index -> user.monsters[index] != -1)
            //         .forEach(index -> user.monstersPoint[index] = user.monstersPoint[index] / 2);
            // }
                IntStream.range(0, user.monsters.size())
                    .filter(index -> user.monsters.get(index) != -1)
                    .forEach(index -> user.monstersPoint.set(index, user.monstersPoint.get(index) / 2));
            }
            case 6 -> {
                System.out.println("Critical！すべてのモンスターポイントが倍になる");
                // IntStream.range(0,user.monsters.size())
                //     .filter(index -> user.monsters[index] != -1)
                //     .forEach(index -> user.monstersPoint[index] = user.monstersPoint[index] * 2);
                IntStream.range(0, user.monsters.size())
                    .filter(index -> user.monsters.get(index) != -1)
                    .forEach(index -> user.monstersPoint.set(index, user.monstersPoint.get(index) * 2));
            }
            default -> user.bonusPoint = dice;
        }
    }

    private int monsterTotalPointCalculation(Status user){
        int monterTotalPoint = user.bonusPoint + 
                // IntStream.range(0, user.monsters.size())
                //     .filter(index -> user.monsters[index] != -1)
                //     .map(index -> user.monstersPoint[index])
                //     .sum();
                IntStream.range(0, user.monsters.size())
                        .filter(index -> user.monsters.get(index) != -1)
                        .map(index -> user.monstersPoint.get(index))
                        .sum();

        System.out.print(user.name + " Monster Pointの合計:");
        System.out.println(monterTotalPoint);
        return monterTotalPoint;
    }

    private String judgment(int playerMonterTotalPoint,int cpuMonterTotalPoint){
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

    private void recordBattleHistory() {
        this.writeHistory(this.player);
        this.writeHistory(this.cpu);
    }
    private void writeHistory(Status user){
        // IntStream.range(0, user.history.size())
        //     .filter(index -> user.history[index] == -9999)
        //     .findFirst()
        //     .ifPresent(index -> user.history[index] = user.hp);
        IntStream.range(0, user.history.size())
            .filter(index -> user.history.get(index) == -9999)
            .findFirst()
            .ifPresent(index -> user.history.set(index, user.hp));
    }
}