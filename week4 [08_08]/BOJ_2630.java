package study.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

// 풀이 시간: 80분 | 성공까지 제출수: 1
// 이번 풀이의 특징
//// 원초적으로 구현하던 것을 API 사용해서 단순화함.
//// matrixDevide1~4 코드의 중복을 허용하는 대신 해당 메소드의 구조를 단순하게 가져가는 것을 의도함.

public class B_2630_1 {
	// 입력부
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N;
	static int[][] originMatrix;
	
	// 문제 관리 변수
	static int whiteBox;
	static int blueBox;
	
	// 문제 해결용 메소드
	// 바닥조건 체크 용도
	static boolean fullScanUnityCheck(int[][] matrix) {
		int initiate = matrix[0][0];
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				if(initiate != matrix[row][col]) {
					return false;
				}
			}
		}
		return true;
	}
	
	static int[][] matrixDevide1(int[][] matrix){ // 4분할중 좌상단
		int half = matrix.length/2;
//		하드 버전 샘플코드
//		int [][] subMatrix_hard = new int[half][half];
//		for (int row = 0; row < half; row++) {
//			for (int col = 0; col < half; col++) {
//				subMatrix_hard[row][col]=matrix[row][col];
//			}
//		}
		int [][] subMatrix = new int[half][];
		for (int row = 0; row < half; row++) {
			subMatrix[row] = Arrays.copyOfRange(matrix[row], 0, half); // (0)~(half-1) 행의 일부를 복사
		}
		return subMatrix;
	}
	
	static int[][] matrixDevide2(int[][] matrix){ // 4분할중 우상단
		int half = matrix.length/2;
		int [][] subMatrix = new int[half][];
		
		for (int row = 0; row < half; row++) {
			subMatrix[row] = Arrays.copyOfRange(matrix[row], half, matrix.length); //
		}
		return subMatrix;
	}
	
	static int[][] matrixDevide3(int[][] matrix){ // 4분할중 좌하단
		int half = matrix.length/2;
		int [][] subMatrix = new int[half][];
		
		for (int row = 0; row < half; row++) {
			subMatrix[row] = Arrays.copyOfRange(matrix[row+half], 0, half); // (half)~(half*2-1) 행의 일부를 복사 <- (0)~(half-1)(0)~(half-1) 행의 일부를 복사
		}
		return subMatrix;
	}
	
	static int[][] matrixDevide4(int[][] matrix){ // 4분할중 우하단
		int half = matrix.length/2;
		int [][] subMatrix = new int[half][];
		
		for (int row = 0; row < half; row++) {
			subMatrix[row] = Arrays.copyOfRange(matrix[row+half], half, matrix.length);
		}
		return subMatrix;
	}
	
	
	static void recursion(int[][] matrix) {
		// 재귀 바닥 조건 + 문제 변수 관리
		if (fullScanUnityCheck(matrix)) {
			if(matrix[0][0]==0) {
				whiteBox ++;
			} else {
				blueBox++;
			}
			return;
		}
		
		// 재귀
		recursion(matrixDevide1(matrix));
		recursion(matrixDevide2(matrix));
		recursion(matrixDevide3(matrix));
		recursion(matrixDevide4(matrix));
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
		recursion(originMatrix);
		
		// 출력부
		System.out.println(whiteBox);
		System.out.println(blueBox);
		
	}// 메인 닫는 괄호

}
