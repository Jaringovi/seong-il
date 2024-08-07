package study.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 풀이시간: 100분 | 성공까지: 제출수 1
// 이번 풀이의 목표
//// 사실상 풀이코드가 주어진 문제지만, 예전에 정렬구현 대충 넘겼어서 이번 기회에 제대로 학습.
//// 재귀 연습

public class BOJ_24060 {
	// 입력
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int[] originArr;
	static int N;
	static int K;
	
	
	// 문제 관리 변수
	static int countToK;
	static int answer = -1;	// 조건 달성시 answer에 정답을 입력하는 로직구현. 조건 미달성시 문제의 요구대로 -1 출력되게끔 -1로 초기화.
	
	// 문제 해결용 메소드
	static void mergeSort(int left, int right) { // 파라미터로 대상 arr을 넣어주면 조금 더 일반적인 사용가능
		if(left<right) {
			int middle = (left+right)/2;
			mergeSort(left, middle);
			mergeSort(middle+1, right);
			merge(left,right);
		}
	}
	
	static void merge(int left, int right) { // 파라미터에 middle 부분 임의 제거. mergeSort랑 parameter 맞추는게 더 좋아보여서(문제와 무관함)
		int middle = (left+right)/2;			// 제거한 middle 계산
		int leftPointer = left;					// 두 배열중 왼쪽 배열 순회용 인덱스. left값은 순회 이후 또 사용해야해서 별도로 만듬
		int rightPointer = middle+1;			// 두 배열중 오른쪽 배열 순회용 인덱스.
		int[] tmpArr = new int[right-left+1];	// 정렬한 부분 임시로 저장할 배열 할당. 원래 배열에서 정렬 동시에 하면 값이 오염돼서 임시 저장 필요.
		int tmpIndex =0;						// 임시 배열 관리용 인덱스
		
		while((leftPointer<=middle) && (rightPointer<=right)) {	// 정렬 결과 만들어서 임시 배열에 저장. 두 배열중 하나가 끝날때까지. 무조건 하나가 먼저 끝남.
			if(originArr[leftPointer]<=originArr[rightPointer]) {
				tmpArr[tmpIndex++] = originArr[leftPointer++];	// 더 작은값 부터 채워넣음. 넣을때 pointer는 항상 이동. ++로 코드라인 줄이기
			}
			else {
				tmpArr[tmpIndex++] = originArr[rightPointer++];	// 더 작은값 부터 채워넣음. 넣을때 pointer는 항상 이동. ++로 코드라인 줄이기
			}
		}
		
		while(leftPointer<=middle) {	// 배열이 남았다면, 임시 배열에 남은거 마저 채워주기
			tmpArr[tmpIndex++] = originArr[leftPointer++];
		}
		while(rightPointer<=right) {	// 배열이 남았다면, 임시 배열에 남은거 마저 채워주기
			tmpArr[tmpIndex++] = originArr[rightPointer++];
		}
		
		for (int i = 0; i < tmpArr.length; i++) {	// 임시 배열 순회하면서 originArr에도 정렬 적용. originArr의 인덱스는 left+i로 평행하게 움직이게 처리.
			originArr[left+i] = tmpArr[i];
			countToK++;
			if(countToK == K) {
				answer = tmpArr[i];
			}
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		// 입력부
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(tokens.nextToken());
		
		originArr = new int[N];
		tokens = new StringTokenizer(input.readLine());		
		for (int i = 0; i < N; i++) {
			originArr[i] = Integer.parseInt(tokens.nextToken());
		}
//		System.out.println(Arrays.toString(originArr)); // 입력 정상 확인
		
		
		// 동작부
		mergeSort(0, N-1);
		// 출력부
//		System.out.println(Arrays.toString(originArr)); // 정렬 정상 작동 여부 확인
		System.out.println(answer);
		
	}//메인 닫는 괄호

}
