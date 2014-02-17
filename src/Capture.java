
public class Capture {
	
	private Odometer odometer;
	private Navigation navigation;
	private Detection detection;
	public static int i = 0;
	public static int j = 0;
	
	private boolean pastLanes[][] = new boolean[7][3];
	private boolean movingVerticle;
	
	public Capture(Odometer odometer, Detection detection) {
		this.odometer = odometer;
		this.navigation = odometer.getNavigation();
		this.detection = detection;
		this.movingVerticle = false;
	}
	
	public void capture(){
		int blockNumber;
		int turningAngle = 0;
			
		if(detection.getDistance() < 20){
			blockNumber = detection.getBlockNumber();
			if(blockNumber == 0){
				pushToFinish();
			}
			else if(blockNumber == 1){
				movingVerticle = true;
				j += 15;
				turningAngle = 0;
			}
			else{
				movingVerticle = false;
				i += 15;
				turningAngle = 90;
			}
		}
		else{
			if(movingVerticle){
				j += 15;
				turningAngle = 0;
			}
			else{
				i += 15;
				turningAngle = 90;
			}
		}
		navigation.travelTo(i, j);
		navigation.turnTo(turningAngle, true);
		
		capture();
			
		/*	if(detection.getDistance() < 150){
				pastLanes[(int)odometer.getX() % 15][ (int)odometer.getY() % 15] = false;
			}
			else{
				pastLanes[(int)odometer.getX() % 15][ (int)odometer.getY() % 15] = true;
			}
				
		}*/
	}
	
	public void goToPastLane(){
	}
	public void pushToFinish(){
		
	}
}
