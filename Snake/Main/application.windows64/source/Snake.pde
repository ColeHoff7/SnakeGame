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
  
  void addSegment(){
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
  
  
  
  void setDirection(String s){
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
