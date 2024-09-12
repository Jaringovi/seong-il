/**
	// 메소드 1개 => 주사위 숫자 업데이트
	격자칸의 숫자와 주사위 바닥면이 모두 0이 아니면 아무일도 안 일어남.
	그렇지 않다면 두 숫자가 바뀜.
	
	// 메소드 1개 // 프로그램 제어의 시작예상 => 주사위 이동
	명령대로 갈수있는지 확인 (격자 밖으로 나가는지 확인)
	갈수 있으면 주사위 위치값을(그에 맞게 이동) 
	주사위 위치값이라는 좌표를 선언 후 관리할 필요성!!!!
	갈수 없으면 그냥 건너뛰는것 같음.
	
	// 메소드 1개 => 주사위 회전
	명령대로 주사위를 회전 시켜야됨.
	아무래도 전개도를 참고해서 배열을 만들고, => 주사위 값을 관리하는 배열 변수의 필요성!!
	거기에 값들을 회전한 상태의 인덱스에 맞춰서 수정해주는것 같음. 
	
	여기까지 10분 걸림
	문제 읽는데는 대충 5~10분 걸림
	
	순서 => 주사위 이동 => 주사위 회전 => 주사위 숫자 업데이트
*/

import java.util.*;
import java.io.*;

public class BOJ_G1_14499_2 {
	// 입력루틴
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	
	// 세팅부
	static int[] diceXY = new int[2]; // 격자상의 좌표
	static int[] dice = {0,0,0,0,0,0}; // 각 인덱스에는 전개도 상의 눈금이 기록되고 관리된다.
	static int[] querys;
	static int N;
	static int M;
	static int K;
	static int temp;
	static int[][] originMatrix;
	
	static int[][] delta = {{0,1},{0,-1},{-1,0},{1,0}};
	
	/**
	 * @param XY = 주사위 위치값 => static으로 뺄수도 있을듯
	 * @param command = 명령값
	 * @note
	 *	주사위 이동
	  	명령대로 갈수있는지 확인 (격자 밖으로 나가는지 확인)
		갈수 있으면 주사위 위치값을 해당 방향으로 +1
			주사위 위치값이라는 좌표를 선언 후 관리할 필요성!!!!
		갈수 없으면 아무것도 안하고 건너뛰는것 같음.
	 */
	static void canMoveThenGo(int[]XY, int command) {
		// dx, dy = 명령대로 이동했을때 위치
		int dx = diceXY[0] + delta[command-1][0]; // int dx = delta[command-1][0];
		int dy = diceXY[1] + delta[command-1][1]; // int dy = delta[command-1][1];
		if((0<=dx && dx<N) && (0<=dy && dy<M)) { // 명령대로 이동했을때 위치가 지도 안이다
			diceXY[0] += delta[command-1][0]; // 이동했으니 좌표도 업데이트
			diceXY[1] += delta[command-1][1];
			rotateUpdate(command); // 이동할때 회전했으니 업데이트
			switching();
			output.append(dice[6-1]).append("\n");
			
		} else { // 지도 밖으로 나감. 아무 일 X
		}
		
		
	}
	// 주사위 회전 반영 및 관리
	
	static void rotateUpdate(int command) {
		if(command == 1) { // 오른쪽으로 회전
			temp = dice[6-1]; // 6번눈은 5번 인덱스인데 헷갈려서 6-1로 표기
			dice[6-1] = dice[4-1];
			dice[4-1] = dice[1-1];
			dice[1-1] = dice[3-1];
//			dice[3-1] = dice[6-1];  // X
			dice[3-1] = temp; 		// O
		} else if(command == 2) { // 왼쪽으로 회전
			temp = dice[3-1];
			dice[3-1] = dice[1-1];
			dice[1-1] = dice[4-1];
			dice[4-1] = dice[6-1];
			dice[6-1] = temp;
		} else if(command == 3) { // 위로 회전
			temp = dice[6-1];
			dice[6-1] = dice[5-1];
			dice[5-1] = dice[1-1];
			dice[1-1] = dice[2-1];
			dice[2-1] = temp;
		} else if(command == 4) { // 아래로 회전
			temp = dice[2-1];
			dice[2-1] = dice[1-1];
			dice[1-1] = dice[5-1];
			dice[5-1] = dice[6-1];
			dice[6-1] = temp;
		}
	}
	
	// 주사위 바닥값과 격자값 스위치
	static void switching() {
		int under = dice[1-1]; // 바닥면
		int x = diceXY[0];
		int y = diceXY[1];
		if(dice[1-1]!=0 && originMatrix[x][y] !=0) { // 둘 다 0이 아니면 아무일도 일어나지 않음
		} else { // 둘중 하나는 0임 => 스위칭 | 둘다 0임 => 스위칭해도 상관 X
			temp = under;
			dice[1-1] = originMatrix[x][y];
			originMatrix[x][y] = temp;
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		// 입력부
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		diceXY[0] = Integer.parseInt(tokens.nextToken());
		diceXY[1] = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken()); // 쿼리의 개수
		//// 지도 배열
		originMatrix = new int[N][M];
		for (int row = 0; row < originMatrix.length; row++) { // row<N
			tokens = new StringTokenizer(input.readLine());
			for (int col = 0; col < originMatrix[row].length; col++) { // col<M
				originMatrix[row][col] = Integer.parseInt(tokens.nextToken());
			}
		}
		//// 쿼리 배열
		tokens = new StringTokenizer(input.readLine());
		querys = new int[K];
		for(int i=0; i<K; i++) {
			querys[i] = Integer.parseInt(tokens.nextToken());
		}
		
		
		// 세팅부
		
		// 작업부
		for (int i = 0; i < K; i++) {
			int command = querys[i];
			canMoveThenGo(diceXY, command);
			System.out.println(Arrays.toString(dice));
//			System.out.println(Arrays.toString(diceXY));
			for (int j = 0; j < N; j++) {
				System.out.println(Arrays.toString(originMatrix[j]));
			}
			System.out.println("------------------------");
			
		}
		
		// 출력부
//		System.out.println(Arrays.toString(querys));
		System.out.println(output);
	}

}
