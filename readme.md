# Ferroli connect hacking resources

> INCOMPLETE. FOR DEVELOPERS.

## Main idea

The Ferroli connect gateway talks with outside world (specifically the mobile app) enstablishing an outgoing encrypted mqtt connection with a cloud mqtt broker (eu-mqtt.topband-cloud.com port 8883).
Luckly, the encription is partially broken:
 - the gateway DOES NOT verify the broker certificate
 - the broker DOES NOT verify the gateway certificate

By using a modified version of the mosquitto mqtt broker is possible to:
1) implement a men-in-the-middle attack to sniff the traffic between the gateway and the broker
2) implement an alternative server that simulate the Ferroli cloud one and run it locally. This allows custom integration with the device.

The Ferroli mobile app was reversed enginereed to understand the application protocol.
Source code on the android app ar provided in the FerroliCONNECT_1.3.9_source_from_JADX folder.

An initial attempt at implementing the alternative server is provided in the ferroli folder; at the moment, only decoding of messages from the gateway has been done.
Additionally the gateway must receve weather information for better heating performances; an alternative way to provide this nformation has been roughtly implemented in the pyton sever.

## Steps to reproduce

1) setup a local mqtt broker with encription and self-signed certificates and configure your own firewall to redirect port 8883 to the local server. I had to modify mosquitto source code to prevent certificate verification and log authentication credentials of the gateway in plain text. The gol of this step is to optain the gateway clientid, username, password and base subscription topic.
2) setup mosquitto to bridge with the cloud ferroli broker in order to inspect the application protocol, a base configuration file is provided in the mosquitto folder.

## Next steps

- Complete the reverse engineering of the application protocol
- Implement an alternative server that conforms to the protocol and relays the information/commands to Home Assistant (maybe through mqtt)
- (optional) Implement a custom Home Assistant card for the chrono thermostat