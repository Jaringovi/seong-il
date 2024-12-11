package study.week18;
import java.io.*;
import java.util.*;

public class BOJ_20665_독서실거리두기_최선_성공 {
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
	static class Candidate{
		int distance;
		int seatNum;
		Candidate(int distance,	int seatNum){
			this.distance = distance;
			this.seatNum = seatNum;
		}
	}
	static List<Reservation> reservationList = new ArrayList<>();
	static Map<Integer, Integer> inMap = new HashMap<>(); // 사용중인 좌석의 위치.
	static Map<String, List<Integer>> outTimeMap = new HashMap<>(); // 사용중인 좌석의 퇴실시간.
	static String nowTime ="0900";
	static int nowReservation=0;
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
			if(inTime.equals(outTime)) {
				continue;
			}
			Reservation reservation = new Reservation(inTime, outTime);
			reservationList.add(reservation);
		}
		//// 예약 조건에 맞게 정렬
		reservationList.sort(Comparator
			.comparing((Reservation reservation) -> reservation.inTime)
			.thenComparing((Reservation reservation) -> reservation.outTime)
		);
		// 세팅
		// 작업
		for(;;) {
			if("2100".equals(nowTime)) { //2100이면 어차피 다 나가고, 더 못 들어오고, 더 이용도 못함.
				break;
			}
//			System.out.println(inMap.size());
			// 현재 시간 작업 시작
			//// 퇴실처리
			List<Integer> outSeatList = outTimeMap.get(nowTime);
			outTimeMap.remove(nowTime);
//			System.out.println(outSeatList);
			if(outSeatList != null) {
				for (int i = 0; i < outSeatList.size(); i++) {
					int seatNum = outSeatList.get(i);
					inMap.remove(seatNum);
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
			if(inMap.containsKey(P)==false) {
//				System.out.println(useTime);
				useTime++;
			}
			//// 시간이동
			timeMove();
		}
		// 출력
//		System.out.println(P);
//		for (Reservation reservation : reservationList) {
//			System.out.println(reservation.inTime +" : "+ reservation.outTime);
//		}
		System.out.println(useTime);
	}
	
	static void inSeat(String outTime, int seatNum) {
		// inMap에 반영
		inMap.put(seatNum, seatNum);
		// outTimeMap에 반영
		if(outTimeMap.containsKey(outTime)) {
			outTimeMap.get(outTime).add(seatNum);
		} else {
			outTimeMap.put(outTime, new ArrayList<>());
			outTimeMap.get(outTime).add(seatNum);
		}
	}
	
	static void assignSeat(String outTime) {
		if(inMap.size()==0) {
			// 0번에 넣기.
			inSeat(outTime,0);
			return;
		}
		// 사용중 좌석 꺼내서 정렬함
		Collection<Integer> values = inMap.values();
		List<Integer> sortedValues = new ArrayList<>(values);
		Collections.sort(sortedValues);
		
		// 배정할 좌석의 후보 리스트 생성
		List<Candidate> candidateList = new ArrayList<>();
		int first = sortedValues.get(0);
		int last = sortedValues.get(sortedValues.size()-1);
		
		// 왼쪽 끝 안 막힘
		if(first > 0) {
			candidateList.add(new Candidate(first, 0));
		}
		// 오른쪽 끝 안 막힘
		if(last < N-1) {
			candidateList.add(new Candidate((N-1)-last, N-1));
		}
		// 양쪽 끝 막힌 구간 작업
		for (int i = 0; i < sortedValues.size()-1; i++) {
			int pre = sortedValues.get(i);
			int next = sortedValues.get(i+1);
			int radi = (next-pre)/2;
			int position = (next+pre)/2; // 내림을 써도 되는 이유 => 좌석번호가 적은것을 써야하기 때문
			candidateList.add(new Candidate(radi, position));
		}
		// 간격이 크고 좌석 번호가 작은게 맨 앞으로 오게 정렬
		candidateList.sort(Comparator
				.comparing((Candidate candidate) -> candidate.distance, Comparator.reverseOrder())
				.thenComparing((Candidate candidate) -> candidate.seatNum)
		);
		int assign = candidateList.get(0).seatNum;
		// 넣기
		inSeat(outTime,assign);
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
