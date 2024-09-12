// 이렇게 해서 틀리는 이유!!! 
// 소요시간이 더 늦은게 먼저 방문처리 해버리면, 소요시간이 더 빠른게 방문처리에서 필터링됨. 
// 따라서 방문처리는 참 거짓이 아니라, 도착한 시간을 기록해서 먼저 도착한 녀석을 기록해두고. 
// 도착시간을 비교해서 필터링 해야함.

import java.io.*;
import java.util.*;

public class Main {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	// 세팅
	static int N;
	static int K;
	static int miniTime = Integer.MAX_VALUE;
	static int[] visitTime; // 해당 좌표에 도착한 최단 시간을 기록함.
	
	// 메소드
	static void BFS() {
		// 초기화
		Queue<int[]> q = new LinkedList();
		q.offer(new int[] {N,0});
		
		// 돌아돌아 
		while(q.isEmpty() == false) {
			// 꺼냄
			int[] now = q.poll();
			int location = now[0];
			int time = now[1];
			
			// 확장할 필요 없는 큐 필터링
			//// 음수로 수렴하는것들은 불필요함. // location이 인덱스와도 연관돼있기 때문에 최상단에 위치해야함.
			if(location<0)
				continue;
			//// 최소 시간 이상인 값 버림.
			if(time>=miniTime) {
				continue;
			}
			// 이전에 방문한 최단시간보다 지금 도착한 시간이 더 빠르면 진행해야 하므로, 그 반대는 필터링
			if(visitTime[location]<=time) 
				continue;
			// 해당 좌표 최단시간 신기록 갱신
			visitTime[location]=time;
			//// K값 나올때 시간 저장. 이 시간보다 늦은건 필요 없음.
			if(location==K) {
				miniTime = Math.min(miniTime, time);
				continue; // 더 이상 확장 불필요
			}
			// K값을 넘겼을때는 내리는거만 작동시키기.(주로 *2로 계속 확장하는거 예외처리) 내리다가 시간초과하면 버려짐.
			else if(location>K) {
				q.offer(new int[] {location-1, time+1});
				continue;
			}
			// 예외처리 필요없는 것들
			else { // 0<= location <K
				q.offer(new int[] {location*2, time});
				q.offer(new int[] {location+1, time+1});
				q.offer(new int[] {location-1, time+1});
			}
		}
	}
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		// 세팅
		visitTime = new int[100000*10];
		Arrays.fill(visitTime, Integer.MAX_VALUE);
		// 작업
		BFS();
		// 출력
		System.out.println(miniTime);
	}

}
