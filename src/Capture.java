//Alessandro Parisi 260529758
//Shahrzad Tighnavardmollasarae 260413622

public class Capture {
	
	private Odometer odometer;
	private Navigation navigation;
	private Detection detection;
	public static int ver= 0;
	public static int hor = 0;
	int numberOfHorizontalReadings = 0;
	public boolean oppositeDirection = false;
	private final int MAP_END_PORTION = 150;
	private final int MAX_DISTANCE = 30;
	private final int MAX_HORIZONTAL_READINGS = 3;
	private final int MAP_SEMI_MIDPOINT = 34;
	private final int END_POINT_HOR = 70;
	private final int END_POINT_VER = 190;
	private final int MAP_EXTREME_LEFT = 0;
	private final int MAP_EXTREME_RIGHT = 0;
	private final int INCREAMENT_AMOUNT = 10;
	private final int BLUE_BLOCK = 2;
	private final int WALL_BLOCK = 1;
	private final int CLOSE_ENOUGH_RANGE = 16;
	
	private boolean movingVerticle;

	public Capture(Odometer odometer, Detection detection) {
		this.odometer = odometer;
		this.navigation = odometer.getNavigation();
		this.detection = detection;
		this.movingVerticle = true;//at first moving vertically
		
	}
	
	/*This method works to capture the blue block and bring it to the destination.
	 * It works by avoiding wall blocks (and walls) while 
	 * traveling towards the destination and searching for the blue block. 
	 */
	public void capture(){
		
		
		int blockNumber;

		/*Run the loop until its at the end portion*/
		
		while(odometer.getX() <= MAP_END_PORTION){
	
			/*If the robot is moving not moving vertically increment a counter so that if the robot moves 
			 * in a horizontal direction for more than 3 intervals, he should try to move vertically again
			 * to make the robot slowly achieve its goals.
			 */
			if(!movingVerticle){
				numberOfHorizontalReadings++;
			}
			else{
				numberOfHorizontalReadings = 0;
			}
			
			//Check if there is a block in front of the robot
			if(detection.getDistance() < MAX_DISTANCE){
				blockNumber = detection.getBlockNumber();
				
				//If it found the blue block, push it to the finish
				if(blockNumber == BLUE_BLOCK){
					pushToFinish();
				}
				/*If it found a wall block, depending on whether it is moving
				 * vertically or horizontally, move it in the other orientation.
				 * (ex: if it is moving horizontally start moving it vertically)
				 * Also find the correct orientation to be moving if the robot is 
				 * moving vertically. That is to say, the robot should find out which
				 * side it should turn on (does it move in the positive horizontal
				 * or negative horizontal).
				 */
				else if(blockNumber == WALL_BLOCK){
					
					if(movingVerticle){	
						goCorrectSideAroundBlock();
						
						/*The depending on the direction of motion along the horizontal axis
						 * update the horizontal component appropriately
						 */ 
						if(oppositeDirection){
							hor -= INCREAMENT_AMOUNT;
						}
						else{
							hor += INCREAMENT_AMOUNT;
						}
						
						movingVerticle = false;
					}
					
					//If the robot is moving horizontally, start moving vertically
					else{
						ver += INCREAMENT_AMOUNT;
						movingVerticle = true;
					}
					
				}
				
				/*If the robot detects an object but it can't tell which block it is
				 * this is a misreading and so we should change to horizontal direction
				 * to be safe and see which horizontal direction to move in.
				 */
				else{
					movingVerticle = false;
					/*The depending on the direction of motion along the horizontal axis
					 * update the horizontal component appropriately
					 */ 
					if(oppositeDirection){
						hor -= INCREAMENT_AMOUNT;
					}
					else{
						hor += INCREAMENT_AMOUNT;
					}
				}
			}
			
			/*If there is no block in the robots way, the robot 
			 * should check if has been moving horizontally for less than 3 
			 * iterations, then it will keep moving horizontally in the direction
			 * specified earlier (oppositeDirection). If it is moving vertically or it moved
			 * horizontally for more than 3 iterations, start trying to move vertically again.
			 */
			else{
				if(!movingVerticle && numberOfHorizontalReadings < MAX_HORIZONTAL_READINGS){	
					movingVerticle = false;
					if(oppositeDirection){
						hor -= INCREAMENT_AMOUNT;
					}
					else{
						hor += INCREAMENT_AMOUNT;
					}
				}
				else{
					movingVerticle = true;
					ver += INCREAMENT_AMOUNT;
				}
			}
			
			//Travel the robot to the new location
			navigation.travelTo(ver, hor);
		}
		
		/*If the robot is in the end portion of the map, move to a specific corner and
		 * start searching more thoroughly for the blue block
		 */
		ver = MAP_END_PORTION;
		hor = 0;
		oppositeDirection = false;
		findBlueBlock();
	}
	
	/*This methods will find the blue block by moving left to right across the map until
	 * it scans the blue object. When it does it pushes it to the finish.
	 */
	public void findBlueBlock(){
		
		//Travel the robot to the new location
		navigation.travelTo(ver, hor);
		
		//If the robot is at an extremity change its orientation along the horizontal and keep searching
	
		if(hor > MAP_EXTREME_RIGHT || hor < MAP_EXTREME_LEFT){
			ver += 15;
			if(oppositeDirection){
				oppositeDirection = false;
			}
			else{
				oppositeDirection = true;
			}
		}
		
		/*The depending on the direction of motion along the horizontal axis
		 * update the horizontal component appropriately
		 */
		if(oppositeDirection){
			hor -= INCREAMENT_AMOUNT;
		}
		else{
			hor += INCREAMENT_AMOUNT;
		}
		
		//If you scan a blue block and it very close move it to the finish
		if(detection.getDistance() < CLOSE_ENOUGH_RANGE && detection.getBlockNumber() == BLUE_BLOCK){
			pushToFinish();
		}
		
		//Else recursively call this method and keep searching
		else{
			findBlueBlock();
		}
		
	}
	
	/*This methods returns the orientation the robot should take when 
	 * moving horizontally, depending on the distance from each wall of the robot.
	 */
	public void goCorrectSideAroundBlock(){
		
		if(odometer.getY() < MAP_SEMI_MIDPOINT){
			oppositeDirection =  false;
		}
		else{
			oppositeDirection = true;
		}
	}
	
	/*Push the block to the finishing point, throw an exception to 
	 * get out of the recursive stack.
	 */
	public void pushToFinish(){
		navigation.travelTo(END_POINT_VER,END_POINT_HOR);
	}
}
