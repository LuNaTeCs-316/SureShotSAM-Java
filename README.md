## SureShotSAM-Java

This is the Java version of FRC Team 316 LuNaTeCs' 2013 robot code, and the definitive version going forward. The robot project is based off of the IterativeRobot template, using a custom Subsystem/OI/Autonomous State pattern.

#### Main project files
- __SureShotSAM.java__: Main robot class. Subclasses `IterativeRobot`.  
- __RobotMap.java__: Mapping of OI constants to named values.  
- __OI.java__: TeleOp mode controller. Maps joystick inputs to subsystem actions.  
- __subsystems/__: Contains Subsystem classes
  - `Drivetrain`: Robot drivetrain
  - `Pickup`: Frisbee pickup subsystem
  - `Shooter`: Shooter subsystem
  - `Climber`: Climbing subsystem
- __auto/__: Autonomous related code
  - __AutonomousMode.java__: Abstract base class for all autonomous modes
  - __StateMachineAuto.java__: Base class for Finite State Machine based autonomous modes
  - __DoNothingAuto.java__: Self explanatory
  - __ThreeDiskAuto.java__: Simple auto mode to just shoot three frisbees
  - __FiveDiskAuto.java__: Five-disk center line autonomous; shoot first shots then pickup additional disks from the center line
  - __KinectAuto.java__: Drive the robot via the Microsoft Kinect
  - __TestAuto.java__: Autonomous for testing whatever needs to be tested
- __lib/__: Library code
  - __LuNaDrive.java__: Custom drivetrain class
  - __Potentiometer.java__, __Tachometer.java__: Wrapper classes for use with the WPILib PIDController class
  - __SimplePIDController.java__: Simple inline threadless loopless PIDController
  - __Util.java__: Utility functions

--

This code is released under the BSD License. Details can be found in the LICENSE.md file.  

_LuNaTeCs 316_ is a competitive robotics team in the [FIRST Robotics Competition (FRC)](http://www.usfirst.org/roboticsprograms/frc/), part of a 501(c)(3) non-profit organization, _South Jersey Robotics, Inc._ To learn more, visit our website at [lunatecs316.org](http://lunatecs316.org)
