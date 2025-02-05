package study.week25;
import java.io.*;
import java.util.*;
// 디버깅 포인트
// 윗방향이 y가 증가하고, 오른쪽 방향이 x가 증가하는 일반적인 좌표평면 상에서 
// 시계방향으로 돌리게 되면 nx += pmy;ny -= pmx; 가 돼야한다.
// 하지만 문제에서는 아래방향이 y가 증가하고, 오른쪽 방향이 x가 증가하는 좌표평면이므로
// 이 좌표평면에서 시계방향은 일반적인 좌표평면에서는 반시계방향이 된다.
// 따라서 일반적인 좌표평면에서 반시계방향으로 돌리게 되면 nx -= pmy; ny += pmx; 가 된다.
public class BOJ_15685_드래곤커브 {
	// 입력 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static boolean[][] visit = new boolean[101][101];
	static int[][] delta4 = {{1,0},{0,-1}, {-1,0},{0,1}}; // 아 이거 맞나 갑자기 바보돼서 행,열을 x,y에 대입했는지 y,x에 대입했는지 헷갈리네
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		int N = Integer.parseInt(input.readLine());
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(input.readLine());
			int x = Integer.parseInt(tokens.nextToken());
			int y = Integer.parseInt(tokens.nextToken());
			int direction = Integer.parseInt(tokens.nextToken());
			int generation = Integer.parseInt(tokens.nextToken());
			// 입력 받을때마다 드래곤 커브 생성
			List<int[]> dragonCurve = makeDragonCurve(x,y,direction,generation);
			for (int point = 0; point < dragonCurve.size(); point++) {
				int[] nowPoint = dragonCurve.get(point);
//				System.out.println(Arrays.toString(nowPoint));
				int pointX = nowPoint[0];
				int pointY = nowPoint[1];
				visit[pointX][pointY] = true; // 해당 점 true로 교체.
			}
//			System.out.println("================");
		}
		// 세팅
		int count = 0;
		// 작업
		for (int row = 0; row < 101-1; row++) { // 1*1 사각형의 네꼭지점 체크 => 2*2 정사각형 체크랑 같아서 범위 -1씩함
			for (int col = 0; col < 101-1; col++) {
				int sum = 0;
				if(visit[row][col])
					sum++;
				if(visit[row+1][col])
					sum++;
				if(visit[row][col+1])
					sum++;
				if(visit[row+1][col+1])
					sum++;
				if (sum==4) { // 네 꼭짓점이 모두 드래곤 커브의 일부
					count++;
				}
			}
		}
		// 출력
		System.out.println(count);
	}
	private static List<int[]> makeDragonCurve(int x, int y, int direction, int generation) {
		
		// 0) 초기화 : 커브를 구성하는 점들을 list에 담음.
		List<int[]> dragonCurve = new ArrayList<>();
		dragonCurve.add(new int[] {x,y});
		int dx = x+ delta4[direction][0];
		int dy = y+ delta4[direction][1];
		dragonCurve.add(new int[] {dx,dy});
		
		// 1) 각 세대 갱신
		for (int i = 0+1; i <= generation; i++) { 
			// 이 반복문의 반복마다, i세대 드래곤 커브가 생성됨. 따라서 0세대는 이미 초기화했으므로 건너뛰고. 
			// generation까지 반복해야한다.
			
			int nowSize = dragonCurve.size();
			int[] endPoint = dragonCurve.get(nowSize-1);// list에 마지막으로 추가된 점이 끝점임. 
														// 끝점에서 가장 먼점이 90도 회전할때도 가장 먼점이 되므로, 새로운 끝점이 된다.
			for (int point = nowSize-1-1; point >= 0 ; point--) { // list에 실시간으로 추가될 예정이라서, 현재 시점을 변수로 저장해두고 반복문의 범위지정에 사용함.
				int[] nowPoint = dragonCurve.get(point); // 역순으로 순회하며 점을 선택.
				int[] newPoint = rotate(endPoint, nowPoint); // 선택된 점을 시계방향으로 90도 회전
				boolean isOK = isDuplicate(newPoint); 	// 중복인지 확인.
				if(isOK) {	// 중복 아니면 추가. // 만약 새로운 끝점이 중복이라면 중복이더라도 추가하는게 맞긴함...
					dragonCurve.add(newPoint);
				}
			}
		}
		
		return dragonCurve;
	}
	private static boolean isDuplicate(int[] newPoint) {
		// 중복검사 작업해서 중복아니면 true 보내서 추가. 중복이면 false 보내서 추가 방지.
		// 일단은 프랙탈은 발산하니까 점이 중복으로 잡히지 않을거라는 생각으로 항상 true 리턴.
		
		// 임시로 범위 체크 기능으로 사용. 디버깅 예상 포인트
		if( (0<= newPoint[0] && newPoint[0]<101) && (0<= newPoint[1] && newPoint[1]<101) )
			return true;
		else
			return false;
	}
	private static int[] rotate(int[] endPoint, int[] nowPoint) {
		// endPoint를 원점취급하기 위해서 원점으로 평행이동. endPoint의 값을 다 빼줌.
		// pmx = parrel move x
		int pmx = nowPoint[0]-endPoint[0]; // nowPoint[0]+dx = endPoint[0]
		int pmy = nowPoint[1]-endPoint[1]; // nowPoint[0]+dx = endPoint[0]
		
		int nx = endPoint[0]; // 어차피 원점에서 증감하게 될거라서 미리 선언하기.
		int ny = endPoint[1];
//		if(pmx*pmy==0) { // 로직이 똑같음. 합치기 가능.
////			if(pmx>0 && pmy==0) { // 로직이 똑같음. 합치기 가능.
////				nx -= pmy;
////				ny += pmx;
////			}
////			if(pmx==0 && pmy>0) {
////				nx -= pmy;
////				ny += pmx;
////			}
////			if(pmx<0 && pmy==0) {
////				nx -= pmy;
////				ny += pmx;
////			}
////			if(pmx==0 && pmy<0) {
////				nx -= pmy;
////				ny += pmx;
////			}
//			nx -= pmy;
//			ny += pmx;
//		} else { // 로직이 똑같음. 합치기 가능.
////			if(pmx > 0 && pmy < 0) { // ㄱ
////				nx -= pmy;
////				ny += pmx;
////			}
////			if(pmx > 0 && pmy > 0) { // ㄴ
////				nx -= pmy;
////				ny += pmx;
////			}
////			if(pmx < 0 && pmy > 0) { // ㄷ
////				nx -= pmy;
////				ny += pmx;
////			}
////			if(pmx < 0 && pmy < 0) { // ㄹ
////				nx -= pmy;
////				ny += pmx;
////			}
//			nx -= pmy;
//			ny += pmx;
//		}
		nx -= pmy;
		ny += pmx;
		
		return new int[] {nx, ny};
	}
//	private static int[] rotate(int[] endPoint, int[] nowPoint) {
//		// 원점(끝점)으로 오기위해서. x는 dx만큼 가까워지고, y는 dy만큼 가까워진다. 
//		// 그 후 원점으로 가까워진 만큼 원점에서 멀어지기위해 움직인다. x가 dy 만큼 멀어지고, y는 dx만큼 멀어진다.
//		// 가깝고 멀고는 부호를 바꿔주면 된다고 생각한다.
//		int dx = endPoint[0]-nowPoint[0]; // nowPoint[0]+dx = endPoint[0]
//		int dy = endPoint[1]-nowPoint[1]; // nowPoint[1]+dy = endPoint[1]
//		
//		int nx = endPoint[0]-dy;
//		int ny = endPoint[1]-dx;
//		
//		return new int[] {nx, ny};
//	}
	
	// 메소드

}
