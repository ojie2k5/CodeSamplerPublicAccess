
import 'dart:async';
import 'dart:io';
import 'dart:ui';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:kubo_appointment/termsPrivacy.dart';
import 'package:kubo_appointment/userDashboard.dart';
import 'package:kubo_appointment/utils.dart';
import 'Appointments/appointment.dart';
import 'CheckOutPayment/mobileCheckOut.dart';
import 'CheckOutPayment/webCheckOut.dart';
import 'InAppPurchase/inAppPurchase.dart';
import 'UserAccess&Restriction/access&RestrictionbyPaymentStatus.dart';
import 'UserDashBoard/userDashBoardMobile.dart';
import 'main.dart';
import '../Instructions/instructions.dart';
import 'ContactUs/contactUs.dart';



class AgentProfile extends StatefulWidget{
static const String route = '/agentProfile';

@override
Profile createState() => Profile();

}

bool agentProfileButtonsVisibilityBool = true;
bool agentProfileVisibilityBool = false;
bool agentProfilePaymentTermsVisibilityBool = false;
bool isStripePaymentPlatform = true;
String paymentPlatformString = "";

class Profile extends State<AgentProfile>{

  @override
  void initState() {
    super.initState();
    callPaymentPlatformDetails();
    userProfilePopulateController(context,setState);
    /// Resets all Boolean values
    resetBooleanValues(setState);
    webOrDevice();
    populateValueFromFirebaseAuth();
    //webhookEndPoints();
    pauseRequestTextController = new TextEditingController();
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();

    if(userProfileFormKey.currentState != null){
    userProfileFormKey.currentState!.dispose();}
    if(userBusinessPhoneNumberFormKey.currentState !=null){
      userBusinessPhoneNumberFormKey.currentState!.dispose();
    }
    if(userPhoneNumberFormKey.currentState != null){
      userPhoneNumberFormKey.currentState!.dispose();
    }
    pauseRequestTextController.dispose();
  }

