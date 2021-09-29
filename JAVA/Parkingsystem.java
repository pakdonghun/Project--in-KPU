package test1;

import java.util.Scanner;
import java.util.Arrays;

public class Parkingsystem { //클래스
    public static void main(String[] args) { //메소드
        @SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
        System.out.println("/* 주차 정산 프로그램 */\n\n");
        System.out.println("근무자의 이름을 입력하세요 : ");
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
        System.out.println("주차장 크기(행,렬)을 입력하세요 : ");
        row = input.nextInt();
        column = input.nextInt();
        
        parkingAreaSize = new int[row][column]; //행 x 열 의 주차공간 생성
        parked = new String[row][column][2];    //차 번호 저장을 위한 배열 생성
        for(int i = 0; i < row; i++){           //모든 원소 공백으로 초기화
            for(int j = 0; j < column; j++){
                Arrays.fill(parked[i][j]," ");
            }
        }
        
        System.out.println(name + "근무를 시작합니다");
        ParkingSystem(); //start
    }
    //[{차번호,이름}] [!!!] [] []
    //parking돼있으면 그 자리의index저장, 동시에 가장 앞자리부터 빈자리 완전탐색 후 빈공간 index 저장
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
    
    //등록 돼있는지 판단 -> 2차원 배열 완전탐색으로 검색 
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
            if(inputCarInfo.equals("퇴근")){
                System.out.println(name + "퇴근합니다");
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
    
    // 등록 안될 때 
    public void printUnRegistered(){
        System.out.println("등록되지 않은 차량입니다");
    }
    
    // 주차
    public void enter(int row_idx, int column_idx, String inputCarInfo){
        parked[row_idx][column_idx][NUM] = inputCarInfo;
        parked[row_idx][column_idx][NAME] = carOwner;
        System.out.println(parked[row_idx][column_idx][NUM] + "번 차량 (" + row_idx + "," + column_idx + ")에 주차했습니다.");
                    
    }
    
    // 만석
    public void printFull(){
        System.out.println("만차입니다");
    }
    
    // 출차 
    public void exit(int row_idx, int column_idx){
        System.out.println("차주" + parked[row_idx][column_idx][NAME] + "," +parked[row_idx][column_idx][NUM] +"번 차량 출차했습니다.");
        parked[row_idx][column_idx][NUM] =" ";
        parked[row_idx][column_idx][NAME] =" ";
        row_idx = 0;
        column_idx = 0;        
    }
    
    // 주차공간 출력함수 
    public void printParkingArea(){
        for(int i = 0 ; i < row; i++){
            for(int j = 0 ; j < column; j++){
                String carNum = (parked[i][j][NUM] != " ") ? parked[i][j][NUM] + "(" + parked[i][j][NAME] + ")" : "_____";
                System.out.print("(" + i + "," + j + ") : " + carNum + "   ");
            }
            System.out.println("");
        }
    }
    
    //차량 번호입력 
    public String inputCarInfo(){
        System.out.println("차량 번호를 입력하세요");
        String carInfo = input.next();
        
        return carInfo;
    }
}