package study.week18;
import java.io.*;
import java.util.*;

public class BOJ_20665_독서실거리두기_나이브 {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static int T;
	static int P;
	static class Reservation{
		String inTime;
		String outTime;
		Reservation(String inTime,	String outTime){
			this.inTime = inTime;
			this.outTime = outTime;
		}
	}
	static List<Reservation> reservationList = new ArrayList<>();
	static int nowReservation=0;
	static String[] seatANDOutTime;
	static String nowTime ="0900";
	static int useTime;
	// 메소드
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		T = Integer.parseInt(tokens.nextToken());
		P = Integer.parseInt(tokens.nextToken())-1; // 인덱스 번호와 맞춤
		for (int t = 0; t < T; t++) {
			tokens = new StringTokenizer(input.readLine());
			String inTime = tokens.nextToken();
			String outTime = tokens.nextToken();
			Reservation reservation = new Reservation(inTime, outTime);
			reservationList.add(reservation);
		}
		//// 예약 조건에 맞게 정렬
		reservationList.sort(Comparator
			.comparing((Reservation reservation) -> reservation.inTime)
			.thenComparing((Reservation reservation) -> reservation.outTime)
		);
		// 세팅
		seatANDOutTime = new String[N];
		Arrays.fill(seatANDOutTime, "빈 좌석");
		// 작업
		for(;;) {
//			System.out.println(nowTime+Arrays.toString(seatANDOutTime));
			if(nowTime.equals("2100")) { //2100이면 어차피 다 나가고, 더 못 들어오고, 더 이용도 못함.
				break;
			}
			
			// 현재 시간 작업 시작
			//// 퇴실처리
			for (int seatNum = 0; seatNum < N; seatNum++) {
				if(seatANDOutTime[seatNum].equals(nowTime)) { // 해당 좌석 퇴실 조건 만족
					seatANDOutTime[seatNum] = "빈 좌석"; // 초기화 => 퇴실
				}
			}
			//// 입실처리
			for(;;){
				if (nowReservation == reservationList.size()) {
					break;
				}
				Reservation reservation = reservationList.get(nowReservation);
				if(reservation.inTime.equals(nowTime)) {
					assignSeat(reservation.outTime);
					nowReservation++;
				} else {
					break;
				}
			}
			// 현재 시간 작업 종료
			//// 사용가능 여부 확인
			if(seatANDOutTime[P]=="빈 좌석") {
//				System.out.println(nowTime+Arrays.toString(seatANDOutTime));
				useTime++;
			}
			//// 시간이동
			timeMove();
		}
		// 출력

		System.out.println(useTime);
	}
	
	
	static void assignSeat(String outTime) {
		List<Integer> useSeatList = new ArrayList<>();
		for (int seatNum = 0; seatNum < N; seatNum++) {
			if(seatANDOutTime[seatNum] != "빈 좌석") { // 사용중임 => 퇴실 이후에 해야 됨
				useSeatList.add(seatNum);
			}
		}
		// 사용중인 좌석이 없다 => 0번 좌석에 집어넣고 종료.
		if(useSeatList.size()==0) {
			seatANDOutTime[0] = outTime;
//			System.out.println("first");
			return;
		}
		// 좌석별로 최대거리 저장하기 위한 배열 생성.
		int[] best = new int[N];
		// 좌석별로 왼쪽 최단거리, 오른쪽 최단거리 계산 후. 좌우 최단거리의 최소값을 좌석에 저장.
		for (int seatNum = 0; seatNum < N; seatNum++) {
			int left = 200;
			int right = 200;
			for (int compare = 0; compare < useSeatList.size(); compare++) {
				int useSeatNum = useSeatList.get(compare);
//				if(useSeatNum==seatNum) { // 이미 사용중인 좌석은 입실할 여지가 없으므로 점프.
//					best[seatNum] = 0;
//					continue;
//				}
				if(useSeatNum<seatNum) {
					left = useSeatNum;
				} else if(useSeatNum>seatNum) {
					right = useSeatNum;
					break; // 더 진행할 필요 X
				}
			}
			left = Math.abs(seatNum-left);
			right = Math.abs(seatNum-right);
			if(seatNum==0) {
//				System.out.println(left +" : "+right);
			}
			int radius = Math.min(left, right);
			best[seatNum] = radius;
			// 이미 사용중인 좌석은 입실할 여지가 없으므로 거리는 0으로 할당.
			if(useSeatList.contains(seatNum)) {
				best[seatNum] = 0;				
			}
		}
//		System.out.println(Arrays.toString(best));
		// best 순회하면서 값이 가장 큰 자리에 입실시키기. 인덱스는 가장 작은 곳에.
		int maxDistance = -1;
		int maxDistanceIndex = 0;
		for (int seatNum = 0; seatNum < best.length; seatNum++) {
			if(maxDistance < best[seatNum]) { // 더 클때만 갱신. 같을때는 갱신 안함 => 최대값중 인덱스 제일 작은값 유지
				maxDistance = best[seatNum];
				maxDistanceIndex = seatNum;
			}
		}
		// 해당 인덱스에 입실 == 퇴실시간 기록
		seatANDOutTime[maxDistanceIndex] = outTime;
	}

	static void timeMove() {
		String HH = nowTime.substring(0, 2);
		String MM = nowTime.substring(0+2, 2+2);
		int hh = Integer.parseInt(HH);
		int mm = Integer.parseInt(MM);
		// 1분 이동
		mm++;
		// 60분이면 1시간 이동
		if(mm==60) {
			hh++;
			mm=mm%60;
		}
		// 자릿수 0000으로 맞춰줌
		if(hh<10) {
			HH = "0"+hh;
		} else {
			HH = ""+hh;
		}
		if(mm<10) {
			MM = "0"+mm;
		} else {
			MM = ""+mm;
		}
		// nowTime++ 의 결과
		nowTime = HH+MM;
	}

}
