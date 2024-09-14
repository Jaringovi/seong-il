// 문제 풀이 블로깅
// https://velog.io/@bed_is_good/%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%EB%B0%B1%EC%A4%80-3079-%EC%9E%85%EA%B5%AD%EC%8B%AC%EC%82%AC-JAVA

import java.io.*;
import java.util.*;

public class BOJ_3079_입국심사4 {
	// 입력 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static int M;
	static int[] origin;
	static long best;
	// 메소드
	//// 이진탐색
	static void bs(long start, long end) {
		// 바닥 조건
		if(start>end)
			return;
		
		// 재귀 파트
		long mid = (start+end)/2;
		if(isOK(mid)) {
			best = mid; // 성공할때마다 저장. 가장 최신 성공의 값으로 갱신됨. 최소값 유지.
			bs(start, mid-1);
		}else {
			bs(mid+1,end);
		}
	}
	
	//// 탐색 방향 결정 로직
	static boolean isOK(long mid) {
		long sum =0;
		for(int i=0; i<N; i++) {
			if(sum>=M) 
				break;
			sum += mid/origin[i]; // 정해진 시간(mid)동안 각각의 입국심사대가 처리할수 있는 사람의 수. 그것들의 합. // mid/origin[i]이 맞는데... origin[i]/mid 이따구로 해놨음.
		}
		if(sum>=M) // mid 시간동안 M명 이상을 처리할수 있음. ==> mid를 줄여보자
			return true;
		else // sum<M ==> mid 시간동안 M명을 처리할 수 없음. ==> mid를 늘려보자
			return false;
	}
	
	
	// 메인
	public static void main(String[] args) throws Exception {
		
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		
		origin = new int[N];
		for(int i=0; i<N; i++) {
			origin[i] = Integer.parseInt(input.readLine());
		}
		// 세팅
		// 작업
		bs((long) 0, (long) origin[0]*M); // 아무 입국심사대에서나 M번처리하면 모두 처리 가능하므로 끝값을 그렇게 설정.
		// 출력
		System.out.println(best);
	}
}