  @override
  Widget build(BuildContext context){
    return Scaffold(
      /// resizeToAvoidBottomInset Prevents widget going over the top when keyboard appears
        resizeToAvoidBottomInset: resizeToAvoidBottomInsetBool,
        backgroundColor: pageBackgroundColor(),
        //key: userProfilePageKey,
        appBar: AppBar(
          backgroundColor: pageBackgroundColor(),
          //leading: Image.asset('assets/images/houseIcon.png', scale: 0.8,),
          leading: Container(alignment: Alignment.center,
            child: GestureDetector(
            onTap: () { /* Write listener code here */},
            child: Builder(
              builder: (context) => GestureDetector(
                  child: InkWell(
                    borderRadius: BorderRadius.circular(100),
                      splashColor: Colors.lightGreen.shade900,
                      hoverColor: Colors.white12,
                      onTap: () {
                      Navigator.pushReplacementNamed(context, MyDashboard.route,);
                      (kIsWeb == true || Platform.isMacOS == true)
                          ?DashBoard().resetValuesForBackToMainDashBoard()
                          :mobileResetValuesForBackToMainDashBoard(setState);
                      },
                  child: Container(alignment: Alignment.center,
                      child: Icon(Icons.dashboard_outlined, color: Colors.teal,),
                  )
              ),
              ),

            ),
          ),),
          title: Container(alignment: Alignment.center,
          child: Text("Agent Profile", style: TextStyle(letterSpacing: 1),),),
          actions: <Widget>[
            Visibility(
              child: IconButton(icon: CircleAvatar(
                minRadius: 60,
                maxRadius: 200,
                backgroundColor: Colors.grey,
                backgroundImage: profileImage,
                foregroundImage: profileImage,
          ),
            onPressed: () {
              // do something
            },
          ), visible: true,),
            SizedBox(width: 20,),
          ],
        ),
        body: (kIsWeb == true || Platform.isMacOS == true)
            /// For Web Devices
            ?Stack(
                alignment: Alignment.center,
                children:[
                  ///Main Page
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      SizedBox(
                        child: Container(color: Colors.black),
                        width: 10,
                      ),
                      /// Buttons
                      Visibility(
                        visible: true,
                        //visible: mainDashBoardPageVisibilityBool,
                        child: Expanded(
                            flex: 1,
                            child: Material(
                                color: Colors.black,
                                shadowColor: tealShadowColorRGB(),
                                elevation: elevationTextContainer(),
                                child: Visibility(
                                  visible: mainPageVisibilityBool,
                                  child:  Column(
                                    crossAxisAlignment: CrossAxisAlignment.center,
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: <Widget>[
                                      SizedBox(height: 40,),
                                      /// Button panel
                                      Container(
                                          alignment: Alignment.topCenter,
                                          child: ConstrainedBox(
                                              constraints: BoxConstraints(maxWidth: textFieldSized),
                                              child: Column(children: [
                                                SizedBox(height: 10),
                                                /// Profile
                                                Tooltip(
                                                textStyle: toolTipTextStyle(),
                                                message: "Click/Tap to Show",
                                                child:InkWell(
                                                    borderRadius: BorderRadius.all(Radius.circular(100)),
                                                    splashColor: Colors.lightGreen.shade900,
                                                    hoverColor: Colors.white12,
                                                    child: Container(
                                                      height: 40,
                                                      alignment: Alignment.center,
                                                      padding: EdgeInsets.all(0),
                                                      child: instructionTextStyleTitleExpandablePanel('User Profile'),
                                                    ),
                                                    onTap:  (){
                                                      setState(() {
                                                      resetValueUserProfile();
                                                       });
                                                    }

                                                )),
                                                SizedBox(height: 20),
                                                /// Payment terms
                                                Tooltip(
                                                    textStyle: toolTipTextStyle(),
                                                    message: "Click/Tap to Show",
                                                    child:InkWell(
                                                        borderRadius: BorderRadius.all(Radius.circular(100)),
                                                        splashColor: Colors.lightGreen.shade900,
                                                        hoverColor: Colors.white12,
                                                        child: Container(
                                                            height: 40,
                                                            alignment: Alignment.center,
                                                            padding: EdgeInsets.all(0),
                                                            child: instructionTextStyleTitleExpandablePanel('Payment')
                                                        ),
                                                        onTap:  () async {
                                                          setState(() {
                                                            resetValuePaymentTerms();
                                                          });
                                                        }
                                                    )),
                                                SizedBox(height: 20),
                                                /// Right/terms of use
                                                Tooltip(
                                                    textStyle: toolTipTextStyle(),
                                                    message: "Click/Tap to Show",
                                                    child:InkWell(
                                                        borderRadius: BorderRadius.all(Radius.circular(100)),
                                                        splashColor: Colors.lightGreen.shade900,
                                                        hoverColor: Colors.white12,
                                                        child: Container(
                                                          height: 40,
                                                          alignment: Alignment.center,
                                                          padding: EdgeInsets.all(0),
                                                          child: instructionTextStyleTitleExpandablePanel('Policy/Terms of Use')
                                                        ),
                                                        onTap:  (){
                                                          setState(() {
                                                            Navigator.pushReplacementNamed(context, TermsOfServicePrivacyState.route,);
                                                          });
                                                        }

                                                    )),
                                                SizedBox(height: 20),
                                                /// Contact Us
                                                Tooltip(
                                                    textStyle: toolTipTextStyle(),
                                                    message: "Click/Tap",
                                                    child:InkWell(
                                                        borderRadius: BorderRadius.all(Radius.circular(100)),
                                                        splashColor: Colors.lightGreen.shade900,
                                                        hoverColor: Colors.white12,
                                                        child: Container(
                                                            height: 40,
                                                            alignment: Alignment.center,
                                                            padding: EdgeInsets.all(0),
                                                            child: instructionTextStyleTitleExpandablePanel('Contact Us')
                                                        ),
                                                        onTap:  (){
                                                          setState(() {
                                                            contactUsDialog(context);
                                                          });
                                                        }

                                                    )),
                                                SizedBox(height: 30),
                                                /// Delete Account
                                                Tooltip(
                                                    textStyle: toolTipTextStyle(),
                                                    message: "Click/Tap",
                                                    child:InkWell(
                                                        borderRadius: BorderRadius.all(Radius.circular(100)),
                                                        splashColor: Colors.lightGreen.shade900,
                                                        hoverColor: Colors.white12,
                                                        child: Container(
                                                            height: 40,
                                                            alignment: Alignment.center,
                                                            padding: EdgeInsets.all(0),
                                                            child: deleteUserTextStyleTitleExpandablePanel('Delete User Account')
                                                        ),
                                                        onTap:  (){
                                                          setState(() {
                                                            deleteUserAccountDialog(context);
                                                          });
                                                        }

                                                    )),
                                                SizedBox(height: 40),

                                              ],)
                                          )
                                      ),
                                      SizedBox(height: 100),
                                      Container(
                                        alignment: Alignment.bottomCenter,
                                        child: Text("All Rights Reserved  " + yyMmDd.format(DateTime.now()),
                                          style: TextStyle(color: Colors.white60,fontSize: 10),
                                        ),
                                      )
                                    ],),),)),
                      ),
                      /// SizedBox
                      Visibility(
                        //visible: false,
                        //visible: mainDashBoardPageVisibilityBool,
                          child: Material(
                              color: Colors.black,
                              shadowColor: Colors.teal.shade900.withOpacity(0.3),
                              elevation: elevationTextContainer(),
                              child: SizedBox(
                                child: Container(color: Colors.black),
                                width: 0.2,
                              ))),

                      /// userProfile Right Side
                      Visibility(
                          visible: expandedUserProfile,
                          child: Expanded(
                              flex: 3,
                              child: Material(
                                  color: Colors.white,
                                  shadowColor: tealShadowColorRGB(),
                                  elevation: elevationTextContainer(),
                                  child:Container(
                                    child: Center(
                                        child: userProfileWidget()

                                  )
                                  )
                              )
                          )
                      ),
                      /// User Payment Widget
                      Visibility(
                        visible: expandedPaymentTerms,
                          child: Expanded(
                              flex: 3,
                              child: Material(
                                  color: Colors.white,
                                  shadowColor: tealShadowColorRGB(),
                                  elevation: elevationTextContainer(),
                                  child:Container(
                                      child: Center(
                                          child: userPaymentWidget()
                                      )
                                  )
                              )
                          )
                      ),
                    ],
                  ),
                  /// Circular Progress Indicator with counter
                  Visibility(
                      visible: dialogReusableCircularIndicatorVisibilityBool,
                      child: Container(
                          color: Colors.black,
                      height: MediaQuery.of(context).size.height,
                      width: MediaQuery.of(context).size.width,
                      alignment: Alignment.center,
                      child:BackdropFilter(
                            filter: ImageFilter.blur(sigmaX: 40.0, sigmaY: 40.0,),
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                CircularProgressIndicator(
                                  value: reusableCircularIndicatorValue,
                                  semanticsLabel: "Sending ...",
                                  backgroundColor: Colors.teal,
                                  valueColor: AlwaysStoppedAnimation(Colors.white),
                                  strokeWidth: 5,
                                ),
                                SizedBox(height: 20),
                                Container(
                                  alignment: Alignment.center,
                                  child: Text("Percent:  " + (reusableCircularIndicatorValue * 10).round().toString() + " %", style: TextStyle(fontSize: 20, color: Colors.white),),
                                ),
                                SizedBox(height: 15),
                                Container(
                                    padding: const EdgeInsets.symmetric(vertical: 25.0),
                                    alignment: Alignment.center,
                                    width: 300,
                                    child:Text('Sending ...In Progress,',maxLines: 3, style: TextStyle(fontSize: 18, color: Colors.white,),textAlign: TextAlign.center,)),
                              ],),
                          )
                      )
                  ),
                  /// Circular Progress Indicator No counter
                  Container(
                    height: MediaQuery.of(context).size.height/2,
                    width: MediaQuery.of(context).size.width/2,
                    alignment: Alignment.center,
                    child: Visibility(
                      //visible: true,
                        visible: noCounterReusableCircularIndicatorVisibilityBool,
                        child: BackdropFilter(
                          filter: ImageFilter.blur(sigmaX: 20.0, sigmaY: 20.0,),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              SizedBox(height: 20),
                              CircularProgressIndicator(
                                value: reusableCircularIndicatorValue,
                                semanticsLabel: "In progress ...",
                                backgroundColor: Colors.teal,
                                valueColor: AlwaysStoppedAnimation(Colors.black38),
                                strokeWidth: 5,
                              ),
                              SizedBox(height: 20),
                              Container(
                                alignment: Alignment.center,
                                child: Text((reusableCircularIndicatorValue * 10).round().toString() +'   ...In Progress,', style: TextStyle(fontSize: 20, color: Colors.white),),
                              ),
                              SizedBox(height: 10),
                            ],),
                        )
                    ),),
                  /// Circular Progress Indicator No counter
                  Container(
                    alignment: Alignment.center,
                    child: Visibility(
                        visible: mainPageReusableCircularIndicatorVisibilityBool,
                        child: BackdropFilter(
                          filter: ImageFilter.blur(sigmaX: 100.0, sigmaY: 100.0,),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.center,

                            children: [
                              CircularProgressIndicator(
                                value: reusableCircularIndicatorValue,
                                semanticsLabel: "In progress ...",
                                backgroundColor: Colors.teal,
                                valueColor: AlwaysStoppedAnimation(Colors.black38),
                                strokeWidth: 5,
                              ),
                              SizedBox(height: 20),
                              Container(
                                alignment: Alignment.center,
                                child: Text((reusableCircularIndicatorValue * 10).round().toString() +'   ...In Progress,', style: TextStyle(fontSize: 20, color: Colors.white),),
                              ),
                              SizedBox(height: 10),
                            ],),
                        )
                    ),),

                ])
            /// For Mobile
            :Stack(
            alignment: Alignment.center,
            children:[
              ///Main Page
              SingleChildScrollView(
                child: Column(
                children: [
                  SizedBox(
                    child: Container(color: Colors.black),
                    height: 10,
                  ),
                  /// Buttons
                  Visibility(
                    visible: agentProfileButtonsVisibilityBool,
                    child: Material(
                      color: Colors.black,
                      shadowColor: tealShadowColorRGB(),
                      elevation: elevationTextContainer(),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.center,
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: <Widget>[
                          SizedBox(height: 40,),
                          /// Button panel
                          Container(
                              alignment: Alignment.topCenter,
                              child: ConstrainedBox(
                                  constraints: BoxConstraints(maxWidth: textFieldSized),
                                  child: Column(children: [
                                    SizedBox(height: 10),
                                    /// Profile
                                    Tooltip(
                                        textStyle: toolTipTextStyle(),
                                        message: "Click/Tap to Show",
                                        child:InkWell(
                                            borderRadius: BorderRadius.all(Radius.circular(100)),
                                            splashColor: Colors.lightGreen.shade900,
                                            hoverColor: Colors.white12,
                                            child: Container(
                                              height: 40,
                                              alignment: Alignment.center,
                                              padding: EdgeInsets.all(0),
                                              child: instructionTextStyleTitleExpandablePanel('User Profile'),
                                            ),
                                            onTap:  (){
                                              setState(() {
                                                agentProfileButtonsVisibilityBool = false;
                                                agentProfileVisibilityBool = true;
                                                agentProfilePaymentTermsVisibilityBool = false;
                                               // resetValueUserProfile();
                                              });
                                            }

                                        )),
                                    SizedBox(height: 20),
                                    /// Payment
                                    Tooltip(
                                        textStyle: toolTipTextStyle(),
                                        message: "Click/Tap to Show",
                                        child:InkWell(
                                            borderRadius: BorderRadius.all(Radius.circular(100)),
                                            splashColor: Colors.lightGreen.shade900,
                                            hoverColor: Colors.white12,
                                            child: Container(
                                              height: 40,
                                              alignment: Alignment.center,
                                              padding: EdgeInsets.all(0),
                                              child: instructionTextStyleTitleExpandablePanel('Subscription'),
                                            ),
                                            onTap:  (){
                                              setState(() {
                                                agentProfileButtonsVisibilityBool = false;
                                                agentProfileVisibilityBool = false;
                                                agentProfilePaymentTermsVisibilityBool = false;
                                                Navigator.pushReplacementNamed(context, MarketScreen.route,);
                                                // resetValueUserProfile();
                                              });
                                            }

                                        )),
                                    SizedBox(height: 20),
                                    /// Right/terms of use
                                    Tooltip(
                                        textStyle: toolTipTextStyle(),
                                        message: "Click/Tap to Show",
                                        child:InkWell(
                                            borderRadius: BorderRadius.all(Radius.circular(100)),
                                            splashColor: Colors.lightGreen.shade900,
                                            hoverColor: Colors.white12,
                                            child: Container(
                                                height: 40,
                                                alignment: Alignment.center,
                                                padding: EdgeInsets.all(0),
                                                child: instructionTextStyleTitleExpandablePanel('Policy/Terms of Use')
                                            ),
                                            onTap:  (){
                                              setState(() {
                                                Navigator.pushReplacementNamed(context, TermsOfServicePrivacyState.route,);
                                              });
                                            }

                                        )),
                                    SizedBox(height: 20),
                                    /// Contact Us
                                    Tooltip(
                                        textStyle: toolTipTextStyle(),
                                        message: "Click/Tap",
                                        child:InkWell(
                                            borderRadius: BorderRadius.all(Radius.circular(100)),
                                            splashColor: Colors.lightGreen.shade900,
                                            hoverColor: Colors.white12,
                                            child: Container(
                                                height: 40,
                                                alignment: Alignment.center,
                                                padding: EdgeInsets.all(0),
                                                child: instructionTextStyleTitleExpandablePanel('Contact Us')
                                            ),
                                            onTap:  (){
                                              setState(() {
                                                Navigator.pushReplacementNamed(context, ContactUs.route);
                                              });
                                            }

                                        )),
                                    SizedBox(height: 30),
                                    /// Delete Account
                                    Tooltip(
                                        textStyle: toolTipTextStyle(),
                                        message: "Click/Tap",
                                        child:InkWell(
                                            borderRadius: BorderRadius.all(Radius.circular(100)),
                                            splashColor: Colors.lightGreen.shade900,
                                            hoverColor: Colors.white12,
                                            child: Container(
                                                height: 40,
                                                alignment: Alignment.center,
                                                padding: EdgeInsets.all(0),
                                                child: deleteUserTextStyleTitleExpandablePanel('Delete User Account')
                                            ),
                                            onTap:  (){
                                              setState(() {
                                                deleteUserAccountDialog(context);
                                              });
                                            }

                                        )),
                                    SizedBox(height: 40),

                                  ],)
                              )
                          ),
                          SizedBox(height: 100),
                          /// All rights reserved
                          Container(
                            alignment: Alignment.bottomCenter,
                            child: Text("All Rights Reserved  " + yyMmDd.format(DateTime.now()),
                              style: TextStyle(color: Colors.white60,fontSize: 10),
                            ),
                          )
                        ],)
                    ),
                  ),
                  /// userProfile Right Side
                  Visibility(
                      visible: agentProfileVisibilityBool,
                      child: Material(
                          color: Colors.white,
                          shadowColor: tealShadowColorRGB(),
                          elevation: elevationTextContainer(),
                          child:Container(
                            height: MediaQuery.of(context).size.height/1,
                            width: MediaQuery.of(context).size.width/1,
                              child: Center(
                                  child: userProfileWidget()

                              )
                          )
                      )
                  ),
                  SizedBox(
                    child: Container(color: Colors.black),
                    height: 100,
                  ),
                ],
              ),),
              /// Circular Progress Indicator with counter
              Visibility(
                  visible: dialogReusableCircularIndicatorVisibilityBool,
                  child: Container(
                      color: Colors.black,
                      height: MediaQuery.of(context).size.height,
                      width: MediaQuery.of(context).size.width,
                      alignment: Alignment.center,
                      child:BackdropFilter(
                        filter: ImageFilter.blur(sigmaX: 40.0, sigmaY: 40.0,),
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            CircularProgressIndicator(
                              value: reusableCircularIndicatorValue,
                              semanticsLabel: "Sending ...",
                              backgroundColor: Colors.teal,
                              valueColor: AlwaysStoppedAnimation(Colors.white),
                              strokeWidth: 5,
                            ),
                            SizedBox(height: 20),
                            Container(
                              alignment: Alignment.center,
                              child: Text("Percent:  " + (reusableCircularIndicatorValue * 10).round().toString() + " %", style: TextStyle(fontSize: 20, color: Colors.white),),
                            ),
                            SizedBox(height: 15),
                            Container(
                                padding: const EdgeInsets.symmetric(vertical: 25.0),
                                alignment: Alignment.center,
                                width: 300,
                                child:Text('Sending ...In Progress,',maxLines: 3, style: TextStyle(fontSize: 18, color: Colors.white,),textAlign: TextAlign.center,)),
                          ],),
                      )
                  )
              ),
              /// Circular Progress Indicator No counter
              Container(
                height: MediaQuery.of(context).size.height/2,
                width: MediaQuery.of(context).size.width/2,
                alignment: Alignment.center,
                child: Visibility(
                  //visible: true,
                    visible: noCounterReusableCircularIndicatorVisibilityBool,
                    child: BackdropFilter(
                      filter: ImageFilter.blur(sigmaX: 20.0, sigmaY: 20.0,),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          SizedBox(height: 20),
                          CircularProgressIndicator(
                            value: reusableCircularIndicatorValue,
                            semanticsLabel: "In progress ...",
                            backgroundColor: Colors.teal,
                            valueColor: AlwaysStoppedAnimation(Colors.black38),
                            strokeWidth: 5,
                          ),
                          SizedBox(height: 20),
                          Container(
                            alignment: Alignment.center,
                            child: Text((reusableCircularIndicatorValue * 10).round().toString() +'   ...In Progress,', style: TextStyle(fontSize: 20, color: Colors.white),),
                          ),
                          SizedBox(height: 10),
                        ],),
                    )
                ),),
              /// Circular Progress Indicator No counter
              Container(
                alignment: Alignment.center,
                child: Visibility(
                    visible: mainPageReusableCircularIndicatorVisibilityBool,
                    child: BackdropFilter(
                      filter: ImageFilter.blur(sigmaX: 100.0, sigmaY: 100.0,),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,

                        children: [
                          CircularProgressIndicator(
                            value: reusableCircularIndicatorValue,
                            semanticsLabel: "In progress ...",
                            backgroundColor: Colors.teal,
                            valueColor: AlwaysStoppedAnimation(Colors.black38),
                            strokeWidth: 5,
                          ),
                          SizedBox(height: 20),
                          Container(
                            alignment: Alignment.center,
                            child: Text((reusableCircularIndicatorValue * 10).round().toString() +'   ...In Progress,', style: TextStyle(fontSize: 20, color: Colors.white),),
                          ),
                          SizedBox(height: 10),
                        ],),
                    )
                ),),

            ])

    );
  }
  Widget userProfileWidget() {
    return Scaffold(
        backgroundColor: Color.fromRGBO(212, 210, 210, 1),
        resizeToAvoidBottomInset: false,
        appBar: PreferredSize(
            preferredSize: (kIsWeb == true || Platform.isMacOS == true)?Size.fromHeight(0):Size.fromHeight(60),
            child: (kIsWeb == true || Platform.isMacOS == true)
                ?Container(color: Colors.transparent,)
                :Container(
              /// Transparent Enables to show background image from scaffold
              color: Colors.transparent,
              child: Stack(
                children: [
                  /// Back button
                  Container(
                      alignment: Alignment.topLeft,
                      margin: EdgeInsets.only(left: 10, top: 20),
                      child: Visibility(
                        visible: invisibleWhenDeletePage,
                        child: mobileAgentProfileBackButton(setState),
                      )
                  ),
                ],
              ),
            )
        ),
        body: Stack(children: <Widget>[
          SingleChildScrollView(
              child: Column(children: [
                /// Profile
                Container(
                  alignment: Alignment.topCenter,
                  child: ConstrainedBox(
                      constraints: BoxConstraints(
                        //minHeight: MediaQuery.of(context).size.height/2,
                          maxWidth: textFieldSized+100
                      ),
                      child: Column(
                        children: [
                          Visibility(
                              visible: true,
                              child:  SizedBox(
                                height: (kIsWeb == true || Platform.isMacOS == true)?20:10,
                              )),
                          Form(
                            key: userProfileFormKey,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              mainAxisSize: MainAxisSize.min,
                              children: <Widget>[
                                /// Text ... Edit
                                Container(
                                  alignment: Alignment.bottomRight,
                                  child: Visibility(
                                    visible:true,
                                    // visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                    //&& dialogReusableCircularIndicatorVisibilityBool == false),
                                    child: (isProfileEdit == false)
                                        ?Text("Edit . . .",style: TextStyle(fontSize: 12),)
                                        : GestureDetector(
                                      onTap: (){
                                        setState((){

                                          resetBooleanValues(setState);
                                          userProfilePopulateController(context,setState);


                                          /// resets Main Contact number text Container visibility to visible
                                          contactNumberTextContainerVisibilityBool = true;
                                          /// resets Business Contact number text Container visibility to visible
                                          businessContactNumberTextContainerVisibilityBool= true;
                                          isBusinessPhoneNumberChangingBool = false;
                                          editProfileBusinessPhoneNumberBool = false;
                                          editBusinessNo = false;
                                          isProfileEdit = false;
                                          userCancelPhoneVerification = true;
                                        });

                                        //Navigator.of(context).pop();
                                      },
                                      child: Align(
                                          alignment: Alignment.topRight,
                                          child: Row(
                                            mainAxisAlignment: MainAxisAlignment.end,
                                            crossAxisAlignment: CrossAxisAlignment.center,
                                            children: [
                                              Text("Cancel . . . ",style: TextStyle(fontSize: 12),),
                                              CircleAvatar(
                                                radius: 10.0,
                                                backgroundColor: Colors.black,
                                                child: Icon(Icons.close, color: Colors.red, size: 14,),
                                              ),
                                            ],)
                                      ),
                                    ),
                                  ),

                                ),
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false),
                                  child:SizedBox(height: 5),),
                                /// FirstLast Name Container
                                Visibility(
                                  visible: (
                                      noCounterReusableCircularIndicatorVisibilityBool == false
                                          && dialogReusableCircularIndicatorVisibilityBool == false
                                          && firstLastNameContainerVisibilityBool
                                  ),

                                  child:firstLastNameContainer(context, setState),
                                ),
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false),
                                  child:SizedBox(height: 10),),
                                /// Email Text Container
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false
                                      && emailContainerVisibilityBool
                                  ),
                                  child: emailContainer(context, setState),
                                ),
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false),
                                  child:SizedBox(height: 10),),
                                /// Contact Number Text Container
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false
                                      && phoneNumberContainerVisibilityBool
                                  ),
                                  child: phoneNumberContainer(context, setState),
                                ),
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false),
                                  child:SizedBox(height: 10),),
                                /// Business Contact No.

                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false
                                      && businessPhoneNumberContainerVisibilityBool
                                  ),
                                  child: phoneNumberBusinessContainer(context, setState),
                                ),
                                /// SizedBox
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false),
                                  child:SizedBox(height: 10),),
                                /// Old Password Text Container
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false
                                      && passWordContainerVisibilityBool
                                  ),
                                  child: passwordContainer(context, setState),
                                ),
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false),
                                  child:SizedBox(height: 30),),
                              ],
                            ),
                          ),
                          SizedBox(
                            height: 20,
                          ),
                          Visibility(
                            visible: (kIsWeb == true || Platform.isMacOS == true)?false:true,
                              child:  SizedBox(
                            height: 100,
                          ))
                        ],
                      )),
                ),
              ],)),

        ]));

  }
  Widget userPaymentWidget() {
    return Scaffold(
        backgroundColor: Color.fromRGBO(212, 210, 210, 1),
        resizeToAvoidBottomInset: false,
        appBar: PreferredSize(
            preferredSize: (kIsWeb == true || Platform.isMacOS == true)?Size.fromHeight(0):Size.fromHeight(60),
            child: (kIsWeb == true || Platform.isMacOS == true)
                ?Container(color: Colors.transparent,)
                :Container(
              /// Transparent Enables to show background image from scaffold
              color: Colors.transparent,
              child: Stack(
                children: [
                  /// Back button
                  Container(
                      alignment: Alignment.topLeft,
                      margin: EdgeInsets.only(left: 10, top: 20),
                      child: Visibility(
                        visible: invisibleWhenDeletePage,
                        child: mobileAgentProfileBackButton(setState),
                      )
                  ),
                ],
              ),
            )
        ),
        body: (isStripePaymentPlatform == true)
            ?Stack(children: <Widget>[
          SingleChildScrollView(
              child: Column(children: [
                Container(
                  alignment: Alignment.topCenter,
                  child: ConstrainedBox(
                      constraints: BoxConstraints(
                          maxWidth: textFieldSized+100
                      ),
                      child: Column(
                        children: [
                          SizedBox(
                            height: 20,
                          ),
                          Form(
                            //key: userProfileFormKey,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              mainAxisSize: MainAxisSize.min,
                              children: <Widget>[
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false),
                                  child:SizedBox(height: 30),),
                                /// Payment due date text
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                                    child:Padding(
                                        padding: EdgeInsets.all(5.0),
                                        child:Container(
                                          width: widthTextContainerMainProfilePage(context),
                                          child:Text("Payment Due Date:",
                                              overflow: TextOverflow.ellipsis,
                                              maxLines:1,
                                              style: agentProfileHintLabelStyleTextGoogleFonts(),
                                              textAlign: TextAlign.start),)
                                    )
                                ),
                                /// Sized Box
                                SizedBox(height: 10),
                                /// Due Date Text Container
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                    child:Material(
                                        borderRadius: BorderRadius.circular(12),
                                        color: Colors.white,
                                        //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                        shadowColor: (editProfileEmailBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                                        elevation: elevationTextContainer(),
                                        child: Padding(
                                            padding: EdgeInsets.all(5.0),
                                            child:Container(
                                                width: widthTextContainerMainProfilePage(context),
                                                child:ListTile(
                                                  leading: Icon(Icons.payment_outlined, size: 24, color:Colors.teal.shade900),
                                                  title:Text(yyMmDd.format(userProfileDateListTemp.trialTime),
                                                      overflow: TextOverflow.ellipsis,
                                                      maxLines:1,
                                                      style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                                      textAlign: TextAlign.center),
                                                  trailing: GestureDetector(
                                                    onTap: () async {},
                                                    child:  CircleAvatar(
                                                        radius: 16.0,
                                                        backgroundColor: Colors.black12,
                                                        child: Icon(Icons.verified_user_outlined, color: Colors.teal, size: 16,)
                                                    ),
                                                  ),


                                                )
                                            )
                                        )
                                    )
                                ),
                                SizedBox(height: 30),
                                /// Current Subscription Title
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                                    child:Padding(
                                        padding: EdgeInsets.all(5.0),
                                        child:Container(
                                          width: widthTextContainerMainProfilePage(context),
                                          child:Text("Current Subscription:",
                                              overflow: TextOverflow.ellipsis,
                                              maxLines:1,
                                              style: agentProfileHintLabelStyleTextGoogleFonts(),
                                              textAlign: TextAlign.start),)
                                    )
                                ),
                                /// Sized Box
                                SizedBox(height: 10),
                                /// Current Subscription TextContainer
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                    child:Material(
                                        borderRadius: BorderRadius.circular(12),
                                        color: Colors.white,
                                        //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                        shadowColor: (editProfileEmailBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                                        elevation: elevationTextContainer(),
                                        child: Padding(
                                            padding: EdgeInsets.all(5.0),
                                            child:Container(
                                                width: widthTextContainerMainProfilePage(context),
                                                child:ListTile(
                                                  leading: Icon(Icons.payment_outlined, size: 24, color:Colors.teal.shade900),
                                                  title:Text(subscriptionString,
                                                      overflow: TextOverflow.ellipsis,
                                                      maxLines:1,
                                                      style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                                      textAlign: TextAlign.center),
                                                  trailing: GestureDetector(
                                                    onTap: () async {
                                                      getCustomer();
                                                    },
                                                    child:  CircleAvatar(
                                                        radius: 16.0,
                                                        backgroundColor: Colors.black12,
                                                        child: Icon(Icons.verified_user_outlined, color: Colors.teal, size: 16,)
                                                    ),
                                                  ),

                                                )
                                            )
                                        )
                                    )
                                ),
                                SizedBox(height: 30),
                                /// Payment Link Title
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                                    child:Padding(
                                        padding: EdgeInsets.all(5.0),
                                        child:Container(
                                          width: widthTextContainerMainProfilePage(context),
                                          child:Text("Payment Link:",
                                              overflow: TextOverflow.ellipsis,
                                              maxLines:1,
                                              style: GoogleFonts.sourceSerifPro(
                                                  color: Colors.black,
                                                  fontSize: 18,
                                                  fontWeight: FontWeight.bold,
                                                  letterSpacing: 1.2,
                                                  height: 1.5),
                                              textAlign: TextAlign.center
                                          ),
                                        )
                                    )
                                ),
                                SizedBox(height: 10),
                                (isUserAgentPaid == "PAID")
                                /// CustomerPortal pay link
                                    ?Column(children: [
                                  Padding(
                                      padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                      child:Material(
                                          borderRadius: BorderRadius.circular(12),
                                          color: Colors.teal,
                                          //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                          shadowColor: tealShadowColorRGB(),
                                          elevation: elevationTextContainer(),
                                          child: Padding(
                                              padding: EdgeInsets.all(5.0),
                                              child:Container(
                                                  width: widthTextContainerMainProfilePage(context),
                                                  child:ListTile(
                                                    leading: Icon(Icons.payments_outlined, size: 24, color:Colors.white),
                                                    title:Text('Receipt & Payment',
                                                        overflow: TextOverflow.ellipsis,
                                                        maxLines:1,
                                                        style: (isDarkThemBool ==true)?styleTextGoogleWhiteFontsTextField():styleTextGoogleFontsDarkThemeTextField(),
                                                        textAlign: TextAlign.center),
                                                    trailing: GestureDetector(
                                                      child:  CircleAvatar(
                                                          radius: 16.0,
                                                          backgroundColor: Colors.black12,
                                                          child: Icon(Icons.verified_user_outlined, color: Colors.white, size: 16,)
                                                      ),
                                                    ),
                                                    onTap: () async {
                                                      /// Customer Portal Link
                                                      customerPortalLink();
                                                    },
                                                  )
                                              )
                                          )
                                      )
                                  ),
                                  SizedBox(height: 20),
                                ],)
                                    :Column(
                                  children: [
                                    /// Monthly pay link
                                    Padding(
                                        padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                        child:Material(
                                            borderRadius: BorderRadius.circular(12),
                                            color: Colors.teal,
                                            //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                            shadowColor: tealShadowColorRGB(),
                                            elevation: elevationTextContainer(),
                                            child: Padding(
                                                padding: EdgeInsets.all(5.0),
                                                child:Container(
                                                    width: widthTextContainerMainProfilePage(context),
                                                    child:ListTile(
                                                      leading: Icon(Icons.payments_outlined, size: 24, color:Colors.white),
                                                      title:Text('Monthly Payment',
                                                          overflow: TextOverflow.ellipsis,
                                                          maxLines:1,
                                                          style: (isDarkThemBool ==true)?styleTextGoogleWhiteFontsTextField():styleTextGoogleFontsDarkThemeTextField(),
                                                          textAlign: TextAlign.center),
                                                      trailing: GestureDetector(
                                                        child:  CircleAvatar(
                                                            radius: 16.0,
                                                            backgroundColor: Colors.black12,
                                                            child: Icon(Icons.verified_user_outlined, color: Colors.white, size: 16,)
                                                        ),
                                                      ),
                                                      onTap: () async {
                                                        // webhookEndPoints();
                                                        // print(webhookEndPoints().toString() + " webhook");
                                                        launchUrlMonthlyPaymentURL(context);
                                                      },
                                                    )
                                                )
                                            )
                                        )
                                    ),
                                    SizedBox(height: 30),
                                    /// Yearly pay link
                                    Padding(
                                        padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                        child:Material(
                                            borderRadius: BorderRadius.circular(12),
                                            color: Colors.teal,
                                            //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                            shadowColor: tealShadowColorRGB(),
                                            elevation: elevationTextContainer(),
                                            child: Padding(
                                                padding: EdgeInsets.all(5.0),
                                                child:Container(
                                                    width: widthTextContainerMainProfilePage(context),
                                                    child:ListTile(
                                                      leading: Icon(Icons.payments_outlined, size: 24, color:Colors.white),
                                                      title:Text('Yearly Payment',
                                                          overflow: TextOverflow.ellipsis,
                                                          maxLines:1,
                                                          style: (isDarkThemBool ==true)?styleTextGoogleWhiteFontsTextField():styleTextGoogleFontsDarkThemeTextField(),
                                                          textAlign: TextAlign.center),
                                                      trailing: GestureDetector(
                                                        child:  CircleAvatar(
                                                            radius: 16.0,
                                                            backgroundColor: Colors.black12,
                                                            child: Icon(Icons.verified_user_outlined, color: Colors.white, size: 16,)
                                                        ),
                                                      ),
                                                      onTap: () async {
                                                        launchUrlYearlyPaymentURL();
                                                      },
                                                    )
                                                )
                                            )
                                        )
                                    ),
                                  ],),
                                SizedBox(height: 60),
                                /// Pause Payment dialog
                                Visibility(
                                  visible: (subscriptionCancellationString == 'NOTONCANCELLATION')?true:false,
                                  child: Padding(
                                      padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                      child:Material(
                                          borderRadius: BorderRadius.circular(12),
                                          color: (subscriptionStatusString != 'NotOnPaymentPause')?Colors.blueGrey.shade50.withOpacity(0.8):Colors.red.shade900,
                                          //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                          shadowColor: tealShadowColorRGB(),
                                          elevation: elevationTextContainer(),
                                          child: Padding(
                                              padding: EdgeInsets.all(0.0),
                                              child:Container(
                                                  width: textFieldSized,
                                                  child:ListTile(
                                                    title:Text((subscriptionStatusString != 'NotOnPaymentPause')?"IS ON PAUSE":'Pause Payment/Subscription',
                                                        overflow: TextOverflow.ellipsis,
                                                        maxLines:1,
                                                        style: (isDarkThemBool ==false)?dashBoardLandingPageRegularStyleTextGoogleFontsDark():dashBoardLandingPageRegularStyleTextGoogleFonts(),
                                                        textAlign: TextAlign.center),
                                                    onTap: () async {
                                                      if(subscriptionStatusString == 'NotOnPaymentPause'){
                                                        /// Requesting pause payment
                                                        requestPausedSubscriptionDialog(context);
                                                      }
                                                    },
                                                  )
                                              )
                                          )
                                      )
                                  ),
                                ),
                                /// Refund link
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                    child:Material(
                                        borderRadius: BorderRadius.circular(12),
                                        color: Colors.red.shade900,
                                        //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                        shadowColor: tealShadowColorRGB(),
                                        elevation: elevationTextContainer(),
                                        child: Padding(
                                            padding: EdgeInsets.all(0.0),
                                            child:Container(
                                                width: textFieldSized,
                                                child:ListTile(
                                                  title:Text('Request a Refund',
                                                      overflow: TextOverflow.ellipsis,
                                                      maxLines:1,
                                                      style: (isDarkThemBool ==false)?dashBoardLandingPageRegularStyleTextGoogleFontsDark():dashBoardLandingPageRegularStyleTextGoogleFonts(),
                                                      textAlign: TextAlign.center),
                                                  onTap: () async {
                                                    ///TODO: refund dialog, link,
                                                    ///TODO: get confirmation and payment charge ID
                                                  },
                                                )
                                            )
                                        )
                                    )
                                ),
                              ],
                            ),
                          ),
                          SizedBox(
                            height: 20,
                          )
                        ],
                      )),
                )
              ],)),
        ])
            :Center(
            child: Container(
                alignment: Alignment(0, -0.4),
                child: Text(
                    "Your Current Payment Platform is "+paymentPlatformString.toUpperCase().toString()
                    +"\n\n"+stringForPaymentPlatform(),
                    textAlign: TextAlign.center,
                    style: TextStyle(color: Colors.red, fontSize: 18)))));

  }
  Widget mobileUserPaymentWidget() {
    return Scaffold(
        backgroundColor: Color.fromRGBO(212, 210, 210, 1),
        resizeToAvoidBottomInset: false,
        appBar: PreferredSize(
            preferredSize: (kIsWeb == true || Platform.isMacOS == true)?Size.fromHeight(0):Size.fromHeight(60),
            child: (kIsWeb == true || Platform.isMacOS == true)
                ?Container(color: Colors.transparent,)
                :Container(
              /// Transparent Enables to show background image from scaffold
              color: Colors.transparent,
              child: Stack(
                children: [
                  /// Back button
                  Container(
                      alignment: Alignment.topLeft,
                      margin: EdgeInsets.only(left: 10, top: 20),
                      child: Visibility(
                        visible: invisibleWhenDeletePage,
                        child: mobileAgentProfileBackButton(setState),
                      )
                  ),
                ],
              ),
            )
        ),
        body: Stack(children: <Widget>[
          SingleChildScrollView(
              child: Column(children: [
                Container(
                  alignment: Alignment.topCenter,
                  child: ConstrainedBox(
                      constraints: BoxConstraints(
                          maxWidth: textFieldSized+200,
                        minHeight: MediaQuery.of(context).size.height,
                      ),
                      child: Column(
                        children: [
                          Form(
                            //key: userProfileFormKey,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              mainAxisSize: MainAxisSize.min,
                              children: <Widget>[
                                Visibility(
                                  visible: (noCounterReusableCircularIndicatorVisibilityBool == false
                                      && dialogReusableCircularIndicatorVisibilityBool == false),
                                  child:SizedBox(height: 10),),
                                /// Payment due date text
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                                    child:Padding(
                                        padding: EdgeInsets.all(5.0),
                                        child:Container(
                                          alignment: Alignment.centerLeft,
                                          //width: widthTextContainerMainProfilePage(context),
                                          child:Text("Payment Due Date:",
                                              overflow: TextOverflow.ellipsis,
                                              maxLines:2,
                                              style: agentProfileHintLabelStyleTextGoogleFonts(),
                                              textAlign: TextAlign.start),)
                                    )
                                ),
                                /// Sized Box
                                SizedBox(height: 5),
                                /// Due Date Text Container
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                    child:Material(
                                        borderRadius: BorderRadius.circular(12),
                                        color: Colors.white,
                                        shadowColor: (editProfileEmailBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                                        elevation: elevationTextContainer(),
                                        child: Padding(
                                            padding: EdgeInsets.all(5.0),
                                            child:Container(
                                                //width: ,
                                                child:ListTile(
                                                  leading: Icon(Icons.payment_outlined, size: 24, color:Colors.teal.shade900),
                                                  title:Text((userProfileDateListTemp.subscription != 'TRIAL 15 DAYS')
                                                      ?yyMmDd.format(userProfileDateListTemp.trialTime)
                                                      :yyMmDd.format(userProfileDateListTemp.trialTime.add(Duration(days: 14))
                                                  ),
                                                      overflow: TextOverflow.ellipsis,
                                                      maxLines:2,
                                                      style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                                      textAlign: TextAlign.center),
                                                  trailing: GestureDetector(
                                                    onTap: () async {},
                                                    child:  CircleAvatar(
                                                        radius: 16.0,
                                                        backgroundColor: Colors.black12,
                                                        child: Icon(Icons.verified_user_outlined, color: Colors.teal, size: 16,)
                                                    ),
                                                  ),


                                                )
                                            )
                                        )
                                    )
                                ),
                                SizedBox(height: 20),
                                /// Current Subscription Title
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                                    child:Padding(
                                        padding: EdgeInsets.all(5.0),
                                        child:Container(
                                          alignment: Alignment.centerLeft,
                                          //width: widthTextContainerMainProfilePage(context),
                                          child:Text("Current Subscription:",
                                              overflow: TextOverflow.ellipsis,
                                              maxLines:2,
                                              style: agentProfileHintLabelStyleTextGoogleFonts(),
                                              textAlign: TextAlign.start),)
                                    )
                                ),
                                /// Sized Box
                                SizedBox(height: 5),
                                /// Current Subscription TextContainer
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                    child:Material(
                                        borderRadius: BorderRadius.circular(12),
                                        color: Colors.white,
                                        //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                        shadowColor: (editProfileEmailBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                                        elevation: elevationTextContainer(),
                                        child: Padding(
                                            padding: EdgeInsets.all(5.0),
                                            child:Container(
                                                //width: widthTextContainerMainProfilePage(context),
                                                child:ListTile(
                                                  leading: Icon(Icons.payment_outlined, size: 24, color:Colors.teal.shade900),
                                                  title:Text(subscriptionString,
                                                      overflow: TextOverflow.ellipsis,
                                                      maxLines:2,
                                                      style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                                      textAlign: TextAlign.center),
                                                  trailing: GestureDetector(
                                                    onTap: () async {
                                                      getCustomer();
                                                    },
                                                    child:  CircleAvatar(
                                                        radius: 16.0,
                                                        backgroundColor: Colors.black12,
                                                        child: Icon(Icons.verified_user_outlined, color: Colors.teal, size: 16,)
                                                    ),
                                                  ),

                                                )
                                            )
                                        )
                                    )
                                ),
                                SizedBox(height: 20),
                                /// Payment Link Title
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                                    child:Padding(
                                        padding: EdgeInsets.all(5.0),
                                        child:Container(
                                          //width: widthTextContainerMainProfilePage(context),
                                          alignment: Alignment.centerLeft,
                                          child:Text("Payment Link:",
                                              overflow: TextOverflow.ellipsis,
                                              maxLines:2,
                                              style: GoogleFonts.sourceSerifPro(
                                                  color: Colors.black,
                                                  fontSize: 18,
                                                  fontWeight: FontWeight.bold,
                                                  letterSpacing: 1.2,
                                                  height: 1.5),
                                              textAlign: TextAlign.center
                                          ),
                                        )
                                    )
                                ),
                                SizedBox(height: 10),
                                (isUserAgentPaid == "PAID")
                                /// CustomerPortal pay link
                                    ?Column(children: [
                                  Padding(
                                      padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                      child:Material(
                                          borderRadius: BorderRadius.circular(12),
                                          color: Colors.teal,
                                          //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                          shadowColor: tealShadowColorRGB(),
                                          elevation: elevationTextContainer(),
                                          child: Padding(
                                              padding: EdgeInsets.all(5.0),
                                              child:Container(
                                                  //width: widthTextContainerMainProfilePage(context),
                                                  child:ListTile(
                                                    leading: Icon(Icons.payments_outlined, size: 24, color:Colors.white),
                                                    title:Text('Receipt & Payment',
                                                        overflow: TextOverflow.ellipsis,
                                                        maxLines:2,
                                                        style: (isDarkThemBool ==true)?styleTextGoogleWhiteFontsTextField():styleTextGoogleFontsDarkThemeTextField(),
                                                        textAlign: TextAlign.center),
                                                    trailing: GestureDetector(
                                                      child:  CircleAvatar(
                                                          radius: 16.0,
                                                          backgroundColor: Colors.black12,
                                                          child: Icon(Icons.verified_user_outlined, color: Colors.white, size: 16,)
                                                      ),
                                                    ),
                                                    onTap: () async {
                                                      /// Customer Portal Link
                                                      mobileCustomerPortalLink();
                                                    },
                                                  )
                                              )
                                          )
                                      )
                                  ),
                                  SizedBox(height: 20),
                                ],)
                                    :Column(
                                  children: [
                                    /// Monthly pay link
                                    Padding(
                                        padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                        child:Material(
                                            borderRadius: BorderRadius.circular(12),
                                            color: Colors.teal,
                                            //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                            shadowColor: tealShadowColorRGB(),
                                            elevation: elevationTextContainer(),
                                            child: Padding(
                                                padding: EdgeInsets.all(5.0),
                                                child:Container(
                                                    //width: widthTextContainerMainProfilePage(context),
                                                    child:ListTile(
                                                      leading: Icon(Icons.payments_outlined, size: 24, color:Colors.white),
                                                      title:Text('Monthly Payment',
                                                          overflow: TextOverflow.ellipsis,
                                                          maxLines:2,
                                                          style: (isDarkThemBool ==true)?styleTextGoogleWhiteFontsTextField():styleTextGoogleFontsDarkThemeTextField(),
                                                          textAlign: TextAlign.center),
                                                      trailing: GestureDetector(
                                                        child:  CircleAvatar(
                                                            radius: 16.0,
                                                            backgroundColor: Colors.black12,
                                                            child: Icon(Icons.verified_user_outlined, color: Colors.white, size: 16,)
                                                        ),
                                                      ),
                                                      onTap: () async {
                                                        // webhookEndPoints();
                                                        // print(webhookEndPoints().toString() + " webhook");
                                                        launchUrlMonthlyPaymentURL(context);
                                                      },
                                                    )
                                                )
                                            )
                                        )
                                    ),
                                    SizedBox(height: 30),
                                    /// Yearly pay link
                                    Padding(
                                        padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                        child:Material(
                                            borderRadius: BorderRadius.circular(12),
                                            color: Colors.teal,
                                            //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                            shadowColor: tealShadowColorRGB(),
                                            elevation: elevationTextContainer(),
                                            child: Padding(
                                                padding: EdgeInsets.all(5.0),
                                                child:Container(
                                                    //width: widthTextContainerMainProfilePage(context),
                                                    child:ListTile(
                                                      leading: Icon(Icons.payments_outlined, size: 24, color:Colors.white),
                                                      title:Text('Yearly Payment',
                                                          overflow: TextOverflow.ellipsis,
                                                          maxLines:1,
                                                          style: (isDarkThemBool ==true)?styleTextGoogleWhiteFontsTextField():styleTextGoogleFontsDarkThemeTextField(),
                                                          textAlign: TextAlign.center),
                                                      trailing: GestureDetector(
                                                        child:  CircleAvatar(
                                                            radius: 16.0,
                                                            backgroundColor: Colors.black12,
                                                            child: Icon(Icons.verified_user_outlined, color: Colors.white, size: 16,)
                                                        ),
                                                      ),
                                                      onTap: () async {
                                                        launchUrlYearlyPaymentURL();
                                                      },
                                                    )
                                                )
                                            )
                                        )
                                    ),
                                  ],),
                                SizedBox(height: 10),
                                /// Pause Payment dialog
                                Visibility(
                                  visible: (subscriptionCancellationString == 'NOTONCANCELLATION')?true:false,
                                  child: Padding(
                                      padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                      child:Material(
                                          borderRadius: BorderRadius.circular(12),
                                          color: (subscriptionStatusString != 'NotOnPaymentPause')?Colors.yellow.shade900:Colors.red.shade900,
                                          //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
                                          shadowColor: tealShadowColorRGB(),
                                          elevation: elevationTextContainer(),
                                          child: Padding(
                                              padding: EdgeInsets.all(0.0),
                                              child:Container(
                                                 // width: textFieldSized+ ,
                                                  child:ListTile(
                                                    title:Text((subscriptionStatusString != 'NotOnPaymentPause')?"IS ON PAUSE":'Pause Payment/Subscription',
                                                        overflow: TextOverflow.ellipsis,
                                                        maxLines:2,
                                                        style: (isDarkThemBool ==false)?dashBoardLandingPageRegularStyleTextGoogleFontsDark():dashBoardLandingPageRegularStyleTextGoogleFonts(),
                                                        textAlign: TextAlign.center),
                                                    onTap: () async {
                                                      if(subscriptionStatusString == 'NotOnPaymentPause'){
                                                        /// Requesting pause payment
                                                        requestPausedSubscriptionDialog(context);
                                                      }
                                                    },
                                                  )
                                              )
                                          )
                                      )
                                  ),
                                ),
                                /// Refund link
                                Padding(
                                    padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                    child:Material(
                                        borderRadius: BorderRadius.circular(12),
                                        color: Colors.red.shade900,
                                        shadowColor: tealShadowColorRGB(),
                                        elevation: elevationTextContainer(),
                                        child: Padding(
                                            padding: EdgeInsets.all(0.0),
                                            child:Container(
                                                //width: textFieldSized,
                                                child:ListTile(
                                                  title:Text('Request a Refund',
                                                      overflow: TextOverflow.ellipsis,
                                                      maxLines:1,
                                                      style: (isDarkThemBool ==false)?dashBoardLandingPageRegularStyleTextGoogleFontsDark():dashBoardLandingPageRegularStyleTextGoogleFonts(),
                                                      textAlign: TextAlign.center),
                                                  onTap: () async {
                                                    ///TODO: refund dialog, link,
                                                    ///TODO: get confirmation and payment charge ID
                                                  },
                                                )
                                            )
                                        )
                                    )
                                ),
                              ],
                            ),
                          ),
                          SizedBox(
                            height: 100,
                          )
                        ],
                      )),
                )
              ],)),
        ]));

  }
  callPaymentPlatformDetails() async {
    UserPurchasedDetails? value = await getFireBaseSubscriptionPlatform();
    setState((){
      if (value!.userDeviceWhenPurchased == "StripePay"
          || value.subscriptionStatus == "CANCELLED"
          || value.userDeviceWhenPurchased == ""){
           isStripePaymentPlatform = true;
      }
      else{
           isStripePaymentPlatform = false;
      }
      paymentPlatformString = value.userDeviceWhenPurchased.toString();
    });

  }

}


