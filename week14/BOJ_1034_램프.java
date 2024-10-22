package study.week14;
// [ 11,856 KB | 68 ms ]
import java.io.*;
import java.util.*;

public class BOJ_1034_램프 {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static int N;
	static int M;
	static int K;
	static Map<Long, Integer> countMap = new HashMap<>(); 	// long값으로 해싱하고 같은 값이 들어오면 값 +1
	static Map<Long, Integer> turnOnMap = new HashMap<>();	// long값으로 해싱한 ID에 몇개 켜야되는지 저장하기.
	static int maxi = 0;
	// 메소드
	//// id로 변환
	static long hash(char[] cArr) {
		long sum=0;
		for (int i = 0; i < cArr.length; i++) {
			if(cArr[i]=='1')
				sum += (long) Math.pow(2, i);
		}
//		System.out.println(sum);
		return sum;
	}
	//// 키는데 필요한 스위치 개수
	static int turnOn(char[] cArr) {
		int sum=0;
		for (int i = 0; i < cArr.length; i++) {
			if(cArr[i]=='0')
				sum++; // 켜야 하는 전구의 개수.
		}
//		System.out.println(sum);
		return sum;
	}
	//// 이 id가 켜질수 있는지 체크
	static boolean isOK(int r) {
		if(r>K) 		// 켜야되는 스위치를 다 못킴.
			return false;
		int s = K-r; 	// 잉여 스위치 구함.
		if(s%2==0) 		// 잉여가 짝수면 소멸가능. 킬수있음.
			return true;
		else			// 잉여가 홀수면 소멸 불가능. 못킴.
			return false;
	}
	// 메인
	public static void main(String[] args) throws Exception{
		// 입력
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		for (int i = 0; i < N; i++) {
			char[] cArr = input.readLine().toCharArray(); 	// for문 돌리기 편하게 문자열을 char[]로 받음
			long id = hash(cArr);							// 이진수를 십진수로 바꿔서 id 만듬. 50비트라서 long으로 받음
			int turnOn = turnOn(cArr);						// 켜야되는 전구의 개수 => 켜야되는 스위치의 개수를 저장
			turnOnMap.put(id, turnOn);						// id 값에 켜야되는 스위치의 개수 매핑
			if(countMap.containsKey(id)) {					// id 값을 매핑한적 있으면, 그 값에 +1 // 연습. containsKey() 연습 // 디버깅 포인트  2. countMap.containsKey(cArr) 이따구로 적었네.
				int count = countMap.get(id);				// 디버깅 포인트  2. countMap.containsKey(cArr) 이따구로 적었네. 정신없다 정신없어.
				countMap.put(id, count+1);
			} else {										// id 값을 매핑한적 없으면, 1 저장.
				countMap.put(id, 1);
			}
		}
		K = Integer.parseInt(input.readLine());				// 디버깅 포인트 1. 변수 입력을 안받았음 ㅋㅋ;;
		
		// 세팅
		// 작업
		Set<Long> keySet = countMap.keySet();				// 연습. keySet() 연습.
//		for (long key: keySet) {
////			System.out.println(key+" : "+countMap.get(key));
//			int r = turnOnMap.get(key);
//			boolean result = isOK(r);						// 이 패턴이 킬수있는건지 판단.
////			System.out.println(r+" : "+result);
//			if(result)										// 이 패턴을 킬수 있다면?
//				maxi = Math.max(maxi, countMap.get(key));	// 이 패턴의 개수를 최대값과 비교해서 갱신한다.
//		}
		
		Long[] keys = keySet.toArray(new Long[0]);			// 연습. 배열로 만드는 연습.
//		System.out.println(Arrays.toString(keys));
		for (int i = 0; i < keys.length; i++) {
			int r = turnOnMap.get(keys[i]);
			boolean result = isOK(r);						// 이 패턴이 킬수있는건지 판단.
			if(result)										// 이 패턴을 킬수 있다면?
				maxi = Math.max(maxi, countMap.get(keys[i]));	// 이 패턴의 개수를 최대값과 비교해서 갱신한다.
		}
		// 출력
		System.out.println(maxi);
		
	}// 메인 종료

}
