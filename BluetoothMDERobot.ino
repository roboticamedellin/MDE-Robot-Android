#include <SoftwareSerial.h>
SoftwareSerial BTserial(9, 8); // RX | TX
char c;
String comando;

int IN1 = 10;
int IN2 = 11;

int IN3 = 12;
int IN4 = 13;

void setup(){
  BTserial.begin(9600);
  pinMode (IN4, OUTPUT); 
  pinMode (IN3, OUTPUT);
  pinMode (IN4, OUTPUT);
  pinMode (IN3, OUTPUT);
}
void loop(){
  BTserialEvent();
}

void BTserialEvent(){
  if (BTserial.available()) {
    c = BTserial.read();
    if (c == '\n') {
      parseCommand(comando);
      comando = "";
    }
    else {
      comando += c;
    }
  }
}

void parseCommand(String com) {
  String part1;
  String part2;

  part1 = com.substring(0, com.indexOf("-"));
  part2 = com.substring(com.indexOf("-") + 1);

  if(part1 == "echo"){
    echo(part2);
  }else
  if(part1 == "f"){
    moveForward();
  }else
  if(part1 == "b"){
    moveBack();
  }else
  if(part1 == "s"){
    stopMotor();
  }else
  if(part1 == "c"){
    moveClock();
  }else
  if(part1 == "u"){
    moveUnClock();
  }

}

void echo(String valor){
  BTserial.print("responding-to-");
  BTserial.println(valor);
}

void moveForward(){
  digitalWrite (IN1, HIGH);
  digitalWrite (IN2, LOW);
  digitalWrite (IN4, HIGH);
  digitalWrite (IN3, LOW);
}

void moveBack(){
  digitalWrite (IN1, LOW);
  digitalWrite (IN2, HIGH);
  digitalWrite (IN4, LOW);
  digitalWrite (IN3, HIGH);
}

// Clock and unclock
void moveClock(){
  digitalWrite (IN1, LOW);
  digitalWrite (IN2, HIGH);
  digitalWrite (IN4, HIGH);
  digitalWrite (IN3, LOW);
}

void moveUnClock(){
  digitalWrite (IN1, HIGH);
  digitalWrite (IN2, LOW);
  digitalWrite (IN4, LOW);
  digitalWrite (IN3, HIGH);
}

void stopMotor(){
  digitalWrite (IN1, LOW);
  digitalWrite (IN2, LOW);
  digitalWrite (IN4, LOW);
  digitalWrite (IN3, LOW);
}
