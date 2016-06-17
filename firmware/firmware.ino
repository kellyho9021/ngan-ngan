#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
 #include <DNSServer.h>
#include <WiFiManager.h>
#include <EEPROM.h>


WiFiManager wifiManager;

char ssid[25];
char pwd[25];
char user[25] = "binh";
 
const char* host = "54.183.182.211";
const int httpPort = 8080;
const char* deviceKey = "GiCungDuoc";

const int ledPin = 13;


String url = "/Ngan-Ngan/Binh";
int value = 0;

/***********************************************************************************/
// LOAD AND SAVE CREDENTIALS
/***********************************************************************************/
void save_data(char* data) {
  Serial.println("Write data to EEPROM");
  EEPROM.begin(512);
  for (int i = 0; i < strlen(data); ++i) {
    EEPROM.write(i, (int)data[i]);
    delay(1);
  }
  EEPROM.commit();
  Serial.println("Write data complete");
  delay(100);
}

void load_data() {
  Serial.println("Read data from EEPROM");
  EEPROM.begin(512);
  int count = 0;
  int address = 0;
  char data[100];
  while (count < 3) {
    char read_char = (char)EEPROM.read(address);
    delay(1);
    if (read_char == '#') {
      ++count;
      switch (count) {
        case 1: strcpy(ssid, data); break;
        case 2: strcpy(pwd, data); break;
        case 3: strcpy(user, data); break;
//        case 4: strcpy(deviceKey, data); break;
      }
      strcpy(data,"");
    } 
    else {
      strncat(data, &read_char, 1);  
    }
    ++address;
  }
  Serial.println("Read data complete");
  Serial.print("data: ");
  Serial.println(data);
  delay(100);
}

void data_setup(char* data) {
  char* sep = "#";
  strcat(data, wifiManager._ssid.c_str());
  strcat(data, sep);
  strcat(data, wifiManager._pass.c_str());
  strcat(data, sep);
  strcat(data, user);
  strcat(data, sep);
  strcat(data, deviceKey);
  strcat(data, sep);
  Serial.println("This is the final string:");
  Serial.println(data);
  Serial.println();
}

/***********************************************************************************/
//AP MODE
/***********************************************************************************/
void resetWifi() {
  wifiManager.resetSettings();
}

void configModeCallback (WiFiManager *myWiFiManager) {
  Serial.println("Entered config mode");
}

void setup_wifi() { 
  wifiManager.setAPCallback(configModeCallback);
  Serial.println("Setting up Wifi");
  if (!wifiManager.autoConnect("DeviceConfig","69config69")) {    
    Serial.println("failed to connect and hit timeout");
    //reset and try again, or maybe put it to deep sleep
    ESP.reset();
//    delay(1000);
  }
}

/***********************************************************************************/
//HTTP Request
/***********************************************************************************/
void sendStatus(bool t) {
  Serial.println("UPDATE STATUS");
  WiFiClient client;
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  } 
  if (t) {
    client.print(String("GET ") + url + "?status=on&dkey=" + deviceKey + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
  } else {
    client.print(String("GET ") + url + "?status=off&dkey=" + deviceKey + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
  }
}

/***********************************************************************************/
//SETUP AND LOOP
/***********************************************************************************/
void setup() {
  Serial.begin(115200);
  pinMode(ledPin, OUTPUT);

  load_data();
  strcpy(user,"Binh");
  
  // We start by connecting to a WiFi network
 
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, pwd);
  
  for (int c = 0; c <= 30 and WiFi.status() != WL_CONNECTED; ++c) {
    delay(500);
    Serial.print(".");
    if (c == 30) {
      Serial.println();
      Serial.println("Connection Time Out...");
      Serial.println("Enter AP Mode...");
      setup_wifi();
      char data[100];
      data_setup(data);
      save_data(data);
      ESP.reset();
//      delay(1000);
    }
  }
 
  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}
 
void loop() {
  ++value;
  WiFiClient client;
  
  Serial.print("connecting to ");
  Serial.println(host);
  
  // Use WiFiClient class to create TCP connections
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    ESP.reset();
    return;
  }
  
  // We now create a URI for the request
  
  Serial.print("Requesting URL: ");
  Serial.println(url);
  
  // This will send the request to the server
  
  client.print(String("GET ") + url + "?dkey=" + deviceKey + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
  delay(50);
  
  // Read all the lines of the reply from server and print them to Serial
  int count = 0;
  String line;
  while (client.available() and count <=7) {
    line = client.readStringUntil('\r\n');
    ++count;
  }
  Serial.print("LINE ");
  Serial.println(line);
  if (line.startsWith("on")) {
    digitalWrite(ledPin, HIGH);
    sendStatus(true);
  } else {
    digitalWrite(ledPin, LOW);
    sendStatus(false);
  }
  
  Serial.println();
//  Serial.println("closing connection");
}
