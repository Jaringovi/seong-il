import java.io.*;
import java.util.*;

/**
- @author : "김성일"
- @since : 2024. 8. 22.
- @link : ""
- @timecomplex : "O(4^(n-1)*(n-1)^2)" 대력 1.6억 연산
- @performance : ["19,644 "KB | "276"ms] -> []
- @category : "완전탐색 - 중복순열"
- @풀이 시간 : 7분 (기존 소스코드 활용) | (소스코드 작성시간 70분)
- @성공까지 제출 수 : 1회
- @활용 소스코드 : "SWEA_4008_숫자만들기"
- @note : 
	연산자를 나열할수 있는 모든 경우의 수를 중복순열로 구하고. [4^(11)*(11)^2 => 대략 1.6억]
	각 경우의 수에 대해서 유효성검사를 실시
	통과한 경우의 수에 대해서 연산을 실시
	연산 결과를 보고 min max 값 비교 후 업데이트
*/

public class BOJ_14888_연산자_끼워넣기 {
	// 입력 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	
	// 세팅부
	static final char[] pool = {'+','-','*','/'};
	static int[] poolCounter; 
	static int[] numberOrder; 
	static int N;
	static int min;
	static int max;
	static boolean minIsFirst = true;
	static boolean maxIsFirst = true;
	
	
	// 중복순열 생성 . 중복 조합을 생성하면 최적화 가능하지만 번거로워서 그냥 중복순열 만들어서 유효성 검사로 필터링. 연산량 계산해보니까 가능해보임
	static void makePI(char[] leaf, int nowDigit, final int targetDigit, final char[] pool) {
		// 바닥 조건
		if(nowDigit >= targetDigit) {
			// 각 leaf노드 별로 할일 추가
			isValid(leaf);
			return;
		}
		// 재귀 파트
		for (int i = 0; i < pool.length; i++) {
			leaf[nowDigit] = pool[i];
			makePI(leaf, nowDigit+1, targetDigit, pool);
		}
	}
	
	// 중복순열 연산자 유효성 검사. 메소드
//		static boolean isValid(char[] leaf) {
	static void isValid(char[] leaf) {
		int plus = 0;
		int minus = 0;
		int multiple = 0;
		int divide = 0;
		// 각 연산자 개수 세기. 계속 목표 개수와 비교하면서 세면 목표개수를 초과했을때 바로 나가서 최적화 가능
		for(char c : leaf) {
			if(c=='+') {
				plus++;
			} else if(c=='-') {
				minus++;
			} else if(c=='*') {
				multiple++;
			} else if(c=='/') {
				divide++;
			}		
		}
		if((plus==poolCounter[0])&&(minus==poolCounter[1])&&(multiple==poolCounter[2])&&(divide==poolCounter[3])) { // 주어진 연산자 개수 조건 충족
			calc(leaf);
			return;
//				return true;
		}
//			return false; // 주어진 연산자 개수 조건 불충족
	}
	
	// 중복순열로 생성된 노드중 유효성 검사를 통과한 경우의수로 최종 계산. 메소드
	static void calc(char[] leaf) {
		int operand = numberOrder[0];
		for (int i = 0; i < (N-1); i++) { // 연산자 개수만큼 반복문 돈다.
			int operand2 = numberOrder[i+1]; //  numberOrder와 순서를 맞추기 위해 i에+1한다.
			char operator = leaf[i];
			if(operator=='+') {
				operand = operand + operand2;
			} else if(operator=='-') {
				operand = operand - operand2;
			} else if(operator=='*') {
				operand = operand * operand2;
			} else if(operator=='/') {
				operand = operand / operand2;
			}
		}
		// 이 시점에서 operand는 모든 연산이 끝난 상태
		minMaxUpdate(operand);
	}
	
	// 최소값 최대값 관리. 메소드
	static void minMaxUpdate(int contender) {
		if (minIsFirst&&maxIsFirst) {
			min = contender;
			max = contender;
			minIsFirst = false;
			maxIsFirst = false;
		}
//			System.out.println(min +" | "+ max);
		min = Math.min(min,contender);
		max = Math.max(max, contender);
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		// 입력부
		N = Integer.parseInt(input.readLine());
		//// 숫자 카드 풀
		numberOrder = new int[N];
		tokens = new StringTokenizer(input.readLine());
		for (int i = 0; i < N; i++) {
			numberOrder[i] = Integer.parseInt(tokens.nextToken());
		}
		//// 연산자 카드 풀
		poolCounter = new int[4];
		tokens = new StringTokenizer(input.readLine());
		for (int i = 0; i < 4; i++) {
			poolCounter[i] = Integer.parseInt(tokens.nextToken());
		}
		
		// 세팅부
		minIsFirst=true; // 이거 초기화 하는거 깜빡했다. 중요한 포인트
		maxIsFirst=true;		

		// 작업부
		makePI(new char[N-1], 0, N-1, pool);
		
		// 출력부
		System.out.println(max+"\n"+min);

	} // 메인 닫기
}
