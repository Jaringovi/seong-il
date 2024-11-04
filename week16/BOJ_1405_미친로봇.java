import java.io.*;
import java.util.*;
// 횟수만큼 왔다갔다한다. // 따라서 움직일때마다 depth가 증가하고. // 봤을때 depth가 움직일수 있는 횟수면 바닥조건에 도착한것이다.
public class Main {


	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int depth;
	static double N;
	static double E;
	static double W;
	static double S;
	static double[] pDeltas;
	static int[][] deltas = {{-1,0}, {0,+1}, {0,-1}, {+1,0}}; // pDeltas의 순서와 맞춘다.
	static boolean[][] visit ;//= new boolean[29][29]; // 모든 경우를 커버하는 최대 크기.
	static double simplePathProbability;
//	static int count;
	// 메소드
	static void DFS(int nowDepth, double p, int[] nowLocation) {
//		debug();
		// 바닥 조건
		if(nowDepth >= depth) {
			// 추가 작업. 확률 더해주기.
//			count++;
//			System.out.println(simplePathProbability +" : " +p+" : "+count);
//			System.out.println("==============");
			simplePathProbability += p;
			return;
		}
		// 재귀 파트
		int x = nowLocation[0];
		int y = nowLocation[1];
		for (int i = 0; i < deltas.length; i++) {
			int dx = deltas[i][0];
			int dy = deltas[i][1];
			int nx = x+dx;
			int ny = y+dy;
			// 보드 밖으로 나갈일은 없음. 그렇게 크기를 설계해둬서.
			// 다음으로 갈 곳이 방문했던 곳인지만 확인하기. 방문한곳이면 점프.
			if(visit[nx][ny]) {
				continue;
			}
			// 방문처리 해주기.
			visit[nx][ny] = true;
			DFS(nowDepth+1, p*pDeltas[i], new int[] {nx,ny});
			visit[nx][ny] = false; 	// 아직도 이게 구체적으로 어떤식으로 안전하고 완벽하게 복원되는지 직관적으로 와닿지는 않아.
									// 작은 샘플로 시뮬레이션 구현해보니까 된다는것을 피부로 이해.
									// 호출한데서 바로 지워주니까, 저 함수가 끝나고 돌아오면서 바로 지워짐. 그런데 그게 재귀적 차원에서 실행됨.
		}
		
	}
	
	static void debug() {
		for (int i = 0; i < visit.length; i++) {
			System.out.println(Arrays.toString(visit[i]));
		}
		System.out.println("===========debug============");
	}
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		depth = Integer.parseInt(tokens.nextToken());
		//// 입력 순서에 맞게 확률로 변환해서 세팅.
		E = Double.parseDouble(tokens.nextToken())/100;
		W = Double.parseDouble(tokens.nextToken())/100;
		S = Double.parseDouble(tokens.nextToken())/100;
		N = Double.parseDouble(tokens.nextToken())/100;
		// 세팅
		visit = new boolean[depth*2+1][depth*2+1];
		visit[depth][depth] = true; // 초기화 까먹음. 디버깅 포인트.
		pDeltas = new double[] {N,E,W,S};
		// 작업
		DFS(0,1,new int[] {depth,depth});
		// 출력
//		System.out.println(1-simplePathProbability);
		System.out.println(simplePathProbability);
	}// 메인
}
