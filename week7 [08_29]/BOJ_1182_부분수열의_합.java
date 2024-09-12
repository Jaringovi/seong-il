import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_S2_1182_부분수열의합3 {
	
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	// 세팅
	static int N;
	static int S;
	static int[] pool;
	static int total;
	
	// 부분집합
	static void makeSubset(boolean[]leaf, int nowDigit) {
		// 바닥 조건
		if(nowDigit==N) {
			// 추가 작업
			sum(leaf);
			return;
		}
		// 재귀 파트
		leaf[nowDigit] = false;
		makeSubset(leaf, nowDigit+1);
		leaf[nowDigit] = true;
		makeSubset(leaf, nowDigit+1);
	}
	
	//
	static void sum(boolean[] leaf) {
		boolean out = false;
		for (int i = 0; i < N; i++) {
			out = leaf[i] || out; // leaf[i] 하나라도 true있으면 트루됨.
		}
		if(out==false) { // 공집합 => 탈출
			return;
		}
		
		int sum=0;
		for (int i = 0; i < N; i++) {
			if(leaf[i]) {
				sum += pool[i];
			}
		}
		if(Math.abs(sum) ==S) {
			total++;
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		// 입력부
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		S = Integer.parseInt(tokens.nextToken());
		S = Math.abs(S);
		tokens = new StringTokenizer(input.readLine());
		pool = new int[N];
		for (int i = 0; i < N; i++) {
			pool[i] = Integer.parseInt(tokens.nextToken());
		}
		// 세팅부

		// 동작부
		makeSubset(new boolean[N], 0);
		
		// 출력부
		System.out.println(total);
	}

}
