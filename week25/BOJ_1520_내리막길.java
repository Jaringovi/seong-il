import java.io.*;
import java.util.*;

public class Main {
    static int M, N;
    static int[][] board, dp;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void BOJ_1520_내리막길 (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        
        board = new int[M][N];
        dp = new int[M][N];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1; // 아직 방문하지 않은 위치
            }
        }

        System.out.println(dfs(0, 0));
    }

    static int dfs(int x, int y) {
        // 도착지점에 도달하면 1을 반환
        if (x == M - 1 && y == N - 1) {
            return 1;
        }

        // 이미 계산한 위치라면 해당 값 반환
        if (dp[x][y] != -1) {
            return dp[x][y];
        }

        dp[x][y] = 0; // 현재 위치에서 시작하는 경로 개수 초기화

        // 상하좌우 이동
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < M && ny >= 0 && ny < N && board[nx][ny] < board[x][y]) {
                dp[x][y] += dfs(nx, ny);
            }
        }

        return dp[x][y];
    }
}
