package study.week20;
import java.io.*;
import java.util.*;

public class BOJ_11060_점프점프 {
	// 입력 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅

	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		int N = Integer.parseInt(input.readLine());
		int[] origin = new int[N];
		int[] dp = new int[N];
		//// 도달 불가능 및 최소값 비교를 위해 큰수로 할당.
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 0;
		tokens = new StringTokenizer(input.readLine());
		for (int i = 0; i < N; i++) {
			origin[i] = Integer.parseInt(tokens.nextToken());
		}
		// 세팅
		// 작업
		for (int cell = 0; cell < N; cell++) {
			// 해당 셀이 도달 불가능 지점이면, 거기서 다른 곳으로 이동하는 로직을 실행해선 안되므로 점프. 디버깅 포인트
			if(dp[cell]==Integer.MAX_VALUE) {
				continue;
			}
			// 해당 셀에서 갈수 있는 사거리를 구하고. 그만큼 반복문 돌려서 도착한 위치에 dp값과 출발지의 dp값 +1의 최소값을 비교후 갱신.
			int maxRange = origin[cell];
			for (int range = 0; range <= maxRange; range++) {
				if(cell+range>=N) { // 맨끝에서 사거리가 100이면 전체범위 초과하므로 범위를 초과할때는 종료. 디버깅 포인트
					break;
				}
				dp[cell+range] = Math.min(dp[cell+range], dp[cell]+1);
			}
		}
		// 출력
		if(dp[N-1] == Integer.MAX_VALUE) {
			System.out.println(-1);
		}else {
			System.out.println(dp[N-1]);
		}
	}
	// 메소드

}
