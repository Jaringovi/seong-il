package study.week18;
import java.io.*;
import java.util.*;
public class BOJ_1713_후보추천하기 {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static StringBuilder output = new StringBuilder();
	// 세팅
	static int N;
	static int total;
	static int[] querys;
	
	static class Candidate{
		int like;
		int birth;
		Candidate(int like, int birth){
			this.like = like;
			this.birth = birth;
		}
	}
	
	static Map<Integer, Candidate> candis;
	static int birthDay;
	// 메소드
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력
		N = Integer.parseInt(input.readLine());
		total = Integer.parseInt(input.readLine());
		tokens = new StringTokenizer(input.readLine());
		querys = new int[total];
		for (int i = 0; i < total; i++) {
			querys[i] = Integer.parseInt(tokens.nextToken());
		}
		// 세팅
		candis = new HashMap<>();
		// 작업
		for (int i = 0; i < total; i++) {
//			System.out.println(candis.keySet());
			int query = querys[i];
			// 해당 키값이 이미 있으니까 like만 ++ 해주자.
			if(candis.containsKey(query)) {
				candis.get(query).like++;
				continue;
			}
			// 해당 키값이 Map에 없고, 여유있으니까 그냥 넣음
			if(candis.size()<N) {
				candis.put(query, new Candidate(1, birthDay++));
				continue; // 넣었으니까 바로 다음 query로 넘어가기. => else문 안쓰기 위함.
			} 
			// 해당 키값이 Map에 없고 Map에 여유도 없음;
			//// 하나 삭제하고 추가해야함. values 꺼내서 정렬함. 맨 마지막 항목의 birth를 ID로 사용
			Collection<Candidate> values = candis.values();
			List<Candidate> sortedValues = new ArrayList<>(values);
			sortedValues.sort(Comparator
				.comparing((Candidate candidate) -> candidate.like)
				.thenComparing((Candidate candidate) -> candidate.birth)
			);
			Candidate targetCandi = sortedValues.get(0);
			int targetBirth = targetCandi.birth; // birth는 AutoIncrement 값으로 설계
			int targetKey = 0;
			// 삭제
      for (Map.Entry<Integer, Candidate> entry : candis.entrySet()) {
          int key = entry.getKey();
          Candidate candidate = entry.getValue();
          if (candidate.birth == targetBirth) {
              targetKey = key;
              break;
          }
      }
      // 삭제 후 새 후보 추가
      candis.remove(targetKey);
      candis.put(query, new Candidate(1, birthDay++));

		}
		//// keySet 정렬
		List<Integer> sortedKeys = new ArrayList<>(candis.keySet());
		Collections.sort(sortedKeys);
		for (int key: sortedKeys) {
			output.append(key).append(" ");
		}
		// 출력
		System.out.println(output);
	}

}
