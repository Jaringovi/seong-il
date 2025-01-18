package study.week23;
import java.io.*;
import java.util.*;
public class BOJ_14728_벼락치기 {
	// 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int[][] origin;
	static int[][] dp;
	static int N;
	static int T;
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		T = Integer.parseInt(tokens.nextToken());
		origin = new int[N+1][2]; 	// 열의 사이즈와 맞춰야 값을 참조하기 단순해짐
		dp = new int[T+1][N+1]; 	// 초기화값이 필요해서 행과 열의 사이즈를 +1함
		for (int i = 0+1; i < N+1; i++) { // 사이즈를 맞추기 위해 추가한 0번째 아이템은 건너뜀
			tokens = new StringTokenizer(input.readLine());
			origin[i][0] = Integer.parseInt(tokens.nextToken()); // 비용
			origin[i][1] = Integer.parseInt(tokens.nextToken()); // 이익
		}
		// 세팅
		// 작업
		for (int t = 0+1; t < T+1; t++) { // 0번 행 건너뜀 => 어차피 0으로 초기화돼있음
			for (int n = 0+1; n < N+1; n++) { // 0번 열 건너뜀 => 어차피 0으로 초기화돼있음. 디버깅 포인트 : "n < N" => "n < N+1"
				int nowItemCost = origin[n][0];
				int nowItemProfit = origin[n][1];

				//dp[t][n]
				if(t<nowItemCost) {
					// 집어 넣는게 아예 불가능함. 안 넣음. 이전 아이템 차례일때 결과 가져오기
					dp[t][n] = dp[t][n-1];
				} else {
					// 집어 넣을수 있음, 근데 집어 넣은거랑 안 넣은거랑 비교해서 더 큰걸 남겨야함.
					dp[t][n] = Math.max(nowItemProfit + dp[t-nowItemCost][n-1], dp[t][n-1]); 
				}
			}
		}
		// 출력
		System.out.println(dp[T][N]);
//		for (int i = 0; i < T+1; i++) {
//			System.out.println(Arrays.toString(dp[i])); 
//		}
	}
	// 메소드
}
