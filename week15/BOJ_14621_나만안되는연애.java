import java.io.*;
import java.util.*;
public class BOJ_14621_나만안되는연애 {

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
	
	static int allSum;
	static boolean[] visited;
	static int[] sex;
	static ArrayList<int[]>[] nodeList; // 각 노드번호에서 갈수있는 노드 저장하기

	
	
	// 메인	
	public static void main(String[] args) throws IOException {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		V = Integer.parseInt(tokens.nextToken());
		E = Integer.parseInt(tokens.nextToken());
		//// extra // 성별 확인하는 배열 입력받기
		sex = new int[V+1];
		tokens = new StringTokenizer(input.readLine());
		for (int i = 0+1; i < V+1; i++) {
			 if(tokens.nextToken().equals("M")) {
				 sex[i] = +1;
			 } else {
				 sex[i] = -1;
			 }
		}
		
		nodeList = new ArrayList[V+1];
		for (int i = 0; i < V+1; i++) {
			nodeList[i] = new ArrayList<>();
		}
		for (int i = 0; i < E; i++) {
			tokens = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokens.nextToken());
			int to = Integer.parseInt(tokens.nextToken());
			int weight = Integer.parseInt(tokens.nextToken());
			
//			int[] edge = {to,weight};
			if(sex[to]!=sex[from]) { // 서로의 성별이 다를때만 간선 추가.
				nodeList[from].add(new int[] {to,weight});
				nodeList[to].add(new int[] {from,weight});
			}
		}
		// 세팅
		visited = new boolean[V+1];
		visited[0] = true; // 어차피 갈일 없는 빈곳이니까 미리 방문처리
		// 작업
		PriorityQueue<Node> q = new PriorityQueue<>();
		q.offer(new Node(1,0)); // 1번 노드부터 시작. 시작할때는 간선을 타고 간게 아니니까 가중치는 0으로 넣어준다. //Node클래스에 static 안달아주면 컴파일 안되넹..
		int usedNodes=0;
		while(q.isEmpty()==false) {
			if(usedNodes==V) {
				break;
			}
			Node now = q.poll(); // 방문한 것임.
			if(visited[now.vertex]) { // 이번 싸이클이 방문한 노드면 이번 싸이클 패스
				continue;
			}
			// 방문처리
			visited[now.vertex] = true;
			usedNodes++;
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
		if(usedNodes==V) {
			System.out.println(allSum);
		} else {
			System.out.println(-1);
		}
		
	} // 메인 종료


}
