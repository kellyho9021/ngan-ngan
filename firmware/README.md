#Firmware for the NodeMCU
##Current Tasks:
1. Break into smaller functions
2. Store credential (user, deviceID, ssid, pwd) in EEPROM
3. Load credential when boot up (unplug, power loss)
4. Hard reset function

##Achieved Tasks:
1. Connect to WiFi with hard-coded ssid, password
2. Connect to host with hard-coded host, url
3. Pull request from server (TCP HTTP Get)
4. Receive data, turn Led on/off
5. Send current status to server
