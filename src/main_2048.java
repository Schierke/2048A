

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;




public class main_2048 extends JPanel {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private static final int Tile_size =64;
	 private static final String FONT_NAME = "Arial";
	 private static final Color BG_COLOR = new Color(0xbbada0);
	private static final int Tile_margin = 16;
	private static Node Tile=new Node(); ;
	  boolean myWin = false;
	  boolean myLose = false;
	public static int offset(int arg){
		return arg*(Tile_size+Tile_margin)+Tile_margin;
	}

	main_2048(){
		
		setFocusable(true);
		 addKeyListener(new KeyAdapter() {
		      @Override
		      public void keyPressed(KeyEvent e) {
		    	  if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		              resetGame();
		            }
		    	  else{
		    	  if (!canMove(Tile.matrix)) {
			          myLose = true;
			       
			        }

			      if (!myWin && !myLose) {
			    	 switch (e.getKeyCode()) {
			            case KeyEvent.VK_LEFT:
			            {
			            	Node val=new Node(Tile);
			            	val.up();
							if(isEqual(val.matrix,Tile.matrix)==1||val.score!=Tile.score)
			            	{
								Tile.up();
						    	addNumber(Tile.matrix);

			            	}
			            }
			              break;
			            case KeyEvent.VK_RIGHT:
			              {
			            	  Node val=new Node(Tile);
				            	val.down();
								if(isEqual(val.matrix,Tile.matrix)==1||val.score!=Tile.score)
				            	{
									Tile.down();
							    	addNumber(Tile.matrix);

				            	}
			              }
			              break;
			            case KeyEvent.VK_UP:
			            {
			            	Node val=new Node(Tile);
			            	val.left();
							if(isEqual(val.matrix,Tile.matrix)==1||val.score!=Tile.score)
			            	{
								Tile.left();
						    	addNumber(Tile.matrix);

			            	}
			            }
			              break;
			            case KeyEvent.VK_DOWN:
			              {
			            	  Node val=new Node(Tile);
				            	val.right();
								if(isEqual(val.matrix,Tile.matrix)==1||val.score!=Tile.score)
				            	{
									Tile.right();
							    	addNumber(Tile.matrix);

				            	}
			              }
			              break;
			            case KeyEvent.VK_SPACE:
			            {
					    	 int s=yolo();
					    	 if(s==0) Tile.left();
						    	else if(s==1) Tile.right();
						    	else if(s==2) Tile.up();
						    	else if(s==3) Tile.down();	
						    	addNumber(Tile.matrix);

			            }
			            break;
			          }
			    	  
				    	
				    				       
			    	 }

			        if (!myWin && !canMove(Tile.matrix)) {
			          myLose = true;
			        }
		    	  }
			        repaint();
		      }
		    });
		 
