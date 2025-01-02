package study.week21;
import java.io.*;
import java.util.*;
public class BOJ_20165_인내의도미노장인호석 {
	// 입력 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	// 세팅
	static int N;
	static int M;
	static int R;
	static int[][] origin;
	static char[][] visit;
	static int score;
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		R = Integer.parseInt(tokens.nextToken());

		origin = new int[N][M];
		for (int row = 0; row < N; row++) {
			tokens = new StringTokenizer(input.readLine());
			for (int col = 0; col < M; col++) {
				origin[row][col] = Integer.parseInt(tokens.nextToken())-1;
			}
		}
		// 세팅
		visit = new char[N][M];
		for (int i = 0; i < N; i++) {
			Arrays.fill(visit[i], 'S');
		}
		// 작업
		for (int r = 0; r < R; r++) {
			// 공격 수행
			tokens = new StringTokenizer(input.readLine());
			int x = Integer.parseInt(tokens.nextToken())-1;
			int y = Integer.parseInt(tokens.nextToken())-1;
			char d = tokens.nextToken().charAt(0);
			attack(x,y,d);
			debug();
			// 수비 수행
			tokens = new StringTokenizer(input.readLine());
			x = Integer.parseInt(tokens.nextToken())-1;
			y = Integer.parseInt(tokens.nextToken())-1;
			defend(x,y);
			debug();

		}
		// 출력
		output.append(score).append("\n");
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < M; col++) {
				output.append(visit[row][col]).append(" ");
			}
			output.append("\n");
		}
		System.out.println(output);
	}
	private static void debug() {
//		output.append(score).append("\n");
//		for (int row = 0; row < N; row++) {
//			for (int col = 0; col < M; col++) {
//				output.append(visit[row][col]).append(" ");
//			}
//			output.append("\n");
//		}
//		System.out.println(output);
//		System.out.println("================");
//		output.setLength(0);
	}
	// 메소드
	private static void defend(int x, int y) {
		// 아무일도 일어나지 않는지 확인 => 서있으면 아무일도 없음
		if(visit[x][y]=='S') {
			return;
		} else {
			// 여기 도착하면 누워있을때임 => 일으켜 세움.
			visit[x][y]='S';
			return;
		}
	}
	
	private static void attack(int x, int y, char d) {
		// 아무일도 일어나지 않는지 확인 => 누워있으면 아무일도 없음
		if(visit[x][y]=='F') {
			return;
		}
		// 여기 도착하면 서있을때임
		int roundScore = 0;
		
		//// 동쪽으로 넘어갈때
		if(d=='E') {
			//// 도착지 미리 선언하고 갱신하면서 재사용. 
			//// 테스트 결과 반복문 검사할때마다 갱신된 값으로 if문 돌아감.
			int destination = y + origin[x][y];
			for (int i = y; i <= destination; i++) { // 도착한후까지는 작동함
				// 토큰이 범위 밖이면 중단해야함. 범위는 열방향, 행방향 다름.
				if(i<0 || M <=i) {
					break;
				}
				
				// 해당 셀이 서있을때 일을해야함.
				if(visit[x][i]=='S') {
					visit[x][i]='F';
					roundScore++;
					int candiDesti = i+origin[x][i];
					destination = Math.max(destination, candiDesti);
				}
			}
			score += roundScore;
		}
		
		//// 서쪽으로 넘어갈때
		if(d=='W') {
			//// 도착지 미리 선언하고 갱신하면서 재사용. 
			//// 테스트 결과 반복문 검사할때마다 갱신된 값으로 if문 돌아감.
			int destination = y - origin[x][y];
			for (int i = y; i >= destination; i--) { // 도착한후까지는 작동함
				// 토큰이 범위 밖이면 중단해야함. 범위는 열방향, 행방향 다름.
				if(i<0 || M <=i) {
					break;
				}
				
				// 해당 셀이 서있을때 일을해야함.
				if(visit[x][i]=='S') {
					visit[x][i]='F';
					roundScore++;
					int candiDesti = i-origin[x][i];
					destination = Math.min(destination, candiDesti); // 낮아지는 방향일때는 Math.max가아니라 Math.min => 디버깅 포인트.
				}
			}
			score += roundScore;
		}
		
		//// 남쪽으로 넘어갈때
		if(d=='S') {
			//// 도착지 미리 선언하고 갱신하면서 재사용. 
			//// 테스트 결과 반복문 검사할때마다 갱신된 값으로 if문 돌아감.
			int destination = x + origin[x][y];
			for (int i = x; i <= destination; i++) { // 도착한후까지는 작동함
				// 토큰이 범위 밖이면 중단해야함. 범위는 열방향, 행방향 다름.
				if(i<0 || N <=i) {
					break;
				}
				
				// 해당 셀이 서있을때 일을해야함.
				if(visit[i][y]=='S') {
					visit[i][y]='F';
					roundScore++;
					int candiDesti = i+origin[i][y];
					destination = Math.max(destination, candiDesti);
				}
			}
			score += roundScore;
		}
		
		//// 북쪽으로 넘어갈때
		if(d=='N') {
			//// 도착지 미리 선언하고 갱신하면서 재사용. 
			//// 테스트 결과 반복문 검사할때마다 갱신된 값으로 if문 돌아감.
			int destination = x - origin[x][y];
			for (int i = x; i >= destination; i--) { // 도착한후까지는 작동함
				// 토큰이 범위 밖이면 중단해야함. 범위는 열방향, 행방향 다름.
				if(i<0 || N <=i) {
					break;
				}
				
				// 해당 셀이 서있을때 일을해야함.
				if(visit[i][y]=='S') {
					visit[i][y]='F';
					roundScore++;
					int candiDesti = i-origin[i][y];
					destination = Math.min(destination, candiDesti); // 낮아지는 방향일때는 Math.max가아니라 Math.min => 디버깅 포인트.
				}
			}
			score += roundScore;
		}
		
		
	}
}