mobileAgentProfileBackButton(setState){
  return GestureDetector(
    onTap: () {
      setState(() {
         agentProfileButtonsVisibilityBool = true;
         agentProfileVisibilityBool = false;
         agentProfilePaymentTermsVisibilityBool = false;
      });
    },
    child: Row(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Icon(Icons.arrow_back_ios_rounded, size: 18,color:Colors.teal.shade600 ),
          SizedBox(width: 2),
          /// TextField "Back"
          Tooltip(
            textStyle: toolTipTextStyle(),
            message: "Click to go back",
            child: Text("Back to Profile Page ",
                style: TextStyle(color: Colors.black, fontSize: 12)),
          )
        ]),
  );
}

populateValueFromFirebaseAuth() async {
  Map tempData ={};

  User? userData = FirebaseAuth.instance.currentUser;
  tempData['clientName'] = (userData!.displayName !=null)?userData.displayName:"EMPTY NAME";
  tempData['user'] = userData;

  return tempData;
}

/// Resets Value
  bool expandedUserProfile = true;
  bool expandedPrivacyPolicy = false;
  bool expandedTermsOfUse = false;
  bool expandedPersonalInfoGathering = false;
  bool expandedPaymentTerms = false;
  resetValueUserProfile(){

    expandedUserProfile = true;

    expandedPrivacyPolicy = false;
    expandedTermsOfUse = false;
    expandedPersonalInfoGathering = false;
    expandedPaymentTerms = false;
    //expandedPersonalInfoGathering = false;
  }
  resetValuePrivacyPolicy(){

    expandedPrivacyPolicy = true;

    expandedUserProfile = false;
    expandedTermsOfUse = false;
    expandedPersonalInfoGathering = false;
    expandedPaymentTerms = false;
    //expandedPersonalInfoGathering = false;
  }
  resetValueTermsOfUse(){

    expandedTermsOfUse = true;

    expandedUserProfile = false;
    expandedPrivacyPolicy = false;
    expandedPersonalInfoGathering = false;
    expandedPaymentTerms = false;
    //expandedPersonalInfoGathering = false;
  }
  resetValuePersonalInfoGathering(){

    expandedPersonalInfoGathering = true;

    expandedUserProfile = false;
    expandedPrivacyPolicy = false;
    expandedTermsOfUse = false;
    expandedPaymentTerms = false;
    //expandedPersonalInfoGathering = false;
  }
  resetValuePaymentTerms(){

    expandedPaymentTerms = true;

    expandedUserProfile = false;
    expandedPrivacyPolicy = false;
    expandedTermsOfUse = false;
    expandedPersonalInfoGathering = false;

    //expandedPersonalInfoGathering = false;
  }

  contactUsDialog(context){
    showDialog(
      //barrierColor: Colors.black,
        barrierDismissible: false,
        useSafeArea: true,
        context: context,
        builder: (context) {
          return BackdropFilter(
              filter: ImageFilter.blur(
                sigmaX: 15.0,
                sigmaY: 15.0,
              ),
              child: StatefulBuilder(builder: (context, setState) {
                return SingleChildScrollView(
                    child: Container(
                      //child: FittedBox(
                      // fit: BoxFit.scaleDown,
                      child: Stack(
                        children: <Widget>[
                          Container(
                            decoration: BoxDecoration(
                              // color: Colors.white,
                              //shape: BoxShape.rectangle,
                              borderRadius: BorderRadius.circular(16.0),
                            ),
                            child: AlertDialog(
                              scrollable: true,
                              backgroundColor: Colors.white,
                              title: Container(
                                alignment: Alignment.center,
                                child: Text(
                                  "Message Us",
                                  style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                ),
                              ),
                              content: Container(
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  mainAxisSize: MainAxisSize.min,
                                  children: <Widget>[
                                    SizedBox(height: 10),
                                    /// Set UP a Desktop and a mobile
                                    Container(
                                     child:  (kIsWeb ==true && Platform.isMacOS == true )?contactUsTextFieldContainerWebDesktop(context, setState)
                                         :null,
                                   ),
                                    Container(
                                      child:  (kIsWeb ==false && Platform.isMacOS == true )?contactUsTextFieldContainerWebDesktop(context, setState)
                                          :null,
                                    ),
                                    SizedBox(height: 10),
                                  ],
                                ),
                              ),
                              actions: <Widget>[
                                SizedBox(
                                  width: 5,
                                ),
                                SizedBox(
                                  height: 20,
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                      //)
                    ));
              }));
        });
  }
  contactUsTextFieldContainerWebDesktop(context, setState){

  return ConstrainedBox(
    constraints: BoxConstraints(minWidth: (kIsWeb==true && Platform.isMacOS == true)?textFieldSized + 300:textFieldSized),
    child: Column(
      children: [
        /// Email Password LogIn TextField
        Visibility(
            visible: true,
            //visible:(isLoggingInByEmailPassword == true && isLoggingInByPhoneNumber == false)?true: false,
            child: Column(
              children: [
                Form (
                  //key: addViewingScheduleFormKey,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    mainAxisSize: MainAxisSize.min,
                    children: <Widget>[
                      /// Add Subject TextField
                      Padding(
                          padding: EdgeInsets.fromLTRB(0, 0, 0, 0),
                          child:Material(
                              borderRadius: BorderRadius.circular(12),
                              color: Colors.white,
                              shadowColor: (editProfileFirstLastNameBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                              elevation: elevationTextContainer(),
                              child:  Container(
                                  constraints: BoxConstraints(maxWidth: textFieldSized+80),
                                      child:Padding(
                                          padding: EdgeInsets.fromLTRB(5,5,5,5),
                                          child: TextFormField(
                                          style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                          textAlignVertical: TextAlignVertical.center,
                                          //textAlign: TextAlign.center,
                                          autofocus: false,
                                          cursorColor: Colors.black,
                                          controller: contactUsSubjectController,
                                          decoration: InputDecoration(
                                            border: InputBorder.none,
                                            floatingLabelBehavior: FloatingLabelBehavior.never,
                                            labelStyle: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                            label: Text( "Add Subject"),
                                            prefixIcon: Icon(Icons.edit_outlined, color: Colors.black, size: 18),
                                            suffixIcon: IconButton(
                                              onPressed: () => setState(() {
                                                contactUsSubjectController.clear();
                                              }),
                                              icon: Icon(
                                                Icons.clear_rounded,
                                                size: 16,
                                                color: Colors.red.shade400,
                                              ),
                                            ),
                                          ),

                                          keyboardType: TextInputType.multiline,
                                          //maxLength: 100,
                                          //maxLengthEnforcement: MaxLengthEnforcement.enforced,
                                          minLines: 1,
                                          //hintText: "Time",
                                          onTap: (){setState((){
                                          });},
                                          onChanged:(value) {
                                            setState((){
                                              // _startViewingTimeController.text = value;
                                            });
                                          }
                                      )
                                      )
                              ),

                          )
                      ),
                      SizedBox(height: 5),
                      /// Message TextField
                      Padding(
                          padding: EdgeInsets.fromLTRB(0, 0, 0, 0),
                          child:Material(
                            borderRadius: BorderRadius.circular(12),
                            color: Colors.white,
                            shadowColor: (editProfileFirstLastNameBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                            elevation: elevationTextContainer(),
                            child:  Container(
                                constraints: BoxConstraints(maxWidth: textFieldSized+80),
                                child:Padding(
                                    padding: EdgeInsets.fromLTRB(5,5,5,5),
                                    child: TextFormField(
                                        style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                        autofocus: false,
                                        cursorColor: Colors.black,
                                        controller: contactUsMessageController,
                                        textAlignVertical: TextAlignVertical.center,
                                        decoration: InputDecoration(
                                          border: OutlineInputBorder(
                                            borderSide: BorderSide.none,              // No border
                                            borderRadius: BorderRadius.circular(20),  // Apply corner radius
                                          ),
                                          focusColor: Colors.black,
                                          floatingLabelBehavior: FloatingLabelBehavior.never,
                                          labelStyle: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                          label: Text( "Add Message"),
                                          prefixIcon: Icon(Icons.edit_outlined, color: Colors.black, size: 18),
                                          suffixIcon: IconButton(
                                            onPressed: () => setState(() {
                                              contactUsMessageController.clear();
                                            }),
                                            icon: Icon(
                                              Icons.clear_rounded,
                                              size: 16,
                                              color: Colors.red.shade400,
                                            ),
                                          ),
                                        ),
                                        keyboardType: TextInputType.multiline,
                                        maxLength: 1000,
                                        maxLengthEnforcement: MaxLengthEnforcement.enforced,
                                        maxLines: 10,
                                        //hintText: "Time",
                                        onTap: (){setState((){});},
                                        onChanged:(value) {setState((){
                                            // _startViewingTimeController.text = value;
                                          });
                                        }
                                    )
                                )
                            ),

                          )
                      ),
                      SizedBox(height: 40),
                      /// Clients Full Name
                      ConstrainedBox(
                          constraints: BoxConstraints(maxWidth: textFieldSized),
                          child: Container(alignment: Alignment.center,
                              child: Text('Sender:  '+firebaseAuth.currentUser!.displayName.toString(), style: dialogStyleTextGoogleFontsDark(),maxLines: 10,
                                textAlign: TextAlign.center,)
                          )),
                      SizedBox(height: 30),
                      /// Clients Email
                      ConstrainedBox(
                          constraints: BoxConstraints(maxWidth: textFieldSized),
                          child: Container(alignment: Alignment.center,
                              child: Text('Your Email:  '+firebaseAuth.currentUser!.email.toString(), style: dialogStyleTextGoogleFontsDark(),maxLines: 10,
                                textAlign: TextAlign.center,)
                          )),
                      SizedBox(height: 30),
                      /// kubo email
                      ConstrainedBox(
                          constraints: BoxConstraints(maxWidth: textFieldSized),
                          child: Container(alignment: Alignment.center,
                              child: Text('KuboHut Email:  support@kubomaster.com', style: dialogStyleTextGoogleFontsDark(),maxLines: 10,
                                textAlign: TextAlign.center,)
                          )),
                      SizedBox(height: 20),
                    ],
                  ),
                ),
              ],
            )),
        SizedBox(height: 25,),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            /// Cancel
            Container(
                width: 100,
                height: 50,
                decoration:BoxDecoration(shape: BoxShape.circle),
                alignment: Alignment.topCenter,
                child:InkWell(
                    borderRadius: BorderRadius.all(Radius.circular(100)),
                    splashColor: Colors.lightGreen.shade900,
                    hoverColor: Colors.white,
                    child: Container(
                      width: 100,
                      height: 100,
                      alignment: Alignment.center,
                      padding: EdgeInsets.all(0),
                      child: Text('Cancel', style: styleTextGoogleFontsTextTealNormal()),
                    ),
                    onTap:  (){
                      setState(() {
                        contactUsMessageController.clear();
                        Navigator.pop(context);
                      });
                    }

                )),
            SizedBox(width: 0,),
            ///Send
            Container(
                width: 100,
                height: 50,
                decoration:BoxDecoration(shape: BoxShape.circle),
                alignment: Alignment.topCenter,
                child:InkWell(
                    borderRadius: BorderRadius.all(Radius.circular(100)),
                    splashColor: Colors.lightGreen.shade900,
                    hoverColor: Colors.white,
                    child: Container(
                      width: 100,
                      height: 100,
                      alignment: Alignment.center,
                      padding: EdgeInsets.all(0),
                      child: Text('Send', style: styleTextGoogleFontsTextTealNormal()),
                    ),
                    onTap:  ()async{
                      //setState((){
                      emailContactUsKuboMaster(contactUsSubjectController.text,
                        contactUsMessageController.text,
                        firebaseAuth.currentUser!.displayName,
                        contactUsClientsEmailController.text,);
                      /// Delete when sending is setup
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(content: Text("Email SEnding for WEB not set up")),
                      );
                      //Navigator.of(context).pop();
                      //});
                    }

                )),

          ],),
        SizedBox(height: 20,),
      ],
    ),
  );
}

  paymentMonthlyDueDate(){
    DateTime tempDate;
    DateTime dateNow = DateTime.now();
    DateTime dateTrial = userProfileDateList[0].trialTime.add(Duration(days: 15));
    if(dateNow.month == dateTrial.month &&  dateNow.day < dateTrial.day){
      return yyMmDd.format(dateTrial);
      
    }
    else if(dateNow.month == dateTrial.month &&  dateNow.day > dateTrial.day){

      tempDate = DateTime(DateTime.now().year, DateTime.now().month+1, dateTrial.day);
      return yyMmDd.format(tempDate);

    }
    else if(dateNow.month != dateTrial.month &&  dateNow.day < dateTrial.day){
      tempDate = DateTime(DateTime.now().year, DateTime.now().month, dateTrial.day);
      return yyMmDd.format(tempDate);

    }
    else if (dateNow.month != dateTrial.month && dateNow.day > dateTrial.day){
      tempDate = DateTime(DateTime.now().year, DateTime.now().month+1, dateTrial.day);
      return yyMmDd.format(tempDate);
    }
  }
  paymentYearlyDueDate(){
  DateTime tempDate;
  DateTime dateNow = DateTime.now();
  DateTime dateTrial = trialTimeString.add(Duration(days: 15));
  if(dateNow.year == dateTrial.year &&  dateNow.day < dateTrial.day){
    return yyMmDd.format(dateTrial);

  }
  else if(dateNow.year == dateTrial.year &&  dateNow.day > dateTrial.day){

    tempDate = DateTime(dateNow.year + 1, dateTrial.month, dateTrial.day);
    return yyMmDd.format(tempDate);

  }
  else if(dateNow.year != dateTrial.year &&  dateNow.day < dateTrial.day){
    tempDate = DateTime(dateNow.year, dateTrial.month, dateTrial.day);
    return yyMmDd.format(tempDate);

  }
  else if (dateNow.year != dateTrial.year && dateNow.day > dateTrial.day){
    tempDate = DateTime(dateNow.year + 1, dateTrial.month, dateTrial.day);
    return yyMmDd.format(tempDate);
  }
}


/// Deleting Account
  deleteUserAccountDialog(context){
  showDialog(
    //barrierColor: Colors.black,
      barrierDismissible: false,
      useSafeArea: true,
      context: context,
      builder: (context) {
        return BackdropFilter(
            filter: ImageFilter.blur(
              sigmaX: 15.0,
              sigmaY: 15.0,
            ),
            child: StatefulBuilder(builder: (context, setState) {
              return SingleChildScrollView(
                  child: Container(
                    //child: FittedBox(
                    // fit: BoxFit.scaleDown,
                    child: Stack(
                      children: <Widget>[
                        Container(
                          decoration: BoxDecoration(
                            // color: Colors.white,
                            //shape: BoxShape.rectangle,
                            borderRadius: BorderRadius.circular(16.0),
                          ),
                          child: AlertDialog(
                            scrollable: true,
                            backgroundColor: Colors.white,
                            title: Container(
                              alignment: Alignment.center,
                              child: Text(
                                "Deleting User Account",
                                style: TextStyle(
                                  color: Colors.black,
                                ),
                              ),
                            ),
                            content: Container(
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                mainAxisSize: MainAxisSize.min,
                                children: <Widget>[
                                  SizedBox(height: 10),

                                  Visibility(
                                    visible: instructionStep01VisibilityBool,
                                    child: Container(
                                        width: textFieldSized,
                                        child: Padding(
                                            padding:
                                            EdgeInsets.fromLTRB(0, 0, 0, 0),
                                            child: Column(
                                                children:[
                                                  Text(" You are about to permanently delete your account",
                                                      style: GoogleFonts.sourceSerifPro(
                                                          color: Colors.red.shade900,
                                                          fontSize: 25,
                                                          fontWeight: FontWeight.normal,
                                                          letterSpacing: 1.2,
                                                          height: 1.5),
                                                      textAlign: TextAlign.center,
                                                      maxLines: 50,
                                                      overflow: TextOverflow.visible),
                                                  SizedBox(height: 30,),

                                                  Text("Sorry, to hear that your are deleting your account.  "
                                                      "Hope, we see you again as we evaluate and improve our services"
                                                      "\n\nThank you! and Good Day!",
                                                      style: GoogleFonts.montserrat(
                                                          color: Color.fromRGBO(
                                                              39, 37, 37, 1),
                                                          fontSize: 12,
                                                          fontWeight: FontWeight.normal,
                                                          letterSpacing: 1.2,
                                                          height: 1.5),
                                                      textAlign: TextAlign.justify,
                                                      maxLines: 50,
                                                      overflow: TextOverflow.visible),
                                                  SizedBox(height: 10,),
                                                ]
                                            )
                                        )
                                    ),
                                  ),

                                  SizedBox(height: 10),
                                ],
                              ),
                            ),
                            actions: <Widget>[
                              SizedBox(
                                width: 5,
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: <Widget>[

                                  /// Close Button
                                  Visibility(
                                    //visible: instructionCloseButtonBool,
                                    child: Container(
                                      alignment: Alignment.centerRight,
                                      child: ElevatedButton(
                                        child: Text(
                                          "Exit",
                                          style: TextStyle(
                                              color: Colors.white,
                                              fontWeight: FontWeight.bold),
                                        ),
                                        onPressed: () {
                                          setState(() {
                                            Navigator.pop(context);
                                          });
                                        },
                                      ),),
                                  ),
                                  SizedBox(width: 10,),
                                  /// Delete Button
                                  Visibility(
                                    //visible: instructionCloseButtonBool,
                                    child: Container(
                                      alignment: Alignment.centerRight,
                                      child: ElevatedButton(
                                        child: Text(
                                          "Delete",
                                          style: TextStyle(
                                              color: Colors.white,
                                              fontWeight: FontWeight.bold),
                                        ),
                                        onPressed: () {
                                          setState(() {
                                            firebaseAuth.currentUser!.delete();
                                            Navigator.pop(context);
                                          });
                                        },
                                      ),),
                                  ),
                                ], //<Widget>[]
                              ),
                              SizedBox(
                                height: 20,
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                    //)
                  ));
            }));
      });
}
  deleteUserTextStyleTitleExpandablePanel(text){
  return Text(text,
      style: GoogleFonts.sourceSerifPro(
          color: Colors.teal.shade100,
          fontSize: 12,
          fontWeight: FontWeight.bold,
          letterSpacing: 1.2,
          height: 1.5),
      textAlign: TextAlign.left,
      maxLines: 50,
      overflow: TextOverflow.visible);
}
  agentProfileHintLabelStyleTextGoogleFonts(){
  return GoogleFonts.sourceSerifPro(color: Color.fromRGBO(60, 60, 60, 1),
      fontSize: 12,
      letterSpacing: 1.4,
      fontWeight: FontWeight.w100

  );
}

/// < = =  Start FirstLastName code = = >
///
/// FirstLast Name Container
  firstLastNameContainer(context, setState){

  return Column(children: [
    /// First Name Title
    Visibility(
        visible: (editProfileFirstLastNameBool == false && editOldPassWordBool == false)?true:false,
        child: Padding(
                padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                child:Padding(
                    padding: EdgeInsets.all(5.0),
                    child:Container(
                      //key: phoneContainerKey,
                      width: widthTextContainerMainProfilePage(context),
                      child:Text("First Name",
                          overflow: TextOverflow.ellipsis,
                          maxLines:1,
                          style: agentProfileHintLabelStyleTextGoogleFonts(),
                          textAlign: TextAlign.start),)
                )
            )
    ),
    /// First Name Text Container
    Visibility(
        visible: (editProfileFirstLastNameBool == false && editOldPassWordBool == false)?true:false,
        child: Tooltip(
            textStyle: toolTipTextStyle(),
            message: "Click Icon to Edit",
            child:Padding(
            padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
            child:Material(
                borderRadius: BorderRadius.circular(12),
                color: Colors.white,
                shadowColor: (editProfileFirstLastNameBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                elevation: elevationTextContainer(),
                child: Padding(
                    padding: EdgeInsets.all(5.0),
                    child:Container(
                      //key: phoneContainerKey,
                        width: widthTextContainerMainProfilePage(context),
                        child:ListTile(
                          leading: Icon(Icons.person_outline_rounded, size: 24,color:Colors.teal.shade900),
                          title:Text(userProfileFirstNameController.text.toUpperCase(),
                              overflow: TextOverflow.ellipsis,
                              maxLines:1,
                              style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                              textAlign: TextAlign.start),
                          trailing: GestureDetector(
                            onTap: (){
                              setState(() {
                                /// Resets all Boolean values
                                resetBooleanValues(setState);

                                isProfileEdit = true;
                                editProfileFirstLastNameBool = true;
                                userPasswordUpdateBool = false;
                              });
                            },
                            child: CircleAvatar(radius: 16.0,
                                backgroundColor: Colors.black12,
                                child: (isUpdateDisplayNameSuccessBool == false)
                                    ?Icon(Icons.edit, color: Colors.red.shade300, size: 16,)
                                    :Icon(Icons.verified_user_outlined, color: Colors.teal, size: 16,)
                            ),
                          ),

                        ))
                )
            )
        )
        )
    ),
    /// Sized Box
    Visibility(
        visible: (editProfileFirstLastNameBool == false && editOldPassWordBool == false)?true:false,
        child:SizedBox(height: 0)),
    /// Last Name Title
    Visibility(
        visible: (editProfileFirstLastNameBool == false && editOldPassWordBool == false)?true:false,
        child: Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child:Padding(
                padding: EdgeInsets.all(5.0),
                child:Container(
                  //key: phoneContainerKey,
                  width: widthTextContainerMainProfilePage(context),
                  child:Text("Last Name",
                      overflow: TextOverflow.ellipsis,
                      maxLines:1,
                      style: agentProfileHintLabelStyleTextGoogleFonts(),
                      textAlign: TextAlign.start),)
            )
        )
    ),
    /// Last Name Text Container
    Visibility(
        visible: (editProfileFirstLastNameBool == false && editOldPassWordBool == false)?true:false,
        child: Tooltip(
            textStyle: toolTipTextStyle(),
            message: "Click Icon to Edit",
            child:Padding(
            padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
            child:Material(
                borderRadius: BorderRadius.circular(12),
                color: Colors.white,
                shadowColor: (editProfileFirstLastNameBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                elevation: elevationTextContainer(),
                child: Padding(
                    padding: EdgeInsets.all(5.0),
                    child:Container(
                      //key: phoneContainerKey,
                        width: widthTextContainerMainProfilePage(context),
                        child:ListTile(
                          leading: Icon(Icons.person_outline_rounded, size: 24, ),
                          title:Text(userProfileLastNameController.text.toUpperCase(),
                              overflow: TextOverflow.ellipsis,
                              maxLines:1,
                              style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                              textAlign: TextAlign.start),

                          trailing: GestureDetector(
                            onTap: (){
                              setState(() {
                                /// Resets all Boolean values
                                resetBooleanValues(setState);

                                isProfileEdit = true;
                                editProfileFirstLastNameBool = true;
                                userPasswordUpdateBool = false;

                              });
                            },
                            child: CircleAvatar(
                                radius: 16.0,
                                backgroundColor: Colors.black12,
                                child: (isUpdateDisplayNameSuccessBool == false)
                                    ?Icon(Icons.edit, color: Colors.red.shade300, size: 16,)
                                    :Icon(Icons.verified_user_outlined, color: Colors.teal, size: 16,)
                            ),
                          ),
                        )
                    )
                )
            )
        ))
    ),
    /// Sized Box
    Visibility(
        visible: editProfileFirstLastNameBool,
        child:SizedBox(height: 25)),
    /// First Name TextFormField
    Container(
        constraints: BoxConstraints(maxWidth: textFieldSized),
        child:Visibility(
        visible: editProfileFirstLastNameBool,
        child:TextFormField(
            cursorColor: Colors.white60,
            style: TextStyle(color: Colors.white),
            textAlign: TextAlign.center,
            enabled: (editProfileFirstLastNameBool ==true)?true:false,
            autofocus: true,
            controller: userProfileFirstNameController,
            decoration: InputDecoration(
              floatingLabelBehavior: FloatingLabelBehavior.never, //Hides label on focus or if filled
              labelText: "First Name",
              filled: true, // Needed for adding a fill color
              fillColor: Colors.grey.shade800,
              isDense: true,  // Reduces height a bit
              border: OutlineInputBorder(
                borderSide: BorderSide.none,              // No border
                borderRadius: BorderRadius.circular(12),  // Apply corner radius
              ),
              prefixIcon: Icon(Icons.person_add_outlined, size: 20, color: Colors.white,),
              suffixIcon: IconButton(
                onPressed: () => setState(() {userProfileFirstNameController.clear();}),
                icon: Icon(Icons.clear_rounded, size: 12, color: Colors.red,),
              ),
            ),
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Please enter lowercase FirstName only !!!';
              }
              return null;
            },
            onTap: (){setState((){});},
            onChanged:(value) {setState((){});}
        ))
    ),
    /// Sized Box
    Visibility(
        visible: editProfileFirstLastNameBool,
        child:SizedBox(height: 25)),
    /// Last Name TextFormField
    Container(
        constraints: BoxConstraints(maxWidth: textFieldSized),
        child:Visibility(
        visible: editProfileFirstLastNameBool,
        child:TextFormField(
            cursorColor: Colors.white60,
            style: TextStyle(color: Colors.white),
            textAlign: TextAlign.center,
            enabled: (editProfileFirstLastNameBool == true)?true:false,
            controller: userProfileLastNameController,
            decoration: InputDecoration(
              floatingLabelBehavior: FloatingLabelBehavior.never, //Hides label on focus or if filled
              labelText: "Last Name",
              filled: true, // Needed for adding a fill color
              fillColor: Colors.grey.shade800,
              isDense: true,  // Reduces height a bit
              border: OutlineInputBorder(
                borderSide: BorderSide.none,              // No border
                borderRadius: BorderRadius.circular(12),  // Apply corner radius
              ),
              prefixIcon: Icon(Icons.person_add_outlined, size: 20, color: Colors.white,),
              suffixIcon: IconButton(
                onPressed: () => setState(() {userProfileLastNameController.clear();}),
                icon: Icon(Icons.clear_rounded, size: 12, color: Colors.red,),
              ),
            ),
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Must Not be empty and lower case only !!!';
              }
              return null;
            },
            onTap: (){setState((){});},
            onChanged:(value) {setState((){
            });}
        ))
    ),
    /// Sized Box
    Visibility(
        visible: editProfileFirstLastNameBool,
        child:SizedBox(height: 10)),
    /// First Last Name Save button
    Visibility(
        visible: (editProfileFirstLastNameBool == true
            && isUpdateDisplayNameSuccessBool == false
            && successUserDisplayNameVisibilityBool == false),
        child: Column(
            children:[
              ElevatedButton(
                onPressed: () {
                  if( userProfileFirstNameController.text != ""
                      && userProfileLastNameController.text != ""
                      && userProfileFormKey.currentState!.validate()){
                    setState(() {
                      /// Resets all Boolean values
                      resetBooleanValues(setState);

                      /// call dialog where circular indicator
                      timerValue = 3;
                      stringLabel = "  . . . Updating profile";
                      (kIsWeb == true || Platform.isMacOS == true)
                          ?reusableCircularProgressIndicatorDialog(context)
                          :mobileReusableCircularProgressIndicatorDialog(context);

                      userProfileFirstNameController.text =userProfileFirstNameController.text.toLowerCase();
                      userProfileLastNameController.text = userProfileLastNameController.text.toLowerCase();

                      mainPageVisibilityBool = false;
                      Future.delayed(Duration(milliseconds: 500),(){
                        userDisplayNameUpdate(
                            userProfileFirstNameController.text.toLowerCase(),
                            userProfileLastNameController.text.toLowerCase(),
                            context, setState);
                      });
                    });
                  }else{
                    return;
                  }
                },
                child: Text('Save Changes'),
              ),
              SizedBox(height: 20),]
        )
    ),
    /// Error FirstLast Name Update TextContainer
    Visibility(
      //visible: true
        visible: (editProfileFirstLastNameBool == true
            && isUpdateDisplayNameSuccessBool == false
            && errorUserDisplayNameVisibilityBool == true
            && successUserDisplayNameVisibilityBool == false
        ),
        child: Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child:errorFirstLastNameTextContainer()
        )
    ),
    /// Success FirstLast Name Update TextContainer
    Visibility(
        visible: false,
        //visible: (editProfileFirstLastNameBool == false
        //&& isUpdateDisplayNameSuccessBool == true
        //&& errorUserDisplayNameVisibilityBool == false
        //&& successUserDisplayNameVisibilityBool == true
        //),
        child: Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child:successFirstLastNameTextContainer()
        )
    ),
    /// Sized Box
    Visibility(
        visible: editProfileFirstLastNameBool,
        child:SizedBox(height: 10)),
    /// Divider
    Visibility(
        visible: editProfileFirstLastNameBool,
        child: Column(
          children: [
            Divider(
                thickness: 2,
                color: Colors.black54
            ),
            SizedBox(height: 2),
            Text("Any changes on "
                "\nEmail or Phone Number "
                "\nwill require verification", style: TextStyle(),textAlign: TextAlign.center,),
            SizedBox(height: 2),
            Divider(
                thickness: 1,
                color: Colors.black54
            ),
          ],
        )
    ),
  ]);
}
/// Displays Success or Error on FirstLast Name Update
  errorFirstLastNameTextContainer(){

  return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        //SizedBox(height: 25,),
        Text("Error Updating First/Last Name!",
            overflow: TextOverflow.visible,
            maxLines:4,
            style: (isDarkThemBool ==true)?errorStyleTextGoogleFontsDarkThemeVerificationText():errorStyleTextGoogleFontsVerificationText(),
            textAlign: TextAlign.center),
        SizedBox(height: 10,),
        Container(constraints: BoxConstraints(maxWidth: 300),
          child: Text(errorFirstLastNameString,
              overflow: TextOverflow.ellipsis,
              maxLines:10,
              style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
              textAlign: TextAlign.start),),
        SizedBox(height: 10,),
        SizedBox(height: 10,),
        //Text("closing in .. $counterToCloseRedirectValue secs",
        //overflow: TextOverflow.ellipsis,
        //maxLines:1,
        //style: styleTextGoogleFontsTextField(),
        //textAlign: TextAlign.start),
      ]);

}
  successFirstLastNameTextContainer(){
  return Column(
    crossAxisAlignment: CrossAxisAlignment.center,
    mainAxisAlignment: MainAxisAlignment.center,
    children: [
      Text("Successfully Updated !",
          overflow: TextOverflow.ellipsis,
          maxLines:3,
          style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
          textAlign: TextAlign.start),
      SizedBox(height: 10,),
      SizedBox(height: 10,),
      //Text("closing in .. $counterToCloseRedirectValue secs",
      //overflow: TextOverflow.ellipsis,
      //maxLines:1,
      //style: styleTextGoogleFontsTextField(),
      //textAlign: TextAlign.start),
    ],);
}


/// < -- Start Password Code -- >
///
/// Password Container
passwordContainer(context, setState){
  return Column(children: [
    /// Password Title
    Visibility(
        visible: (editProfileFirstLastNameBool == false && editOldPassWordBool == false)?true:false,
        child: Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child:Padding(
                padding: EdgeInsets.all(5.0),
                child:Container(
                  //key: phoneContainerKey,
                  width: widthTextContainerMainProfilePage(context),
                  child:Text("Password",
                      overflow: TextOverflow.ellipsis,
                      maxLines:1,
                      style: agentProfileHintLabelStyleTextGoogleFonts(),
                      textAlign: TextAlign.start),)
            )
        )
    ),
    /// Old Password Text Container
    Visibility(
        visible: (editOldPassWordBool == true && editProfileFirstLastNameBool == false)?false:true,
        child: Tooltip(
            textStyle: toolTipTextStyle(),
            message: "Click Icon to Edit",
            child:Padding(
            padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
            child:Material(
                borderRadius: BorderRadius.circular(12),
                color: Colors.white,
                shadowColor: (editOldPassWordBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                elevation: elevationTextContainer(),
                child: Padding(
                    padding: EdgeInsets.all(5.0),
                    child:Container(
                      //key: phoneContainerKey,
                        width: widthTextContainerMainProfilePage(context),
                        child:ListTile(
                          leading: Icon(Icons.lock_outline_rounded, size: 24, color:Colors.teal.shade900),
                          title:Text((obscuredOldPassword)
                              ?"*" * userProfileOldPasswordController.text.length
                              :userProfileOldPasswordController.text,
                              overflow: TextOverflow.ellipsis,maxLines:1,
                              style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                              textAlign:  TextAlign.start),
                          trailing:GestureDetector(
                              onTap: (){
                                if(editProfileFirstLastNameBool == false){
                                  setState(() {
                                    /// Resets all Boolean values
                                    resetBooleanValues(setState);

                                    isProfileEdit = true;

                                    editOldPassWordBool = true;
                                    oldPasswordReAuthButtonVisibilityBool = true;
                                    userProfileOldPasswordController
                                        .clear();
                                    mainPageReusableCircularIndicatorVisibilityBool = false;
                                  });
                                }
                              },
                              child: CircleAvatar(
                                  radius: 16.0,
                                  backgroundColor: Colors.black12,
                                  child: (isNewPasswordReAuthSuccessBool == false)
                                      ?editIconsOnOff(editProfileFirstLastNameBool == false)
                                      :Icon(Icons.verified_user_outlined, color: Colors.teal, size: 16,)

                              )),

                        ))
                )
            )
        ))
    ),
    /// Text Container for "Changing Password...."
    Visibility(
        visible:(editOldPassWordBool == true
            && newPasswordVisibilityBool == false
            && editProfileFirstLastNameBool == false),
        child:Text("To change Password."
            "\nMust enter OLD Password and Re - Authenticate.", textAlign: TextAlign.center,
          style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),)
    ),
    SizedBox(height: 20),
    /// Old Password TextFormField
    Container(
        constraints: BoxConstraints(maxWidth: 300),
        child:Visibility(
            visible: (editOldPassWordBool == true && editProfileFirstLastNameBool == false),
            child:TextFormField(
                maxLength: 100,
                cursorColor: Colors.white60,
                style: TextStyle(color: Colors.white),
                autofocus: true,
                textAlign: TextAlign.center,
                enabled: (editOldPassWordBool == true && isOldPasswordReAuthSuccessBool == false)?true:false,
                obscureText: obscuredOldPassword,
                controller: userProfileOldPasswordController,
                decoration: InputDecoration(
                  floatingLabelBehavior: FloatingLabelBehavior.never, //Hides label on focus or if filled
                  labelText: "Enter Old Password",
                  filled: true, // Needed for adding a fill color
                  fillColor: Colors.grey.shade800,
                  isDense: true,  // Reduces height a bit
                  border: OutlineInputBorder(
                    borderSide: BorderSide.none,              // No border
                    borderRadius: BorderRadius.circular(12),  // Apply corner radius
                  ),
                  prefixIcon: (isOldPasswordReAuthSuccessBool == false)
                      ?Icon(Icons.lock_open_rounded, size: 18, color:Colors.white)
                      :Icon(Icons.lock_rounded, size: 18,color:Colors.white),
                  suffixIcon: (isOldPasswordReAuthSuccessBool)
                      ?Icon(Icons.verified_user_outlined, color: Colors.teal, size: 18,)
                      :Padding(
                        padding: const EdgeInsets.fromLTRB(0, 0, 4, 0),
                        child: GestureDetector(
                          onTap: (){
                              setState(() {
                                obscuredOldPassword = !obscuredOldPassword;
                              });
                            },
                          child: Icon(
                            obscuredOldPassword
                                ? Icons.visibility_rounded
                                : Icons.visibility_off_rounded,
                            size: 24, color: Colors.red.shade400,
                          ),),),
                ),
                validator: (value){
                  if(value == null || value.isEmpty || value.length < 6){
                    return 'Please enter your old password';
                  }
                  return null;
                },
                onTap: (){setState((){});},
                onChanged:(value) {setState((){
                  oldPasswordString = userProfileOldPasswordController.text;
                });}
            )
        )
    ),
    /// Sized Box
    Visibility(
        visible:(editOldPassWordBool == true
            && oldPasswordReAuthButtonVisibilityBool == true
            && editProfileFirstLastNameBool == false
        ),
        child:SizedBox(height: 25)),
    /// PassWord Re- Authenticate Button
    Visibility(
      visible:(editOldPassWordBool == true
          && oldPasswordReAuthButtonVisibilityBool == true
          && editProfileFirstLastNameBool == false
      ),
      child: ElevatedButton(
        onPressed: ()  {
          setState(() {
            if(editOldPassWordBool == true){

              /// call dialog where circular indicator
              timerValue = 1;
              stringLabel = "  . . . In Progress";
              (kIsWeb == true || Platform.isMacOS == true)
                  ?reusableCircularProgressIndicatorDialog(context)
                  :mobileReusableCircularProgressIndicatorDialog(context);

              Future.delayed(Duration(milliseconds: 5),(){
                mainPageVisibilityBool = false;
                userReAuthentication(userProfileEmailController.text,
                    userProfileOldPasswordController.text, context, setState);
              });

            }else{
              return;
            }
          });

        },
        child: Text('Re - Authenticate'),
      ),),
    /// SizedBox
    Visibility(
      visible: editOldPassWordBool == true
          && oldPasswordReAuthButtonVisibilityBool == true
          && editProfileFirstLastNameBool == false
          && errorPassWordReAuthVisibilityContainerBool == true
          && successPassWordReAuthVisibilityContainerBool == false
          && mainPageReusableCircularIndicatorVisibilityBool == false,
      child:SizedBox(height: 25),),
    /// Success OLD Re- authentication TextContainer
    Visibility(
        visible: (editOldPassWordBool == true
            && oldPasswordReAuthButtonVisibilityBool == false
            && editProfileFirstLastNameBool == false
            && errorPassWordReAuthVisibilityContainerBool == false
            && successPassWordReAuthVisibilityContainerBool == true
            && mainPageReusableCircularIndicatorVisibilityBool == false
        ),
        child: Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child: successPassWordReAuthTextContainer()
        )
    ),
    /// Error OLD Re- authentication TextContainer
    Visibility(
      //visible: true
        visible: (editOldPassWordBool == true
            && oldPasswordReAuthButtonVisibilityBool == true
            && editProfileFirstLastNameBool == false
            && errorPassWordReAuthVisibilityContainerBool == true
            && successPassWordReAuthVisibilityContainerBool == false
            && mainPageReusableCircularIndicatorVisibilityBool == false
        ),
        child: Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child: errorPassWordReAuthTextContainer()
        )
    ),
    /// Requirements for New Password Authentication TextContainer
    Visibility(
      //visible: true
        visible: (editOldPassWordBool == true
            && newPasswordVisibilityBool == true
            && editProfileFirstLastNameBool == false
            && errorPassWordReAuthVisibilityContainerBool == false
            && successPassWordReAuthVisibilityContainerBool == false
            && mainPageReusableCircularIndicatorVisibilityBool == false
        ),
        child: Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child:newPassWordRequirementsTextContainer()
        )
    ),
    /// New Password TextFormField
    Container(
        constraints: BoxConstraints(maxWidth: 300),
        child:Visibility(
          visible: (editOldPassWordBool == true
              && newPasswordVisibilityBool == true
              && editProfileFirstLastNameBool == false
              && errorPassWordReAuthVisibilityContainerBool == false
              && successPassWordReAuthVisibilityContainerBool == false
              && mainPageReusableCircularIndicatorVisibilityBool == false
          ),
          child:TextFormField(
              cursorColor: Colors.white60,
              style: TextStyle(color: Colors.white),
              enabled: newPasswordVisibilityBool,
              obscureText: obscuredNewPassword,
              controller: userProfileNewPasswordController,
              decoration: InputDecoration(
                floatingLabelBehavior: FloatingLabelBehavior.never, //Hides label on focus or if filled
                labelText: "New Password",
                filled: true, // Needed for adding a fill color
                fillColor: Colors.grey.shade800,
                isDense: true,  // Reduces height a bit
                border: OutlineInputBorder(
                  borderSide: BorderSide.none,              // No border
                  borderRadius: BorderRadius.circular(12),  // Apply corner radius
                ),
                prefixIcon: Icon(Icons.lock_rounded, size: 24,color:Colors.white),
                suffixIcon: Padding(
                  padding: const EdgeInsets.fromLTRB(0, 0, 4, 0),
                  child: GestureDetector(
                    onTap: (){
                      setState(() {
                        obscuredNewPassword = !obscuredNewPassword;
                      });
                    },
                    child: Icon(
                      obscuredNewPassword
                          ? Icons.visibility_rounded
                          : Icons.visibility_off_rounded,
                      size: 24,
                    ),
                  ),
                ),
              ),
              validator: (value){
                if(value == null || value.isEmpty || value.length < 6){
                  return 'Please enter a at least Six unique character';
                }
                return null;
              },
              onTap: (){setState((){});},
              onChanged:(value) {setState((){});}
          ),
        )
    ),
    SizedBox(height: 25),
    /// New Password Repeat TextFormField
    Container(
        constraints: BoxConstraints(maxWidth: 300),
        child:Visibility(
          visible: (editOldPassWordBool == true
              && newPasswordVisibilityBool == true
              && editProfileFirstLastNameBool == false
              && errorPassWordReAuthVisibilityContainerBool == false
              && successPassWordReAuthVisibilityContainerBool == false
              && mainPageReusableCircularIndicatorVisibilityBool == false
          ),
          child: TextFormField(
              cursorColor: Colors.white60,
              style: TextStyle(color: Colors.white),
              enabled: newPasswordVisibilityBool,
              obscureText: obscuredRepeatNewPassword,
              controller: userProfilePasswordRepeatController,
              decoration: InputDecoration(
                floatingLabelBehavior: FloatingLabelBehavior.never, //Hides label on focus or if filled
                labelText: "Confirm New Password",
                filled: true, // Needed for adding a fill color
                fillColor: Colors.grey.shade800,
                isDense: true,  // Reduces height a bit
                border: OutlineInputBorder(
                  borderSide: BorderSide.none,              // No border
                  borderRadius: BorderRadius.circular(12),  // Apply corner radius
                ),
                prefixIcon: Icon(Icons.lock_outline_rounded, size: 24, color: Colors.white),
                suffixIcon: Padding(
                  padding: const EdgeInsets.fromLTRB(0, 0, 4, 0),
                  child: GestureDetector(
                    onTap: (){
                      setState(() {
                        obscuredRepeatNewPassword = !obscuredRepeatNewPassword;
                      });
                    },
                    child: Icon(
                      obscuredRepeatNewPassword
                          ? Icons.visibility_rounded
                          : Icons.visibility_off_rounded,
                      size: 24,
                    ),
                  ),
                ),
              ),
              validator: (value){
                if(value != userProfileNewPasswordController.text
                    || userProfileNewPasswordController.text.isEmpty
                    || value!.isEmpty){
                  return 'Password Mismatch';
                }
                return null;
              },
              onTap: (){setState((){});},
              onChanged:(value) {setState((){});}
          ),
        )
    ),
    SizedBox(height: 25),
    /// New PassWord Authenticate Button
    Visibility(
      visible:(editOldPassWordBool == true
          && newPasswordVisibilityBool == true
          && editProfileFirstLastNameBool == false
          && oldPasswordReAuthButtonVisibilityBool == false
          && errorPassWordReAuthVisibilityContainerBool == false
          && successPassWordReAuthVisibilityContainerBool == false
          && isNewPasswordReAuthSuccessBool == false
          && mainPageReusableCircularIndicatorVisibilityBool == false
      ),
      child: ElevatedButton(
        onPressed: ()  {
          setState(() {

            if(userProfileFormKey.currentState!.validate() && isPasswordCompliant(userProfilePasswordRepeatController.text)){

              timerValue = 2;
              stringLabel = "Updating new password   . . . In Progress";
              (kIsWeb == true || Platform.isMacOS == true)
                  ?reusableCircularProgressIndicatorDialog(context)
                  :mobileReusableCircularProgressIndicatorDialog(context);
              mainPageVisibilityBool = false;
              /// Resets all Boolean values
              resetBooleanValues(setState);
              Future.delayed(Duration(milliseconds: 500),(){
                userUpdateOldToNewPassword(userProfilePasswordRepeatController.text, context, setState);
              });



            }else{
              return;
            }
          });

        },
        child: Text('Authenticate New Password'),
      ),),
    /// Error New Password Authentication TextContainer
    Visibility(
      //visible: true
        visible: (editOldPassWordBool == true
            && newPasswordVisibilityBool == true
            && editProfileFirstLastNameBool == false
            && isNewPasswordReAuthSuccessBool == false
            && errorNewPasswordVisibilityContainerBool == true
            && successNewPasswordVisibilityContainerBool == false
            && mainPageReusableCircularIndicatorVisibilityBool == false
        ),
        child: Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child: errorNEWPassWordAuthTextContainer()
        )
    ),
    /// Successfully verified New Password Authentication TextContainer
    Visibility(
      //visible: true
        visible: (editOldPassWordBool == true
            && newPasswordVisibilityBool == true
            && editProfileFirstLastNameBool == false
            && isNewPasswordReAuthSuccessBool == true
            && errorNewPasswordVisibilityContainerBool == false
            && successNewPasswordVisibilityContainerBool == true
            && mainPageReusableCircularIndicatorVisibilityBool == false
        ),
        child: Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child: successPassWordReAuthTextContainer()
        )
    ),
  ],);
}
/// Displays Success or Error on NEW Password verification
newPassWordRequirementsTextContainer(){
  return  Container(

      decoration: BoxDecoration(
           //color: Colors.black26,
        borderRadius: BorderRadius.all(Radius.circular(0.0)),
          shape: BoxShape.rectangle,
          boxShadow: <BoxShadow>[
          ]),
      child: Column(

      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        SizedBox(height: 10,),
        Container(constraints: BoxConstraints(maxWidth: 300),
          child: Text("Must contain a minimum of"
              "\n* 6 characters, * uppercase, * lowercase, * numbers"
              "\n* specialized characters !@#%^&*",
              overflow: TextOverflow.ellipsis,
              maxLines:10,
              style: (isDarkThemBool==true)?styleTextGoogleFontsDarkThemeTextContainerNotes():styleTextGoogleFontsTextContainerNotes(),
              textAlign: TextAlign.center),),
        SizedBox(height: 20,),
      ])
  );
}
errorNEWPassWordAuthTextContainer(){

  return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        //SizedBox(height: 25,),
        Text("Error Authenticating Password!",
            overflow: TextOverflow.visible,
            maxLines:4,
            style: (isDarkThemBool==true)?errorStyleTextGoogleFontsDarkThemeVerificationText():errorStyleTextGoogleFontsVerificationText(),
            textAlign: TextAlign.center),
        SizedBox(height: 10,),
        ///Text container auto width sizing
        (isDarkThemBool==true)
        ?containerConstraintWidthDarkThemeDeviceSizing(errorNewPassUpdateAuthString)
        :containerConstraintWidthDeviceSizing(errorNewPassUpdateAuthString),
        SizedBox(height: 10,),
        SizedBox(height: 10,),
        //Text("closing in .. $counterToCloseRedirectValue secs",
        //overflow: TextOverflow.ellipsis,
        //maxLines:1,
        //style: styleTextGoogleFontsTextField(),
        //textAlign: TextAlign.start),
      ]);

}
/// Displays Success or Error on Old Password verification
errorPassWordReAuthTextContainer(){

  return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
    //SizedBox(height: 25,),
    Text("Error Re - Authenticating Password!",
        overflow: TextOverflow.visible,
        maxLines:4,
        style: (isDarkThemBool==true)?errorStyleTextGoogleFontsDarkThemeVerificationText():errorStyleTextGoogleFontsVerificationText(),
        textAlign: TextAlign.center),
    SizedBox(height: 10,),
        ///Text container auto width sizing
        (isDarkThemBool==true)
            ?containerConstraintWidthDarkThemeDeviceSizing(errorNewPassUpdateAuthString)
            :containerConstraintWidthDeviceSizing(errorNewPassUpdateAuthString),
    SizedBox(height: 10,),
    SizedBox(height: 10,),
    //Text("closing in .. $counterToCloseRedirectValue secs",
        //overflow: TextOverflow.ellipsis,
        //maxLines:1,
        //style: styleTextGoogleFontsTextField(),
        //textAlign: TextAlign.start),
  ]);

}
successPassWordReAuthTextContainer(){
  return Column(
    crossAxisAlignment: CrossAxisAlignment.center,
    mainAxisAlignment: MainAxisAlignment.center,
    children: [
    Text("Successfully Verified !",
        overflow: TextOverflow.ellipsis,
        maxLines:3,
        style: (isDarkThemBool==true)
            ?errorStyleTextGoogleFontsDarkThemeVerificationText()
            :errorStyleTextGoogleFontsVerificationText(),
        textAlign: TextAlign.start),
    SizedBox(height: 10,),
    SizedBox(height: 10,),
    //Text("closing in .. $counterToCloseRedirectValue secs",
        //overflow: TextOverflow.ellipsis,
        //maxLines:1,
        //style: styleTextGoogleFontsTextField(),
        //textAlign: TextAlign.start),
  ],);
}

