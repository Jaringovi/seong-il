class Solution {
    public int minOperations(int[][] grid, int x) {
        // 각각의 cell을 X로 나눈 나머지 값이 전부 같으면 uni-value 가능
        // 각각의 cell을 X로 나눈 몫의 값의 평균이 모든 cell의 무게중심 => 최단거리.
        // 평균을 구할때 몫의 평균이 계산이 용이함.
        // 평균이  나누어 떨어지지 않을때. 
            // 최단거리가 되기 위해서 올림.내림.반올림 뭐가 더 좋을지에 대한 고민
            // 일단은 반올림.
        // 편차합을 답으로 제출


        // uni-value 가능성 검토.
        int remain = grid[0][0]%x;
        boolean impossible = false;
        for(int[] row : grid){
            for(int cell : row){
                if(cell%x != remain){
                    impossible = true;
                    break;
                }
            }
        }
        // 불가능함.
        if(impossible){
            return -1;
        }
        // 가능함.
        List<Integer> quotientList = new ArrayList<>();
        int quotientSum = 0; // 10^9이라 int로 충분
        for(int[] row : grid){
            for(int cell : row){
                int quotient = cell/x;
                quotientSum += quotient;
                quotientList.add(quotient);
            }
        }
        // 평균 구함.
        int avr = Math.round( (float)quotientSum/((float) grid.length * (float)grid[0].length));
        // 평균이 아니라 평균 근처의 값이 정답일수도 있음. 
        // 따라서 평균 인근의 값을 편하게 bruteForce 하기 위한 트릭
        int radius = 11; // 이 값만 수정하면 범위 조정 가능.
        List<Integer> avrList = new ArrayList<>();
        for(int i=-radius; i<=radius; i++){
            avrList.add(avr+i);
        }
        List<Integer> deviationSumList = new ArrayList<>();
        for(int i=0; i<avrList.size(); i++){
            deviationSumList.add(0);
        }

        // 여러개의 편차합 계산 
        for(Integer e : quotientList){
            for(int i=0; i<avrList.size(); i++){
                int oldValue = deviationSumList.get(i);
                int newValue = Math.abs(avrList.get(i)-e);
                deviationSumList.set(i, oldValue+newValue);
            }   
        }

        // 최소값 결정.
        int result = deviationSumList.get(0);
        for(Integer deviationSum : deviationSumList){
            result = Math.min(result,deviationSum);
        }
        return result;
    }
}
