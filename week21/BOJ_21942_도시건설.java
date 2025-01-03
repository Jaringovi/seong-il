package study.week21;

import java.io.*;
import java.util.*;

// 1. 임의의 정점을 선택하여 하나의 정점을 갖는 최초의 트리를 구성한다.
// 2. 트리에 포함된 정점과 트리에 포함되지 않은 정점 간의 간선 중 가장 작은 가중치를 가지는 간선을 선택하여 트리에 추가한다.
// 3. 모든 정점이 트리에 포함될 때 까지 2를 반복한다.

public class BOJ_21942_도시건설 {
	// 클래스 만들기.
	static class Node implements Comparable<Node> {
		int vertex, weight;
		
		Node(int vertex, int weight){
			this.vertex = vertex;
			this.weight = weight;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.weight, o.weight);
		}
		
	}
	
	// 입력루틴
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	// 세팅
	static int V;
	static int E;
	
	static long allSum;
	static long allCost;
	static int visitCount;
	static boolean[] visited;
	static ArrayList<int[]>[] nodeList; // 각 노드번호에서 갈수있는 노드 저장하기

	
	
	// 메인	
	public static void main(String[] args) throws IOException {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		V = Integer.parseInt(tokens.nextToken());
		E = Integer.parseInt(tokens.nextToken());
		nodeList = new ArrayList[V+1];
		for (int i = 0; i < V+1; i++) {
			nodeList[i] = new ArrayList<>();
		}
		for (int i = 0; i < E; i++) {
			tokens = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokens.nextToken());
			int to = Integer.parseInt(tokens.nextToken());
			int weight = Integer.parseInt(tokens.nextToken());
			allCost += weight;
			
			nodeList[from].add(new int[] {to,weight});
			nodeList[to].add(new int[] {from,weight});
		}
		// 세팅
		visited = new boolean[V+1];
		visited[0] = true; // 어차피 갈일 없는 빈곳이니까 미리 방문처리
//		visitCount++;
		// 작업
		PriorityQueue<Node> q = new PriorityQueue<>();
		q.offer(new Node(1,0)); // 1번 노드부터 시작. 시작할때는 간선을 타고 간게 아니니까 가중치는 0으로 넣어준다. //Node클래스에 static 안달아주면 컴파일 안되넹..
		
		while(q.isEmpty()==false) {
			Node now = q.poll(); // 방문한 것임.
			if(visited[now.vertex]) { // 이번 싸이클이 방문한 노드면 이번 싸이클 패스
				continue;
			}
			// 방문처리
			visited[now.vertex] = true;
			visitCount++;
			allSum += now.weight;
			// 인큐 타이밍
			for (int i = 0; i < nodeList[now.vertex].size(); i++) {
				int[] edge = nodeList[now.vertex].get(i);
				int nextVertex = edge[0];
				int edgeWeight = edge[1];
				if(visited[nextVertex]) { // 갈수있는 노드가 방문한 노드면 패스. 점프
					continue;
				}
				q.offer(new Node(nextVertex,edgeWeight));
			}
		}
		// 출력
//		System.out.println(visitCount);
		if(visitCount==V) {
			System.out.println(allCost-allSum);
		} else {
			System.out.println(-1);
		}
		
	} // 메인 종료

}
