package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

public class BOJ_14501_1 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	// 세팅
	static int N;
	static int[][] workSheet;
	static ArrayList<Integer>[] workLoader;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 입력부
		N = Integer.parseInt(input.readLine());
		
		// 풀 초기화
		workLoader = new ArrayList[N];
		for (int i = 0; i < workLoader.length; i++) {
			workLoader[i] = new ArrayList<Integer>();
		}
		// 워크시트 초기화
		workSheet = new int[N][2]; // 포인트. N+1이 맞나?
		for (int day = 0; day < N; day++) {
			tokens = new StringTokenizer(input.readLine());
			int time = Integer.parseInt(tokens.nextToken());
			int pay = Integer.parseInt(tokens.nextToken());
			workSheet[day][0] = time;
			workSheet[day][1] = pay;
			if(day+(time-1)<N) {
				workLoader[day+(time-1)].add(time-1); 	// 해당 날짜에 끝나는 일이 며칠전부터 시작하는지 기록하는 용도. 
				// 2일 걸리는 일이면 어제 시작하니까 1을 기록한다. 인덱스 차이도 어차피 1임
			}
		}
		
		// 세팅부
		int[] dp = new int[N]; // 0인덱스는 0일째까지의 최대값, N번인덱스는 N일째까지의 최대값. 두 값 사이의 차이가 중요
		
		// 작업부
//		System.out.println("day |  i |  a |  usingIndex |  dp[usingIndex] |  workSheet[usingIndex][1] |  candidate");
		for (int day = 0; day < workSheet.length; day++) {
			if(workLoader[day].size() == 0) { // 할일이 없지 않다. 앞의 DP값을 받아와서 자기 자리에 채워넣어야 뒤에 차례때 최대값이 제대로 전달됨
				if(day==0) {
					continue;
				}
				dp[day]=dp[day-1];
			} else { // 현재 날짜 기준으로 업데이트 가능한 지점마다 업데이트 값 생성하고, 업데이트 값의 최대값으로 업데이트 하는 코드
				int max = 0; // 후보군에서 최대값 구하기 위함
				
				for (int i = 0; i < workLoader[day].size(); i++) { // 현재 날짜 기준으로 업데이트 가능한 지점마다 업데이트 값 생성하고
					int a = workLoader[day].get(i);
					int usingIndex = day-a;
					int dpUsingIndex = usingIndex-1;
					if(usingIndex==0) {
						dpUsingIndex = 0;
					}
					int candidate = dp[dpUsingIndex]+workSheet[usingIndex][1];
//					System.out.println(day+" | "+ i+" | "+ a+" | "+ usingIndex+" | "+ dp[dpUsingIndex]+" | "+ workSheet[usingIndex][1]+" | "+ candidate);
					if(candidate>max) { // 최대값 
						max = candidate;
					}
				} 
				if(day!=0) { // 아 초항 따로 안 빼놔서 개귀찮다...
					dp[day] = Math.max(max, dp[day-1]); // 업데이트 값의 최대값과 이전 DP값과 비교해서 더 큰 값을 업데이트 하는 코드
				} else {
					dp[day] = max;
				}
			}
		}
		
		// 출력부
		System.out.println(dp[N-1]);
//		System.out.println(Arrays.toString(dp)); // 디버깅용 코드
//		for (int i = 0; i < workLoader.length; i++) { // 디버깅용 코드
//			System.out.println(workLoader[i]);
//		}
	} // 메인 닫기

}
