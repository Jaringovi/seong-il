package study.week22;
import java.io.*;
import java.util.*;
public class BOJ_5052_전화번호목록 {
	// 고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	// 세팅

	// 메인
	public static void main(String[] args)  throws Exception {
		int T = Integer.parseInt(input.readLine());
		for (int t = 0; t < T; t++) {
			// 테케별 입력
			int N = Integer.parseInt(input.readLine());
			//// 맵과 리스트 선언
			Map<String, Boolean> callNumberMap = new HashMap<>();
			String[] callNumberList = new String[N];
			//// 맵과 리스트 채우기
			for (int n = 0; n < N; n++) {
				String callNumber = input.readLine().replaceAll(" ", ""); // 혹시 몰라서 중간공백 제거
				callNumberList[n] = callNumber;
				callNumberMap.put(callNumber, true);
			}
			// 테케별 세팅
			// 테케별 작업
			boolean isConsistency = true; // 로직상 일관성이 없는걸 검출할거니까, 있다고 가정하고 시작.
			for (int i = 0; i < callNumberList.length; i++) {
				// 일관성 깨지면 탈출
				if(isConsistency==false) {
					break;
				}
				// 번호의 모든 접두어에 대해서 맵에 있는지 체크
				String nowCallNumber = callNumberList[i];
				String temp;
				for (int endIndex = 0+1; endIndex < nowCallNumber.length(); endIndex++) {
					temp = nowCallNumber.substring(0, endIndex);
					if(callNumberMap.containsKey(temp)) {
						// 현재 선택한 전화번호의 일부가 다른 전화번호로 등록되어있다 => 일관성 없음
						isConsistency=false;
//						System.out.println(nowCallNumber +":"+temp);
						break;
					}
				}
			}
			// 테케별 출력
			if(isConsistency) {
				output.append("YES").append("\n");
			} else {
				output.append("NO").append("\n");
			}
		}
		// 모든 테케 종료
		System.out.println(output);
	}
	// 메소드
}
