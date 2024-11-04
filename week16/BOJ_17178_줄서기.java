import java.io.*;
import java.util.*;

public class Main {

	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static Queue<int[]> q = new LinkedList();
	static List<int[]> sortedQueue = new ArrayList();
	static Stack<int[]> stack = new Stack<>();
	static int nowPoint;
	static String mission = "GOOD"; // 실패할때 BAD로 만들고 작업 끝내면 되니까. 미리 GOOD으로 초기화. 한 마디로 현재 로직에서는 성공이 레귤러, 실패가 이레귤러인 상태이다.
	// 메소드
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		N = Integer.parseInt(input.readLine());
		for (int line = 0; line < N; line++) {
			tokens = new StringTokenizer(input.readLine());
			for (int human = 0; human < 5; human++) {
				String ticket = tokens.nextToken();
				
				StringTokenizer temp = new StringTokenizer(ticket, "-"); // 티켓을 알파벳과 숫자로 쪼갬
				String alphabet = temp.nextToken();
				int alphabetNumber = (int) alphabet.charAt(0); // 알파벳을 순자로 변환해서 저장.
				int number = Integer.parseInt(temp.nextToken()); // 숫자 저장
				int[] realTicket = {alphabetNumber, number};
				q.offer(realTicket);
				sortedQueue.add(realTicket);
			}
		}
		// 세팅
		// 주어진 조건대로 정렬해서 정답 만들기.
		sortedQueue.sort(Comparator
							.comparingInt((int[] e) -> e[0])
							.thenComparingInt((int[] e) -> e[1])
							);
		// 작업
		while(true) { // 탈출 조건을 바꿔봄.
			// 큐가 빠진 다음에는 일단 멈추고 로직의 스코프를 스택에서 봐야함.
			if(q.isEmpty()) {
				break;
			}
			
			// 스택이 안 비어있으면 여기서 선제 작업
			if(stack.empty()==false) {
				if(stack.peek().equals(sortedQueue.get(nowPoint))) { 	// 디버깅 포인트. 틀리면 .equals()를 ==으로 바꿔보자. 
																		//둘다 안되면 그냥 앞뒤로 비교하는 메소드 만들어서 호출하자.
					// 지금 스택에서 빼면됨. 그리고 정답에서 비교할 곳을 한칸 앞으로 보내줘야한다.
					stack.pop();
					nowPoint++;
					// 그러고 바로 아래로 가는게 아니라 다시 스택부터 확인해야됨. 따라서 continue로 아래 로직을 점프해준다.
					continue;
				}
			}
			// 스택에서 작업을 못할때 이곳으로 온다.
//			if(q.isEmpty()==false) { // 큐는 다 비어있고 스택은 남아있는 상황을 대비해서 검사한다. 아 근데 탈출조건 바뀌어서 이건 필요없겠네.
				int[] nowTicket = q.poll();
				if(nowTicket.equals(sortedQueue.get(nowPoint))) {
					// 지금 들어갈수 있으니까 아무데도 안 넣으면, 입장한 셈치고. 포인트 한칸 앞으로 이동.
					nowPoint++;
				} else {
					// 지금 못 들어가니까, 일단 대기열에 넣어.
					stack.push(nowTicket);
				}
//			}
			// 
		}
		
		// 큐 다 빠졌음. 스택 남아있는지 확인해보자.
		while(stack.empty()==false) {
			int[] nowTicket = stack.pop();
			if(nowTicket.equals(sortedQueue.get(nowPoint))) {
				nowPoint++;
			} else { // 여기서 입장을 못한다 => 미션실패!
				mission = "BAD";
				break;
			}
		}
		
		// 출력
		System.out.println(mission);
	}// 메인

}
