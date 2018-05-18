#include <Wire.h> // Must include Wire library for I2C
#include "SparkFun_MMA8452Q.h" // Includes the SFE_MMA8452Q library
#include <ArduinoJson.h>

MMA8452Q accel;

void acceleroSetup(){
  accel.init();
}

// This function demonstrates how to use the accel.cx, accel.cy,
//  and accel.cz variables.
// Before using these variables you must call the accel.read()
//  function!
void printCalculatedAccels()
{
  
  
  Serial.print(accel.cx, 3);
  Serial.print("\t");
  lcd.print(" X:");
  lcd.print(accel.cx, 3);  
  lcd.setCursor(0, 1);
  
  Serial.print(accel.cy, 3);
  Serial.print("\t");
  lcd.print("Y:");
  lcd.print(accel.cy, 3);
  
  Serial.print(accel.cz, 3);
  Serial.print("\t");
  lcd.print(" Z:");
  lcd.print(accel.cz, 3);
  
}

void printAcceleroValues() {
  if (accel.available())
  {
    accel.read();
    
    lcd.clear();
    lcd.print("Accel:");
    printCalculatedAccels();
    delay(100); //  take a break
  }
}

float getAccelCx(){
  return accel.cx;
}
float getAccelCy(){
  return accel.cy;
}
float getAccelCz(){
  return accel.cz;
}


