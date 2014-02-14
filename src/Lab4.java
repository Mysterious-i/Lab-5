import lejos.nxt.*;
import lejos.util.Timer;

//Alessandro Parisi 260529758
//Shahrzad Tighnavardmollasarae 260413622

public class Lab4 {

	public static void main(String[] args) {
		
		int buttonChoice;
		
		// setup the odometer, display, and ultrasonic and light sensors
		TwoWheeledRobot patBot = new TwoWheeledRobot(Motor.A, Motor.B);
		Odometer odo = new Odometer(patBot, true);
		
		//Timer LCDTimer = new Timer(100, lcd);
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S1);
		
		Detection detection = new Detection(colorSensor, us);
		
		do {
			// clear the display
			LCD.clear();

			// ask the user whether the motors should drive in a square or float
			LCD.drawString("< Left  |  Right >", 0, 0);
			LCD.drawString("        |         ", 0, 1);
			LCD.drawString(" Falling|  Rising ", 0, 2);
			LCD.drawString(" Edge   |  Edge   ", 0, 3);
 
			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT
				&& buttonChoice != Button.ID_RIGHT);
		

		if (buttonChoice == Button.ID_LEFT) {	
						
			LCDInfo lcd = new LCDInfo(odo, detection);
			
			//falling edge US localization
			
			/*LCD.clear();
			LCDInfo lcd = new LCDInfo(odo);
			USLocalizer usl = new USLocalizer (odo, us, USLocalizer.LocalizationType.FALLING_EDGE);
			usl.doLocalization();*/
			
		} 
		else {
			
			//Rising edge US localization

			USLocalizer usl = new USLocalizer (odo, us, USLocalizer.LocalizationType.RISING_EDGE);
			usl.doLocalization();

		}
		
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);
		System.exit(0);
		
	}

}
