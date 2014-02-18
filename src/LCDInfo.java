import lejos.nxt.LCD;
import lejos.util.Timer;
import lejos.util.TimerListener;
//Alessandro Parisi 260529758
//Shahrzad Tighnavardmollasarae 260413622
public class LCDInfo implements TimerListener{
	public static final int LCD_REFRESH = 350;
	private Odometer odo;
	private Timer lcdTimer;
	private Detection detection;
	
	// arrays for displaying data
	private double [] pos;
	
	public LCDInfo(Odometer odo, Detection detection) {
		this.odo = odo;
		this.lcdTimer = new Timer(LCD_REFRESH, this);
		LCD.clear();
		// initialise the arrays for displaying data
		pos = new double [3];
		
		this.detection = detection;
		
		// start the timer
		lcdTimer.start();
	}
	
	public void timedOut() { 
		odo.getPosition(pos);
		LCD.clear();

		LCD.drawString("Color: ", 0, 1);
		LCD.drawString("Distance: ", 0, 2);
		LCD.drawString("Color #: ", 0, 3);	
		
		LCD.drawInt(detection.getColor(), 10, 1);
		LCD.drawInt(detection.getDistance(), 10, 2);
		LCD.drawInt(detection.getBlockNumber(), 10, 3);
		
		LCD.drawString(detection.getBlockType(), 0, 4);
	}
}
