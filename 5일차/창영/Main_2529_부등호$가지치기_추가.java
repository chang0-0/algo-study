import java.util.*;
import java.io.*;

public class Main_2529_부등호$가지치기_추가 {
    static int K;
    static int[] ans;
    static boolean[] visit = new boolean[10];
    static char[] inequalitySignArr;
    static String maxResult = String.valueOf(Long.MIN_VALUE);
    static String minResult = String.valueOf(Long.MAX_VALUE);

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/2529.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        K = Integer.parseInt(br.readLine());
        inequalitySignArr = new char[K];
        // 숫자를 담는 배열 크기는 부등호 배열 크기 + 1
        ans = new int[K + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            inequalitySignArr[i] = st.nextToken().charAt(0);
        }

        DFS(0);
        System.out.println(maxResult);
        System.out.println(minResult);
    } // End of main

    private static void DFS(int depth) {
        if (depth == K + 1) { // 숫자가 기준이므로 K+1 이전 단계에서 이미 가지치기를 통해서 만족하는 조건을 검사하였으므로, 따로 체크 할 필요 X
            // 가능성 통과 후 문자열 만들기
            if (!PossibleCheck(ans[depth - 2], ans[depth - 1], inequalitySignArr[depth - 2])) {
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= K; i++) {
                sb.append(ans[i]);
            }

            // 만들어진 숫자로 최대값 비교
            String str = sb.toString();
            if (Long.parseLong(maxResult) < Long.parseLong(str)) {
                maxResult = str;
            }

            if (Long.parseLong(minResult) > Long.parseLong(str)) {
                minResult = str;
            }
            return;
        } else if (depth >= 2) { // 가지치기 -> 중간중간 마다 가능성을 체크해서 틀린곳은 제외를 한다.
            if (!PossibleCheck(ans[depth - 2], ans[depth - 1], inequalitySignArr[depth - 2])) {
                return;
            }
        }

        for (int i = 0; i < 10; i++) { // 0부터 9까지 중복이 없는 순열 조합 만들기
            if (visit[i]) continue;

            visit[i] = true;
            ans[depth] = i;
            DFS(depth + 1);
            visit[i] = false;
        }
    } // End of DFS

    private static boolean PossibleCheck(int num1, int num2, char inequalitySign) {
        // 만들어진 조합 ans 배열과, inequalitySignArr의 배열을 나열 해서 가능한 조합인지 확인하고,
        // 가능할 경우에는 만들어진 숫자의 값을 return;
        if (inequalitySign == '>') {
            if (num1 < num2) {
                return false;
            }
        } else if (inequalitySign == '<') {
            if (num1 > num2) {
                return false;
            }
        }

        return true;
    } // End of PossibleCheck
} // End of Main class