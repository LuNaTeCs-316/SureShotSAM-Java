/**
 * This file is one giant comment to help someone get started who is trying to 
 * understand team 316's code.
 * 
 * This code is written in java using the iterative method and the code 
 * is broken down into classes that attempt to describe 
 * (objectify) subsystems of the robot.
 * Higher level code then calls functions in each particular subsystem.
 * 
 ********************************************************************** 
 ********************************************************************** 
 * subsystems
 ********************************************************************** 
 ********************************************************************** 
 * 
 * so, in general, there are files located in the subsystem folder 
 * that control and provide input and  output for the following:
 * 
 * climber - used to control climbing the pyramid.  for us it is
 * basically just two pistons that pull our robot up to the first level
 * 
 * functions are:
 *	Climber.climb (true); //to retract piston and pull robot up
 *	Climber.climb (false); //to release piston and let robot down
 *
 *
 *
 * drivetrain - controls the wheels of the robot and also can 
 * provide indication of wheel rotations through encoders
 * mounted on the front two wheels.
 * 
 * functions are:
 * Drivetrain.arcadeDrive(Joystick) //-used in teleop
 * Drivetrain.arcadeDrive(throttle, turn) //autonomous
 * Drivetrain.tankDrive(left, right) //not used, for 2 joys
 * 
 * 
 * 
 * pickup - controls raising and lowering of the pickup arm and
 * running the pickup motor in forward or reverse.  no indications 
 * provided.
 * functions are:
 * Pickup.setBeltState (forward/reverse/off)
 * Pickup.raise ()
 * Pickup.lower ()
 * Pickup.stop ()
 * 
 * 
 * 
 * shooter - controls raising and lowering of the shooter and
 * running the shooter motor.  indications provided are shooter 
 * motor speed and height of the shoolter mech.  Lights on the rear 
 * of the pickup arm are also wired in to give indications to the
 * operators.
 * functions are:
 * Shooter.move () //move shooter mech up or down manually
 * Shooter.enable () //turn on shooter motor
 * Shooter.disable () //turn off shooter motor
 * Shooter.fire(true/false) //fire or retract firing piston
 * Shooter.indications () //provide led lights indications
 * Shooter.moveToPosition(position) //use speed controller to
 *	control speed of shooter wheel
 * Shooter.atSpeed ()  //return true if wheel is at speed
 * 
 * 
 ********************************************************************** 
 ********************************************************************** 
 * higher level control 
 ********************************************************************** 
 ********************************************************************** 
 * So now that we have briefly described the subsystems, we can look
 * at the higher level systems.  The robot will always be in one of two
 * different modes, either teleop which is manual control or autonomous
 * which is robot control.  The code which controls which mode we are in 
 * SureShotSam.java
 * 
 * Teleop -
 * The teleop mode is in the OI.java file - OI stands for 
 * OperatorInterface and that file is at the top level directory.
 * How this code works is that every 20msec the entire code loop runs
 * then we simply call all the functions there that we want controled.
 * In the main teleop loop of SureShotSam.java you will see:
 *      OI.runDrivetrain();
 *      OI.runPickup();
 *      OI.runShooter();
 *      OI.runClimber();
 *
 * So each of these functions is called every 20ms and then the status 
 * of the operator's buttons are checked, for example, and the  
 * corresponding actions are taken.
 * 
 * 
 * autonomous -
 * In autonomous mode SureShotSam.java reads the driver's 
 * station slider #1 using:
 * 	driverStation.getAnalogIn(1)) 
 * then calls autoMode.run() and the correct autonomous magically 
 * is called.  Currently there are only 3 autonomous modes written.
 * mode 0: do nothing  
 * 
 * mode 1: shoot 3 frisbees
 * 
 * mode 5: control with kinect.  This is only for fun.
 * 
 * 
 * 
 ********************************************************************** 
 ********************************************************************** 
 * library functions 
 ********************************************************************** 
 ********************************************************************** 
 * In addition to the code above, some of the code needs some "helper" 
 * code to be written and you will find such in the lib folder.  
 * 
 * PID loop wrapper classes -
 * These PID loops are used by the speed controllers to control   
 * an output in a loop based upon either a distance or a speed.
 * The PID controller code expects whatever is supplying that 
 * information to respond to the call "getRPM" or "getOutput",
 * for example.
 * Many of the devices we use, such as potentiometers or tachometers
 * will provide a value, but not respond to that function call.  What 
 * we do in those cases is create what is called a "wrapper class" 
 * which simply wraps the class with the function calls that the 
 * PID needs.
 * 
 * 
 * drive wheel controlling -
 * Here we have a situation where we are doing a lot of math
 * processesing to the joystick signal before we send it off to
 * the drive wheel controller.  The prurpose was to attempt to give
 * our robot a better ability to turn.
 * 
 * 
 * utility functions -
 * Libraries are also a good place to put functions that might be 
 * commonly used all over your code and are not specific to any one
 * object on the robot.  These are usually things like math functions,
 * ranging values, dead bands, etc.
 * 
 * 
 * @author jcardona
 */
