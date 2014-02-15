import lejos.nxt.ColorSensor;
import lejos.nxt.UltrasonicSensor;


public class Detection {
	
	private ColorSensor colorSensor;
	private UltrasonicSensor us;
	
	public Detection(ColorSensor colorSensor, UltrasonicSensor us){
		this.colorSensor = colorSensor;
		this.us = us;
		this.colorSensor.setFloodlight(true);
	}
	
	public int getColor(){
		return colorSensor.getColor().getBlue();
	}
	public int getDistance(){
		 return us.getDistance();
	 }
	 
	public boolean isFlagBlock(){
		boolean isBlock = false;
		switch(getDistance()){
		case 0: 
			if(getColor() > 20){
				isBlock = true;
			}
			break;
		case 5: 
			if(getColor() > 20){
				isBlock = true;
			}
			break;
		case 10: 
			if(getColor() > 20){
				isBlock = true;
			}
			break;
		case 15: 
			if(getColor() > 20){
				isBlock = true;
			}
			break;
		case 20: 
			if(getColor() > 20){
				isBlock = true;
			}
			break;
		case 25: 
			if(getColor() > 20){
				isBlock = true;
			}
			break;
		case 30: 
			if(getColor() > 20){
				isBlock = true;
			}
			break;
		}
		
		return isBlock;
	}
}
