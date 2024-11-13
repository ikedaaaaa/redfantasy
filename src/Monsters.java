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
            br.readLine();
            while ((line = br.readLine()) != null) {
                List<String> values = new ArrayList<>(List.of(line.split(",")));
                String monsterName = values.get(0);
                int monsterPoint = Integer.parseInt(values.get(1));
                monsters.add(new Monster(monsterName, monsterPoint));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
