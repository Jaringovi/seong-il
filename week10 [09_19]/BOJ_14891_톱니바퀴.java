// 문제 풀이 블로깅
// https://velog.io/@bed_is_good/%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%EB%B0%B1%EC%A4%80-14891-%ED%86%B1%EB%8B%88%EB%B0%94%ED%80%B4-JAVA

import java.io.*;
import java.util.*;
public class BOJ_14891_톱니바퀴6 {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	
	// 세팅
	static int[][] gears;
	static int[][] cmd; // 대상 톱니 / 회전 방향
	static int sum;
	static int K;
	
	// 메소드

	//// 회전 판정
	static void canRotateThenDo(int selectedGear, int direction) {
		int[] temp = new int[4];			// 각 기어의 회전방향은 0으로 초기화돼있음. 회전이 전파 안된 기어는 값이 0이라 회전안함.
		temp[selectedGear] = direction;
		
		// 오른쪽으로 확장(전파)
		int point = selectedGear; 			// 시작 기어의 위치 => 오른쪽으로 넘어갈때마다 +1돼야함.
		int nowDirection = direction;		// 시작 기어의 회전방향 => 오른쪽으로 넘어갈때마다 *-1돼야함.
		while(true) {
			if(point==4-1) 					// 4번 기어는 비교할 오른쪽 기어가 없기 때문에 반복문 탈출
				break;
			if(gears[point][3-1]!=gears[point+1][7-1]) { // 서로 다른 극이므로 오른쪽으로 넘어갈수 있음. 
				point +=1;
				nowDirection = nowDirection*-1;
				temp[point] = nowDirection;	// 회전이 전파된 기어자리에 회전방향 저장해두기.
			} else { 						// 회전이 전달안되므로 탈출
				break;
			}
		}
		
		// 왼쪽으로 확장(전파)
		point = selectedGear; 				// 시작 기어의 위치 => 왼쪽으로 넘어갈때마다 -1돼야함.
		nowDirection = direction;			// 시작 기어의 회전방향 => 왼쪽으로 넘어갈때마다 *-1돼야함.
		while(true) {
			if(point==1-1) 					// 1번 기어는 비교할 왼쪽 기어가 없기 때문에 반복문 탈출
				break;
			if(gears[point-1][3-1]!=gears[point][7-1]) { // 서로 다른 극이므로 왼쪽으로 넘어갈수 있음. 
				point -=1;
				nowDirection = nowDirection*-1;
				temp[point] = nowDirection;	// 회전이 전파된 기어자리에 회전방향 저장해두기.
			} else { 						// 회전이 전달안되므로 탈출
				break;
			}
		}
		
		// 전부 회전시키기.
		for (int i = 0; i < temp.length; i++) {
			rotate(i, temp[i]);
		}
	}
	
	//// 회전
	static void rotate(int gear, int direction) {
		int[] tempOrigin = gears[gear].clone();
		for (int i = 0; i < tempOrigin.length; i++) {
			gears[gear][(i+direction+8)%8] = tempOrigin[i]; 	// 8은 더해도 %8로 사라지는 부분이기 때문에 +1 로직에서도 정상적으로 작동한다. -1로직을 위해 +8을 하는 것이다.
		}
	}
	
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		
		//// 기어 입력
		gears = new int[4][8];
		for (int i = 0; i < 4; i++) {
			char[] cList = input.readLine().toCharArray();
			for (int j = 0; j < cList.length; j++) {
				gears[i][j] = Character.getNumericValue(cList[j]);
			}
		}
		//// 커맨드 입력
		K = Integer.parseInt(input.readLine());
		cmd = new int[K][2];
		for (int k = 0; k < K; k++) {
			tokens = new StringTokenizer(input.readLine());
			cmd[k][0] = Integer.parseInt(tokens.nextToken()) -1;	// 기어번호를 인덱스번호와 맞춤.
			cmd[k][1] = Integer.parseInt(tokens.nextToken())   ;
		}
		// 세팅
		// 작업
		for (int i = 0; i < cmd.length; i++) {
			int selectedGear = cmd[i][0];
			int direction = cmd[i][1];
			canRotateThenDo(selectedGear, direction);
		}
		
		sum = 0;
		for (int i = 0; i < gears.length; i++) {
			if (gears[i][0] == 1){ // i번 기어의 12시 방향이 S극(1)이면 2의 i승을 더해준다.
				sum += (int) Math.pow(2,i);
			}
		}
		// 출력
		System.out.println(sum);
	}

}
