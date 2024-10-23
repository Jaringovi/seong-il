package study.week14;

import java.io.*;
import java.util.*;
public class BOJ_1699_제곱수의합_Advanced {
	// 입력고정
	static BufferedReader input = new  BufferedReader(new InputStreamReader(System.in));
	// 세팅
	static int N;
	static ArrayList<Integer> sub = new ArrayList<>(); // N의 최대값이 10^5이므로 그보다 더 큰 제곱수를 사용할일은 없음.
	static int [] dp;
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
		dp = new int[N+1];
		
		// 작업
		for (int row = 0+1; row < N+1; row++) { // 0일때는 0으로 초기화해둔다. 자기자신을 더하면서 항의 개수가 1로 초기화되도록 설계.
			int mini = INF;	// 초기에 비교할 대상용.
			for (int col = 0; col < sub.size(); col++) {	// 모든 제곱수를 순회하면서 지금 목표 값에서 해당 제곱수를 뺐을때의 상태를 확인. 그때의 값들 중에서 최소값을 남겨서 저장한다.
				int referIndex = row-sub.get(col);
				if(referIndex<0) { // 참고할 인덱스가 없으면 패스
					continue;
				}
				mini = Math.min(mini, dp[referIndex]+1);	// 최소값 비교후 갱신
			}
			dp[row] = mini;									// 이후 재사용 위해서. 최소값 저장.
		}
		

		// 출력
		System.out.println(dp[N]);
		
		
	}// 메인 종료

}
