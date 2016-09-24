public  class Node {
	public int[][] matrix;
	int score;
	public Node(){
		matrix=new int[4][4];
		score=0;
	}
	public Node(int[][] a,int b){
		matrix=a;
		score=b;
	}
	public Node(Node copy){
		this(copy.getMatrix(),copy.getScore());
	}
	public int[][] getMatrix(){
		return matrix;
	}
	public int getScore(){
		return score;
	}
	public void left(){
		 int matrix1[][]= new int[4][4];
		    for(int s=0; s<=3;s++)
			{
				for (int d=0;d<=3;d++)
				{
					matrix1[s][d]=matrix[s][d];
				}
			}
		    for(int i=0; i<=3;i++)
		        for (int j=3;j>=0;j--)
		        {
		            if (matrix1[i][j] !=0) 
		            {
		                for (int k=j; k>=0; k--)
		                    if (matrix1[i][k]==0)
		                    {       
		                        for (int l=k; l<=2;l++) 
		                            matrix1[i][l]=matrix1[i][l+1];    
		                        matrix1[i][3]=0;
		                    }
		                break;
		            }

		        }

		    for(int i=0; i<=3;i++)
		        for (int j=0;j<=2;j++)
		        {

		            if (matrix1[i][j]==matrix1[i][j+1])  
		            {
		            	int num=matrix1[i][j];
		    			score+=2*num;
		                matrix1[i][j]=matrix1[i][j]*2;
		                for (int l=j+1; l<=2;l++) 
		                    matrix1[i][l]=matrix1[i][l+1];    
		                matrix1[i][3]=0;             
		            }

		        }
		matrix=matrix1;
	}
	public void right(){
		int matrix1[][]= new int[4][4];
		for(int s=0; s<=3;s++)
		{
			for (int d=0;d<=3;d++)
			{
				matrix1[s][d]=matrix[s][d];
			}
		}
		for(int i=0; i<=3;i++)
			for (int j=0;j<=3;j++)
			{
				if (matrix1[i][j] !=0) 
				{
					for (int k=j; k<=3; k++)
						if (matrix1[i][k]==0)
						{		
							for (int l=k; l>=1;l--) 
								matrix1[i][l]=matrix1[i][l-1];	
							matrix1[i][0]=0;
						}
					break;
				}
			}
		//gop 2 so giong nhau
		for(int i=0; i<=3;i++)
			for (int j=3;j>=1;j--)
			{
				
				//xet lan 1
				if (matrix1[i][j]==matrix1[i][j-1])  
				{
					int num=matrix1[i][j];
					score+=2*num;
					matrix1[i][j]=matrix1[i][j]*2;
					for (int l=j-1; l>=1;l--) 
						matrix1[i][l]=matrix1[i][l-1];	
					matrix1[i][0]=0;				
				}
				

			}
		matrix=matrix1;
	}
	public void up(){
		int[][] matrix1 = new int[4][4];
		for(int s=0; s<=3;s++)
		{
			for (int d=0;d<=3;d++)
			{
				matrix1[s][d]=matrix[s][d];
			}
		}
		for(int j=0; j<=3;j++)
			for (int i=3;i>=0;i--)
			{
				if (matrix1[i][j] !=0) 
				{
					for (int k=i; k>=0; k--)
						if (matrix1[k][j]==0)
						{		
							for (int l=k; l<=2;l++) 
								matrix1[l][j]=matrix1[l+1][j];	
							matrix1[3][j]=0;
						}
					break;
				}
				
			}

		//gop 2 so giong nhau
		for(int j=0; j<=3;j++)
			for (int i=0;i<=2;i++)
			{
				//xet lan 1
				if (matrix1[i][j]==matrix1[i+1][j])  
				{
					int num=matrix1[i][j];
					score+=2*num;
					matrix1[i][j]=matrix1[i][j]*2;
					for (int l=i+1; l<=2;l++) 
						matrix1[l][j]=matrix1[l+1][j];	
					matrix1[3][j]=0;				
				}
				
			}
		matrix=matrix1;
	}
	public void down(){
		int[][] matrix1 = new int[4][4];
		for(int s=0; s<=3;s++)
		{
			for (int d=0;d<=3;d++)
			{
				matrix1[s][d]=matrix[s][d];
			}
		}
		for(int j=0; j<=3;j++)
			for (int i=0;i<=3;i++)
			{
				if (matrix1[i][j] !=0) 
				{
					for (int k=i; k<=3; k++)
						if (matrix1[k][j]==0)
						{		
							for (int l=k; l>=1;l--) 
								matrix1[l][j]=matrix1[l-1][j];	
							matrix1[0][j]=0;
						}
					break;
				}
			}
		//gop 2 so giong nhau
		for(int j=0; j<=3;j++)
			for (int i=3;i>=1;i--)
			{
				//xet lan 1
				if (matrix1[i][j]==matrix1[i-1][j])  
				{
					int num=matrix1[i][j];
					score+=2*num;
					matrix1[i][j]=matrix1[i][j]*2;
					for (int l=i-1; l>=1;l--) 
						matrix1[l][j]=matrix1[l-1][j];	
					matrix1[0][j]=0;				
				}
				

			}
		matrix=matrix1;
	}
}

