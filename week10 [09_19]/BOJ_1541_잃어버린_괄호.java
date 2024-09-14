// 문제 풀이 블로깅
// https://velog.io/@bed_is_good/%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%EB%B0%B1%EC%A4%80-1541-%EC%9E%83%EC%96%B4%EB%B2%84%EB%A6%B0-%EA%B4%84%ED%98%B8-JAVA

import java.io.*;
import java.util.*;

public class Main {

	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	// 세팅
	static ArrayList<Integer> operandList;
	static ArrayList<Character> operatorList;
	static ArrayList<Integer> subSums;
	// 메소드
	

	
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		operandList = new ArrayList<>();
		operatorList = new ArrayList<>();
		subSums = new ArrayList<>();
		
		tokens = new StringTokenizer(input.readLine(),"-+",true);
		for (int i = 0; ; i++) { // 무한루프
			if(tokens.hasMoreTokens()==false) { // 더 이상 토큰이 없으면 탈출
				break;
			}
			if(i%2==0) { // 짝수번째는 피연산자임. origin 리스트에 분류하기.
				int operand = Integer.parseInt(tokens.nextToken());
				operandList.add(operand);
			} else { // 홀수번쨰는 연산자임. operator 리스트에 분류하기.
				char operator = tokens.nextToken().charAt(0);
				operatorList.add(operator);
			}
		}
		// 세팅

		// 작업
		int sum =0;
		sum += operandList.get(0); 	// 첫번째꺼는 미리 더해놓기 그래야 더하는 로직이 단순해짐. 지금 시점 하나만 더하면 되니까. 
									// 안 그러면 반복문에서 처음에 두개 더해야되는데 뒤에서도 같은 로직 쓰면서 계속 1번씩 중복해서 더해짐.
		for (int i = 0; i < operatorList.size(); i++) {
			if(operatorList.get(i) == '-') { // 연산자가 -가 나오기 전까지는 계속 더한다. -가 나오면 지금까지 더해놓은거 subSums에 저장하기. 
				subSums.add(sum); 	// -가 나오면 지금까지 더해놓은거 저장하기. 
				sum = 0;			// 다시 처음부터 더하기 위해서 초기화하기.
			}
			sum += operandList.get(i+1); 
		}
		// 마지막 한덩어리도 add
		subSums.add(sum);
		
		// 값 계산. 첫번째만 양수고 나머지는 다 음수이므로 빼줌.
		int answer = subSums.get(0);
		for (int i = 0+1; i < subSums.size(); i++) {
			answer -= subSums.get(i);
		}
		// 출력
		System.out.println(answer);
	}
}
