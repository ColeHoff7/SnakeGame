import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Main extends PApplet {


int screenX = 0;
int screenY = 0;
final int block = 30;
int x1 = 1;
int y1= 1;
int[][] board = new int[25][25];
Snake snake = new Snake();
Food food = new Food();
boolean gameOver = false;
boolean gameStart = true;
int score = 0;
PImage krumpe;
boolean code = true;
int codeint = 0;
int[] konami = new int[11]; //up up down down left right left right bottom left (f) top left (r) start 
AudioPlayer player;
Minim minim;
AudioPlayer player2;
Minim minim2;
int flash = 0;
int bcolor1 = 0;
int bcolor2 = 0;
int bcolor3 = 0;
boolean firstOver = true;
boolean secondOver = false;

public void setup(){
  minim = new Minim(this);
  player = minim.loadFile("sandstorm.mp3");
  minim2 = new Minim(this);
  player2 = minim.loadFile("SNAKE.mp3");
  screenX = displayWidth;
  screenY = displayHeight;
  clear();
  size(750,750);
  frameRate(15);
  krumpe = loadImage("krumpeface.png");
}

public void draw(){
 clear();
 if(secondOver){
   delay(4000);
   player2.close();
   secondOver = false;
 }
 if(!gameOver){
   firstOver = true;
  if(gameStart){
   textSize(32);
   fill(255);
   textAlign(CENTER);
   text("SNAKE",375,375);
   text("PRESS START",375,420);
   textAlign(RIGHT);
   textSize(12);
   text("hint: Konami",740,740);
   textAlign(LEFT);
   text("Made by Cole Hoffbauer",10,740);
  }
  else{
  for(int y = 0; y < 10; y++){
    if(konami[y] == 0){
      code = false;
    }
  }
  if(code){
    player.play();
    if(flash%10 == 0){
      bcolor1 = round(random(255));
      bcolor2 = round(random(255));
      bcolor2 = round(random(255));
    }
    background(bcolor1,bcolor2,bcolor3);
    flash++;
    fill(153,50,204);
    textSize(26);
    textAlign(CENTER);
    text("KRUMPE CODE ENTERED!",375,30);
  }
  frameRate(10 + snake.difficulty);
  moveSnake();
  if(!gameOver)drawSnake();
  drawFood();
  checkCollisions();
  fill(255);
  for(int i = 0; i < 25; i++){
    for(int j = 0; j < 25; j++){
      if(board[i][j] > 0){
        if(!code)rect(i * block, j * block, block, block);
        else image(krumpe, i*block, j*block, block, block);
      }
    }
  }
  for(int i = 0; i < 25; i++){
    for(int j = 0; j < 25; j++){
      if(board[i][j]>0){
        board[i][j]--;
      }
    }
  }
  }
 } 
 else if(gameOver){
   snake = new Snake();
   food = new Food();
   for(int i = 0; i < 25; i++){
    for(int j = 0; j < 25; j++){
      board[i][j] = 0;
    }
   }
   textSize(32);
   fill(255);
   textAlign(CENTER);
   text("GAME OVER",375,375);
   text("PRESS START TO RESTART",375,420);
   if(code){
     player.close();
     player2.play();
     code = false;
     secondOver = true;
   }
 }
 textSize(15);
 fill(255);
 textAlign(RIGHT);
 text("Score: " + score,740,30);
}

public void moveSnake(){
//move tail to ahead of head, update xpos and ypos
    if(snake.direction.equals("Right")){
      snake.segs.get(snake.tail).xPos = snake.xPos + 1;
      snake.segs.get(snake.tail).yPos = snake.yPos;
      snake.xPos++;
      if(snake.xPos > 24) gameOver = true;
    }
    else if(snake.direction.equals("Up")){
      snake.segs.get(snake.tail).yPos = snake.yPos - 1;
      snake.segs.get(snake.tail).xPos = snake.xPos;
      snake.yPos--;
      if(snake.yPos < 0) gameOver = true;
    }
    else if(snake.direction.equals("Left")){
      snake.segs.get(snake.tail).xPos = snake.xPos - 1;
      snake.segs.get(snake.tail).yPos = snake.yPos;
      snake.xPos--;
      if(snake.xPos < 0) gameOver = true;
    }
    else if(snake.direction.equals("Down")){
      snake.segs.get(snake.tail).yPos = snake.yPos + 1;
      snake.segs.get(snake.tail).xPos = snake.xPos;
      snake.yPos++;
      if(snake.yPos > 24) gameOver = true;
    }
    snake.tail--;
    if(snake.tail<0) snake.tail = snake.size-1;
    /*System.out.println("Xpos: " +snake.xPos);
    System.out.println("Ypos: " +snake.yPos);*/
}

public void drawSnake(){
  for(int i = 0; i < snake.size; i++){
    board[snake.segs.get(i).xPos][snake.segs.get(i).yPos]++;
  }
}

public void drawFood(){
  board[food.xPos][food.yPos]++;
}

public void checkCollisions(){
  int z = 0;
  for(int i = 0; i < snake.size; i++){
    int x = snake.segs.get(i).xPos;
    int y = snake.segs.get(i).yPos;
    if(x == snake.xPos && y == snake.yPos){
      if(z > 0){
        //System.out.println("RAN INTO ITSELF");
        gameOver = true;
        return;
      }
      z++;
    }
  }
  
  if(snake.xPos == food.xPos && snake.yPos == food.yPos){
    board[food.xPos][food.yPos]--;
    snake.addSegment();
    score+=100;
    food.move();
  }
}

public void keyPressed(){
  if(key == 'd'){
    if(!gameStart)snake.setDirection("Right");
      if(codeint == 5 || codeint == 7){
        konami[codeint] = 1;
      }
  }
  else if(key == 'x'){
    if(!gameStart)snake.setDirection("Down");
      if(codeint == 2 || codeint == 3){
        konami[codeint] = 1;
      }
  }
  else if(key == 'a'){
    if(!gameStart)snake.setDirection("Left");
      if(codeint == 4 || codeint == 6){
        konami[codeint] = 1;
      }
  }
  else if(key == 'w'){
    if(!gameStart)snake.setDirection("Up");
    if(codeint>1) codeint = 0;
      if(codeint == 0 || codeint == 1){
        konami[codeint] = 1;
      }
  }
  else if(keyCode == ENTER || keyCode == RETURN){
    if(gameOver) score = 0;
      if(codeint == 10){
        konami[codeint] = 1;
      }
    gameOver = false;
    gameStart = false;
  }
  else if(key == 'r'){
      if(codeint == 9){
        konami[codeint] = 1;
      }
  }
  else if(key == 'f'){
      if(codeint == 8){
        konami[codeint] = 1;
      }
  }
  codeint++;
}

public boolean sketchFullScreen(){
  return true;
}
class Food{
  int xPos;
  int yPos;
  
  Food(){
    xPos = round(random(23)) + 1;
    yPos = round(random(23)) + 1;
  }
  
  public void move(){
    xPos = round(random(23)) + 1;
    yPos = round(random(23)) + 1;
  }
  
}
class Segment{
  int xPos;
  int yPos;
  
  Segment(int x, int y){
    xPos = x;
    yPos = y;
  }
  
}
class Snake{
  int size = 1;
  String direction;
  //Segment[] segs = new Segment[1];
  ArrayList<Segment> segs = null;
  int xPos = 1;
  int yPos = 1;
  int tail = 0;
  int difficulty = 0;
  
  Snake(){
    //segs[0] = new Segment(5,5);
    segs = new ArrayList<Segment>();
    segs.add(new Segment(5,5));
    direction = "Right";
    xPos = 5;
    yPos = 5;
    int x = segs.get(0).xPos;
  }
  
  public void addSegment(){
    //int x = segs[tail].xPos;
    //int y = segs[tail].yPos;
    //segs[size] = new Segment(x,y);
    int x = segs.get(tail).xPos;
    int y = segs.get(tail).yPos;
    segs.add(tail+1, new Segment(x,y));
    tail = tail+1;
    size++;
    difficulty = round(size/5);
  }
  
  
  
  public void setDirection(String s){
    if(s.equals("Up")){
      if(!direction.equals("Down")) direction = s;
    }
    if(s.equals("Down")){
      if(!direction.equals("Up")) direction = s;
    }
    if(s.equals("Left")){
      if(!direction.equals("Right")) direction = s;
    }
    if(s.equals("Right")){
      if(!direction.equals("Left")) direction = s;
    }
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--hide-stop", "Main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
