import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.math.BigInteger;

public class BOJ_2870_수학숙제 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	// 스트링 빌더 전체랑 임시, 그리고 전체 관리하는 카운터 하나 제작
	static StringBuilder whole = new StringBuilder();
	static StringBuilder temp = new StringBuilder();
	static int counter = 0;
	//	전체 라인 남겨두기
	//	스트링 빌더 쓰기, 필요시 그냥 어레이 리스트 사용
	
	// 도구 제작1
	static char[] letterPool = new char[26];
	
	static boolean check(char c) {
		for(int i=0;i<letterPool.length;i++) {
			if(letterPool[i]==(c)) {
				return true;  //c == char --> -1
			}
		}
		return false; //c == int --> 값 그대로
	}
	
	// 처리함수
	static void core(String line) {
		int pre;
		if(check(line.charAt(0))) {
			pre = -1;
		} else {
			pre = Character.getNumericValue(line.charAt(0));
			temp.append(pre);
		}
		int now;
		for(int i=1;i<line.length();i++) { // pre를 미리 초기화해둬서 i=0이 아니라 i=1부터
			if(check(line.charAt(i))==false) {
//				now = Integer.parseInt(line.charAt(i)); // 잘 작동하는지 테스트 필요함. 버그 뜨면 여기부터 확인. 일단 여기는 숫자 저장타이밍
				now = Character.getNumericValue(line.charAt(i)); // 잘 작동하는지 테스트 필요함. 버그 뜨면 여기부터 확인. 일단 여기는 숫자 저장타이밍
			} else {
				now = -1;
			}
			
			// decide
			decide(pre,now);
			
			// 포인트 이동
			pre = now;
		}
		// 디버그 결과. 캐릭터로 끝날때 필요없이 반복됨. now가 int일때만 돌아가게 작동.
		if(pre>-1) {
			whole.append(temp).append(" ");
			temp.setLength(0);
			counter++;
		}
	}
	
	static void decide(int pre, int now) { //false -> int, true -> char
		// 저장해야되는 값은 now
		if(pre > -1 && now == -1) {
			// 저장값 append -> 임시 저장소 초기화
			whole.append(temp).append(" "); // 버그 예상 포인트. 토크나이저로 초기화하기 위해 공백 삽입
			temp.setLength(0);
			counter++;
			return;
		}
		else if(pre == -1 && now > -1) {
			// 임시저장소 채우기 시작
			temp.append(now);
			return;
		}
		else if(pre > -1 && now > -1) {
			// 임시저장소 계속 채움
			temp.append(now);
			return;
		}
		else if(pre == -1 && now == -1) {
			// 임시저장소 사용 X 그냥 지나감
			return; // 처리 끝
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 레터풀 완성
		for(int i=0;i<26;i++) {
			letterPool[i] =(char)('a'+i);
		}
		
		// 받아둔 입력 처리
		int T = Integer.parseInt(input.readLine());
		for(int t=0;t<T;t++) {
			core(input.readLine());
		}
//		System.out.println(whole.toString());
		// 결과 인트 어레이로 변환
		BigInteger[] box = new BigInteger[counter];
		tokens = new StringTokenizer(whole.toString());
		for(int i=0; i<counter ; i++) {
			box[i] = new BigInteger(tokens.nextToken());
//			BigInteger num = new BigInteger("123456789000000");
		}
		Arrays.sort(box);
		
		for(BigInteger i : box) {
			System.out.println(i);
		}
	}
}
