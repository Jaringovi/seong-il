package study.week14;

import java.io.*;
import java.util.*;
public class BOJ_16935_배열돌리기3_Advanced {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	// 세팅
	static int N;
	static int M;		// 원본 한셀에 대해 작업이 끝날때마다 초기화하는 용도
	static int workN; 	// 작업용. 3,4 명령마다 스왑하는 용도 
	static int workM;
	static int R;
	static int[][] origin;
	static int[][] answer;
	static int[] cmds;
	static int row;
	static int col;
	
	// 메소드
	static void reset() { // 작업하면서 스왑하던 작업용 workN,workM초기화
		workN=N;
		workM=M;
	}
	static void swap() { // 3,4 번 다음에 1,2,5,6 이 오면 N,M이 바뀐다. 그냥 한바닥 돌면서 하자.
		int tempN = workN;
		workN=workM;
		workM=tempN;
	}
	
	static void cmd1() {
		row = (workN-1)-row;
	}
	
	static void cmd2() {
		col = (workM-1)-col;
	}
	
	static void cmd3() {
		int temp = col;
		col = (workN-1)-row;
		row = temp;
		swap(); // 회전 작업 다 하고 스왑
	}
	
	static void cmd4() {
		cmd3();
		cmd3();
		cmd3();
	}
	
	static void cmd5() { // 디버깅 포인트1. 영역 4분할 위치 잘못적음. // 디버깅 포인트2. else if 조건문에 workN과 workM을 바꿔적음.
		
		// 1번
		if(row<workN/2 && col<workM/2) {
			col = col+workM/2;
		}
		// 2번
		else if(row<workN/2 && col>=workM/2) {
			row = row+workN/2;
		}
		// 3번
		else if(row>=workN/2 && col>=workM/2) {
			col = col-workM/2;
		}
		// 4번
		else{//if(row>=workM/2 && col<workN/2) {
			row = row-workN/2;
		}
	}
	
	static void cmd6() {
		cmd5();
		cmd5();
		cmd5();
	}
	
	
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		R = Integer.parseInt(tokens.nextToken());
		origin = new int[N][M];
		for (int row = 0; row < N; row++) {
			tokens = new StringTokenizer(input.readLine());
			for (int col = 0; col < M; col++) {
				origin[row][col] = Integer.parseInt(tokens.nextToken());
			}
		}
		cmds = new int[R];
		tokens = new StringTokenizer(input.readLine());
		for (int i = 0; i < R; i++) {
			cmds[i] = Integer.parseInt(tokens.nextToken());
		}
		// 세팅
		int count=0;
		for (int i = 0; i < cmds.length; i++) {
			if(cmds[i]==3 || cmds[i]==4) { // 디버깅 포인트3. || 로 묶어야되는데 실수로 &&로 묶음
				count++;
			}
		}
		//// 정답배열의 행과 열 사이즈 결정하기.
		if(count%2==0) {
			answer = new int[N][M];
		} else { // 홀수번 90도 회전하면 행열 길이가 서로 바뀜
			answer = new int[M][N];
		}
		// 작업
		for (int rowL = 0; rowL < N; rowL++) {
			for (int colL = 0; colL < M; colL++) {
				reset(); // 셀마다 리셋
				int value = origin[rowL][colL];
				row = rowL;
				col = colL;
				
				//// 셀마다. 한 셀이 모든 명령이 끝났을때 위치 구해서. 해당 위치에 값 저장.
				for (int i = 0; i < cmds.length; i++) {
					int cmd = cmds[i];
					if(cmd==1)
						cmd1();
					if(cmd==2)
						cmd2();
					if(cmd==3)
						cmd3();
					if(cmd==4)
						cmd4();
					if(cmd==5)
						cmd5();
					if(cmd==6)
						cmd6();
				}
				answer[row][col] = value; // 정답의 좌표를 구했으니 값을 저장한다.
			}
		}
		
		// 출력값 만들기
		for (int row = 0; row < answer.length; row++) {
			for (int col = 0; col < answer[0].length; col++) {
				output.append(answer[row][col]).append(" ");
			}
			output.append("\n");
		}
		// 출력
		System.out.println(output);
	}// 메인
}
