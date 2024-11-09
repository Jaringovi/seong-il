// 먼저 색종이를 두면서 오려지는 영역을 구한다
// 오려지지 않는 곳을 0, 오려지는 곳을 1로 저장한다.
// 좌하단 꼭지점 좌표를 토대로 10*10 영역을 오린다.
// 색종이 하나당 연산량 100, 색종이 최대개수 100 => 100*100=10,000 => 전처리 origin

// 오려지는 영역의 구간합을 구해둔다. => 100*100=10,000 => 전처리 prefixSum
// 전체 점 10,000개 중에서 점 2개를 선택해서 직사각형을 만들고 그 넓이를 계산한다. 
//// => 10,000개의 점중 점2개를 선택하는 경우의 수 10,000C2 => 점 2개 조합으로 고르기.
//// 문제 발생 => 구간합 구하는 방향은 한쪽 대각선 방향으로 주어지는데 점 100개중에 점 2개를 선택하는 조합을 쓰면 
//// => 구간합 대각선 방향대로 점이 선택안될수 있음 => 그러면 그거에 맞게 대각선 방향 점을 생성해줘야됨 => 번거로움
//// => 그냥 [0][0] 부터 [100][100] 까지의 방향으로 선택하자.
//// => 그냥 10,000C2로 구한 경우의 수에서 구간합의 대각선 방향으로 점이 배치 안돼있으면 버리자.
//// => 다시 생각해보니 조합 구하는 로직상 구간합을 구하기 적합한 배치로 점이 찍힘.

// 구간합의 차이를 통해서 해당 직사각형 안에서 오려진 넓이를 계산한다 => 상수 => isRectangle
// 오려진 넓이가 직사각형 넓이와 같다면 직사각형으로 오려진 것이다. => 상수 => isRectangle
// 최대값과 비교하며 갱신한다. => 상수 => isRectangle
// 

import java.io.*;
import java.util.*;
public class BOJ_2571_색종이3 {
	// 입력 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static int[][] origin = new int[100][100];
	static int[][] prefixSum = new int[100][100];
//	static int[][] pool = new int[10000][2];
	static int maxArea;
	// 메소드
	//// 10*10 정사각형으로 오리기 (샘플 그림과 다르지만 상하반전 시키면 같은 패턴이다. 그러면 넓이를 구하는데는 지장이 없다.)
	static void cutSquare(int x, int y) {
		for (int dx = 0; dx < 10; dx++) {
			for (int dy = 0; dy < 10; dy++) {
				origin[x+dx][y+dy] = 1;
			}
		}
	}
	//// 구간합 구해서 직사각형으로 오릴수 있는지 확인하는 메소드
	static void isRectangle(int start, int end) {
		int startX = start/100;
		int startY = start%100;
		int endX = end/100;
		int endY = end%100;
		int rangeLarge = prefixSum[endX][endY];
		
		if(startX-1>=0) {
			int rangeY = prefixSum[startX-1][endY];
			rangeLarge -= rangeY;
		}
		
		if(startY-1>=0) {
			int rangeX = prefixSum[endX][startY-1];
			rangeLarge -= rangeX;
		}
		if(startX-1>=0 && startY-1>=0) {
			int rangeSmall = prefixSum[startX-1][startY-1];
			rangeLarge += rangeSmall;
		}
		
		int rectangleArea = (endX-startX+1)*(endY-startY+1);
		if(rectangleArea == rangeLarge) {
			maxArea = Math.max(maxArea, rectangleArea);
		}
		
	}
	
	static void makeCombi(int[] leaf, int nowDigit, int targetDigit, int pool, int start) { // leaf의 0번 값이 작은쪽, 1번 값이 큰쪽. 대각선 방향으로 배치됨. 100으로 나눠서 몫과 나머지를 x,y 값으로 써야함.
		// 바닥 조건
		if(nowDigit>= targetDigit) {
			// 추가 작업. isRectangle
			isRectangle(leaf[0], leaf[1]);
			return;
		}
		// 재귀 파트
		for (int i = start; i < 100*100; i++) {
			leaf[nowDigit] = i;
			makeCombi(leaf, nowDigit+1, targetDigit, pool, i+1);
		}
	}
	
	// 메인
	public static void main(String[] args) throws Exception{
		// 입력
		N = Integer.parseInt(input.readLine());
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(input.readLine());
			int x = Integer.parseInt(tokens.nextToken())-1;
			int y = Integer.parseInt(tokens.nextToken())-1;
			cutSquare(x, y);
		}
		// 세팅
		//// 맨 처음 꼭지점 초기화
		prefixSum[0][0] = origin[0][0];
		//// 맨 첫 행 초기화
		for (int row = 0+1; row < 100; row++) {
			prefixSum[row][0] = origin[row][0] + prefixSum[row-1][0];
		}
		//// 맨 첫 열 초기화
		for (int col = 0+1; col < 100; col++) {
			prefixSum[0][col] = origin[0][col] + prefixSum[0][col-1];
		}
		//// 위의 것들을 기반으로 전부 누적합 계산
		for (int row = 0+1; row < 100; row++) {
			for (int col = 0+1; col < 100; col++) {
				prefixSum[row][col] = origin[row][col] + prefixSum[row-1][col] + prefixSum[row][col-1] - prefixSum[row-1][col-1]; 
			}
		}
		//// pool 생성
//		for (int row = 0; row < 100; row++) {
//			for (int col = 0; col < 100; col++) {
//				pool[row*100+col] = new int[] {row, col};
//			}
//		}
		// 작업
		makeCombi(new int[2], 0, 2, 100*100, 0);
		// 출력
		System.out.println(maxArea);
	}// 메인

}
