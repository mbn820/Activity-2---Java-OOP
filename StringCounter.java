public class StringCounter {

      public int countOccurencesOf(String text, String target) {
          if(text.length() > target.length()) { return 0; }

          int numberOfCompares = target.length() - text.length() + 1;
          int count = 0;

          for(int i = 0; i < numberOfCompares; i++) {
              String sub = target.substring(i, i + text.length());
              if(text.equals(sub)) count++;
          }

          return count;
      }


}//endClass
