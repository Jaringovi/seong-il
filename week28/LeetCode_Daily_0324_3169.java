class Solution {
    public int countDays(int days, int[][] meetings) {
        int result = 0;
        // 날짜차이 큰 순으로 정렬 => 최적화 위함.
        // 포함되거나 연결되면 병합됨
        // 리스트 순회하면서 
            // 새로 들어오는 미팅기간 확인해서 병합(연결)되는지 확인.
                // 병합(연결)될때마다 합치면, 새로 들어온 미팅기간이 중간 연결점으로 3개가 연결되는지 확인하기 위해서
                // 한번 더 리스트를 순회하면서 서로 합쳐지는지 확인해야 함.
                // 하지만 리스트를 다 돌고, 병합(연결)되는 애들을 전부 임시 리스트에 담아서, 거기서 하나로 합치면
                // 연쇄적으로 병합(연결)되는 것을 한번의 순회로 커버 가능함.
                // 기간이 긴 순서대로 정렬했기 때문에 새로 들어오는 미팅 기간이 연쇄적으로 병합(연결)시킬수 있는 개수는
                // 최대가 3개가 됨. 정렬을 안했다면 여러개가 됨. 
                    // => 갭+기존에 들어온 미팅기간> 새로 들어온 미팅기간
                // 병합(연결)때 넣었다 뺐다 해야되니까 set을 쓰자.
        // 모든 미팅기간을 넣었다면 미팅Set을 순회하면서 미팅날짜를 구해서 days에서 빼주자.


        // meetings를 리스트화하자.
        List<List<Integer>> meetingsList = new ArrayList<>();
        for(int i=0; i<meetings.length; i++){
            List<Integer> temp = new ArrayList<>();
            for(int j=0; j<meetings[i].length; j++){
                temp.add(meetings[i][j]);
            }
            meetingsList.add(temp);
        }
        // meetings를 정렬하자.
        meetingsList.sort(Comparator.comparingInt( (List<Integer> o) -> (o.get(1) - o.get(0)) ).reversed());

        // 병합된 미팅들 관리하는 Set
        Set<List<Integer>> meetingsSet = new HashSet<>();
        meetingsSet.add(meetingsList.get(0));

        // meeting 전부 넣기
        for(int i=0+1; i<meetingsList.size(); i++){
            // temp
            List<Integer> temp = new ArrayList<>();

            List<Integer> now = meetingsList.get(i);
            
            // meetingsSet 순회하기
            Iterator<List<Integer>> iterator = meetingsSet.iterator();
            boolean toggle = false;
            while(iterator.hasNext()){
                List<Integer> already = iterator.next();
                // 포함 확인 => 그냥 아무일도 안하고 다음으로
                toggle = false;
                if( already.get(0) <= now.get(0) && now.get(1) <= already.get(1)){
                    toggle = true;
                    break;
                }
                // 연결 확인 => 앞뒤로 거리가 1씩 차이나는지 확인.
                else if( Math.abs(already.get(0)-now.get(1)) <=1 || Math.abs(already.get(1)-now.get(0)) <=1 ){
                    temp.add(already.get(0));
                    temp.add(already.get(1));
                    temp.add(now.get(0));
                    temp.add(now.get(1));
                    iterator.remove(); // set크기 줄여야함.
                    if(temp.size()==8){ // 구조적으로 크기는 최대 8임
                        break;
                    }
                }
                // 디버깅 포인트. 연결 확인 2 => 중간에 걸치는지 확인 필요.
                else if( 
                    (already.get(0) <= now.get(1) && already.get(1) > now.get(0))
                    || 
                    (now.get(0) <= already.get(1) && now.get(1) > already.get(0) ) ){
                    temp.add(already.get(0));
                    temp.add(already.get(1));
                    temp.add(now.get(0));
                    temp.add(now.get(1));
                    iterator.remove(); // set크기 줄여야함.
                    if(temp.size()==8){ // 구조적으로 크기는 최대 8임
                        break;
                    }
                }
            }
            // meetingsSet 순회 끝
            // 비어있으면 아무것도 안하고, 뭐 있으면 새로 만들어서 추가해줘야지.
            // 디버깅 포인트. 비어있으면 연결점이 없거나 포함이 안되거나. 
            // 포함했는지 상태를 저장하는 변수 추가해서. 연결점이 없을때는 여기서 새롭게 추가해야 함.
            if(temp.isEmpty()){
                if(toggle==false){ // 포함이 아니다.
                    meetingsSet.add(now);
                }
                continue;
            }
            
            // 최대 최소 구해서 새롭게 meetingsSet 추가
            int max = temp.get(0);
            int min = temp.get(0);
            for(Integer value : temp){
                max = Math.max(max, value);
                min = Math.min(min, value);
            }
            List<Integer> insert = new ArrayList<>();
            insert.add(min);
            insert.add(max);
            meetingsSet.add(insert);
        }

        // 날짜 계산
        for(List<Integer> meeting : meetingsSet){
            // System.out.println(meeting.toString());
            result += meeting.get(1)-meeting.get(0)+1;
        }

        return days-result;
    }
}
