package study.week19;

import java.io.*;
import java.util.*;

public class BOJ_2660_회장뽑기 {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	// 세팅
	static int N;
	static List<Integer>[] connectionList ;
	static boolean[] visited;
	static int[] distanceGrade;
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		N = Integer.parseInt(input.readLine());
		//// 그래프 초기화
		connectionList = new List[N];
		for (int i = 0; i < N; i++) {
			connectionList[i] = new ArrayList<>();
		}
		//// 그래프 양방향으로 입력하기
		for(;;) {
			tokens = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokens.nextToken()) -1;
			int to = Integer.parseInt(tokens.nextToken()) -1;
			if (from == -2 && to ==-2) { // -1에서 -1해서 -2임.
				break;
			}
			connectionList[from].add(to);
			connectionList[to].add(from);
		}
		// 설정
		visited = new boolean[N];
		distanceGrade = new int[N];
		// 작업
		//// 노드별 점수 구하기
		for (int node = 0; node < N; node++) {
			BFS(node);
		}
		//// 회장 뽑기.
		int minGrade = 100;
		////// 최소값 뽑기
		for (int i = 0; i < distanceGrade.length; i++) {
			if(distanceGrade[i]<minGrade) {
				minGrade = distanceGrade[i];
			}
		}
		////// 후보 뽑기
		List<Integer> candi = new ArrayList<>(); 
		for (int i = 0; i < N; i++) {
			if(distanceGrade[i]==minGrade) {
				candi.add(i+1);
			}
		}
		// 출력
		output.append(minGrade).append(" ").append(candi.size()).append("\n");
		for (int i = 0; i < candi.size(); i++) {
			output.append(candi.get(i)).append(" ");
		}
		System.out.println(output);
	}
	// 메소드
	private static void BFS(int node) {
		// 방문 배열 초기화
		Arrays.fill(visited, false);
		visited[node] = true;
		int lastDepth = 0;
		// 큐 초기화.
		Queue<int[]> q = new LinkedList<int[]>();
		List<Integer> list = connectionList[node];
		for (int i = 0; i < list.size(); i++) {
			q.offer(new int[] {list.get(i), 1});
			visited[list.get(i)] = true;
		}
		// 큐에서 꺼내기.
		while(q.isEmpty()==false) {
			int[] nowNode = q.poll();
			int nowNodeNum = nowNode[0];
			int nowNodeDepth = nowNode[1];
			lastDepth = nowNodeDepth;
			
			List<Integer> nowList = connectionList[nowNodeNum];
			for (int i = 0; i < nowList.size(); i++) {
				// 방문한적 있는 노드는 점프.
				if(visited[nowList.get(i)]) {
					continue;
				}
				// 방문한 적 없는 노드는 깊이 더해서 큐에 넣기.
				q.offer(new int[] {nowList.get(i), nowNodeDepth+1});
				visited[nowList.get(i)] = true;
			}
		}
		// 모든 노드 돌았음.
		distanceGrade[node] = lastDepth;
	}

}
