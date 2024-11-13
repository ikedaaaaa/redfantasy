// import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Status
 */
public class Status {
    public List<Integer> monsters = new ArrayList<>(Collections.nCopies(5, -1));
    public List<Integer> monstersPoint = new ArrayList<>(Collections.nCopies(5, 0));
    public int hp;
    public int bonusPoint;
    public List<Integer> history = new ArrayList<>(Collections.nCopies(100, -9999));
    public String name;

    public Status(String name) {
        this.name = name;
        this.hp = 50;
        this.bonusPoint = 0;
        this.history.set(0, this.hp);
    }

    public void resetStatus() {
        Collections.fill(this.monsters, -1);
        this.bonusPoint = 0;
    }
}
