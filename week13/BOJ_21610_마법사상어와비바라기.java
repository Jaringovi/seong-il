package study.week13;
// [ 17,520KB | 144ms ]
import java.io.*;
import java.util.*;

public class BOJ_21610_마법사상어와비바라기2 {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static int M;
	static int[][] origin;
	static int[][] visit;
	static int[][] cmd;
	static int round;
	static int[][] deltas = {{0,-1},{-1,-1}, {-1,0},{-1,+1}, {0,+1},{+1,+1}, {+1,0},{+1,-1}}; // deltas[cmd-1][0]
	static ArrayList<int[]> clouds = new ArrayList<>();
	
	
	// 메소드
	//// 구름의 생성
	static void make() {
		// 구름의 생성이 새로운 round의 기준임.
		round++; // 새 round 업데이트
		clouds.clear();
		// 구름 현재 위치 저장
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				if( (visit[row][col]!=round-1) && (origin[row][col]>=2) ) { // 이전 라운드에 구름이 도착한 곳이 아니고, 물이 2이상 있는 곳이라면. 구름생성
					clouds.add(new int[]{row,col});
					origin[row][col] -=2;
				}
			}
		}
//		debug("after make");
	}
	//// 구름의 이동 (%N 하면 되는거 아님?)
	static void move(int direction, int distance) {
		// 비내리기 먼저 다하고 그 다음에 물복사버그 써야 데이터의 일관성이 유지됨. 그게 규칙임.
		// 구름이 도착할때마다 물복사 버그 쓸때는 대각선 위치에 물이 없던곳이어도. 모든 구름이 비내리기를 다 하고 나서 물복사 버그 쓸때는 그곳에 물이 있을수도 있음. 
		for (int i = 0; i < clouds.size(); i++) {
			// 구름이 도착할 위치 계산
			int x = clouds.get(i)[0];
			int y = clouds.get(i)[1];
			int dx = deltas[direction-1][0]*distance;
			int dy = deltas[direction-1][1]*distance;
			int nx = x +dx;
			int ny = y +dy;
			
			// 상하좌우 연결하는 스킬 
			//// 디버깅 포인트 1. 
			//// 먼저 나머지를 구하자. nx가 아주 큰 음수일때 한번의 +N으로 정상범위에 들어올수 있도록. 
			//// 그리고 N을 더해주자. 
			//// 그리고 한번더 %N을 해주자. 이미 정상범위라면 +N하면서 정상범위 밖으로 나가니까. 
			//// 이러면 5칸 짜리에서 -100칸 뒤로 가는것도 계산가능하다.
			nx = ((nx%N)+N)%N;
			ny = ((ny%N)+N)%N;
			
			// 비 내리기.
			origin[nx][ny] +=1;
			// 구름 사라짐.
			//// 현재 round를 저장해서, 가장 최근에 방문한 곳임을 구분
			visit[nx][ny] = round;
		}
		// 물복사 버그
		for (int i = 0; i < clouds.size(); i++) {
			// 구름이 도착할 위치 계산
			int x = clouds.get(i)[0];
			int y = clouds.get(i)[1];
			int dx = deltas[direction-1][0]*distance;
			int dy = deltas[direction-1][1]*distance;
			int nx = x +dx;
			int ny = y +dy;
			// 상하좌우 연결하는 스킬
			nx = ((nx%N)+N)%N;
			ny = ((ny%N)+N)%N;
			// 물복사 버그 사용 시점
			int bonus = bug(nx, ny);
			origin[nx][ny] += bonus;
		}
		debug("after move");
	}
	//// 물복사 버그
	static int bug(int x, int y) {
		int sum = 0;
		for (int i = 0; i < 4; i++) {
			int diagonalIndex = i*2+1; // 기존 deltas에서 인덱스가 홀수면 대각방향인 것을 활용.
			int dx = deltas[diagonalIndex][0];
			int dy = deltas[diagonalIndex][1];
			int nx = x+dx;
			int ny = y+dy; // 디버깅 포인트 2. ny = x+dy 이따구로 적어둠.
			if( (0<=nx && nx<N) && (0<=ny && ny<N) && (origin[nx][ny]>0) ) { // 보드 안에 있는 격자이고. 격자에 물이 있을때
				sum++;
			}
		}
		return sum;
	}

	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		origin = new int[N][N];
		visit = new int[N][N];
		cmd = new int[M][2];
		//// 격자칸 받기
		for (int row = 0; row < N; row++) {
			tokens = new StringTokenizer(input.readLine());
			for (int col = 0; col < N; col++) {
				origin[row][col] = Integer.parseInt(tokens.nextToken());
			}
		}
		//// 명령어 집합 받기
		for (int i = 0; i < M; i++) {
			tokens = new StringTokenizer(input.readLine());
			cmd[i][0] = Integer.parseInt(tokens.nextToken());
			cmd[i][1] = Integer.parseInt(tokens.nextToken());
		}
		// 세팅
		debug("first");
		//// 최초의 구름 생성.
		round++; // 구름 생성할때 round가 새로 시작됨.
		//// 디버깅 포인트 3. 최초 구름 스폰지역들을 리터럴로 그대로 넣었음. (N,1), (N,2) 이렇게. 인덱스에 맞게 -1씩 해줬어야함.
		clouds.add(new int[] {(N)-1,1-1});
		clouds.add(new int[] {(N)-1,2-1});
		clouds.add(new int[] {(N-1)-1,1-1});
		clouds.add(new int[] {(N-1)-1,2-1});

		// 작업
		//// 디버깅 포인트 4.  작업순서 헷갈림. 구름의 생성이 작업의 마무리인데, 작업의 시작이라고 착각함.  
		for (int i = 0; i < cmd.length; i++) {
			int direction = cmd[i][0];
			int distance= cmd[i][1];
			move(direction, distance);
			make();
		}
		//// 합계 계산
		int sum = 0;
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				sum += origin[row][col];
			}
		}
		// 출력
		System.out.println(sum);
		
	} // 메인 종료
	
	// 디버깅 스킬.
	//// 각 함수의 호출에 따라 상태를 관찰하고 싶을때.
	//// 해당 함수 맨 마지막에 debug 코드 삽입. 어떤 함수가 불렀는지 구분하는 문자열도 그 함수에 넣어두면. 디버깅 코드 유지보수가 편해짐.
	//// 제출할때는 debug() 내부를 주석처리하면 작동안함.
	static void debug(String funcName) {
//		for (int i = 0; i < N; i++) {
//			System.out.println(Arrays.toString(origin[i]));
//		}
//		System.out.println("========================="+funcName);
	}
}
