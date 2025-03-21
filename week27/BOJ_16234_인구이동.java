package study.week27;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_16234_인구이동 {
    // 고정
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    // 세팅
    static int N;
    static int L;
    static int R;
    static int[][] originMatrix;

    static int dayCount;
    static boolean terminateToggle;

    static int[][] delta4 = {{0,1},{0,-1}, {1,0},{-1,0}};

//    static Set<Integer> visited;
    static Set<Integer> notVisited;
    // 메인
    public static void main(String[] args) throws Exception{
        // 입력
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        L = Integer.parseInt(tokens.nextToken());
        R = Integer.parseInt(tokens.nextToken());
        originMatrix = new int[N][N];
        for (int row = 0; row < N; row++) {
            tokens = new StringTokenizer(input.readLine());
            for (int col = 0; col < N; col++) {
                int cell = Integer.parseInt(tokens.nextToken());
                originMatrix[row][col] = cell;
            }
        }
        // 세팅
//        visited = new HashSet<>();
        notVisited = new HashSet<>();
        Set<Integer> notVisitedOrigin = new HashSet<>();
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                int hash = row*N+col;
                notVisitedOrigin.add(hash);
            }
        }
        // 작업
        while (terminateToggle==false){
            // 하루 시작
            terminateToggle=true;
            /// 방문셋 초기화
//            visited.clear();
    //        notVisited.clear(); // 로직 안에서 어차피 다 뺌
            notVisited.addAll(notVisitedOrigin);
            /// 하루동안 아래 반복.
            while (notVisited.isEmpty()==false){
                int nextStart = notVisited.iterator().next();
                BFS(nextStart);
            }
            if(terminateToggle==false){
                dayCount++;
            }
        }
        // 출력
        System.out.println(dayCount);
    }

    // 메소드

    /// encode
    private static int encode(int row, int col) {
        return row*N+col;
    }
    /// decode
    private static int[] decode(int hash) {
        int col = hash%N;
        int row = hash/N;
        return new int[] {row, col};
    }

    private static void BFS(int hash) {
        // 큐 생성 초기화
        Queue<Integer> bfsQ = new LinkedList<>();
        bfsQ.offer(hash);
        List<Integer> hashList = new ArrayList<>();
        int total = 0;
        // 이니시 값 방문처리
        notVisited.remove(hash);

        // 큐 꺼내기 시작 => 탐색 시작
        while (bfsQ.isEmpty() == false){
            // 큐 꺼냄
            // List에 넣고. 합계 구하고, BFS 끝난 다음에 리스트 사이즈로 나눠주고 바꿔주기
            int nowHash = bfsQ.poll();
            int x = nowHash/N;
            int y = nowHash%N;
            hashList.add(nowHash);
            total+=originMatrix[x][y];

            // 확장 단계
            // 다음 방향과 좌표 생성
            for (int i = 0; i < delta4.length; i++) {
                int dx = delta4[i][0];
                int dy = delta4[i][1];
                int nx = dx+x;
                int ny = dy+y;
                int nextHash = encode(nx,ny);
                //// 다음 좌표 범위 검증
                if(nx>=N || nx<0 || ny>=N || ny<0){
                    continue;
                }
                //// 다음 좌표 방문 검증
                if(notVisited.contains(nextHash)==false){
                    continue;
                }
                //// 다음 좌표 인구 검증
                int gap = Math.abs(originMatrix[x][y] -  originMatrix[nx][ny]);
                if(L<=gap && gap<=R){
                } else{
                    continue;
                }
                //// 확장하는 단계 + 큐에 넣기 + 방문처리
                bfsQ.offer(nextHash);
                //// 방문처리
//                visited.add(nextHash);
                notVisited.remove(nextHash);
            }
        }
        // BFS 종료
        int meanPopulation = total/hashList.size();
        for (int i = 0; i < hashList.size(); i++) {
            int decodeTargetHash = hashList.get(i);
            int row = decodeTargetHash/N;
            int col = decodeTargetHash%N;
            originMatrix[row][col]=meanPopulation;
        }
        // 국경 열리면 인구 이동 가능하니까, 종료 토글 작동 안하게 방지.
        if(hashList.size()>1){
            terminateToggle = false;
        }
    }
}
