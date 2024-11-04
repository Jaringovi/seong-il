package study.week16;
import java.io.*;
import java.util.*;
public class BOJ_6236_용돈관리 {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static int M;
	static int[] origin;
	static int total;
	static int bestK=Integer.MAX_VALUE;
	static int hardDay;
	// 메소드
	static void bs(int s, int e) {
		// 바닥 조건
		if(s>e) {
			// 추가작업?
			return;
		}
		// 재귀 파트
		int mid = (s+e)/2;
		if(check(mid)) {
			bs(s,mid-1);
		} else {
			bs(mid+1,e);
		}
	}
	static boolean check(int mid) { // mid=k
		int m = 0; 
		m++;// 최초 한번은 출금해야죠.
		int balance = mid;
		for (int i = 0; i < origin.length; i++) {
			if(m>M) { // 이미 실패했으니까 그만 돌자.
				break;
			}
			int Ni = origin[i];
			if(balance-Ni>=0) {
				balance -= Ni;
			} else {
				m++; // 돈 모자르네 출금해
				balance = mid; // 남은돈 입금후 K원만큼 출금했으니, 잔고는 K원
				balance -= Ni; // i번째 필요한 돈은 그대로 사용.
			}
		}
		if(m>M) {
			return false;
		} else {
			bestK = Math.min(bestK, mid);
			return true;
		}
	}
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		origin = new int[N];
		// 세팅
		for (int i = 0; i < N; i++) {
			int Ni = Integer.parseInt(input.readLine());
			origin[i] = Ni;
			total+=Ni;
			hardDay = Math.max(hardDay, Ni);
		}
		// 작업
		bs(hardDay,total);
		// 출력
		System.out.println(bestK);
	}// 메인

}
