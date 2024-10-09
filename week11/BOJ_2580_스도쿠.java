package study.week11;

// 17,344KB + 1,016ms

import java.io.*;
import java.util.*;
public class BOJ_2580_스도쿠 {
	// 입력 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	// 설정
	static int[][] origin = new int[9][9];
	static ArrayList<int[]> blanks = new ArrayList<>();
	static boolean[] visited = new boolean[9];
	static int N;
	static boolean workDone=false;

	// 메소드
	// 백트래킹. 답하나 찾으면 백트래킹 아예 종료해야됨. 타고타고 올라가서. true로 타고 올라가서 false일때만 동작하게 해야되나.
	static void back(int pointer) { // pointer는 0부터 blanks.size()-1 까지
//		System.out.println(pointer +"is running");
//		System.out.println(N +"is running");
		if(pointer==N) { // 마지막 위치도 통과한 상태에서 여기로 온다.
			for (int row = 0; row < origin.length; row++) {
				for (int col = 0; col < origin.length; col++) {
					output.append(origin[row][col]).append(" ");
				}
				output.append("\n");
			}
			System.out.println(output);
			workDone = true;
			return;
		}
		if(workDone) // workDone==false일때만 작업을 확장하자. true가 되면 바닥에서부터 최상위로 수직으로 상승함. 이미 반복문에 잡혀있는 재귀문들이 여기서 다 막히게 되기 때문이다.
			return;
		
		int[] temp = blanks.get(pointer);
		int x = temp[0];
		int y = temp[1];
		for (int i = 0; i < 9; i++) {
			int value = i+1; // i+1 => 1~9
			if(check(x, y, (value))) {
				origin[x][y] = value; 
				back(pointer+1);
				origin[x][y] = 0;
			}
		}
	}
	
	
	static boolean check(int x, int y, int value) {
		Arrays.fill(visited, false);
		for (int row = 0; row < origin.length; row++) {
			int index = origin[row][y] -1; 	//-1해줘서 인덱스로 변환됨
			if(index==-1)	// 빈칸은 0임. 빈칸이 들어오면 인덱스에러뜸
				continue;
			visited[index] = true; 			// 방문처리
		}
		for (int col = 0; col < origin.length; col++) {
			int index = origin[x][col] -1; 	
			if(index==-1)
				continue;
			visited[index] = true; 			
		}
		int xr = x/3;
		int yr = y/3;
		for (int row = 3*xr; row < 3*(xr+1); row++) {
			for (int col = 3*yr; col < 3*(yr+1); col++) {
				int index = origin[row][col] -1; 
				if(index==-1)
					continue;
				visited[index] = true;
			}
		}
		
		if(visited[value-1])
			return false;
		else 				// 미방문한 값이면 넣어도 됨.
			return true;
			
	}

	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		for (int row = 0; row < 9; row++) {
			tokens = new StringTokenizer(input.readLine());
			for (int col = 0; col < 9; col++) {
				int value = Integer.parseInt(tokens.nextToken());
				origin[row][col] = value;
				if(value==0) {
					blanks.add(new int[] {row, col});
				}
			}
		}
		// 설정
		N = blanks.size();
		// 작업
		back(0);
		// 출력
		
		
	}

}
