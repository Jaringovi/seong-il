package study.week14;
// [137,720kb | 248ms]
import java.io.*;
import java.util.*;
public class BOJ_1699_제곱수의합 {
	// 입력고정
	static BufferedReader input = new  BufferedReader(new InputStreamReader(System.in));
	// 세팅
	static int N;
	static ArrayList<Integer> sub = new ArrayList<>(); // N의 최대값이 10^5이므로 그보다 더 큰 제곱수를 사용할일은 없음.
	static int [][] dp;
	static int [] minis;
	static int INF = Integer.MAX_VALUE;
	// 메소드
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		N = Integer.parseInt(input.readLine());
		// 세팅
		//// N이하인 제곱수 구해서 ArrayList에 넣기
		for (int n = 0+1; n < N+1; n++) {
			int nSquare = (int) Math.pow(n, 2);
			if(nSquare <= N) {
				sub.add(nSquare);
			} else {
				break;
			}
		}
		//// dp 테이블 초기화
		dp = new int[N+1][sub.size()];
		
		minis = new int[N+1];
		// 작업
		//// dp 테이블 채우기. dp[n][k] = 제곱수 k를 더했을때 n이 된다. 이때 더한 제곱수의 최소 개수.  = minis[n-k]+1 = n-k를 만드는데 더한 제곱수의 최소개수 +1. (n-k가 음수면 못 만드니까 INF.)
		for (int row = 0+1; row < N+1; row++) { // row는 0+1부터 시작해야 됨. 0번 행은 전부 0으로 초기화해야됨. 좀 더 의미있게 하려면 여기도 INF로 채우고. sub.get(col)의 값이 row와 일치할때 1을 초기화하는게 의미적으로 맞음.
			for (int col = 0; col < sub.size(); col++) {
				int prev = row - sub.get(col);
				if(prev<0) {
					dp[row][col] = INF;
				} else {
					dp[row][col] = minis[prev]+1;
				}
			} // dp테이블 행 채우기 끝. minis의 동일행 채우기 시작해야함.
			//// minis의 동일행 채우기 시작
			int mini = INF;
			for (int col = 0; col < sub.size(); col++) {
				mini = Math.min(mini, dp[row][col]);
			}
			minis[row] = mini;
		}
		// 출력
		System.out.println(minis[N]);
		
		
	}// 메인 종료

}
