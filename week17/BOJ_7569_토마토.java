import java.io.*;
import java.util.*;

//입력 받을때, 
//토큰이 1이면 해당 위치를 큐에 넣고 해당 위치 방문처리. => + 디버깅. 큐에 넣을때마다 count++. 토마토가 있는거니까 total++  
//토큰이 0이면 패스,  => + 디버깅. 토마토가 있는거니까 total++  
//토큰이 -1이면 방문처리
//
//큐에 들어가는 형태:
//[depth,x,y,z]
//
//큐에서 하나씩 빼면서 다음으로 갈수있는지 확인하고 갈수있으면 해당 위치를 큐에 넣고 depth를+1해준다. 
//큐에 넣을때 count도 ++해준다.
//큐에 넣자마자 바로 방문처리도 해준다.
//
//다음으로 갈수있는지 확인하는 로직은 
//	큐브 범위 밖이면 못가고. 안이면 갈수있고. => +디버깅. if(nextX<0 && nextX>=X) => if(nextX<0 || nextX>=X)
//	해당 위치가 방문한 곳이면 못가고, 방문 안한곳이면 갈수있고.
//큐를 다 뺐을때 total==count이면 모든 토마토가 익은거니까 마지막에 뽑은 것의 depth를 제출
//아니면 다 익지 못하는거니까 -1 제출


public class Main {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int total;
	static int count;
	static int lastDepth;
	
	static int X;
	static int Y;
	static int Z;
	
	static int[][][] cube;
	static boolean[][][] visit; // 방문처리 배열. 
		//토마토가 없는 -1도 이미 방문처리해두고, 방문한 곳을 벽취급하자. 벽을 만나면 확장을 멈추는 방식
	static int[][] deltas = {
			{1,0,0}, {-1,0,0}, 
			{0,1,0}, {0,-1,0}, 
			{0,0,1}, {0,0,-1} 
	};
	static Queue<int[]> q = new LinkedList();
	
	// 메소드
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		X = Integer.parseInt(tokens.nextToken());
		Y = Integer.parseInt(tokens.nextToken());
		Z = Integer.parseInt(tokens.nextToken());
		
		cube = new int[X][Y][Z];
		visit = new boolean[X][Y][Z];
		
		for (int z = 0; z < Z; z++) {
			for (int y = 0; y < Y; y++) {
				tokens = new StringTokenizer(input.readLine());
				for (int x = 0; x < X; x++) {
					int value = Integer.parseInt(tokens.nextToken());
					if(value==1) {
						q.offer(new int[] {0,x,y,z});
						visit[x][y][z] = true;
						count++;
						total++;
					} else if(value == -1) {
						visit[x][y][z] = true;
					} else { // value ==0
						total++;
					}
				}
			}
		}
		// 세팅
		// 작업
		while(q.isEmpty()==false) {
			int[] nowNode = q.poll();
			int nowDepth = nowNode[0];
			lastDepth = nowDepth;
			int nowX = nowNode[1];
			int nowY = nowNode[2];
			int nowZ = nowNode[3];
			for (int i = 0; i < deltas.length; i++) {
				int nextX = nowX + deltas[i][0];
				int nextY = nowY + deltas[i][1];
				int nextZ = nowZ + deltas[i][2];
				// 확장 가능한지 확인.
				if(nextX<0 || nextX>=X) { // X 방향으로 범위이탈
					continue;
				}
				if(nextY<0 || nextY>=Y) { // Y 방향으로 범위이탈
					continue;
				}
				if(nextZ<0 || nextZ>=Z) { // Z 방향으로 범위이탈
					continue;
				}
				if(visit[nextX][nextY][nextZ]) { // 다음 위치가 이미 방문한 곳임
					continue;
				}
	
				// 여기까지 내려왔다 => 확장 가능하다.
				q.offer(new int[] {nowDepth+1, nextX, nextY, nextZ});
				visit[nextX][nextY][nextZ] = true;
				count++;
			}
		}
		// 큐 다 빠짐
		
		// 출력
		if(count==total) {
			System.out.println(lastDepth);
		} else{
			System.out.println(-1);
		}
	}// 메인 종료

}