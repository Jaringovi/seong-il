package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 피보나치 생성 -> 동적계획법, 알맞은 2번째값 찾기 -> 이진탐색(폐기) 그냥++로 변경,  피보나치 생성중 값 초과 -> 백트래킹

public class BOJ_2502_2 {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int first = 1;
	static int second = 1;
	static int out;
	
	static int generateFibonacci(int first, int second, final int D, final int K) { // 정상 작동 확인
		for (int i = 0; i < D-2; i++) {
			int next = first+second;
			if(next>K) { // 백트래킹 second는 가망이 없어 first++
				return 1;
			}
			if(first>second) {
				second = next;
			} else {
				first = next;
			}
		}
		int last = Math.max(second, first);
		if(last>K) { // 너무 큼! second는 가망이 없어 first++
			return 1;
		} else if(last<K){ // 너무 작음! second 아직 가망이 있음 second++
			return -1;
		} else {
			return 0; // 정답
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		
		// 입력부
		tokens = new StringTokenizer(input.readLine());
		int D = Integer.parseInt(tokens.nextToken());
		int K = Integer.parseInt(tokens.nextToken());
		
//		System.out.println(generateFibonacci(2, 7, 6, 41));
//		generateFibonacci(2, 7, 6, 41);
		
		// 작업부
		while(true) {
			int result = generateFibonacci(first, second, D, K);
			if(result==0) {
				break;
			} else if(result == -1) {
				second++;
			} else if(result == 1) {
				first++;
				second = first; // second도 다시 초기화
			}
		}
		
		// 처리부
		System.out.println(first);
		System.out.println(second);
//		System.out.println(generateFibonacci(2, 26, 7, 218));
	}

}
