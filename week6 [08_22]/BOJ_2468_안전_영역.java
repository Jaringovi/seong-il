/**
- @author : "김성일"
- @since : 2024. 08. 23.
- @link : "https://www.acmicpc.net/problem/2468"
- @timecomplex : "O(n)"
- @performance : ["16276"KB | "204"ms] -> []
- @category : "완전탐색 - DFS"
- @풀이 시간 : 60분
- @성공까지 제출 수 : 2회 (아 디버깅하려고 반복문 회수 줄인거 까먹었다)
- @활용 소스코드 : ""
- @디버깅_포인트 : 
- @공부할_메소드 : 
- @훈련할_스킬 : 
	dfs들어갈때 준서식 제어방식 사용하니까 너무 편함. 이걸로 정착하자
	오른쪽 아래만 전파 시켰는데 장애물을 넘어서 역탐색을 할수도 있다는 사실을 간과했다
- @note : 
	물에 잠기지 않는 안전한 영역 == 안잠긴곳 + 상하좌우 인접해있으면 한 덩어리로 취급함.
	비가 오는 양을 0부터 100까지 테스트해보고 안전 영역의 최대개수를 찾아라.
	
	문제 풀이 20분 코딩 시작
	
*/
import java.util.*;
import java.io.*;

public class BOJ_S1_2348_안전영역 {
	// 입력 루틴
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	// 세팅
	static int[][] originMatrix;
	static boolean[][] visited;
	static int safeArea;   	// 안전 영역 개수
	static int safeAreaMax; // 안전영역 개수 최대값, 계속 업데이트
	static int maxHeight;	// 최적화용 // 일단은 미사용
	static boolean safeAreaMaxIsFirst = true; // 사용후 꼭 false로 토글 필수!!!!!!
	
	static int N;
	static int[][] delta = {{0,1},{0,-1},{1,0},{-1,0}};
	static boolean canCount;
	
	
	// 메소드
	
	//// 재귀적 그래프 탐색  // 디버깅 포인트... 왜 오른쪽, 아래만 봤지... 그냥 상하좌우로 봐라...
	static void dfs(int x, int y, final int rain) {
		visited[x][y] = true;
		// 바닥 조건
		if(rain >= originMatrix[x][y]) { // x-y는  물에 잠김. 그래프 확장 불가 => 바닥 조건 = 리턴
			return;
		}
		canCount = true;
		// 재귀 파트
		for (int i = 0; i < delta.length; i++) {
			int dx = x+delta[i][0];
			int dy = y+delta[i][1];
			if((0<=dx && dx<N) && (0<=dy && dy<N) && (visited[dx][dy]==false)) { // 인덱스 유효성 검사 + 방문하지 않은 셀로만 이동!
				dfs(dx,dy,rain);
			}
		}
	}
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 입력부
		N = Integer.parseInt(input.readLine());
		originMatrix = new int[N][N];
		for (int row = 0; row < N; row++) {
			tokens = new StringTokenizer(input.readLine());
			for (int col = 0; col < N; col++) {
				originMatrix[row][col] =  Integer.parseInt(tokens.nextToken());
			}
		}
		
		// 세팅부
		// 작업부
		for (int rain = 0; rain < 101; rain++) { // 0을 넣은 이유 모두 높이가 1일경우 0일때 안전영역의 개수가 1이고 1이상일때 0이라서
			safeArea = 0;
			visited = new boolean[N][N]; // 디버깅 초기화 포인트가 여기임.
			for (int row = 0; row < N; row++) {
				for (int col = 0; col < N; col++) {
					if(visited[row][col]==false) { // 미방문 == false 미방문이면 작업!
						canCount = false; // 초기화 포인트
						dfs(row,col,rain);
						if(canCount) {
							safeArea++;
						}
					}
				}
			} // 전 범위 그래프 탐색 종료
//			System.out.println(safeArea);
			if(safeAreaMaxIsFirst) {
				safeAreaMax = safeArea;
				safeAreaMaxIsFirst = false; // 또 까먹을뻔 했네, 까먹는게 습관이야 아주
			} else {
				safeAreaMax = Math.max(safeAreaMax, safeArea);
			}
		}
		// 출력부
		System.out.println(safeAreaMax);
	}

}
