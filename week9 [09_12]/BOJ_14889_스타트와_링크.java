// 전략! 20명에서 10명을 조합으로 뽑아서 팀을 만들고
// 팀 조합 만들어질때마다 상대 팀 조합도 임시로 만든다.
// 각 팀의 시너지를 구하는 메소드는 10명중에 2명을 중복순열로 뽑는 메소드로 구현한다.

import java.io.*;
import java.util.*;

public class Main {
	// 입력고정
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	// 세팅
	static int N;
	static int[][] origin;
	static int[] team;
	static int[] rival;
	static int[] playerPool;
	
	static int[] synergyPair = new int[2];
	static int teamSynergy;  // 사이클마다 초기화
	static int rivalSynergy; // 사이클마다 초기화
	static int mini = Integer.MAX_VALUE;
	
	// 메소드
	//// 조합 생성
	static void makeCombi(int[] leaf, int nowDigit, final int targetDigit, final int pool, int start) {
		// 바닥 조건
		if(nowDigit >= targetDigit) {
			// 작업 추가
//			System.out.println(Arrays.toString(leaf));
			// team 결정된 상태, rival 생성
			makeRival();
			// 팀별 시너지 계산, 계산전 시너지 총계 초기화
			teamSynergy =0;
			rivalSynergy =0;
			makePI(synergyPair, 0, 2, team, 1);
			makePI(synergyPair, 0, 2, rival, 2);
			// 팀별 시너지 차이값 계산 후 최소값 업데이트
			int gap = Math.abs(teamSynergy - rivalSynergy);
			mini = Math.min(mini, gap);
			return;
		}
		
		// 재귀 파트
		for (int i = start; i < pool+1; i++) {
			leaf[nowDigit] = i;
			makeCombi(leaf, nowDigit+1, targetDigit, pool, i+1);
		}
		
	}
	
	//// 상대팀 만들기
	static void makeRival() {
		// playerPool 초기화
		for (int i = 0; i < playerPool.length; i++) {
			playerPool[i]=i;
		}
		// team 지우기
		for (int i = 0; i < team.length; i++) {
			int del = team[i];
			playerPool[del] =0;
		}
		// playerPool 정렬후 클로닝
		Arrays.sort(playerPool);
		rival = Arrays.copyOfRange(playerPool, (N/2)+1, N+1); // 포문으로 값만 바꾸면 최적화
	}
	
	//// 중복순열로 팀의 시너지 계산
	static void makePI(int[] leaf, int nowDigit, final int targetDigit, final int[] poolTeam, int indicator) {
		// 바닥 조건
		if(nowDigit>= targetDigit) {
			// 추가 작업
			if(indicator ==1) {
				teamSynergy += origin[leaf[0]-1][leaf[1]-1];
			} else if(indicator ==2) {
				rivalSynergy += origin[leaf[0]-1][leaf[1]-1];
			}
			
			return;
		}
		// 재귀 파트
		for (int i = 0; i < poolTeam.length; i++) {
			leaf[nowDigit] = poolTeam[i];
			makePI(leaf, nowDigit+1, targetDigit, poolTeam, indicator);
		}
	}
	
	
	// 메인
	public static void main(String[] args) throws Exception {
		// 입력부
		N = Integer.parseInt(input.readLine());
		origin = new int [N][N];
		for(int row =0; row<N; row++) {
			tokens = new StringTokenizer(input.readLine());
			for(int col =0; col<N; col++) {
				origin[row][col] = Integer.parseInt(tokens.nextToken());
			}
		}
//		 세팅부
		team = new int[N/2];
		rival = new int[N/2];
		playerPool = new int[N+1];
		// 작업부
//		makeCombi(new int[2], 0, 2, 4, 1);
//		makeCombi(new int[10], 0, 10, 20, 1);
		makeCombi(team, 0, N/2, N, 1);

		// 출력부
		System.out.println(mini);
	}

}
