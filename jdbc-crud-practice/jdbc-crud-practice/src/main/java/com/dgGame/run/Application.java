package com.dgGame.run;

import com.dgGame.model.dao.CharacterDAO;
import com.dgGame.model.dto.CharacterDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static com.dgGame.common.JDBCTemplate.close;
import static com.dgGame.common.JDBCTemplate.getConnection;

public class Application {

    public static void main(String[] args) {

        Connection con = getConnection();
        CharacterDAO registDAO = new CharacterDAO();
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("1. 캐릭터 조회");
            System.out.println("2. 전체 캐릭터 조회");
            System.out.println("3. 캐릭터 공격력 수정");
            System.out.println("4. 신규 캐릭터 등록");
            System.out.println("5. 캐릭터 삭제");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴를 선택해주세요. : ");
            int menuNo = sc.nextInt();
            sc.nextLine();

            switch(menuNo) {
                case 1 : // 1. 캐릭터 정보 조회(직업 연결)
                    System.out.print("조회할 캐릭터명을 입력해주세요 : ");
                    String charName = sc.nextLine();

                    CharacterDTO selChar = registDAO.selectCharacterInfo(charName, con);
                    System.out.println("selChar = " + selChar); close(con); break;

                case 2 : // 2. 전체 캐릭터 정보 조회(직업 연결)
                    List<CharacterDTO> charList = registDAO.selectAllCharacterList(con);

                    for(CharacterDTO allList : charList) {
                        System.out.println("allList = " + allList);
                    } close(con); break;

                case 3 : // 3. 캐릭터 공격력 수정
                    // 3-1. 공격력을 수정할 캐릭터 선택 및 수정값 입력
                    System.out.print("공격력을 수정할 캐릭터명을 입력하세요 : ");
                    String characterName = sc.nextLine();
                    System.out.print("수정할 공격력 수치를 입력하세요 : ");
                    int characterApoint = sc.nextInt();
                    sc.nextLine();

                    // 3-2. 해당 캐릭터 공격력 수정 값 저장
                    CharacterDTO udtChar = new CharacterDTO();
                    udtChar.setName(characterName);
                    udtChar.setApoint(characterApoint);

                    // 3-3. 해당 캐릭터 공격력 수정을 위한 메소드 호출 후 등록
                    int result = registDAO.updateCharacterApoint(udtChar, con);

                    if(result >0) {
                        System.out.println(characterName + " 공격력 수정 성공!");
                    } else {
                        System.out.println("공격력 수정 실패!");
                    } close(con); break;

                case 4 : // 4. 신규 캐릭터 등록
                    // 4-1. 신규 캐릭터 등록을 위한 정보 입력
                    System.out.print("등록할 캐릭터명을 입력하세요 : ");
                    String newCharacterName = sc.nextLine();
                    System.out.print("등록할 캐릭터의 레벨을 입력하세요 : ");
                    int newCharacterLevel = sc.nextInt();
                    System.out.print("등록할 캐릭터의 체력을 입력하세요 : ");
                    int newCharacterHp = sc.nextInt();
                    System.out.print("등록할 캐릭터의 공격력을 입력하세요 : ");
                    int newCharacterApoint = sc.nextInt();
                    System.out.print("등록할 캐릭터의 방어력을 입력하세요 : ");
                    int newCharacterDpoint = sc.nextInt();
                    System.out.print("등록할 캐릭터의 직업을 입력하세요(전사, 궁수, 마법사) : ");
                    sc.nextLine();
                    String newCharacterJob = sc.nextLine();

                    // 4-2. 신규 캐릭터 등록을 위한 값 가공
                    String jobId = "";
                    switch (newCharacterJob) {
                        case "전사" : jobId = "w1"; break;
                        case "궁수" : jobId = "a1"; break;
                        case "마법사" : jobId = "m1"; break;
                    }

                    // 4-3. 신규 캐릭터 정보 저장
                    CharacterDTO newChar = new CharacterDTO();
                    newChar.setName(newCharacterName);
                    newChar.setLevel(newCharacterLevel);
                    newChar.setHp(newCharacterHp);
                    newChar.setApoint(newCharacterApoint);
                    newChar.setDpoint(newCharacterDpoint);
                    newChar.setJobName(jobId);

                    // 4-4. 신규 캐릭터 등록을 위한 메소드 호출 후 등록
                    int result1 = registDAO.insertNewCharacter(newChar, con);

                    if(result1 > 0) {
                        System.out.println("\'" + newCharacterName + "\'" + " 캐릭터 등록 완료");
                    } else {
                        System.out.println("\'" + newCharacterName + "\'" + " 캐릭터 등록 실패");
                    } close(con); break;

                case 5 : // 5. 캐릭터 삭제
                    // 5-1. 삭제할 캐릭터명 입력
                    System.out.print("삭제할 캐릭터명을 입력하세요 : ");
                    String delCharacterName = sc.nextLine();

                    // 5-2. 삭제할 캐릭터명 저장
                    CharacterDTO delChar = new CharacterDTO();
                    delChar.setName(delCharacterName);

                    // 5-3. 메소드 호출 후 해당 캐릭터 삭제
                    int result2 = registDAO.deleteCharacter(delChar, con);

                    if(result2 > 0) {
                        System.out.println("\'" + delCharacterName + "\'" + " 캐릭터 삭제 완료");
                    } else {
                        System.out.println("\'" + delCharacterName + "\'" + " 캐릭터 삭제 실패");
                    } close(con); break;

                case 9 :
                    System.out.println("프로그램 종료"); return;

                default :
                    System.out.println("잘못된 메뉴번호 입력");
            }
        }
    }
}
