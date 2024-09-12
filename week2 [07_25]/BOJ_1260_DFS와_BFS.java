// 양방향 + 두 노드 사이의 간선이 두개 이상 가능
import java.io.*;
import java.util.*;

public class Main {
	// 입력고정 
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	static StringBuilder output2 = new StringBuilder();
	
	// 세팅
	static int N;
	static int M;
	static int V;
	
	static PriorityQueue<Integer>[] nodeList; 	// DFS용
	static PriorityQueue<Integer>[] nodeList2;	// BFS용
	static boolean[] visited;
	static boolean[] visited2;
	
	// 메소드
	//// DFS
	static void DFS(int now) {
		// 바닥 조건
		if(visited[now]) {// 방문한 노드일때 탈출!
			return;
		}
		// 방문처리
		visited[now] =true;
		output.append(now).append(" ");
		// 재귀 파트
		while(nodeList[now].isEmpty()==false) { //맨 끝이라 더 갈곳이 없는 노드는 반복문 시작하질 않음. 끝남
			int next = nodeList[now].poll();
//			if(visited[next]) // 방문노드면 패스 ==> 안해도 됨. 어차피 바닥조건에서 걸러짐.
//				continue;
			DFS(next);
		}
	}
	
	//// BFS
	static void BFS(int now) {
		// 초기화
		Queue<Integer> q = new LinkedList<Integer>();
		q.offer(now);
		
		// 순회
		while(q.isEmpty()==false) {
			int next = q.poll();
			if(visited2[next]) // 방문노드면 패스
				continue;
			visited2[next] = true; // 디큐했을때 방문처리
			output2.append(next).append(" "); // 방문할때 마킹
			while(nodeList2[next].isEmpty()==false) { // 디큐한 노드 => 다음 노드 => 다음 노드에서 갈수있는곳 모두 인큐
				int next2 = nodeList2[next].poll();
				if(visited2[next2]) // 방문노드면 패스
					continue;
				q.offer(next2);
			}
		}
	}
	
	
	// 메인
	public static void main(String[] args) throws Exception {
		
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		V = Integer.parseInt(tokens.nextToken());
		nodeList = new PriorityQueue[N+1];
		nodeList2 = new PriorityQueue[N+1];
		
		for (int i = 0; i < N+1; i++) {
			nodeList[i] = new PriorityQueue<Integer>();
			nodeList2[i] = new PriorityQueue<Integer>();
		}
		//// 양방향 그래프 인접리스트로 구현. 대신 우선순위 큐로 구현해서 항상 노드번호가 최소값인 녀석부터 꺼낼수 있도록.
		for (int i = 0; i < M; i++) {
			tokens = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokens.nextToken());
			int to = Integer.parseInt(tokens.nextToken());
			nodeList[from].offer(to);
			nodeList[to].offer(from);
			nodeList2[from].offer(to);
			nodeList2[to].offer(from);
		}
		
		
		
		// 세팅
		visited = new boolean[N+1];
		visited2 = new boolean[N+1];
		// 작업
		DFS(V);
		BFS(V);
		// 출력
		System.out.println(output);
		System.out.println(output2);
	}// 메인 종료

}