/// < = = Start Email code = = >
///
/// Email Container
emailContainer(context, setState){
  return Column(children:[
    /// Email  Title
    Visibility(
        visible: (editProfileFirstLastNameBool == false && editOldPassWordBool == false)?true:false,
        child: Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child:Padding(
                padding: EdgeInsets.all(5.0),
                child:Container(
                  //key: phoneContainerKey,
                  width: widthTextContainerMainProfilePage(context),
                  child:Text("Email",
                      overflow: TextOverflow.ellipsis,
                      maxLines:1,
                      style: agentProfileHintLabelStyleTextGoogleFonts(),
                      textAlign: TextAlign.start),)
            )
        )
    ),
    /// Email Text Container
    Visibility(
      //visible:
      visible: (isEmailChangingBool == false && editOldPassWordBool == false)?true:false,
      child: Tooltip(
          textStyle: toolTipTextStyle(),
          message: "Click Icon to Edit",
          child:Padding(
          padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
          child:Material(
              borderRadius: BorderRadius.circular(12),
              color: Colors.white,
            //borderRadius: BorderRadius.only(topLeft: Radius.circular(50)),
              shadowColor: (editProfileEmailBool == true)?redShadowColorRGB():tealShadowColorRGB(),
              elevation: elevationTextContainer(),
              child: Padding(
                  padding: EdgeInsets.all(5.0),
                  child:Container(
                    //key: emailContainerKey,
                      width: widthTextContainerMainProfilePage(context),
                      child:ListTile(
                        leading: Icon(Icons.attach_email_outlined, size: 24, color:Colors.teal.shade900),
                        title:Text(userProfileEmailController.text,
                            overflow: TextOverflow.ellipsis,
                            maxLines:1,
                            style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                            textAlign: TextAlign.start),
                        trailing: GestureDetector(
                          onTap: () async {

                            /// Disables tap if editProfileFirstLastNameBool is true
                            if(editProfileFirstLastNameBool == false){
                              setState(() {
                                /// Resets all Boolean values
                                resetBooleanValues(setState);

                                isProfileEdit = true;

                                /// Call to close FirstLastName Visibility
                                firstLastNameContainerVisibilityBool = false;
                                phoneNumberContainerVisibilityBool = false;
                                businessPhoneNumberContainerVisibilityBool = false;
                                passWordContainerVisibilityBool = false;

                                isEmailChangingBool = true;
                                editProfileEmailBool = true;
                                editProfilePhoneNumberBool = false;
                              });
                              //await reusableDialog();
                              //setState((){});
                            }
                            else{
                              // Call to close FirstLastName Visibility
                              firstLastNameContainerVisibilityBool = false;
                            }
                          },
                          child:  CircleAvatar(
                              radius: 16.0,
                              backgroundColor: Colors.black12,
                              child: (isEmailVerified == false)
                                  ?editIconsOnOff(editProfileFirstLastNameBool == false)
                                  :Icon(Icons.verified_user_outlined, color: Colors.teal, size: 16,)
                          ),
                        ),


                      )
                  )
              )
          )
      ))
  ),
    Container(
        child: Column(
          children:[
            /// Sized Box
            Visibility(
                visible: (dialogReusableCircularIndicatorVisibilityBool == false
                    && editProfileEmailBool==true
                    && isEmailChangingBool == true),
                child:SizedBox(height: 15,)
            ),
            /// Title
            Visibility(
                visible: (dialogReusableCircularIndicatorVisibilityBool == false
                    && editProfileEmailBool==true
                    && isEmailChangingBool == true),
                child:Container(
                  alignment: Alignment.center,
                  child: Text("New Email", style: GoogleFonts.sourceSerifPro(
                  color: Colors.black,
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                      letterSpacing: 1.2,
                      height: 1.5),
                    textAlign: TextAlign.left,
                    maxLines: 50,
                    overflow: TextOverflow.visible),)
            ),
            /// Sized Box
            Visibility(
                visible: (dialogReusableCircularIndicatorVisibilityBool == false
                    && editProfileEmailBool==true
                    && isEmailChangingBool == true),
                child:SizedBox(height: 25,)
            ),
            /// Email TextFormField
            Visibility(
                visible: (dialogReusableCircularIndicatorVisibilityBool == false
                    && editProfileEmailBool==true
                    && isEmailChangingBool == true
                    && isNewEmailVerificationSuccessfulVisibilityBool == false
                ),
                child:Padding(
                    padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                    child:Form(
                        key:userEmailFormKey,
                        child: TextFormField(
                            cursorColor: Colors.white60,
                            style: TextStyle(color: Colors.white),
                            textAlign: TextAlign.center,
                            enabled: (editProfileEmailBool ==true)?true:false,
                            controller: userProfileEmailController,
                            decoration: InputDecoration(
                              floatingLabelBehavior: FloatingLabelBehavior.never, //Hides label on focus or if filled
                              labelText: "New Email",
                              filled: true, // Needed for adding a fill color
                              fillColor: Colors.grey.shade800,
                              isDense: true,  // Reduces height a bit
                              border: OutlineInputBorder(
                                borderSide: BorderSide.none,              // No border
                                borderRadius: BorderRadius.circular(10),  // Apply corner radius
                              ),
                              //prefixIcon: Icon(Icons.email_outlined, size: 24),
                              suffixIcon: IconButton(
                              onPressed: () => setState(() {userProfileEmailController.clear();}),
                              icon: Icon(Icons.clear_rounded, size: 12, color: Colors.red.shade400,),
                               ),
                            ),
                            validator: (value){
                              if(!isEmail(value!)){
                                return 'Please enter a valid email';
                              }
                              return null;
                            },
                            onTap: (){setState((){});},
                            onChanged:(value) {setState((){});}
                        )
                    ))
            ),
            /// Sized Box
            Visibility(
                visible: (dialogReusableCircularIndicatorVisibilityBool == false
                    && editProfileEmailBool==true
                    && isEmailChangingBool == true
                    && isNewEmailVerificationSuccessfulVisibilityBool == false
                ),
                child:SizedBox(height: 25,)
            ),
            ///  Old Email / Email already existed TextFormField
            Visibility(
              visible: (editProfileEmailBool == true
                  && editProfilePhoneNumberBool == false
                  && isEmailExistedOrOldBool ==true
                  && dialogReusableCircularIndicatorVisibilityBool == false
                  && editOldPassWordBool == false
                  && isNewEmailVerificationSuccessfulVisibilityBool == false
              ),
              child: Visibility(
                  visible: isEmailExistedOrOldBool,
                  child: Padding(
                      padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                      child:Column(children: [
                        Text("Email already existed",
                            overflow: TextOverflow.ellipsis,
                            maxLines:1,
                            style: errorStyleTextGoogleFontsVerificationText(),
                            textAlign: TextAlign.center),
                        SizedBox(height: 10,),
                        //Text(_userProfileEmailController.text,
                        //overflow: TextOverflow.ellipsis,
                        //maxLines:1,
                        //style: styleTextGoogleFontsTextField(),
                        //textAlign: TextAlign.start),
                      ],)
                  )
              ),
            ),
            /// Sized Box
            Visibility(
                visible: (dialogReusableCircularIndicatorVisibilityBool == false
                    && editProfileEmailBool==true
                    && isEmailChangingBool == true
                    && isNewEmailVerificationSuccessfulVisibilityBool == false
                ),
                child:SizedBox(height: 25,)
            ),
            ///Button to new Email verify
            Visibility(
              visible: (dialogReusableCircularIndicatorVisibilityBool == false
                  && editProfileEmailBool==true
                  && isEmailChangingBool == true
                  && isNewEmailVerificationSuccessfulVisibilityBool == false
              ),
              child: ElevatedButton(
                onPressed: () async {
                  dynamic tempData = await populateValueFromFirebaseAuth();
                  //print(tempData.toString() + " ElevatedButton");
                  setState(() {

                    emailUserStringValue = userProfileEmailController.text;
                    if(userEmailFormKey.currentState!.validate()) {
                      if (editProfileEmailBool == true
                          && tempData['user'].emailVerified == true
                          //fireAuth.currentUser?.emailVerified == true
                          && emailUserStringValue != tempData['user'].email
                          && isSentEmailVerificationLinkBool == false)
                      {

                        emailErrorTextContainerVisibilityBool = false;
                        isEmailChangingBool = true;
                        isEmailExistedOrOldBool = false;

                        reusableCircularIndicatorValue = .2;
                        circularReusableIndicatorValueTransitionOnly(setState);
                        dialogReusableCircularIndicatorVisibilityBool = true;

                        /// hides close button on reusable dialog
                        isEmailPhoneVerificationProcessingVisBool = false;

                        userUpdateCurrentUserEmail(context, setState, emailUserStringValue);
                      }

                      ///
                      /// Email existed, old email is verified
                      else if (editProfileEmailBool == true
                          && tempData['user'].emailVerified == true
                          //fireAuth.currentUser?.emailVerified == true
                          && emailUserStringValue != tempData['user'].email
                          && isSentEmailVerificationLinkBool == false)
                      {
                        //resetBooleanValues();

                        isEmailChangingBool = true;
                        isEmailExistedOrOldBool = true;
                        dialogReusableCircularIndicatorVisibilityBool = false;
                      }

                      /// User Request to re send new email verification link
                      else if (isSentEmailVerificationLinkBool == true){

                        //resetBooleanValues();

                        /// Resets Periodic Timer for checking if email is verified
                        /// This refreshes new set of timer when new email link is requested
                        /// and prevents multiple  the same timer being active
                        registrationTimer?.cancel();
                        updateEmailTimer?.cancel();

                        emailErrorTextContainerVisibilityBool = false;
                        isEmailChangingBool = true;
                        isEmailExistedOrOldBool = false;

                        //reusableCircularIndicatorValue =;
                        circularReusableIndicatorValueTransitionOnly(setState);
                        dialogReusableCircularIndicatorVisibilityBool = true;

                        userUpdateCurrentUserEmail(context, setState, emailUserStringValue);
                      }

                      else {
                        return;
                      }
                    }
                  });
                },child: (isSentEmailVerificationLinkBool == false)?Text('Verify'):Text('Re-Verify'),
              ),
            ),
            /// Sized Box
            Visibility(
                visible: (dialogReusableCircularIndicatorVisibilityBool == false
                    && editProfileEmailBool==true
                    && isEmailChangingBool == true
                    && isNewEmailVerificationSuccessfulVisibilityBool == false
                ),
                child:SizedBox(height: 10,)
            ),
            /// Email Text Container for Successfully verified new Email
            Visibility(
                visible: isNewEmailVerificationSuccessfulVisibilityBool,
                child: Padding(
                    padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                    child:verifiedEmailTextContainer(),
                )
            ),
            /// Sized Box
            Visibility(
                visible: isNewEmailVerificationSuccessfulVisibilityBool,
                child:SizedBox(height: 40,)
            ),
            /// Email Text Container for Error verified new Email
            Visibility(
              visible: (emailErrorTextContainerVisibilityBool == true
                  && dialogReusableCircularIndicatorVisibilityBool == false
                  && editOldPassWordBool == false
              ),
              child:Padding(
                padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                child: emailErrorTextContainer(""),
              ) ,),
            /// Sized Box
            Visibility(
                visible: isNewEmailVerificationSuccessfulVisibilityBool,
                child:SizedBox(height: 40,)
            ),
          ],
        )
    ),
    /// Sized Box
    Visibility(
        visible: (dialogReusableCircularIndicatorVisibilityBool == false
            && editProfileEmailBool==true
            && isEmailChangingBool == true),
        child:SizedBox(height: 40,)
    ),
    /// Email Verification is sent, and waiting for user to click the link
    Visibility(
          visible: (isEmailVerified == false
              && isSentEmailVerificationLinkBool == true
              && dialogReusableCircularIndicatorVisibilityBool == false
              && editOldPassWordBool == false
          ),
          child: Padding(
              padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
              child: (isDarkThemBool==true)
                  ?containerConstraintWidthDarkThemeDeviceSizing(emailVerificationSentString)
                  :containerConstraintWidthDeviceSizing(emailVerificationSentString)

          )
      ),
  ]);
}
/// Displays Success on new Email verification
verifiedEmailTextContainer(){
  return Column(

    crossAxisAlignment: CrossAxisAlignment.center,
    mainAxisAlignment: MainAxisAlignment.center,
    children: [
    //SizedBox(height: 25,),
    Text("Successfully Verified !",
        overflow: TextOverflow.ellipsis,
        maxLines:1,
        style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
        textAlign: TextAlign.start),
    SizedBox(height: 15,),
    Text(userProfileEmailController.text,
        overflow: TextOverflow.ellipsis,
        maxLines:1,
        style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
        textAlign: TextAlign.start),
    SizedBox(height: 25,),
    Text("closing in .. $reusableCircularIndicatorValue secs",
        overflow: TextOverflow.ellipsis,
        maxLines:1,
        style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
        textAlign: TextAlign.start),
  ],);
}
/// Email error Text
emailErrorTextContainer(String emailString){
  return Column(children: [
    SizedBox(height: 25,),
    Text("Error with your email!",
        overflow: TextOverflow.visible,
        maxLines:4,
        style: (isDarkThemBool ==true)?errorStyleTextGoogleFontsDarkThemeVerificationText():errorStyleTextGoogleFontsVerificationText(),
        textAlign: TextAlign.center),
    SizedBox(height: 10,),
    ///Text container auto width sizing
    containerConstraintWidthDeviceSizing(stringEmailError),
    SizedBox(height: 10,),

  ]);
}

