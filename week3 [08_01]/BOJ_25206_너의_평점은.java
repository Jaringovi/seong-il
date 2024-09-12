package study.week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

//목표 실수형 데이터(float,double) 계산을 최소화하기
// [0,1,2,3,4] "whole"배열 생성 -> 각 인덱스는 이수학점과 대응됨 -> 각 인덱스에 어레이 리스트 로딩 
	// -> 어레이 리스트에는 평가점수들 하나씩 add

// 자료구조 세팅
	// "whole"배열 생성
	// "Grade" Map -> key:"A+", value: 9

// 줄마다 읽으면서
	// 세번째 토큰(평가 점수)이 "P"일 경우 해당 라인 패스

	// 첫번째 토큰은 처리X -> 과목명은 버림
	// 두번째 토큰은 이수학점 -> int로 변환 -> "whole"의 인덱스로 사용
	// 세번째 토큰은 평가 점수 -> 맵의 Key로 사용 -> 맵의 value값을 "whole"의 인덱스에 add
		
// 마무리
	// 전부 끝나면 학점 계산
	// Int A (어레이 리스트 사이즈 * 인덱스*2)의 총합
	// Double B (어레이 리스트별 총합 * 인덱스)의 총합
	// B/A


public class BOJ_25206_1 {
	
	static ArrayList<Integer> [] whole = new ArrayList [5];
	static Map<String, Integer>  gradeMap = new HashMap<>();
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	
	public static void main(String[] args) throws IOException {
	
		// 자료구조 세팅
		for(int i=0;i<whole.length;i++) {
			whole[i] = new ArrayList<>();
		}
		
		gradeMap.put("A+", 9);
		gradeMap.put("A0", 8);
		gradeMap.put("B+", 7);
		gradeMap.put("B0", 6);
		gradeMap.put("C+", 5);
		gradeMap.put("C0", 4);
		gradeMap.put("D+", 3);
		gradeMap.put("D0", 2);
		gradeMap.put("F", 0);
		
		// 입력+처리
		String line ;
		while((line = input.readLine()) != null) { // 콘솔환경에서는 EOF를 못만남... 이거 때문에 자바 접을뻔
			tokens = new StringTokenizer(line);
			
			String subject = tokens.nextToken();
			int time = (int)Float.parseFloat(tokens.nextToken());
			String grade = tokens.nextToken();
			
			if(grade.equals("P")) { // PASS는 PASS
				continue;
			}
			
			whole[time].add(gradeMap.get(grade)); //
		}

		int globalGradeSum = 0;
		for(int i=0;i<whole.length;i++) {
			int localGradeSum = 0;
			for(int a: whole[i]) {
				localGradeSum+=a;
			}
			globalGradeSum += localGradeSum*i;
		}
		
		int globalTimeSum = 0;
		for(int i=0;i<whole.length;i++) {
			globalTimeSum += whole[i].size()*i*2;
		}
		System.out.println(1.0*globalGradeSum/globalTimeSum);

	}

}
