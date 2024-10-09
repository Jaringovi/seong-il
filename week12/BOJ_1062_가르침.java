package study.week12;
// [ 13,260 KB | 592 ms ]
import java.io.*;
import java.util.*;
public class BOJ_1062_가르침 {
	
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	// 세팅
	static int N;
	static int K;
	static char[][] words;
	static char[] comboK; // 재귀에서 leaf로 태워보낼 리스트. 메모리 아끼기 위함. 안 이래도 되겠는데?
	static char[] fixPool = {'a','n','t','i','c'};
	static char[] realPool = {'b','d','e','f','g','h','j','k','l','m','o','p','q','r','s','u','v','w','x','y','z'};
	
	static int countMax = 0;
	// 메소드
	//// 조합 구하기.
	static void makeCombo(int nowDigit, int targetDigit, char[] leaf, char[] pool, int start) {
		// 바닥 조건
		if(nowDigit>=targetDigit) {
			// 추가작업
//			System.out.println(Arrays.toString(leaf));
			readWords(leaf);
			return;
		}
		
		// 재귀 파트
		for (int i = 0+start; i < pool.length; i++) {
			leaf[nowDigit] = pool[i];
			makeCombo(nowDigit+1, targetDigit, leaf, pool, i+1);
		}
	}
	
	//// 단어 읽기.
	static void readWords(char[] leaf) {
		int count = 0;
		boolean can = true;
		// 단어들에서 단어순회
		for (int i = 0; i < words.length; i++) {
			char[] word = words[i];
			can = true;	// can == true면 단어를 읽을수있다. false면 못 읽는다. 
			// 단어에서 철자순회
			for (int j = 0; j < word.length; j++) {
				char c = word[j];
				if(canRead(c, leaf)==false) {
					can = false;
					break; // 못 읽는 글자 나옴 => 이 단어 패스 
				}
			} // 철자 순회 종료
			if(can) {
				count++; // 여기에 도착했다는 뜻은 can이 false가 되지 않았다는 뜻. 그 단어를 끝까지 읽었다는 뜻.
			}
		} // 단어 순회 종료
		countMax = Math.max(countMax, count);
	}
	
	//// 읽을수 있는 글자인지 확인.
	static boolean canRead(char c, char[] leaf) {
		// 고정풀에서 읽을수 있으면 트루 리턴
		for (int i = 0; i < fixPool.length; i++) {
			if(fixPool[i]==c) {
				return true;
			}
		}
		// 조합풀에서 읽을수 있으면 트루 리턴
		for (int i = 0; i < leaf.length; i++) {
			if(leaf[i]==c) {
				return true;
			}
		}
		// 여기까지 오면 못 읽는거니까 false 리턴
		return false;
	}
	
	// 메인
	public static void main(String[] args) throws Exception {
		
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		words = new char[N][];
		for (int i = 0; i < N; i++) {
			words[i] = input.readLine().toCharArray();
		}
		// 세팅
		comboK = new char[K];
		// 작업
		if(K<5) {
			System.out.println(countMax);
		} else {
			K-=5; // 고정풀에서 K개중에서 5개를 이미 사용함.
			makeCombo(0, K, new char[K], realPool, 0);
			System.out.println(countMax);
		}
		// 출력
		
	}// 메인 종료

}
