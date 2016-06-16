/*
SparkFun Inventor's Kit 
Example sketch 07

TEMPERATURE SENSOR

  Use the "serial monitor" window to read a temperature sensor.

  The TMP36 is an easy-to-use temperature sensor that outputs
  a voltage that's proportional to the ambient temperature.
  You can use it for all kinds of automation tasks where you'd
  like to know or control the temperature of something.

  More information on the sensor is available in the datasheet:
  http://cdn.sparkfun.com/datasheets/Sensors/Temp/TMP35_36_37.pdf

  Even more exciting, we'll start using the Arduino's serial port
  to send data back to your main computer! Up until now, we've 
  been limited to using simple LEDs for output. We'll see that
  the Arduino can also easily output all kinds of text and data.

Hardware connections:

  Be careful when installing the temperature sensor, as it is
  almost identical to the transistors! The one you want has 
  a triangle logo and "TMP" in very tiny letters. The
  ones you DON'T want will have "222" on them.

  When looking at the flat side of the temperature sensor
  with the pins down, from left to right the pins are:
  5V, SIGNAL, and GND.

  Connect the 5V pin to 5 Volts (5V).
  Connect the SIGNAL pin to ANALOG pin 0.
  Connect the GND pin to ground (GND).

This sketch was written by SparkFun Electronics,
with lots of help from the Arduino community.
This code is completely free for any use.
Visit http://learn.sparkfun.com/products/2 for SIK information.
Visit http://www.arduino.cc to learn about the Arduino.

Version 2.0 6/2012 MDG
*/

// We'll use analog input 0 to measure the temperature sensor's
// signal pin.


void setup()
{

  Serial.begin(9600);
  pinMode(5, OUTPUT);
  pinMode(4, INPUT);
  pinMode(16, OUTPUT);
}


void loop()
{
  

  float voltage;
  float degreesC;
  float degreesF;

  voltage = analogRead(A0) * 0.004882814;


  degreesC = (voltage - 0.5) * 100.0;

  

  degreesF = degreesC * (9.0/5.0) + 32.0;
  long duration;
  long distance; 

  digitalWrite(5, LOW);
  delay(0.2);
  digitalWrite(5, HIGH);
  delay(0.01);
  digitalWrite(5, LOW);
  duration = pulseIn(4, HIGH);

  distance = (duration/2) / 29.1;


  Serial.print("voltage: ");
  Serial.print(voltage);
  Serial.print("  deg C: ");
  Serial.print(degreesC);
  Serial.print("  deg F: ");
  Serial.println(degreesF);
  Serial.print(distance);
  Serial.println("  cm");
  delay(1);

 

  delay(1000); // repeat once per second (change as you wish!)
}

