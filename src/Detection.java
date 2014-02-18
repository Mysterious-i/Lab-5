import lejos.nxt.ColorSensor;
import lejos.nxt.UltrasonicSensor;

//Alessandro Parisi 260529758
//Shahrzad Tighnavardmollasarae 260413622

/*This class is used to maintain all the code that is used
 * to detect objects and determine what is the type of the object.
 */
public class Detection {
	
	private ColorSensor colorSensor;
	private UltrasonicSensor us;
	
	public Detection(ColorSensor colorSensor, UltrasonicSensor us){
		this.colorSensor = colorSensor;
		this.us = us;
		this.colorSensor.setFloodlight(true);
	}
	
	//Method returning the color the color sensor reads
	public int getColor(){
		return colorSensor.getColor().getBlue();
	}
	
	//Methods returning the distance the ultrasonic sensor reads
	public int getDistance(){
		 return us.getDistance();
	}
	
	/*Method that calculates the block type depending on the distance read by the
	 * ultrasonic sensor and the color of the object read by the color sensor.
	 */
	public String getBlockType(){
		String s;
		if(us.getDistance() < 26){
			if(getColor() > 26){
				s = "Blue Block";
			}
			else
				s = "Wall Block";
		}
		else
			s = "No Block";
		
		return s;
	}
	
	/*Returns a number representing the different block types depending.
	 * 0 - No Block
	 * 1 - Wall Block
	 * 2 - Blue Block
	 */
	public int getBlockNumber(){
		String s;
		s = getBlockType();
		if (s.equals("Blue Block")){
			return 2;
		}
		else if (s.equals("Wall Block")){
			return 1;
		}
		return 0;
	}
}