		 resetGame(); 
	
	}
	public static int isEqual(int[][] tile1,int[][] tile2){
		for(int i=0;i<=3;i++)
			for(int j=0;j<=3;j++)
				if(tile1[i][j]!=tile2[i][j]) return 1;
		return 0;
	}
	public static int  yolo(){
		
		Map<String, Object> result= new HashMap<String, Object>();
		//result=minimax(Tile,5,true);
		result=alphaBeta(Tile,5,0,100000,true);
		int d=(int) result.get("direction");
		return d;
}
	private static int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore) {
        int score = (int) (actualScore+Math.log(actualScore)*numberOfEmptyCells -clusteringScore);
        return Math.max(score, Math.min(actualScore, 1));
    }
	private static int calculateClusteringScore(int[][] boardArray) {
        int clusteringScore=0;
        
        int[] neighbors = {-1,0,1};
        
        for(int i=0;i<boardArray.length;++i) {
            for(int j=0;j<boardArray.length;++j) {
                if(boardArray[i][j]==0) {
                    continue; //ignore empty cells
                }
                
                //clusteringScore-=boardArray[i][j];
                
                //for every pixel find the distance from each neightbors
                int numOfNeighbors=0;
                int sum=0;
                for(int k : neighbors) {
                    int x=i+k;
                    if(x<0 || x>=boardArray.length) {
                        continue;
                    }
                    for(int l : neighbors) {
                        int y = j+l;
                        if(y<0 || y>=boardArray.length) {
                            continue;
                        }
                        
                        if(boardArray[x][y]>0) {
                            ++numOfNeighbors;
                            sum+=Math.abs(boardArray[i][j]-boardArray[x][y]);
                        }
                        
                    }
                }
                
                clusteringScore+=sum/numOfNeighbors;
            }
        }
        
        return clusteringScore;
    }
	public static int getNumberofEmptyCell(int[][] Tile1){
		
		int k=0;
		for(int i=0;i<=3;i++)
			for(int j=0;j<=3;j++)
				if(Tile1[i][j]==0){
					
					k++;
				}
		return k;
	}
	public static Map<String, Object> minimax(Node Tile1, int depth,boolean maximizingplayer){
		int bestScore;
		Map<String, Object> result= new HashMap<String, Object>();
		int direction = 0;
		
		if(depth==0 || !canMove(Tile1.matrix)) {
			bestScore=heuristicScore(Tile1.score,getNumberofEmptyCell(Tile1.matrix),calculateClusteringScore(Tile1.matrix));
			// bestScore=Tile1.score;
		}
		else{
		if(maximizingplayer==true){
			
			bestScore=Integer.MIN_VALUE;
			
			
			Map<String, Object> currentResult= new HashMap<String, Object>();
			for(int dem=0;dem<=3;dem++){
				Node val=new Node(Tile1);
				if(dem==0 )
				{
					val.left();
				if(isEqual(val.matrix,Tile1.matrix)==1 ||val.score!=Tile1.score)
						{
							currentResult=minimax(val,depth-1,false);
						}
				else continue;
				}
				
				
				
				if(dem==1 )
				{
					val.right();
				if(isEqual(val.matrix,Tile1.matrix)==1 || val.score!=Tile1.score)
						currentResult=minimax(val,depth-1,false);
				else continue;
				}
				
				
				if(dem==2 )
				{
					val.up();
				if(isEqual(val.matrix,Tile1.matrix)==1 ||val.score!=Tile1.score)
						currentResult=minimax(val,depth-1,false);
				else continue;
				}
					
				if(dem==3 )
				{
					val.down();
				if(isEqual(val.matrix,Tile1.matrix)==1 ||val.score!=Tile1.score)
						currentResult=minimax(val,depth-1,false);
				else continue;
				}
				
				int currentScore=(int) currentResult.get("Score");
				if(bestScore<currentScore)
				{
					bestScore=currentScore;
				
					direction=dem;
				}
				}
		}
		else{
			bestScore=Integer.MAX_VALUE;
			
			int []possibleValue={2,4};
			
			Map<String, Object> currentResult= new HashMap<String, Object>();
			int [] getEmpty=getEmptyCell(Tile1.matrix);
			if(getEmpty[0]==0 && getEmpty[1]==0) bestScore=0;
			for(Integer k: getEmpty){
				int i=k/4;
				int j=k%4;
			for(int value:possibleValue){
				Node val=new Node(Tile1);
				
				val.matrix[i][j]=value;
				currentResult = minimax(val, depth-1,true);
				int currentScore=(int) currentResult.get("Score");
				if(bestScore>currentScore)
				{
					bestScore=currentScore;
				}
				
			}
		}
		}
		}
	  	result.put("Score",bestScore);
		result.put("direction",direction);
		return result;
	}	
	public static Map<String, Object> alphaBeta(Node Tile1, int depth,int alpha,int beta,boolean maximizingplayer){
		int bestScore;
		Map<String, Object> result= new HashMap<String, Object>();
		int direction = 0;
		
		if(depth==0 || !canMove(Tile1.matrix)) {
			bestScore=heuristicScore(Tile1.score,getNumberofEmptyCell(Tile1.matrix),calculateClusteringScore(Tile1.matrix));
			// bestScore=Tile1.score;
		}
		else{
		if(maximizingplayer==true){
			
			bestScore=Integer.MIN_VALUE;
			Map<String, Object> currentResult= new HashMap<String, Object>();
			for(int dem=0;dem<=3;dem++){
				Node val=new Node(Tile1);
				if(dem==0 )
				{
					val.left();
				if(isEqual(val.matrix,Tile1.matrix)==1 ||val.score!=Tile1.score)
						{
							currentResult=alphaBeta(val,depth-1,alpha,beta,false);
						}
				else continue;
				}
				
				
				
				if(dem==1 )
				{
					val.right();
				if(isEqual(val.matrix,Tile1.matrix)==1 || val.score!=Tile1.score)
					currentResult=alphaBeta(val,depth-1,alpha,beta,false);
				else continue;
				}
				
				
				if(dem==2 )
				{
					val.up();
				if(isEqual(val.matrix,Tile1.matrix)==1 ||val.score!=Tile1.score)
					currentResult=alphaBeta(val,depth-1,alpha,beta,false);
				else continue;
				}
					
				if(dem==3 )
				{
					val.down();
				if(isEqual(val.matrix,Tile1.matrix)==1 ||val.score!=Tile1.score)
					currentResult=alphaBeta(val,depth-1,alpha,beta,false);
				else continue;
				}
				
				int currentScore=(int) currentResult.get("Score");
				if(bestScore<currentScore)
				{
					bestScore=currentScore;
				
					direction=dem;
				}
				if(alpha<bestScore){
					alpha=bestScore;
				}
				if(beta<=alpha) break; //beta cut-off
				}
		}
		else{
			bestScore=Integer.MAX_VALUE;
			
			int []possibleValue={2,4};
			
			Map<String, Object> currentResult= new HashMap<String, Object>();
			int [] getEmpty=getEmptyCell(Tile1.matrix);
			if(getEmpty[0]==0 && getEmpty[1]==0) bestScore=0;
			for(Integer k: getEmpty){
				int i=k/4;
				int j=k%4;
			for(int value:possibleValue){
				Node val=new Node(Tile1);
				
				val.matrix[i][j]=value;
				currentResult=alphaBeta(val,depth-1,alpha,beta,true);
				int currentScore=(int) currentResult.get("Score");
				if(bestScore>currentScore)
				{
					bestScore=currentScore;
				}
				if(beta>bestScore) beta=bestScore;
				if(beta<=alpha) break;// Alpha cut-off
			}
		}
		}
		}
	  	result.put("Score",bestScore);
		result.put("direction",direction);
		return result;
	}	

	public static int[] getEmptyCell(int [][] tile1){
	int[] getCell=new int[16];
	int k=0;
	for(int i=0;i<=3;i++)
		for(int j=0;j<=3;j++)
			if(tile1[i][j]==0){
				getCell[k]=4*i+j;
				k++;
			}
	return getCell;
}
public void resetGame() {
	  
	    myWin = false;
	    myLose = false;
	    Tile.matrix = new int[4][4];
	   Tile.score=0;
	    addNumber(Tile.matrix);
	    addNumber(Tile.matrix);
	  }
