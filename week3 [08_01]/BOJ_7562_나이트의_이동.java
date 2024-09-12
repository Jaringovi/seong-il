package study.week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// TRY2의 목표
// TRY1에서 스택오버플로우가 발생 -> 재귀함수가 원인 -> 반복문으로 수정

public class BOJ_7562_2 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int [][] matrix;
	static int [][] visited;
	static int [] end; // 목적지
	static int n; // 보드 한변의 크기
	
	// q.offer(value); 큐의 맨 끝에 요소를 추가한다. 큐가 가득 찬 경우 요소를 추가하지 않고 false를 반환시킨다.
	// q.poll() : 큐의 맨 앞에서 요소를 제거하고 반환한다. 큐가 비어있는 경우 null을 반환한다.
	static Queue<Integer> queueRow; // 큐 초기화를 스태틱에서 하니까, 런타임에서 테케마다 초기화하는걸 까먹었음 ㅋㅋ.. 잊지말자!
	static Queue<Integer> queueCol; // 큐 초기화를 스태틱에서 하니까, 런타임에서 테케마다 초기화하는걸 까먹었음 ㅋㅋ.. 잊지말자!
	static Queue<Integer> queueDepth; // 큐 초기화를 스태틱에서 하니까, 런타임에서 테케마다 초기화하는걸 까먹었음 ㅋㅋ.. 잊지말자!
	
	// 나이트 이동패턴 12시 기준 시계방향
	static int[][] generateNext(int[] node) { // {row,col,depth}
		int row = node[0];
		int col = node[1];
		int depth = node[2];
		int[][] nextNodes= {{row-2,col+1,depth},{row-1,col+2,depth},  {row+1,col+2,depth},{row+2,col+1,depth},  
				{row+2,col-1,depth},{row+1,col-2,depth},  {row-1,col-2,depth},{row-2,col-1,depth}}; 
		return nextNodes;
	}
	// 범위 필터
	static boolean verifyRange(int[] node){
		int row = node[0];
		int col = node[1];
		if((0<=row&&row<n)&&(0<=col&&col<n)) {			
			return true;
		}
		return false;
	}
	// 인큐
	static void queueOffer(int[] node) {
		queueRow.offer(node[0]);
		queueCol.offer(node[1]);
		queueDepth.offer(node[2]+1); // 깊이 관리하는 로직
	}
	// 디큐
	static int[] queuePoll() {
		int[] node = {queueRow.poll(),queueCol.poll(),queueDepth.poll()};
		return node;
	}
	// 방문확인
	static boolean visitedCheck(int[] node) { // 방문했으면 true, 미방문 false
		if(visited[node[0]][node[1]]==1) {
			return true;
		}
		else {
			return false;			
		}
	}
	// 방문
	static void visit(int[] node) { // 방문했으면 true, 미방문 false
		visited[node[0]][node[1]]=1; 
	}
	// 목적지 도착 확인
	static boolean ending(int[] node) {
		if(end[0]==node[0] && end[1]==node[1]) {
//			System.out.println(Arrays.toString(node));
			return true;
		}
		return false;
	}
	
	// 디버그용
	
	
	// BFS
	static void bfs() {
		for(;;) {
			if(!queueRow.isEmpty()) { // 큐 안 비었음
				int[] node = queuePoll(); // 디큐
//				System.out.println(Arrays.toString(node));
				if(ending(node)){ // 목적지 도착확인
					System.out.println(node[2]-1); // 정답 프린트 -> 처음에 이니시할때 0에서 자동으로 1 더해줘서 끝에서 1뺌
					break;
				}
				if(!visitedCheck(node)) { // 미방문 -> 작업 -> 방문 깃발꽂기
					visit(node); // 방문 깃발꽂기
					for(int[] nextNode: generateNext(node)) { // 현재 선택된 노드를 기준으로 나이트 이동패턴 경로 생성
						if(verifyRange(nextNode)) { // 이동패턴을 개별적으로 범위 검증 -> true 반환 -> 범위 이내 -> 큐에 인큐
							queueOffer(nextNode);
						}
					}
				} 
//				else { // 방문 -> 패스
//				}
//				bfs(); // bfs 반복
			} else { // 큐 비었음
				break; // bfs 종료
			}	
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		int T = Integer.parseInt(input.readLine());
		for(int t=0;t<T;t++) { // 테케 순회
			n = Integer.parseInt(input.readLine()); // 보드 크기
			matrix = new int[n][n]; // 보드 초기화
			visited = new int[n][n]; // 방문확인용 보드 초기화
			
			tokens = new StringTokenizer(input.readLine());
			int[] start = {Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),0};
			
			tokens = new StringTokenizer(input.readLine());
			end = new int[] {Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken()),0};
			
			queueRow = new LinkedList<Integer>();
			queueCol = new LinkedList<Integer>();
			queueDepth = new LinkedList<Integer>();
			
			queueOffer(start); // 이니시에이트
			bfs();
		}
	}
}
