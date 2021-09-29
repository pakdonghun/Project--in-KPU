package test1;

import java.util.Scanner;
import java.util.Arrays;

public class Parkingsystem { //Ŭ����
    public static void main(String[] args) { //�޼ҵ�
        @SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
        System.out.println("/* ���� ���� ���α׷� */\n\n");
        System.out.println("�ٹ����� �̸��� �Է��ϼ��� : ");
        String name = input.next();
        
        @SuppressWarnings("unused")
		Parking parking = new Parking(name);
    }
}

class Parking{
    String name;
    int[][] parkingAreaSize;
    final static int NUM = 0;
    final static int NAME = 1;
    final static int MAX_P = 10;
    private boolean parkingFlag;
    private String carOwner = "";
    private static int row_idx = 0, column_idx = 0;
    private int row, column;
    private String[][][] parked;
    private String[][] registered = {{"1111","Kim"},{"2222","Choi"},{"3333","Seok"},{"4444","Joo"},{"5555","Park"}};
    Scanner input = new Scanner(System.in);
    
    
    
    public Parking(String name){                //init 
        this.name = name;
        System.out.println("������ ũ��(��,��)�� �Է��ϼ��� : ");
        row = input.nextInt();
        column = input.nextInt();
        
        parkingAreaSize = new int[row][column]; //�� x �� �� �������� ����
        parked = new String[row][column][2];    //�� ��ȣ ������ ���� �迭 ����
        for(int i = 0; i < row; i++){           //��� ���� �������� �ʱ�ȭ
            for(int j = 0; j < column; j++){
                Arrays.fill(parked[i][j]," ");
            }
        }
        
        System.out.println(name + "�ٹ��� �����մϴ�");
        ParkingSystem(); //start
    }
    //[{����ȣ,�̸�}] [!!!] [] []
    //parking�������� �� �ڸ���index����, ���ÿ� ���� ���ڸ����� ���ڸ� ����Ž�� �� ����� index ����
    public boolean isParked(String carNum){ 
        parkingFlag = false;
        
        for(int i = 0; i < row; i++){
            for(int j = 0 ; j < column; j++){
                if(parked[i][j][NUM].equals(carNum)){
                    carOwner = parked[i][j][NAME];
                    row_idx = i;
                    column_idx = j;
                    return true;
                }
                else if ((parked[i][j][NUM].equals(" ")) && (parkingFlag == false)){
                    row_idx = i;
                    column_idx = j;
                    parkingFlag = true;
                }
            }
        }
        return false;
    }
    
    //��� ���ִ��� �Ǵ� -> 2���� �迭 ����Ž������ �˻� 
    public boolean isRegistered(String carNum){
        for(int i = 0; i < registered.length; i++){
            if (registered[i][NUM].equals(carNum)){
                carOwner = registered[i][NAME];
                return true;
            }
        }
        return false;
    }
    
    
    // Main 
    private void ParkingSystem(){
        while (true){
            printParkingArea();
            
            String inputCarInfo = inputCarInfo();
            if(inputCarInfo.equals("���")){
                System.out.println(name + "����մϴ�");
                break;
            }
            
            if(isRegistered(inputCarInfo) == true){      
                if(isParked(inputCarInfo) == true){
                    exit(row_idx,column_idx);
                }
                else{
                    if (parkingFlag == false){
                        printFull();
                    }
                    else{
                        enter(row_idx,column_idx,inputCarInfo);
                    }
                }
            }
            else{
                printUnRegistered();
            }
        }
    }
    
    // ��� �ȵ� �� 
    public void printUnRegistered(){
        System.out.println("��ϵ��� ���� �����Դϴ�");
    }
    
    // ����
    public void enter(int row_idx, int column_idx, String inputCarInfo){
        parked[row_idx][column_idx][NUM] = inputCarInfo;
        parked[row_idx][column_idx][NAME] = carOwner;
        System.out.println(parked[row_idx][column_idx][NUM] + "�� ���� (" + row_idx + "," + column_idx + ")�� �����߽��ϴ�.");
                    
    }
    
    // ����
    public void printFull(){
        System.out.println("�����Դϴ�");
    }
    
    // ���� 
    public void exit(int row_idx, int column_idx){
        System.out.println("����" + parked[row_idx][column_idx][NAME] + "," +parked[row_idx][column_idx][NUM] +"�� ���� �����߽��ϴ�.");
        parked[row_idx][column_idx][NUM] =" ";
        parked[row_idx][column_idx][NAME] =" ";
        row_idx = 0;
        column_idx = 0;        
    }
    
    // �������� ����Լ� 
    public void printParkingArea(){
        for(int i = 0 ; i < row; i++){
            for(int j = 0 ; j < column; j++){
                String carNum = (parked[i][j][NUM] != " ") ? parked[i][j][NUM] + "(" + parked[i][j][NAME] + ")" : "_____";
                System.out.print("(" + i + "," + j + ") : " + carNum + "   ");
            }
            System.out.println("");
        }
    }
    
    //���� ��ȣ�Է� 
    public String inputCarInfo(){
        System.out.println("���� ��ȣ�� �Է��ϼ���");
        String carInfo = input.next();
        
        return carInfo;
    }
}