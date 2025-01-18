package study.week23;
import java.io.*;
import java.util.*;

public class BOJ_1477_휴게소세우기 {
	// 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static int M;
	static int L;
	static int[] origin;
	static int[] terms;
	static int best;
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		L = Integer.parseInt(tokens.nextToken());
		
		origin = new int[N+2]; // 구간 형성을 쉽게 하기 위해서 시작점과, 종결점도 배열에 추가하기 위해서 +2
		tokens = new StringTokenizer(input.readLine());
		for (int i = 0+2; i < N+2; i++) {
			origin[i] = Integer.parseInt(tokens.nextToken());
		}
		origin[0] = 0;
		origin[1] = L;
		// 세팅
		Arrays.sort(origin);
//		System.out.println(Arrays.toString(origin));
		terms = new int[N+1];
		for (int i = 0; i < N+1; i++) {
			terms[i] = origin[i+1] - origin[i];
		}
//		System.out.println(Arrays.toString(terms));
		// 작업
		binarySearch(1,1000); // binarySearch(0,1000) 범위를 이렇게 설정하면, zero divide 이슈 발생함.
		// 출력
		System.out.println(best);
	}
	// 메소드
	private static void binarySearch(int start, int end) {
		// 바닥 조건
		if(start>end) {
			// 필요시 추가작업
			return;
		}
		// 재귀 파트
		int mid = start + (end-start)/2;
		if(isOK(mid)) {	// mid 길이의 구간으로 표준화 성공. 더 작은 길이로 도전해야함.
			best = mid;
			binarySearch(start, mid-1);
		} else {		// mid 길이의 구간으로 표준화 실패. 더 긴 길이로 도전해야함.
			binarySearch(mid+1, end);
		}
	}
	private static boolean isOK(int mid) {
		int needSum = 0;
		for (int i = 0; i < terms.length; i++) {
			int term = terms[i];
			int need = term/mid;
			if(term%mid==0) { // 딱 나눠 떨어진다면, 구간을 나누는 파티션은 몫 보다 1개 덜 필요하다.
				need--;
			}
			needSum += need;
		}
		if(needSum<=M) {
			return true;
		} else {
			return false;
		}
	}
	
}
