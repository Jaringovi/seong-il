
class Solution {
    public int[] maxPoints(int[][] grid, int[] queries) {
        // 쿼리를 돌면서 BFS 탐색. 탐색해서 방문한 곳의 개수를 저장
        int[] results = new int[queries.length];
        Queue<int[]> q = new LinkedList<>();
        Set<Integer> visited = new HashSet<>(); // Set<hash>
        int r = grid.length;
        int c = grid[0].length;
        int[][] delta4 = { {0,1},{0,-1}, {1,0},{-1,0} };
        
        // 최적화 전처리
        Map<Integer, Integer> dp = new HashMap<>();

        TreeSet<Integer> sortedQueries = new TreeSet<>();
        for(int query : queries){
            sortedQueries.add(query);
        }
        // 임시 큐
        PriorityQueue<int[]> tempQ = new PriorityQueue<>(Comparator.comparingInt( (int[] o) -> (o[2]) )); // cell 값 작은순으로


        boolean firstToken = false;
        for(int query : sortedQueries){
            // 이전의 확장불가능 지점 꺼낼수 있는만큼 꺼내기
            while(tempQ.isEmpty()==false &&tempQ.peek()[2]<query){
                int[] tempNow = tempQ.poll();
                q.offer(tempNow);
                visited.add(tempNow[0]*c+tempNow[1]);
            }


            // BFS 시작
            // 조건부로 딱 한번 bfsQ 초기화
            if(grid[0][0]<query && !firstToken){
                q.offer(new int[] {0,0,grid[0][0]});
                visited.add(0);
                firstToken = true;
            }

            // 큐 탐색 시작
            while(q.isEmpty()==false){
                // 큐 꺼내기
                int[] now = q.poll();
                int row = now[0];
                int col = now[1];
                // 확장 검증
                for(int[] delta : delta4){
                    int nrow = row + delta[0];
                    int ncol = col + delta[1];

                    /// 인덱스 범위 검사 => 인덱스 아웃은 패스
                    if( (0<=nrow && nrow<r) && (0<=ncol && ncol<c) ){
                    } else{
                        continue;
                    }
                    /// 방문 여부 검사 => 방문한 곳은 패스
                    if(visited.contains(nrow*c+ncol)){
                        continue;
                    }
                    /// 문제 고유 조건 검사 대소 비교. => query 이하는 패스
                    if(grid[nrow][ncol]>=query){
                        // 확장 불가능 지점 저장
                        tempQ.offer(new int[] {nrow, ncol, grid[nrow][ncol]});
                        continue;
                    }
                    /// 확장
                    q.offer(new int[] {nrow, ncol, grid[nrow][ncol]});
                    /// 방문처리
                    visited.add(nrow*c+ncol);
                }
            }
            // 큐 다 빠짐.
            dp.put(query, visited.size());
        }
        // 모든 쿼리 종료 => 결과 채우기
        for(int i=0; i<queries.length; i++){
            int pushValue = dp.get(queries[i]);
            results[i] = pushValue;
        }

        return results;
    }
}
