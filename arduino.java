
#include "SD.h"
#define SD_ChipSelectPin 4
#include "TMRpcm.h"
#include "SPI.h"
TMRpcm tmrpcm;

int acc_x_array[4];
int acc_x_array_index=0;
int acc_z_array[4];
int acc_z_array_index=0;//declaring array and its variables
boolean is_hello , is_yes , is_love ,is_you , is_like,is_fuckoff;
int hello_yes_threshold_acc_x = 50/* !!!! type a threshold */ , hello_yes_threshold_acc_y = -55/* !!!! */ , you_threshold_acc_z = 50/* !!!! */ , you_threshold_acc_y = -55/* !!!! */ ;

void setup(){
  Serial.begin(9600);
  tmrpcm.speakerPin = 9;
  tmrpcm.setVolume(6);

  if (!SD.begin(SD_ChipSelectPin)) {
    Serial.println("SD fail");
    return;
   }
  } 
void loop(){
  int acc_x = analogRead(A5)-352 , acc_y=analogRead(A6)-352 , acc_z=analogRead(A7)-352 ;//creating accelerometer readings
  int  hello_threshold_acc_x_count=0 , yes_threshold_acc_x_count=0 ,you_threshold_acc_z_count=0 ;
  int flex_thumb = analogRead(A0)-760 , flex_index = analogRead(A1)-720 , flex_middle = analogRead(A2)-697 , flex_ring = analogRead(A3)-770 , flex_pinky = analogRead(A4)-790 ;//reading flex sensor values
  
  Serial.print("ax = ");
  Serial.print(acc_x);
  Serial.print("\t");
  
  Serial.print("ay = ");
  Serial.print(acc_y);
  Serial.print("\t");
  
  Serial.print("az = ");
  Serial.print(acc_z);
  Serial.println("\t");
  Serial.print("\n") ;

  Serial.print("thumb = ");
  Serial.print(flex_thumb);
  Serial.print("\t");

  Serial.print("index = ");
  Serial.print(flex_index);
  Serial.print("\t");

  Serial.print("middle = ");
  Serial.print(flex_middle);
  Serial.print("\t");

  Serial.print("ring = ");
  Serial.print(flex_ring);
  Serial.print("\t");

  Serial.print("pinky = ");
  Serial.print(flex_pinky);
  Serial.println("\t");
  Serial.print("\n");
  delay(50);
  //check hello and yes
  
  
  if( acc_x > hello_yes_threshold_acc_x && acc_y < hello_yes_threshold_acc_y ){
    if(/*higher values*/ flex_thumb < 23 && flex_index < 12 && flex_middle <24 && flex_ring <30 && flex_pinky<23) {
       is_hello = true ;
    }
    if(/*lower values*/flex_thumb > 50 && flex_index >100 && flex_middle > 95 && flex_ring >65 && flex_pinky>55) {
       is_yes = true ;
    }

  }
  
//check for you 

    if(/*higher values*/ flex_thumb < 23 && flex_index < 12 && flex_middle <24 && flex_ring <30 && flex_pinky<23 && acc_z > you_threshold_acc_z && acc_y < you_threshold_acc_y ){
       is_you = true ;
    }
  
  //check for "i love you"
  if(/*lower values*/flex_thumb > 0 && flex_index >-5 && flex_middle > 90 && flex_ring >50 && flex_pinky>-15 &&
    /*higher values*/ flex_thumb <40 && flex_index < 20 && flex_middle <140 && flex_ring <90 && flex_pinky<16) {
    is_love = true ;
  }

  // check for like
  if(/*lower values*/flex_thumb > -20 && flex_index >100 && flex_middle > 90 && flex_ring >60 && flex_pinky>50 &&
    /*higher values*/ flex_thumb < 20 && flex_index < 160 && flex_middle <140 && flex_ring <100 && flex_pinky<95) {
    is_like = true ;
  }

  //check for fuckoff
  if( /*lower values*/flex_thumb > 40 && flex_index >110 && flex_middle > 0 && flex_ring >55 && flex_pinky>40 &&
    /*higher values*/ flex_thumb < 120 && flex_index < 160 && flex_middle <35 && flex_ring <90 && flex_pinky<100) {
    is_fuckoff = true ;
  }

 
  
//give outputs
  if(is_hello) {

    Serial.println("hello") ;
    tmrpcm.play("hello.wav");
    acc_x = 0 , acc_y= 0 , acc_z= 0  ;
    flex_thumb = 0 , flex_index = 0 , flex_middle = 0 , flex_ring = 0 , flex_pinky = 0 ;
    is_hello = false ;
    delay(1000);
  }// give hello output

  if(is_yes) {

    Serial.println("yes") ;
    tmrpcm.play("yes.wav");
    acc_x = 0 , acc_y= 0 , acc_z= 0  ;
    flex_thumb = 0 , flex_index = 0 , flex_middle = 0 , flex_ring = 0 , flex_pinky = 0 ;
    is_yes=false ;
    delay(1000);
  }// give yes output

  if(is_love) {
    Serial.println("i love you") ;
    tmrpcm.play("love.wav");
    flex_thumb = 0 , flex_index = 0 , flex_middle = 0 , flex_ring = 0 , flex_pinky = 0 ;
    is_love=false ;
    delay(1000);
  }// give "i love you " output

  if(is_you) {
    Serial.println("you") ;
    tmrpcm.play("you.wav");
    is_you=false ;
    acc_x = 0 , acc_y= 0 , acc_z= 0  ;
    flex_thumb = 0 , flex_index = 0 , flex_middle = 0 , flex_ring = 0 , flex_pinky = 0 ;
    delay(1000);
  }

  if(is_like) {
    Serial.println("like") ;
    tmrpcm.play("like.wav");
    flex_thumb = 0 , flex_index = 0 , flex_middle = 0 , flex_ring = 0 , flex_pinky = 0 ;
    is_like=false ;
    delay(1000);
  }

  if(is_fuckoff) {
    Serial.println("Fuck Off") ;
    tmrpcm.play("fuckoff.wav");
    flex_thumb = 0 , flex_index = 0 , flex_middle = 0 , flex_ring = 0 , flex_pinky = 0 ;
    is_fuckoff=false ;
    delay(1000);
  }

  
}



