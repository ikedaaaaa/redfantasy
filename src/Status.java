import java.util.Arrays;
/**
 * Status
 */

 public class Status {
    public int[] monsters = new int[5];
    public int[] monstersPoint = new int[5];
    public int hp;
    public int bonusPoint;
    public int[] history = new int[100];
    public Status(){
        this.hp = 50;
        this.bonusPoint = 0;
        Arrays.setAll(this.monsters, value -> -1);
        this.history[0] = this.hp;
        Arrays.setAll(this.history, value -> -9999);
    }
 }