class Food{
  int xPos;
  int yPos;
  
  Food(){
    xPos = round(random(23)) + 1;
    yPos = round(random(23)) + 1;
  }
  
  void move(){
    xPos = round(random(23)) + 1;
    yPos = round(random(23)) + 1;
  }
  
}
