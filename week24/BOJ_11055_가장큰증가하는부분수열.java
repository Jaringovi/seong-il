package study.week24;
import java.io.*;
import java.util.*;
public class BOJ_11055_가장큰증가하는부분수열 {
    // 입력고정
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    // 세팅
    // 메인
    public static void main(String[] args) throws Exception {
        // 입력
        int N = Integer.parseInt(input.readLine());
        int[] arr = new int[N];
        int[] dp = new int[N];
        tokens = new StringTokenizer(input.readLine());
        for (int i = 0; i < N; i++) {
            int element = Integer.parseInt(tokens.nextToken());
            arr[i] = element;
            dp[i] = element;
        }
        // 세팅
        // 작업
        for (int cursor = 0; cursor < N; cursor++) { // 현재 dp에서 업데이트할 위치
            for (int candidate = 0; candidate < cursor; candidate++) { // 업데이트 후보군
                if(arr[candidate]<arr[cursor]){ // candidate의 값으로 끝난 수열 이후에 현재 cursor값이 추가될수 있는 상황
                    dp[cursor] = Math.max(dp[cursor], dp[candidate]+arr[cursor]); // dp[cursor]의 값을 계속 업데이트. 하면서 변수 사용량과 로직을 단순하게 가져갈수 있다.
                }
            }
        }
        int maxValue = -1;
        for (int i = 0; i < dp.length; i++) {
            maxValue = Math.max(maxValue, dp[i]);
        }
        // 출력
        System.out.println(maxValue);
    }
    // 메소드
}
