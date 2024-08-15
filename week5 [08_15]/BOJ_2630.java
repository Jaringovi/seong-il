package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
// 풀이 시간 : 60분 
// 성공까지 제출수 : 4회
// 활용 코드 : [2630] 색종이 만들기 (2 - Try) |
// 이번 풀이의 특징
//// 문제 해석하기 힘들다..
//// 토너먼트 느낌으로 바닥부터 맨위까지 계속 올라가야함!..ㅋㅋ..
//// 재귀문을 재귀 부분과 리턴 부분으로 구분해봄
//// subMatrix를 생성하는 코드라서 마지막 테스트 케이스에서 시간초과 나오네 ㅋㅋ.. 그래서 인덱스로 분할하는 버전 사용
//// 그래도 시간초과가 뜨네... 그냥...! arrayList에서 array로 바꿔봄...
//// 막혔던 시간초과 통과했는데 이제는 스택오버플로우가 뜨네
//// 그냥 한번에 쫙 쪼개지 말고 한사분면씩 들어가는게 맞겠다 이건 DFS인데 재귀보다

public class BOJ_2630_1 {

	// 입력부
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N;
	static int[][] originMatrix;
	static int[] temp4 = new int[4];
	
	// 문제 해결용 메소드
	static int[] matrixDevidByIndex1(int[]points4) { // int x0, int x1, int y0, int y1 // [x0,y0] [x1,y0] [x0,y1] 점 3개면 사각형 특정 가능. x0~x1과 y0~y1로 컨트롤 가능
		int x0 = points4[0]; 
		int x1 = points4[1]; 
		int y0 = points4[2];
		int y1 = points4[3];
		int xHalf = (x1 + x0)/2; // 4*4 기준으로 테스트. 끝에 +1하고 빼고 /2 하고 -1해야 왼쪽의 끝 인덱스가 나옴. 오른쪽의 시작 인덱스는 +1해서 쓰면 될듯
		int yHalf = (y1 + y0)/2;
		int [] nextBox= {x0, xHalf,y0, yHalf};
		return nextBox;
	}
	static int[] matrixDevidByIndex2(int[]points4) { // int x0, int x1, int y0, int y1 // [x0,y0] [x1,y0] [x0,y1] 점 3개면 사각형 특정 가능. x0~x1과 y0~y1로 컨트롤 가능
		int x0 = points4[0]; 
		int x1 = points4[1]; 
		int y0 = points4[2];
		int y1 = points4[3];
		int xHalf = (x1 + x0)/2; // 4*4 기준으로 테스트. 끝에 +1하고 빼고 /2 하고 -1해야 왼쪽의 끝 인덱스가 나옴. 오른쪽의 시작 인덱스는 +1해서 쓰면 될듯
		int yHalf = (y1 + y0)/2;
		int [] nextBox= {xHalf+1, x1,y0, yHalf};
		return nextBox;
	}
	static int[] matrixDevidByIndex3(int[]points4) { // int x0, int x1, int y0, int y1 // [x0,y0] [x1,y0] [x0,y1] 점 3개면 사각형 특정 가능. x0~x1과 y0~y1로 컨트롤 가능
		int x0 = points4[0]; 
		int x1 = points4[1]; 
		int y0 = points4[2];
		int y1 = points4[3];
		int xHalf = (x1 + x0)/2; // 4*4 기준으로 테스트. 끝에 +1하고 빼고 /2 하고 -1해야 왼쪽의 끝 인덱스가 나옴. 오른쪽의 시작 인덱스는 +1해서 쓰면 될듯
		int yHalf = (y1 + y0)/2;
		int [] nextBox= {x0, xHalf, yHalf+1, y1};
		return nextBox;
	}
	static int[] matrixDevidByIndex4(int[]points4) { // int x0, int x1, int y0, int y1 // [x0,y0] [x1,y0] [x0,y1] 점 3개면 사각형 특정 가능. x0~x1과 y0~y1로 컨트롤 가능
		int x0 = points4[0]; 
		int x1 = points4[1]; 
		int y0 = points4[2];
		int y1 = points4[3];
		int xHalf = (x1 + x0)/2; // 4*4 기준으로 테스트. 끝에 +1하고 빼고 /2 하고 -1해야 왼쪽의 끝 인덱스가 나옴. 오른쪽의 시작 인덱스는 +1해서 쓰면 될듯
		int yHalf = (y1 + y0)/2;
		int [] nextBox= {xHalf+1, x1, yHalf+1, y1};
		return nextBox;
	}
	
	
	static int recursion(int[] points4) {
		// 재귀 바닥 조건 + 문제 변수 관리
		int x0 = points4[0]; 
		int x1 = points4[1]; 
		int y0 = points4[2];
		int y1 = points4[3];
		if(x1-x0==1) {
			int count = 0;
			for (int row = x0; row < x1+1; row++) {
				for (int col = y0; col < y1+1; col++) {
					temp4[count++] = originMatrix[row][col];
				}
			}
			Arrays.sort(temp4);
			return temp4[1];
		}
		
		
		// 재귀
		int winner1 = recursion(matrixDevidByIndex1(points4));
		int winner2 = recursion(matrixDevidByIndex2(points4));
		int winner3 = recursion(matrixDevidByIndex3(points4));
		int winner4 = recursion(matrixDevidByIndex4(points4));
		temp4[0] = winner1;
		temp4[1] = winner2;
		temp4[2] = winner3;
		temp4[3] = winner4;

		
		// 리턴 처리
		Arrays.sort(temp4);
		return temp4[1];
		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 입력부
		N = Integer.parseInt(input.readLine());
		
		//// 초기 매트릭스 생성
		originMatrix = new int[N][N];
		for (int row = 0; row < N; row++) {
			tokens = new StringTokenizer(input.readLine());
			for (int col = 0; col < N; col++) {
				originMatrix[row][col] = Integer.parseInt(tokens.nextToken());
			}
		}
//		입력과 초기화 테스트용 출력코드
//		for (int i = 0; i < originMatrix.length; i++) {
//			System.out.println(Arrays.toString(originMatrix[i]));
//		}
		
		// 문제 해결부
		int[] arr = {0,N-1,0,N-1};
		int answer= recursion(arr);
		
		// 출력부
		System.out.println(answer);

	}// 메인 닫는 괄호

}