private static void addNumber(int matrix1[][]){
	int check=0;
	int index1;
	int index2;
	if(!isFull(matrix1)){
	do{
	index1=(int)(Math.random()*4);
	
	 index2=(int)(Math.random()*4);
	if(matrix1[index1][index2]==0) check=1;
	}
	while(check==0);
	matrix1[index1][index2]=Math.random()<0.9?2:4;
	}
	
	
}
public static boolean isFull(int Tile1[][]){
	
	for(int x=0;x<=3;x++)
		for(int y=0;y<=3;y++){
			if(Tile1[x][y]==0) return false;
			
		}
	return true;
}
public static boolean canMove(int[][] Tile1){
	if(isFull(Tile1) == false) return true;
	else{
		
		for(int i=0;i<=3;i++)
			for(int j=0;j<=3;j++){
				int valeur = Tile1[i][j];
				if((i<3 && valeur == Tile1[i+1][j]) ||(j<3 && valeur == Tile1[i][j+1])) return true;
			}
	}
	return false;
}
///////////////////********************************///////////////////////////////



	@Override
	
	public void paint(Graphics g){
		super.paint(g);
		
		 g.setColor(BG_COLOR);
		 g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		    for (int x = 0; x < 4; x++) {
		      for (int y = 0; y < 4; y++) {
		        drawTile(Tile.matrix[x][y],g, x, y);
		      }
		    
		  }
	}
	
	public Color getForeground(int value) {
	      return value < 16 ? new Color(0x776e65) :  new Color(0xf9f6f2);
	    }
	public void drawTile(int a,Graphics g1, int x,int y){
		Graphics2D g= (Graphics2D) g1;
		 g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		  int xoff=offset(x);
		  int yoff=offset(y);
		  g.setColor(getBackground(a));
		  g.setFont(new Font("Serif",Font.BOLD,18));
		  g.drawString("How to play:", 360, 30);
		  g.setFont(new Font("Serif",Font.BOLD,14));
		  g.drawString("- Using arrow keys to move", 350, 60);
		  g.drawString("- Using space key for auto-playing", 350, 80);
		  g.setFont(new Font("Monospace",Font.BOLD,20));
		  g.drawString("ENJOY!!",420,200);
		  g.fillRoundRect(xoff, yoff, Tile_size, Tile_size,14,14);
		  String s = String.valueOf(a);
		  g.setColor(getForeground(a));
		    final int size = a < 100 ? 36 : a < 1000 ? 32 : 24;
		    final Font font = new Font(FONT_NAME, Font.BOLD, size);
		    g.setFont(font);
		    final FontMetrics fm = getFontMetrics(font);
		    
		    final int w = fm.stringWidth(s);
		    
		    final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];
		
		    if (a != 0)
		      g.drawString(s, xoff + (Tile_size-w) / 2, yoff + Tile_size - (Tile_size-h ) / 2 - 2);

		    if (myWin || myLose) {
		      g.setColor(new Color(255, 255, 255, 30));
		      g.fillRect(0, 0, getWidth(), getHeight());
		      g.setColor(new Color(78, 139, 202));
		      g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
		      if (myWin) {
		        g.drawString("You won!", 68, 150);
		      }
		      if (myLose) {
		        g.drawString("Game over!", 50, 130);
		        g.drawString("You lose!", 64, 200);
		      }
		      if (myWin || myLose) {
		        g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
		        g.setColor(new Color(128, 128, 128, 128));
		        g.drawString("Press ESC to play again", 80, getHeight() - 40);
		      }
		    }
		    g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
		    g.drawString("Score: " + Tile.score, 200, 365);

	}
	 public Color getBackground(int value) {
	      switch (value) {
	        case 2:    return new Color(0xeee4da);
	        case 4:    return new Color(0xede0c8);
	        case 8:    return new Color(0xf2b179);
	        case 16:   return new Color(0xf59563);
	        case 32:   return new Color(0xf67c5f);
	        case 64:   return new Color(0xf65e3b);
	        case 128:  return new Color(0xedcf72);
	        case 256:  return new Color(0xedcc61);
	        case 512:  return new Color(0xedc850);
	        case 1024: return new Color(0xedc53f);
	        case 2048: return new Color(0xedc22e);
	      }
	      return new Color(0xcdc1b4);
	    }	


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame game = new JFrame();
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setTitle("2048");
		game.setSize(800, 420);
	//	game.add(createMenuBar());
		game.add(new main_2048());
		
		game.setVisible(true);
	}

}
