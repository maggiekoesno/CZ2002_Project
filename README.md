# Student Course Registration And Mark Entry (SCRAME)

This project is an compulsory assignment for NTU's CZ2002 Object Oriented Design and Programming.

The SCRAME application is a console-based application, and as the name suggests, the application allows features related to registration of students to courses and mark entry. The application has 10 major features:

* Add a student
* Add a course
* Register student for a course
* Check available slot in a class
* Print student list for a course
* Enter course assessment components weightage
* Enter coursework mark
* Enter exam mark
* Print course statistics
* Print student transcript

This source code is Group SS5's implementation of the application, on Academic Year 2018/19 Semester 1.

## Getting Started

Clone this repository by executing

### Prerequisites

Make sure you have installed [Java Development Kit](https://www.oracle.com/technetwork/java/javase/downloads/index.html) in order to use `java` and `javac`. Don't forget to add JDK's bin folder to PATH.

Verify the installation of `java` with:

```
$ java -version
```

You'll see the following output:

```
java version "1.8.0_181"
Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)
```

Also, verify the installation of `javac`:

```
javac -version
```

You'll see the following output:

```
javac 1.8.0_181
```

### Installing

Once you have cloned this repository, create a `data` folder, which contains the `.ser` files for storing the data for the application.

```
mkdir data
cd data
touch courses.ser students.ser records.ser
```

Next, go back to the root folder and create a `classes` folder, which will contain all the `.class` files once the source code is compiled.

```
cd ..
mkdir classes
```

We have provided a script file to automate the compilation process of the source code. Execute the following command to use the script.

```
./script.sh
```

If the script is not executable, execute the following command:

```
chmod 755 script.sh
```

## Authors
The members of the group are:
* Elbert Widjaja
* Jason Sebastian
* Kevin Winata
* Margaret Claire

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
