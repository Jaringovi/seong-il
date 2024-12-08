import java.io.*;
import java.util.*;
// 판이 주어졌을때, 누가 이겼는지 확인. 
// 둘중 하나만 이길수 있다. 승리조건을 한게임에서 2개 가질수 없다.
// 색깔별로 돌의 좌표를 리스트에 전부 담아둔다.
// 5개가 연속인 것의 모습은 1 ㅡ / \ 이렇게 네종류이다.

// 리스트에서 하나씩 꺼내서 4방향 각각에 대해 양방향 DFS 탐색을 실시한다.
// 양방향 DFS 탐색결과 동일색의 돌의 개수가 정확히 5일때 탐색을 종료한다.
// 방문처리 불필요 => 최적화시 방문할때 어느 방향으로 진행중이었는지도 기록하면 최적화될듯함.
// 시간복잡도 => 최대 돌의 개수 * 탐색방향 * 탐색깊이 => 19*19*4*5 = 361*20 = 7220

public class Main {
	// 입력 고정
	static BufferedReader input  = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	// 세팅
	static List<int[]> blacks = new ArrayList<>();
	static List<int[]> whites = new ArrayList<>();
	static int[][] origin = new int[19][19];
	static int[][] deltas = {{0,1}, {1,0}, {-1,1}, {1,1}};
	static int localCount;
	static boolean gameEnd;
	static int winner;
	static int winX;
	static int winY;
	// 메소드
	
	// DFS(xy, 0~3, 1~2)
	static boolean DFS(int[] xy, final int direction, final int color) {
		localCount=0; // 매시도마다 초기화
		forwardDFS(xy, direction, color);
		backwardDFS(xy, direction, color);
		localCount--; // 처음에 시작점을 두번 더해서 한번 빼줌
		
		if(localCount==5) { // 성공시점
			gameEnd = true;
			winner = color;
			output.append(winner).append("\n").append(winX +1).append(" ").append(winY +1);
			System.out.println(output);
			return true;
		} else {
			return false;
		}
	}
	
	//
	static void forwardDFS(int[] xy, final int direction, final int color) {
		if(localCount>6) {
			return;
		}
		int x = xy[0];
		int y = xy[1];
		// 바닥 조건 (동일색이 아니면 중단)
		if(origin[x][y] != color) { // x,y 가 유효범위인지 확인할 필요X 앞에서 이미 다 검증된 상태.
			return;
		}
		// 재귀 파트
		//// 해당 위치가 동일색이니까 개수카운트
		localCount++;
		//// 원점으로부터 방향에 맞게 이동
		int dx = deltas[direction][0];
		int dy = deltas[direction][1];
		int nx = x+dx;
		int ny = y+dy;
		//// 범위 유효성 확인
		if(isValid(nx, ny)) {
			forwardDFS(new int[] {nx,ny}, direction, color);
		}
	}
	
	static void backwardDFS(int[] xy, final int direction, final int color) {
		if(localCount>6) {
			return;
		}
		int x = xy[0];
		int y = xy[1];
		// 바닥 조건 (동일색이 아니면 중단)
		if(origin[x][y] != color) { // x,y 가 유효범위인지 확인할 필요X 앞에서 이미 다 검증된 상태.
			return;
		}
		// 재귀 파트
		//// 해당 위치가 동일색이니까 개수카운트
		localCount++;
		winX = x;
		winY = y;
		//// 원점으로부터 방향에 맞게 이동
		int dx = -deltas[direction][0];
		int dy = -deltas[direction][1];
		int nx = x+dx;
		int ny = y+dy;
		//// 범위 유효성 확인
		if(isValid(nx, ny)) {
			backwardDFS(new int[] {nx,ny}, direction, color);
		}
	}
	
	static boolean isValid(int nx, int ny) {
		if(nx<0 || nx>=19 || ny<0 || ny>=19) {
			return false;
		} else {
			return true;
		}
	}
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		for (int row = 0; row < 19; row++) {
			tokens = new StringTokenizer(input.readLine());
			for (int col = 0; col < 19; col++) {
				int token = Integer.parseInt(tokens.nextToken());
				origin[row][col] = token; // 보드에 저장. 추후 승리확인용
				if(token == 1) {
					blacks.add(new int[]{row,col});
				}else if(token == 2){
					whites.add(new int[]{row,col});
				}
			}
		}
		// 세팅
		// 작업
		//// 흑돌 먼저 승리 확인
		for (int i = 0; i < blacks.size(); i++) {
			int[] xy = blacks.get(i);
			for (int direction = 0; direction < 4; direction++) {
				if(DFS(xy, direction, 1)) { // 제출할 좌표는 4방향 모두 xy이다. => 아 맨 왼쪽 잡는 로직 추가필요 역방향에서 맨마지막값 받아오면 될듯
					break;
				}
			}
			// 아래 코드는 DFS에서 처리하자.
			if(gameEnd) { 
				break;
			}
		}
		
		
		//// 백돌 승리 확인
		for (int i = 0; i < whites.size(); i++) {
			if(gameEnd) { // break가 여기서 걸리면 이미 흑돌에서 이긴 상황
				break;
			}
			int[] xy = whites.get(i);
			for (int direction = 0; direction < 4; direction++) {
				if(DFS(xy, direction, 2)) { // 제출할 좌표는 4방향 모두 xy이다. => 아 맨 왼쪽 잡는 로직 추가필요 역방향에서 맨마지막값 받아오면 될듯
					break;
				}
			}
			if(gameEnd) {
				break;
			}
		}
		// 출력
		if(gameEnd == false) {
			System.out.println(0);
		}
	}
}
