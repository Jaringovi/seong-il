import java.util.Arrays;
import java.util.Scanner;

public class BOJ_1417_국회의원_선거 {
    public static void main(String[] args) {
    	// 입력
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] hubo = new int[n];
        for(int i=0;i<n;i++) {
        	hubo[i] = scanner.nextInt();
        }
        
        
    	// 주인공 득표수 체크
        int vote1 = hubo[0];
        // 적들 수 체크 --> 적들 인덱스를 관리할 배열의 크기를 위해
        int counter = 0; //--> 자신도 적에 포함
        for(int i: hubo) {
        	if (vote1 <=i) {
        		counter++;
        	}
        }
//        System.out.println(counter);
        
        // 적들(인덱스) 관리 배열 생성
        int[] juk = new int[counter];
        int insertI = 0;
        for(int i=0;i<n;i++) {
        	if (vote1 <=hubo[i]) {
        		juk[insertI++] = i;
        	}
        }
//        for(int i:juk) {
//            System.out.print(i+" ");
//        }
        
        
        // 누가 제일 높은지 확인 -> 해당 사람과 +-해서 올리기 ->0번이 제일 높을때까지.
        int buyCount = 0; // 매수횟수
        for(int never = 0;never<1;never--) { // 어차피 탈출은 break;
//        	System.out.println(Arrays.toString(hubo));
            // 최고 찾음
            int max = hubo[juk[0]]; // 기본 초기화
            int maxIndex = 0; // 인덱스 기본 초기화
            for (int i = 0; i < juk.length; i++) {
            	int jukIndexInHubo=juk[i];
                if (hubo[jukIndexInHubo] > max) {
                    max = hubo[jukIndexInHubo]; //최대값 찾기 위해 기록
                    maxIndex = jukIndexInHubo; // 인덱스 기록 (정말 필요한 사실)
                }
            }
            // 최고가 누구냐로 처리여부 결정
            if(maxIndex==0) { // 주인공 당선    
            	// 동표자 존재 여부 확인
//            	int sameVote = 0;
            	int sameVote = -1; 	// -1이 아니고 0으로 초기화해서 문제였음. 
            						// 아래 반복문을 인덱스로 한게 아니라서 처음에 주인공도 무조건 동표로 카운팅 됐다...
            	for(int i : hubo) {
            		if(i==hubo[0]) {
            			sameVote++;
            		}
            	}
            	if(sameVote!=0) {
            		buyCount++;
//            		System.out.println(buyCount+"범인1");
            		System.out.println(buyCount);
            		break;
            	}
            	else {
//            		System.out.println(buyCount+"범인2");
            		System.out.println(buyCount);
                	break;
            	}
            	
            }else { // 주인공 낙선. 매수 시도
            	hubo[0]++;
            	hubo[maxIndex]--;
            	buyCount++;
            }
//        	System.out.println(buyCount);
//        	System.out.println(Arrays.toString(hubo));
            // 위에를 반복문에 넣으세요
        }

    }

}
