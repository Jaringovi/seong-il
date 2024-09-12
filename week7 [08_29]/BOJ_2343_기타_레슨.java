package study.week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_S2_2343_기타레슨2 {
	
	// 입력 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	// 세팅
	static int N;
	static int M;
	static int[] lectures;
	
	// 추가 세팅
	static int minK;
	static boolean isFirst = true;
	
	// 용량 적당한가?
	static boolean sizeEnough(int k) {
		int quatity = M-1; // 아 처음에 하나 꺼내서 담고 시작하는구나
		int sum = 0;
		for (int i = 0; i < lectures.length; i++) {
			if(sum+lectures[i]<=k) {
				sum += lectures[i];
			} else {
				quatity --;
				sum =0;
				sum += lectures[i];
			}
		}
		if(quatity>=0) {
			return true;
		} else {
			return false;
		}
	}
	
	// 재귀적 이진탐색
	static void binarySearch(int start, int end) {
		// 바닥 조건
//		if(Math.abs(start-end)==1) {
		if(start == end) {
			// 작업 추가
			boolean result = sizeEnough(end);
			if(result) {
				minK = end;
			}	// 마지막에 한번 더 확인
			
			result = sizeEnough(start);
			if(result) {
				minK = start;
			}	// 마지막에 한번 더 확인
			return;
		}
		
		// 재귀파트
		int mid = (start+end)/2;
		boolean result = sizeEnough(mid);
		if(result) {
			if(isFirst) {
				minK = mid;
				isFirst = false;
			}
			minK = Math.min(minK, mid);
			binarySearch(start, mid-1);
		} else {
			binarySearch(mid+1, end);
		}
	}
	
	public static void main(String[] args) throws IOException {
		// 입력부
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		lectures = new int[N];
		tokens = new StringTokenizer(input.readLine());
		for (int i = 0; i < N; i++) {
			lectures[i] = Integer.parseInt(tokens.nextToken());
		}

		// 세팅부
		int sum =0;
		for (int i = 0; i < lectures.length; i++) {
			sum+=lectures[i];
		}
		// 작업부
		binarySearch(1, (int) Math.pow(10, 9));		
		// 출력부
		System.out.println(minK);
		
	} // 메인 종료

}
