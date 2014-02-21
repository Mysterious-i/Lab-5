import lejos.nxt.*;
import lejos.util.Timer;

//Alessandro Parisi 260529758
//Shahrzad Tighnavardmollasarae 260413622

public class Lab5 {

	public static void main(String[] args) {
		
		int buttonChoice;
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S1);
		
		Detection detection = new Detection(colorSensor, us);
		
		// setup the odometer, display, and ultrasonic and light sensors
		TwoWheeledRobot patBot = new TwoWheeledRobot(Motor.A, Motor.B);
		Odometer odometer = new Odometer(patBot, true);
		
		Capture capture = new Capture(odometer, detection);

		do {
			// clear the display
			LCD.clear();

			// ask the user whether the motors should drive in a square or float
			LCD.drawString("< Left>", 0, 0);
			LCD.drawString("        ", 0, 1);
			LCD.drawString(" Start", 0, 2);
 
			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT
				&& buttonChoice != Button.ID_RIGHT);

		if (buttonChoice == Button.ID_LEFT) {	
						
			LCDInfo lcd = new LCDInfo(odometer, detection);

			LCD.clear();
			
			//Perform using localization using falling edge and the ultrasonic sensor
			USLocalizer usl = new USLocalizer (odometer, us, USLocalizer.LocalizationType.FALLING_EDGE);
			usl.doLocalization();
			
			//Switch the mode of the ultrasonic sensor to coninuous because it was in ping mode for the localization
			us.continuous();
			
			odometer.setPosition(new double [] {0.0, 0.0, 0.0}, new boolean [] {true, true, true});
			odometer.setAng(0);
			
			//Try to capture the object, use an exception to exit the recursive stack

			capture.capture();
			
		} 
		
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);
		System.exit(0);
		
	}

}
