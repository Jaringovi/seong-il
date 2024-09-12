import java.io.*;
import java.util.*;

// 가장 인접한 두 공유기 사이의 거리를 최대로 하는
// => 거리 C가 있는데. 각 공유기 간 거리가 C이상이면 된다. 
public class Main {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	//세팅
	static int N;
	static int C;
	static int[] origin;
	static int best;
	//메소드
	static void binarySearch(int start, int end) {
		// 바닥 조건
		if(start>end)
			return;
		
		// 재귀 파트
		int mid = (start+end)/2;
		if(isOK(mid)) {
			binarySearch(start, mid-1);
		} else {
			best = mid;
			binarySearch(mid+1, end);
		}
		
	}
	
	// 사용한 공유기의 개수가 C개보다 적으면 => 더 많이 => 더 좁게
	// 사용한 공유기의 개수가 C개보다 많으면 => 더 적게 => 더 넓게
	static boolean isOK(int mid) {
		int quauntity = 1;
		int compare = origin[0];
		for (int i = 1; i < origin.length; i++) {
			if(origin[i] -compare >=mid) {
				quauntity ++;
				compare = origin[i];
			}
		}
		if(quauntity>=C) {
			return false;
		} else {
			return true;
		}
		
	}
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		origin = new int[N];
		for (int i = 0; i < N; i++) {
			origin[i] = Integer.parseInt(input.readLine());
		}
		// 세팅
		Arrays.sort(origin);
		// 작업
		binarySearch(0, origin[N-1]);
		// 출력
		System.out.println(best);
	}// 메인 종료

}
