package study.week13;
// [ 11,540KB | 64ms ]
import java.io.*;
import java.util.*;
public class BOJ_12919_A와B2_3 {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	// 세팅
	static char[] S;
	static char[] T;
	static int answer;
	static boolean solve;
	// 메소드

	//// A를 제거한 결과를 반환
	static char[] aa(char[] start) {
		int l = start.length;
		char[] result = new char[l-1];
		for (int i = 0; i < l-1; i++) {
			result[i] = start[i];
		}
		return result;
	}
	////B를 제거한 결과를 반환
	static char[] bb(char[] start) {
		int l = start.length;
		char[] result = new char[l-1];
		for (int i = 0; i < l-1; i++) {
			result[i] = start[(l-1)-i];
		}
		return result;
	}
	//// 재귀
	static void recursion(char[] start) {
//		System.out.println(Arrays.toString(start)); // 디버깅용 코드
		// 바닥 조건
		if(start.length<=S.length) { 	// 여기서 끝내야 함.
			if(Arrays.equals(start, S)) { // 타겟이 되는 S와 바닥에서 만들어진 문자열이 같다면 전체 재귀 종료.
				answer = 1;				// 사실상 solve 대신 써도 됨.
				solve =true;			// 더 이상의 재귀적 확장을 막기 위함.
			}
			return;
		}
		
		// 문제 해결된 상태면 확장없이 종료. 앞으로 추가확장 안됨. 이미 Call된 것들만 남는데, 이것들도 똑같은 방식으로 종료됨.
		if(solve)
			return;
		
		// 재귀 파트
		char first = start[0];
		char last = start[start.length-1];
		if(last=='A') {
			recursion(aa(start));
		} 
		if(first=='B') {
			recursion(bb(start));
		}
		return;
	}
	
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		S = input.readLine().toCharArray();
		T = input.readLine().toCharArray();
		// 세팅
		// 작업
		recursion(T);
		// 출력
		System.out.println(answer);
		
	} // 메인종료

}
