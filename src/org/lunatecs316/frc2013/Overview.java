/**
 * This file is one giant comment to help someone get started who is trying to 
 * understand Team 316's code.
 * 
 * Our code is written in Java using the IterativeRobot style. The program is
 * organized into subsystems, an Operator Interface (OI)/Teleop Controller, and
 * Finite State Machine based Autonomous modes.
 * 
 ********************************************************************** 
 ********************************************************************** 
 * Subsystems
 ********************************************************************** 
 ********************************************************************** 
 * 
 * Subsystems are classes that group related robot sensors and actuators
 * together and define actions that can be performed. All subsystems extend
 * the base Subsystem class. A description of each subsystem follows:
 * 
 * Drivetrain - controls the drive wheels and provides navigation with the gyro
 * and encoders.
 * 
 * Methods:
 * Drivetrain#arcadeDrive(double throttle, double turn) - arcade style driving
 * Drivetrain#tankDrive(double left, double right) - tank style driving
 * Drivetrain#driveStraight(double inches) - moves the robot straight to a distance
 * Drivetrain#turn(double degrees) - turns the robot in place
 * 
 * Pickup - controls raising and lowering of the pickup arm and
 * running the pickup motor in forward or reverse.
 * 
 * Methods:
 * Pickup#raise() - raise the pickup arm
 * Pickup#lower() - lower the pickup arm
 * Pickup#stop() - stop movement of the pickup arm
 * Pickup#setBeltState(Pickup.BeltState state) - set the state of the pickup belts (forwards/reverse/off)
 * 
 * Shooter - controls raising and lowering of the shooter and
 * running the shooter motor. Indications provided are shooter 
 * motor speed and height of the shooter mech.  Lights on the rear 
 * of the pickup arm are also wired in to give indications to the
 * operators.
 * 
 * Methods:
 * Shooter#move(double val) - manually adjust the position of the shooter
 * Shooter#enable() - turn on the shooter motor
 * Shooter#disable() - turn off shooter motor
 * Shooter#fire(boolean value) - fire or retract firing piston
 * Shooter#indications() - provide led lights indications
 * Shooter#moveToPosition(double position) - move the shooter to a position
 * Shooter#atSpeed() - check if the shooter motor is up to speed
 * 
 * Climber - manages the climbing piston.
 * 
 * Methods:
 * Climber#extendHooks()
 * Climber#retractHooks()
 * 
 * The Subsystems (plural) class contains a single shared instance of each
 * subsystem. If working in a class that subclasses Subsystems (such as the OI
 * or any Autonomous mode), the subsystems can be accessed as regular variables:
 * 
 * drivetrain.arcadeDrive(0,0);
 * shooter.enable();
 * climber.extendHooks();
 * 
 * Elsewhere, the subsystems can be accessed through the Subsystems class:
 * 
 * Subsystems.drivetrain.arcadeDrive(0,0);
 * Subsystems.shooter.disable();
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
