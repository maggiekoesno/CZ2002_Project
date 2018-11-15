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

Clone this repository by executing the following command:

```bash
git clone https://github.com/maggiekoesno/CZ2002_Project.git
```

The `javadoc` HTML pages for the project is located under the `docs` folder. Open `index.html` to access the HTML pages.

### Prerequisites

Make sure you have installed the latest [Java Development Kit](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html) in order to use `java` and `javac`. Don't forget to add JDK's bin folder to PATH.

Verify the installation of `java` with:

```bash
java -version
```

You'll see the following output:

```
java version "1.8.0_181"
Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)
```

Also, verify the installation of `javac`:

```bash
javac -version
```

You'll see the following output:

```
javac 1.8.0_181
```

### Installing

Once you have cloned this repository, under the project's root folder, create a `data` folder, which contains the `.ser` files for storing the data for the application.

```bash
mkdir data
cd data
touch courses.ser students.ser records.ser
```

Next, go back to the root folder and create a `classes` folder, which will contain all the `.class` files once the source code is compiled.

```bash
cd ..
mkdir classes
```

A script file is provided to automate the compilation process of the source code. Execute the following command to compile the source code.

```bash
./script.sh
```

If the script is not executable, execute the following command:

```bash
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
