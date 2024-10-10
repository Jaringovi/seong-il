import java.io.*;
import java.util.*;

public class BOJ_1149_RGB거리2 {
	// 입력루틴
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	// 세팅
	// 메소드
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 입력
		int N = Integer.parseInt(input.readLine());
		int[][] dp = new int[3][N];
		tokens = new StringTokenizer(input.readLine());
		dp[0][0] = Integer.parseInt(tokens.nextToken());
		dp[1][0] = Integer.parseInt(tokens.nextToken());
		dp[2][0] = Integer.parseInt(tokens.nextToken());
		
		for (int i = 0+1; i < N; i++) { // 위에서 한번 초항을 설정했으니까 i=0+1
			tokens = new StringTokenizer(input.readLine());
			int Air = Integer.parseInt(tokens.nextToken());
			int Aig = Integer.parseInt(tokens.nextToken());
			int Aib = Integer.parseInt(tokens.nextToken());
			dp[0][i] = Math.min(dp[1][i-1],dp[2][i-1]) + Air;
			dp[1][i] = Math.min(dp[0][i-1],dp[2][i-1]) + Aig;
			dp[2][i] = Math.min(dp[1][i-1],dp[0][i-1]) + Aib;
		}
		// 세팅
		// 작업
		int answer = Math.min(dp[0][N-1], dp[1][N-1]);
		answer = Math.min(answer, dp[2][N-1]);
		// 출력
//		System.out.println("R: "+Arrays.toString(dp[0]));
//		System.out.println("G: "+Arrays.toString(dp[1]));
//		System.out.println("B: "+Arrays.toString(dp[2]));
		System.out.println(answer);
	}
}
