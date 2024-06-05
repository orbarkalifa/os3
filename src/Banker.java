import java.util.*;

public class Banker {
    int[] Resource;
    static int[][] allocation = {
            {1, 0, 2, 1, 1},
            {2, 0, 1, 1, 0},
            {1, 1, 0, 1, 0},
            {1, 1, 1, 1, 0}
    };
    static int[][] claim = {
            {1, 1, 2, 1, 3},
            {2, 2, 2, 1, 0},
            {2, 1, 3, 1, 0},
            {1, 1, 2, 2, 1},
    };
    static int[] available = {0, 0, 1, 1, 2};

//    public Banker(int Pn,int Rn) {
//        Scanner input = new Scanner(System.in);
//        allocation = new int[Pn][Rn];
//        available = new int[Rn];
//        claim = new int[Pn][Rn];
//        System.out.println("fill allocation table");
//        for (int i = 0; i < Pn; i++) {
//            for (int j = 0; j < Rn; j++) {
//                System.out.printf("enter the amount of resource %d in process %d", j + 1, i + 1);
//                int value = input.nextInt();
//                while (value < 0) {
//                    System.out.println("negative value try again");
//                    value = input.nextInt();
//                }
//                allocation[i][j] = value;
//            }
//        }
//        System.out.println("fill available table");
//        for (int j = 0; j < Rn; j++) {
//            System.out.printf("enter the amount of resource %d", j + 1);
//            int value = input.nextInt();
//            while (value < 0) {
//                System.out.println("negative value try again");
//                value = input.nextInt();
//            }
//            available[j] = value;
//        }
//        System.out.println("fill claim table");
//        for (int i = 0; i < Pn; i++) {
//            for (int j = 0; j < Rn; j++) {
//                System.out.printf("enter the amount of resource %d in process %d", j + 1, i + 1);
//                int value = input.nextInt();
//                while (value < 0) {
//                    System.out.println("negative value try again");
//                    value = input.nextInt();
//                }
//                claim[i][j] = value;
//            }
//        }
//    }

    public static void findDeadlock() {
        boolean flag = false;

        ArrayList<String> done = new ArrayList<>();
        int[] w = available.clone();


        int[][] Q = new int[claim.length][claim[0].length];
        for (int i = 0; i < claim.length; i++) {
            for (int j = 0; j < claim[i].length; j++) {
                Q[i][j] = claim[i][j] - allocation[i][j];
            }
        }

        for (int i = 0; i < claim.length; i++) {
            int[] current = Q[i].clone();
            for (int j = 0; j < w.length; j++) {
                if (!(w[j] >= current[j]) || done.contains("process " + (i + 1))) {
                    flag = false;
                    break;
                }
                flag = true;
            }
            if (flag) {
                done.add("process " + (i + 1));
                for (int k = 0; k < allocation[i].length; k++) {
                    Q[i][k] = 0;
                    w[k] += allocation[i][k];
                }
                i = -1;
            }
        }


        if (done.size() == allocation.length) {
            System.out.println("safe");
            return;
        }
        System.out.println("Deadlock! ");
        for (int i = 0; i < allocation.length; i++)
            if (!done.contains("process " + (i + 1))) System.out.println("process " + (i + 1));
    }
    public static void findMinAmount(int n){
        int max=0;
        for (int[] ints : claim) max = Math.max(max, ints[n - 1]);
        System.out.printf("The minimum amount of resource %d is %d\n",n,max);
    }
    public static void checkRequest(int n,int[] request) {
            int[] current = claim[n - 1].clone();
            for(int i=0;i<current.length;i++)
                current[i] -= allocation[n-1][i];
            for (int j = 0; j < request.length; j++) {
                if (!(request[j] >= current[j])) {
                    System.out.println("Request cannot be approved\n");
                    return;
                }
            }
        System.out.println("Request can be approved\n");
    }
    public static void main(String[] args) {


        findMinAmount(3);
        checkRequest(1, new int[]{0, 0, 1, 1, 1});
        Banker.findDeadlock();

    }


}