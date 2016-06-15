#include <ESP8266WiFi.h>
 
const char* ssid     = "Binh";
const char* password = "123456789";
 
const char* host = "54.183.182.211";
const int httpPort = 8080;

const int ledPin = 13;

void setup() {
  Serial.begin(115200);
  delay(100);
  pinMode(ledPin, OUTPUT);

  
  // We start by connecting to a WiFi network
 
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
 
  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  
}


String url = "/hackuci/Binh";
int value = 0;

void sendStatus(bool t)
{
  Serial.println("UPDATE STATUS");
  WiFiClient client;
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  } 
  if (t)
  {
    client.print(String("GET ") + url + "?status=on" + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
  } else {
    client.print(String("GET ") + url + "?status=off" + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
  }
}
 
void loop() {
  delay(500);
  ++value;
  WiFiClient client;
  
  Serial.print("connecting to ");
  Serial.println(host);
  
  // Use WiFiClient class to create TCP connections
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }
  
  // We now create a URI for the request
  
  Serial.print("Requesting URL: ");
  Serial.println(url);
  
  // This will send the request to the server
  
  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
  delay(500);
  
  // Read all the lines of the reply from server and print them to Serial
  int count = 0;
  String line;
  while (client.available() and count <=7)
  {
    line = client.readStringUntil('\r\n');
    ++count;
  }
  Serial.print("LINE ");
  Serial.println(line);
  if (line.startsWith("on"))
  {
    digitalWrite(ledPin, HIGH);
    sendStatus(true);
  } else
  {
    digitalWrite(ledPin, LOW);
    sendStatus(false);
  }
  
  Serial.println();
//  Serial.println("closing connection");
}
