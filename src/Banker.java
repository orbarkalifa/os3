// Or Bar Califa 318279429
// Daniel Fradkin 316410885

import java.util.*;

public class Banker {
    int[][] allocation;
    int[][] claim;
    int[] available;


    public Banker(int Pn, int Rn) {
        //first init based on parameters process number and recourse number
        Scanner input = new Scanner(System.in);
        allocation = new int[Pn][Rn];
        available = new int[Rn];
        claim = new int[Pn][Rn];
        //init allocation matrix
        System.out.println("fill allocation table");
        for (int i = 0; i < Pn; i++) {
            for (int j = 0; j < Rn; j++) {
                System.out.printf("enter the amount of resource %d in process %d", j + 1, i + 1);
                int value = input.nextInt();
                while (value < 0) {
                    System.out.println("negative value try again");
                    value = input.nextInt();
                }
                allocation[i][j] = value;
            }
        }
        //init available array
        System.out.println("fill available table");
        for (int j = 0; j < Rn; j++) {
            System.out.printf("enter the amount of resource %d", j + 1);
            int value = input.nextInt();
            while (value < 0) {
                System.out.println("negative value try again");
                value = input.nextInt();
            }
            available[j] = value;
        }
        //init claim matrix
        System.out.println("fill claim table");
        for (int i = 0; i < Pn; i++) {
            for (int j = 0; j < Rn; j++) {
                System.out.printf("enter the amount of resource %d in process %d", j + 1, i + 1);
                int value = input.nextInt();
                while (value < 0) {
                    System.out.println("negative value try again");
                    value = input.nextInt();
                }
                claim[i][j] = value;
            }
        }
    }

    public void findDeadlock() {
        //flag to check that the whole vector fits the condition
        boolean flag = false;
        // list of done process
        ArrayList<String> done = new ArrayList<>();
        //copy of available as part of prev
        int[] w = available.clone();
        //need matrix init
        int[][] Q = new int[claim.length][claim[0].length];
        for (int i = 0; i < claim.length; i++) {
            for (int j = 0; j < claim[i].length; j++) {
                Q[i][j] = claim[i][j] - allocation[i][j];
            }
        }
        //current takes process i and checks if the w is bigger in every cell
        for (int i = 0; i < claim.length; i++) {
            int[] current = Q[i].clone();
            for (int j = 0; j < w.length; j++) {
                if ((w[j] < current[j]) || done.contains("process " + (i + 1))) {
                    //failed condition or done don't need to count change flag to false
                    flag = false;
                    break;
                }
                flag = true;
            }
            if (flag) {
                //success add to done and take resources back
                done.add("process " + (i + 1));
                for (int k = 0; k < allocation[i].length; k++) {
                    Q[i][k] = 0;
                    w[k] += allocation[i][k];
                }
                //start the index of first loop back to -1 to check all processes after change
                i = -1;
            }
        }
        //check if safe
        if (done.size() == allocation.length) {
            System.out.println("safe");
            return;
        }
        //didn't pass not safe prints everybody who isn't in done
        System.out.println("Deadlock! ");
        for (int i = 0; i < allocation.length; i++)
            if (!done.contains("process " + (i + 1))) System.out.println("process " + (i + 1));
    }

    public void findMinAmount(int n) {

        int max = 0;
        //loop runs on claim and check the highest demand for recourse n
        for (int[] ins : claim) max = Math.max(max, ins[n - 1]);
        System.out.printf("The minimum amount of resource %d is %d\n", n, max);
    }

    public void checkRequest(int n, int[] request) {
        // need takes process i and checks if the request is bigger in every cell

        // init need array
        int[] need = claim[n - 1].clone();
        for (int i = 0; i < need.length; i++)
            need[i] -= allocation[n - 1][i];


        for (int i = 0; i < available.length; i++) {
            if (available[i] < request[i] || available[i] < need[i]) {
                System.out.println("Request can not be approved\n");
                return;
            }
        }
        //passed
        System.out.println("Request can be approved\n");
    }

    public static void main(String[] args) {
        //start getting inputs
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of processes ");
        int pn = input.nextInt();
        System.out.println("Enter the number of resources  ");
        int rn = input.nextInt();
        Banker program = new Banker(pn, rn);

        program.findMinAmount(3);
        program.checkRequest(1, new int[]{0, 0, 0, 0, 0});
        program.findDeadlock();

    }


}