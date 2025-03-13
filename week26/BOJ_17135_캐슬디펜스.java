package study.week26;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_17135_캐슬디펜스 {
    // 고정
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    // 세팅
    static int best = -1;
    static int globalCount;
    static int N;
    static int M;
    static int D;
    static int[][] originMatrix;
    static int[][] workingMatrix;
    static boolean[][] visited;
    static Queue<int[]> visitQ = new LinkedList<>(); // 대신에 Deque 쓰면 백트래킹에서도 쓸만하겠는데!!!
    static int[][] delta3 = {{0,-1}, {-1,0}, {0,1}}; // 순서대로 왼쪽, 위, 오른쪽

    // 메인
    public static void main(String[] args) throws Exception {
        // 입력
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());
        D = Integer.parseInt(tokens.nextToken());

        originMatrix = new int[N+1][M];

        for (int row = 0; row < N; row++) {
            tokens = new StringTokenizer(input.readLine());
            for (int col = 0; col < M; col++) {
                originMatrix[row][col] = Integer.parseInt(tokens.nextToken());
            }
        }
        // 세팅
        workingMatrix = new int[N+1][M];
        visited = new boolean[N+1][M];
        // 작업
        generateCombination(new int[3], 0, 3, M, 0);
        // 출력
        System.out.println(best);
    }
    // 메소드
    //// 조합 생성 매소드
    static void  generateCombination(int[] leaf, int nowDigit, int targetDigit, int pool, int start) {
        // 바닥 조건
        if(nowDigit>=targetDigit) {
            // leaf 배열은 궁수 배치의 인덱스 => 게임 진행 메소드 호출
            gameReset(leaf);
            gameStart(leaf);
            return;
        }
        // 재귀 파트
        for (int i = start; i < pool; i++) { // i+1은
            leaf[nowDigit] = i;
            generateCombination(leaf, nowDigit+1, targetDigit, pool, i+1);
        }

    }

    //// 게임판 초기화.
    private static void gameReset(int[] leaf) {
        // globalCount 초기화
        globalCount = 0;
        // 게임판 초기화. // 딥카피 메소드 까먹음.
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                workingMatrix[row][col] = originMatrix[row][col];
            }
        }
    }

    //// 게임 게임 진행 메소드
    private static void gameStart(int[] leaf) {
        // 최대 턴수 => N => N+1행이 1칸씩 N번 위로 올라가서 1행이 되면 끝남.
        // => 궁수 바로 윗칸에서 BFS 돌리는걸로 => N이 --해서 1이 될때까지.
        for (int turnCount = N; turnCount >= 1; turnCount--) {
            allFire(leaf, turnCount);
        }
        // globalCount 와  best 비교.
        best = Math.max(best, globalCount);
    }



    //// 사격 매소드(= 맨하튼 거리 계산해서 후보 구하고, 맨 col값이 제일 작은것이 맨 왼쪽이니 제거 처리. => DFS)
    private static void allFire(int[] leaf, int turnCount) {
        // 궁수별로 타겟하는 표적 좌표 저장 공간
        int[][] targets = new int[3][2]; // 궁수1[x,y]

        // 각 궁수별로 바로 앞칸에서 DFS 수행해서 좌표 찾으면 리턴
        for (int i = 0; i < 3; i++) {
            int[] target = BFS( turnCount-1, leaf[i]); // 궁수 바로 윗칸에서 시작함.
            targets[i] = target;

            // 방문 배열 초기화
            while (visitQ.isEmpty() == false){
                int[] xy = visitQ.poll();
                visited[xy[0]][xy[1]] = false;
            }
        }
        // 표적들 제거
        for (int i = 0; i < 3; i++) {
            if (targets[i] == null){
                continue;
            }
            int x = targets[i][0];
            int y = targets[i][1];
            if(workingMatrix[x][y]==1){
                globalCount++;
            }
            workingMatrix[x][y] = 0;
        }
    }


    private static void moveCastle(int turnCount) {
    }

    private static int[] BFS(int row, int col) {
        // 큐 생성 초기화
        Queue<int[]> bfsQ = new LinkedList<>();
        bfsQ.offer(new int[] {row,col});
        // 이니시 값 방문처리
        visited[row][col] = true;
        visitQ.offer(new int[] {row, col});

        // 큐 꺼내기 시작 => 탐색 시작
        while (bfsQ.isEmpty() == false){
            // 큐 꺼냄
            int[] xy = bfsQ.poll();
            int x = xy[0];
            int y = xy[1];
            // 표적 확인 => 조건 합격 시
            if( workingMatrix[x][y] == 1 ){
                return new int[] {x,y};
            }

            // 확장 단계
            //// 다음 방향과 좌표 생성
            for (int i = 0; i < 3; i++) {
                int dx = delta3[i][0];
                int dy = delta3[i][1];
                int nx = dx+x;
                int ny = dy+y;
                //// 다음 좌표 범위 검증
                if(nx>=N+1 || nx<0 || ny>=M || ny<0){
                    continue;
                }
                //// 다음 좌표 방문 검증
                if(visited[nx][ny]){
                    continue;
                }
                //// 다음 좌표 거리 검증
                int distance = Math.abs(nx-row) + Math.abs(ny-col);
                if(distance>D-1){
                    continue;
                }
                //// 확장하는 단계 + 큐에 넣기 + 방문처리
                bfsQ.offer(new int[] {nx, ny});
                //// 방문처리
                visited[nx][ny] = true;
                visitQ.offer(new int[] {nx, ny});
            }
        }
        // 표적 미확보
        return null;
    }

}
