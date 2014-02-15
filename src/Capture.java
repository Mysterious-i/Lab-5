
public class Capture {
	
	private Odometer odometer;
	private Navigation navigation;
	private Detection detection;
	public static int i;
	private boolean pastLanes[][] = new boolean[7][3];
	
	public Capture(Odometer odometer, Detection detection) {
		this.odometer = odometer;
		this.navigation = odometer.getNavigation();
		this.detection = detection;
	}
	
	public void capture(){
		
		int j = 0;
		for(int i = 15; i < 195; i=i+15){
			
			if(detection.getDistance() < 30){
				goToPastLane();
			}
			
			navigation.travelTo(j, i);
			navigation.turnTo(0, true);
			if(detection.getDistance() < 150){
				pastLanes[(int)odometer.getX() % 15][ (int)odometer.getY() % 15] = false;
			}
			else{
				pastLanes[(int)odometer.getX() % 15][ (int)odometer.getY() % 15] = true;
			}
				
		}
		
		/*
		for(int i = 15; i < 75; i=i+15){
			
			if(detection.isFlagBlock()){
				break;
			}
			
			navigation.travelTo(i, 0);
			navigation.turnTo(0, true);
			
		}
		*/
	}
	
	public void goToPastLane(){
	}
}
