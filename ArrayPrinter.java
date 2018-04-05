public class ArrayPrinter {

    public void printArray(String[] arr) {
        for(String elem : arr) {
            System.out.printf("|%s|", elem);
        }
        System.out.println();
    }

    public void printArrayNoNextLine(String[] arr) {
        for(String elem : arr) {
            System.out.printf("|%s|", elem);
        }
    }

    public void printArray(String[][] table) {
        for(String[] elem : table) {
            printArray(elem);
        }
    }

    public void printArray(Integer[] arr) {
        for(int elem : arr) {
            System.out.printf("|%d|", elem);
        }
        System.out.println();
    }

    public void printArrayNoNextLine(Integer[] arr) {
        for(int elem : arr) {
            System.out.printf("|%d|", elem);
        }
    }


  //public void printArray(int[][] arr);




}//endClass
