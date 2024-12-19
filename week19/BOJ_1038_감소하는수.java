package study.week19;
import java.io.*;
import java.util.*;
public class BOJ_1038_감소하는수 {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static List<Long> decreasingNumbers = new ArrayList<>();
	static int[] nums = {9,8,7,6,5,4,3,2,1,0};
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		N = Integer.parseInt(input.readLine());
		// 세팅
		// 작업
		for (int i = 0+1; i < 10+1; i++) {
			generateCombination(new int[i], 0, i, nums, 0);
		}
		Collections.sort(decreasingNumbers);
		// 출력
		if(N+1<=decreasingNumbers.size()) {
			System.out.println(decreasingNumbers.get(N));
		} else {
			System.out.println(-1);
		}

	}
	// 메소드
	static void  generateCombination(int[] leaf, int nowDigit, int digit, int[] pool, int start) {
		// 바닥 조건
		if(nowDigit>=digit) {
			long sum = 0;
			for (int i = 0; i < leaf.length; i++) {
				sum += leaf[i]*(long)Math.pow(10, digit-1-i);
			}
			decreasingNumbers.add(sum);
			return;
		}
		// 재귀 파트
		for (int i = start; i < pool.length; i++) {
			leaf[nowDigit] = pool[i]; 	
			generateCombination(leaf, nowDigit+1, digit, pool, i+1); 
		}
		
	}
}
