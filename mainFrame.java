/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class mainFrame extends javax.swing.JFrame {

    int turn = 0; // 0 is red, 1 is green
    int[][] board = new int[6][7]; //1 is red, 2 is green
    boolean win = false; //Check if player has won
    boolean activate = true; //activate or deactivate board based on initial conditions
    boolean colorChosen = false; //Check if player 1 has picked a color
    boolean colorChosen2 = false; //Check if player 2 has picked a color
    boolean draw = true; //draw transluscent placer for column 1
    boolean draw1 = true; //draw transluscent placer for column 2
    boolean draw2 = true; //draw transluscent placer for column 3
    boolean draw3 = true; //draw transluscent placer for column 4
    boolean draw4 = true; //draw transluscent placer for column 5
    boolean draw5 = true; //draw transluscent placer for column 6
    boolean draw6 = true; //draw transluscent placer for column 7
    BufferedWriter writer = null;
    BufferedReader reader = null;
    Color C1; //Color for player 1
    Color C2; //Color for player 2
    String colorPicked; //Color that player 1 picked
    String colorPicked2; //Color that player 2 picked
    
    String[] colorChoices = {"BLACK", "YELLOW", "CYAN", "DARK GRAY",
            "GRAY", "GREEN", "MAGENTA", "ORANGE", "PINK", "RED"}; //Colors that the player can choose from
    
    
    public mainFrame() {
        initComponents();
        this.repaint();
    }

    @Override
    public void paint(Graphics grphcs) {
        //Initial draw of the board
        Graphics g = jPanel1.getGraphics();
        super.paint(grphcs);
        g.setColor(Color.BLACK);
        for (int k = 0; k < 6; k++) {
            for (int i = 0; i < 7; i++) {
                g.drawRect(i * 100, k * 100, 100, 100);
            }
        }
    }
    

    public void redraw(int col) {
        int fall = 0;
        Graphics2D g = (Graphics2D) jPanel1.getGraphics();

        //Check if top slot is empty or not
        if (board[0][col] != 0) {
            return;
        }
        
        for (int k = 0; k < board.length; k++) {
            //animate flling tiles
            if (board[k][col]==0){
            try {
                Thread.sleep(15);
                if (turn == 0) {
                    g.setColor(C1);
                }
                if (turn == 1) {
                    g.setColor(C2);
                }
                //Draw Oval
                g.fillOval(100 * col, 100 * k, 100, 100);
                Thread.sleep(250);
                //Erase Oval
                g.setColor(Color.LIGHT_GRAY);
                g.clearRect(100 * col, k * 100, 100, 100);
                g.setColor(Color.BLACK);
                g.drawRect(100 * col, k * 100, 100, 100);
                

            } catch (InterruptedException ex) {
            }
            }
        }
        
        //Refresh board values
        for (int k = 1; k < board.length; k++) {
            if (board[k][col] != 0) {
                if (turn == 0) {
                    board[k - 1][col] = 1;
                    turn = 1;
                    break;
                } else if (turn == 1) {
                    board[k - 1][col] = 2;
                    turn = 0;
                    break;
                }
            }
        }
        //Check if bottom slot is empty
        if (board[5][col] == 0) {
            if (turn == 0) {
                board[5][col] = 1;
                turn = 1;
            } else if (turn == 1) {
                board[5][col] = 2;
                turn = 0;
            }
        }
        
        //Draw board if top is empty and bottom is filled
        for (int k = 0; k < board.length; k++) {
            //If square is unfilled, draw an empty square
            if (board[k][col] == 0) {
                g.drawRect(100 * col, k * 100, 100, 100);
                g.setColor(Color.BLACK);
            //If square has value one,  draw an empty square and an oval with color corresponding with player 1    
            } else if (board[k][col] == 1) {
                g.drawRect(100 * col, k * 100, 100, 100);
                g.setColor(C1);
                g.fillOval(100 * col, k * 100, 100, 100);
                g.setColor(Color.BLACK);
            //If square has value one,  draw an empty square and an oval with color corresponding with player 2    
            } else if (board[k][col] == 2) {
                g.drawRect(col * 100, k * 100, 100, 100);
                g.setColor(C2);
                g.fillOval(col * 100, k * 100, 100, 100);
                g.setColor(Color.BLACK);
            }
        }
        
    }
    
    public void animate(int col){
       //Inital animation code
       Graphics2D g = (Graphics2D) jPanel1.getGraphics();
       
       for (int k=0;k<board.length;k++){
        try{
        Thread.sleep(15);
        g.setColor(Color.RED);
        g.fillOval(100*col,100*k,100,100);
        Thread.sleep(150);
            g.setColor(Color.LIGHT_GRAY);
            g.clearRect(100*col, k*100, 100, 100);
            g.setColor(Color.BLACK);
            g.drawRect(100*col,k*100,100,100);
        
        } catch(InterruptedException ex) {}
       }
    }
    
    public boolean checkDraw(){
        //Check if game is a draw
        boolean isDraw = false;
        for (int row=0; row<board.length;row++){
            for(int col=0; col<board[0].length;col++){
                if (board[row][col]==0){
                    return false;
                }
            }
        }
        return true;
    }

    public void drawFancyPlacer(int col , MouseEvent evt) {
        
        Graphics2D g = (Graphics2D) jPanel1.getGraphics();
        Color cRed = new Color(255,0,0,90);
        Color cGreen = new Color(0,255,0,90);
        
        if (turn == 0 && board[0][col] == 0) {
                if ( (evt.getX() < (100*col + 100)) && (evt.getX() > 100*col) && draw==true) {
                    g.setColor(cRed);
                    g.fillOval(100*col, 0, 100, 100);
                    //draw=false;
                    System.out.println("False!");
                } 
                else if ((evt.getX() > (100*col + 100)) || (evt.getX()<100*col)) {
                    g.setColor(Color.lightGray);
                    g.clearRect(100*col, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(100*col, 0, 100, 100);

                }
            }
                        
            if (turn == 1 && board[0][col] == 0) {
                if (evt.getX() < 100*col + 100 && evt.getX() > 100*col && draw==true) {
                    g.setColor(cGreen);
                    g.fillOval(100*col, 0, 100, 100);
                    //draw=false;
                    System.out.println("False!");
                } 
                else if (evt.getX() > 100*col + 100 || evt.getX()<100*col) {
                    g.setColor(Color.lightGray);
                    g.clearRect(100*col, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(100*col, 0, 100, 100);
                    //draw=true;
                    System.out.println("False!");
                }
            }
    }

    public mainFrame(GraphicsConfiguration gc) {
        super(gc);
    }

    public boolean checkWin() {
        boolean win = false;

        //Horizontal checks
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == 1 && board[row][col] == board[row][col + 1] && board[row][col] == board[row][col + 2] && board[row][col] == board[row][col + 3]) {
                    return true;
                }
                if (board[row][col] == 2 && board[row][col] == board[row][col + 1] && board[row][col] == board[row][col + 2] && board[row][col] == board[row][col + 3]) {
                    return true;
                }
            }
        }
        //Vertical checks
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == 1 && board[row][col] == board[row + 1][col] && board[row][col] == board[row + 2][col] && board[row][col] == board[row + 3][col]) {
                    return true;
                }
                if (board[row][col] == 2 && board[row][col] == board[row + 1][col] && board[row][col] == board[row + 2][col] && board[row][col] == board[row + 3][col]) {
                    return true;
                }
            }
        }
        //Diagonal checks
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (col + 3 < board[0].length && row - 3 >= 0) {
                    if (board[row][col] == 1 && board[row][col] == board[row - 1][col + 1] && board[row][col] == board[row - 2][col + 2] && board[row][col] == board[row - 3][col + 3]) {
                        return true;
                    } else if (board[row][col] == 2 && board[row][col] == board[row - 1][col + 1] && board[row][col] == board[row - 2][col + 2] && board[row][col] == board[row - 3][col + 3]) {
                        return true;
                    }
                }
                if (row + 3 < board.length && col + 3 < board[0].length) {
                    if (board[row][col] == 1 && board[row][col] == board[row + 1][col + 1] && board[row][col] == board[row + 2][col + 2] && board[row][col] == board[row + 3][col + 3]) {
                        return true;
                    }
                    if (board[row][col] == 2 && board[row][col] == board[row + 1][col + 1] && board[row][col] == board[row + 2][col + 2] && board[row][col] == board[row + 3][col + 3]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        redScore = new javax.swing.JTextField();
        Red = new javax.swing.JLabel();
        greenScore = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Reset = new javax.swing.JButton();
        saveGame = new javax.swing.JButton();
        loadGame = new javax.swing.JButton();
        colorSelect = new javax.swing.JButton();
        colorSelect2 = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 740, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );

        redScore.setText("0");
        redScore.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        redScore.setEnabled(false);
        redScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redScoreActionPerformed(evt);
            }
        });

        Red.setText("Player 1:");

        greenScore.setText("0");
        greenScore.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        greenScore.setEnabled(false);

        jLabel2.setText("Player 2:");

        Reset.setText("Reset Board");
        Reset.setEnabled(false);
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });

        saveGame.setText("Save");
        saveGame.setEnabled(false);
        saveGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGameActionPerformed(evt);
            }
        });

        loadGame.setText("Load Game");
        loadGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadGameActionPerformed(evt);
            }
        });

        colorSelect.setText("Player 1 Select Tile Color");
        colorSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorSelectActionPerformed(evt);
            }
        });

        colorSelect2.setText("Player 2 Select Tile Color");
        colorSelect2.setEnabled(false);
        colorSelect2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorSelect2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Red)
                            .addComponent(redScore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(100, 100, 100)
                        .addComponent(saveGame)
                        .addGap(87, 87, 87)
                        .addComponent(Reset)
                        .addGap(60, 60, 60)
                        .addComponent(loadGame)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(greenScore, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorSelect)
                    .addComponent(colorSelect2))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(greenScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(colorSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(colorSelect2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(saveGame)
                                        .addComponent(loadGame))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Red)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(redScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        if (colorChosen == false || colorChosen2 == false) {
            JOptionPane.showMessageDialog(null, "Pick colors for both players!");
        } else if (activate == true) {
            Reset.setEnabled(true);
            saveGame.setEnabled(true);
            colorSelect.setEnabled(false);
            colorSelect2.setEnabled(false);
            if (evt.getX() < 100 && evt.getX() > 0) {
                redraw(0);
            } else if (evt.getX() < 200) {
                redraw(1);
            } else if (evt.getX() < 300) {
                redraw(2);
            } else if (evt.getX() < 400) {
                redraw(3);
            } else if (evt.getX() < 500) {
                redraw(4);
            } else if (evt.getX() < 600) {
                redraw(5);
            } else if (evt.getX() < 700) {
                redraw(6);
            }
            //If a player has won
            if (checkWin() == true) {
                //If Player 1 wins
                if (turn == 1) {
                    JOptionPane.showMessageDialog(null, colorPicked + " Wins!");
                    int currentScoreRed = Integer.parseInt(redScore.getText());
                    redScore.setText(Integer.toString(currentScoreRed + 1));
                    activate = false;
                }
                //If Player 2 wins
                if (turn == 0) {
                    JOptionPane.showMessageDialog(null, colorPicked2 + " Wins!");
                    int currentScoreGreen = Integer.parseInt(greenScore.getText());
                    greenScore.setText(Integer.toString(currentScoreGreen + 1));
                    activate = false;
                }

            }
            //Check if the game is a tie!
            if (checkDraw() == true) {
                JOptionPane.showMessageDialog(null, "Draw!");
                activate = false;
            }
        }
    }//GEN-LAST:event_jPanel1MouseClicked

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        // TODO add your handling code here:
        Graphics2D g = (Graphics2D) jPanel1.getGraphics();
        
        //Set all elements in the matrix to 0
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = 0;
            }
        }
        
        //Completely clear the board
        g.clearRect(0, 0, 700, 600);
        
        //Redraw the rectangles
        for (int k = 0; k < 6; k++) {
            for (int i = 0; i < 7; i++) {
                g.drawRect(i * 100, k * 100, 100, 100);
            }
        }

        colorSelect2.setEnabled(true);
        colorSelect.setEnabled(true);
        saveGame.setEnabled(false);
        activate = true;
    }//GEN-LAST:event_ResetActionPerformed

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        // TODO add your handling code here:
        if (evt.getX() < 100) {
            //System.out.println("Mouse Entered");
        }

    }//GEN-LAST:event_jPanel1MouseEntered

    private void redScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redScoreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_redScoreActionPerformed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // TODO add your handling code here:
        if (evt.getX() < 100) {
            System.out.println("Mouse Entered");
        }
    }//GEN-LAST:event_jPanel1MouseDragged
    
    public void hover(MouseEvent evt){
        
        int columnNum = evt.getX()/100;
        
        Graphics2D g = (Graphics2D) jPanel1.getGraphics();
        int R1 = C1.getRed();
        int B1 = C1.getBlue();
        int G1 = C1.getGreen();
        Color cPlayer1 = new Color(R1,G1,B1,90);
        int R2 = C2.getRed();
        int B2 = C2.getBlue();
        int G2 = C2.getGreen();
        Color cPlayer2 = new Color(R2,G2,B2,90);
        
        if (turn == 0 && board[0][columnNum] == 0) {
                    g.setColor(cPlayer1);
                    g.fillOval(0, columnNum*100, 100, 100);
                    draw=false;
                    
                    g.setColor(Color.lightGray);
                    g.clearRect(0, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(0, 0, 100, 100);
                    draw=true;
                }
            
            
            if (turn == 1 && board[0][0] == 0) {
                if (evt.getX() < 100 && draw==true) {
                    g.setColor(cPlayer2);
                    g.fillOval(0, 0, 100, 100);
                    draw=false;
                } else if (evt.getX()>100) {
                    g.setColor(Color.lightGray);
                    g.clearRect(0, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(0, 0, 100, 100);
                    draw=true;
                }
            }
    }
    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        // TODO add your handling code here:
        if (colorChosen ==true&&colorChosen2==true){
            
        Graphics2D g = (Graphics2D) jPanel1.getGraphics();
        int R1 = C1.getRed();
        int B1 = C1.getBlue();
        int G1 = C1.getGreen();
        Color cPlayer1 = new Color(R1,G1,B1,90);
        int R2 = C2.getRed();
        int B2 = C2.getBlue();
        int G2 = C2.getGreen();
        Color cPlayer2 = new Color(R2,G2,B2,90);
                
        if (activate == true&&colorChosen==true&&colorChosen2==true) {

           
            if (turn == 0 && board[0][0] == 0) {
                if (evt.getX() < 100 && draw==true) {
                    g.setColor(cPlayer1);
                    g.fillOval(0, 0, 100, 100);
                    draw=false;
                } else if (evt.getX()>100) {
                    g.setColor(Color.lightGray);
                    g.clearRect(0, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(0, 0, 100, 100);
                    draw=true;
                }
            }
            
            if (turn == 1 && board[0][0] == 0) {
                if (evt.getX() < 100 && draw==true) {
                    g.setColor(cPlayer2);
                    g.fillOval(0, 0, 100, 100);
                    draw=false;
                } else if (evt.getX()>100) {
                    g.setColor(Color.lightGray);
                    g.clearRect(0, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(0, 0, 100, 100);
                    draw=true;
                }
            }
            
            //******************************

            if (turn == 0 && board[0][1] == 0) {
                if (evt.getX() < 200 && evt.getX() > 100 && draw1==true) {
                    g.setColor(cPlayer1);
                    g.fillOval(100, 0, 100, 100);
                    draw1=false;
                } else if (evt.getX()>200 || evt.getX()<100) {
                    g.setColor(Color.lightGray);
                    g.clearRect(100, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(100, 0, 100, 100);
                    draw1=true;
                }
            }
            
            if (turn == 1 && board[0][1] == 0) {
                if (evt.getX() < 200 && evt.getX() > 100 && draw1==true) {
                    g.setColor(cPlayer2);
                    g.fillOval(100, 0, 100, 100);
                    draw1=false;
                } else if (evt.getX()>200 || evt.getX()<100) {
                    g.setColor(Color.lightGray);
                    g.clearRect(100, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(100, 0, 100, 100);
                    draw1=true;
                }
            }
           
            //******************************
            
            if (turn == 0 && board[0][2] == 0) {
                if (evt.getX() < 300 && evt.getX() > 200 && draw2==true) {
                    g.setColor(cPlayer1);
                    g.fillOval(200, 0, 100, 100);
                    draw2=false;
                } else if (evt.getX()>300 || evt.getX()<200) {
                    g.setColor(Color.lightGray);
                    g.clearRect(200, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(200, 0, 100, 100);
                    draw2=true;
                }
            }
            
            if (turn == 1 && board[0][2] == 0) {
                if (evt.getX() < 300 && evt.getX() > 200 && draw2==true) {
                    g.setColor(cPlayer2);
                    g.fillOval(200, 0, 100, 100);
                    draw2=false;
                } else if (evt.getX()>300 || evt.getX()<200) {
                    g.setColor(Color.lightGray);
                    g.clearRect(200, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(200, 0, 100, 100);
                    draw2=true;
                }
            }
            
            //*****************************

            if (turn == 0 && board[0][3] == 0) {
                if (evt.getX() < 400 && evt.getX() > 300 && draw3==true) {
                    g.setColor(cPlayer1);
                    g.fillOval(300, 0, 100, 100);
                    draw3=false;
                } else if (evt.getX()>400 || evt.getX()<300) {
                    g.setColor(Color.lightGray);
                    g.clearRect(300, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(300, 0, 100, 100);
                    draw3=true;
                }
            }
            
            if (turn == 1 && board[0][3] == 0) {
                if (evt.getX() < 400 && evt.getX() > 300 && draw3==true) {
                    g.setColor(cPlayer2);
                    g.fillOval(300, 0, 100, 100);
                    draw3=false;
                } else if (evt.getX()>400 || evt.getX()<300) {
                    g.setColor(Color.lightGray);
                    g.clearRect(300, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(300, 0, 100, 100);
                    draw3=true;
                }
            }
            
            //*******************************
            
            if (turn == 0 && board[0][4] == 0) {
                if (evt.getX() < 500 && evt.getX() > 400 &&draw4==true) {
                    g.setColor(cPlayer1);
                    g.fillOval(400, 0, 100, 100);
                    draw4=false;
                } 
                else if (evt.getX()>500 || evt.getX()<400) {
                    g.setColor(Color.lightGray);
                    g.clearRect(400, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(400, 0, 100, 100);
                    draw4=true;
                }
            }
            
            if (turn == 1 && board[0][4] == 0) {
                if (evt.getX() < 500 && evt.getX() > 400 &&draw4==true) {
                    g.setColor(cPlayer2);
                    g.fillOval(400, 0, 100, 100);
                    draw4=false;
                } 
                else if (evt.getX()>500 || evt.getX()<400) {
                    g.setColor(Color.lightGray);
                    g.clearRect(400, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(400, 0, 100, 100);
                    draw4=true;
                }
            }
            
            //*******************************
            
            if (turn == 0 && board[0][5] == 0) {
                if (evt.getX() < 600 && evt.getX() > 500 &&draw5==true) {
                    g.setColor(cPlayer1);
                    g.fillOval(500, 0, 100, 100);
                    draw5=false;
                } 
                else if (evt.getX()>600 || evt.getX()<500) {
                    g.setColor(Color.lightGray);
                    g.clearRect(500, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(500, 0, 100, 100);
                    draw5=true;
                }
            }
            
            if (turn == 1 && board[0][5] == 0) {
                if (evt.getX() < 600 && evt.getX() > 500 &&draw5==true) {
                    g.setColor(cPlayer2);
                    g.fillOval(500, 0, 100, 100);
                    draw5=false;
                } 
                else if (evt.getX()>600 || evt.getX()<500) {
                    g.setColor(Color.lightGray);
                    g.clearRect(500, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(500, 0, 100, 100);
                    draw5=true;
                }
            }
            
            //*********************************
            
            if (turn == 0 && board[0][6] == 0) {
                if (evt.getX() < 700 && evt.getX() > 600 &&draw6==true) {
                    g.setColor(cPlayer1);
                    g.fillOval(600, 0, 100, 100);
                    draw6=false;
                } 
                else if (evt.getX()>700 || evt.getX()<600) {
                    g.setColor(Color.lightGray);
                    g.clearRect(600, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(600, 0, 100, 100);
                    draw6=true;
                }
            }
            
            if (turn == 1 && board[0][6] == 0) {
                if (evt.getX() < 700 && evt.getX() > 600 &&draw6==true) {
                    g.setColor(cPlayer2);
                    g.fillOval(600, 0, 100, 100);
                    draw6=false;
                } 
                else if (evt.getX()>700 || evt.getX()<600) {
                    g.setColor(Color.lightGray);
                    g.clearRect(600, 0, 100, 100);
                    g.setColor(Color.BLACK);
                    g.drawRect(600, 0, 100, 100);
                    draw6=true;
                }
            }
        }

    }
        
    }//GEN-LAST:event_jPanel1MouseMoved

    private void saveGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveGameActionPerformed
        // TODO add your handling code here:
        String toSave = "";
        for (int k = 0; k < board.length; k++) {
            for (int i = 0; i < board[0].length; i++) {
                toSave += board[k][i];
            }
        }
        
        
        String s = JOptionPane.showInputDialog(null,"Enter the file name you want to save as");
        
        try {
            writer = new BufferedWriter(new FileWriter(s+".txt"));
            writer.write(toSave);
        } catch (IOException e) {
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
        
     
    }//GEN-LAST:event_saveGameActionPerformed

    private void loadGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadGameActionPerformed
        // TODO add your handling code here:
        Graphics2D g = (Graphics2D) jPanel1.getGraphics();
        String s = JOptionPane.showInputDialog(null, "Enter the file name you want to open");
        String values = "";
        
        int counter = 0;
        try {
            FileReader file = new FileReader(s + ".txt");
            reader = new BufferedReader(file);
            String line = reader.readLine();
            while (line != null) {
                values += line;
                line = reader.readLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Invalid File!");
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = Integer.parseInt(values.substring(counter, counter + 1));
                counter++;
            }
        }

        for (int k = 0; k <= 6; k++) {
            for (int i = 0; i <= 7; i++) {
                g.clearRect(100 * k, 100 * i, 100, 100);
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == 0) {
                    g.drawRect(100 * col, row * 100, 100, 100);
                    g.setColor(Color.BLACK);
                } else if (board[row][col] == 1) {
                    g.drawRect(100 * col, row * 100, 100, 100);
                    g.setColor(Color.RED);
                    g.fillOval(100 * col, row * 100, 100, 100);
                    g.setColor(Color.BLACK);
                } else if (board[row][col] == 2) {
                    g.drawRect(col * 100, row * 100, 100, 100);
                    g.setColor(Color.GREEN);
                    g.fillOval(col * 100, row * 100, 100, 100);
                    g.setColor(Color.BLACK);
                }
            }
        }
        
    }//GEN-LAST:event_loadGameActionPerformed

    private void colorSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorSelectActionPerformed
        // TODO add your handling code here:
        
        String input = (String) JOptionPane.showInputDialog(null, "Choose your Colour",
                "Colour Chooser", JOptionPane.QUESTION_MESSAGE, null, colorChoices, // Array of choices
                colorChoices[0]); // Initial choice        
        
        switch (input) {
            case "BLACK":
                C1 = Color.BLACK;
                colorSelect.setEnabled(false);
                colorPicked = "Black";
                colorChosen = true;
                break;
            case "YELLOW":
                C1 = Color.YELLOW;
                colorSelect.setEnabled(false);
                colorPicked = "Yellow";
                colorChosen = true;
                break;
            case "CYAN":
                C1 = Color.CYAN;
                colorSelect.setEnabled(false);
                colorPicked = "Cyan";
                colorChosen = true;
                break;
            case "DARK GRAY":
                C1 = Color.DARK_GRAY;
                colorSelect.setEnabled(false);
                colorPicked = "Dark Gray";
                colorChosen = true;
                break;
            case "GRAY":
                C1 = Color.GRAY;
                colorSelect.setEnabled(false);
                colorPicked = "Gray";
                colorChosen = true;
                break;
            case "GREEN":
                C1 = Color.GREEN;
                colorSelect.setEnabled(false);
                colorPicked = "Green";
                colorChosen = true;
                break;
            case "MAGENTA":
                C1 = Color.MAGENTA;
                colorSelect.setEnabled(false);
                colorPicked = "Magenta";
                colorChosen = true;
                break;
            case "ORANGE":
                C1 = Color.ORANGE;
                colorSelect.setEnabled(false);
                colorPicked = "Orange";
                colorChosen = true;
                break;
            case "PINK":
                C1 = Color.PINK;
                colorSelect.setEnabled(false);
                colorPicked = "Pink";
                colorChosen = true;
                break;
            case "RED":
                C1 = Color.RED;
                colorSelect.setEnabled(false);
                colorPicked = "Red";
                colorChosen = true;
                break;
            default:
                C1 = Color.RED;
                colorSelect.setEnabled(false);
                colorSelect2.setEnabled(true);
                
        }
        colorSelect2.setEnabled(true);
        

    }//GEN-LAST:event_colorSelectActionPerformed

    private void colorSelect2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorSelect2ActionPerformed
        // TODO add your handling code here:
        String[] colorChoices = {"BLACK", "YELLOW", "CYAN", "DARK GRAY",
            "GRAY", "GREEN", "LIGHT GRAY", "MAGENTA", "ORANGE", "PINK", "RED"};
        
        String input = (String) JOptionPane.showInputDialog(null, "Choose your Colour",
                "Colour Chooser", JOptionPane.QUESTION_MESSAGE, null, // Use
                // default
                // icon
                colorChoices, // Array of choices
                colorChoices[0]); // Initial choice        
        
        switch (input) {
            case "BLACK":
                C2 = Color.BLACK;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Black";
                colorChosen2 = true;
                break;
            case "YELLOW":
                C2 = Color.YELLOW;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Yellow";
                colorChosen2 = true;
                break;
            case "CYAN":
                C2 = Color.CYAN;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Cyan";
                colorChosen2 = true;
                break;
            case "DARK GRAY":
                C2 = Color.DARK_GRAY;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Dark Gray";
                colorChosen2 = true;
                break;
            case "GRAY":
                C2 = Color.GRAY;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Gray";
                colorChosen2 = true;
                break;
            case "GREEN":
                C2 = Color.GREEN;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Green";
                colorChosen2 = true;
                break;
            case "LIGHT GRAY":
                C2 = Color.LIGHT_GRAY;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Light Gray";
                colorChosen2 = true;
                break;
            case "MAGENTA":
                C2 = Color.MAGENTA;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Magenta";
                colorChosen2 = true;
                break;
            case "ORANGE":
                C2 = Color.ORANGE;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Orange";
                colorChosen2 = true;
                break;
            case "PINK":
                C2 = Color.PINK;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Pink";
                colorChosen2 = true;
                break;
            case "RED":
                C2 = Color.RED;
                colorSelect2.setEnabled(false);
                colorPicked2 = "Red";
                colorChosen2 = true;
                break;
            default:
                C2 = Color.GREEN;
                colorSelect2.setEnabled(false);
        }
    }//GEN-LAST:event_colorSelect2ActionPerformed

    /**
         * @param args the command line arguments
         */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new mainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Red;
    private javax.swing.JButton Reset;
    private javax.swing.JButton colorSelect;
    private javax.swing.JButton colorSelect2;
    private javax.swing.JTextField greenScore;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loadGame;
    private javax.swing.JTextField redScore;
    private javax.swing.JButton saveGame;
    // End of variables declaration//GEN-END:variables

    private int getBoardPadding() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