/// <  - - Stat Phone Number Code - - >
phoneNumberContainer(context, setState){
  if(userProfileBusinessContactNoController.text != "" && userProfileBusinessContactNoController.text.length>10){
    tempStringBusinessPhoneNo = userProfileBusinessContactNoController.text.substring(2,userProfileBusinessContactNoController.text.length);
  }
  return Column(
    children: [
      /// Contact No. Title
      Visibility(
          visible: (
              editBusinessNo == false &&
              //businessContactNumberTextContainerVisibilityBool == true &&
              editProfileFirstLastNameBool == false
                  && editOldPassWordBool == false)?true:false,
          child: Padding(
              padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
              child:Padding(
                  padding: EdgeInsets.all(5.0),
                  child:Container(
                    //key: phoneContainerKey,
                    width: widthTextContainerMainProfilePage(context),
                    child:Text("Mobile Contact No.",
                        overflow: TextOverflow.ellipsis,
                        maxLines:1,
                        style: agentProfileHintLabelStyleTextGoogleFonts(),
                        textAlign: TextAlign.start),)
              )
          )
      ),
      /// Contact Number Text Container
      Visibility(
          visible: (
              businessContactNumberTextContainerVisibilityBool == true &&
              contactNumberTextContainerVisibilityBool == true
              && isPhoneNumberChangingBool == false
              && editOldPassWordBool == false
              )?true:false,
          child: Tooltip(
              textStyle: toolTipTextStyle(),
              message: "Click Icon to Edit",
              child:Padding(
              padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
              child:Material(
                  borderRadius: BorderRadius.circular(12),
                  color: Colors.white,
                  shadowColor: (isPhoneNumberChangingBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                  elevation: elevationTextContainer(),
                  child: Padding(
                      padding: EdgeInsets.all(5.0),
                      child:Container(
                        //key: phoneContainerKey,
                          width: widthTextContainerMainProfilePage(context),
                          child:ListTile(
                            leading: Icon(Icons.smartphone_outlined, size: 24, color:Colors.teal.shade900),
                            title:Text(userProfileContactNoController.text,
                                overflow: TextOverflow.ellipsis,
                                maxLines:1,
                                style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                textAlign: TextAlign.start),

                            trailing:GestureDetector(
                              onTap: (){
                                /// Disables tap if editProfileFirstLastNameBool is true
                                if(editProfileFirstLastNameBool == false){
                                  setState((){
                                    ///Keep at the top to avoid resetting values anything before it
                                    /// Resets all Boolean values
                                    resetBooleanValues(setState);
                                    /// END
                                    /// hides title for business phone number if true
                                    editSmsNo = true;

                                    isProfileEdit = true;
                                    /// Call to close Main Contact Number TextContainer
                                    contactNumberTextContainerVisibilityBool = false;

                                    /// Call to close FirstLastName Visibility
                                    firstLastNameContainerVisibilityBool = false;
                                    emailContainerVisibilityBool = false;
                                    passWordContainerVisibilityBool = false;

                                    isPhoneNumberChangingBool = true;
                                    editProfilePhoneNumberBool = true;

                                    /// Calls to clear TextFormField
                                    userProfileContactNoController.clear();
                                  });
                                }
                                else{
                                  // Call to close FirstLastName Visibility
                                  firstLastNameContainerVisibilityBool = false;
                                }
                              },
                              child:  CircleAvatar(
                                  radius: 16.0,
                                  backgroundColor: Colors.black12,
                                  child: (isPhoneNumberVerified == false)
                                      ?editIconsOnOff(editProfileFirstLastNameBool == false)
                                      :Icon(Icons.verified_user_outlined, color: Colors.teal, size: 16,)
                              ),
                            ),

                          ))
                  )
              )
          ))
      ),
      Container(
          child: Column(
            children:[
              /// Sized Box
              Visibility(
                  visible: (dialogReusableCircularIndicatorVisibilityBool == false
                      && getSMSCodeTextContainerVisibilityBool == false
                      && editProfilePhoneNumberBool==true
                      && isPhoneNumberChangingBool == true),
                  child:SizedBox(height: 15,)
              ),
              /// Title
              Visibility(
                  visible: (dialogReusableCircularIndicatorVisibilityBool == false
                      && getSMSCodeTextContainerVisibilityBool == false
                      && editProfilePhoneNumberBool==true
                      && isPhoneNumberChangingBool == true),
                  child:Container(
                    alignment: Alignment.center,
                    child: Text("New Mobile Phone Number",
                        style: GoogleFonts.sourceSerifPro(
                            color: Colors.black,
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                            letterSpacing: 1.2,
                            height: 1.5),
                        textAlign: TextAlign.left,
                        maxLines: 50,
                        overflow: TextOverflow.visible),)

              ),
              /// Sized Box
              Visibility(
                  visible: (dialogReusableCircularIndicatorVisibilityBool == false
                      && getSMSCodeTextContainerVisibilityBool == false
                      && editProfilePhoneNumberBool==true
                      && isPhoneNumberChangingBool == true
                      && isPhoneVerificationSuccessfulVisibilityBool == false
                  ),
                  child:SizedBox(height: 25,)
              ),
              /// Phone Number TextFormFiled
              Visibility(
                  visible: (editProfilePhoneNumberBool == true
                      && getSMSCodeTextContainerVisibilityBool == false
                      && isPhoneVerificationSuccessfulVisibilityBool == false
                      && dialogReusableCircularIndicatorVisibilityBool == false),
                  child: Padding(
                      padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                      child:Form(
                          key:userPhoneNumberFormKey,
                          child: TextFormField(
                              autofocus: true,
                              cursorColor: Colors.white60,
                              style: TextStyle(color: Colors.white),
                              textAlign: TextAlign.center,
                              controller: userProfileContactNoController,
                              enabled: (editProfilePhoneNumberBool == true)?true:false,
                              decoration: InputDecoration(
                                floatingLabelBehavior: FloatingLabelBehavior.never,
                                filled: true,
                                fillColor: Colors.grey.shade900,
                                isDense: true,
                                border: OutlineInputBorder(
                                  borderSide: BorderSide.none,
                                  borderRadius: BorderRadius.circular(12),
                                ),
                                suffixIcon: IconButton(
                                  onPressed: () => setState(() {userProfileContactNoController.clear();}),
                                  icon: Icon(Icons.clear_rounded, size: 12, color: Colors.red.shade400,),
                                ),
                              ),
                              inputFormatters: <TextInputFormatter>[FilteringTextInputFormatter.allow(RegExp(r'[0-9]')),],
                              validator:(value){
                                if (value == null || value.isEmpty || !isPhone(value) || value.length < 10 )
                                {
                                  return 'Please enter a valid phone number.'
                                      '\nAt least 10 numbers, no spaces'
                                      '\nand no " ( ), - , _ "';
                                }
                                return null;

                              },
                              onTap: (){
                                //setState((){});
                              },
                              onChanged:(value) {
                                //setState((){});
                              }
                          )
                      )
                  )
              ),
              /// Sized Box
              Visibility(
                  visible: (dialogReusableCircularIndicatorVisibilityBool == false
                      && getSMSCodeTextContainerVisibilityBool == false
                      && editProfilePhoneNumberBool==true
                      && isPhoneNumberChangingBool == true
                      && isPhoneVerificationSuccessfulVisibilityBool == false
                  ),
                  child:SizedBox(height: 25,)
              ),
              /// Phone Number Text Container existed and verified phone
              Visibility(
                visible: (isPhoneAlreadyExistedVisibilityBool == true
                    && getSMSCodeTextContainerVisibilityBool == false
                    && dialogReusableCircularIndicatorVisibilityBool == false
                    && isPhoneVerificationSuccessfulVisibilityBool == false
                ),
                child: Visibility(
                    visible: true,
                    //visible: fireAuth.currentUser?.emailVerified == true,
                    child: Padding(
                      padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                      child: phoneAlreadyExisted(),
                    )
                ),
              ),
              /// Sized Box
              Visibility(
                  visible: (dialogReusableCircularIndicatorVisibilityBool == false
                      && getSMSCodeTextContainerVisibilityBool == false
                      && editProfilePhoneNumberBool==true
                      && isPhoneNumberChangingBool == true
                      && isPhoneVerificationSuccessfulVisibilityBool == false
                  ),
                  child:SizedBox(height: 25,)
              ),
              /// Button for new Phone verify
              Visibility(
                visible: (editProfilePhoneNumberBool==true
                    && isPhoneVerificationSuccessfulVisibilityBool == false
                    && getSMSCodeTextContainerVisibilityBool == false
                    && dialogReusableCircularIndicatorVisibilityBool == false
                )?true:false,
                child: ElevatedButton(
                  onPressed: () {
                    setState(() {
                      //print(userProfileContactNoController.text + " userProfileContactNoController.text");
                      phoneNumber = ("+1"+userProfileContactNoController.text);

                      if(userPhoneNumberFormKey.currentState!.validate()
                          && editProfilePhoneNumberBool == true
                          && isSentSMSCodeBool == false) {

                        /// Calls for Circular Indicator then redirect to SMS code Dialog
                        dialogReusableCircularIndicatorVisibilityBool = true;

                        /// Calls circular indicator and sends SMS code
                        circularIndicatorForPhone(setState,context,phoneNumber);

                        /// hides close button on reusable dialog
                        isEmailPhoneVerificationProcessingVisBool = false;

                        /// calls to reset error checking
                        isSmsCodeVerificationHasErrorBool = false;

                        /// if true, call the get data from firebase and update sharedPref and variables
                        agentProfileUpdateBool = true;

                      }
                      else {
                        return;
                      }

                    });
                  },child: Text('Phone Verify'),
                ),),
              /// Sized Box
              Visibility(
                  visible:getSMSCodeTextContainerVisibilityBool,
                  child:SizedBox(height: 10,)
              ),
              /// Phone Number Text Form Field SMS Code input
              Visibility(
                  visible:(getSMSCodeTextContainerVisibilityBool
                      && userCancelPhoneVerification == false
                      && isPhoneVerificationSuccessfulVisibilityBool == false
                  ),
                  child: Padding(
                      padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                      child: (isDarkThemBool==true)
                          ?smsGetCodeDarkThemeTextContainer(setState,context)
                          :smsGetCodeTextContainer(setState, context)
                  )
              ),
              /// Sized Box
              Visibility(
                  visible: (dialogReusableCircularIndicatorVisibilityBool == false
                      && getSMSCodeTextContainerVisibilityBool == false
                      && editProfilePhoneNumberBool==true
                      && isPhoneNumberChangingBool == true
                      && isPhoneVerificationSuccessfulVisibilityBool == false
                  ),
                  child:SizedBox(height: 10,)
              ),
              /// Phone Number Text Container for Successfully verified new number
              Visibility(
                  visible: isPhoneVerificationSuccessfulVisibilityBool,
                  child: Padding(
                      padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                      child: verifiedPhoneTextContainer(setState)
                  )
              ),
              /// Sized Box
              Visibility(
                  visible: isPhoneVerificationSuccessfulVisibilityBool,
                  child:SizedBox(height: 40,)
              ),
            ],
          )
      ),

  ],);
}

/// <  - - Stat Phone Number Code - - >
String tempStringBusinessPhoneNo= "";
bool businessContactNumberTextContainerVisibilityBool = true;
bool isBusinessPhoneNumberChangingBool = false;
bool editProfileBusinessPhoneNumberBool = false;
bool editSmsNo = false;
bool editBusinessNo = false;
phoneNumberBusinessContainer(context, setState){
  return Column(
    children: [
      /// Contact No. Title
      Visibility(
          visible: (
               editSmsNo == false &&
              //contactNumberTextContainerVisibilityBool == true &&
              editProfileFirstLastNameBool == false && editOldPassWordBool == false)?true:false,
          child: Padding(
              padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
              child:Padding(
                  padding: EdgeInsets.all(5.0),
                  child:Container(
                    //key: phoneContainerKey,
                    width: widthTextContainerMainProfilePage(context),
                    child:Text("Business Contact No.",
                        overflow: TextOverflow.ellipsis,
                        maxLines:1,
                        style: agentProfileHintLabelStyleTextGoogleFonts(),
                        textAlign: TextAlign.start),)
              )
          )
      ),
      /// Contact Number Text Container
      Visibility(
          visible: (contactNumberTextContainerVisibilityBool == true &&
              businessContactNumberTextContainerVisibilityBool == true
              && isBusinessPhoneNumberChangingBool == false
              && editOldPassWordBool == false
          )?true:false,
          child: Tooltip(
              textStyle: toolTipTextStyle(),
              message: "Click Icon to Edit",
              child:Padding(
                  padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                  child:Material(
                      borderRadius: BorderRadius.circular(12),
                      color: Colors.white,
                      shadowColor: (isBusinessPhoneNumberChangingBool == true)?redShadowColorRGB():tealShadowColorRGB(),
                      elevation: elevationTextContainer(),
                      child: Padding(
                          padding: EdgeInsets.all(5.0),
                          child:Container(
                            //key: phoneContainerKey,
                              width: widthTextContainerMainProfilePage(context),
                              child:ListTile(
                                leading: Icon(Icons.phone, size: 24, color:Colors.teal.shade900),
                                title:Text((userProfileBusinessContactNoController.text!= "")?userProfileBusinessContactNoController.text:tempStringBusinessPhoneNo,
                                    overflow: TextOverflow.ellipsis,
                                    maxLines:1,
                                    style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
                                    textAlign: TextAlign.start),

                                trailing:GestureDetector(
                                  onTap: (){
                                    /// Disables tap if editProfileFirstLastNameBool is true
                                    if(editProfileFirstLastNameBool == false){
                                      setState((){
                                        ///Keep at the top to avoid resetting values anything before it
                                        /// Resets all Boolean values
                                        resetBooleanValues(setState);
                                        /// END
                                        /// hides title for sms phone number if true
                                        editBusinessNo = true;

                                        isProfileEdit = true;
                                        /// Call to close Main Contact Number TextContainer
                                        businessContactNumberTextContainerVisibilityBool = false;

                                        /// Call to close FirstLastName Visibility
                                        firstLastNameContainerVisibilityBool = false;
                                        emailContainerVisibilityBool = false;
                                        passWordContainerVisibilityBool = false;
                                        contactNumberTextContainerVisibilityBool =false;

                                        isBusinessPhoneNumberChangingBool = true;
                                        editProfileBusinessPhoneNumberBool = true;

                                        /// Calls to clear TextFormField
                                        userProfileBusinessContactNoController.clear();
                                      });
                                    }
                                    else{
                                      // Call to close FirstLastName Visibility
                                      firstLastNameContainerVisibilityBool = false;
                                    }
                                  },
                                  child:  CircleAvatar(
                                      radius: 16.0,
                                      backgroundColor: Colors.black12,
                                      child: (isPhoneNumberVerified == false)
                                          ?editIconsOnOff(editProfileFirstLastNameBool == false)
                                          :Icon(Icons.verified_user_outlined, color: Colors.teal, size: 16,)
                                  ),
                                ),

                              ))
                      )
                  )
              )
          )
      ),
      Container(
          child: Column(
            children:[
              /// Sized Box
              Visibility(
                  visible: (dialogReusableCircularIndicatorVisibilityBool == false
                      && getSMSCodeTextContainerVisibilityBool == false
                      && editProfileBusinessPhoneNumberBool==true
                      && isBusinessPhoneNumberChangingBool == true),
                  child:SizedBox(height: 15,)
              ),
              /// Title
              Visibility(
                  visible: (dialogReusableCircularIndicatorVisibilityBool == false
                      && getSMSCodeTextContainerVisibilityBool == false
                      && editProfileBusinessPhoneNumberBool==true
                      && isBusinessPhoneNumberChangingBool == true),
                  child:Container(
                    alignment: Alignment.center,
                    child: Text("New Business Phone Number",
                        style: GoogleFonts.sourceSerifPro(
                            color: Colors.black,
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                            letterSpacing: 1.2,
                            height: 1.5),
                        textAlign: TextAlign.left,
                        maxLines: 50,
                        overflow: TextOverflow.visible),)

              ),
              /// Sized Box
              Visibility(
                  visible: (dialogReusableCircularIndicatorVisibilityBool == false
                      && getSMSCodeTextContainerVisibilityBool == false
                      && editProfileBusinessPhoneNumberBool==true
                      && isBusinessPhoneNumberChangingBool == true
                      && isPhoneVerificationSuccessfulVisibilityBool == false
                  ),
                  child:SizedBox(height: 25,)
              ),
              /// Phone Number TextFormFiled
              Visibility(
                  visible: (editProfileBusinessPhoneNumberBool == true
                      && getSMSCodeTextContainerVisibilityBool == false
                      && isPhoneVerificationSuccessfulVisibilityBool == false
                      && dialogReusableCircularIndicatorVisibilityBool == false),
                  child: Padding(
                      padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
                      child:Form(
                          key:userBusinessPhoneNumberFormKey,
                          child: TextFormField(
                              autofocus: true,
                              cursorColor: Colors.white60,
                              style: TextStyle(color: Colors.white),
                              textAlign: TextAlign.center,
                              controller: userProfileBusinessContactNoController,
                              enabled: (editProfileBusinessPhoneNumberBool == true)?true:false,
                              decoration: InputDecoration(
                                floatingLabelBehavior: FloatingLabelBehavior.never,
                                filled: true,
                                fillColor: Colors.grey.shade900,
                                isDense: true,
                                border: OutlineInputBorder(
                                  borderSide: BorderSide.none,
                                  borderRadius: BorderRadius.circular(12),
                                ),
                                suffixIcon: IconButton(
                                  onPressed: () => setState(() {userProfileBusinessContactNoController.clear();}),
                                  icon: Icon(Icons.clear_rounded, size: 12, color: Colors.red.shade400,),
                                ),
                              ),
                              inputFormatters: <TextInputFormatter>[FilteringTextInputFormatter.allow(RegExp(r'[0-9]')),],
                              validator:(value){
                                if (value == null || value.isEmpty || !isPhone(value) || value.length < 10 )
                                {
                                  return 'Please enter a valid phone number.'
                                      '\nAt least 10 numbers, no spaces'
                                      '\nand no " ( ), - , _ "';
                                }
                                return null;

                              },
                              onTap: (){
                                //setState((){});
                              },
                              onChanged:(value) {
                                //setState((){});
                              }
                          )
                      )
                  )
              ),
              /// Sized Box
              Visibility(
                  visible: (dialogReusableCircularIndicatorVisibilityBool == false
                      && getSMSCodeTextContainerVisibilityBool == false
                      && editProfileBusinessPhoneNumberBool==true
                      && isBusinessPhoneNumberChangingBool == true
                      && isPhoneVerificationSuccessfulVisibilityBool == false
                  ),
                  child:SizedBox(height: 25,)
              ),
              /// Button for new Phone verify
              Visibility(
                visible: (editProfileBusinessPhoneNumberBool==true
                    && isPhoneVerificationSuccessfulVisibilityBool == false
                    && getSMSCodeTextContainerVisibilityBool == false
                    && dialogReusableCircularIndicatorVisibilityBool == false
                )?true:false,
                child: ElevatedButton(
                  onPressed: () {
                    setState(()  {
                      businessPhoneNumber = ("+1"+userProfileBusinessContactNoController.text);

                      if(userBusinessPhoneNumberFormKey.currentState!.validate()
                          && editProfileBusinessPhoneNumberBool == true
                          ) {
                        /// returns to main agent profile page
                       // resetBooleanValues(setState);

                        savingEditedProfile(userProfileFirstNameController.text,userProfileLastNameController.text, userEmail);
                        userProfilePopulateController(context,setState);

                        Future.delayed(Duration(milliseconds: 100),(){
                          resetValuesForBusinessPhoneNoEdit(setState);
                        });

                        /// Calls circular indicator
                        timerValue = 2;
                        stringLabel = "...In Progress";
                        (kIsWeb == true || Platform.isMacOS == true)
                            ?reusableCircularProgressIndicatorDialog(context)
                            :mobileReusableCircularProgressIndicatorDialog(context);
                      }
                      else {
                        return;
                      }

                    });
                  },child: Text('Business Phone Verify'),
                ),),
              /// Sized Box
              Visibility(
                  visible: isPhoneVerificationSuccessfulVisibilityBool,
                  child:SizedBox(height: 40,)
              ),
            ],
          )
      ),

    ],);
}
/// Reset variables when Business Phone Number Edit is done
resetValuesForBusinessPhoneNoEdit(setState){
  setState((){

    /// if true, call the get data from firebase and update sharedPref and variables
    agentProfileUpdateBool = true;
    /// hides title for sms phone number if true
    editBusinessNo = false;
    isProfileEdit = false;
    /// Call to close Main Contact Number TextContainer
    businessContactNumberTextContainerVisibilityBool = true;
    contactNumberTextContainerVisibilityBool = true;

    /// Call to close FirstLastName Visibility
    firstLastNameContainerVisibilityBool = true;
    emailContainerVisibilityBool = true;
    passWordContainerVisibilityBool = true;
    phoneNumberContainerVisibilityBool = true;

    isBusinessPhoneNumberChangingBool = false;
    editProfileBusinessPhoneNumberBool = false;
  });
}
/// Displays Success on new Phone verification
verifiedPhoneTextContainer(setState){
  return Column(children: [
    SizedBox(height: 25,),
    Text("Successfully Verified !",
        overflow: TextOverflow.ellipsis,
        maxLines:3,
        style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
        textAlign: TextAlign.start),
    SizedBox(height: 10,),
    Text(userProfileContactNoController.text,
        overflow: TextOverflow.ellipsis,
        maxLines:1,
        style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
        textAlign: TextAlign.start),
    SizedBox(height: 10,),
    Text("closing in .. $reusableCircularIndicatorValue secs",
        overflow: TextOverflow.ellipsis,
        maxLines:1,
        style: (isDarkThemBool ==true)?styleTextGoogleFontsDarkThemeTextField():styleTextGoogleGreyFontsTextField(),
        textAlign: TextAlign.start),
  ],);
}
/// Display Phone is already existed and verified and linked. (Old phone)
phoneAlreadyExisted(){
  return Column(children: [
    Text("Is already in used !"
        "\nPlease enter a new number",
        //"\nfor the current user:",
        overflow: TextOverflow.ellipsis,
        maxLines:6,
        style: errorStyleTextGoogleFontsVerificationText(),
        textAlign: TextAlign.center),
    SizedBox(height: 20,),
   // Text(_userProfileContactNoController.text,
        //overflow: TextOverflow.ellipsis,
        //maxLines:1,
        //style: styleTextGoogleFontsTextTealShade50(),
        //textAlign: TextAlign.start),
  ],);
}

/// Requesting Pause Payment
TextEditingController pauseRequestTextController = TextEditingController();
String subscriptionStatusString = "";
String subscriptionCancellationString = "";
String monthDurationDropDownInitialValue = "00 Month/s";
List<String> listOfPauseMonthDuration = [
  "00 Month/s",
  "01 Month",
  "02 Months",
  "03 Months",
  "04 Months",
  "05 Months",
  "06 Months",
  "07 Months",
  "08 Months",
  "09 Months",
  "10 Months",
  "11 Months",
  "12 Months"
];
requestPausedSubscriptionDialog(context){
  //print(remainingDaysInt.toString() + " remainingDaysInt");
  showDialog(
    //barrierColor: Colors.black,
      barrierDismissible: false,
      useSafeArea: true,
      context: context,
      builder: (context) {
        return BackdropFilter(
            filter: ImageFilter.blur(
              sigmaX: 10.0,
              sigmaY: 10.0,
            ),
            child: StatefulBuilder(builder: (context, setState) {
              return SingleChildScrollView(
                  child: Container(
                    //child: FittedBox(
                    // fit: BoxFit.scaleDown,
                    child: Stack(
                      children: <Widget>[
                        Container(
                          decoration: BoxDecoration(
                            // color: Colors.white,
                            //shape: BoxShape.rectangle,
                            borderRadius: BorderRadius.circular(16.0),
                          ),
                          child: AlertDialog(
                            scrollable: true,
                            backgroundColor: Colors.white,
                            title: Container(
                              alignment: Alignment.center,
                              child: Text("Requesting Pause Subscription/Payment",
                                style: TextStyle(
                                  color: Colors.black,
                                ),
                              ),
                            ),
                            content: Container(
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                mainAxisSize: MainAxisSize.min,
                                children: <Widget>[
                                  SizedBox(height: 20),
                                  /// Paused Selection and text
                                  Visibility(
                                    visible: true,
                                    child: Container(
                                        width: textFieldSized,
                                        child: Padding(
                                            padding:
                                            EdgeInsets.fromLTRB(0, 0, 0, 0),
                                            child: Column(
                                                children:[
                                                  Padding(
                                                      padding: EdgeInsets.fromLTRB(0, 0, 0, 0),
                                                      child:Material(
                                                          borderRadius: BorderRadius.circular(12),
                                                          color: Colors.white,
                                                          shadowColor: tealShadowColorRGB(),
                                                          elevation: elevationTextContainer(),
                                                          child: Padding(
                                                            padding: EdgeInsets.fromLTRB(5,5,10,5),
                                                            child:DropdownButtonFormField<String>(
                                                              borderRadius:BorderRadius.circular(12),
                                                              isExpanded: true,
                                                              alignment: Alignment.center,
                                                              decoration: InputDecoration(
                                                                border: InputBorder.none,
                                                                prefixIcon: Icon(
                                                                    Icons.more_time_rounded,
                                                                    color: Color
                                                                        .fromRGBO(
                                                                        20,
                                                                        20,
                                                                        20,
                                                                        1),
                                                                    size: 18),
                                                              ),
                                                              icon: Visibility(
                                                                  visible: true,
                                                                  child: Icon(
                                                                    Icons.arrow_circle_down_outlined,
                                                                    color: Color
                                                                        .fromRGBO(
                                                                        20,
                                                                        20,
                                                                        20,
                                                                        1),
                                                                    size: 18,
                                                                  )),
                                                              isDense: true,
                                                              focusColor: Colors.white,
                                                              dropdownColor: Colors.white,
                                                              style: dialogStyleTextGoogleFontsDark(),
                                                              value: monthDurationDropDownInitialValue,
                                                              hint: Text('Month Duration'),
                                                              onTap: () {
                                                                setState(() {

                                                                });
                                                              },
                                                              onChanged: (newValue) {
                                                                setState(() {
                                                                  monthDurationDropDownInitialValue = newValue.toString();
                                                                  if (monthDurationDropDownInitialValue != "00 Month/s") {
                                                                    pauseRequestTextController.text = (newValue.toString());

                                                                  }
                                                                });
                                                              },
                                                              items: listOfPauseMonthDuration.map((hour) {
                                                                return DropdownMenuItem(
                                                                  alignment: Alignment.center,
                                                                  child: (listOfPauseMonthDuration.length == 0)
                                                                      ? Text(
                                                                      monthDurationDropDownInitialValue)
                                                                      : new Text(hour.toUpperCase().toString(),
                                                                    style: GoogleFonts.sourceSerifPro(
                                                                        color: Colors.red.shade900,
                                                                        fontSize: 16,
                                                                        fontWeight: FontWeight.normal,
                                                                        letterSpacing: 1.2,
                                                                        height: 1.5),
                                                                    //userDashBoardAlertDialogStyleTextGoogleFontsBlack()
                                                                  ),
                                                                  value: hour.toString(),
                                                                );
                                                              }).toList(),
                                                              validator: (value) {
                                                                if (value == null ||
                                                                    value == "00 Month/s") {
                                                                  return 'Please Choose Month Duration !!!';
                                                                }
                                                                return null;
                                                              },
                                                            ),
                                                          )
                                                      )
                                                  ),
                                                  SizedBox(height: 30,),
                                                  /// Pause details text
                                                  Text(pausedDetailString,
                                                      style: GoogleFonts.montserrat(
                                                          color: Color.fromRGBO(
                                                              39, 37, 37, 1),
                                                          fontSize: 12,
                                                          fontWeight: FontWeight.normal,
                                                          letterSpacing: 1.2,
                                                          height: 1.5),
                                                      textAlign: TextAlign.center,
                                                      maxLines: 50,
                                                      overflow: TextOverflow.visible),
                                                  SizedBox(height: 30,),
                                                  /// Customer Portal Payment Link
                                                  InkWell(child: Text("Receipt & Payment",
                                                      style: GoogleFonts.sourceSerifPro(
                                                          color: Colors.teal,
                                                          fontSize: 14,
                                                          fontWeight: FontWeight.bold,
                                                          letterSpacing: 1.2,
                                                          height: 1.5),
                                                      textAlign: TextAlign.center,
                                                      maxLines: 50,
                                                      overflow: TextOverflow.visible),
                                                    onTap: ()  async {
                                                      /// Customer Portal Link
                                                      customerPortalLink();
                                                    },
                                                  ),
                                                ]
                                            )
                                        )
                                    ),
                                  ),
                                  SizedBox(height: 20),
                                ],
                              ),
                            ),
                            actions: <Widget>[
                              Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  /// Close Button
                                  Visibility(
                                    child: ElevatedButton(
                                      child: Text(
                                        "Close",
                                        style: TextStyle(
                                            color: Colors.white,
                                            fontWeight: FontWeight.bold),
                                      ),
                                      onPressed: () {
                                        setState(() {
                                          pauseRequestTextController.clear();
                                          monthDurationDropDownInitialValue = '00 Month/s';
                                          Navigator.pop(context);
                                        });
                                      },
                                    ),
                                  ),
                                  SizedBox(width: 20,),
                                  /// Request Button
                                  Visibility(
                                    child: ElevatedButton(
                                      child: Text(
                                        "Requesting",
                                        style: TextStyle(
                                            color: Colors.white,
                                            fontWeight: FontWeight.bold),
                                      ),
                                      onPressed: () {
                                        setState(() {
                                          if(pauseRequestTextController.text != ""
                                              && pauseRequestTextController.text != '00 Month/s'
                                              && pauseRequestTextController.text.isNotEmpty){


                                            /// Make a stripe on paused Request
                                            String stringNoMonths = pauseRequestTextController.text.substring(0,2).trim();
                                            //print(stringNoMonths.toString() + " stringNoMonths");
                                            int numberOfMonthsOnPaused = int.parse(stringNoMonths);
                                            //print(numberOfMonthsOnPaused.toString() + " numberOfMonthsOnPaused");
                                            requestPausedSubscription(numberOfMonthsOnPaused);
                                            ///

                                            //print(pauseRequestTextController.text.toString() +  " pauseRequestTextController.text");
                                            pauseRequestTextController.clear();
                                            monthDurationDropDownInitialValue = '00 Month/s';


                                            /// Call Circular Indicator
                                            timerValue = 3;
                                            stringLabel = "Requesting  ...In Progress";
                                            (kIsWeb == true || Platform.isMacOS == true)
                                                ?reusableCircularProgressIndicatorDialog(context)
                                                :mobileReusableCircularProgressIndicatorDialog(context);
                                            ///
                                            ///

                                            /// add delay to wait for indicator to be done
                                            Future.delayed(Duration(milliseconds: 3050),(){
                                              /// Call Dialog request is successfully processed
                                              Navigator.pop(context);
                                              onPausedRequestSuccessfulDialog(context, numberOfMonthsOnPaused);
                                              ///
                                            });
                                          }else{return;}

                                        });
                                      },
                                    ),
                                  ),

                                ],
                              ),
                              SizedBox(
                                height: 20,
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                    //)
                  ));
            }));
      });
}
/// Call Dialog request is successfully processed
onPausedRequestSuccessfulDialog(context, int noMonths){
  int monthNo = noMonths*30;
  showDialog(
    //barrierColor: Colors.black,
      barrierDismissible: false,
      useSafeArea: true,
      context: context,
      builder: (context) {
        return BackdropFilter(
            filter: ImageFilter.blur(
              sigmaX: 10.0,
              sigmaY: 10.0,
            ),
            child: StatefulBuilder(builder: (context, setState) {
              return SingleChildScrollView(
                  child: Container(
                    //child: FittedBox(
                    // fit: BoxFit.scaleDown,
                    child: Stack(
                      children: <Widget>[
                        Container(
                          decoration: BoxDecoration(
                            // color: Colors.white,
                            //shape: BoxShape.rectangle,
                            borderRadius: BorderRadius.circular(16.0),
                          ),
                          child: AlertDialog(
                            scrollable: true,
                            backgroundColor: Colors.white,
                            title: Container(
                              alignment: Alignment.center,
                              child: Text('Subscription ON PAUSE:',
                                style: TextStyle(
                                  color: Colors.black,
                                ),
                              ),
                            ),
                            content: Container(
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                mainAxisSize: MainAxisSize.min,
                                children: <Widget>[
                                  SizedBox(height: 10),
                                  /// Text Details
                                  Visibility(
                                    child: Container(
                                        width: textFieldSized,
                                        child: Padding(
                                            padding:
                                            EdgeInsets.fromLTRB(0, 0, 0, 0),
                                            child: Column(
                                                children:[
                                                  ///SUCCESSFUL
                                                  Text("Processed",
                                                      style: GoogleFonts.sourceSerifPro(
                                                          color: Colors.red.shade900,
                                                          fontSize: 25,
                                                          fontWeight: FontWeight.normal,
                                                          letterSpacing: 1.2,
                                                          height: 1.5),
                                                      textAlign: TextAlign.center,
                                                      maxLines: 50,
                                                      overflow: TextOverflow.visible),
                                                  SizedBox(height: 20,),
                                                  ///Start Date
                                                  Text("Pause ends on:   "+yyMmDd.format(userProfileDateListTemp.trialTime.add(Duration(days: monthNo))),
                                                      style: GoogleFonts.sourceSerifPro(
                                                          color: Colors.red.shade900,
                                                          fontSize: 18,
                                                          fontWeight: FontWeight.normal,
                                                          letterSpacing: 1.2,
                                                          height: 1.5),
                                                      textAlign: TextAlign.center,
                                                      maxLines: 50,
                                                      overflow: TextOverflow.visible),
                                                  SizedBox(height: 20,),
                                                  SizedBox(height: 20,),
                                                  Text(successfulOnPauseRequestString,
                                                      style: GoogleFonts.montserrat(
                                                          color: Color.fromRGBO(
                                                              39, 37, 37, 1),
                                                          fontSize: 12,
                                                          fontWeight: FontWeight.normal,
                                                          letterSpacing: 1.2,
                                                          height: 1.5),
                                                      textAlign: TextAlign.center,
                                                      maxLines: 50,
                                                      overflow: TextOverflow.visible),
                                                  SizedBox(height: 30,),
                                                  /// Customer Portal Payment Link
                                                  InkWell(child: Text("Receipt & Payment",
                                                      style: GoogleFonts.sourceSerifPro(
                                                          color: Colors.teal,
                                                          fontSize: 14,
                                                          fontWeight: FontWeight.bold,
                                                          letterSpacing: 1.2,
                                                          height: 1.5),
                                                      textAlign: TextAlign.center,
                                                      maxLines: 50,
                                                      overflow: TextOverflow.visible),
                                                    onTap: ()  async {
                                                      /// Customer Portal Link
                                                      customerPortalLink();
                                                    },
                                                  ),

                                                ]
                                            )
                                        )
                                    ),
                                  ),
                                  SizedBox(height: 20),
                                ],
                              ),
                            ),
                            actions: <Widget>[
                              Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Stack(
                                    alignment: Alignment.center,
                                    children: <Widget>[
                                      /// Close Button
                                      Visibility(
                                        //visible: false,
                                        visible: true,
                                        child:InkWell(child: Text("Close",
                                            style: GoogleFonts.sourceSerifPro(
                                                color: Colors.blueGrey,
                                                fontSize: 14,
                                                fontWeight: FontWeight.bold,
                                                letterSpacing: 1.2,
                                                height: 1.5),
                                            textAlign: TextAlign.center,
                                            maxLines: 50,
                                            overflow: TextOverflow.visible),
                                          onTap: (){
                                          Navigator.pop(context);
                                          },
                                        ),
                                      ),
                                    ], //<Widget>[]
                                  ),
                                ],
                              ),
                              SizedBox(
                                height: 20,
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                    //)
                  ));
            }));
      });
}
///
String successfulOnPauseRequestString ="Paused Subscription will start at the end of your current due date.\n\n"
    "And Payment charge will start at the end of the paused period.\n\n"
    "When on pause, you are still able to log in, to edit or resume your subscription.\n\n";
///
String pausedDetailString="You are requesting To PAUSE Payment and Restrict Access on Month/s you have selected.\n\n"
    "Payment will automatically resume at the end of PAUSED DATE.\n\n"
    "Note that pause payment ONLY starts at the end of your current subscription.";
///

/// Populating TextEditingController at StartUp
String subscriptionString = "";
String isUserAgentPaid = "";
DateTime trialTimeString = DateTime.now();

userProfilePopulateController(context ,setState){
  FirebaseAuth.instance
      .authStateChanges()
      .listen((User? user) async {
    if (user == null) {
      print('User is currently signed out!');
    } else {
      final temProfileData = await getAgentProFileOnce();
      String tempStringSMSPhoneNo = "";
      String tempBusinessPhoneNo = "";

      tempName.add(user.displayName?.split("_")[0]);
      tempName.add(user.displayName?.split("_")[1]);

      if(user.phoneNumber != null && user.phoneNumber!.length>10){
        tempStringSMSPhoneNo = user.phoneNumber!.substring(2,user.phoneNumber!.length);
      }

      if(temProfileData.userProfileDateList.businessNumber != null
          && temProfileData.userProfileDateList.businessNumber.length>10){
        tempBusinessPhoneNo = temProfileData.userProfileDateList.businessNumber
            .substring(2,temProfileData.userProfileDateList.businessNumber.length);
      }
      if(temProfileData.userProfileDateList.subscription != ""){
        subscriptionString = temProfileData.userProfileDateList.subscription;
      }
      if(temProfileData.userProfileDateList.trialTime != null){
        trialTimeString = temProfileData.userProfileDateList.trialTime;
      }


      userProfileFirstNameController.clear();
      userProfileLastNameController.clear();
      userProfileEmailController.clear();
      userProfileOldPasswordController.clear();
      userProfileContactNoController.clear();
      //userProfileBusinessContactNoController.clear();

    setState((){
      isUserAgentPaid = temProfileData.userProfileDateList.billStatus.toUpperCase().toString();
      userProfileFirstNameController.text = tempName[0].toString();
      userProfileLastNameController.text = tempName[1];
      userProfileEmailController.text = ((user.email != null)?user.email :"No Email Saved")!;
      userProfileOldPasswordController.text = "************";
      userProfileContactNoController.text = ((user.phoneNumber != "")?tempStringSMSPhoneNo :"No Phone Number Uploaded");
      userProfileSubscriptionController.text = subscriptionString;
      userProfileBusinessContactNoController.text = tempBusinessPhoneNo;
      tempStringBusinessPhoneNo = (temProfileData != null)?tempBusinessPhoneNo: "  No Business NO";

    });


    }
  });
}

final String clientPrivacyAgreement =
    "KuboMaster.LLC's and affiliates and KuboMaster's software application and website, will/has the intention to collect, "
    "PERSONAL DATA, which is not limited to:\n\n"
    "*  Full Name, \n"
    "*  Email, \n"
    "*  Phone Number,\n\n"
    "WE, gather these data information for all intent and purposes, of creating a: \n\n"
    "*  Realty Agent Account,\n"
    "*  Enables realty agent to call, message and email potential buyer.\n"
    "*  Enables KuboHut both web and mobile application to set appointment reminder by means of sms message and email. \n\n"
    "WE, dot not share any private and public information, unless these information is "
    "for you the client and user(the potential home buyer) "
    "is using these information strictly for  communication in relation to/for :\n\n"
    "* House viewing schedule,\n"
    "* Appointment Reminder, cancel and updates\n\n"
    "Outside of the scope mentioned above, KuboMaster.LLC and all of it's affiliates is/are "
    "not liable to any/all potential liabilities/damages.\n\n"
    "By design all of KuboMaster.LLC's web/mobile software application, does not have an option to download , print, or procure any/all "
    "DIGITAL copies by YOU(Client) or by any outside/third party, including USER(Potential buyer), ENSURING, "
    "all public and private data will not be abuse.\n\n"
    "* Storage and Retention *\n\n"
    "All PUBLIC and PRIVATE  DATA is stored in a secure password protected online server.\n"
    "Data is encrypted and only KuboMaster.LLC and affiliates, YOU(CLIENT) and USER(HOUSE POTENTIAL BUYER) is able to "
    "read, decrypt and process data specific only to USER's information. \n\n"
    "Data is/are retained only from the time of creation up to the whole duration of House Viewing Appointment "
    "and to a MAXIMUM of 7 days after the appointment schedule date. "
    "DATA will be PERMANENTLY deleted and untraceable.\n\n"
    "Due to the nature of untraceable deletion of USER data, KuboMaster.LLC expects YOU(CLIENT/AGENT REALTOR) to self obtain and or procure "
    "both personal and public information of USER(HOUSE POTENTIAL BUYER) by personally obtaining permission from USER(HOUSE POTENTIAL BUYER),\n\n"
    "KuboMaster.LLC and affiliates is/are not liable to any/all potential damages/liability in regards to obtaining "
    "USER(HOUSE POTENTIAL BUYER) personal and public information outside of KuboMaster.LLC and affiliates scope of service.\n\n"
    "Below bears the name of the Client, who adheres and understand the rules/terms being imposed. And legally binds itself and "
    "releases KuboMaster.LLC and affiliates from any/all liabilities of misuse, abuse, dishonest, and any illegal activities";


final String termsOfUse =
    "I agreed to abide, to KuboMaster.LLC user agreement and to a government rules and protection against any form of abuse of use.\n\n"
    "I acknowledge that KuboMaster.LLC's software application and website, requires all users to use it to "
    "its fullest extent only for the sole purpose of:"
    "\n\n * Maintaining calendar schedule "
    "\n\n * Creating schedule for home viewing, "
    "\n\n * Enabling potential house buyer to make home viewing appointment."
    "\n\n * Communication, exclusively by programmable text message and email for the sole purpose of:"
    "\nAppointment setup, updates, cancellation, and reminder."
    "\n\n * Create QR code to display, for USER(HOUSE POTENTIAL BUYER) to scan and redirected to a "
    "website where USER(HOUSE POTENTIAL BUYER) is/are able to set up, edit, cancel a house viewing appointment "
    "\n\n\nAny use of KuboMaster.LLC's software application for web and mobile beyond what is mention above,"
    "is considered abuse of use, and deemed illegal, punishable by all extent of the law. "
    "\n\nKuboMaster.LLC is not and will not be liable to any and all damages that resulted from abuse of use."
    "\n\nWith my full knowledge, I am willing to provide my personal data, for all intent and purposes,"
    "of creating a\n\n"
    "*  Realtor Agent Account,\n\n"
    "*  Payment Processing for  monthly or annual Subscription"
    "\n\nBelow bears the name of the Client, who adheres and understand the rules/terms being imposed. And legally binds itself and "
    "releases KuboMaster.LLC and affiliates from any/all liabilities of misuse, abuse, dishonest, and any illegal activities";


final String userInfoGatheringString =
    "I acknowledged that KuboMaster.LLC's software application and website, will/has the intention to collect,"
    "my personal data, which is not limited to:\n\n"
    "*  Full Name, \n"
    "*  Email, \n"
    "*  Phone Number,\n"
    "*  Credit Card information for Payment, \n\n"
    "and with my full knowledge, I am willing to provide the said personal data, for all intent and purposes,"
    "of creating\n\n"
    "*  A realtor agent account,\n"
    "*  Payment Processing for  monthly or annual Subscription\n"
    "*  Communication between YOU(CLIENT/AGENT REALTOR) and USER(HOUSE POTENTIAL BUYER) and KuboMaster.LLC.\n"
    "My personal data acquired by KuboMaster.LLC and affiliates ,"
    "will be converted in an encrypted form, preventing unauthorized entity to access."
    "My data is stored securely in an online database server\n\n"
    "My personal information will NOT be shared to any entity except to the intending, receiver, that is the "
    "Realtor Agent and or potential house buyer. \n\n"
    "I do understand, that the intending potential house buyer will make contact either by phone or by email\n\n"
    "ALL DATA, will be deleted in a rolling maintenance basis and an IMMEDIATE DELETION without TRACE "
    "from all data storage is to be expected, upon request by either YOU(CLIENT/AGENT REALTOR) "
    "and/or USER(HOUSE POTENTIAL BUYER)";

  stringForPaymentPlatform(){
    if(paymentPlatformString == ""){
      return "Please LOG IN to your Google Play account"
          "\n\n OR LOG IN to Kubo Master Android App and go to payment section of your profile"
          "\n\n THANK YOU";
    }
    else if(paymentPlatformString == "ApplePay"){
      return "Please LOG IN to your APPLE STORE account"
          "\n\n OR LOG IN to Kubo Master IOS and go to payment section of your profile"
          "\n\n THANK YOU";
    }
    else if(paymentPlatformString == "GooglePay"){
      return "Please LOG IN to your Google Play account"
          "\n\n OR LOG IN to Kubo Master Android App and go to payment section of your profile"
          "\n\n THANK YOU";
    }
    else if(paymentPlatformString == "ApplePay"){
      return "Please LOG IN to your APPLE STORE account"
          "\n\n OR LOG IN to Kubo Master IOS or MACOS App and go to payment section of your profile"
          "\n\n THANK YOU";
    }
  }
