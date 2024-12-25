import java.io.*;
import java.util.*;
// 순열 만들어서 값이 맞는지 계산
// 맞으면 출력
public class Main {
	// 입력 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	// 세팅
	static int N;
	static int[] alpabets;
	static boolean searchStop;
	// 메인
	public static void BOJ_13140_HelloWorld (String[] args) throws Exception {
		// 입력
		N = Integer.parseInt(input.readLine());
		// 세팅
		alpabets = new int[7];
		alpabets[0] = (int) Math.pow(10,4); // h
		alpabets[1] = (int) Math.pow(10,3); // e
		alpabets[2] = (int) Math.pow(10,2) + (int) Math.pow(10,1)*2; // l
		alpabets[3] = (int) Math.pow(10,3) + (int) Math.pow(10,0); 	 // o
		alpabets[4] = (int) Math.pow(10,4); // w
		alpabets[5] = (int) Math.pow(10,2); // r
		alpabets[6] = (int) Math.pow(10,0); // d
		// 작업
		makePermutation(new int[7], 0, 7, 10, new boolean[10]);
		// 출력
		if(searchStop==false) {
			System.out.println("No Answer");
		} else {
		}
	}
	// 메소드
	static void makePermutation(int[] leaf, int nowDigit, int targetDigit, int pool, boolean[] visit) {
		// 답 찾으면 나머지 모두 자동종료 시키기.
		if(searchStop == true) {
			return;
		}
		
		// 바닥조건
		if(nowDigit>=targetDigit) {
			// 채점 타이밍
			int sum = 0;
			for (int i = 0; i < leaf.length; i++) {
				sum += leaf[i]*alpabets[i];
			}
			// 채점 성공
			if(sum==N) {
				// h 혹은 w가 0이면 그냥 버리기.
				if(leaf[0]==0 || leaf[4]==0) {
					return;
				}
				int over = 0;
				over += (int) Math.pow(10, 4)*leaf[0]; // h
				over += (int) Math.pow(10, 3)*leaf[1]; // e
				over += (int) Math.pow(10, 2)*leaf[2]; // l
				over += (int) Math.pow(10, 1)*leaf[2]; // l
				over += (int) Math.pow(10, 0)*leaf[3]; // o
				
				int under = 0;
				under += (int) Math.pow(10, 4)*leaf[4]; // w
				under += (int) Math.pow(10, 3)*leaf[3]; // o
				under += (int) Math.pow(10, 2)*leaf[5]; // r
				under += (int) Math.pow(10, 1)*leaf[2]; // l
				under += (int) Math.pow(10, 0)*leaf[6]; // d
				
//				System.out.println(over+ " : " + under);
				// hello를 스트링으로 만들고 마진 추가하기.
				String overString = "  "+over;
				// world를 스트링으로 만들고 마진과 덧셈기호 추가하기.
				String underString = "+ "+under;
				// 가로선 
				String hl = "-------";
				// 계산결과 구하기
				int result = over+under;
				String resultString = result+"";
				int resultLeftMargin = 7-resultString.length();
				for (int i = 0; i < resultLeftMargin; i++) {
					resultString=" "+resultString;
				}
				// output에 붙이고 출력하기.
				output.append(overString).append("\n");
				output.append(underString).append("\n");
				output.append(hl).append("\n");
				output.append(resultString).append("\n");
				System.out.println(output);
				// 나머지 작업 중단하기
				searchStop = true;
			}
			return;
		}
		// 재귀부분
		for (int i = 0; i < pool; i++) {
			if(visit[i] == false) {
				visit[i] = true;
				leaf[nowDigit] = i;
				makePermutation(leaf, nowDigit+1, targetDigit, pool, visit);
				visit[i] = false;
			}
		}
	}
}
