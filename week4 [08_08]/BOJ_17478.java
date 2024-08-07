package study.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 풀이 시간: 60분 | 성공까지 제출수: 12
// 이번 풀이의 특징
//// 윈도우 환경에서 테케 출력 긁어오니까 개행문자가 "\r\n"임
//// 그거 기준으로 구현해서 출력형식이 잘못됨.
//// 백준의 채점환경에서 개행문자는 "\n"이기 때문.

public class B_17478_1 {
	static String initi ="어느 한 컴퓨터공학과 학생이 유명한 교수님을 찾아가 물었다.\n";
	static String repeatParagraph1 = "\"재귀함수가 뭔가요?\"\n";
	static String repeatParagraph2 = "\"잘 들어보게. 옛날옛날 한 산 꼭대기에 이세상 모든 지식을 통달한 선인이 있었어.\n";
	static String repeatParagraph3 = "마을 사람들은 모두 그 선인에게 수많은 질문을 했고, 모두 지혜롭게 대답해 주었지.\n";
	static String repeatParagraph4 = "그의 답은 대부분 옳았다고 하네. 그런데 어느 날, 그 선인에게 한 선비가 찾아와서 물었어.\"\n";
	static String returnSentence = "라고 답변하였지.\n";
	static String LastreturnSentence = "라고 답변하였지.";
	static String answer = "\"재귀함수는 자기 자신을 호출하는 함수라네\"\n";
	static StringBuilder output = new StringBuilder();
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	static void generateIndent(int nTime) { // 인덴트 생성기 for 가독성 향상
		for(int i=0;i<nTime;i++) {
			output.append("____");
		}
	}
	
	static void recursion(int start, int goal) {
		if(start == goal) {
			generateIndent(start);
			output.append(repeatParagraph1);
			generateIndent(start);
			output.append(answer);
			generateIndent(start);
			output.append(returnSentence);
			return;
		}
		else {
			generateIndent(start);
			output.append(repeatParagraph1);
			generateIndent(start);
			output.append(repeatParagraph2);
			generateIndent(start);
			output.append(repeatParagraph3);
			generateIndent(start);
			output.append(repeatParagraph4);
		}
		recursion(start+1,goal);
		generateIndent(start);
		output.append(returnSentence);
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		int repeatTime = Integer.parseInt(input.readLine());
//		repeatTime++;
		int count = 0;
		output.append(initi);
		recursion(count, repeatTime);
		String str = output.toString();
		System.out.println(str);
	}

}
