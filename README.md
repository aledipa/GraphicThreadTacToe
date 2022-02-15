# Graphic ThreadTacToe

A JavaFX multithreading desktop Tic Tac Toe game
(Check [Notes](#notes))

## Description

The main class takes care of the graphics and functions for the win and timer control. A non-graphical thread takes care of the timer, while another thread takes care of its termination and of the screens, including the end game screen.
There are two synchronized threads that take care of the alternation of the players' turns and save their information so that it can be analyzed in case of victory. They end when the match ends.



## Getting Started

### Dependencies

* Needed JavaFX and FXML libraries
* Main imports:
  * java.io.IOException;
  * javafx.application.Application;
  * javafx.event.ActionEvent;
  * javafx.event.EventHandler;
  * javafx.scene.Group;
  * javafx.scene.Scene;
  * javafx.scene.control.Button;
  * javafx.scene.text.*;
  * javafx.stage.Stage;
  * javafx.scene.layout.GridPane;
  * java.util.concurrent.ExecutorService;
  * java.util.concurrent.Executors;
  * javafx.application.Platform;
  * javafx.scene.layout.Pane;
  * javafx.scene.paint.Color;
 

### Installing

```
# Clone the repository
$ git clone https://github.com/aledipa/GraphicThreadTacToe.git
$ cd GraphicTreadTacToe/

# Test run the code
$ cd src/graphicthreadtactoe/
$ javac *.java && java GraphicThreadTacToe
```

### Executing program

you can either
* Download the [lastest release](https://github.com/aledipa/GraphicThreadTacToe/releases/tag/v1.0)
* Compile the source by yourself
```
# Create build folder
$ mkdir 

# Compile to Jar file in build
$ javac -d ./build *.java
$ cd build/
$ jar cvf GraphicThreadTacToe.jar *
```

## Help

This project was developed using the NetBeans environment, so if you encounter some errors, just import the project on NetBeans and Run/Build it using the NetBeans IDE.

## Authors

Alessandro Di Pasquale 
Developer's accounts:
- [GitHub - AleDipa](https://github.com/aledipa)
- [GitLab - AleDipa](https://gitlab.com/AleDipa)

## Version History

* 0.2
    * Various bug fixes and optimizations
    * See [commit change](https://github.com/aledipa/GraphicThreadTacToe/commits/main) or See [release history](https://github.com/aledipa/GraphicThreadTacToe/releases)
* 0.1
    * Initial Release

## License

This project is licensed under the MIT License - see the LICENSE.md file for details

## Notes
This was developed as a school project
