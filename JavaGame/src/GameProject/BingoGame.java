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
   static int buttonIndexSave1 = 0; //ó������ �ε��� ��
   static int buttonIndexSave2 = 0; //�ι�°���� �ε��� ��  
   static Timer timer;
   static int tryCount = 0;
   static int successCount = 0; //����õ����� �����ߴ��� ī��Ʈ 0~8
   

   static class MyFrame extends JFrame implements ActionListener{
      public MyFrame(String title) {
         super(title);
         this.setLayout(new BorderLayout());
         this.setSize(400, 500);
         this.setVisible(true);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         initUI(this); //��ũ��UI����
         mixCard();
         
         
         this.pack(); //�����ڸ� ������ �Ⱥ��̰�
      }
      @Override
      public void actionPerformed(ActionEvent e) { //��ư Ŭ�� �� ������ �׼�
         
         if(openCount == 2) { //ī�尡 �ΰ� ���ȴٸ� ���̻� �������ʵ��� ����
            return;
         }
         
         JButton btn = (JButton)e.getSource();
         int index = getButtonIndex(btn);
         btn.setIcon(changeImage(images[index])); //�̹��� ����
         
         openCount++;
         if(openCount ==1) {//ù ��° ī��
            buttonIndexSave1 = index; //��ư �ε����� �����ϰ� �־����
         }
         else if (openCount ==2) { //�� ��° ī��
            buttonIndexSave2 = index; //�ε����� �����ߴٰ� ù��° ī��� ������ Ȯ���ؾ���
            tryCount++; //�ι� ���ȱ� ������ �õ�Ƚ�� ī��Ʈ��
            labelMessage.setText("Find Some Fruit!"+"Try"+tryCount);
            
            boolean isBingo = checkCard(buttonIndexSave1, buttonIndexSave2); //���� ī������ Ȯ��
            if(isBingo == true) { //���ٸ� public boolean checkCard���� Ȯ��
               openCount = 0;
               successCount++;
               if(successCount == 8) { //����Ƚ���� 8�̸� ���� ��
                  labelMessage.setText("Game Over"+"Try"+tryCount);
               }
            }else { //�ٸ��ٸ�
               backToQuestion();
            }
         }
         
      }
      public void backToQuestion() { //ī�尡 �ٸ��ٸ�
         timer = new Timer(1000, new ActionListener() { //1���Ŀ� �׼��� �����ϵ���
            @Override
            public void actionPerformed(ActionEvent e) {
               
               openCount = 0; //�ٽ��ʱ�ȭ �ֳĸ� ī�带 ������ ���ؼ�
               buttons[buttonIndexSave1].setIcon(changeImage("question.png")); //ī�� �ΰ��� �ٸ��⶧���� �����̹����� �ٽ� �ٲ��� 
               buttons[buttonIndexSave2].setIcon(changeImage("question.png"));
               timer.stop();
            }
         });
         timer.start();
      }
      
      public boolean checkCard(int index1, int index2) { //ù��°ī��� �ι�°�� ������ Ȯ��
         if(index1 == index2) {//ù��°�� �ι�°ī�� �̸��� �Ȱ��� �ָ� �ȵ�
            return false;
         }
         if(images[index1].contentEquals(images[index2])) { //ī�尡 ������
            return true;
         }else { //ī�尡 �ٸ���
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
   static void mixCard() { //ī�� 16���� �������� mix
      Random rand = new Random();
      for(int i=0; i<1000; i++) { //1000������ mix
         int random = rand.nextInt(15)+1; //1~15������
         String temp = images[0]; //ù��°�� �̹����� ��������
         images[0]=images[random]; //ù��° �̹����� �����̹����� �ٲ��ش�
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

      //��ư 16�� ������
      panelCenter = new JPanel();
      panelCenter.setLayout(new GridLayout(4,4));
      panelCenter.setPreferredSize(new Dimension(400,400));
      for(int i=0; i<16; i++) {
         buttons[i]=new JButton();
         buttons[i].setPreferredSize(new Dimension(100,100));
         buttons[i].setIcon(changeImage("question.png")); //ó�� ������ ����ǥ �̹���
         buttons[i].addActionListener(myFrame); //��ưŬ���� 
         panelCenter.add(buttons[i]);
      }
      myFrame.add("Center",panelCenter);
   }
   
   static ImageIcon changeImage(String filename) {
      ImageIcon icon = new ImageIcon("./img/"+filename);
      Image originImage = icon.getImage();
      Image changedImage = originImage.getScaledInstance(80,80,Image.SCALE_SMOOTH);
      ImageIcon icon_new = new ImageIcon(changedImage); //����ǥ�̹������� �ٲ�
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