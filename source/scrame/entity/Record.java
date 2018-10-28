package scrame.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import scrame.entity.Course;
import scrame.entity.Student;
import scrame.helper.Mark;

public class Record {
  private Student student;
  private Course course;
  private HashMap<String, Float> mark;

  public Record(Student student, Course course, HashMap<String, Float> mark) {
    this.student = student;
    this.course = course;

    // whose values[1] == "false" (don't have child)

    HashSet<String> fromMark = new HashSet<String>();
    HashSet<String> fromCourse = new HashSet<String>();
    for (Map.Entry<String, Float> entry : mark.entrySet()) {
      fromMark.add(entry.getKey());
    }
    for (Map.Entry<String, String[]> entry : course.getWeightage().entrySet()) {
      if (entry.getValue()[1].equals("false")) {
        fromCourse.add(entry.getKey());
      }
    }
    if (!fromMark.containsAll(fromCourse) || !fromCourse.containsAll(
      fromMark
    )) {
      throw new IllegalArgumentException("Illegal mark set.");
    }
    this.mark = mark;
  }

  // TODO: public Map<String, Float> getMark();

  //   Map<String, String[]> weightage = c.getWeightage();

  //   }

  // }

  // }

  // }

  // }

  // }

  // }

  // }
}

