package study.week13;
// [ 16,464KB | 96ms ]
import java.io.*;
import java.util.*;

public class BOJ_2294_동전2 {
	
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	// 세팅
	static int N;
	static int K;
	static int[] coins;
	static int[] minis;
	static int[][] dp;
	static int INF = Integer.MAX_VALUE;
	// 메서드
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		coins = new int[N];
		for (int i = 0; i < N; i++) {
			coins[i] = Integer.parseInt(input.readLine()); // 굳이 중복 없앨 필요 없음. 정렬할 필요도 없음.
		}
		// 세팅
		dp = new int[K+1][N]; 	// K값을 그대로 쓰기 위해서 0번 인덱스도 추가. 
								// 이렇게 하면 장점. 0번에 0의 값을 넣어두면, 
								// (Kj-Aj==0) 일때 DP[Ki-Aj][X]의 값이 항상 0이고 
								// 이때 +1을 하는 전체 로직으로 초기화를 함께 수행할수 있다.
								// 이 알고리즘은 도달불가능 표시와, 단 한번의 초기화(값을 채워나갈 기원점), 그리고 전체 로직으로 구성돼있다
		
		minis = new int[K+1]; 	// 각 가격별 최소 동전수를 저장하는 용도의 배열. 매번 dp의 행을 읽어도 되지만, 그냥 최적화 해봤음. 
								// dp테이블에 열 하나 추가해서 거기다가 넣어도 되지만, 뭔가 직관성 떨어져서 배열로 분리해서 만듬.
		// 작업
		for (int k = 0+1; k < K+1; k++) { 	// k=0일때인 dp[0][x]는 자동으로 0으로 초기화 된 상태.
			// int mini = INF;				// 행마다 추가로 저장되는 값과 mini값 비교해서 갱신하다가(그래서 당연하게도 for문 밖에서 선언.), 마지막에 최소값인 상태일때 minis에 저장하는 넣는용도.
											// 이렇게 하려다가. 복잡한것 같아서 직관성 위주로 코드를 전환. 행 완성했을때, 해당 행 한바퀴 돌면서 최소값 갱신해서 minis에 넣는 방식으로 전환.
			// 행 채우기.
			for (int i = 0; i < N; i++) {
				int Ai = coins[i];
				if(k-Ai<0) {
					dp[k][i] = INF; 		// 이전 상태가 -값이라면 현실에서 불가능한 상태 => 도달 불가능지점.
				} else{
					int preValue = minis[k-Ai];
					if(preValue==INF) {
						dp[k][i] = preValue;
					} else {
						dp[k][i] = minis[k-Ai]+1;
					}
				}
			}
			// 행의 최소값 구해서 저장하기.
			int mini = INF;
			for (int i = 0; i < N; i++) {
				int compare = dp[k][i];
				mini = Math.min(mini, compare);
			}
			minis[k] = mini;
		}
		// 출력
		if(minis[K]==INF) {
			System.out.println(-1);
		} else {
			System.out.println(minis[K]);
		}
		
	}// 메인 종료
}
