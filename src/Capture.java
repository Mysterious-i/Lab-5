
public class Capture {
	
	private Odometer odometer;
	private Navigation navigation;
	private Detection detection;
	public static int ver= 0;
	public static int hor = 0;
	int counter = 0;
	public boolean oppositeDirection = false;
	
	private boolean movingVerticle;
	
	
	public Capture(Odometer odometer, Detection detection) {
		this.odometer = odometer;
		this.navigation = odometer.getNavigation();
		this.detection = detection;
		this.movingVerticle = true;//at first moving vertically
		
	}
	
	public void capture(){
		int blockNumber;
		
		if(odometer.getX() > 150){
			ver = 150;
			hor = 0;
			oppositeDirection = false;
			findBlueBlock();
		}
		
		if(!movingVerticle){
			counter++;
		}
		else{
			counter = 0;
		}
		
		if(detection.getDistance() < 30){
			blockNumber = detection.getBlockNumber();
			
			if(blockNumber == 2){
				pushToFinish();
			}
			else if(blockNumber == 1){
				
				if(movingVerticle){	
					goCorrectSideAroundBlock();
					if(oppositeDirection){
						hor -= 10;
					}
					else{
						hor += 10;
					}
					movingVerticle = false;
				}
				else{
					ver += 10;
					movingVerticle = true;
				}
				
			}
			else{
				movingVerticle = false;
				if(oppositeDirection){
					hor -= 10;
				}
				else{
					hor += 10;
				}
			}
		}
		else{
			if(!movingVerticle && counter < 3){	
				movingVerticle = false;
				if(oppositeDirection){
					hor -= 10;
				}
				else{
					hor += 10;
				}
			}
			else{
				movingVerticle = true;
				ver += 10;
			}
		}
		navigation.travelTo(ver, hor);
		
		capture();
	}
	/*
	public void capture(){
		navigation.travelTo(60, 0);
		capture2();
	}
	public void capture2(){

		checkAllSides();
		checkIfBlockAndType();
		

	}
	public void checkAllSides(){
		navigation.turnTo(270, true);
		checkIfBlockAndType();
		navigation.turnTo(0, true);
		checkIfBlockAndType();
		navigation.turnTo(90, true);
		checkIfBlockAndType();
	}
	

	public void checkIfBlockAndType(){
		
		int blockNumber = 0;		
		if(detection.getDistance() < 23){
			blockNumber = detection.getBlockNumber();
			if(blockNumber == 2){
				pushToFinish();
			}
			else if(blockNumber == 1){
				goCorrectSideAroundBlock();
				
			}
		}
		else if (detection.getDistance() < 100){
			moveUpToBlock();
		}
	}
	public void moveUpToBlock(){

	}
	public void goToPastLane(){
	}

*/
	public void findBlueBlock(){
		
		navigation.travelTo(ver, hor);
		
		if(hor > 59 || hor < 0){
			ver += 15;
			if(oppositeDirection){
				oppositeDirection = false;
			}
			else{
				oppositeDirection = true;
			}
		}
		
		if(oppositeDirection){
			hor -= 10;
		}
		else{
			hor += 10;
		}
		
		if(detection.getDistance() < 16){
			pushToFinish();
		}
		
		else{
			findBlueBlock();
		}
		
	}
	public void goCorrectSideAroundBlock(){
		
		if(odometer.getY() < 34){
			oppositeDirection =  false;
		}
		else{
			oppositeDirection = true;
		}
	}
	public void pushToFinish(){
		navigation.travelTo(190,70);
		throw new NullPointerException();
	}
}
