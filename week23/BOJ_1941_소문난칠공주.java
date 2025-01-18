package study.week23;
import java.io.*;
import java.util.*;
// S파가 4명이상 필수
// 25C7 조합 생성
// 조합에서 BFS로 연결확인
public class BOJ_1941_소문난칠공주 {
	// 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	// 세팅
	static int[][] origin = new int[5][5];
	static boolean[] visit = new boolean[25];
	static int[] deltaOther = {-1,1,-5,5};
	static int[] delta0 = {1,-5,5};
	static int[] delta4 = {-1,-5,5};
	static int successCount;
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		for (int row = 0; row < 5; row++) {
			char[] line = input.readLine().toCharArray();
			for (int col = 0; col < line.length; col++) {
				if (line[col] == 'S') { // 
					origin[row][col] = 1;
				} else {
					origin[row][col] = 0;
				}
			}
		}
		// 세팅
		// 작업
		generateCombination(new int[7], 0, 7, 25, 0);
//		int[] test = {5,6,7,8,9,14,19};
//		validation(test);
		// 출력
		System.out.println(successCount);
	}
	// 메소드
	static void  generateCombination(int[] leaf, int nowDigit, int digit, int pool, int start) {
		// 바닥 조건
		if(nowDigit>=digit) {
			// 추가작업
			validation(leaf);
			return;
		}
		// 재귀 파트
		for (int i = start; i < pool; i++) {
			leaf[nowDigit] = i; 	
			generateCombination(leaf, nowDigit+1, digit, pool, i+1); 
		}
		
	}
	private static void validation(int[] leaf) {
		// S파가 다수인지 확인. false면 다수파가 아니니까 종료.
		if (sIsMajority(leaf)==false) {
//			System.out.println("sIsMajority==false");
			return;
		}
		// 세팅
		int connectedCount = 0;
		Arrays.fill(visit,false);
		
		// 큐 초기화
		Queue<Integer> q = new LinkedList<Integer>();
		q.offer(leaf[0]);
		connectedCount++;
		visit[leaf[0]] = true;
		// 큐에 뭐가 들어있는 동안 작업
		while(q.isEmpty()==false) {
			int nowCell = q.poll();
//			System.out.println(nowCell +" : "+connectedCount);
			// 1차원으로 축소한 좌표의 상하좌우 좌표를 구하고, 조합 풀에 있는지 확인
			// 확인 결과 있으면 큐에 추가하면서 연결 풀에 추가
			// 큐에 추가하면서 연결된 칸의 개수 카운팅하기.
			// 무한루프를막기 위해 큐에 들어간 값은 방문처리하기.
			int[] nexts = generateNext(nowCell);
			for (int nextCell: nexts) {
				// visit 배열에서 인덱스 에러 나는것을 방지하기
				if(nextCell<0 || 25<=nextCell) {
					continue;
				}
				// 다음 칸 후보가 이미 방문됐다면 이 후보에 대한 작업은 건너뛰기. (큐에 다시 들어갈 일 없음)
				if(visit[nextCell]) {
					continue;
				}
				// 해당 후보를 방문하지 않았다는 뜻. 조합풀에 있는지 확인하고 이후 작업
				if (isHere(leaf,nextCell)) {
					q.offer(nextCell);
					connectedCount++;
					visit[nextCell] = true;
				}
			}
		}
		
		// 큐가 다 빠짐
		if(connectedCount==7) {
			// S파가 다수파이고. 연결된 칸도 7개임. 성공한 조합
			successCount++;
//			System.out.println(Arrays.toString(leaf));
		}
		return;
	}
	
	// 4에서 +1 되는게 문제임. 5에서 -1 되는게 문제임. 행이 바껴서 인접하지 않는데, 인접하다고 착각하는 코드였는데. 이 함수로 착각을 해결함.
	private static int[] generateNext(int nowCell) {
		int[] result;
		if(nowCell%5 == 0) {
			result = new int[3];
			for (int i = 0; i < delta0.length; i++) {
				result[i] = nowCell + delta0[i];
			}
		} else if(nowCell%5 == 4) {
			result = new int[3];
			for (int i = 0; i < delta4.length; i++) {
				result[i] = nowCell + delta4[i];
			}
		} else {
			result = new int[4];
			for (int i = 0; i < deltaOther.length; i++) {
				result[i] = nowCell + deltaOther[i];
			}
		}
		return result;
	}
	private static boolean sIsMajority(int[] leaf) {
		int sum = 0;
		// 1차원 좌표값을 2차원 좌표값으로 복원. 해당 위치에 S파가 있는지 확인
		for (int i = 0; i < leaf.length; i++) {
			int cell = leaf[i];
			int row = cell/5;
			int col = cell%5;
			if(origin[row][col]==1) {
				sum++;
			}
		}
		if (4 <= sum) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean isHere(int[] leaf, int nextCell) {
		boolean result = false;
		// 조합풀에서 해당 값이 있으면 결과를 true로 바꾸고 작업 종료.
		for (int i = 0; i < leaf.length; i++) {
			if (leaf[i]==nextCell) {
				result = true;
				break;
			}
		}
		return result;
	}
}
