import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
public class realsim {
		public static void main(String[] args) {
			String str ="";
			int count =0;
			String tokenstr = "";
			int i = 0;
			int j = 0;
			double quanta[] = new double[3];
			double percent[] = new double[3];
			int blocksize = 500;
			double timer = 0;
			boolean notdone= false;
			//Reading file with CPU bursts and processes
			int cpuburstcount = 0;
	        int internalcount = 0;
	        int calcpuburst = 0;
	        double division =0;
	        try{
	            File myfile = new File("process.txt");
	            Scanner scan= new Scanner(myfile);
	            while(scan.hasNextLine())
	            {
	                        str=scan.nextLine();
	                        StringTokenizer strtok = new  StringTokenizer(str);
	                        while(strtok.hasMoreTokens())
	                        {
	                            tokenstr=strtok.nextToken();
	                            calcpuburst = (tokenstr.length()+1)/2;
	                            if(cpuburstcount<calcpuburst)
	                            	cpuburstcount = calcpuburst;
	                            
	                        }
	                   internalcount++;

	            }//  end while(scan.hasNextLine())
	        }// end try
	        catch (IOException ioe) {
	        System.out.println("Could not process file");
	        }
			double cpubursts [][] = new double[internalcount][cpuburstcount];
			String arrayofStrings[] = new String [internalcount];
	    	int xcounter = 0;

	        try{
	        	int ycounter = 0;
	            File myfile = new File("process.txt");
	            Scanner scan= new Scanner(myfile);
	            while(scan.hasNextLine())
	            {
	                        str=scan.nextLine();
	                        StringTokenizer strtok = new  StringTokenizer(str);
	                        while(strtok.hasMoreTokens())
	                        {
	                            tokenstr=strtok.nextToken();
	                            if (xcounter % 2 == 1)
	                            {
	                            	String []results = tokenstr.split(",");
	                            	for(int x=0; x<results.length;x++)
	                            	{
	                            		cpubursts[ycounter][x] = Integer.parseInt(results[x]);
	                            	}
	                            }

	                            xcounter++;
	                            
	                        }
	                   ycounter++;

	            }//  end while(scan.hasNextLine())
	        }// end try
	        catch (IOException ioe) {
	        System.out.println("Could not process file");
	        }
	        
	        for(int x=0; x< arrayofStrings.length; x++)
	        {
	        	for(int y =0; y<=cpuburstcount; y++)
	        	{
	        	//cpubursts[x][y] = Integer.parseInt(results[y]);
	        	}
	        	
	        }

	        
			//Remove and Read from file
			//double cpubursts [][] = new double[][]{{2,3,3},{4,10}};
			double cpuremaining[][] = cpubursts;
			//Number of cpu process that is the rows in the cpubursts
			double turnaroundtime[] = new double[cpubursts.length];
			double waitime[] = new double[cpubursts.length];
			int currentqueue =0;
			boolean entered = false;
			boolean entered1 = false;
			//int rows = 0;
			//int columns =0;
			int countercst =0;
			boolean IOburstcomplete[] = new boolean[cpubursts.length];
			for(int x = 0; x<cpubursts.length; x++)
			{
				for(int y =0; y<cpubursts[x].length;y++)
				{
					//columns++;
				}
				//rows++;
				//System.out.print("\n");
			}
			//Copying array from cpubursts to cpuremaining
			for (int y = 0; y < cpubursts.length; y++){
				for (int x = 0; x < cpubursts[y].length; x++){
					cpuremaining[y][x] = cpubursts[y][x];
					//System.out.print(cpuremaining[y][x]+ " ");
				}
				//System.out.print("\n");
			}
			double cst = 0.0;
			//Reading the quantas and cst 
			try{
				File myfile = new File("cstq.txt");
	            Scanner scan= new Scanner(myfile);
	            while(scan.hasNextLine())
	            {
	            			str=scan.nextLine();
	            			StringTokenizer strtok = new  StringTokenizer(str);
	            			while(strtok.hasMoreTokens())
	            			{
	            			    tokenstr=strtok.nextToken();
	            			    
	                  			//System.out. println(tokenstr);
	            			    if(count ==1)
	            			    {
	                				
	                				cst = Double.parseDouble(tokenstr);
	                				//System.out.println(cst);

	            			    }
	            				if(count == 3|| count ==6 || count == 9)
	            				{
	            				// Remove the commas from the string
	            					tokenstr = tokenstr.substring(0, tokenstr.length()-1);
	            					quanta[i] = Double.parseDouble(tokenstr);
	            					//System.out.println(quanta[i]);
	            					if (i< 2)
	            					{
	            						i++;
	            					}
	            				}
	            				if(count == 4|| count ==7 || count == 10)
	            				{
	            				// Remove the commas from the string
	            					percent[j] = Double.parseDouble(tokenstr);
	            					//System.out.println(percent[j]);
	            					if (j< 2)
	            					{
	            						j++;
	            					}
	            				}
	                            count++;
	            			}// end while(strtok.hasMoreTokens())

	            			
	            }//  end while(scan.hasNextLine())


			}// end try
			catch (IOException ioe) {
			System.out.println("Could not cstq file");
			}
			//System.out.println("The number of strings tokens read is:" + count);
			if(percent[0]+percent[1]+percent[2] != 1)
			{
				System.out.println("Percentages must add up to one. Check cstq.txt file and configure correctly");
				System.exit(0);
			}
			double blocktimeq1 = percent[0] * blocksize;
			double blocktimeq2 = percent[1] * blocksize;
			double blocktimeq3 = percent[2] * blocksize;
			System.out.println("blocktimeq1 "+ blocktimeq1);
			System.out.println("blocktimeq2 "+ blocktimeq2);
			System.out.println("blocktimeq3 "+ blocktimeq3);
			//size of the number of processes
			double complete[] =new double[cpubursts.length];
					
				//Break statement label
				endqueue1:
				do
				{
					notdone = false;
					for(int x = 0; x<cpubursts.length; x++)
					{
						complete[x] = cpubursts[x].length;
						for(int y =0; y<cpubursts[x].length;y++)
						{
							if(cpuremaining[x][y]>=quanta[currentqueue])
							{
								cpuremaining[x][y] = cpuremaining[x][y] - quanta[currentqueue];
								timer+=quanta[currentqueue];
								waitime[x]+= quanta[currentqueue];
								turnaroundtime[x]+= quanta[currentqueue];
								if(cpuremaining[x][y]>0 && currentqueue<2)
									currentqueue++;
								if(timer >blocktimeq1)
									break endqueue1;
								

							}
							else if(cpuremaining[x][y]==0)
							{
								//System.out.println("The process at p"+x+" burst"+y+ " completed with queue 1");
								complete[x] = complete[x] -1;
							}
							else
							{
								timer += quanta[currentqueue] - cpuremaining[x][y];
								waitime[x]+= quanta[currentqueue] - cpuremaining[x][y];
								turnaroundtime[x]+= quanta[currentqueue] - cpuremaining[x][y];
								cpuremaining[x][y]= 0;
								complete[x] = complete[x] -1;
								double divison = (cpuremaining[x][y])/(quanta[currentqueue]);
								if(division>0.25 && currentqueue>0)
									currentqueue--;
							}
							countercst++;
							
						}//End horizontal iteration
						if(complete[x]>0 && complete[x]!=1)
						{
							//Adding context switch time to timer and and wait time
							timer+= cst;
							waitime[x]+= cst;
							turnaroundtime[x]+= cst;
							//countercst++;
							if(timer >blocktimeq1)
								break endqueue1;						

						}
						else if (complete[x]==0 && IOburstcomplete[x]== false)
						{
							System.out.println("IO burst loop about to start");
							timer+=50;
							waitime[x]+=50;
							turnaroundtime[x]+=50;
							IOburstcomplete[x] = true;
							if(timer >blocktimeq1)
							break endqueue1;

						}// end else if

						
					}// End vertical iteration in 2 dimensional array.
					for(int x = 0; x<cpubursts.length; x++)
					{
						for(int y =0; y<cpubursts[x].length;y++)
							{
								if(cpuremaining[x][y]>0)
									notdone = true;
							}
					}
					
				}while(timer < blocktimeq1 && notdone);
				//Add last IO burst to timer
				timer += 50;
				//System.out.println("Queue 1");
				double realturntime = 0;
				double sumwaitime = 0;
				for(int x = 0; x<cpubursts.length; x++)
				{	
					sumwaitime+=waitime[x];
				}
				for(int x = 0; x<cpubursts.length; x++)
				{	
					waitime[x]= sumwaitime - waitime[x];
					System.out.println("Wait time for p" +x +": "+ waitime[x]);
					if(x==cpubursts.length-1)
						realturntime = turnaroundtime[x] + waitime[x] + 50;
					else
						realturntime = turnaroundtime[x] + waitime[x];
					System.out.println("Turn around time for p" +x +": "+ realturntime);
					
				}// End of printing for queue 1
				
				//Queue 2 begins here
				currentqueue++;
				endqueue2:
				while(timer < blocktimeq1+blocktimeq2 && notdone)
				{
					entered = true;
					notdone = false;
					for(int x = 0; x<cpubursts.length; x++)
					{
						complete[x] = cpubursts[x].length;
						for(int y =0; y<cpubursts[x].length;y++)
						{
							if(cpuremaining[x][y]>=quanta[currentqueue])
							{
								cpuremaining[x][y] = cpuremaining[x][y] - quanta[currentqueue];
								timer+=quanta[currentqueue];
								waitime[x]+= quanta[currentqueue];
								turnaroundtime[x]+= quanta[currentqueue];
								if(cpuremaining[x][y]>0 && currentqueue<2)
									currentqueue++;
								if(timer >blocktimeq1+blocktimeq2)
									break endqueue2;
								

							}
							else if(cpuremaining[x][y]==0)
							{
								System.out.println("The process at p"+x+" burst"+y+ " completed with queue 2");
								complete[x] = complete[x] -1;
							}
							else
							{
								timer += quanta[currentqueue] - cpuremaining[x][y];
								waitime[x]+= quanta[currentqueue] - cpuremaining[x][y];
								turnaroundtime[x]+= quanta[currentqueue] - cpuremaining[x][y];
								cpuremaining[x][y]= 0;
								complete[x] = complete[x] -1;
								double divison = cpuremaining[x][y]/quanta[currentqueue];
								if(division>0.25 && currentqueue>0)
									currentqueue--;

							}
							countercst++;
							
						}//End horizontal iteration
						if(complete[x]>0 && complete[x]!=1)
						{
							//Adding context switch time to timer and and wait time
							timer+= cst;
							waitime[x]+= cst;
							turnaroundtime[x]+= cst;
							//countercst++;
							if(timer >blocktimeq1+blocktimeq2)
								break endqueue2;

						}
						else if (complete[x]==0 && IOburstcomplete[x]== false)
						{
							System.out.println("IO burst loop about to start");
							timer+=50;
							waitime[x]+=50;
							turnaroundtime[x]+=50;
							IOburstcomplete[x] = true;
							if(timer >blocktimeq1+blocktimeq2)
							break endqueue2;

						}// end else if

						
					}// End vertical iteration in 2 dimensional array.
					for(int x = 0; x<cpubursts.length; x++)
					{
						for(int y =0; y<cpubursts[x].length;y++)
							{
								if(cpuremaining[x][y]>0)
									notdone = true;
							}
					}
					
				}// End while loop
				//Add last IO burst to timer
				if(entered == false)
				{
					//System.out.println("Did not enter Queue 2");
				}
				else
				{
					timer += 50;
					System.out.println("Queue 2");
					for(int x = 0; x<cpubursts.length; x++)
					{	
						sumwaitime+=waitime[x];
					}
					for(int x = 0; x<cpubursts.length; x++)
					{	
						waitime[x]= sumwaitime - waitime[x];
						System.out.println("Wait time for p" +x +": "+ waitime[x]);
						if(x==cpubursts.length-1)
							realturntime = turnaroundtime[x] + waitime[x] + 50;
						else
							realturntime = turnaroundtime[x] + waitime[x];
						System.out.println("Turn around time for p" +x +": "+ realturntime);
					
					}
				}// queue 2 ends here
				
				//Queue 3 begins here
				currentqueue++;
				endqueue3:
				while(timer < blocktimeq1+blocktimeq2+blocktimeq3 && notdone)
				{
					entered1 = true;
					notdone = false;
					for(int x = 0; x<cpubursts.length; x++)
					{
						complete[x] = cpubursts[x].length;
						for(int y =0; y<cpubursts[x].length;y++)
						{
							if(cpuremaining[x][y]>=quanta[currentqueue])
							{
								cpuremaining[x][y] = cpuremaining[x][y] - quanta[currentqueue];
								timer+=quanta[currentqueue];
								waitime[x]+= quanta[currentqueue];
								turnaroundtime[x]+= quanta[currentqueue];
								if(cpuremaining[x][y]>0 && currentqueue<2)
									currentqueue++;
								if(timer >blocktimeq1+blocktimeq2 +blocktimeq3)
									break endqueue3;
								

							}
							else if(cpuremaining[x][y]==0)
							{
							//	System.out.println("The process at p"+x+" burst"+y+ " completed with queue 3");
								complete[x] = complete[x] -1;
							}
							else
							{
								timer += quanta[currentqueue] - cpuremaining[x][y];
								waitime[x]+= quanta[currentqueue] - cpuremaining[x][y];
								turnaroundtime[x]+= quanta[currentqueue] - cpuremaining[x][y];
								cpuremaining[x][y]= 0;
								complete[x] = complete[x] -1;
								double divison = cpuremaining[x][y]/quanta[currentqueue];
								if(division>0.25 && currentqueue>0)
									currentqueue--;

							}
							countercst++;
						}//End horizontal iteration
						if(complete[x]>0 && complete[x]!=1)
						{
							//Adding context switch time to timer and and wait time
							timer+= cst;
							waitime[x]+= cst;
							turnaroundtime[x]+= cst;
							//countercst++;
							if(timer >blocktimeq1+blocktimeq2+blocktimeq3)
								break endqueue3;

						}
						else if (complete[x]==0 && IOburstcomplete[x]== false)
						{
							System.out.println("IO burst loop about to start");
							timer+=50;
							waitime[x]+=50;
							turnaroundtime[x]+=50;
							IOburstcomplete[x] = true;
							if(timer >blocktimeq1+blocktimeq2+blocktimeq3)
							break endqueue3;

						}// end else if

						
					}// End vertical iteration in 2 dimensional array.
					for(int x = 0; x<cpubursts.length; x++)
					{
						for(int y =0; y<cpubursts[x].length;y++)
							{
								if(cpuremaining[x][y]>0)
									notdone = true;
							}
					}
					
				}// End while loop
				//Add last IO burst to timer
				if(entered1 == false)
				{
					//System.out.println("Did not enter Queue 3");
				}
				else
				{
					timer += 50;
					System.out.println("Queue 3");
					for(int x = 0; x<cpubursts.length; x++)
					{	
						sumwaitime+=waitime[x];
					}
					for(int x = 0; x<cpubursts.length; x++)
					{	
						waitime[x]= sumwaitime - waitime[x];
						System.out.println("Wait time for p" +x +": "+ waitime[x]);
						if(x==cpubursts.length-1)
							realturntime = turnaroundtime[x] + waitime[x] + 50;
						else
							realturntime = turnaroundtime[x] + waitime[x];
						System.out.println("Turn around time for p" +x +": "+ realturntime);
					
					}
				}
				System.out.println("Number of context switch time " +countercst);
				System.out.println("Total Simulation time " +timer +"ms");
				

			
		}

	}
