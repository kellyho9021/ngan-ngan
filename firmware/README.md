#Firmware for the NodeMCU
##Wish List:
1. Fix reset issue
2. Fix print loaded data issue 
3. Hard reset function

##Achieved:
1. Connect to WiFi with hard-coded ssid, password
2. Connect to host with hard-coded host, url
3. Pull request from server (TCP HTTP Get)
4. Receive data, turn Led on/off
5. Send current status to server
6. Store credential (user, deviceID, ssid, pwd) in EEPROM
7. Load credential when boot up (unplug, power loss)
8. Break into smaller functions
