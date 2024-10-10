import java.io.*;
import java.util.*;
public class Main {i
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	// 세팅
	// 메소드
	
	// 메인
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(input.readLine());
		int[][] dp = new int[N][10];
		for (int i = 0+1; i < 10; i++) {
			dp[0][i] = 1;
		}
		for (int row = 0+1; row < N; row++) {
			// 0=>1 8=>9
			for (int col = 0+0; col < 10-1; col++) {
				dp[row][col+1] = (dp[row][col+1]+dp[row-1][col])%1_000_000_000;
			}
			// 9=>8 1=>0
			for (int col = 0+1; col < 10+0; col++) {
				dp[row][col-1] = (dp[row][col-1]+dp[row-1][col])%1_000_000_000;
			}
		}
		long sum=0;
		for (int i = 0; i < 10; i++) {
			sum = (sum +dp[N-1][i])%1_000_000_000;
		}
		long answer = sum%1_000_000_000;
		System.out.println(answer);
	} // 메인 종료
