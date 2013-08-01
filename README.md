## SureShotSAM-Java

This is the Java version of FRC Team 316 LuNaTeCs' 2013 robot code, and the definitive version going forward. The robot project is based off of the IterativeRobot template, and uses the Command-based robot pattern.

#### Main project files
- __SureShotSAM.java__: Main robot class. Subclasses `IterativeRobot`.  
- __RobotMap.java__: Mapping of OI constants to named values.  
- __OI.java__: TeleOp mode controller. Maps joystick inputs to subsystem actions. 
- __Constants.java__: Robot Constants manager. Uses WPILib `Preferences` class to update constants via the SmartDashboard
- __Debugger.java__: Helper class to aid in the process of printing debugging information to the console
- __subsystems/__: Robot Subsystem classes; defines the basic functionality of each of the robot subsystems
- __commands/__: Robot Command classes; define actions the robot can take
	- __CommandBase.java__: The base class for all commands. Contains a static instance of each robot subsystem
- __auto/__: Autonomous Commands
- __lib/__: Library code
  - __LuNaDrive.java__: Custom drivetrain class
  - __Potentiometer.java__, __Tachometer.java__: Wrapper classes for use with the WPILib PIDController class
  - __SimplePIDController.java__: Simple inline threadless loopless PIDController
  - __Util.java__: Utility functions

--

This code is released under the BSD License. Details can be found in the _LICENSE.md_ file. WPILib is copyright (c)2009 by FIRST; for details see _BSD_License_for_WPILib_code.txt_  

_LuNaTeCs 316_ is a competitive robotics team in the [FIRST Robotics Competition (FRC)](http://www.usfirst.org/roboticsprograms/frc/), part of a 501(c)(3) non-profit organization, _South Jersey Robotics, Inc._ To learn more, visit our website at [lunatecs316.org](http://lunatecs316.org)
