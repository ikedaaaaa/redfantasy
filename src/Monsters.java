/**
 * Monsters
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Monsters {

    public List<Monster> monsters;

    public Monsters() {
        monsters = new ArrayList<>();
        loadMonstersFromCSV("./data/monsters.csv");
    }

    // CSVファイルからモンスターのデータを読み込むメソッド
    private void loadMonstersFromCSV(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            // 1行目（ヘッダー）を読み飛ばす
            br.readLine();
            
            // 各行を読み込み、Monsterオブジェクトを作成してリストに追加
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String monsterName = values[0];
                int monsterPoint = Integer.parseInt(values[1]);
                monsters.add(new Monster(monsterName, monsterPoint));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
