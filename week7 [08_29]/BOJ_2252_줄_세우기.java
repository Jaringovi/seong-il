import java.io.*;
import java.util.*;


public class Main {
	
	//입력 루틴
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder ouput = new StringBuilder();
	
	// 세팅
	static int N;
	static int M;
	
	static int[] inEdgeCount;
	static ArrayList<Integer>[] nodeList; // 인티저 어레이리스트를 담는 배열. 배열의 인덱스는 노드의 번호. 들어있는 integer는 진출간선의 번호들
	
	// 메소드
	//// 위상정렬
	static void sortSort() {
		// init
		Queue<Integer> q = new LinkedList<Integer>();
		for (int i = 0+1; i < inEdgeCount.length; i++) { // i = 0+1 포인트. 0번 인덱스는 0번 노드인데, 0번 노드는 없어서 건너뜀,
			if(inEdgeCount[i]==0) { // 진입차수가 0인 노드번호는 큐에 인큐
				q.offer(i); // q.offer(inEdgeCount[i]) 왜 이랬어... 인덱스 번호를 넣는게 맞습니다.
			}
		}
		// 큐에서 빼기
		while(q.isEmpty()==false) { // 큐에 뭐가 들어있는 동안
			int nowNode = q.poll(); // 큐에서 꺼낼때마다 저장해줘야됨. 정렬되는 순서임
			ouput.append(nowNode).append(" "); // 답인 출력문에다가 저장. 추가로직이 필요한 경우는 LIST에다가 저장해줘도 OK
			for (int i = 0; i < nodeList[nowNode].size(); i++) {
				int nextNode = nodeList[nowNode].get(i);
				inEdgeCount[nextNode]--;	// nowNode가 보고있는 nextNode의 진입차수를 1개씩 줄여줌
				if(inEdgeCount[nextNode]==0) { // nextNode의 진입 차수가 0이되면 nextNode를 큐에 인큐해줌
					q.offer(nextNode);
				}
			}
		}
	}
	
	// 메인
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		inEdgeCount = new int[N+1];
		nodeList = new ArrayList [N+1];
		for (int i = 0; i < N+1; i++) {
			nodeList[i] = new ArrayList<>();
		}
		for (int i = 0; i < M; i++) {
			tokens = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokens.nextToken());
			int to = Integer.parseInt(tokens.nextToken());
			nodeList[from].add(to); // from에서 to로 가는 간선이니까. from노드에 to를 추가함.
			inEdgeCount[to]++; // to로 들어오는 간선이 있으니까 진입차수에 ++해줌.
		}
		// 세팅
		
		// 작업
		sortSort();
		// 출력
		System.out.println(ouput);
		
		
	}

}
