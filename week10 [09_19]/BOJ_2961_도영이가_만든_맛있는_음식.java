// 문제 풀이 블로깅
// https://velog.io/@bed_is_good/%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%EB%B0%B1%EC%A4%80-2961-%EB%8F%84%EC%98%81%EC%9D%B4%EA%B0%80-%EB%A7%8C%EB%93%A0-%EB%A7%9B%EC%9E%88%EB%8A%94-%EC%9D%8C%EC%8B%9D-JAVA

import java.io.*;
import java.util.*;
import java.math.*;


public class BOJ_2961_도영이가_만든_맛있는_음식 {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	// 세팅
	static int N;
	static int[][] origin; // new int[N][2]
	static boolean[] subset;
	static boolean miniIsFirst = true;
	static BigInteger mini;
	
	// 메서드
	// 부분집합 구하기
	static void makeSubset(boolean[] leaf, int nowDigit, final int targetDigit) {
		// 바닥 조건
		if(nowDigit>=targetDigit) {
			// 추가작업
			calc();
			return;
		}
		
		// 재귀 파트
		leaf[nowDigit] = false;		// 미포함
		makeSubset(leaf, nowDigit+1, targetDigit);
		leaf[nowDigit] = true;		// 포함
		makeSubset(leaf, nowDigit+1, targetDigit);
	}
	
	// 각각의 부분집합에서 계산하기
	static void calc() {
		// 처음값 넣기 위함.
		
		// 신맛과 쓴맛은 자연수이기 때문에 처음에 0으로 시작해도 된다. 마지막에도 값이 0이면 공집합이라는 뜻이므로 버린다. 
		// => 그렇지 않다. 쓴맛은 곱하기이므로 1부터 시작해야한다. 공집합 처리는 그냥 token을 사용하자.
		// => 쓴맛이 합이고 신맛이 곱이다... 반대로 착각하는 이슈가 있었다...
		boolean token = false;
		BigInteger S = BigInteger.valueOf(1);
		BigInteger B = BigInteger.valueOf(0);
		
		// 부분집합의 값들 계산
		for(int i=0; i<N; i++) {
			if(subset[i]==false)	// 코드 가독성을 위해서 인덴트 뎁스를 낮추는 차원에서 false일때 버린다. true일때 계산하기 위해서
				continue;
			token = true; // 반복문이 끝날때까지 token이 false면 공집함임을 의미함.
			// subset[i]==true일때 로직
			BigInteger Si = BigInteger.valueOf(origin[i][0]);
			BigInteger Bi = BigInteger.valueOf(origin[i][1]);
			S = S.multiply(Si); 		// 계산 값을 S에 다시 저장 안해줘서 gap이 1만 나오는 이슈가 있었음... BigInteger 낯설다...
			B = B.add(Bi);	// 계산 값을 B에 다시 저장 안해줘서 gap이 1만 나오는 이슈가 있었음... BigInteger 낯설다...
		}
		if(token==false) // 공집합 버림 token ==true일때 버렸던 이슈가 있었음... 단순 착오
			return;
		BigInteger gap = B.subtract(S); // B는 곱하기로 커지고 S는 더하기로 커지므로 무조건 B가 더 크다. => 반례가 있을지도 모르니 속 편하게 절대값 구하자.
		gap = gap.abs(); // 절대값을 gap에 저장해주지 않는 이슈가 있었다...
//		System.out.println(S+" | "+ B+" | "+gap +" | "+mini);
		if(miniIsFirst) {
			mini = gap;
			miniIsFirst = false;
		} else { // 최소값 초기화 성공 이후
			mini = mini.min(gap);
		}
	}
	
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		N = Integer.parseInt(input.readLine());
		origin = new int[N][2];
		for(int i=0; i<N; i++) {
			tokens = new StringTokenizer(input.readLine());
			int Si = Integer.parseInt(tokens.nextToken());
			int Bi = Integer.parseInt(tokens.nextToken());
			origin[i][0] = Si;
			origin[i][1] = Bi;
		}
		subset = new boolean[N];
		// 세팅
		// 작업
		makeSubset(subset, 0, N);
		// 출력
		System.out.println(mini);
	}

}
