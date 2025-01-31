package study.week24;


import java.io.*;
import java.util.*;
// 구간합 배열을 한다.
// 구간합 배열의 차이를 통해 모든 부분배열에 대해서 각각의 합을 구해서.
// 부분배열 합 pool에 넣어준다.
// 두 배열의 부분배열의 합 pool에서 순서쌍을 만든다.(이중for문)
// 순서쌍의 합이 T인지 확인한다.

// 시간복잡도 (1000^2)^2 => 1조
// poolA,B를 정렬해서 T보다 큰 부분은 다 버리자
// => 시간복잡도 개선 불가능.
// 약간의 요행. 메이저하지 않은, 마이너한 최적화.
// 값이 음수일수도 있어서 T보다 큰걸 버릴수도 없음.
// 나올수 있는 최소값인 -10억을 0 또는 양수로 만들기위해 모든 값에 대해서 10억을 더해준다면 마이너한 최적화도 사용가능함.
// 나올수 있는 최대값이 10억이라서 10억을 더해도 20억으로 int타입으로 커버가 가능하다.
// poolB를 정렬하자. 그리고 poolA를 순회하면서 "T-poolA.get(i)" 값을 poolB에서 이진탐색으로 찾자.
// 그런데 "T-poolA.get(i)"값이 poolB에 여러개일수 있으니까. 도착한 지점에서 앞뒤로 스캔하면서 더 있는지 확인해야함.
// 같은 값이 또 나올수 있으니까 아예 캐싱해둘까? 이 값이 몇개 있는지 캐싱?
// 걍 값마다 몇개 있는지 미리 세둘까? 어? 이거 해쉬맵? 값 찾아가는데 이진탐색도 필요없네?

public class BOJ_2143_두배열의합 {
    // 입력 고정
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    // 세팅
//	static int answerCount;
//	static int N;
//	static int M;
    // 메인
    public static void main(String[] args) throws Exception {
        // 입력
        int T = Integer.parseInt(input.readLine());

        int N = Integer.parseInt(input.readLine());
        int[] arrayA = new int[N];
        tokens = new StringTokenizer(input.readLine());
        int sumA = 0;
        for (int i = 0; i < N; i++) {
            sumA += Integer.parseInt(tokens.nextToken());
            arrayA[i] = sumA;
        }

        int M = Integer.parseInt(input.readLine());
        int[] arrayB = new int[M];
        tokens = new StringTokenizer(input.readLine());
        int sumB = 0;
        for (int i = 0; i < M; i++) {
            sumB += Integer.parseInt(tokens.nextToken());
            arrayB[i] = sumB;
        }

        // 세팅
        long answerCount = 0;
//		int[] prefixSumA = new int[N]; // 입력 받을때 그냥 구간합 구함.
//		int[] prefixSumB = new int[M];

        List<Integer> poolA = new ArrayList();
        List<Integer> poolB = new ArrayList();
        Map<Integer,Long> hashMapA = new HashMap<>();
        Map<Integer,Long> hashMapB = new HashMap<>();

        // 작업
        // poolA 생성
//		poolA.add(arrayA[0]); // 아래 반복문으로 0은 안들어감. => 디버깅. 다 더한걸 빼먹었네.
        for (int i = 0; i < arrayA.length; i++) {
            poolA.add(arrayA[i]);
        }
        for (int i = 0+1; i < arrayA.length; i++) {
            for (int j = 0; j < i; j++) {
                int subSum = arrayA[i]-arrayA[j];
                poolA.add(subSum);
            }
        }

        // poolB 생성
//		poolB.add(arrayB[0]); // 아래 반복문으로 0은 안들어감.
        for (int i = 0; i < arrayB.length; i++) {
            poolB.add(arrayB[i]);
        }
        for (int i = 0+1; i < arrayB.length; i++) {
            for (int j = 0; j < i; j++) {
                int subSum = arrayB[i]-arrayB[j];
                poolB.add(subSum);
            }
        }

        // hashMapB 생성
        for (int i = 0; i < poolB.size(); i++) {
            int subSum = poolB.get(i);
            if(hashMapB.containsKey(subSum)) { // key 있음
                long oldValue = hashMapB.get(subSum);
                hashMapB.put(subSum, oldValue+1);
            } else {
                hashMapB.put(subSum, (long) 1);
            }
        }

        // hashMapA 생성 대신하는데 메모리, 시간의 비용이 높은것으로 인식됨. 그냥 쭉 읽으면서 그때그때 계산하기.
        for (int i = 0; i < poolA.size(); i++) {
            int subSum = poolA.get(i);
            int target = T-subSum;
            if (hashMapB.containsKey(target)) {
                answerCount+= hashMapB.get(target);
            }
        }

        // 출력
        System.out.println(answerCount);
    }
    // 메소드
}
