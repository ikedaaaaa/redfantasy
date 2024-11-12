import java.util.Arrays;
public class Main {
    static RedFantasy rf = new RedFantasy();
	public static void main(String[] args) {
		while (true) {
			try {
				if (rf.getPlayerHp() > 0 && rf.getCpuHp() > 0) {
					Thread.sleep(30);
					rf.startPhase();
				} else if (rf.getPlayerHp() <= 0) {
					System.out.println("Playerは死んでしまった");
					break;
				} else if (rf.getCpuHp() <= 0) {
					System.out.println("CPUは死んでしまった");
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Player History:");
		int[] playerHistory = rf.getPlayerHistory();
		Arrays.stream(playerHistory)
		.filter(value -> value != -9999)
		.forEach(value -> System.out.print(value + "\t"));
	

		System.out.println("\nCPU History:");
		int[] cpuHistory = rf.getCpuHistory();
		Arrays.stream(cpuHistory)
		.filter(value -> value != -9999)
		.forEach(value -> System.out.print(value + "\t"));
		
		System.out.println("");
	}
}