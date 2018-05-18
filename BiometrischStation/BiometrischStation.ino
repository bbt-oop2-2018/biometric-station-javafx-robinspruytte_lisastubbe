#include <ArduinoJson.h>
#include <LiquidCrystal.h>

LiquidCrystal lcd(8, 9, 4, 5, 6, 7);

//  variables
int selectButton_pin = A0;

bool displayAccelero = false;
bool selectButtonState = false;

void setup() {
  Serial.begin(115200);
  lcd.begin(16, 2);
 

  temperatureSetup();
  heartbeadSetup(); // sets up to read Pulse Sensor rawSignalData every 2mS
  acceleroSetup();
}

void loop() {
  
  displaySelect();

  dataInJsonToSerial();
  delay(1000); //  take a break
}

// select --> 613
// right --> 815
// up --> 1023
void displaySelect() {
  if (analogRead(selectButton_pin) > 900) {
    selectButtonState = false;
  }
  if (analogRead(selectButton_pin) < 900) {
    delay(50); // debouncing button
    if (analogRead(selectButton_pin) < 900) {
      if (!selectButtonState) {
        selectButtonState = true;
        displayAccelero = !displayAccelero;
      }
    }

  }

  if (displayAccelero) {
    printAcceleroValues();

  } else {
    lcd.clear();
    printHeartBeat();

    lcd.setCursor(0, 1);
    printTemperature();
  }
}

void dataInJsonToSerial() {
  //json
  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& rootJson = jsonBuffer.createObject();
  
  rootJson["time"] = millis();
  rootJson["bpm"] = getBPM();
  rootJson["temperature"] = getTemperature();
  rootJson["accelcx"] = getAccelCx();
  rootJson["accelcy"] = getAccelCy();
  rootJson["accelcz"] = getAccelCz();
  

  Serial.println("");
  rootJson.printTo(Serial);
  Serial.println("");
}

