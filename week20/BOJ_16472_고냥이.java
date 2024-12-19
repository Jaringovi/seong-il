package study.week20;
import java.io.*;
import java.util.*;
public class BOJ_16472_고냥이 {
	// 입력 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//	static StringTokenizer tokens;
	// 세팅
	static int N;
	static char[] charArray;
	
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		N = Integer.parseInt(input.readLine());
		charArray = input.readLine().toCharArray();
		// 세팅
		int right = 0;
		int left = 0;
		int maxLength = -1;
		Map<Character, Integer> usePool = new HashMap<>();
		//// usePool 초기화. 최소 1개는 쓰니까. right 0일때 그냥 넣고 시작하자.
		usePool.put(charArray[right], 1);
		// 작업
		while(right < charArray.length) { // right가 배열의 마지막 인덱스에 도착하면 끝남.
//			System.out.println("roop1");
//			System.out.println("right: " +right);
//			System.out.println("left: " +left);
//			System.out.println("=========");
			// 아직 더 확장 가능. 
			// 정확히는 N과 같으면 확장 가능할지 아닐지 미지수임. 
			// 따로 분기로 내지 말고. 
			// 일단 추가하고 여기서 할일을 확장 불가능한 순간에 해주는 방식으로 처리하자.
			if(usePool.size()<=N) {
				// 한칸 더 가고 => 예상 디버깅 포인트. 아 잠만 이러면 처음에 넣어줄때 예외처리를 생각해야겟는데??
				right++;
				if(right >= charArray.length) {
					break;
				}
				// 도착한 곳의 값 풀에 추가
				if(usePool.containsKey(charArray[right])) {
					int count = usePool.get(charArray[right]);
					usePool.put(charArray[right], count+1); 					
				} else {
					usePool.put(charArray[right], 1); 					
				}
			}
			// 확장 불가능함. 
			// 먼저 left와 right의 인덱스 차이를 구해서 지금 구한 문자열의 길이를 갱신. 
			// 이때 right은 지금 위치에서 -1을 해줘야 함. right-1 일때까지만 usePool.size()==N임.
			// left 움직일 차례. 
			else {
				int nowLength = (right-1) -left +1;
				maxLength = Math.max(maxLength, nowLength);
//				System.out.println(maxLength);
				// 사용 문자 풀의 크기가 조건인 N과 같아질때까지. left를 옆으로 보낼거임.
				while(usePool.size()>N) {
//					System.out.println("roop2");
					int count = usePool.get(charArray[left]);
					// 마지막 남은 하나임. 이거 통과하면 이제 사용 문자 풀의 크기가 하나 줄어들어야 됨. remove 처리하고 left++;
					if(count==1) {
						usePool.remove(charArray[left]);
					} else {
						usePool.put(charArray[left], count-1); // 이 부분 처리 안해서 디버깅했음.
					}
					left++;
				}
			}
		}
		// 위에 있는 코드는 마지막 직전 구간의 문자열 길이를 구하고. 마지막 구간을 만드는 코드임
		// 이제 마지막 구간의 길이를 구하는 작업이 남았음.
		int nowLength = (right-1) -left +1;
		maxLength = Math.max(maxLength, nowLength);
		// 출력
		System.out.println(maxLength);
	}
	// 메소드

}
