package study.week21;

import java.io.*;
import java.util.*;
public class BOJ_19941_햄버거분배 {
	// 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static int K;
	static char[] table;
	static boolean[] visit;
	static int total;
	// 메인
	public static void main(String[] args) throws Exception{
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		table = input.readLine().toCharArray();
		// 세팅
		visit = new boolean[N];
		// 작업
		//// 테이블 순회
		searchH();
		// 출력
		System.out.println(total);
	}
	private static void searchH() {
		for (int i = 0; i < N; i++) {
			//// 햄버거  발견
			if(table[i]=='H') {
				//// 먹을사람 탐색
				searchP(i);
			}
		}
	}
	//  메소드
	private static void searchP(int center) {
		// K만큼 좌우탐색
		for (int i = center-K; i <= center+K; i++) {
			// 인덱스가 테이블범위 밖으로 나갔으면 중단. 
			//=> 디버깅 포인트. 중단이 아니고 건너 뛰어야함. 
			// 맨앞에 넘어가는 부분이 있더라도 그 뒤로는 정상 범주인 값들이 있기때문이다. 
			// 처음에 범위 밖으로 나갔다고 해서 뒤까지 버리면 정상범주도 버리게 됨.
			if(i<0 || N<=i) {
				continue;
			}
			// 사람 탐색 + 햄버거 안 먹은 사람
			if(table[i]=='P' && visit[i]==false) {
//				System.out.println(center+":"+i);
				visit[i] = true;
				total++;
				break;
			}
		}
	}
}
