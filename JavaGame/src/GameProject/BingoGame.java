package GameProject;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class BingoGame {
   static JPanel panelNorth;
   static JPanel panelCenter;
   static JLabel labelMessage;
   static JButton[] buttons = new JButton[16];
   static String[] images = {
      "fruit01.png", "fruit02.png", "fruit03.png", "fruit04.png",
      "fruit05.png", "fruit06.png", "fruit07.png", "fruit08.png",
      "fruit01.png", "fruit02.png", "fruit03.png", "fruit04.png",
      "fruit05.png", "fruit06.png", "fruit07.png", "fruit08.png",
   };
   static int openCount = 0; //count : 0,1,2
   static int buttonIndexSave1 = 0; //처음열린 인덱스 값
   static int buttonIndexSave2 = 0; //두번째열린 인덱스 값  
   static Timer timer;
   static int tryCount = 0;
   static int successCount = 0; //몇번시도끝에 성공했는지 카운트 0~8
   

   static class MyFrame extends JFrame implements ActionListener{
      public MyFrame(String title) {
         super(title);
         this.setLayout(new BorderLayout());
         this.setSize(400, 500);
         this.setVisible(true);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         initUI(this); //스크린UI세팅
         mixCard();
         
         
         this.pack(); //가장자리 여백은 안보이게
      }
      @Override
      public void actionPerformed(ActionEvent e) { //버튼 클릭 시 나오는 액션
         
         if(openCount == 2) { //카드가 두개 열렸다면 더이상 열리지않도록 리턴
            return;
         }
         
         JButton btn = (JButton)e.getSource();
         int index = getButtonIndex(btn);
         btn.setIcon(changeImage(images[index])); //이미지 오픈
         
         openCount++;
         if(openCount ==1) {//첫 번째 카드
            buttonIndexSave1 = index; //버튼 인덱스를 저장하고 있어야함
         }
         else if (openCount ==2) { //두 번째 카드
            buttonIndexSave2 = index; //인덱스를 저장했다가 첫번째 카드랑 같은지 확인해야함
            tryCount++; //두번 열렸기 때문에 시도횟수 카운트함
            labelMessage.setText("Find Some Fruit!"+"Try"+tryCount);
            
            boolean isBingo = checkCard(buttonIndexSave1, buttonIndexSave2); //같은 카드인지 확인
            if(isBingo == true) { //같다면 public boolean checkCard에서 확인
               openCount = 0;
               successCount++;
               if(successCount == 8) { //성공횟수가 8이면 게임 끝
                  labelMessage.setText("Game Over"+"Try"+tryCount);
               }
            }else { //다르다면
               backToQuestion();
            }
         }
         
      }
      public void backToQuestion() { //카드가 다르다면
         timer = new Timer(1000, new ActionListener() { //1초후에 액션이 수행하도록
            @Override
            public void actionPerformed(ActionEvent e) {
               
               openCount = 0; //다시초기화 왜냐면 카드를 뒤집기 위해서
               buttons[buttonIndexSave1].setIcon(changeImage("question.png")); //카드 두개가 다르기때문에 원래이미지로 다시 바꿔줌 
               buttons[buttonIndexSave2].setIcon(changeImage("question.png"));
               timer.stop();
            }
         });
         timer.start();
      }
      
      public boolean checkCard(int index1, int index2) { //첫번째카드와 두번째가 같은지 확인
         if(index1 == index2) {//첫번째와 두번째카드 이름을 똑같이 주면 안됨
            return false;
         }
         if(images[index1].contentEquals(images[index2])) { //카드가 같으면
            return true;
         }else { //카드가 다르면
            return false;
         }
      }
      public int getButtonIndex(JButton btn) {
         int index = 0;
         for (int i=0; i<16; i++) {
            if(buttons[i]==btn) {
               index = i;
            }
         }
         return index;
      }
      
   }
   static void mixCard() { //카드 16개를 랜덤으로 mix
      Random rand = new Random();
      for(int i=0; i<1000; i++) { //1000번까지 mix
         int random = rand.nextInt(15)+1; //1~15번까지
         String temp = images[0]; //첫번째의 이미지를 가져오고
         images[0]=images[random]; //첫번째 이미지와 랜덤이미지를 바꿔준다
         images[random]=temp;
      }
   }
   
   static void initUI(MyFrame myFrame) { 
      panelNorth = new JPanel();
      panelNorth.setPreferredSize(new Dimension(400,100));
      panelNorth.setBackground(Color.BLUE);
      labelMessage = new JLabel("Find Same Fruit!  " + "Try 0");
      labelMessage.setPreferredSize(new Dimension(400,100));
      labelMessage.setForeground(Color.WHITE);
      labelMessage.setHorizontalAlignment(JLabel.CENTER); 
      panelNorth.add(labelMessage);
      myFrame.add("North", panelNorth);

      //버튼 16개 설정값
      panelCenter = new JPanel();
      panelCenter.setLayout(new GridLayout(4,4));
      panelCenter.setPreferredSize(new Dimension(400,400));
      for(int i=0; i<16; i++) {
         buttons[i]=new JButton();
         buttons[i].setPreferredSize(new Dimension(100,100));
         buttons[i].setIcon(changeImage("question.png")); //처음 나오는 물음표 이미지
         buttons[i].addActionListener(myFrame); //버튼클릭시 
         panelCenter.add(buttons[i]);
      }
      myFrame.add("Center",panelCenter);
   }
   
   static ImageIcon changeImage(String filename) {
      ImageIcon icon = new ImageIcon("./img/"+filename);
      Image originImage = icon.getImage();
      Image changedImage = originImage.getScaledInstance(80,80,Image.SCALE_SMOOTH);
      ImageIcon icon_new = new ImageIcon(changedImage); //물음표이미지에서 바뀜
      return icon_new;
   }
   
   public static void main(String[] args) {
      new MyFrame("Bingo Game");

   }

   public void play() {
      // TODO Auto-generated method stub
      new MyFrame("Bingo Game");
   }

}