package study.week14;

import java.io.*;
import java.util.*;
public class BOJ_16935_배열돌리기4 {
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
	
	static int[] cmd1(int[] xy) {
		int row = xy[0];
		int col = xy[1];
		
		int nrow = (workN-1)-row;
		int ncol = col;
		int[] next = {nrow,ncol};
		return next;
	}
	
	static int[] cmd2(int[] xy) {
		int row = xy[0];
		int col = xy[1];
		
		int nrow = row;
		int ncol = (workM-1)-col;
		int[] next = {nrow,ncol};
		return next;
	}
	
	static int[] cmd3(int[] xy) {
		int row = xy[0];
		int col = xy[1];
		
		int nrow = col;
		int ncol = (workN-1)-row;
		int[] next = {nrow,ncol};
		swap(); // 회전 작업 다 하고 스왑
		return next;
	}
	
	static int[] cmd4(int[] xy) {
		xy = cmd3(xy);
		xy = cmd3(xy);
		xy = cmd3(xy);
		return xy;
	}
	
	static int[] cmd5(int[] xy) { // 디버깅 포인트1. 영역 4분할 위치 잘못적음. // 디버깅 포인트2. else if 조건문에 workN과 workM을 바꿔적음.
		int row = xy[0];
		int col = xy[1];
		
		// 1번
		if(row<workN/2 && col<workM/2) {
			int nrow = row;
			int ncol = col+workM/2;
			int[] next = {nrow,ncol};
			return next;
		}
		// 2번
		else if(row<workN/2 && col>=workM/2) {
			int nrow = row+workN/2;
			int ncol = col;
			int[] next = {nrow,ncol};
			return next;
		}
		// 3번
		else if(row>=workN/2 && col>=workM/2) {
			int nrow = row;
			int ncol = col-workM/2;
			int[] next = {nrow,ncol};
			return next;
		}
		// 4번
		else{//if(row>=workM/2 && col<workN/2) {
			int nrow = row-workN/2;
			int ncol = col;
			int[] next = {nrow,ncol};
			return next;
		}
	}
	
	static int[] cmd6(int[] xy) {
		xy = cmd5(xy);
		xy = cmd5(xy);
		xy = cmd5(xy);
		return xy;
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
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < M; col++) {
				reset(); // 셀마다 리셋
				int value = origin[row][col];
				int[] xy = {row,col};
				//// 셀마다. 한 셀이 모든 명령이 끝났을때 위치 구해서. 해당 위치에 값 저장.
				for (int i = 0; i < cmds.length; i++) {
					int cmd = cmds[i];
					if(cmd==1)
						xy=cmd1(xy);
					if(cmd==2)
						xy=cmd2(xy);
					if(cmd==3)
						xy=cmd3(xy);
					if(cmd==4)
						xy=cmd4(xy);
					if(cmd==5)
						xy=cmd5(xy);
					if(cmd==6)
						xy=cmd6(xy);
				}
				answer[xy[0]][xy[1]] = value; // 정답의 좌표를 구했으니 값을 저장한다.
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
