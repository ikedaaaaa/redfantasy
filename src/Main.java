import java.util.Arrays;
public class Main {
    static RedFantasy rf = new RedFantasy();
	public static void main(String[] args) {
		while (true) {
			try {
				if (rf.player.hp > 0 && rf.cpu.hp > 0) {
					Thread.sleep(30);
					rf.startPhase();
				} else if (rf.player.hp <= 0) {
					System.out.println("Playerは死んでしまった");
					break;
				} else if (rf.cpu.hp <= 0) {
					System.out.println("CPUは死んでしまった");
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Player History:");
		Arrays.stream(rf.player.history)
		.filter(value -> value != -9999)
		.forEach(value -> System.out.print(value + "\t"));
	

		System.out.println("\nCPU History:");
		Arrays.stream(rf.cpu.history)
		.filter(value -> value != -9999)
		.forEach(value -> System.out.print(value + "\t"));
		
		System.out.println("");
	}
}