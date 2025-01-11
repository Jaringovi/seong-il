package study.week22;
import java.io.*;
import java.util.*;
public class BOJ_13702_이상한술집 {
	// 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static int K;
	static int best;
	static int[] kettles;
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		kettles = new  int[N];
		int maxCapacity=0;
		for (int i = 0; i < N; i++) {
			int kettle = Integer.parseInt(input.readLine());
			kettles[i] = kettle;
			maxCapacity = Math.max(maxCapacity, kettle);
		}
		// 세팅
		// 작업
		binarySearch(0,maxCapacity);
//		System.out.println(isOK(351));
		
		// 출력
		System.out.println(best);
	}
	// 메소드
	private static void binarySearch(int left, int right) {
	    while (left <= right) {
	        int mid = left + (right - left) / 2;

	        if (isOK(mid)) {
	            best = mid;
	            left = mid + 1;
	        } else {
	            right = mid - 1;
	        }
	    }
	}
	private static boolean isOK(int mid) {
		// 모두에게 mid만큼 막걸리를 줌
		// 주전자 하나를 꺼내서 거기서 mid만큼 덜어내서 줄수있는지 확인함
		// 줄수 있으면 다음 사람으로 넘어감.
		// 주전자에 남은 양이 mid보다 적으면, 새로운 주전자를 꺼냄 (새 주전자인데도 mid보다 적을수 있음 => 버리고 새거 꺼냄)
		// 새로 꺼낸 주전자에서 줄수있는지 다시 확인함
		
		// 초기화 레벨
		int kettleCount = 0;
		int humanCount = 0;
		
		int nowCapacity = kettles[kettleCount];
		kettleCount++;
		
		//반복 레벨
		for(;;) {
			// 테스트를 반복할 이유가 사라지는 순간: humanCount가 K이상일때, kettleCount가 주어진 수 N이상일때
			if(humanCount>=K) { // 사람들 다 줌
				break;
			}
			if(kettleCount>N) { // 주전자 다 씀. 디버깅 포인트. kettleCount>=N ==>> kettleCount>N
				break;
			}
//			System.out.println(humanCount+" "+kettleCount+" "+nowCapacity );
			// 테스트 레벨
			if(nowCapacity>=mid) { // 지금 줄수있네 => 한명한테 줌
				nowCapacity -= mid;
				humanCount++;
			} else {	// 지금 줄수없네 => 새 주전자 꺼내
				if(kettleCount==N) {  // 마지막 주전자를 고른 상태에서 새로운 주전자를 꺼낼수 없어서, 예외처리
					kettleCount++; // 종료조건에 도달하기 위해 카운트는 올려준다.
					continue;
				}
				nowCapacity = kettles[kettleCount];
				kettleCount++;
			}
		}
		
		// 디버그 레벨
//		System.out.println(humanCount);
		
		// 확인 레벨 : 
			// 주전자를 다 쓰고 사람들에게 다 못 줬거나 => false
			// 주전자를 다 쓰고 사람들에게 다 줬거나 => true
			// 주전자를 다 안 쓰고 사람들에게 다 줬거나 => true
		if(humanCount==K) {
			return true;
		} else {
			return false;
		}
	}
}
