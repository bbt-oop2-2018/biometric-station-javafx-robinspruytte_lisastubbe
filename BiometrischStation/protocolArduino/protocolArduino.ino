void setup() {
  serial.begin(115200);

{
  {"startOfFrame": "AAAA", 
  "hartslag":"|", 
  "temperatuur":"&",
  "B_accelerometer":"BB",
  "C_accelerometer":"CC",
  "D_accelerometer":"DD",
  "endOfFrame":"ZZZZ"
  }
  }
  char json[]= "{\"startOfFrame"\:\"AAAA"\, \"hartslag"\:\"|"\, \"temperatuur"\:\"&"\,\"B_accelerometer"\:"\BB"\,\"C_accelerometer"\:\"CC"\,\"D_accelerometer"\:\"DD"\,\"endOfFrame"\:\"ZZZZ"\};}";
  JsonObject& root = jsonBuffer.parseObject(json);
  if(!root.success()){
    serial.println("parseObject() failed");
    return;
    }
    const char* startOfFrame = root["startOfFrame"];
    double hartslag = root["hartslag"];
    double temperatuur = root["temperatuur"];
    double B_accelerometer = root["B_accelerometer"];
    double C_accelerometer = root["C_accelerometer"];
    double D_accelerometer = root["D_accelerometer"];
    const char* endOfFrame = root["endOfFrame"];

    Serial.println(startOfFrame);
    Serial.println(hartslag);
    Serial.println(temperatuur);
    Serial.println(B_accelerometer);
    Serial.println(C_accelerometer);
    Serial.println(D_accelerometer);
    Serial.println(endOfFrame);
}
void loop() {
  // put your main code here, to run repeatedly:

}
