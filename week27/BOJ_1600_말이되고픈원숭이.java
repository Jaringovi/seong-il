package study.week27;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1600_말이되고픈원숭이 {
    // 고정
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    // 세팅
    static int K;
    static int W;
    static int H;
    static int[][] originMatrix;
    static boolean[][][] visit; // [row][col][말 무빙]

    //
    static class State {
        int row;
        int col;
        int depth;
        int useK;

        State(int row, int col, int depth, int useK){
            this.row = row;
            this.col = col;
            this.depth = depth;
            this.useK = useK;
        }
    }

    static int[][] delta8 = {
            {-2,+1},{-1,+2},
            {+1,+2},{+2,+1},
            {+2,-1},{+1,-2},
            {-1,-2},{-2,-1}
    };
    static int[][] delta4 = {{0,1},{0,-1}, {1,0},{-1,0}};
    // 메인
    public static void main(String[] args) throws Exception{
        // 입력
        K = Integer.parseInt(input.readLine());
        tokens = new StringTokenizer(input.readLine());
        W = Integer.parseInt(tokens.nextToken());
        H = Integer.parseInt(tokens.nextToken());
        originMatrix = new int[H][W];
        for (int row = 0; row < H; row++) {
            tokens = new StringTokenizer(input.readLine());
            for (int col = 0; col < W; col++) {
                int cell = Integer.parseInt(tokens.nextToken());
                originMatrix[row][col] = cell;
            }
        }
        // 세팅
        visit = new boolean[H][W][K+1]; // k = 0~K

        // 작업

        // 출력
        System.out.println(BFS());
    }

    // 메소드


    private static int BFS() {
        // 큐 생성 초기화
        Queue<State> bfsQ = new LinkedList<>();
        bfsQ.offer(new State(0, 0, 0, 0));
        // 이니시 값 방문처리
        visit[0][0][0] = true;

        // 큐 꺼내기 시작 => 탐색 시작
        while (bfsQ.isEmpty() == false){
            // 큐 꺼냄
            // List에 넣고. 합계 구하고, BFS 끝난 다음에 리스트 사이즈로 나눠주고 바꿔주기
            State now = bfsQ.poll();
            int x = now.row;
            int y = now.col;

            // 큐에서 꺼낸게 정답인지 확인
            if( x==H-1 && y==W-1 && now.useK<=K ){
                return now.depth;
            }

            // 확장 단계
            // 8방 탐색 먼저
            // 다음 방향과 좌표 생성
            if(now.useK<=K-1){
                for (int i = 0; i < delta8.length; i++) {
                    int dx = delta8[i][0];
                    int dy = delta8[i][1];
                    int nx = dx+x;
                    int ny = dy+y;
                    //// 다음 좌표 범위 검증
                    if(nx>=H || nx<0 || ny>=W || ny<0){
                        continue;
                    }
                    //// 다음 좌표 방문 검증
                    if(visit[nx][ny][now.useK+1]){
                        continue;
                    }
                    //// 다음 좌표 지형 검증
                    if(originMatrix[nx][ny]==1){
                        continue;
                    }

                    //// 확장하는 단계 + 큐에 넣기 + 방문처리
                    bfsQ.offer(new State(nx,ny, now.depth+1, now.useK+1));
                    //// 방문처리
                    visit[nx][ny][now.useK+1]=true;
                }
            }

            // 다음 4방탐색
            // 다음 방향과 좌표 생성
            for (int i = 0; i < delta4.length; i++) {
                int dx = delta4[i][0];
                int dy = delta4[i][1];
                int nx = dx+x;
                int ny = dy+y;
                //// 다음 좌표 범위 검증
                if(nx>=H || nx<0 || ny>=W || ny<0){
                    continue;
                }
                //// 다음 좌표 방문 검증
                if(visit[nx][ny][now.useK]){
                    continue;
                }
                //// 다음 좌표 지형 검증
                if(originMatrix[nx][ny]==1){
                    continue;
                }

                //// 확장하는 단계 + 큐에 넣기 + 방문처리
                bfsQ.offer(new State(nx,ny, now.depth+1, now.useK));
                //// 방문처리
                visit[nx][ny][now.useK]=true;
            }
        }
        // BFS 종료
        return -1;
    }
}
